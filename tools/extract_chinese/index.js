const CryptoJS = require("crypto-js");
const fs = require("fs");
const Undici = require("undici");

const agent = new Undici.Agent({
  connect: {
    rejectUnauthorized: false
  }
})

Undici.setGlobalDispatcher(agent)

// define the main endpoint
const baseUrl = "https://card.yantootech.com/";
// the hardcoded secret found in the wxapkg
const secret = "L+~f4,Ir)b$=pkfb";

/**
 * Encrypt an object or a text given the predefined secret
 */
function encrypt(text) {
  if(typeof text == "object") {
    text = JSON.stringify(text);
  }

  let KEY2 = secret;
  let IV2 = secret;
  const data = CryptoJS.enc.Utf8.parse(text);
  const key = CryptoJS.enc.Utf8.parse(KEY2);
  const iv = CryptoJS.enc.Utf8.parse(IV2);
  let encrypted = CryptoJS.AES.encrypt(data, key, {
    iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  });
  return encrypted.toString();
}

/**
 * Decrypt a given text to a json object
 */
function decrypt(text) {
  const keyStr = secret;
  const ivStr = secret;
  var key = CryptoJS.enc.Utf8.parse(keyStr);
  const iv = CryptoJS.enc.Utf8.parse(ivStr);

  const decrypt = CryptoJS.AES.decrypt(text, key, {
    iv,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  });
  return JSON.parse(decrypt.toString(CryptoJS.enc.Utf8));
}

/**
 * Make a (post) request to the (public facing) API
 */
async function request(url, body) {
  const headers = {
    "Blade-Auth": "codlabtech@gmail.com",
    "token": "fooling around, having some time",
    "Tenant-Id": "xxx",
    "Content-Type": "application/json;charset=UTF-8",
    // for some reason, the post requests in the original package sets both...
    // since in some case, this could be important (yet buggy backend), let's keep it that way
    "content-type": "application/json"
  };

  const params = {
    method: "POST",
    headers: headers,
    body: JSON.stringify({ data: encrypt(body) })
  };

  const result = await fetch(url, params);
  const json = await result.json();

  if (json.code === '0000') {
    return decrypt(json.data);
  }

  console.log("having faulty answer :", json);
  throw `invalid code obtained ${json.code}`;
}

// PoC
// TODO: implementation of saving the json file for later extraction
async function retrievePage(page = 1) {
  console.log(`retrieving page ${page}`);
  const json = await request(`${baseUrl}/api/Cards/getList`,
    {
      page: page,
      page_size: 50
    }
  );

  const { cards_list } = json;
  const { page_size, total } = json.page_info;

  if (page_size * page < total) {
    const toAppend = await retrievePage(page + 1);
    return cards_list.concat(toAppend);
  }

  return cards_list;
}

const folder = "../../data_existing/catalog/zh/"
if (!fs.existsSync(folder)) {
  fs.mkdirSync(folder);
}

function last(string, split) {
  const _split = string.split(split)
  return _split[_split.length - 1];
}

retrievePage().then(array => {
  const cards = array.map(card => {
    const card_sets = Array.isArray(card.card_sets) ? card.card_sets : [ parseInt(card.card_sets) ]

    const specialMap = {
      "p1/1": "1/C1 ZH 1",
      "p1/2": "2/C1 ZH 1",
      "p1/3": "3/C1 ZH 2",
      "p1/4": "4/C1 ZH 1",
      "P1/8": "8/P1 ZH 1",
      "P1/8": "8/P1 ZH 1",
      "P1/9": "9/P1 ZH 1",
      "P1/10": "10/P1 ZH 1",
      "P1/25": "25/P1 ZH 1",
      "P1/30": "30/P1 ZH 1",
      "p2/14": "14/P1 ZH 2",
      "p2/15": "15/P1 ZH 2",
      "p2/16": "16/P1 ZH 2",
      "p2/17": "17/P1 ZH 2",
      "p2/31": "31/P1 ZH 2",
      "p2/32": "32/P1 ZH 2",
      "p3/26": "26/P1 ZH 3",
      "p3/27": "27/P1 ZH 3",
      "p3/28": "28/P1 ZH 3",
      "p3/29": "29/P1 ZH 3",
      "p3/39": "39/P1 ZH 3",
      "p4_34": "34/P1 ZH 4",
      "p4_35": "35/P1 ZH 4",
      "p4_36": "36/P1 ZH 4",
      "p4_37": "37/P1 ZH 4",
      "p4_38": "38/P1 ZH 4",
      "p4_40": "40/P1 ZH 4",
      "p3_43": "43/P2 ZH 3",
    }

    const hasOverride = specialMap[card.Collector_Number];

    if (!hasOverride && card.Collector_Number.indexOf("/") >= 0) {
      console.log(card)
      throw "Invalid Collector Number, couldn't process the card"
    }

    const set_number = "204";
    const collector_number = last(card.Collector_Number, "/");

    const card_identifier = !!card.card_identifier ? card.card_identifier
      : (
        !!hasOverride ? hasOverride : `${collector_number}/${set_number} ZH ${card.card_sets}`
      )

    return {
      "name": card.name,
      "subtitle": card.subtitle,
      "strength": card.strength,
      "willpower": card.willpower,
      "quest_value": card.quest_value,
      "rarity": "RARE", // todo card.rarity.toUppercase(),
      "ink_cost": card.ink_cost,
      "author": "Randy Bishop",
      "deck_building_id": card.deck_building_id,
      "culture_invariant_id": card.culture_invariant_id,
      "sort_number": card.sort_number,
      "additional_info": [],
      "ink_convertible": !isNaN(card.ink_convertible) ? card.ink_convertible > 0 : !!card.ink_convertible,
      "abilities": [], // TODO
      "subtypes": card.subtypes.map(sub => sub.name),
      "flavor_text": card.flavor_text,
      "rules_text": card.rules_text,
      "card_identifier": card_identifier,
      "image_urls": [
        {
          "height": 512,
          "url": `https://card.yantootech.com/${card.image_url}`.replace("/public/","/")
        }
      ],
      "foil_mask_url": "",
      "card_sets": card_sets,
      "magic_ink_colors": Array.isArray(card.magic_ink_colors) ? card.magic_ink_colors : [card.magic_ink_colors]
     }
  })

  const fd_main = fs.openSync(folder + "/raw.json", "w");
  fs.writeSync(fd_main, JSON.stringify(array, null, 2))

  const fd = fs.openSync(folder + "/full.json", "w");
  fs.writeSync(fd, JSON.stringify({
    cards: {
      "all": cards
    }
  }, null, 2))
});
