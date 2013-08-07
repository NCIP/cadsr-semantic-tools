/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

PROMPT TESTING DE ALT_NAME
select name, detl_name
from designations
where ac_idseq = 
(select de_idseq from data_elements
where long_name = 'Gene ID java.lang.String'
and created_by = 'LADINO'
)
;

PROMPT DE ALT_NAME CLASSIFICATIONS
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
(select de_idseq from data_elements
where long_name = 'Gene ID java.lang.String'
and created_by = 'LADINO'
)
); 

