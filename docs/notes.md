# Notes sur les choix

## Étape 5 – Qui choisit la stratégie ?

Deux options :

- **l'enum porte la stratégie** (`ModeTransport.ROUTE` connaît son tarif) : simple, mais l'enum dépend alors
  des classes de tarif, et on ne peut pas changer de tarif sans toucher à l'enum (ni en injecter un faux dans un test) ;
- **une `Map<ModeTransport, TarifTransport>` injectée** : l'enum reste un simple identifiant, et le choix des
  stratégies se fait à un seul endroit, la classe `TransFret` qui assemble l'application.

Choix retenu : la `Map` injectée, pour les tarifs, les remises et les délais. Les règles d'acceptation de
chaque mode (`ContraintesMode`) suivent le même principe.

## Étape 8 – Preuve par l'extension

### Mode `FLUVIAL`

Nouvelles classes : `TarifFluvial`, `DelaiFluvial`, `Peniche`.

Fichiers existants modifiés :

| Fichier | Modification |
|---|---|
| `ModeTransport` | ajout de la constante `FLUVIAL` |
| `TransFret` | branchement du tarif, du délai et des contraintes (réfrigéré interdit, pas d'urgence) |
| `Flotte` | ajout de la péniche `PEN-0001` (2 500 t) |
| `TableDistances` | trajet Paris → Le Havre (365 km) |

Soit 4 fichiers, uniquement des déclarations ou de l'assemblage, contre 8 fichiers et de la logique
modifiée dans le code historique (voir `diagnostic.md`).

### Supplément « hors gabarit »

Nouvelle classe : `SupplementHorsGabarit`. Fichier existant modifié : `AssembleurPrix` (une ligne pour le placer
dans la chaîne). Aucun autre décorateur n'a été touché.

### Les 68 tests passent-ils toujours ?

Avec le mode `FLUVIAL` seul, oui. Avec le supplément hors gabarit appliqué partout, 6 tests échouent : leurs colis
mesurent 240 cm et les prix attendus suivent les anciennes règles. Ce n'est pas une régression, c'est la nouvelle
règle qui change les prix.

Comme `transport-contrat` ne doit pas être modifié, le supplément est activable : `new TransFret()` (utilisé par
`Main`) l'applique, et l'adaptateur des tests de contrat utilise `new TransFret(false)`, c'est-à-dire les règles
d'origine. Les 68 tests restent verts et le supplément a ses propres tests unitaires.

### Tests unitaires ajoutés

`TarifFluvialTest`, `DelaiFluvialTest`, `SupplementHorsGabaritTest`, `FluvialTest` (expédition fluviale de bout en bout).
