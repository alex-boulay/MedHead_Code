# MedHead_Code 


## Spring tests 

*Spring est utilisé ici dans l'environement SpringTools Suite 4.*  
*Le port de sortie est 29001 affin de respecter des contraintes internes*

### maven: tests
Tests unitaires et intégrations se font en simultané avec la cible maven (lance aussi jacoco qui a une cible dedans)

### Tests Jacoco
#### Les tests Jacoco s'exécutent dans la cible tests
 /target/jacoco/index.html

### Database H2 
La database H2 est accessible depuis sa connection native (il faut ouvrir la requête d'accès dans le code et permettre le passage des différents headers pour y accéder l'ensemble étant sécurisé

https://localhost:29001/h2-console

## E2E 
### Tests Jmeter
Les tests Jmeter s'exécutent depuis le fichier jmx 
`jmeter -n -t MeadHead.jmx`

### Tests Postman
La collection Postman s'exécute via 
`postman collection run P11_MedHead.postman_collection.json`
ou directement en la chargeant dans postman
*dans le cadre du CI/CD j'utilise newman.cmd (.cmd pour éviter de lancer en version script)*

## FrontEnd 
### Nuxt.js
Le Front est dans le dossier MedHead/
bat `cd /MedHead/`
bat `npm run dev`

## CI/CD
### Jenkins
Le deploiement de la pipeline multibranche est faite via le *jenkinsfile*