PROMPT TESTING CONCEPT FOR PROP "Symbol"

PROMPT DERIVATION RULE
select name, long_name 
from con_derivation_rules_ext dr, Properties_ext prop
where prop.condr_idseq = dr.condr_idseq
and prop.long_name = 'Symbol';

PROMPT CONCEPTS:
select prop.long_name "prop.LongName", con.long_name "CON.LongName", con.preferred_name "CON.PrefName"
from concepts_ext con, Properties_ext prop, con_derivation_rules_ext dr, component_concepts_ext comp
where prop.condr_idseq = dr.condr_idseq
and prop.long_name = 'Preferred Name'
and comp.condr_idseq = dr.condr_idseq
and comp.con_idseq = con.con_idseq;


PROMPT TESTING MULTIPLE CONCEPTS FOR PROP "Preferred Name"

PROMPT DERIVATION RULE
select name, long_name 
from con_derivation_rules_ext dr, Properties_ext prop
where prop.condr_idseq = dr.condr_idseq
and prop.long_name = 'Preferred Name';

PROMPT CONCEPTS:
select prop.long_name "prop.LongName", con.long_name "CON.LongName", con.preferred_name "CON.PrefName"
from concepts_ext con, Properties_ext prop, con_derivation_rules_ext dr, component_concepts_ext comp
where prop.condr_idseq = dr.condr_idseq
and prop.long_name = 'Preferred Name'
and comp.condr_idseq = dr.condr_idseq
and comp.con_idseq = con.con_idseq;

