select recs.long_name, recs.direction, source.long_name, target.long_name, 
 source_low_multiplicity "srcLow",  source_high_multiplicity "srcHi", target_low_multiplicity "tarLow", target_low_multiplicity "tarHi"
from oc_recs_ext recs, object_classes_ext source, object_classes_ext target
where t_oc_idseq = target.oc_idseq
and s_oc_idseq = source.oc_idseq
and recs.created_by = 'LADINO';