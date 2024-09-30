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


# README RPC:

## RPC Rik:
Ik heb de module course gemaakt. Aangezien de applicatie niet helemaal gesplitst/samengevoegd kon worden staat deze in de branch: [splits-exam-in-module](https://github.com/huict/hulp-24a-v3b-testvision-team4-v3b/tree/splits-exam-in-module).
Hier staan de Course, Exam, Questions etc., samen met de services en repositories. De DTO's staan in de Commons module.
Op deze branch werkt de monoliet zelf niet meer omdat de dependencies veranderd zijn.

## RPC Matthew:

### DDD 
Voordat ik aan mijn module ben begonnen heb ik mijn stuk(Submssion, Grading. Gradingcritera, Statistics) uitgebreid op o.a. de feedback van de monoliet
- Onnodige setters eruit
- DTO's en objecten in services(Course, Teacher. Test) niet = null gemaakt
- Geen setters in GradingRequest en SubmissionResponse
- In de calculation van een grade wordt er nu gebruikt gemaakt complexere logica met daarbij de GradingCriteria van beide soort vragen(Open en Multiple vragen)

### Implementatie/Functionaliteit
Ik heb de SubmissionGrading module gemaakt, die de code bevat van Submission(aggerate) en Grading  -> 
- de pom van deze module heeft een referentie naar de parent pom
- de parent pom heeft de SubmissionGrading als module
- Er zijn geen dependecies naar andere modules
- De dto's betrekkend op de SubmissionGrading module zijn in de aparte Commons module
Aangezien er nog wat domeindingen moesten gebeuren, andere modules nog niet afwaren/gemerged was ik niet staat optijd succesvol een poging te doen tot communicatie met andere modules.


## RPC Ruben:
Ik heb alles in de examination module gemaakt en gezet. De code staat in de examination branch.

## RPC Pasquinel:
...

# README Monoliet:

# Module uitleg

## Teacher exam validation. (Pasquinel Bhikhoe)

valt onder: Teacher,TeacherMail en validation en gedeeltes van exam,course.
Teacher exam validation is een functie wat een bestaande toets uit de wachtlijst pakt.
Aan deze toets zijn twee leraren 
Deze toets wordt dan volledig getoond en dan kan de leraar,de validator, de toets accepteren of afwijzen.
Bij het accepteren wordt de toets op de goede lijst gezet en kan de exam gemaakt worden door de student.
Bij het afwijzen moet de validator een reden toevoegen en wordt de toets op een afwijslijst gezet.
De maker moet nu de toets veranderen en opnieuw inleveren, waarna de validator het weer kan beoordelen, etc.

Er is voor dit systeem gekozen omdat dit systeem logisch en effectief is, een vergelijkbaar systeem wordt hier ook op github gebruikt met pull requests.
Er is  niet te veel aandacht besteed aan login omdat dit redelijk eenvoudig is om hierna nog toe te voegen.


## Toetsen kunnen aangemaakt worden. (Rik Prins)
Met deze use case kan een docent een toets aanmaken.
Hiervoor moesten een paar dingen gebeuren:

1. Er kan een cursus aangemaakt worden
1. Er kunnen vragen gemaakt worden
1. Er kan een toets gemaakt worden
1. Er kunnen vragen aan de toets worden gekoppeld
1. De toets kan aan een cursus worden gekoppeld

De domeinklassen die hier nodig waren:
Course, Question en Test.
Hiervoor zijn ook services, repositories en controllers voor gemaakt(voor CRUD).

Todo's: 
Er kunnen verschillende type vragen gemaakt worden(multiple choice, open vragen)

## Een student kan een exam starten en afmaken. (Ruben Strydom)

### Wat ik geprogrammeerd heb
- User klasse
- Student klasse
- Exam klasse
- State klasse
- StudentService klasse
- StudentController klasse
- ExamService klasse
- ExamController klasse
- Alle examen gerelateerde request en response dto's
- Unit tests van de ExamService met behulp van mocks
- Ook heb ik kleine aanpassingen gedaan aan Question en Test Klasse om ervoor te zorgen dat een vraag in een toets beantwoord kan worden.

### Waar ik aandacht aan heb besteed
- Zorgen dat de ExamService en ExamController tenminste de basis errors op een duidelijke manier kan terug geven met behulp van custom exceptions.
- Zorgen dat zoveel requests in records wordt omgezet omdat het veel minder code scheelt.
- Unit tests van de ExamService die meer verhalend zijn en mocks gebruiken.
- Ik heb ook aandacht besteed om de klasses te verdelen in folders die ik een zo goed mogelijke naam probeer te geven.
- ExamService en ExamController met zo'n min mogelijke onnodige code te programmeren

### Waar ik niet aandacht aan heb besteed
- StudentService en StudentController heb ik wat afgerafelt omdat het simpele crud is.

## Een docent kan een ingeleverde toets van een student beoordelen. (Matthew Bijlhout)
### Use case beschrijving
- een docent kan voor een inlevering per vraag punten toekennen en commentaar geven.
- een docent kan voor een inleveing een eindcijfer toekennen en commentaar geven, waardoor automatich de toetsstatistieken worden bijgewerkt.

## Waar is DDD toegepast?

### Entity vs Value Objects 

#### Entities
- `Submission` met `SubmissionService` en `SubmissionRepository` -> als een toets wordt ingeleverd, wordt er een `Submission` aangemaakt. Een docent kan elke vraag apart beoordelen en van commentaar voorzien.
- `Statistics` -> Bij elke beoordeling van een exam worden de toetsstatistieken bijgewerkt.

##### Value Objects
- `GradingCriteria`(hoort bij een `Test`en heeft invloed op de `Grading`)- **DISCLIAMER**: Het value object `GradingCriteria` kan momenteel nog niet gebruikt worden in de applicatie en heeft dus de **MINSTE** aandacht gekregen. Dit komt doordat de use cases voor Test en Exam nog geen rekening houden met het onderscheid tussen open en gesloten vragen. 
- `GradingCriteria` is Immutable, met Equals/Hashcode override

### Aggegrates
- `Statistics` is een aggregate van de aggregate root `Test`. Er is ook geen sprake van een `StatisticsRepository` omdat de `Statistics` alleen via de `Test` kan worden aangepast.
- `Grading` is een aggregate van de aggregate root `Submission`. Er is ook geen sprake van een `GradingRepository` omdat de `Grading` alleen via de `Submission` kan worden aangepast.
