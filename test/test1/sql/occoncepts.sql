PROMPT TESTING MULTIPLE CONCEPTS FOR OC

PROMPT OC
select long_name, preferred_name, preferred_definition, definition_source
from Object_classes_ext
where long_name = 'Clinical Trial Protocol';

PROMPT DERIVATION RULE
select name, long_name 
from con_derivation_rules_ext dr, Object_classes_ext oc
where oc.condr_idseq = dr.condr_idseq
and oc.long_name = 'Clinical Trial Protocol';

PROMPT CONCEPTS:
select oc.long_name "oc.LongName", con.long_name "CON.LongName", con.preferred_name "CON.PrefName"
from concepts_ext con, object_Classes_ext oc, con_derivation_rules_ext dr, component_concepts_ext comp
where oc.condr_idseq = dr.condr_idseq
and oc.long_name = 'Clinical Trial Protocol'
and comp.condr_idseq = dr.condr_idseq
and comp.con_idseq = con.con_idseq;

