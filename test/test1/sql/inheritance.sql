PROMPT DEC:
select long_name, preferred_name, preferred_definition
from data_element_concepts
where oc_idseq in (select oc_idseq from object_classes_ext where long_name = 'Sequence')
and created_by = 'LADINO';

prompt DEC Alt Name
select name, detl_name
from designations
where ac_idseq = 
(select dec_idseq from data_element_concepts
where long_name = 'Sequence ID'
and created_by = 'LADINO'
)
;


PROMPT DE:
select long_name, preferred_name, preferred_definition
from data_elements
where dec_idseq in (
select dec_idseq
from data_element_concepts
where oc_idseq in (select oc_idseq from object_classes_ext where long_name = 'Sequence'))
and created_by = 'LADINO';

PROMPT DE ALT NAME
select name, detl_name
from designations
where ac_idseq = 
(select de_idseq from data_elements
where long_name = 'Sequence ID java.lang.String'
and created_by = 'LADINO'
)
;


PROMPT OC_RECS:
select long_name, preferred_name, preferred_definition
from oc_recs_ext
where t_oc_idseq in (
select oc_idseq from object_classes_ext
where long_name = 'DomainObject')
and created_by = 'LADINO';

