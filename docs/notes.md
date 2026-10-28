# Notes sur les choix

## Étape 5 – Qui choisit la stratégie ?

Deux options :

- **l'enum porte la stratégie** (`ModeTransport.ROUTE` connaît son tarif) : simple, mais l'enum dépend alors
  des classes de tarif, et on ne peut pas changer de tarif sans toucher à l'enum (ni en injecter un faux dans un test) ;
- **une `Map<ModeTransport, TarifTransport>` injectée** : l'enum reste un simple identifiant, et le choix des
  stratégies se fait à un seul endroit, la classe `TransFret` qui assemble l'application.

Choix retenu : la `Map` injectée, pour les tarifs, les remises et les délais. Les règles d'acceptation de
chaque mode (`ContraintesMode`) suivent le même principe.
