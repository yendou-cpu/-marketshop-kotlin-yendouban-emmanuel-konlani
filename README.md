## Étudiant
- KONLANI Yendouban Emmanuel

---

## Technologie utilisée
-Kotlin/Compose 

---

## Description de l'application
STYLE_NOVA est une application mobile de boutique en ligne permettant :
- l’authentification des utilisateurs,
- l’affichage des produits depuis une API,
- la gestion d’un panier,
- le filtrage par catégories,
- et la simulation d’un processus d’achat.

L’application consomme l’API FakeStoreAPI pour récupérer les données produits.

---

# Fonctionnalités implémentées

| Fonctionnalité | État |
|---|---|
| Connexion utilisateur | ✅ |
| Inscription utilisateur | ✅ |
| Affichage catalogue produits | ✅ |
| Détail produit | ✅ |
| Ajout au panier | ✅ |
| Suppression du panier | ✅ |
| Gestion des quantités | ❌ |
| Filtrage par catégories | ✅ |
| Navigation entre écrans | ✅ |
| Persistance session utilisateur | ✅ |
| Historique | ❌ |
| Paiement | ❌ |
| Notifications | ❌ |

---

#  Bibliothèques utilisées

| Package | Version |
|---|---|
| http | ^1.2.0 |
| sqflite | ^2.3.0 |
| sqflite_common_ffi | ^2.3.0 |
| sqflite_common_ffi_web | ^0.4.2 |
| path | ^1.9.0 |
| shared_preferences | ^2.2.0 |
| provider | ^6.1.1 |

---

#  Captures d'écran

##  Écran de connexion
![Login](screenshots/login.png)

##  Catalogue produits
![Home](screenshots/home.png)

##  Panier
![Cart](screenshots/cart.png)

---

#  Difficultés rencontrées

L’une des principales difficultés rencontrées concernait la compatibilité entre Flutter Web et SQLite avec `sqflite_common_ffi_web`. Plusieurs erreurs liées au worker `sqflite_sw.js`, au cache Gradle, au Kotlin daemon et au NDK Android empêchaient l’application de se lancer correctement. Pour résoudre ces problèmes, nous avons réinstallé le NDK Android, nettoyé les caches Gradle (`flutter clean`), activé le mode développeur Windows pour les symlinks Flutter, et configuré correctement les dépendances Web et Android.

---

#  Améliorations possibles

Si nous avions plus de temps, nous aimerions :
- ajouter un système de paiement réel,
- intégrer Firebase Authentication,
- améliorer le design UI/UX,
- ajouter un mode sombre,
- mettre en place des notifications push,
- et synchroniser le panier avec une base de données distante.

---

#  Lien vers la version flutter

https://github.com/VOTRE_USERNAME/marketshop-kotlin

---
