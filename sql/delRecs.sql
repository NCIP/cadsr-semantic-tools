prompt DELETING AC_ATT_CSCSI
delete from ac_att_cscsi_ext 
where att_idseq in
(select desig_idseq from designations
where ac_idseq in 
(select oc_idseq from Object_classes_ext
where upper(created_by) = 'LADINO'
UNION 
select prop_idseq from properties_ext
where upper(created_by) = 'LADINO'
)
);

delete from ac_att_cscsi_ext
where cs_csi_idseq
in (
select cs_csi_idseq
from cs_csi
where cs_idseq in (
select cs_idseq from classification_schemes
where created_by = 'LADINO'
)
);

delete from ac_att_cscsi_ext
where cs_csi_idseq
in (
select cs_csi.cs_csi_idseq
from class_scheme_items csi, cs_csi
where cs_csi.csi_idseq = csi.csi_idseq 
and csi.created_by = 'LADINO'
);

prompt DELETING DESIGNATIONS
delete from designations 
where created_by = 'LADINO';

delete from designations 
where ac_idseq in 
(select oc_idseq from Object_classes_ext
where upper(created_by) = 'LADINO'
UNION
select ocr_idseq from oc_recs_ext
where upper(created_by) = 'LADINO'
UNION 
select prop_idseq from properties_ext
where upper(created_by) = 'LADINO'
UNION 
select con_idseq from concepts_ext
where upper(created_by) = 'LADINO'
UNION 
select dec_idseq from data_element_concepts
where upper(created_by) = 'LADINO'
UNION 
select de_idseq from data_elements
where upper(created_by) = 'LADINO'
);

prompt DELETING DEFINITIONS 
delete from definitions
where created_by = 'LADINO';


--prompt DELETING DEFINITIONS
--delete from definitions 
--where ac_idseq in 
--(select oc_idseq from Object_classes_ext
--where upper(created_by) = 'LADINO'
--UNION 
--select prop_idseq from properties_ext
--where upper(created_by) = 'LADINO'
--UNION 
--select dec_idseq from data_element_concepts
--where upper(created_by) = 'LADINO'
--UNION 
--select de_idseq from data_elements
--where upper(created_by) = 'LADINO'
--UNION 
--select con_idseq from concepts_ext
--where upper(created_by) = 'LADINO')
--;

prompt deleting AC_CSI
delete from ac_csi where ac_idseq in
(select de_idseq from data_elements
where upper(created_by) = 'LADINO'
UNION
select dec_idseq from data_element_concepts
where upper(created_by) = 'LADINO'
UNION
select ocr_idseq from oc_recs_ext 
where upper(created_by) = 'LADINO'
UNION
select oc_idseq from object_classes_ext
where upper(created_by) = 'LADINO'
UNION
select prop_idseq from properties_ext
where upper(created_by) = 'LADINO'
UNION 
select con_idseq from concepts_ext
where upper(created_by) = 'LADINO'
);

delete from ac_csi
where cs_csi_idseq 
in (
select cs_csi_idseq
from cs_csi
where cs_idseq in (
select cs_idseq from classification_schemes
where created_by = 'LADINO'
)
);

delete from ac_att_cscsi_ext
where cs_csi_idseq
in (
select cs_csi_idseq
from cs_csi
where cs_idseq in (
select cs_idseq from classification_schemes
where created_by = 'LADINO'
)
);

prompt DELETING DE ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select de_idseq from data_elements
where upper(created_by) = 'LADINO');

prompt DELETING DEC ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select dec_idseq from data_element_concepts
where upper(created_by) = 'LADINO');

prompt DELETING OCR ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select ocr_idseq from oc_recs_ext 
where upper(created_by) = 'LADINO');

prompt DELETING OC ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select oc_idseq from object_classes_ext
where upper(created_by) = 'LADINO');

prompt DELETING PROP ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select prop_idseq from properties_ext
where upper(created_by) = 'LADINO');

prompt DELETING CONCEPT ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select con_idseq from concepts_ext
where upper(created_by) = 'LADINO');

prompt DELETING CS ADMIN COMPONENTS
delete from administered_components where ac_idseq in
(select cs_idseq from classification_schemes
where upper(created_by) = 'LADINO');

Prompt DELETING DEs
delete from data_elements
where upper(created_by) = 'LADINO';

Prompt DELETING DECs
delete from data_element_concepts
where upper(created_by) = 'LADINO';

Prompt DELETING OC_RECs
delete from oc_recs_ext 
where upper(created_by) = 'LADINO';

Prompt Deleting Ocs
Delete From Object_Classes_Ext
Where Upper(Created_By) = 'LADINO';

Prompt Deleting Props
Delete From Properties_Ext
Where Upper(Created_By) = 'LADINO';


--Prompt DELETING Concept Definitions
--delete from definitions
--where ac_idseq in 
--(select con_idseq from concepts_ext
--where upper(created_by) = 'LADINO')
--;

prompt DELETING OC COMPONENT CONCEPTS
delete from component_concepts_ext
where condr_idseq in
(select condr_idseq from object_classes_ext
where upper(created_by) = 'LADINO');

prompt DELETING PROP COMPONENT CONCEPTS
delete from component_concepts_ext
where condr_idseq in
(
select condr_idseq from properties_ext
where upper(created_by) = 'LADINO'
);

prompt DELETEING COMPONENT CONCEPTS
delete from component_concepts_ext
where con_idseq in 
(select con_idseq from concepts_ext
where upper(created_by) = 'LADINO'
);

prompt DELETING PROP CONCEPT DERIVATIONS RULES
delete from con_derivation_rules_ext 
where condr_idseq in
(
select condr_idseq from properties_ext
where upper(created_by) = 'LADINO'
);

prompt DELETING OC CONCEPT DERIVATIONS RULES
delete from con_derivation_rules_ext 
where condr_idseq in
(
select condr_idseq from object_classes_ext
where upper(created_by) = 'LADINO'
);


Prompt DELETING CONCEPTs
delete from concepts_ext
where upper(created_by) = 'LADINO';

PROMPT DELETING CS_CSI
delete from cs_csi
where cs_idseq in (
select cs_idseq from classification_schemes
where created_by = 'LADINO'
UNION
select csi_idseq from class_scheme_items
where created_by = 'LADINO'
);

delete from cs_csi
where cs_csi_idseq in (
select cs_csi.cs_csi_idseq
from class_scheme_items csi, cs_csi
where cs_csi.csi_idseq = csi.csi_idseq 
and csi.created_by = 'LADINO'
);

PROMPT DELETING CSI
delete from class_scheme_items
where created_by = 'LADINO';

PROMPT DELETE CS
delete from Classification_schemes
where created_by = 'LADINO';



