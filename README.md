# MedHead_Code 
Test 11/n

// TestIT 
// Spécifier les test de profil patern
// Spring Postman
// Extract

Faire des Profils Test et TestIT
Faire un pipeline Jenkins (Aller jusqu'au déploiement)
Postman 

// Token Login PassWorld - GWT
// PostGis requête coordonnées
--> Afficher Hopital
-> Appel 
-> Afficher le détail
// Table des évènements
// Service qui traite et gère la 
// Messaging Queue Service -3
// RabbitMQ
// Formulaire -> clique table d'évent

// Lundi 05/06/2023 ROTI 5/5
- Set-up de nuxt.js 
- Fetch de l'ensemble des éléments APIs
- Affichage de groupspec spec et input field de l'addresse
- J'ai vu pour autowire -> Bréche de sécu existe en dehors de spring
- Settup d'un About pour expliquer la raison du site

// Mardi 06/06/2023 ROTI 2/5
- Meilleur Affichage des différents éléments frontend 
- Réédition du Controlleur des hopitaux meilleure gestion des exceptions
- Réédition du bedFindingService
- Mettre un Logger au lieu d'une StackTrace -> Juste StackTrace était inaproprié meilleure gestion des erreurs API a faire

// Mercredi 07/06/2023 ROTI 3/5 -> J'ai beaucoup travaillé en terme de temps mais pas bcp avancé à cause de petites erreurs/trucs qui ne marchent pas
- Meilleure Gestion des Codes Retours API 
- Débugage d'éléments frontend et gestion de plusieurs Bugs liés à l'interface
- Implémentationd de l'https pour MedHead (plusieurs heures a débeuger le fait qu'il faut une variable d'environement spéciale quand les SSL sont self hosted)
- Tests des Services 
- Requêtes Postman améliorées
- Demande de suppersion des Données personnelles page

// Jeudi 08/06/2023 3/5
- Code Coverage Jacoco
- Nettoyage du Code pour match Jacoco
- Https pour l'API spring
- Réédition du front et de postman pour https
- Tests controlleur Postman (début)
- Test Jmeter (début)

// Vendredi 09/06/2023
- Gestion des Tokens pour le site et les différentes connections  (avant postman aussi)
- Voice Call mentorat 14H30 

// Samedi 10/06/2023
- Setup des Entities pour l'authentification
- Correction d'un problème d'affichage pour les headers de la h2-console

// Dimanche 11/06/2023
- J'ai continué sur Spring Security avec un autre tutoriel plus simple 
- Petits problèmes pour tester mes requêtes corrigés (JSON au lieu de TEXT en selection auto)
- Déploiement de la Base de Données Roles et User 
- Ajout de deux Base users, un Admin et un normal 
- Setting up h2-console Dérrière les filtres Spring Sec

// Lundi 12/06/2022
- Mise en place de Spring Security avec des clés Paires
- Correction de problèmes liés au JWT sur Postman
- Edition des Tests sur Postman -> Login et Register
- Mise en place de variables d'environement pour les Tests sur Postman

// Mardi 13/06/2022
- Tests PostMan :
	-> Bad Auth pour tester les éléments de connectection 
	-> Folder NoAuth pour tester les get sans authorisation

// Mercredi 14/06/2022
- Posman automatisation des tests, 17 tests au total 
- Installation de Mockito
- Edition de Nouveaux tests pour spring security et la configuration



// Pile des Choses à faire
- Nuxt page de connection et page d'enregistrement
- Sécuriser Nuxt
- Jacoco éditer les nouveaux Services
- JMeter
- Intégration de l'ensemble à Jenkins 
- Tests Coté client ??? -> API avec postman -
- Séparation des Tests en Intégration et Unitaires avec les Cibles Maven.

-->Mail récap Lundi ou Mardi.
- Document qui détaille les différents Choix
- Powerpoint du projet

// Nuxt component tag Menu
--Inverser le footer avec le Menu 

@PostMapping( value = "/signin", produces = "application/json" )
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		log.info( "Authentication" );
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken( loginRequest.getUsername(), loginRequest.getPassword() ) );
		SecurityContextHolder.getContext().setAuthentication( authentication );
		return ResponseEntity.ok( jwtUtils.generateJwtResponse(authenticati
cyril l.
14:57
public JwtResponse generateJwtResponse(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userPrincipal.getAuthorities().stream()
				.map( item -> item.getAuthority() )
				.collect( Collectors.toList() );
		var token = generateJwtToken( userPrincipal, roles );
		return new JwtResponse( token,
				userPrincipal.getId(),
				userPrincipal.getFirstname(),
				userPrincipal.getEmail(),
				roles );

	}

--Spring boot security course
https://www.youtube.com/playlist?list=PL82C6-O4XrHe3sDCodw31GjXbwRdCyyuY
https://www.youtube.com/watch?v=KYNR5js2cXE