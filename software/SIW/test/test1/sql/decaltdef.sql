PROMPT TESTING DEC ALT DEF

select definition, defl_name
from definitions
where ac_idseq in
(select dec_idseq 
from data_element_concepts
where long_name = 'Gene ID'
and created_by = 'LADINO');

PROMPT ALT DEF CLASSIFICATION
select CSI_NAME, csitl_name, preferred_name
from class_scheme_items csi, ac_att_cscsi_ext att_csi, cs_csi, classification_schemes cs
where csi.csi_idseq = cs_csi.csi_idseq 
and cs_csi.cs_csi_idseq = att_csi.cs_csi_idseq
and cs.cs_idseq = cs_csi.cs_idseq
and att_csi.att_idseq in 
(
select defin_idseq
from definitions
where ac_idseq = 
(select dec_idseq 
from data_element_concepts
where long_name = 'Gene ID'
and created_by = 'LADINO')
); 
