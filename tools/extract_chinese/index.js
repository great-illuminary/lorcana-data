const CryptoJS = require("crypto-js");

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
request(`${baseUrl}/api/Cards/getList`,
  {
    page: 1,
    page_size: 220
  }
)
.then(json => {
  // result is 
  /*
  {
    cards_list: Card[],
    page_info: {
      page: number, (i.e. the current requested page)
      page_size: number, (i.e. the number of cards page page)
      total: number (e.g. 220)
    }
  }
  */
  console.log(json)
});