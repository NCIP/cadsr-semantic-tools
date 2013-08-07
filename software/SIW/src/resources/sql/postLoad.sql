/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

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



