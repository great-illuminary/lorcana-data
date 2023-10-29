# lorcana-data

![CI](https://github.com/codlab/lorcana-data/actions/workflows/build.yml/badge.svg)
![License](https://img.shields.io/github/license/codlab/lorcana-data)
![Last Release](https://img.shields.io/github/v/release/codlab/lorcana-data)

[
![Discord](https://img.shields.io/badge/Discord-Lorcana_Manager-blue)
](https://discord.gg/cd4hRF2PXm)

Holding lorcana data only

# Integration

**Raw JSON**

You can access to the raw jsons inside [data](./data).

**Gradle**

A Kotlin Multiplatform library is available using

```gradle
implementation("eu.codlab:lorcana-data:$version")
```

This will work on the following platforms :
- Mobile (Android/iOS)
- Web (js)
- Native (MacOS/Linux/Windows)
- JVM (MacOS/Linux/Windows)

# Cards

The cards are defined in the [data folder](./data/)

The model is as the following :

```
cost: int
inkwell: int
attack: 3
defence: 5,
color: int
type: string
illustrator: string
number: int
rarity: string
image: string
dummy: boolean?
languages:
  key:
    name: string
    title: string?
    flavour: string?
    image: string?
edition: []
  name: string
  code: string
actions: string[], it can be mapped to the following abilities list
set_code: string
```

# Abilities

Abilities are set in the [abilities.json file](./data/abilities.json)
And follow the below structure :

```
name:
  logic: string, the logic that can be used to automate the effects
  values:
    _optional_
    song_cost: int?, the cost of the song
    cost: int?
    count: int?
    damages: int?
  title:
    _optional_
    en: the english translation
    fr: the french translation
    de: the german translation
  text:
    en: the english translation
    fr: the french translation
    de: the german translation
```

or as json

```
{
  "name": {
    "logic": "",
    "values": {
      "new_cost": 0
    },
    "text": {
      "en": "",
      "fr": "",
      "de": "",
    }
  }
}
```

# Placeholders

Each card, description, annotations can use placeholders, those are described in the [placeholders.json](./data/placeholders.json).

It consists of a map of placeholder and their representations. Currently only for utf


```
{
  "name": "value"
}
```

## Usage

In the applications using the data, a naive implementation could be something like :

```
  val text = placeholderMap.keys.reduce(ability.text[language]) { acc, key ->
    acc.replace(placeholderMap[key])
  }

  Text (
    text = text
  )
```

# Improvements !

Currently, the abilities is made in a really dumb way but with reference to overriden ability if required
There were 2 obvious solutions, I chose the later

- make singer in abilities while removing singer_4
- making each cards set the values to use

or

- make singer in abilities to be by default with 5
- add ability for ... abilities to be either a regular ability or just have new "values" holder + "reference" name for the ability to use

```
{
  "singer": {
    "logic": "",
    "values": {
      "cost": 0
    },
    "text": {
      "en": "",
      "fr": "",
      "de": "",
    }
  }
} | {
  "singer_4": {
    "reference": "singer"
    "values": {
      "cost": 0
    }
  }
}

```