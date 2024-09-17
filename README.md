# Swagger UI
We hebben voor onze POSTMAN collection ook een swagger-UI: 
- local: http://localhost:8080/swagger-ui/index.html
- public: https://testvision2-team4.victoriousbeach-ec764c0c.westeurope.azurecontainerapps.io/swagger-ui/index.html

# HULP Start Repository

Bij deze de start-repository voor de HULP opdracht.

In deze repository start een lege Spring-boot applicatie. Deze staat net 1 directory dieper dan je gewend 
bent. Dat kun je voor nu negeren, maar zorgt er voor dat de overstap in week 3 net iets prettiger loopt.


Na opstarten kun je alvast kijken op

* http://localhost:8080/actuator voor allerhande debug info (heel soms handig)
* http://localhost:8080/h2-console voor je database.
  
  Het is niet Postgres, maar ik denk dat je de weg wel vindt. Uiteraard mag je PostGres introduceren, 
  maar zet dan ook een bijpassende Dockerfile op.

  (als het goed is kloppen de defaults, anders moet je de url/username even uit de application.properties vissen)


