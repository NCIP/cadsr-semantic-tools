/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

PROMPT Checking for existence of DEC Target Preferred Name

prompt OC public id
select oc_id from object_Classes_ext oc
where oc.long_name = 'Target';

prompt PROP public id
select prop_id from properties_ext prop
where prop.long_name = 'Preferred Name';

select dec.preferred_name, dec.long_name, dec.preferred_definition, dec.version, dec.asl_name
from Data_element_concepts dec, object_Classes_ext oc, properties_ext prop
where dec.oc_idseq = oc.oc_idseq
and dec.prop_idseq = prop.prop_idseq
and oc.long_name = 'Target'
and prop.long_name = 'Preferred Name';
