# Use Case: Een student kan een exam starten en afmaken.

## Wat ik geprogrammeerd heb
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

## Waar ik aandacht aan heb besteed
- Zorgen dat de ExamService en ExamController tenminste de basis errors op een duidelijke manier kan terug geven met behulp van custom exceptions.
- Zorgen dat zoveel requests in records wordt omgezet omdat het veel minder code scheelt.
- Unit tests van de ExamService die meer verhalend zijn en mocks gebruiken.
- Ik heb ook aandacht besteed om de klasses te verdelen in folders die ik een zo goed mogelijke naam probeer te geven.
- ExamService en ExamController met zo'n min mogelijke onnodige code te programmeren

## Waar ik niet aandacht aan heb besteed
- StudentService en StudentController heb ik wat afgerafelt omdat het simpele crud is.
