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

prompt DELETING DESIGNATIONS
delete from designations 
where detl_name = 'SYNONYM' 
and ac_idseq in 
(select oc_idseq from Object_classes_ext
where upper(created_by) = 'LADINO'
UNION 
select prop_idseq from properties_ext
where upper(created_by) = 'LADINO'
);

prompt DELETING DEFINITIONS 
delete from definitions
where defl_name = 'FUN-DEF'; 

prompt DELETING DEFINITIONS
delete from definitions 
where ac_idseq in 
(select oc_idseq from Object_classes_ext
where upper(created_by) = 'LADINO'
UNION 
select prop_idseq from properties_ext
where upper(created_by) = 'LADINO'
);


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

prompt DELETING ADMIN COMPONENTS
delete from administered_components where ac_idseq in
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

Prompt DELETING DEs
delete from data_elements
where upper(created_by) = 'LADINO';

Prompt DELETING DECs
delete from data_element_concepts
where upper(created_by) = 'LADINO';

Prompt DELETING OC_RECs
delete from oc_recs_ext 
where upper(created_by) = 'LADINO';

Prompt DELETING OCs
delete from object_classes_ext
where upper(created_by) = 'LADINO';

Prompt DELETING PROPs
delete from properties_ext
where upper(created_by) = 'LADINO';


Prompt DELETING Concept Definitions
delete from definitions
where ac_idseq in 
(select con_idseq from concepts_ext
where upper(created_by) = 'LADINO')
;

prompt DELETING COMPONENT CONCEPTS
delete from component_concepts_ext
where condr_idseq in
(select condr_idseq from object_classes_ext
where upper(created_by) = 'LADINO'
UNION
select condr_idseq from properties_ext
where upper(created_by) = 'LADINO'
);

delete from component_concepts_ext
where con_idseq in 
(select con_idseq from concepts_ext
where upper(created_by) = 'LADINO'
);

prompt DELETING CONCEPT DERIVATIONS RULES
delete from con_derivation_rules_ext 
where condr_idseq in
(select condr_idseq from object_classes_ext
where upper(created_by) = 'LADINO'
UNION
select condr_idseq from properties_ext
where upper(created_by) = 'LADINO'
);


Prompt DELETING CONCEPTs
delete from concepts_ext
where upper(created_by) = 'LADINO';

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name like 'Employee%')
--;

--delete from administered_components where preferred_name like 'Employee%';

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name like 'Company%')
--;

--delete from administered_components where preferred_name like 'Company%';

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name = 'Manager')
--;

--delete from administered_components where preferred_name = 'Manager';

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name = 'Salary')
--;

--delete from administered_components where preferred_name = 'Salary';

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name = 'id'
--and conte_idseq = 
--(select conte_idseq from contexts where name='TEST')
--)
--;

--delete from administered_components 
--where preferred_name = 'id'
--and conte_idseq = 
--(select conte_idseq from contexts where name='TEST')
--;

--delete from ac_csi
--where ac_idseq in
--(select ac_idseq from administered_components
--where preferred_name = 'name'
--and conte_idseq = 
--(select conte_idseq from contexts where name='TEST')
--)
--;

--delete from administered_components 
--where preferred_name = 'name'
--and conte_idseq = 
--(select conte_idseq from contexts where name='TEST')
--and version=1
--;



--delete from administered_components 
--where AcTL_name = 'CONCEPT'
--and preferred_name like 'TEST%';



--delete from cs_csi
--where upper(created_by) = 'LADINO';

--delete from class_scheme_items
--where upper(created_by) = 'LADINO';

--delete from classification_schemes
--where upper(created_by) = 'LADINO';

