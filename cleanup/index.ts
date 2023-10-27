import * as fs from "fs";

function mapLower(obj: any) {
    return Object.keys(obj).reduce((acc: any, k: string) => {
        acc[k.toLowerCase()] = obj[k];
        return acc;
    }, {});
}

interface Language {
    name: string,
    title: string,
    flavour: string,
    image: string
}

interface Languages {
    [key: string]: Language
}

function filterNonNull(languages: Languages) {
    return Object.keys(languages).reduce((acc: Languages, k: string) => {
        const subObject = languages[k];
        const hasNonNull = !!Object.keys(subObject).find((k: keyof Language) => {
            // unknown, likely name or title
            if (!subObject[k]) return false;

            if (`${subObject[k]}`.toLowerCase() == "unknown") {
                return false;
            }

            // card back likely the image
            if (`${subObject[k]}`.indexOf("cards%2Fback") >= 0) {
                return false;
            }

            return true;
        });

        if (hasNonNull) {
            acc[k.toLowerCase()] = languages[k];
        }

        return acc;
    }, {});
}

function mapCard(card: any) {
    delete card.action;
    delete card.id;
    delete card.created_at;
    delete card.updated_at;
    delete card.blurhash;
    delete card.spoiler;
    if(!card.actions) {
        card.actions = [];
    }

    if(card.language && typeof card.language == "string") {
        card.language = card.language.toLowerCase();
    }

    if (card.card_set_id) {
        card.setCode = (() => {
            switch(card.card_set_id) {
                case 2: return "tfc";
                case 1: return "d23";
                default: return "rotf"
            }
        })();
        delete card.card_set_id;
    }

    if (card.languages) {
        Object.keys(card.languages).forEach(key => {
            const holder = card.languages[key];

            if (holder.card_id) delete holder.card_id;
            if (holder.language) delete holder.language;

            delete holder.actions;
            delete holder.action;
        });
        card.languages = filterNonNull(mapLower(card.languages));
    }

    return card
}

function cleanUp(set: string, content: string) {
    const json = JSON.parse(content);
    const cleaned = json.map((obj: any) => mapCard(obj));

    // now mapping the old card to the actual schema

    const output = JSON.stringify(cleaned, null, 2);
    fs.writeFileSync(`../compiled/${set}.json`, output);
    return cleaned;
}

[ "d23", "tfc" ].map( key => cleanUp(key, fs.readFileSync(`../raw/${key}.json`, "utf-8")));
