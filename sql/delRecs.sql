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
where 
(detl_name = 'SYNONYM' 
or detl_name like 'UML ')
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
UNION 
select dec_idseq from data_element_concepts
where upper(created_by) = 'LADINO'
UNION 
select de_idseq from data_elements
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


