delete from data_elements
where created_by = 'LADINO';

delete from data_element_concepts
where created_by = 'LADINO';

delete from oc_recs_ext 
where created_by = 'LADINO';

delete from object_classes_ext
where created_by = 'LADINO';

delete from properties_ext
where created_by = 'LADINO';

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name like 'Employee%')
;

delete from administered_components where preferred_name like 'Employee%';

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name like 'Company%')
;

delete from administered_components where preferred_name like 'Company%';

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name = 'Manager')
;

delete from administered_components where preferred_name = 'Manager';

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name = 'Salary')
;

delete from administered_components where preferred_name = 'Salary';

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name = 'id'
and conte_idseq = 
(select conte_idseq from contexts where name='TEST')
)
;

delete from administered_components 
where preferred_name = 'id'
and conte_idseq = 
(select conte_idseq from contexts where name='TEST')
;

delete from ac_csi
where ac_idseq in
(select ac_idseq from administered_components
where preferred_name = 'name'
and conte_idseq = 
(select conte_idseq from contexts where name='TEST')
)
;

delete from administered_components 
where preferred_name = 'name'
and conte_idseq = 
(select conte_idseq from contexts where name='TEST')
and version=1
;


--delete from cs_csi
--where created_by = 'LADINO';

--delete from class_scheme_items
--where created_by = 'LADINO';

--delete from classification_schemes
--where created_by = 'LADINO';

