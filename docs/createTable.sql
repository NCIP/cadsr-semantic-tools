DROP TABLE sbrext.UML_LOADER_DEFAULTS;
create table sbrext.UML_LOADER_DEFAULTS (
    id                 NUMBER   (11)                   NOT NULL
  , context_name       VARCHAR2 (30)                   
  , context_version    NUMBER   (4,2)                  
  , project_name       VARCHAR2 (100)                    
  , project_version    NUMBER   (4,2)                  
  , version            NUMBER   (4,2)                  
  , workflow_status    VARCHAR2 (20)                   
  , cd_preferred_name  VARCHAR2 (30)                   
  , cd_conte_name      VARCHAR2 (30)                   
);
ALTER TABLE sbrext.UML_LOADER_DEFAULTS ADD CONSTRAINT uml_ldt_pk PRIMARY KEY
(
    id
)
NOT DEFERRABLE
INITIALLY IMMEDIATE
ENABLE NOVALIDATE;
