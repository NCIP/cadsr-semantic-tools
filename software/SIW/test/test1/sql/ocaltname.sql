/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

PROMPT TESTING ALT_NAME
select name, detl_name
from designations
where ac_idseq = 
(select oc_idseq from object_classes_ext
where long_name = 'Clone'
and created_by = 'LADINO'
)
;

PROMPT ALT_NAME CLASSIFICATIONS
select CSI_NAME, csitl_name, preferred_name
from class_scheme_items csi, ac_att_cscsi_ext att_csi, cs_csi, classification_schemes cs
where csi.csi_idseq = cs_csi.csi_idseq 
and cs_csi.cs_csi_idseq = att_csi.cs_csi_idseq
and cs.cs_idseq = cs_csi.cs_idseq
and att_csi.att_idseq in 
(
select desig_idseq
from designations
where ac_idseq = 
(select oc_idseq from object_classes_ext
where long_name = 'Clone'
and created_by = 'LADINO'
)
); 

