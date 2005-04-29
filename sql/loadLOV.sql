--insert into definition_types_lov_ext (defl_name, description)
--values ('UML Class', 'Definition extracted from the UML Class Documentation');

--insert into definition_types_lov_ext (defl_name, description)
--values ('UML Class : UML Attr', 'Attribute Description from a UML Class Diagram');

insert into AC_ATT_TYPES_LOV_EXT (ATL_NAME) values ('DEFINITION');
insert into AC_ATT_TYPES_LOV_EXT (ATL_NAME) values ('DESIGNATION');

insert into CSI_TYPES_LOV (csitl_name, description) 
values ('UML_PACKAGE_NAME', 'Holds the full package name');

insert into CSI_TYPES_LOV (csitl_name, description) 
values ('UML_PACKAGE_ALIAS', 'Holds the package Alias');

insert into CS_TYPES_LOV (CSTL_NAME, DESCRIPTION)
values ('Project', 'Used for CS that describe a project');

insert into DESIGNATION_TYPES_LOV (DETL_NAME)
values ('UML Class:UML Attr');
insert into DESIGNATION_TYPES_LOV (DETL_NAME)
values ('UML Qualified Attr');
insert into DESIGNATION_TYPES_LOV (DETL_NAME)
values ('UML Class');
insert into DESIGNATION_TYPES_LOV (DETL_NAME)
values ('UML Attribute');

insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('NCI');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('NCICB');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('CABIO');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('NCI-GLOSS');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('CSP2000');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('MSH2001');
insert into DEFINITION_TYPES_LOV_EXT (DEFL_NAME)
values ('MSH2002_06_01');

--insert into conceptual_domains (preferred_name, long_name, preferred_definition, conte_idseq)
--values ('BIOINFORMATICS', 'BIOINFORMATICS', 'Bioinformatics Conceptual Domain', (select conte_idseq from contexts where name = 'caCORE'));

insert into conceptual_domains (preferred_name, long_name, preferred_definition, conte_idseq)
bvalues ('UML DEFAULT CD', 'UML DEFAULT CD', 'Default Conceptual Domain for UML Loader', (select conte_idseq from contexts where name = 'caCORE'));

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('byte', 'byte', (select conte_idseq from contexts where name = 'caCORE'), 'java byte type', 'NUMBER', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS' ), 'N');

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('java.util.Date', 'java.util.Date', (select conte_idseq from contexts where name = 'caCORE'), 'Java Date Type', 'DATE', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS' ), 'N');

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('boolean', 'boolean', (select conte_idseq from contexts where name = 'caCORE'), 'Java Boolean type', 'BOOLEAN', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS'), 'E');

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('float', 'float', (select conte_idseq from contexts where name = 'caCORE'), 'Java Float type', 'NUMBER', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS' ), 'N');

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('int', 'int', (select conte_idseq from contexts where name = 'caCORE'), 'Java int Type', 'NUMBER', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS' ), 'N');

insert into value_domains (preferred_name, long_name, conte_idseq, preferred_definition, DTL_NAME, CD_IDSEQ, VD_TYPE_FLAG)
values ('long', 'long', (select conte_idseq from contexts where name = 'caCORE'), 'Java long type', 'NUMBER', (select cd_idseq from conceptual_domains where preferred_name = 'BIOINFORMATICS' ), 'N');


