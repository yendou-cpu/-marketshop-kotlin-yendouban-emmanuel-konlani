# — Version Kotlin / Jetpack Compose

## Étudiant
- KONLANI Yendouban Emmanuel

*  **Lien vers la version Flutter :** [https://github.com/yendou-cpu/-marketshop-flutter-Emmanuel-yendouban-konlani](#)

---

##  Description
**STYLE-NOVA** est une application mobile de mini e-commerce développée en **Kotlin** avec **Jetpack Compose**.

Elle permet à l'utilisateur de parcourir un catalogue de produits, d'ajouter des articles à un panier, de passer commande et de consulter son historique. Les données produits proviennent de l'API publique **FakeStoreAPI**, tandis que le panier et les commandes sont persistés localement avec **Room**.

---

## Fonctionnalités implémentées

### Authentification
* [x] Écran de connexion (login)
* [x] Écran d'inscription (signup)
* [x] Navigation vers l'app après authentification
* [x] Déconnexion depuis le profil

### Écran Catalogue
* [x] Affichage des produits en grille 2 colonnes
* [x] Image, titre, prix, catégorie sur chaque carte
* [x] Filtre par catégorie (`FilterChip` horizontal)
* [x] Indicateur de chargement (`CircularProgressIndicator`)
* [x] Clic sur un produit $\rightarrow$ Écran détail

### Écran Détail produit

### Écran Panier
* [x] Liste des produits ajoutés (depuis `Room`)
* [x] Image, titre, prix unitaire, quantité, sous-total
* [x] Modifier la quantité de chaque ligne
* [x] Supprimer une ligne
* [x] Total général
* [x] Bouton "Passer commande"
* [x] Message "Votre panier est vide"

### Écran Commande
* [ ] Formulaire : nom, téléphone, adresse, ville
* [ ] Validation des champs
* [ ] Récapitulatif du panier en lecture seule
* [ ] Sauvegarde en base et vidage du panier
* [ ] Redirection vers l'historique après commande

### Écran Historique
* [ ] Liste des commandes passées
* [ ] Détail d'une commande au clic

### Écran Profil
* [x] Affichage des infos depuis l'API (`GET /users/1`)
* [x] Modification locale (`SharedPreferences`)
* [x] Switch mode sombre
* [x] Bouton "Vider mes données" avec confirmation
* [x] Déconnexion

### Navigation
* [x] Bottom Navigation Bar (Catalogue, Panier, Historique, Profil)
* [x] Navigation contextuelle (catalogue $\rightarrow$ détail)

---

##  Bibliothèques utilisées

| Bibliothèque | Version | Usage |
| :--- | :--- | :--- |
| **`Jetpack Compose BOM`** | `2026.02.01` | UI déclarative |
| **`Retrofit`** | `2.9.0` | Appels API REST |
| **`Gson Converter`** | `2.9.0` | Désérialisation JSON |
| **`Room`** | `2.6.1` | Persistance locale (panier) |
| **`Coil Compose`** | `2.x` | Chargement d'images asynchrone |
| **`Navigation Compose`** | `2.8.8` | Navigation entre écrans |
| **`Coroutines`** | `1.7.x` | Gestion des tâches asynchrones |
| **`ViewModel`** | `2.10.0` | Gestion d'état et cycle de vie |
| **`KSP`** | `2.0.21-1.0.27` | Annotation processing pour Room |

---

## Captures d'écran


| Catalogue | Détail produit | Panier |
| :---: | :---: | :---: |
| ![Catalogue](https://github.com/yendou-cpu/-marketshop-kotlin-yendouban-emmanuel-konlani/blob/main/app/src/main/java/com/example/projetgestion1/screenshots/catalogue.jpeg) | ![Détail produit](https://github.com/yendou-cpu/-marketshop-kotlin-yendouban-emmanuel-konlani/blob/main/app/src/main/java/com/example/projetgestion1/screenshots/detail.jpeg) | ![Panier](https://github.com/yendou-cpu/-marketshop-kotlin-yendouban-emmanuel-konlani/blob/main/app/src/main/java/com/example/projetgestion1/screenshots/panier.jpeg) |

---

##  Difficultés rencontrées

1. **Configuration de Room avec KSP (AGP 9.x) :** La principale difficulté a été la configuration du moteur d'annotations de Room avec KSP sous les versions récentes d'Android Gradle Plugin. Le plugin `kotlin.android` entrait en conflit avec `kotlin.compose`, provoquant l'erreur `Cannot add extension with name 'kotlin'`. La solution a consisté à retirer explicitement `kotlin.android` pour ne conserver que `kotlin.compose`, qui intègre désormais nativement les configurations nécessaires.
2. **Gestion du statut du Panier (CartViewModel partagé) :** Une autre complexité a concerné le partage et la synchronisation de l'état du panier à travers plusieurs écrans distincts. En instanciant un unique `CartViewModel` au niveau de l'arbre de navigation principal (`AppNavigation`) et en le passant en paramètre aux fonctions composables de `HomeScreen`, `DetailScreen` et `CartScreen`, le panier est resté parfaitement synchronisé sans aucune incohérence de données.

---

##  Améliorations possibles

Avec plus de temps, nous aurions apporté les optimisations suivantes :
* **Nouvelles Fonctionnalités :** Implémentation complète de l'écran d'historique des commandes avec une table dédiée dans la base de données Room, et intégration d'un champ de recherche textuelle dynamique dans le catalogue.
* **Sécurité :** Mise en place d'une gestion d'authentification robuste avec stockage sécurisé d'un token JWT (via *EncryptedSharedPreferences* ou *DataStore*).
* **Architecture & Thème :** Injection d'un mode sombre fonctionnel de manière globale en faisant transiter l'état du thème via un `CompositionLocalProvider`.
* **Robustesse & UX :** Amélioration de la résilience du réseau à l'aide d'un mécanisme de tentative automatique (*retry*) en cas de coupure, intégration d'un état *offline* complet, et ajout d'animations personnalisées lors des transitions entre les routes de navigation.
