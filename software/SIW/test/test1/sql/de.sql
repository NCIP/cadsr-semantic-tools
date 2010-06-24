PROMPT Checking for existence of DE Target Preferred Name String

Prompt dec public id:
select dec_id
from Data_element_concepts dec
where dec.long_name = 'Target Preferred Name';
Prompt vd public id:
select vd_id
from value_domains vd
where vd.long_name = 'java.lang.String';

select de.preferred_name, de.long_name, de.preferred_definition, de.version, de.asl_name
from Data_elements de, Data_element_concepts dec, value_domains vd
where dec.dec_idseq = de.dec_idseq
and dec.long_name = 'Target Preferred Name'
and vd.long_name = 'java.lang.String';
