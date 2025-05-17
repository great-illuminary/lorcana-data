# lorcana-data

![CI](https://github.com/codlab/lorcana-data/actions/workflows/build.yml/badge.svg)
![License](https://img.shields.io/github/license/codlab/lorcana-data)
![Last Release](https://img.shields.io/github/v/release/codlab/lorcana-data)

[
![Discord](https://img.shields.io/badge/Discord-Lorcana_Manager-blue)
](https://discord.gg/cd4hRF2PXm)

![badge](https://img.shields.io/badge/json-kotlin-green)
![badge](https://img.shields.io/badge/android-blue)
![badge](https://img.shields.io/badge/ios-white)
![badge](https://img.shields.io/badge/js-yellow)
![badge](https://img.shields.io/badge/jvm-red)
![badge](https://img.shields.io/badge/linux-blue)
![badge](https://img.shields.io/badge/windows-blueviolet)
![badge](https://img.shields.io/badge/mac-orange)

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

## Usage

```
  val lorcana = Lorcana().loadFromResources()
  # load eiher from the resources or github, in this sample it'll be resources
```

The API will give you either the flatten list of cards or the condensed version.
The difference between boths is that when using the condensed version, you will
be able to get the various other versions of the card :
- enchanted vs non enchanted
- regular set vs promos (or any other version in the future)

**Get the list of flatten cards**

```
val tfc = lorcana.set(SetDescription.TFC).cards

println("This will give you ${tfc.size} = 204+12 cards")
```

**Get the list of virtual cards**

```
val tfc = lorcana.set(SetDescription.TFC).virtualCards

println("This will give you ${tfc.size} = 204 cards")
```

# Cards

The following is the definition for the Card, which are variant specific
The cards are defined in the [data folder](./data/)

The model is as the following :

```
interface Card {
  cost: int
  inkwell: boolean
  colors: InkColor[]
  type: CardType
  classification: ClassificationHolder[]
  attack: int?
  defence: int?
  move_cost: int?
  lore: int?
  languages: CardTranslations
  abilities: Ability[]
  set: SetDescription
  number: int
  illustrator: string
  dreamborn: string
  ravensburger: string
  rarity: VariantRarity
  franchise: Franchise
  third_party: CardThirdParty
}
```

# VirtualCards

The VirtualCard is an object which contains all the card information + every specific variant info
in a sub-array which can then be used to make bijection on the cards themselves
The model is as the following :

```typescript
interface VirtualCard {
  cost: int
  inkwell: boolean
  colors: InkColor[]
  type: CardType
  classification: ClassificationHolder[]
  attack: int?
  defence: int?
  move_cost: int?
  lore: int?
  languages: CardTranslations
  abilities: Ability[]
  variants:
    - set: SetDescription
      number: int
      illustrator: string
      dreamborn: string
      ravensburger: string
      rarity: VariantRarity
  franchise: Franchise
  third_party: CardThirdParty
}
```

# Ability

```typescript
interface Ability {
  type: AbilityType,
  title: TranslationHolder?,
  text: TranslationHolder?,
  ability: string?
}
```

## TranslationHolder & CardTranslations

Those contains information which are country specific :

```typescript
interface TranslationHolder {
  [countryCode: string]: string? // "en" should always be there
}
```

```typescript
interface CardTranslations {
  [code: string]: CardTranslation // "en" should always be there
}

interface CardTranslation {
  name: string,
  title: string?,
  flavor: string?
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
