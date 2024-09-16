# README Matthew

## Use case - Een docent kan een ingeleverde toets van een student beoordelen. 
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
