select definition, defl_name
from definitions
where ac_idseq in
(select oc_idseq 
from object_classes_ext
where long_name = 'Gene'
and created_by = 'LADINO');