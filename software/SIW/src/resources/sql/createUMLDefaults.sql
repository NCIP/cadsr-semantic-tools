PROMPT CREATE TABLE sbrext.uml_loader_defaults

CREATE TABLE sbrext.uml_loader_defaults
(
    id                               NUMBER   (11)                   NOT NULL
  , context_name                     VARCHAR2 (30)                   
  , context_version                  NUMBER   (4,2)                  
  , project_name                     VARCHAR2 (100)                  
  , project_version                  NUMBER   (4,2)                  
  , version                          NUMBER   (4,2)                  
  , workflow_status                  VARCHAR2 (20)                   
  , cd_preferred_name                VARCHAR2 (30)                   
  , cd_conte_name                    VARCHAR2 (30)                   
  , package_filter                   VARCHAR2 (1000)                 
  , project_long_name                VARCHAR2 (255)                  
  , project_description              VARCHAR2 (255)                  
)
ORGANIZATION        HEAP
NOMONITORING
PARALLEL
(
  DEGREE            1
  INSTANCES         1
)
NOCACHE
PCTUSED             40
PCTFREE             10
INITRANS            1
MAXTRANS            255
STORAGE
(
  INITIAL           40960
  NEXT              40960
  MINEXTENTS        1
  MAXEXTENTS        505
  PCTINCREASE       50
  FREELISTS         1
  FREELIST GROUPS   1
  BUFFER_POOL       default
)
LOGGING
TABLESPACE          sbr_data
;

PROMPT CREATE UNIQUE INDEX sbrext.uml_ldt_pk ON sbrext.uml_loader_defaults

CREATE UNIQUE INDEX sbrext.uml_ldt_pk ON sbrext.uml_loader_defaults
(
    id
)
PARALLEL
(
  DEGREE            1
  INSTANCES         1
)
PCTFREE             10
INITRANS            2
MAXTRANS            255
STORAGE
(
  INITIAL           40960
  NEXT              40960
  MINEXTENTS        1
  MAXEXTENTS        505
  PCTINCREASE       50
  FREELISTS         1
  FREELIST GROUPS   1
  BUFFER_POOL       default
)
LOGGING
TABLESPACE          sbr_data
;

PROMPT ALTER TABLE sbrext.uml_loader_defaults ADD CONSTRAINT uml_ldt_pk PRIMARY KEY

ALTER TABLE sbrext.uml_loader_defaults ADD CONSTRAINT uml_ldt_pk PRIMARY KEY
(
    id
)
NOT DEFERRABLE
INITIALLY IMMEDIATE
ENABLE NOVALIDATE;

