col source_name format a15;
col target_name format a15;
col role_name format a35;
col ocr_idseq format a10;


select ocr.ocr_idseq,
  ocr.long_name as role_name,
  oc.long_name as source_name,
  oc2.long_name as target_name
from oc_recs_ext ocr, ac_csi a,
  cs_csi c, classification_schemes cs, class_scheme_items csi,
  Object_classes_ext oc, Object_classes_ext oc2
where a.ac_idseq = ocr.ocr_idseq
 and a.cs_csi_idseq = c.cs_csi_idseq
 and c.csi_idseq = csi.csi_idseq
 and c.cs_idseq = cs.cs_idseq
 and cs.long_name = '&a'
 and ocr.s_oc_idseq = oc.oc_idseq
 and ocr.t_oc_idseq = oc2.oc_idseq

/