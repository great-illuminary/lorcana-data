# lorcana-data
Holding lorcana data only

# Abilities

Abilities are set in the [abilities.json file](./descriptions/abilities.json)
And follow the below structure :

```
name:
  logic: string, the logic that can be used to automate the effects
  values:
    - _optional_
    - song_cost: int?, the cost of the song
    - cost: int?
    - count: int?
    - damages: int?
  title:
    - _optional_
    - en: the english translation
    - fr: the french translation
    - de: the german translation
  text:
    - en: the english translation
    - fr: the french translation
    - de: the german translation
  note:
    - _optional_
    - en: the english translation of the annotation
    - fr: the french translation of the annotation
    - de: the german translation of the annotation
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

Each card, description, annotations can use placeholders, those are described in the [placeholders.json](./descriptions/placeholders.json).

It consists of a map of placeholder and their representations. Currently only for utf


```
{
  "name": {
    "utf": "",
  }
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