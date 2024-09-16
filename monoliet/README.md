# Module uitleg

## teacher test validation (Pasquinel Bhikhoe)
Teacher test validation is een klasse wat een bestaande toets uit de wachtlijst pakt.
Aan deze toets zijn twee leraren 
Deze toets wordt dan volledig getoond en dan kan de leraar,de validator, de toets accepteren of afwijzen.
Bij het accepteren wordt de toets op de goede lijst gezet en kan de test gemaakt worden door de student.
Bij het afwijzen moet de validator een reden toevoegen en wordt de toets op een afwijslijst gezet.
De maker moet nu de toets veranderen en opnieuw inleveren, waarna de validator het weer kan beoordelen, etc.

Er is voor dit systeem gekozen omdat dit systeem logisch en effectief is, een vergelijkbaar systeem wordt hier ook op github gebruikt met pull requests.
Er is  niet te veel aandacht besteed aan login omdat dit redelijk eenvoudig is om hierna nog toe te voegen.


## Toetsen kunnen aangemaakt worden(Rik Prins)
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
