# Étape 1 – Diagnostic du code historique

## Rôle et dépendances de chaque classe

| Classe | Ce qu'elle fait | Dépend de |
|---|---|---|
| `ServiceClient` | valide les données d'un client, l'enregistre, envoie l'email de bienvenue | `Client`, `Database` (singleton), `EmailService` (statique) |
| `ServiceExpedition` | valide une demande, choisit le véhicule, calcule prix et délai, gère le cycle de vie, notifie | `Database`, `CalculateurPrix`, `AffectationVehicule`, `EmailService`, `SmsService`, `Utils`, `Vehicule` |
| `CalculateurPrix` | tarif de base par mode, suppléments, urgence, remise client, assurance, arrondi | `Colis` |
| `AffectationVehicule` | parcourt la flotte et choisit le premier véhicule compatible | `Flotte` (statique), `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` |
| `Flotte` | liste statique des véhicules | toutes les classes de véhicules |
| `IVehicule` / `Vehicule` | interface « fourre-tout » + gestion du chargement | `Colis` |
| `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` | véhicules concrets | `Vehicule` |
| `Client`, `Colis` | modèle (codes `String` / `int`) | — |
| `Expedition` | modèle **et** génération du texte de la facture | `Client`, `Colis`, `Vehicule`, `Utils` |
| `Database` | singleton + maps statiques publiques + faux SQL | `Client`, `Expedition` |
| `EmailService` | méthode statique + journal statique public | — |
| `SmsService` | envoi SMS + journal statique public + clé API en dur | — |
| `Utils` | dates, arrondi, email, table des distances (fourre-tout) | — |

## Défauts → emplacement → principe violé

| # | Défaut | Emplacement | Principe |
|---|---|---|---|
| 1 | `if/else` sur le mode pour le tarif de base | `CalculateurPrix.calculerPrixHT` | **O** (ouvert/fermé) |
| 2 | `if/else` sur le type de client pour la remise | `CalculateurPrix.calculerPrixHT` | **O** |
| 3 | suppléments / urgence / assurance codés en `if` successifs | `CalculateurPrix.calculerPrixHT` | **O** |
| 4 | `switch` sur le mode pour le délai | `ServiceExpedition.calculerDelai` | **O** + **S** |
| 5 | cascade d'`instanceof` + `if` sur le mode | `AffectationVehicule.trouverVehicule` | **O** + **L** |
| 6 | `if` sur le mode pour `rouler` / `naviguer` / `voler` | `ServiceExpedition.demarrerExpedition` | **O** |
| 7 | règles d'acceptation (`AIR`, `MER`, réfrigéré…) en dur | `ServiceExpedition.creerExpedition` | **O** + **S** |
| 8 | `ServiceExpedition` valide, affecte, calcule, persiste **et** notifie | `ServiceExpedition` | **S** |
| 9 | `Expedition` met en forme sa propre facture | `Expedition.genererFacture` | **S** |
| 10 | `Utils` mélange dates, prix, email et distances | `Utils` | **S** |
| 11 | `IVehicule` impose `rouler`, `naviguer`, `voler`, `activerRefrigeration` à tous | `IVehicule` + véhicules | **I** (ségrégation) |
| 12 | `UnsupportedOperationException` dans `Camion`, `Train`, `Bateau`, `Avion` | véhicules | **I** + **L** |
| 13 | `CamionFrigorifique.charger` refuse les colis non réfrigérés | `CamionFrigorifique` | **L** (Liskov) |
| 14 | `Train.faireLePlein` lève une exception, masquée par un `catch` vide | `Train`, `ServiceExpedition.demarrerExpedition` | **L** |
| 15 | `Database.getInstance()` singleton + maps `public static` | `Database`, `ServiceClient`, `ServiceExpedition` | **D** |
| 16 | `EmailService.envoyer` statique, `new SmsService()` en dur | `ServiceClient`, `ServiceExpedition.notifier` | **D** |
| 17 | `new CalculateurPrix()`, `new AffectationVehicule()` dans le service | `ServiceExpedition` | **D** |
| 18 | `Flotte.vehicules` statique et modifiable | `Flotte`, `AffectationVehicule` | **D** |
| 19 | `ServiceExpedition.compteur` statique public | `ServiceExpedition` | **D** (état global) |
| 20 | nombres magiques (`1.2`, `0.10`, `167`, `5000`, `70`, `1440`, `720`, `3000`…) | `CalculateurPrix`, `ServiceExpedition` | lisibilité |
| 21 | mode / statut / type client en `String`, type colis en `int` | partout | lisibilité / typage |
| 22 | `Math.round(x * 100.0) / 100.0` répété (alors que `Utils.arrondir` existe) | `CalculateurPrix`, `ServiceExpedition` | DRY |
| 23 | clé API en dur, SQL concaténé | `SmsService`, `Database` | sécurité |

## Ce qu'il faut modifier aujourd'hui pour…

### …ajouter un mode `FLUVIAL`
1. `CalculateurPrix.calculerPrixHT` : nouveau `else if` pour le tarif + minimum
2. `ServiceExpedition.creerExpedition` : règles réfrigéré interdit, pas d'urgence
3. `ServiceExpedition.calculerDelai` : nouveau `case`
4. `ServiceExpedition.demarrerExpedition` : comment fait partir le véhicule
5. `AffectationVehicule.trouverVehicule` : nouveau `else if` + `instanceof Peniche`
6. `IVehicule` + **toutes** les classes de véhicules si on ajoute une méthode (`naviguerFleuve`…)
7. nouvelle classe `Peniche` (qui doit implémenter `rouler`, `voler`… en lançant des exceptions)
8. `Flotte.initialiser` : ajouter la péniche
9. `Utils.getDistance` : nouveau trajet
10. `Expedition` / `Database` : rien, mais le mode reste une `String` non contrôlée

→ **8 fichiers existants modifiés**, 1 créé.

### …ajouter un supplément « hors gabarit »
1. `CalculateurPrix.calculerPrixHT` : détecter le colis > 200 cm **et** ajouter un `if` de supplément
   (au bon endroit dans l'ordre des calculs)
2. éventuellement `Colis` : méthode `getPlusGrandeDimension()`

→ **1 à 2 fichiers existants modifiés**, mais au cœur de la classe la plus risquée.
