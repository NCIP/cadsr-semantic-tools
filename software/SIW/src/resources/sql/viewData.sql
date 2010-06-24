
-- Object Classes for a model
select ac.long_name oc_long_name
      ,ac.preferred_name oc_preferred_name
	  ,ac.preferred_definition oc_preferred_definition
	  ,conte.name,
	   ac.asl_name
	  ,des.NAME "UML Class"
from   ac_csi acs
      ,administered_components ac
	  ,contexts conte
	  ,designations des
where  cs_csi_idseq in (select csc.cs_csi_idseq
	   				    from   classification_schemes cs, class_scheme_items csi, cs_csi csc
						where  cs.cs_idseq = csc.cs_idseq
						and    csi.csi_idseq = csc.csi_idseq
						and    cs.preferred_name = 'Gene-Test-1')
and    ac.actl_name = 'OBJECTCLASS'
and    acs.ac_idseq = ac.ac_idseq
and    conte.conte_idseq = ac.conte_idseq
and    ac.AC_IDSEQ = des.AC_IDSEQ
and    des.DETL_NAME = 'UML Class'

-- Properties for a model
select ac.long_name prop_long_name
      ,ac.preferred_name prop_preferred_name
	  ,ac.preferred_definition prop_preferred_definition
	  ,conte.name,
	   ac.asl_name
	  ,des.NAME "UML Attribute"
from   ac_csi acs
      ,administered_components ac
	  ,contexts conte
	  ,designations des
where  cs_csi_idseq in (select csc.cs_csi_idseq
	   				    from   classification_schemes cs, class_scheme_items csi, cs_csi csc
						where  cs.cs_idseq = csc.cs_idseq
						and    csi.csi_idseq = csc.csi_idseq
						and    cs.preferred_name = 'Duke-Test')
and    ac.actl_name = 'PROPERTY'
and    acs.ac_idseq = ac.ac_idseq
and    conte.conte_idseq = ac.conte_idseq
and    ac.AC_IDSEQ = des.AC_IDSEQ
and    des.DETL_NAME = 'UML Attribute'


-- DEC's for a model
select ac.long_name prop_long_name
      ,ac.preferred_name prop_preferred_name
	  ,ac.preferred_definition prop_preferred_definition
	  ,conte.name,
	   ac.asl_name
	  ,des.NAME "UML Class-Attribute"
from   ac_csi acs
      ,administered_components ac
	  ,contexts conte
	  ,designations des
where  cs_csi_idseq in (select csc.cs_csi_idseq
	   				    from   classification_schemes cs, class_scheme_items csi, cs_csi csc
						where  cs.cs_idseq = csc.cs_idseq
						and    csi.csi_idseq = csc.csi_idseq
						and    cs.preferred_name = 'OSU-Test')
and    ac.actl_name = 'DE_CONCEPT'
and    acs.ac_idseq = ac.ac_idseq
and    conte.conte_idseq = ac.conte_idseq
and    ac.AC_IDSEQ = des.AC_IDSEQ
and    des.DETL_NAME = 'UML Class:Attribute'



select distinct actl_name from administered_components