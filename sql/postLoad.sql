
update concepts_ext 
set EVS_SOURCE = 'NCI_CONCEPT_CODE'
where created_by like 'UMLLOADER%';

update concepts_ext 
set ORIGIN = 'NCI Thesaurus'
where created_by like 'UMLLOADER%';

update OBJECT_CLASSES_ext
set ORIGIN = 'NCI Thesaurus'
where created_by like 'UMLLOADER%';

update PROPERTIES_ext
set ORIGIN = 'NCI Thesaurus'
where created_by like 'UMLLOADER%';



