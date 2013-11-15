/*L
  Copyright Oracle Inc, SAIC, SAIC-F

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
L*/

CREATE TABLE SBR.USER_ACCOUNTS
(
  UA_NAME                  VARCHAR2(30 BYTE)    NOT NULL,
  DER_ADMIN_IND            VARCHAR2(3 BYTE)     DEFAULT 'No'                  NOT NULL,
  NAME                     VARCHAR2(30 BYTE)        NULL,
  TITLE                    VARCHAR2(240 BYTE)       NULL,
  DESCRIPTION              VARCHAR2(100 BYTE)       NULL,
  ENABLED_IND              VARCHAR2(3 BYTE)     DEFAULT 'Yes'                 NOT NULL,
  ORG_IDSEQ                CHAR(36 BYTE)        NOT NULL,
  PHONE_NUMBER             VARCHAR2(30 BYTE)        NULL,
  FAX_NUMBER               VARCHAR2(30 BYTE)        NULL,
  TELEX_NUMBER             VARCHAR2(30 BYTE)        NULL,
  MAIL_ADDRESS             VARCHAR2(240 BYTE)       NULL,
  ELECTRONIC_MAIL_ADDRESS  VARCHAR2(100 BYTE)       NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_CREATED             DATE                 NOT NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL,
  DATE_MODIFIED            DATE                     NULL,
  ALERT_IND                VARCHAR2(3 BYTE)         NULL
);


CREATE TABLE SBR.AC_STATUS_LOV
(
  ASL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DISPLAY_ORDER  NUMBER                             NULL
);


CREATE TABLE SBR.AC_SUBJECTS
(
  ACSUB_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  SUBJ_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_TYPES_LOV
(
  ACTL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_WF_BUSINESS_ROLES
(
  AWB_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  AWR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  BRL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.AC_WF_RULES
(
  AWR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  SCL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  FROM_ASL_NAME  VARCHAR2(20 BYTE)              NOT NULL,
  TO_ASL_NAME    VARCHAR2(20 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.ADDR_TYPES_LOV
(
  ATL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.ADMINISTERED_COMPONENTS
(
  AC_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  ACTL_NAME             VARCHAR2(20 BYTE)       NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  STEWA_IDSEQ           CHAR(36 BYTE)               NULL,
  CMSL_NAME             VARCHAR2(20 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  UNRESOLVED_ISSUE      VARCHAR2(200 BYTE)          NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  PUBLIC_ID             NUMBER                  NOT NULL
);


CREATE TABLE SBR.ADVANCE_RPT_LOV
(
  NAME           VARCHAR2(50 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.APP_COMPONENT_TYPES_LOV
(
  APCTL_NAME     VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.RULES_LOV
(
  RULE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.SC_CONTEXTS
(
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  SCL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.SC_GROUPS
(
  SCG_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  SCL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  GRP_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.SC_USER_ACCOUNTS
(
  SCUA_IDSEQ         CHAR(36 BYTE)              NOT NULL,
  SCL_NAME           VARCHAR2(30 BYTE)          NOT NULL,
  UA_NAME            VARCHAR2(30 BYTE)          NOT NULL,
  CONTEXT_ADMIN_IND  VARCHAR2(3 BYTE)           NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_CREATED       DATE                       NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBR.SECURITY_CONTEXTS_LOV
(
  SCL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.STEWARDS
(
  STEWA_IDSEQ              CHAR(36 BYTE)        NOT NULL,
  NAME                     VARCHAR2(30 BYTE)    NOT NULL,
  ORG_IDSEQ                CHAR(36 BYTE)        NOT NULL,
  TITLE                    VARCHAR2(240 BYTE)       NULL,
  PHONE_NUMBER             VARCHAR2(30 BYTE)        NULL,
  FAX_NUMBER               VARCHAR2(30 BYTE)        NULL,
  TELEX_NUMBER             VARCHAR2(30 BYTE)        NULL,
  MAIL_ADDRESS             VARCHAR2(240 BYTE)       NULL,
  ELECTRONIC_MAIL_ADDRESS  VARCHAR2(100 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBR.SUBJECTS
(
  SUBJ_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  SUBJ_NAME      VARCHAR2(5 BYTE)               NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  P_SUBJ_IDSEQ   CHAR(36 BYTE)                      NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.SUBMITTERS
(
  SUB_IDSEQ                CHAR(36 BYTE)        NOT NULL,
  NAME                     VARCHAR2(30 BYTE)    NOT NULL,
  ORG_IDSEQ                CHAR(36 BYTE)        NOT NULL,
  TITLE                    VARCHAR2(240 BYTE)       NULL,
  SUBMIT_DATE              DATE                     NULL,
  PHONE_NUMBER             VARCHAR2(30 BYTE)        NULL,
  FAX_NUMBER               VARCHAR2(30 BYTE)        NULL,
  TELEX_NUMBER             VARCHAR2(30 BYTE)        NULL,
  MAIL_ADDRESS             VARCHAR2(240 BYTE)       NULL,
  ELECTRONIC_MAIL_ADDRESS  VARCHAR2(100 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBR.SUCCESS_LOG
(
  MSG   VARCHAR2(100 BYTE)                          NULL,
  TEXT  VARCHAR2(200 BYTE)                          NULL
);


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_CLASSIFICATION_SCHEM
(
  CS_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_CLASSIFICATION_SCHEM IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_AC_CSI
(
  AC_CSI_IDSEQ     CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_AC_CSI IS 'temporary updatable snapshot log';


CREATE TABLE SBR.CSI_TYPES_LOV_BACKUP
(
  CSITL_NAME     VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CS_CSI
(
  CS_CSI_IDSEQ       CHAR(36 BYTE)              NOT NULL,
  CS_IDSEQ           CHAR(36 BYTE)              NOT NULL,
  CSI_IDSEQ          CHAR(36 BYTE)              NOT NULL,
  P_CS_CSI_IDSEQ     CHAR(36 BYTE)                  NULL,
  LINK_CS_CSI_IDSEQ  CHAR(36 BYTE)                  NULL,
  LABEL              VARCHAR2(255 BYTE)         NOT NULL,
  DISPLAY_ORDER      NUMBER(2)                      NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBR.CS_CSI_BACKUP
(
  CS_CSI_IDSEQ       CHAR(36 BYTE)              NOT NULL,
  CS_IDSEQ           CHAR(36 BYTE)              NOT NULL,
  CSI_IDSEQ          CHAR(36 BYTE)              NOT NULL,
  P_CS_CSI_IDSEQ     CHAR(36 BYTE)                  NULL,
  LINK_CS_CSI_IDSEQ  CHAR(36 BYTE)                  NULL,
  LABEL              VARCHAR2(30 BYTE)          NOT NULL,
  DISPLAY_ORDER      NUMBER(2)                      NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBR.CS_CSI_BU
(
  CS_CSI_IDSEQ       CHAR(36 BYTE)              NOT NULL,
  CS_IDSEQ           CHAR(36 BYTE)              NOT NULL,
  CSI_IDSEQ          CHAR(36 BYTE)              NOT NULL,
  P_CS_CSI_IDSEQ     CHAR(36 BYTE)                  NULL,
  LINK_CS_CSI_IDSEQ  CHAR(36 BYTE)                  NULL,
  LABEL              VARCHAR2(30 BYTE)          NOT NULL,
  DISPLAY_ORDER      NUMBER(2)                      NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBR.CS_ITEMS
(
  CSI_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  CSI_NAME              VARCHAR2(255 BYTE)          NULL,
  CSITL_NAME            VARCHAR2(20 BYTE)       NOT NULL,
  DESCRIPTION           VARCHAR2(255 BYTE)          NULL,
  COMMENTS              VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)      NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  DELETED_IND           VARCHAR2(3 BYTE)        NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)        NOT NULL,
  CSI_ID                NUMBER                  NOT NULL,
  VERSION               NUMBER                  NOT NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBR.CS_RECS
(
  CS_REC_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  P_CS_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  C_CS_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DISPLAY_ORDER  NUMBER(2)                          NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CS_TYPES_LOV
(
  CSTL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CS_TYPES_LOV_BACKUP
(
  CSTL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DESIGNATIONS
(
  DESIG_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(2000 BYTE)            NOT NULL,
  DETL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DESIGNATIONS_BACKUP
(
  DESIG_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  DETL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DESIGNATIONS_BACKUP_NEW
(
  DESIG_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  DETL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DESIGNATION_TYPES_LOV
(
  DETL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DESIG_BACKUP
(
  DESIG_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  DETL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DE_BACKUP
(
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CDE_ID                NUMBER                      NULL,
  QUESTION              VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBR.DE_RECS
(
  DE_REC_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  P_DE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  C_DE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.DOCUMENT_TYPES_LOV
(
  DCTL_NAME      VARCHAR2(60 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  ACTL_NAME      VARCHAR2(20 BYTE)                  NULL
);


CREATE TABLE SBR.FAILED_LOG
(
  MSG   VARCHAR2(100 BYTE)                          NULL,
  TEXT  VARCHAR2(200 BYTE)                          NULL
);


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_VALUE_DOMAINS
(
  VD_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_VALUE_DOMAINS IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_DESIGNATIONS
(
  DESIG_IDSEQ      CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_DESIGNATIONS IS 'temporary updatable snapshot log';


CREATE TABLE SBR.UI_LINK_PARAMS
(
  UILP_IDSEQ    CHAR(36 BYTE)                   NOT NULL,
  UIL_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  NAME          VARCHAR2(50 BYTE)               NOT NULL,
  URL_LABEL     VARCHAR2(240 BYTE)              NOT NULL,
  VALUE_SOURCE  VARCHAR2(240 BYTE)              NOT NULL,
  CORE_IND      VARCHAR2(3 BYTE)                DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.WSGSR_USERDATA
(
  USER_NAME  VARCHAR2(30 BYTE)                  NOT NULL,
  PASSWORD   VARCHAR2(30 BYTE)                  NOT NULL,
  TITLE      VARCHAR2(10 BYTE)                      NULL,
  NAME1      VARCHAR2(30 BYTE)                  NOT NULL,
  NAME2      VARCHAR2(30 BYTE)                      NULL,
  SEX        VARCHAR2(4 BYTE)                       NULL,
  EMAIL      VARCHAR2(30 BYTE)                  NOT NULL,
  ADDRESS1   VARCHAR2(30 BYTE)                  NOT NULL,
  ADDRESS2   VARCHAR2(30 BYTE)                      NULL,
  ADDRESS3   VARCHAR2(30 BYTE)                      NULL,
  ADDRESS4   VARCHAR2(30 BYTE)                      NULL,
  POSTCODE   VARCHAR2(10 BYTE)                      NULL,
  TELEPHONE  VARCHAR2(30 BYTE)                      NULL
);


CREATE TABLE SBR.VALUE_DOMAINS
(
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  DTL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  END_DATE              DATE                        NULL,
  VD_TYPE_FLAG          VARCHAR2(1 BYTE)        NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  UOML_NAME             VARCHAR2(20 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  FORML_NAME            VARCHAR2(20 BYTE)           NULL,
  MAX_LENGTH_NUM        NUMBER(8)                   NULL,
  MIN_LENGTH_NUM        NUMBER(8)                   NULL,
  DECIMAL_PLACE         NUMBER(2)                   NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHAR_SET_NAME         VARCHAR2(20 BYTE)           NULL,
  HIGH_VALUE_NUM        VARCHAR2(255 BYTE)          NULL,
  LOW_VALUE_NUM         VARCHAR2(255 BYTE)          NULL,
  REP_IDSEQ             CHAR(36 BYTE)               NULL,
  QUALIFIER_NAME        VARCHAR2(30 BYTE)           NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  VD_ID                 NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL
);


CREATE TABLE SBR.USER_GROUPS
(
  UA_NAME        VARCHAR2(30 BYTE)              NOT NULL,
  GRP_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.WSGSR_SESSIONS
(
  CLIENT_ID   VARCHAR2(240 BYTE)                NOT NULL,
  USER_NAME   VARCHAR2(30 BYTE)                 NOT NULL,
  IP_ADDRESS  VARCHAR2(30 BYTE)                 NOT NULL,
  EXPIRES     DATE                                  NULL
);


CREATE TABLE SBR.VD_RECS
(
  VD_REC_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  P_VD_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  C_VD_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.VD_PV_RECS
(
  VPR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  P_VP_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  C_VP_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.VD_PVS
(
  VP_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  VD_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  PV_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                      NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  ORIGIN         VARCHAR2(240 BYTE)                 NULL,
  CON_IDSEQ      CHAR(36 BYTE)                      NULL,
  BEGIN_DATE     DATE                               NULL,
  END_DATE       DATE                               NULL
);


CREATE TABLE SBR.OC_CADSR
(
  DE_IDSEQ            CHAR(36 BYTE)                 NULL,
  DE_PREFERRED_NAME   VARCHAR2(255 BYTE)            NULL,
  DE_DOC_TEXT         VARCHAR2(255 BYTE)            NULL,
  DEC_PREFERRED_NAME  VARCHAR2(255 BYTE)            NULL,
  VD_PREFERRED_NAME   VARCHAR2(255 BYTE)            NULL,
  CD_PREFERRED_NAME   VARCHAR2(255 BYTE)            NULL,
  VD_MAX_LENGTH_NUM   NUMBER(8)                     NULL,
  VD_DTL_NAME         VARCHAR2(20 BYTE)             NULL
);


CREATE TABLE SBR.OC_COMPRESULT
(
  DE_IDSEQ            CHAR(36 BYTE)                 NULL,
  DE_PREFERRED_NAME   VARCHAR2(255 BYTE)            NULL,
  DEC_PREFERRED_NAME  VARCHAR2(255 BYTE)            NULL,
  VD_PREFERRED_NAME   VARCHAR2(255 BYTE)            NULL,
  DE_LOG              VARCHAR2(2000 BYTE)           NULL,
  DEC_LOG             VARCHAR2(2000 BYTE)           NULL,
  VD_LOG              VARCHAR2(2000 BYTE)           NULL,
  CD_LOG              VARCHAR2(2000 BYTE)           NULL,
  CREATED_DATE        DATE                          NULL
);


CREATE TABLE SBR.OC_COMPRESULT2
(
  DE_PREFERRED_NAME  VARCHAR2(255 BYTE)             NULL,
  VD_PREFERRED_NAME  VARCHAR2(255 BYTE)             NULL,
  CD_PREFERRED_NAME  VARCHAR2(255 BYTE)             NULL,
  VD_LOG             VARCHAR2(2000 BYTE)            NULL,
  CD_LOG             VARCHAR2(2000 BYTE)            NULL,
  PV_MORE            VARCHAR2(2000 BYTE)            NULL,
  PV_LESS            VARCHAR2(2000 BYTE)            NULL,
  CREATED_DATE       DATE                           NULL
);


CREATE TABLE SBR.OC_VD
(
  DE_PREFERRED_NAME     VARCHAR2(255 BYTE)          NULL,
  DE_DOC_TEXT           VARCHAR2(255 BYTE)          NULL,
  VD_PREFERRED_NAME     VARCHAR2(255 BYTE)          NULL,
  CD_PREFERRED_NAME     VARCHAR2(255 BYTE)          NULL,
  PV_PERMISSIBLE_VALUE  VARCHAR2(255 BYTE)          NULL
);


CREATE TABLE SBR.ORGANIZATIONS
(
  ORG_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  RAI            VARCHAR2(255 BYTE)                 NULL,
  NAME           VARCHAR2(80 BYTE)              NOT NULL,
  RA_IND         VARCHAR2(3 BYTE)                   NULL,
  MAIL_ADDRESS   VARCHAR2(240 BYTE)                 NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.PERMISSIBLE_VALUES
(
  PV_IDSEQ             CHAR(36 BYTE)            NOT NULL,
  VALUE                VARCHAR2(255 BYTE)       NOT NULL,
  SHORT_MEANING        VARCHAR2(255 BYTE)       NOT NULL,
  MEANING_DESCRIPTION  VARCHAR2(2000 BYTE)          NULL,
  BEGIN_DATE           DATE                         NULL,
  END_DATE             DATE                         NULL,
  HIGH_VALUE_NUM       NUMBER(10)                   NULL,
  LOW_VALUE_NUM        NUMBER(10)                   NULL,
  DATE_CREATED         DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL,
  VM_IDSEQ             CHAR(36 BYTE)            NOT NULL
);


CREATE TABLE SBR.PERSONS
(
  PER_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  LNAME          VARCHAR2(80 BYTE)              NOT NULL,
  FNAME          VARCHAR2(30 BYTE)              NOT NULL,
  RANK_ORDER     NUMBER(3)                      NOT NULL,
  ORG_IDSEQ      CHAR(36 BYTE)                      NULL,
  MI             VARCHAR2(2 BYTE)                   NULL,
  POSITION       VARCHAR2(80 BYTE)                  NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.PROGRAMS
(
  PA_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.PROGRAM_AREAS_LOV
(
  PAL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.PROPERTIES_LOV
(
  PROPL_NAME     VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_GRANTS
(
  GRANT_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  APPOBJ_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  ROLE_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  PRIV_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_OBJECTS
(
  APPOBJ_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(100 BYTE)             NOT NULL,
  OBJ_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  APP_VERSION    VARCHAR2(30 BYTE)              NOT NULL,
  SCHEMA_NAME    VARCHAR2(20 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  APCTL_NAME     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_OBJECTS_LOV
(
  OBJ_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_PRIV_LOV
(
  PRIV_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_ROLES_LOV
(
  ROLE_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.APP_VERSIONS
(
  APP_VERSION    VARCHAR2(30 BYTE)              NOT NULL,
  RELEASE_DATE   DATE                               NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.BUSINESS_ROLES_LOV
(
  BRL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  DESCRIPTION           VARCHAR2(60 BYTE)           NULL,
  COMMENTS              VARCHAR2(2000 BYTE)         NULL,
  CREATE_ALLOWED_IND    VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  READ_ALLOWED_IND      VARCHAR2(3 BYTE)        DEFAULT 'Yes'                 NOT NULL,
  UPDATE_ALLOWED_IND    VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  DELETE_ALLOWED_IND    VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  VERSION_ALLOWED_IND   VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  CHECKOUT_ALLOWED_IND  VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_CREATED          DATE                    NOT NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  DATE_MODIFIED         DATE                        NULL
);


CREATE TABLE SBR.CD_VMS
(
  CV_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CD_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  SHORT_MEANING  VARCHAR2(255 BYTE)             NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  VM_IDSEQ       CHAR(36 BYTE)                  NOT NULL
);


CREATE TABLE SBR.CHARACTER_SET_LOV
(
  CHAR_SET_NAME  VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL
);


CREATE TABLE SBR.CLASSIFICATION_SCHEMES
(
  CS_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  CSTL_NAME             VARCHAR2(20 BYTE)       NOT NULL,
  LABEL_TYPE_FLAG       CHAR(1 BYTE)            NOT NULL,
  CMSL_NAME             VARCHAR2(20 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CS_ID                 NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL,
  PAR_CS_IDSEQ          CHAR(36 BYTE)               NULL
);


CREATE TABLE SBR.CLASSIFICATION_SCHEMES_BACKUP
(
  CS_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  CSTL_NAME             VARCHAR2(20 BYTE)       NOT NULL,
  LABEL_TYPE_FLAG       CHAR(1 BYTE)            NOT NULL,
  CMSL_NAME             VARCHAR2(20 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL
);


CREATE TABLE SBR.CLASS_SCHEME_ITEMS_BACKUP
(
  CSI_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  CSI_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  CSITL_NAME     VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CM_STATES_LOV
(
  CMSL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.COMM_TYPES_LOV
(
  CTL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.COMPLEX_DATA_ELEMENTS
(
  P_DE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  METHODS        VARCHAR2(4000 BYTE)                NULL,
  RULE           VARCHAR2(4000 BYTE)                NULL,
  CONCAT_CHAR    CHAR(1 BYTE)                       NULL,
  DATE_MODIFIED  DATE                               NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  CRTL_NAME      VARCHAR2(30 BYTE)              NOT NULL
);


CREATE TABLE SBR.COMPLEX_DE_RELATIONSHIPS
(
  CDR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  C_DE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  P_DE_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DISPLAY_ORDER  NUMBER(4)                          NULL,
  DATE_MODIFIED  DATE                               NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  RF_IDSEQ       CHAR(36 BYTE)                      NULL,
  LEFT_STRING    VARCHAR2(50 BYTE)                  NULL,
  RIGHT_STRING   VARCHAR2(50 BYTE)                  NULL
);


CREATE TABLE SBR.COMPLEX_REP_TYPE_LOV
(
  CRTL_NAME      VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(200 BYTE)                 NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL
);


CREATE TABLE SBR.DATATYPES_LOV
(
  DTL_NAME                   VARCHAR2(20 BYTE)  NOT NULL,
  DESCRIPTION                VARCHAR2(60 BYTE)  NOT NULL,
  COMMENTS                   VARCHAR2(2000 BYTE)     NULL,
  CREATED_BY                 VARCHAR2(30 BYTE)  NOT NULL,
  DATE_CREATED               DATE               NOT NULL,
  DATE_MODIFIED              DATE                   NULL,
  MODIFIED_BY                VARCHAR2(30 BYTE)      NULL,
  SCHEME_REFERENCE           VARCHAR2(255 BYTE) NOT NULL,
  ANNOTATION                 VARCHAR2(2000 BYTE)     NULL,
  CODEGEN_COMPATIBILITY_IND  VARCHAR2(3 BYTE)       NULL
);


CREATE TABLE SBR.DATA_ELEMENTS
(
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CDE_ID                NUMBER                  NOT NULL,
  QUESTION              VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBR.DATA_ELEMENTS_BU
(
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CDE_ID                NUMBER                      NULL,
  QUESTION              VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBR.DATA_ELEMENT_CONCEPTS
(
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  CD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  PROPL_NAME            VARCHAR2(20 BYTE)           NULL,
  OCL_NAME              VARCHAR2(20 BYTE)           NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  OBJ_CLASS_QUALIFIER   VARCHAR2(30 BYTE)           NULL,
  PROPERTY_QUALIFIER    VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  OC_IDSEQ              CHAR(36 BYTE)               NULL,
  PROP_IDSEQ            CHAR(36 BYTE)               NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  DEC_ID                NUMBER                  NOT NULL
);


CREATE TABLE SBR.DEC_RECS
(
  DEC_REC_IDSEQ  CHAR(36 BYTE)                  NOT NULL,
  P_DEC_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  C_DEC_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL
);


CREATE TABLE SBR.DEFINITIONS
(
  DEFIN_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DEFINITION     VARCHAR2(2000 BYTE)            NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL,
  DEFL_NAME      VARCHAR2(50 BYTE)                  NULL
);


CREATE TABLE SBR.CONCEPTS_STG
(
  PREFERRED_NAME        VARCHAR2(30 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)         NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CONTEXT_NAME          VARCHAR2(30 BYTE)           NULL,
  VERSION               NUMBER                      NULL
);


CREATE TABLE SBR.CONCEPTUAL_DOMAINS
(
  CD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  DIMENSIONALITY        VARCHAR2(30 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  DATE_CREATED          DATE                    NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CD_ID                 NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL
);


CREATE TABLE SBR.CONTACT_ADDRESSES
(
  CADDR_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  ORG_IDSEQ      CHAR(36 BYTE)                      NULL,
  PER_IDSEQ      CHAR(36 BYTE)                      NULL,
  ATL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  RANK_ORDER     NUMBER(3)                      NOT NULL,
  ADDR_LINE1     VARCHAR2(80 BYTE)              NOT NULL,
  ADDR_LINE2     VARCHAR2(80 BYTE)                  NULL,
  CITY           VARCHAR2(30 BYTE)              NOT NULL,
  STATE_PROV     VARCHAR2(30 BYTE)              NOT NULL,
  POSTAL_CODE    VARCHAR2(10 BYTE)              NOT NULL,
  COUNTRY        VARCHAR2(30 BYTE)                  NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CONTACT_COMMS
(
  CCOMM_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  ORG_IDSEQ      CHAR(36 BYTE)                      NULL,
  PER_IDSEQ      CHAR(36 BYTE)                      NULL,
  CTL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  RANK_ORDER     NUMBER(3)                      NOT NULL,
  CYBER_ADDRESS  VARCHAR2(255 BYTE)             NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CONTEXTS
(
  CONTE_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  LL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  PAL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  LANGUAGE       VARCHAR2(30 BYTE)              NOT NULL,
  VERSION        NUMBER(4,2)                    DEFAULT 1.0                   NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.CSI_RECS
(
  CSI_REC_IDSEQ  CHAR(36 BYTE)                  NOT NULL,
  P_CSI_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  C_CSI_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.CSI_STAGING
(
  CSI_NAME          VARCHAR2(20 BYTE)               NULL,
  DESCRIPTION       VARCHAR2(30 BYTE)               NULL,
  CSITL_NAME        VARCHAR2(20 BYTE)               NULL,
  CDE_COLUMN_VALUE  VARCHAR2(60 BYTE)               NULL
);


CREATE TABLE SBR.CSI_TYPES_LOV
(
  CSITL_NAME     VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.UI_ITEM_LINK_RECS
(
  UIILR_IDSEQ   CHAR(36 BYTE)                   NOT NULL,
  UII_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  UIL_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  UIAL_NAME     VARCHAR2(20 BYTE)                   NULL,
  ACTL_NAME     VARCHAR2(20 BYTE)                   NULL,
  TARGET_FRAME  VARCHAR2(20 BYTE)                   NULL,
  CORE_IND      VARCHAR2(3 BYTE)                DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_ITEM_IMAGES
(
  UIIIM_IDSEQ     CHAR(36 BYTE)                 NOT NULL,
  UITL_NAME       VARCHAR2(20 BYTE)             NOT NULL,
  UIIMG_IDSEQ     CHAR(36 BYTE)                 NOT NULL,
  UII_IDSEQ       CHAR(36 BYTE)                 NOT NULL,
  IMAGE_USE_NAME  VARCHAR2(20 BYTE)                 NULL,
  CORE_IND        VARCHAR2(3 BYTE)              DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_ITEM_HIERARCHIES
(
  UIIH_IDSEQ       CHAR(36 BYTE)                NOT NULL,
  P_UII_IDSEQ      CHAR(36 BYTE)                NOT NULL,
  C_UII_IDSEQ      CHAR(36 BYTE)                NOT NULL,
  OCCURRENCE_SEQ   NUMBER(2)                    DEFAULT 1                     NOT NULL,
  SUPPRESS_PARENT  CHAR(1 BYTE)                 DEFAULT 'N'                       NULL,
  UIH_IDSEQ        CHAR(36 BYTE)                    NULL,
  CORE_IND         VARCHAR2(3 BYTE)             DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_ITEM_GENERATORS
(
  UIG_IDSEQ   CHAR(36 BYTE)                     NOT NULL,
  UII_IDSEQ   CHAR(36 BYTE)                         NULL,
  UIEI_IDSEQ  CHAR(36 BYTE)                         NULL,
  UIL_IDSEQ   CHAR(36 BYTE)                         NULL,
  SEQUENCE    NUMBER(2)                         DEFAULT 1                     NOT NULL,
  TEXT        VARCHAR2(4000 BYTE)               NOT NULL,
  CORE_IND    VARCHAR2(3 BYTE)                  DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_ITEMS
(
  UII_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  DISPLAY_TITLE  VARCHAR2(30 BYTE)              NOT NULL,
  ITEMS_TITLE    VARCHAR2(240 BYTE)                 NULL,
  TOOL_TIP       VARCHAR2(30 BYTE)                  NULL,
  CORE_IND       VARCHAR2(3 BYTE)               DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.AC_CSI
(
  AC_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_CSI_BACKUP
(
  AC_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_CSI_CAT_BU
(
  AC_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.ACTIONS_LOV
(
  AL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_ACTIONS_MATRIX
(
  AAM_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  SCL_NAME              VARCHAR2(30 BYTE)       NOT NULL,
  BRL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  CMSL_NAME             VARCHAR2(20 BYTE)           NULL,
  READ_ALLOWED_IND      VARCHAR2(3 BYTE)        DEFAULT 'Yes'                 NOT NULL,
  UPDATE_ALLOWED_IND    VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  DELETE_ALLOWED_IND    VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  VERSION_ALLOWED_IND   VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  CHECKOUT_ALLOWED_IND  VARCHAR2(3 BYTE)        DEFAULT 'No'                  NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_CREATED          DATE                    NOT NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  DATE_MODIFIED         DATE                        NULL
);


CREATE TABLE SBR.AC_CI_BU
(
  AC_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.AC_CONTACTS
(
  ACC_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  ORG_IDSEQ      CHAR(36 BYTE)                      NULL,
  PER_IDSEQ      CHAR(36 BYTE)                      NULL,
  AC_IDSEQ       CHAR(36 BYTE)                      NULL,
  RANK_ORDER     NUMBER(3)                          NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                      NULL,
  CONTACT_ROLE   VARCHAR2(30 BYTE)                  NULL,
  AR_IDSEQ       CHAR(36 BYTE)                      NULL
);


CREATE TABLE SBR.AC_CSI_DISEASE
(
  DISEASE   VARCHAR2(20 BYTE)                       NULL,
  DE_IDSEQ  CHAR(36 BYTE)                           NULL
);


CREATE TABLE SBR.AC_CSI_STAGING
(
  DISEASE   VARCHAR2(20 BYTE)                       NULL,
  TTU       VARCHAR2(25 BYTE)                       NULL,
  DE_IDSEQ  CHAR(36 BYTE)                           NULL
);


CREATE TABLE SBR.AC_HISTORIES
(
  ACH_IDSEQ         CHAR(36 BYTE)               NOT NULL,
  AC_IDSEQ          CHAR(36 BYTE)               NOT NULL,
  AL_NAME           VARCHAR2(20 BYTE)           NOT NULL,
  SOURCE_AC_IDSEQ   CHAR(36 BYTE)                   NULL,
  ACTION_DATE       DATE                            NULL,
  ARCHIVE_LOCATION  VARCHAR2(60 BYTE)               NULL,
  PERFORMED_BY      VARCHAR2(60 BYTE)               NULL,
  ARCHIVE_FORMAT    VARCHAR2(60 BYTE)               NULL,
  DATE_CREATED      DATE                        NOT NULL,
  CREATED_BY        VARCHAR2(30 BYTE)           NOT NULL,
  DATE_MODIFIED     DATE                            NULL,
  MODIFIED_BY       VARCHAR2(30 BYTE)               NULL
);


CREATE TABLE SBR.AC_RECS
(
  ARS_IDSEQ                    CHAR(36 BYTE)    NOT NULL,
  AC_IDSEQ                     CHAR(36 BYTE)    NOT NULL,
  OVRID_AC_IDSEQ               CHAR(36 BYTE)    NOT NULL,
  OVRID_LONG_NAME              VARCHAR2(255 BYTE)     NULL,
  OVRID_DESCRIPTION            VARCHAR2(2000 BYTE)     NULL,
  OVRID_DISPLAY_LABEL          VARCHAR2(255 BYTE)     NULL,
  OVRID_DEFAULT_RPT_COL_VAL    CHAR(1 BYTE)         NULL,
  OVRID_MORE_ROW_RESTRICTIONS  CHAR(1 BYTE)         NULL,
  OVRID_DEFAULT_COL_MEANING    CHAR(1 BYTE)         NULL,
  DATE_CREATED                 DATE             NOT NULL,
  CREATED_BY                   VARCHAR2(30 BYTE) NOT NULL,
  DATE_MODIFIED                DATE                 NULL,
  MODIFIED_BY                  VARCHAR2(30 BYTE)     NULL
);


CREATE TABLE SBR.AC_REGISTRATIONS
(
  AR_IDSEQ             CHAR(36 BYTE)            NOT NULL,
  AC_IDSEQ             CHAR(36 BYTE)            NOT NULL,
  ORG_IDSEQ            CHAR(36 BYTE)                NULL,
  SUB_IDSEQ            CHAR(36 BYTE)                NULL,
  REGIS_IDSEQ          CHAR(36 BYTE)                NULL,
  REGISTRATION_STATUS  VARCHAR2(50 BYTE)            NULL,
  UNRESOLVED_ISSUE     VARCHAR2(240 BYTE)           NULL,
  ORIGIN               VARCHAR2(240 BYTE)           NULL,
  LAST_CHANGE          VARCHAR2(2000 BYTE)          NULL,
  DATA_IDENTIFIER      VARCHAR2(240 BYTE)           NULL,
  VERSION_IDENTIFIER   VARCHAR2(240 BYTE)           NULL,
  IRDI                 VARCHAR2(1000 BYTE)          NULL,
  DATE_CREATED         DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL,
  BEGIN_DATE           DATE                         NULL,
  END_DATE             DATE                         NULL
);


CREATE TABLE SBR.FORMATS_LOV
(
  FORML_NAME     VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.GROUPS
(
  GRP_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.GROUP_RECS
(
  PARENT_GRP_NAME  VARCHAR2(20 BYTE)            NOT NULL,
  CHILD_GRP_NAME   VARCHAR2(20 BYTE)            NOT NULL,
  CREATED_BY       VARCHAR2(30 BYTE)            NOT NULL,
  DATE_CREATED     DATE                         NOT NULL,
  MODIFIED_BY      VARCHAR2(30 BYTE)                NULL,
  DATE_MODIFIED    DATE                             NULL
);


CREATE TABLE SBR.GRP_BUSINESS_ROLES
(
  GBR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  SCG_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  BRL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  ACTL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.LANGUAGES_LOV
(
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL
);


CREATE TABLE SBR.LIFECYCLES_LOV
(
  LL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.LOOKUP_LOV
(
  LOOKUP_NAME    VARCHAR2(100 BYTE)             NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  HYPERLINK      VARCHAR2(100 BYTE)                 NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(240 BYTE)             NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(240 BYTE)                 NULL,
  LOOKUP_TYPE    VARCHAR2(3 BYTE)                   NULL
);


CREATE TABLE SBR.META_TEXT
(
  MT_IDSEQ   CHAR(36 BYTE)                      NOT NULL,
  AC_IDSEQ   CHAR(36 BYTE)                      NOT NULL,
  ACTL_NAME  VARCHAR2(20 BYTE)                  NOT NULL,
  TEXT_TYPE  VARCHAR2(10 BYTE)                      NULL,
  TEXT       CLOB                                   NULL
);


CREATE TABLE SBR.META_UTIL_STATUSES
(
  UTILITY_NAME   VARCHAR2(40 BYTE)              NOT NULL,
  STATUS_CODE    VARCHAR2(3 BYTE)               NOT NULL,
  DATE_CREATED   DATE                               NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.OBJECT_CLASSES_LOV
(
  OCL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_DEFINITIONS
(
  DEFIN_IDSEQ      CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_DEFINITIONS IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_DATA_ELEMENT_CONCEPT
(
  DEC_IDSEQ        CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_DATA_ELEMENT_CONCEPT IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_DATA_ELEMENTS
(
  DE_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_DATA_ELEMENTS IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_CS_ITEMS
(
  CSI_IDSEQ        CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_CS_ITEMS IS 'temporary updatable snapshot log';


CREATE GLOBAL TEMPORARY TABLE SBR.RUPD$_CS_CSI
(
  CS_CSI_IDSEQ     CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBR.RUPD$_CS_CSI IS 'temporary updatable snapshot log';


CREATE TABLE SBR.REFERENCE_BLOBS
(
  RD_IDSEQ       CHAR(36 BYTE)                      NULL,
  NAME           VARCHAR2(350 BYTE)             NOT NULL,
  MIME_TYPE      VARCHAR2(128 BYTE)                 NULL,
  DOC_SIZE       NUMBER                             NULL,
  DAD_CHARSET    VARCHAR2(128 BYTE)                 NULL,
  LAST_UPDATED   DATE                               NULL,
  CONTENT_TYPE   VARCHAR2(128 BYTE)                 NULL,
  BLOB_CONTENT   BLOB                               NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL
);


CREATE TABLE SBR.REFERENCE_DOCUMENTS
(
  RD_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(255 BYTE)             NOT NULL,
  ORG_IDSEQ      CHAR(36 BYTE)                      NULL,
  DCTL_NAME      VARCHAR2(60 BYTE)              NOT NULL,
  AC_IDSEQ       CHAR(36 BYTE)                      NULL,
  ACH_IDSEQ      CHAR(36 BYTE)                      NULL,
  AR_IDSEQ       CHAR(36 BYTE)                      NULL,
  RDTL_NAME      VARCHAR2(20 BYTE)              DEFAULT 'TEXT'                    NULL,
  DOC_TEXT       VARCHAR2(4000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  URL            VARCHAR2(240 BYTE)                 NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL,
  DISPLAY_ORDER  NUMBER(2)                          NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                      NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                      NULL
);


CREATE TABLE SBR.REFERENCE_FORMATS_LOV
(
  RFL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.REGISTRARS
(
  REGIS_IDSEQ              CHAR(36 BYTE)        NOT NULL,
  NAME                     VARCHAR2(30 BYTE)        NULL,
  ORG_IDSEQ                CHAR(36 BYTE)        NOT NULL,
  TITLE                    VARCHAR2(240 BYTE)       NULL,
  PHONE_NUMBER             VARCHAR2(30 BYTE)        NULL,
  FAX_NUMBER               VARCHAR2(30 BYTE)        NULL,
  TELEX_NUMBER             VARCHAR2(30 BYTE)        NULL,
  MAIL_ADDRESS             VARCHAR2(240 BYTE)       NULL,
  ELECTRONIC_MAIL_ADDRESS  VARCHAR2(100 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBR.REG_STATUS_LOV
(
  REGISTRATION_STATUS  VARCHAR2(50 BYTE)        NOT NULL,
  DESCRIPTION          VARCHAR2(2000 BYTE)          NULL,
  COMMENTS             VARCHAR2(2000 BYTE)          NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_CREATED         DATE                     NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL,
  DISPLAY_ORDER        NUMBER                       NULL
);


CREATE TABLE SBR.RELATIONSHIPS_LOV
(
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.REL_USAGE_LOV
(
  RRL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.RL_RUL
(
  RRX_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  RRL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.VALUE_MEANINGS
(
  SHORT_MEANING         VARCHAR2(255 BYTE)      NOT NULL,
  DESCRIPTION           VARCHAR2(2000 BYTE)         NULL,
  COMMENTS              VARCHAR2(2000 BYTE)         NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL,
  VM_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  VERSION               NUMBER                  NOT NULL,
  VM_ID                 NUMBER                  NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)        NOT NULL,
  DELETED_IND           VARCHAR2(3 BYTE)        NOT NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBR.UI_LINK_LINK_RECS
(
  UILL_IDSEQ         CHAR(36 BYTE)              NOT NULL,
  PARENT_LINK_IDSEQ  CHAR(36 BYTE)              NOT NULL,
  CHILD_LINK_IDSEQ   CHAR(36 BYTE)              NOT NULL,
  FRAME_NAME         VARCHAR2(30 BYTE)          NOT NULL,
  CORE_IND           VARCHAR2(3 BYTE)           DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_LINKS
(
  UIL_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  NAME          VARCHAR2(30 BYTE)               NOT NULL,
  BASE_URL      VARCHAR2(240 BYTE)                  NULL,
  TARGET_FRAME  VARCHAR2(50 BYTE)                   NULL,
  PARENT_LINK   CHAR(36 BYTE)                       NULL,
  CORE_IND      VARCHAR2(3 BYTE)                DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UNIT_OF_MEASURES_LOV
(
  UOML_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  PRECISION      VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(60 BYTE)                  NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBR.UI_TYPES_LOV
(
  UITL_NAME    VARCHAR2(20 BYTE)                NOT NULL,
  DESCRIPTION  VARCHAR2(60 BYTE)                    NULL,
  COMMENTS     VARCHAR2(2000 BYTE)                  NULL,
  CORE_IND     VARCHAR2(3 BYTE)                 DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_REFERENCE
(
  UA_NAME            VARCHAR2(30 BYTE)          NOT NULL,
  BRL_NAME           VARCHAR2(20 BYTE)          NOT NULL,
  STE_REG_SUB_IDSEQ  CHAR(36 BYTE)              NOT NULL,
  SCUA_IDSEQ         CHAR(36 BYTE)                  NULL
);


CREATE TABLE SBR.UI_METADATA
(
  NAME  VARCHAR2(20 BYTE)                           NULL,
  HOST  VARCHAR2(240 BYTE)                          NULL
);


CREATE TABLE SBR.UI_LINK_FRAMESET_RECS
(
  UILF_IDSEQ  CHAR(36 BYTE)                     NOT NULL,
  UIL_IDSEQ   CHAR(36 BYTE)                     NOT NULL,
  UIFS_IDSEQ  CHAR(36 BYTE)                     NOT NULL,
  CORE_IND    VARCHAR2(3 BYTE)                  DEFAULT 'No'                  NOT NULL
);


CREATE TABLE SBR.UI_HIER_LINK_RECS
(
  UIHLR_IDSEQ   CHAR(36 BYTE)                   NOT NULL,
  UIH_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  UIL_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  UIAL_NAME     VARCHAR2(20 BYTE)                   NULL,
  ACTL_NAME     VARCHAR2(20 BYTE)                   NULL,
  TARGET_FRAME  VARCHAR2(20 BYTE)                   NULL,
  CORE_IND      VARCHAR2(3 BYTE)                DEFAULT 'No'                  NOT NULL
);


CREATE UNIQUE INDEX SBR.AL_PK ON SBR.ACTIONS_LOV
(AL_NAME);


CREATE INDEX SBR.AAM_CMSL_FK_IDX ON SBR.AC_ACTIONS_MATRIX
(CMSL_NAME);


CREATE INDEX SBR.AAM_SCL_FK_IDX ON SBR.AC_ACTIONS_MATRIX
(SCL_NAME);


CREATE INDEX SBR.AAM_ASL_FK_IDX ON SBR.AC_ACTIONS_MATRIX
(ASL_NAME);


CREATE INDEX SBR.AAM_BRL_FK_IDX ON SBR.AC_ACTIONS_MATRIX
(BRL_NAME);


CREATE UNIQUE INDEX SBR.AAX_PK ON SBR.AC_ACTIONS_MATRIX
(AAM_IDSEQ);


CREATE INDEX SBR.ACT_ORG_FK_IDX ON SBR.AC_CONTACTS
(ORG_IDSEQ);


CREATE INDEX SBR.ACT_PER_FK_IDX ON SBR.AC_CONTACTS
(PER_IDSEQ);


CREATE UNIQUE INDEX SBR.ACT_PK ON SBR.AC_CONTACTS
(ACC_IDSEQ);


CREATE UNIQUE INDEX SBR.ACC_PK ON SBR.AC_CSI
(AC_CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.ACC_UK ON SBR.AC_CSI
(AC_IDSEQ, CS_CSI_IDSEQ);


CREATE INDEX SBR.AC_CSI_CS_CSI_FK_IDX ON SBR.AC_CSI
(CS_CSI_IDSEQ);


CREATE INDEX SBR.AC_CSI_AC_FK_IDX ON SBR.AC_CSI
(AC_IDSEQ);


CREATE INDEX SBR.SOURCE_AC_FK_IDX ON SBR.AC_HISTORIES
(SOURCE_AC_IDSEQ);


CREATE INDEX SBR.AH_AL_FK_IDX ON SBR.AC_HISTORIES
(AL_NAME);


CREATE UNIQUE INDEX SBR.AH_PK ON SBR.AC_HISTORIES
(ACH_IDSEQ);


CREATE INDEX SBR.ARS_OVRID_AC_FK_IDX ON SBR.AC_RECS
(OVRID_AC_IDSEQ);


CREATE INDEX SBR.ARS_AC_FK_IDX ON SBR.AC_RECS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.ARS_PK ON SBR.AC_RECS
(ARS_IDSEQ);


CREATE UNIQUE INDEX SBR.ARS_UK ON SBR.AC_RECS
(AC_IDSEQ, OVRID_AC_IDSEQ);


CREATE INDEX SBR.AR_RSL_FK_IDX ON SBR.AC_REGISTRATIONS
(REGISTRATION_STATUS);


CREATE INDEX SBR.AR_REGIS_FK_IDX ON SBR.AC_REGISTRATIONS
(REGIS_IDSEQ);


CREATE INDEX SBR.AR_ORG_FK_IDX ON SBR.AC_REGISTRATIONS
(ORG_IDSEQ);


CREATE INDEX SBR.AR_AC_FK_IDX ON SBR.AC_REGISTRATIONS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.CRA_PK ON SBR.AC_REGISTRATIONS
(AR_IDSEQ);


CREATE UNIQUE INDEX SBR.CRA_UK ON SBR.AC_REGISTRATIONS
(AC_IDSEQ, ORG_IDSEQ, SUB_IDSEQ);


CREATE UNIQUE INDEX SBR.ASL_PK ON SBR.AC_STATUS_LOV
(ASL_NAME);


CREATE INDEX SBR.ACSUB_AC_FK_IDX ON SBR.AC_SUBJECTS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.ACSUB_PK ON SBR.AC_SUBJECTS
(ACSUB_IDSEQ);


CREATE UNIQUE INDEX SBR.ACSUB_UK ON SBR.AC_SUBJECTS
(AC_IDSEQ, SUBJ_IDSEQ);


CREATE UNIQUE INDEX SBR.ACTL_PK ON SBR.AC_TYPES_LOV
(ACTL_NAME);


CREATE INDEX SBR.AWB_BRL_FK_IDX ON SBR.AC_WF_BUSINESS_ROLES
(BRL_NAME);


CREATE INDEX SBR.AWB_AWM_FK_IDX ON SBR.AC_WF_BUSINESS_ROLES
(AWR_IDSEQ);


CREATE UNIQUE INDEX SBR.AWB_PK ON SBR.AC_WF_BUSINESS_ROLES
(AWB_IDSEQ);


CREATE INDEX SBR.AWR_SCL_FK_IDX ON SBR.AC_WF_RULES
(SCL_NAME);


CREATE INDEX SBR.AWR_ASL_TO_FK_IDX ON SBR.AC_WF_RULES
(TO_ASL_NAME);


CREATE INDEX SBR.AWR_ASL_FROM_FK_IDX ON SBR.AC_WF_RULES
(FROM_ASL_NAME);


CREATE UNIQUE INDEX SBR.AWM_PK ON SBR.AC_WF_RULES
(AWR_IDSEQ);


CREATE UNIQUE INDEX SBR.AWM_UK ON SBR.AC_WF_RULES
(SCL_NAME, FROM_ASL_NAME, TO_ASL_NAME);


CREATE UNIQUE INDEX SBR.ATL_PK ON SBR.ADDR_TYPES_LOV
(ATL_NAME);


CREATE INDEX SBR.AC_ASL_FK_IDX ON SBR.ADMINISTERED_COMPONENTS
(ASL_NAME);


CREATE BITMAP INDEX SBR.AC_ACTL_NAME_BINDX ON SBR.ADMINISTERED_COMPONENTS
(ACTL_NAME);


CREATE INDEX SBR.PUBLIC_ID_IDX ON SBR.ADMINISTERED_COMPONENTS
(PUBLIC_ID);


CREATE INDEX SBR.UPPERCASE_AC_IDX ON SBR.ADMINISTERED_COMPONENTS
(PREFERRED_NAME);


CREATE BITMAP INDEX SBR.AC_VERSION_BINDX ON SBR.ADMINISTERED_COMPONENTS
(VERSION);


CREATE INDEX SBR.AC_STEWA_FK_IDX ON SBR.ADMINISTERED_COMPONENTS
(STEWA_IDSEQ);


CREATE BITMAP INDEX SBR.AC_LATEST_BINDX ON SBR.ADMINISTERED_COMPONENTS
(LATEST_VERSION_IND);


CREATE INDEX SBR.AC_INDEX_UK ON SBR.ADMINISTERED_COMPONENTS
(PREFERRED_NAME, VERSION, CONTE_IDSEQ);


CREATE BITMAP INDEX SBR.AC_DELETED_BINDX ON SBR.ADMINISTERED_COMPONENTS
(DELETED_IND);


CREATE INDEX SBR.AC_CSL_FK_IDX ON SBR.ADMINISTERED_COMPONENTS
(CMSL_NAME);


CREATE INDEX SBR.AC_CONTE_FK_IDX ON SBR.ADMINISTERED_COMPONENTS
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.AC_PK ON SBR.ADMINISTERED_COMPONENTS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.AC_UK ON SBR.ADMINISTERED_COMPONENTS
(VERSION, PREFERRED_NAME, CONTE_IDSEQ, ACTL_NAME);


CREATE UNIQUE INDEX SBR.ARV_PK ON SBR.ADVANCE_RPT_LOV
(NAME);


CREATE UNIQUE INDEX SBR.ACTV_PK ON SBR.APP_COMPONENT_TYPES_LOV
(APCTL_NAME);


CREATE INDEX SBR.AGT_ARL_FK_IDX ON SBR.APP_GRANTS
(ROLE_NAME);


CREATE INDEX SBR.AGT_APV_FK_IDX ON SBR.APP_GRANTS
(PRIV_NAME);


CREATE INDEX SBR.AGT_AOT_FK_IDX ON SBR.APP_GRANTS
(APPOBJ_IDSEQ);


CREATE UNIQUE INDEX SBR.AGT_PK ON SBR.APP_GRANTS
(GRANT_IDSEQ);


CREATE UNIQUE INDEX SBR.AGT_UK ON SBR.APP_GRANTS
(APPOBJ_IDSEQ, ROLE_NAME, PRIV_NAME);


CREATE INDEX SBR.AOT_DVN_FK_IDX ON SBR.APP_OBJECTS
(APP_VERSION);


CREATE INDEX SBR.AOT_APCTL_FK_IDX ON SBR.APP_OBJECTS
(APCTL_NAME);


CREATE INDEX SBR.AOT_AOV_FK_IDX ON SBR.APP_OBJECTS
(OBJ_NAME);


CREATE UNIQUE INDEX SBR.AOT_PK ON SBR.APP_OBJECTS
(APPOBJ_IDSEQ);


CREATE UNIQUE INDEX SBR.AOT_UK ON SBR.APP_OBJECTS
(NAME, OBJ_NAME);


CREATE UNIQUE INDEX SBR.AOV_PK ON SBR.APP_OBJECTS_LOV
(OBJ_NAME);


CREATE UNIQUE INDEX SBR.AGV_PK ON SBR.APP_PRIV_LOV
(PRIV_NAME);


CREATE UNIQUE INDEX SBR.ARE_PK ON SBR.APP_ROLES_LOV
(ROLE_NAME);


CREATE UNIQUE INDEX SBR.DVN_PK ON SBR.APP_VERSIONS
(APP_VERSION);


CREATE UNIQUE INDEX SBR.BR_PK ON SBR.BUSINESS_ROLES_LOV
(BRL_NAME);


CREATE INDEX SBR.CV_VMV_FK_IDX ON SBR.CD_VMS
(SHORT_MEANING);


CREATE INDEX SBR.CV_CD_FK_IDX ON SBR.CD_VMS
(CD_IDSEQ);


CREATE UNIQUE INDEX SBR.CDV_PK ON SBR.CD_VMS
(CV_IDSEQ);


CREATE UNIQUE INDEX SBR.CDV_UK ON SBR.CD_VMS
(CD_IDSEQ, VM_IDSEQ);


CREATE UNIQUE INDEX SBR.CSV_PK ON SBR.CHARACTER_SET_LOV
(CHAR_SET_NAME);


CREATE INDEX SBR.CS_CSTL_FK_IDX ON SBR.CLASSIFICATION_SCHEMES
(CSTL_NAME);


CREATE INDEX SBR.CS_CONTE_FK_IDX ON SBR.CLASSIFICATION_SCHEMES
(CONTE_IDSEQ);


CREATE INDEX SBR.CS_CMSL_FK_IDX ON SBR.CLASSIFICATION_SCHEMES
(CMSL_NAME);


CREATE INDEX SBR.CS_ASL_FK_IDX ON SBR.CLASSIFICATION_SCHEMES
(ASL_NAME);


CREATE INDEX SBR.UPPERCASE_CS_IDX ON SBR.CLASSIFICATION_SCHEMES
(PREFERRED_NAME);


CREATE UNIQUE INDEX SBR.CS_PK ON SBR.CLASSIFICATION_SCHEMES
(CS_IDSEQ);


CREATE UNIQUE INDEX SBR.CS_UK ON SBR.CLASSIFICATION_SCHEMES
(PREFERRED_NAME, VERSION, CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.CSL_PK ON SBR.CM_STATES_LOV
(CMSL_NAME);


CREATE UNIQUE INDEX SBR.COTL_PK ON SBR.COMM_TYPES_LOV
(CTL_NAME);


CREATE INDEX SBR.CDT_CRV_FK_IDX ON SBR.COMPLEX_DATA_ELEMENTS
(CRTL_NAME);


CREATE UNIQUE INDEX SBR.CDT_PK ON SBR.COMPLEX_DATA_ELEMENTS
(P_DE_IDSEQ);


CREATE INDEX SBR.CDE_CDT_FK_IDX ON SBR.COMPLEX_DE_RELATIONSHIPS
(P_DE_IDSEQ);


CREATE INDEX SBR.CDE_DE_FK_IDX ON SBR.COMPLEX_DE_RELATIONSHIPS
(C_DE_IDSEQ);


CREATE UNIQUE INDEX SBR.CDR_PK ON SBR.COMPLEX_DE_RELATIONSHIPS
(CDR_IDSEQ);


CREATE UNIQUE INDEX SBR.CDE_UK ON SBR.COMPLEX_DE_RELATIONSHIPS
(C_DE_IDSEQ, P_DE_IDSEQ);


CREATE UNIQUE INDEX SBR.CRV_PK ON SBR.COMPLEX_REP_TYPE_LOV
(CRTL_NAME);


CREATE INDEX SBR.UPPERCASE_CD_IDX ON SBR.CONCEPTUAL_DOMAINS
(PREFERRED_NAME);


CREATE INDEX SBR.CD_CONTE_FK_IDX ON SBR.CONCEPTUAL_DOMAINS
(CONTE_IDSEQ);


CREATE INDEX SBR.CD_ASL_FK_IDX ON SBR.CONCEPTUAL_DOMAINS
(ASL_NAME);


CREATE UNIQUE INDEX SBR.CD_PK ON SBR.CONCEPTUAL_DOMAINS
(CD_IDSEQ);


CREATE UNIQUE INDEX SBR.CD_UK ON SBR.CONCEPTUAL_DOMAINS
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE INDEX SBR.CADDR_ATL_FK_IDX ON SBR.CONTACT_ADDRESSES
(ATL_NAME);


CREATE INDEX SBR.CADDR_ORG_FK_IDX ON SBR.CONTACT_ADDRESSES
(ORG_IDSEQ);


CREATE INDEX SBR.CADDR_PER_FK_IDX ON SBR.CONTACT_ADDRESSES
(PER_IDSEQ);


CREATE UNIQUE INDEX SBR.CADDR_PK ON SBR.CONTACT_ADDRESSES
(CADDR_IDSEQ);


CREATE INDEX SBR.CCOMM_CTL_FK_IDX ON SBR.CONTACT_COMMS
(CTL_NAME);


CREATE INDEX SBR.CCOMM_ORG_FK_IDX ON SBR.CONTACT_COMMS
(ORG_IDSEQ);


CREATE INDEX SBR.CCOMM_PER_FK_IDX ON SBR.CONTACT_COMMS
(PER_IDSEQ);


CREATE UNIQUE INDEX SBR.CCOMM_PK ON SBR.CONTACT_COMMS
(CCOMM_IDSEQ);


CREATE INDEX SBR.CONTE_PAL_FK_IDX ON SBR.CONTEXTS
(PAL_NAME);


CREATE INDEX SBR.CONTE_LL_FK_IDX ON SBR.CONTEXTS
(LL_NAME);


CREATE UNIQUE INDEX SBR.CONTE_PK ON SBR.CONTEXTS
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.CONTE_UK ON SBR.CONTEXTS
(NAME, VERSION);


CREATE INDEX SBR.C_CSIR_CSI_FK_IDX ON SBR.CSI_RECS
(C_CSI_IDSEQ);


CREATE INDEX SBR.CSIR_RL_FK_IDX ON SBR.CSI_RECS
(RL_NAME);


CREATE INDEX SBR.P_CSIR_CSI_FK_IDX ON SBR.CSI_RECS
(P_CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.CSX_PK ON SBR.CSI_RECS
(CSI_REC_IDSEQ);


CREATE UNIQUE INDEX SBR.CSITL_PK ON SBR.CSI_TYPES_LOV
(CSITL_NAME);


CREATE INDEX SBR.P_CS_CSI_FK_IDX ON SBR.CS_CSI
(P_CS_CSI_IDSEQ);


CREATE INDEX SBR.CS_CSI_CS_FK_IDX ON SBR.CS_CSI
(CS_IDSEQ);


CREATE INDEX SBR.CS_CSI_CSI_FK_IDX ON SBR.CS_CSI
(CSI_IDSEQ);


CREATE INDEX SBR.LINK_CS_CSI_FK_IDX ON SBR.CS_CSI
(LINK_CS_CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.CS_CSI_PK ON SBR.CS_CSI
(CS_CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.CS_CSI_UK ON SBR.CS_CSI
(CS_IDSEQ, CSI_IDSEQ, P_CS_CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.CSI_CSI_2_UK ON SBR.CS_ITEMS
(CSITL_NAME, CSI_NAME);


CREATE INDEX SBR.CSI_CSITL_FK_IDX ON SBR.CS_ITEMS
(CSITL_NAME);


CREATE INDEX SBR.CSI_CONTE_FK_IDX ON SBR.CS_ITEMS
(CONTE_IDSEQ);


CREATE INDEX SBR.CSI_ASL_FK_IDX ON SBR.CS_ITEMS
(ASL_NAME);


CREATE UNIQUE INDEX SBR.CSI_PK ON SBR.CS_ITEMS
(CSI_IDSEQ);


CREATE UNIQUE INDEX SBR.CSI_UK ON SBR.CS_ITEMS
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE UNIQUE INDEX SBR.CSI2_UK ON SBR.CS_ITEMS
(CSITL_NAME, LONG_NAME, CONTE_IDSEQ, VERSION);


CREATE INDEX SBR.C_CSR_CS_FK_IDX ON SBR.CS_RECS
(C_CS_IDSEQ);


CREATE INDEX SBR.CSR_RL_FK_IDX ON SBR.CS_RECS
(RL_NAME);


CREATE INDEX SBR.P_CSR_CS_FK_IDX ON SBR.CS_RECS
(P_CS_IDSEQ);


CREATE UNIQUE INDEX SBR.CC2R_PK ON SBR.CS_RECS
(CS_REC_IDSEQ);


CREATE UNIQUE INDEX SBR.CTL_PK ON SBR.CS_TYPES_LOV
(CSTL_NAME);


CREATE UNIQUE INDEX SBR.DTL_PK ON SBR.DATATYPES_LOV
(DTL_NAME);


CREATE INDEX SBR.PREFERRED_DEFINITION_IDX ON SBR.DATA_ELEMENTS
(PREFERRED_DEFINITION);


CREATE INDEX SBR.UPPERCASE_LN_DE_IDX ON SBR.DATA_ELEMENTS
(UPPER("LONG_NAME"));


CREATE INDEX SBR.LONG_NAME_IDX ON SBR.DATA_ELEMENTS
(LONG_NAME);


CREATE INDEX SBR.DE_VD_FK_IDX ON SBR.DATA_ELEMENTS
(VD_IDSEQ);


CREATE BITMAP INDEX SBR.DE_LATEST_BINDX ON SBR.DATA_ELEMENTS
(LATEST_VERSION_IND);


CREATE BITMAP INDEX SBR.DE_DELETED_BINDX ON SBR.DATA_ELEMENTS
(DELETED_IND);


CREATE INDEX SBR.DE_DEC_VD_IDX ON SBR.DATA_ELEMENTS
(VD_IDSEQ, DEC_IDSEQ);


CREATE INDEX SBR.DE_DEC_FK_IDX ON SBR.DATA_ELEMENTS
(DEC_IDSEQ);


CREATE INDEX SBR.DE_CONTE_FK_IDX ON SBR.DATA_ELEMENTS
(CONTE_IDSEQ);


CREATE INDEX SBR.DE_ASL_FK_IDX ON SBR.DATA_ELEMENTS
(ASL_NAME);


CREATE INDEX SBR.UPPERCASE_DE_IDX ON SBR.DATA_ELEMENTS
(UPPER("PREFERRED_NAME"));


CREATE INDEX SBR.CDE_ID_IDX ON SBR.DATA_ELEMENTS
(CDE_ID);


CREATE INDEX SBR.CDE_ID_VERSION_IDX ON SBR.DATA_ELEMENTS
(CDE_ID, VERSION);


CREATE UNIQUE INDEX SBR.DE_PK ON SBR.DATA_ELEMENTS
(DE_IDSEQ);


CREATE UNIQUE INDEX SBR.DE_UK ON SBR.DATA_ELEMENTS
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE INDEX SBR.DEC_PRO_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(PROP_IDSEQ);


CREATE INDEX SBR.DEC_PROP_QUAL_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(PROPERTY_QUALIFIER);


CREATE INDEX SBR.DEC_PROPE_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(PROPL_NAME);


CREATE INDEX SBR.DEC_OC_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(OCL_NAME);


CREATE INDEX SBR.DEC_OCT_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(OC_IDSEQ);


CREATE BITMAP INDEX SBR.DEC_LATEST_BINDX ON SBR.DATA_ELEMENT_CONCEPTS
(LATEST_VERSION_IND);


CREATE BITMAP INDEX SBR.DEC_DELETED_BINDX ON SBR.DATA_ELEMENT_CONCEPTS
(DELETED_IND);


CREATE INDEX SBR.DEC_CONTE_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(CONTE_IDSEQ);


CREATE INDEX SBR.DEC_CD_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(CD_IDSEQ);


CREATE INDEX SBR.DEC_ASL_FK_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(ASL_NAME);


CREATE INDEX SBR.UPPERCASE_DEC_IDX ON SBR.DATA_ELEMENT_CONCEPTS
(PREFERRED_NAME);


CREATE UNIQUE INDEX SBR.DEC_PK ON SBR.DATA_ELEMENT_CONCEPTS
(DEC_IDSEQ);


CREATE UNIQUE INDEX SBR.DEC_UK ON SBR.DATA_ELEMENT_CONCEPTS
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE INDEX SBR.DRC_RL_FK_IDX ON SBR.DEC_RECS
(RL_NAME);


CREATE INDEX SBR.DRC_P_DEC_FK_IDX ON SBR.DEC_RECS
(P_DEC_IDSEQ);


CREATE INDEX SBR.DRC_C_DEC_FK_IDX ON SBR.DEC_RECS
(C_DEC_IDSEQ);


CREATE UNIQUE INDEX SBR.DRC_PK ON SBR.DEC_RECS
(DEC_REC_IDSEQ);


CREATE INDEX SBR.DEFIN_LAE_FK_IDX ON SBR.DEFINITIONS
(LAE_NAME);


CREATE INDEX SBR.DEFIN_CONTE_FK_IDX ON SBR.DEFINITIONS
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.DEFIN_PK ON SBR.DEFINITIONS
(DEFIN_IDSEQ);


CREATE INDEX SBR.DEFIN_AC_FK_IDX ON SBR.DEFINITIONS
(AC_IDSEQ);


CREATE INDEX SBR.DESIG_LAE_FK_IDX ON SBR.DESIGNATIONS
(LAE_NAME);


CREATE INDEX SBR.DESIG_DT_FK_IDX ON SBR.DESIGNATIONS
(DETL_NAME);


CREATE INDEX SBR.DESIG_CONTE_FK_IDX ON SBR.DESIGNATIONS
(CONTE_IDSEQ);


CREATE INDEX SBR.DESIG_AC_FK_IDX ON SBR.DESIGNATIONS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.DESIG_PK ON SBR.DESIGNATIONS
(DESIG_IDSEQ);


CREATE UNIQUE INDEX SBR.DESIG_UK ON SBR.DESIGNATIONS
(AC_IDSEQ, NAME, DETL_NAME, CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.DT_PK ON SBR.DESIGNATION_TYPES_LOV
(DETL_NAME);


CREATE INDEX SBR.DER_RL_FK_IDX ON SBR.DE_RECS
(RL_NAME);


CREATE INDEX SBR.DER_P_DE_FK_IDX ON SBR.DE_RECS
(P_DE_IDSEQ);


CREATE INDEX SBR.DER_C_DE_FK_IDX ON SBR.DE_RECS
(C_DE_IDSEQ);


CREATE UNIQUE INDEX SBR.DER_PK ON SBR.DE_RECS
(DE_REC_IDSEQ);


CREATE UNIQUE INDEX SBR.DER_UK ON SBR.DE_RECS
(P_DE_IDSEQ, C_DE_IDSEQ);


CREATE INDEX SBR.DCTL_ACTLV_FK_IDX ON SBR.DOCUMENT_TYPES_LOV
(ACTL_NAME);


CREATE UNIQUE INDEX SBR.DCTL_PK ON SBR.DOCUMENT_TYPES_LOV
(DCTL_NAME);


CREATE UNIQUE INDEX SBR.FORML_PK ON SBR.FORMATS_LOV
(FORML_NAME);


CREATE UNIQUE INDEX SBR.GRP_PK ON SBR.GROUPS
(GRP_NAME);


CREATE UNIQUE INDEX SBR.GRCS_PK ON SBR.GROUP_RECS
(PARENT_GRP_NAME, CHILD_GRP_NAME);


CREATE UNIQUE INDEX SBR.GBR_PK ON SBR.GRP_BUSINESS_ROLES
(GBR_IDSEQ);


CREATE UNIQUE INDEX SBR.GBR_UK ON SBR.GRP_BUSINESS_ROLES
(BRL_NAME, SCG_IDSEQ, ACTL_NAME);


CREATE UNIQUE INDEX SBR.LAE_PK ON SBR.LANGUAGES_LOV
(NAME);


CREATE UNIQUE INDEX SBR.LL_PK ON SBR.LIFECYCLES_LOV
(LL_NAME);


CREATE UNIQUE INDEX SBR.LLV_PK ON SBR.LOOKUP_LOV
(LOOKUP_NAME);


CREATE UNIQUE INDEX SBR.MT_PK ON SBR.META_TEXT
(MT_IDSEQ);


CREATE INDEX SBR.MT_AC_TT_BINDX ON SBR.META_TEXT
(AC_IDSEQ, TEXT_TYPE);


CREATE UNIQUE INDEX SBR.UTLSTAT_PK ON SBR.META_UTIL_STATUSES
(UTILITY_NAME);


CREATE UNIQUE INDEX SBR.OC_PK ON SBR.OBJECT_CLASSES_LOV
(OCL_NAME);


CREATE UNIQUE INDEX SBR.ORGAN_PK ON SBR.ORGANIZATIONS
(ORG_IDSEQ);


CREATE UNIQUE INDEX SBR.ORG_UK ON SBR.ORGANIZATIONS
(NAME);


CREATE INDEX SBR.PV_VMV_FK_IDX ON SBR.PERMISSIBLE_VALUES
(SHORT_MEANING);


CREATE UNIQUE INDEX SBR.PV_PK ON SBR.PERMISSIBLE_VALUES
(PV_IDSEQ);


CREATE UNIQUE INDEX SBR.PV_UK ON SBR.PERMISSIBLE_VALUES
(VM_IDSEQ, VALUE);


CREATE UNIQUE INDEX SBR.PER_PK ON SBR.PERSONS
(PER_IDSEQ);


CREATE UNIQUE INDEX SBR.PRM_PK ON SBR.PROGRAMS
(PA_IDSEQ);


CREATE UNIQUE INDEX SBR.PRM_UK ON SBR.PROGRAMS
(NAME);


CREATE UNIQUE INDEX SBR.PAL_PK ON SBR.PROGRAM_AREAS_LOV
(PAL_NAME);


CREATE UNIQUE INDEX SBR.PROPE_PK ON SBR.PROPERTIES_LOV
(PROPL_NAME);


CREATE INDEX SBR.RB_RD_FK_IDX ON SBR.REFERENCE_BLOBS
(RD_IDSEQ);


CREATE UNIQUE INDEX SBR.RB_PK ON SBR.REFERENCE_BLOBS
(NAME);


CREATE INDEX SBR.RD_DCTL_FK_IDX ON SBR.REFERENCE_DOCUMENTS
(DCTL_NAME);


CREATE INDEX SBR.RD_AC_FK_IDX ON SBR.REFERENCE_DOCUMENTS
(AC_IDSEQ);


CREATE UNIQUE INDEX SBR.RD_PK ON SBR.REFERENCE_DOCUMENTS
(RD_IDSEQ);


CREATE UNIQUE INDEX SBR.RD_UK ON SBR.REFERENCE_DOCUMENTS
(AC_IDSEQ, NAME, DCTL_NAME);


CREATE UNIQUE INDEX SBR.RFL_PK ON SBR.REFERENCE_FORMATS_LOV
(RFL_NAME);


CREATE UNIQUE INDEX SBR.REGIS_PK ON SBR.REGISTRARS
(REGIS_IDSEQ);


CREATE INDEX SBR.REGIS_ORGAN_FK_IDX ON SBR.REGISTRARS
(ORG_IDSEQ);


CREATE UNIQUE INDEX SBR.RSL_PK ON SBR.REG_STATUS_LOV
(REGISTRATION_STATUS);


CREATE UNIQUE INDEX SBR.RL_PK ON SBR.RELATIONSHIPS_LOV
(RL_NAME);


CREATE UNIQUE INDEX SBR.RUL_PK ON SBR.REL_USAGE_LOV
(RRL_NAME);


CREATE INDEX SBR.RRX_RUL_FK_IDX ON SBR.RL_RUL
(RRL_NAME);


CREATE INDEX SBR.RRX_RL_FK_IDX ON SBR.RL_RUL
(RL_NAME);


CREATE UNIQUE INDEX SBR.RRX_PK ON SBR.RL_RUL
(RRX_IDSEQ);


CREATE UNIQUE INDEX SBR.RRX_UK ON SBR.RL_RUL
(RL_NAME, RRL_NAME);


CREATE UNIQUE INDEX SBR.RULE_PK ON SBR.RULES_LOV
(RULE_IDSEQ);


CREATE UNIQUE INDEX SBR.SCC_PK ON SBR.SC_CONTEXTS
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.SCC_UK ON SBR.SC_CONTEXTS
(SCL_NAME);


CREATE INDEX SBR.SCG_SCL_FK_IDX ON SBR.SC_GROUPS
(SCL_NAME);


CREATE INDEX SBR.SCG_GRP_FK_IDX ON SBR.SC_GROUPS
(GRP_NAME);


CREATE UNIQUE INDEX SBR.SCG_PK ON SBR.SC_GROUPS
(SCG_IDSEQ);


CREATE UNIQUE INDEX SBR.SCG_UK ON SBR.SC_GROUPS
(GRP_NAME, SCL_NAME);


CREATE INDEX SBR.SCUA_UA_FK_IDX ON SBR.SC_USER_ACCOUNTS
(UA_NAME);


CREATE INDEX SBR.SCUA_SCL_FK_IDX ON SBR.SC_USER_ACCOUNTS
(SCL_NAME);


CREATE UNIQUE INDEX SBR.SCUA_PK ON SBR.SC_USER_ACCOUNTS
(SCUA_IDSEQ);


CREATE UNIQUE INDEX SBR.SCUA_UK ON SBR.SC_USER_ACCOUNTS
(UA_NAME, SCL_NAME);


CREATE UNIQUE INDEX SBR.SC_PK ON SBR.SECURITY_CONTEXTS_LOV
(SCL_NAME);


CREATE INDEX SBR.STEWA_ORGAN_FK_IDX ON SBR.STEWARDS
(ORG_IDSEQ);


CREATE UNIQUE INDEX SBR.STEWA_PK ON SBR.STEWARDS
(STEWA_IDSEQ);


CREATE INDEX SBR.SUBL_SUBL_FK_IDX ON SBR.SUBJECTS
(P_SUBJ_IDSEQ);


CREATE UNIQUE INDEX SBR.SUBJ_PK ON SBR.SUBJECTS
(SUBJ_IDSEQ);


CREATE INDEX SBR.SUBMI_ORG_FK_IDX ON SBR.SUBMITTERS
(ORG_IDSEQ);


CREATE UNIQUE INDEX SBR.SUBMI_PK ON SBR.SUBMITTERS
(SUB_IDSEQ);


CREATE UNIQUE INDEX SBR.UIHLR_PK ON SBR.UI_HIER_LINK_RECS
(UIHLR_IDSEQ);


CREATE UNIQUE INDEX SBR.UII_PK ON SBR.UI_ITEMS
(UII_IDSEQ);


CREATE UNIQUE INDEX SBR.UIM_UK ON SBR.UI_ITEMS
(DISPLAY_TITLE, TOOL_TIP);


CREATE UNIQUE INDEX SBR.UIG_PK ON SBR.UI_ITEM_GENERATORS
(UIG_IDSEQ);


CREATE UNIQUE INDEX SBR.UIIH_PK ON SBR.UI_ITEM_HIERARCHIES
(UIIH_IDSEQ);


CREATE UNIQUE INDEX SBR.UIIIM_PK ON SBR.UI_ITEM_IMAGES
(UIIIM_IDSEQ);


CREATE UNIQUE INDEX SBR.UIILR_PK ON SBR.UI_ITEM_LINK_RECS
(UIILR_IDSEQ);


CREATE UNIQUE INDEX SBR.UIL_PK ON SBR.UI_LINKS
(UIL_IDSEQ);


CREATE UNIQUE INDEX SBR.ULK_UK ON SBR.UI_LINKS
(NAME, BASE_URL);


CREATE UNIQUE INDEX SBR.UILFR_PK ON SBR.UI_LINK_FRAMESET_RECS
(UILF_IDSEQ);


CREATE UNIQUE INDEX SBR.UILL_PK ON SBR.UI_LINK_LINK_RECS
(UILL_IDSEQ);


CREATE UNIQUE INDEX SBR.UILL_UK ON SBR.UI_LINK_LINK_RECS
(PARENT_LINK_IDSEQ, CHILD_LINK_IDSEQ);


CREATE UNIQUE INDEX SBR.UILP_PK ON SBR.UI_LINK_PARAMS
(UILP_IDSEQ);


CREATE UNIQUE INDEX SBR.URE_PK ON SBR.UI_REFERENCE
(UA_NAME, BRL_NAME);


CREATE UNIQUE INDEX SBR.UIT_PK ON SBR.UI_TYPES_LOV
(UITL_NAME);


CREATE UNIQUE INDEX SBR.UOML_PK ON SBR.UNIT_OF_MEASURES_LOV
(UOML_NAME);


CREATE UNIQUE INDEX SBR.UA_PK ON SBR.USER_ACCOUNTS
(UA_NAME);


CREATE INDEX SBR.UGP_UA_FK_IDX ON SBR.USER_GROUPS
(UA_NAME);


CREATE INDEX SBR.UGP_GRP_FK_IDX ON SBR.USER_GROUPS
(GRP_NAME);


CREATE UNIQUE INDEX SBR.UGP_PK ON SBR.USER_GROUPS
(UA_NAME, GRP_NAME);


CREATE INDEX SBR.VD_UOML_FK_IDX ON SBR.VALUE_DOMAINS
(UOML_NAME);


CREATE BITMAP INDEX SBR.VD_LATEST_BINDX ON SBR.VALUE_DOMAINS
(LATEST_VERSION_IND);


CREATE INDEX SBR.VD_FORML_FK_IDX ON SBR.VALUE_DOMAINS
(FORML_NAME);


CREATE INDEX SBR.VD_DTL_FK_IDX ON SBR.VALUE_DOMAINS
(DTL_NAME);


CREATE BITMAP INDEX SBR.VD_DELETED_BINDX ON SBR.VALUE_DOMAINS
(DELETED_IND);


CREATE INDEX SBR.VD_CSV_FK_IDX ON SBR.VALUE_DOMAINS
(CHAR_SET_NAME);


CREATE INDEX SBR.VD_CONTE_FK_IDX ON SBR.VALUE_DOMAINS
(CONTE_IDSEQ);


CREATE INDEX SBR.VD_CD_FK_IDX ON SBR.VALUE_DOMAINS
(CD_IDSEQ);


CREATE INDEX SBR.VD_ASL_FK_IDX ON SBR.VALUE_DOMAINS
(ASL_NAME);


CREATE INDEX SBR.UPPERCASE_VD_IDX ON SBR.VALUE_DOMAINS
(PREFERRED_NAME);


CREATE UNIQUE INDEX SBR.VD_PK ON SBR.VALUE_DOMAINS
(VD_IDSEQ);


CREATE UNIQUE INDEX SBR.VD_UK ON SBR.VALUE_DOMAINS
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.VMV_PK ON SBR.VALUE_MEANINGS
(VM_IDSEQ);


CREATE UNIQUE INDEX SBR.VMV_UK ON SBR.VALUE_MEANINGS
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE INDEX SBR.VP_CONTE_FK_IDX ON SBR.VD_PVS
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBR.VP_PK ON SBR.VD_PVS
(VP_IDSEQ);


CREATE UNIQUE INDEX SBR.VP_UK ON SBR.VD_PVS
(VD_IDSEQ, CONTE_IDSEQ, PV_IDSEQ);


CREATE INDEX SBR.VP_VD_FK_IDX ON SBR.VD_PVS
(VD_IDSEQ);


CREATE INDEX SBR.VP_PV_FK_IDX ON SBR.VD_PVS
(PV_IDSEQ);


CREATE INDEX SBR.VPR_VDV_FK_IDX ON SBR.VD_PV_RECS
(P_VP_IDSEQ);


CREATE INDEX SBR.VPR_VDV_FK2_IDX ON SBR.VD_PV_RECS
(C_VP_IDSEQ);


CREATE INDEX SBR.VPR_RL_FK_IDX ON SBR.VD_PV_RECS
(RL_NAME);


CREATE UNIQUE INDEX SBR.VPR_PK ON SBR.VD_PV_RECS
(VPR_IDSEQ);


CREATE UNIQUE INDEX SBR.VPR_UK ON SBR.VD_PV_RECS
(RL_NAME, P_VP_IDSEQ, C_VP_IDSEQ);


CREATE INDEX SBR.VR_RL_FK_IDX ON SBR.VD_RECS
(RL_NAME);


CREATE INDEX SBR.VR_P_VD_FK_IDX ON SBR.VD_RECS
(P_VD_IDSEQ);


CREATE INDEX SBR.VR_C_VD_FK_IDX ON SBR.VD_RECS
(C_VD_IDSEQ);


CREATE UNIQUE INDEX SBR.VR_PK ON SBR.VD_RECS
(VD_REC_IDSEQ);


CREATE UNIQUE INDEX SBR.VR_UK ON SBR.VD_RECS
(RL_NAME, P_VD_IDSEQ, C_VD_IDSEQ);


CREATE UNIQUE INDEX SBR.WSGSR_SESS_PK ON SBR.WSGSR_SESSIONS
(CLIENT_ID);


CREATE UNIQUE INDEX SBR.WSGSR_USERDATA_PK ON SBR.WSGSR_USERDATA
(USER_NAME);


CREATE SYNONYM SBREXT.ACTIONS_LOV FOR SBR.ACTIONS_LOV;


CREATE SYNONYM SBREXT.AC_ACTIONS_MATRIX FOR SBR.AC_ACTIONS_MATRIX;


CREATE SYNONYM SBREXT.AC_CI_BU FOR SBR.AC_CI_BU;


CREATE SYNONYM SBREXT.AC_CONTACTS FOR SBR.AC_CONTACTS;


CREATE SYNONYM SBREXT.AC_CSI FOR SBR.AC_CSI;


CREATE SYNONYM SBREXT.AC_CSI_BACKUP FOR SBR.AC_CSI_BACKUP;


CREATE SYNONYM SBREXT.AC_CSI_CAT_BU FOR SBR.AC_CSI_CAT_BU;


CREATE SYNONYM SBREXT.AC_CSI_DISEASE FOR SBR.AC_CSI_DISEASE;


CREATE SYNONYM SBREXT.AC_CSI_STAGING FOR SBR.AC_CSI_STAGING;


CREATE SYNONYM SBREXT.AC_HISTORIES FOR SBR.AC_HISTORIES;


CREATE SYNONYM SBREXT.AC_RECS FOR SBR.AC_RECS;


CREATE SYNONYM SBREXT.AC_REGISTRATIONS FOR SBR.AC_REGISTRATIONS;


CREATE SYNONYM SBREXT.AC_STATUS_LOV FOR SBR.AC_STATUS_LOV;


CREATE SYNONYM SBREXT.AC_SUBJECTS FOR SBR.AC_SUBJECTS;


CREATE SYNONYM SBREXT.AC_TYPES_LOV FOR SBR.AC_TYPES_LOV;


CREATE SYNONYM SBREXT.AC_WF_BUSINESS_ROLES FOR SBR.AC_WF_BUSINESS_ROLES;


CREATE SYNONYM SBREXT.AC_WF_RULES FOR SBR.AC_WF_RULES;


CREATE SYNONYM SBREXT.ADDR_TYPES_LOV FOR SBR.ADDR_TYPES_LOV;


CREATE SYNONYM SBREXT.ADMINISTERED_COMPONENTS FOR SBR.ADMINISTERED_COMPONENTS;


CREATE SYNONYM SBREXT.ADVANCE_RPT_LOV FOR SBR.ADVANCE_RPT_LOV;


CREATE SYNONYM SBREXT.APP_COMPONENT_TYPES_LOV FOR SBR.APP_COMPONENT_TYPES_LOV;


CREATE SYNONYM SBREXT.APP_GRANTS FOR SBR.APP_GRANTS;


CREATE SYNONYM SBREXT.APP_OBJECTS FOR SBR.APP_OBJECTS;


CREATE SYNONYM SBREXT.APP_OBJECTS_LOV FOR SBR.APP_OBJECTS_LOV;


CREATE SYNONYM SBREXT.APP_PRIV_LOV FOR SBR.APP_PRIV_LOV;


CREATE SYNONYM SBREXT.APP_ROLES_LOV FOR SBR.APP_ROLES_LOV;


CREATE SYNONYM SBREXT.APP_VERSIONS FOR SBR.APP_VERSIONS;


CREATE SYNONYM SBREXT.BUSINESS_ROLES_LOV FOR SBR.BUSINESS_ROLES_LOV;


CREATE SYNONYM SBREXT.CD_VMS FOR SBR.CD_VMS;


CREATE SYNONYM SBREXT.CHARACTER_SET_LOV FOR SBR.CHARACTER_SET_LOV;


CREATE SYNONYM SBREXT.CLASSIFICATION_SCHEMES FOR SBR.CLASSIFICATION_SCHEMES;


CREATE SYNONYM SBREXT.CLASSIFICATION_SCHEMES_BACKUP FOR SBR.CLASSIFICATION_SCHEMES_BACKUP;


CREATE SYNONYM SBREXT.CLASS_SCHEME_ITEMS_BACKUP FOR SBR.CLASS_SCHEME_ITEMS_BACKUP;


CREATE SYNONYM SBREXT.CM_STATES_LOV FOR SBR.CM_STATES_LOV;


CREATE SYNONYM SBREXT.COMM_TYPES_LOV FOR SBR.COMM_TYPES_LOV;


CREATE SYNONYM SBREXT.COMPLEX_DATA_ELEMENTS FOR SBR.COMPLEX_DATA_ELEMENTS;


CREATE SYNONYM SBREXT.COMPLEX_DE_RELATIONSHIPS FOR SBR.COMPLEX_DE_RELATIONSHIPS;


CREATE SYNONYM SBREXT.COMPLEX_REP_TYPE_LOV FOR SBR.COMPLEX_REP_TYPE_LOV;


CREATE SYNONYM SBREXT.CONCEPTUAL_DOMAINS FOR SBR.CONCEPTUAL_DOMAINS;


CREATE SYNONYM SBREXT.CONTACT_ADDRESSES FOR SBR.CONTACT_ADDRESSES;


CREATE SYNONYM SBREXT.CONTACT_COMMS FOR SBR.CONTACT_COMMS;


CREATE SYNONYM SBREXT.CONTEXTS FOR SBR.CONTEXTS;


CREATE SYNONYM SBREXT.CSI_RECS FOR SBR.CSI_RECS;


CREATE SYNONYM SBREXT.CSI_TYPES_LOV FOR SBR.CSI_TYPES_LOV;


CREATE SYNONYM SBREXT.CSI_TYPES_LOV_BACKUP FOR SBR.CSI_TYPES_LOV_BACKUP;


CREATE SYNONYM SBREXT.CS_CSI FOR SBR.CS_CSI;


CREATE SYNONYM SBREXT.CS_CSI_BACKUP FOR SBR.CS_CSI_BACKUP;


CREATE SYNONYM SBREXT.CS_CSI_BU FOR SBR.CS_CSI_BU;


CREATE SYNONYM SBREXT.CS_ITEMS FOR SBR.CS_ITEMS;


CREATE SYNONYM SBREXT.CS_RECS FOR SBR.CS_RECS;


CREATE SYNONYM SBREXT.CS_TYPES_LOV FOR SBR.CS_TYPES_LOV;


CREATE SYNONYM SBREXT.CS_TYPES_LOV_BACKUP FOR SBR.CS_TYPES_LOV_BACKUP;


CREATE SYNONYM SBREXT.DATATYPES_LOV FOR SBR.DATATYPES_LOV;


CREATE SYNONYM SBREXT.DATA_ELEMENTS FOR SBR.DATA_ELEMENTS;


CREATE SYNONYM SBREXT.DATA_ELEMENTS_BU FOR SBR.DATA_ELEMENTS_BU;


CREATE SYNONYM SBREXT.DATA_ELEMENT_CONCEPTS FOR SBR.DATA_ELEMENT_CONCEPTS;


CREATE SYNONYM SBREXT.DEC_RECS FOR SBR.DEC_RECS;


CREATE SYNONYM SBREXT.DEFINITIONS FOR SBR.DEFINITIONS;


CREATE SYNONYM SBREXT.DESIGNATIONS FOR SBR.DESIGNATIONS;


CREATE SYNONYM SBREXT.DESIGNATIONS_BACKUP FOR SBR.DESIGNATIONS_BACKUP;


CREATE SYNONYM SBREXT.DESIGNATIONS_BACKUP_NEW FOR SBR.DESIGNATIONS_BACKUP_NEW;


CREATE SYNONYM SBREXT.DESIGNATION_TYPES_LOV FOR SBR.DESIGNATION_TYPES_LOV;


CREATE SYNONYM SBREXT.DESIG_BACKUP FOR SBR.DESIG_BACKUP;


CREATE SYNONYM SBREXT.DE_BACKUP FOR SBR.DE_BACKUP;


CREATE SYNONYM SBREXT.DE_RECS FOR SBR.DE_RECS;


CREATE SYNONYM SBREXT.DOCUMENT_TYPES_LOV FOR SBR.DOCUMENT_TYPES_LOV;


CREATE SYNONYM SBREXT.FAILED_LOG FOR SBR.FAILED_LOG;


CREATE SYNONYM SBREXT.FORMATS_LOV FOR SBR.FORMATS_LOV;


CREATE SYNONYM SBREXT.GROUPS FOR SBR.GROUPS;


CREATE SYNONYM SBREXT.GROUP_RECS FOR SBR.GROUP_RECS;


CREATE SYNONYM SBREXT.GRP_BUSINESS_ROLES FOR SBR.GRP_BUSINESS_ROLES;


CREATE SYNONYM SBREXT.LANGUAGES_LOV FOR SBR.LANGUAGES_LOV;


CREATE SYNONYM SBREXT.LIFECYCLES_LOV FOR SBR.LIFECYCLES_LOV;


CREATE SYNONYM SBREXT.LOOKUP_LOV FOR SBR.LOOKUP_LOV;


CREATE SYNONYM SBREXT.META_TEXT FOR SBR.META_TEXT;


CREATE SYNONYM SBREXT.META_UTIL_STATUSES FOR SBR.META_UTIL_STATUSES;


CREATE SYNONYM SBREXT.OBJECT_CLASSES_LOV FOR SBR.OBJECT_CLASSES_LOV;


CREATE SYNONYM SBREXT.OC_CADSR FOR SBR.OC_CADSR;


CREATE SYNONYM SBREXT.OC_COMPRESULT FOR SBR.OC_COMPRESULT;


CREATE SYNONYM SBREXT.OC_COMPRESULT2 FOR SBR.OC_COMPRESULT2;


CREATE SYNONYM SBREXT.OC_VD FOR SBR.OC_VD;


CREATE SYNONYM SBREXT.ORGANIZATIONS FOR SBR.ORGANIZATIONS;


CREATE SYNONYM SBREXT.PERMISSIBLE_VALUES FOR SBR.PERMISSIBLE_VALUES;


CREATE SYNONYM SBREXT.PERSONS FOR SBR.PERSONS;


CREATE SYNONYM SBREXT.PROGRAMS FOR SBR.PROGRAMS;


CREATE SYNONYM SBREXT.PROGRAM_AREAS_LOV FOR SBR.PROGRAM_AREAS_LOV;


CREATE SYNONYM SBREXT.PROPERTIES_LOV FOR SBR.PROPERTIES_LOV;


CREATE SYNONYM SBREXT.REFERENCE_BLOBS FOR SBR.REFERENCE_BLOBS;


CREATE SYNONYM SBREXT.REFERENCE_DOCUMENTS FOR SBR.REFERENCE_DOCUMENTS;


CREATE SYNONYM SBREXT.REFERENCE_FORMATS_LOV FOR SBR.REFERENCE_FORMATS_LOV;


CREATE SYNONYM SBREXT.REGISTRARS FOR SBR.REGISTRARS;


CREATE SYNONYM SBREXT.REG_STATUS_LOV FOR SBR.REG_STATUS_LOV;


CREATE SYNONYM SBREXT.RELATIONSHIPS_LOV FOR SBR.RELATIONSHIPS_LOV;


CREATE SYNONYM SBREXT.REL_USAGE_LOV FOR SBR.REL_USAGE_LOV;


CREATE SYNONYM SBREXT.RL_RUL FOR SBR.RL_RUL;


CREATE SYNONYM SBREXT.RULES_LOV FOR SBR.RULES_LOV;


CREATE SYNONYM SBREXT.RUPD$_AC_CSI FOR SBR.RUPD$_AC_CSI;


CREATE SYNONYM SBREXT.RUPD$_CLASSIFICATION_SCHEM FOR SBR.RUPD$_CLASSIFICATION_SCHEM;


CREATE SYNONYM SBREXT.RUPD$_CS_CSI FOR SBR.RUPD$_CS_CSI;


CREATE SYNONYM SBREXT.RUPD$_CS_ITEMS FOR SBR.RUPD$_CS_ITEMS;


CREATE SYNONYM SBREXT.RUPD$_DATA_ELEMENTS FOR SBR.RUPD$_DATA_ELEMENTS;


CREATE SYNONYM SBREXT.RUPD$_DATA_ELEMENT_CONCEPT FOR SBR.RUPD$_DATA_ELEMENT_CONCEPT;


CREATE SYNONYM SBREXT.RUPD$_DEFINITIONS FOR SBR.RUPD$_DEFINITIONS;


CREATE SYNONYM SBREXT.RUPD$_DESIGNATIONS FOR SBR.RUPD$_DESIGNATIONS;


CREATE SYNONYM SBREXT.RUPD$_VALUE_DOMAINS FOR SBR.RUPD$_VALUE_DOMAINS;


CREATE SYNONYM SBREXT.SC_CONTEXTS FOR SBR.SC_CONTEXTS;


CREATE SYNONYM SBREXT.SC_GROUPS FOR SBR.SC_GROUPS;


CREATE SYNONYM SBREXT.SC_USER_ACCOUNTS FOR SBR.SC_USER_ACCOUNTS;


CREATE SYNONYM SBREXT.SECURITY_CONTEXTS_LOV FOR SBR.SECURITY_CONTEXTS_LOV;


CREATE SYNONYM SBREXT.STEWARDS FOR SBR.STEWARDS;


CREATE SYNONYM SBREXT.SUBJECTS FOR SBR.SUBJECTS;


CREATE SYNONYM SBREXT.SUBMITTERS FOR SBR.SUBMITTERS;


CREATE SYNONYM SBREXT.SUCCESS_LOG FOR SBR.SUCCESS_LOG;


CREATE SYNONYM SBREXT.UI_HIER_LINK_RECS FOR SBR.UI_HIER_LINK_RECS;


CREATE SYNONYM SBREXT.UI_ITEMS FOR SBR.UI_ITEMS;


CREATE SYNONYM SBREXT.UI_ITEM_GENERATORS FOR SBR.UI_ITEM_GENERATORS;


CREATE SYNONYM SBREXT.UI_ITEM_HIERARCHIES FOR SBR.UI_ITEM_HIERARCHIES;


CREATE SYNONYM SBREXT.UI_ITEM_IMAGES FOR SBR.UI_ITEM_IMAGES;


CREATE SYNONYM SBREXT.UI_ITEM_LINK_RECS FOR SBR.UI_ITEM_LINK_RECS;


CREATE SYNONYM SBREXT.UI_LINKS FOR SBR.UI_LINKS;


CREATE SYNONYM SBREXT.UI_LINK_FRAMESET_RECS FOR SBR.UI_LINK_FRAMESET_RECS;


CREATE SYNONYM SBREXT.UI_LINK_LINK_RECS FOR SBR.UI_LINK_LINK_RECS;


CREATE SYNONYM SBREXT.UI_LINK_PARAMS FOR SBR.UI_LINK_PARAMS;


CREATE SYNONYM SBREXT.UI_METADATA FOR SBR.UI_METADATA;


CREATE SYNONYM SBREXT.UI_REFERENCE FOR SBR.UI_REFERENCE;


CREATE SYNONYM SBREXT.UI_TYPES_LOV FOR SBR.UI_TYPES_LOV;


CREATE SYNONYM SBREXT.UNIT_OF_MEASURES_LOV FOR SBR.UNIT_OF_MEASURES_LOV;


CREATE SYNONYM SBREXT.USER_ACCOUNTS FOR SBR.USER_ACCOUNTS;


CREATE SYNONYM SBREXT.USER_GROUPS FOR SBR.USER_GROUPS;


CREATE SYNONYM SBREXT.VALUE_DOMAINS FOR SBR.VALUE_DOMAINS;


CREATE SYNONYM SBREXT.VALUE_MEANINGS FOR SBR.VALUE_MEANINGS;


CREATE SYNONYM SBREXT.VD_PVS FOR SBR.VD_PVS;


CREATE SYNONYM SBREXT.VD_PV_RECS FOR SBR.VD_PV_RECS;


CREATE SYNONYM SBREXT.VD_RECS FOR SBR.VD_RECS;


CREATE SYNONYM SBREXT.WSGSR_SESSIONS FOR SBR.WSGSR_SESSIONS;


CREATE SYNONYM SBREXT.WSGSR_USERDATA FOR SBR.WSGSR_USERDATA;


ALTER TABLE SBR.USER_ACCOUNTS ADD (
  CONSTRAINT AVCON_985893623_DER_A_000
 CHECK (DER_ADMIN_IND IN ('Yes', 'No')));

ALTER TABLE SBR.USER_ACCOUNTS ADD (
  CONSTRAINT AVCON_985893623_ENABL_000
 CHECK (ENABLED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.USER_ACCOUNTS ADD (
  CONSTRAINT UA_PK
 PRIMARY KEY
 (UA_NAME));


ALTER TABLE SBR.AC_STATUS_LOV ADD (
  CONSTRAINT ASL_PK
 PRIMARY KEY
 (ASL_NAME));


ALTER TABLE SBR.AC_SUBJECTS ADD (
  CONSTRAINT ACSUB_PK
 PRIMARY KEY
 (ACSUB_IDSEQ));

ALTER TABLE SBR.AC_SUBJECTS ADD (
  CONSTRAINT ACSUB_UK
 UNIQUE (AC_IDSEQ, SUBJ_IDSEQ));


ALTER TABLE SBR.AC_TYPES_LOV ADD (
  CONSTRAINT ACTL_PK
 PRIMARY KEY
 (ACTL_NAME));


ALTER TABLE SBR.AC_WF_BUSINESS_ROLES ADD (
  CONSTRAINT AWB_PK
 PRIMARY KEY
 (AWB_IDSEQ));


ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWM_PK
 PRIMARY KEY
 (AWR_IDSEQ));

ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWM_UK
 UNIQUE (SCL_NAME, FROM_ASL_NAME, TO_ASL_NAME));


ALTER TABLE SBR.ADDR_TYPES_LOV ADD (
  CONSTRAINT ATL_PK
 PRIMARY KEY
 (ATL_NAME));


ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AVCON_985893623_DELET_004
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AVCON_985893623_LATES_002
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_PK
 PRIMARY KEY
 (AC_IDSEQ));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_UK
 UNIQUE (VERSION, PREFERRED_NAME, CONTE_IDSEQ, ACTL_NAME));


ALTER TABLE SBR.ADVANCE_RPT_LOV ADD (
  CONSTRAINT ARV_PK
 PRIMARY KEY
 (NAME));


ALTER TABLE SBR.APP_COMPONENT_TYPES_LOV ADD (
  CONSTRAINT ACTV_PK
 PRIMARY KEY
 (APCTL_NAME));


ALTER TABLE SBR.RULES_LOV ADD (
  CONSTRAINT RULE_PK
 PRIMARY KEY
 (RULE_IDSEQ));


ALTER TABLE SBR.SC_CONTEXTS ADD (
  CONSTRAINT SCC_PK
 PRIMARY KEY
 (CONTE_IDSEQ));

ALTER TABLE SBR.SC_CONTEXTS ADD (
  CONSTRAINT SCC_UK
 UNIQUE (SCL_NAME));


ALTER TABLE SBR.SC_GROUPS ADD (
  CONSTRAINT SCG_PK
 PRIMARY KEY
 (SCG_IDSEQ));

ALTER TABLE SBR.SC_GROUPS ADD (
  CONSTRAINT SCG_UK
 UNIQUE (GRP_NAME, SCL_NAME));


ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT AVCON_985893623_CONTE_000
 CHECK (CONTEXT_ADMIN_IND IN ('Yes', 'No')));

ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT SCUA_PK
 PRIMARY KEY
 (SCUA_IDSEQ));

ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT SCUA_UK
 UNIQUE (UA_NAME, SCL_NAME));


ALTER TABLE SBR.SECURITY_CONTEXTS_LOV ADD (
  CONSTRAINT SC_PK
 PRIMARY KEY
 (SCL_NAME));


ALTER TABLE SBR.STEWARDS ADD (
  CONSTRAINT STEWA_PK
 PRIMARY KEY
 (STEWA_IDSEQ));


ALTER TABLE SBR.SUBJECTS ADD (
  CONSTRAINT SUBJ_PK
 PRIMARY KEY
 (SUBJ_IDSEQ));


ALTER TABLE SBR.SUBMITTERS ADD (
  CONSTRAINT SUBMI_PK
 PRIMARY KEY
 (SUB_IDSEQ));


ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT CS_CSI_PK
 PRIMARY KEY
 (CS_CSI_IDSEQ));

ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT CS_CSI_UK
 UNIQUE (CS_IDSEQ, CSI_IDSEQ, P_CS_CSI_IDSEQ));


ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_PK
 PRIMARY KEY
 (CSI_IDSEQ));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI2_UK
 UNIQUE (CSITL_NAME, LONG_NAME, CONTE_IDSEQ, VERSION));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBR.CS_RECS ADD (
  CONSTRAINT CC2R_PK
 PRIMARY KEY
 (CS_REC_IDSEQ));


ALTER TABLE SBR.CS_TYPES_LOV ADD (
  CONSTRAINT CTL_PK
 PRIMARY KEY
 (CSTL_NAME));


ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_PK
 PRIMARY KEY
 (DESIG_IDSEQ));

ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_UK
 UNIQUE (AC_IDSEQ, NAME, DETL_NAME, CONTE_IDSEQ));


ALTER TABLE SBR.DESIGNATION_TYPES_LOV ADD (
  CONSTRAINT DT_PK
 PRIMARY KEY
 (DETL_NAME));


ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_PK
 PRIMARY KEY
 (DE_REC_IDSEQ));

ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_UK
 UNIQUE (P_DE_IDSEQ, C_DE_IDSEQ));


ALTER TABLE SBR.DOCUMENT_TYPES_LOV ADD (
  CONSTRAINT DCTL_PK
 PRIMARY KEY
 (DCTL_NAME));


ALTER TABLE SBR.UI_LINK_PARAMS ADD (
  CONSTRAINT AVCON_995480835_CORE__001
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_LINK_PARAMS ADD (
  CONSTRAINT UILP_PK
 PRIMARY KEY
 (UILP_IDSEQ));


ALTER TABLE SBR.WSGSR_USERDATA ADD (
  CONSTRAINT WSGSR_USERDATA_PK
 PRIMARY KEY
 (USER_NAME));


ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT AVCON_1050407263_VD_TY_000
 CHECK (VD_TYPE_FLAG IN ('E', 'N')));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT AVCON_985893623_DELET_006
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT AVCON_985893623_LATES_004
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT AVCON_985893623_VD_TY_000
 CHECK (VD_TYPE_FLAG IN ('E', 'N')));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_PK
 PRIMARY KEY
 (VD_IDSEQ));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_UK
 UNIQUE (VERSION, PREFERRED_NAME, CONTE_IDSEQ));


ALTER TABLE SBR.USER_GROUPS ADD (
  CONSTRAINT UGP_PK
 PRIMARY KEY
 (UA_NAME, GRP_NAME));


ALTER TABLE SBR.WSGSR_SESSIONS ADD (
  CONSTRAINT WSGSR_SESS_PK
 PRIMARY KEY
 (CLIENT_ID));


ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_PK
 PRIMARY KEY
 (VD_REC_IDSEQ));

ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_UK
 UNIQUE (RL_NAME, P_VD_IDSEQ, C_VD_IDSEQ));


ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_PK
 PRIMARY KEY
 (VPR_IDSEQ));

ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_UK
 UNIQUE (RL_NAME, P_VP_IDSEQ, C_VP_IDSEQ));


ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_PK
 PRIMARY KEY
 (VP_IDSEQ));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_UK
 UNIQUE (VD_IDSEQ, CONTE_IDSEQ, PV_IDSEQ));


ALTER TABLE SBR.ORGANIZATIONS ADD (
  CONSTRAINT AVCON_985893623_RA_IN_000
 CHECK (RA_IND IN ('Yes', 'No')));

ALTER TABLE SBR.ORGANIZATIONS ADD (
  CONSTRAINT ORGAN_PK
 PRIMARY KEY
 (ORG_IDSEQ));

ALTER TABLE SBR.ORGANIZATIONS ADD (
  CONSTRAINT ORG_UK
 UNIQUE (NAME));


ALTER TABLE SBR.PERMISSIBLE_VALUES ADD (
  CONSTRAINT PV_PK
 PRIMARY KEY
 (PV_IDSEQ));

ALTER TABLE SBR.PERMISSIBLE_VALUES ADD (
  CONSTRAINT PV_UK
 UNIQUE (VM_IDSEQ, VALUE));


ALTER TABLE SBR.PERSONS ADD (
  CONSTRAINT PER_PK
 PRIMARY KEY
 (PER_IDSEQ));


ALTER TABLE SBR.PROGRAMS ADD (
  CONSTRAINT PRM_PK
 PRIMARY KEY
 (PA_IDSEQ));

ALTER TABLE SBR.PROGRAMS ADD (
  CONSTRAINT PRM_UK
 UNIQUE (NAME));


ALTER TABLE SBR.PROGRAM_AREAS_LOV ADD (
  CONSTRAINT PAL_PK
 PRIMARY KEY
 (PAL_NAME));


ALTER TABLE SBR.PROPERTIES_LOV ADD (
  CONSTRAINT PROPE_PK
 PRIMARY KEY
 (PROPL_NAME));


ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_PK
 PRIMARY KEY
 (GRANT_IDSEQ));

ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_UK
 UNIQUE (APPOBJ_IDSEQ, ROLE_NAME, PRIV_NAME));


ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_PK
 PRIMARY KEY
 (APPOBJ_IDSEQ));

ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_UK
 UNIQUE (NAME, OBJ_NAME));


ALTER TABLE SBR.APP_OBJECTS_LOV ADD (
  CONSTRAINT AOV_PK
 PRIMARY KEY
 (OBJ_NAME));


ALTER TABLE SBR.APP_PRIV_LOV ADD (
  CONSTRAINT AGV_PK
 PRIMARY KEY
 (PRIV_NAME));


ALTER TABLE SBR.APP_ROLES_LOV ADD (
  CONSTRAINT ARE_PK
 PRIMARY KEY
 (ROLE_NAME));


ALTER TABLE SBR.APP_VERSIONS ADD (
  CONSTRAINT DVN_PK
 PRIMARY KEY
 (APP_VERSION));


ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_CHECK_000
 CHECK (CHECKOUT_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_CREAT_000
 CHECK (CREATE_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_DELET_000
 CHECK (DELETE_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_READ__000
 CHECK (READ_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_UPDAT_000
 CHECK (UPDATE_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT AVCON_985893623_VERSI_000
 CHECK (VERSION_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.BUSINESS_ROLES_LOV ADD (
  CONSTRAINT BR_PK
 PRIMARY KEY
 (BRL_NAME));


ALTER TABLE SBR.CD_VMS ADD (
  CONSTRAINT CDV_PK
 PRIMARY KEY
 (CV_IDSEQ));

ALTER TABLE SBR.CD_VMS ADD (
  CONSTRAINT CDV_UK
 UNIQUE (CD_IDSEQ, VM_IDSEQ));


ALTER TABLE SBR.CHARACTER_SET_LOV ADD (
  CONSTRAINT CSV_PK
 PRIMARY KEY
 (CHAR_SET_NAME));


ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT AVCON_985894271_DELET_000
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT AVCON_985894271_LATES_000
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_PK
 PRIMARY KEY
 (CS_IDSEQ));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_UK
 UNIQUE (PREFERRED_NAME, VERSION, CONTE_IDSEQ));


ALTER TABLE SBR.CM_STATES_LOV ADD (
  CONSTRAINT CSL_PK
 PRIMARY KEY
 (CMSL_NAME));


ALTER TABLE SBR.COMM_TYPES_LOV ADD (
  CONSTRAINT COTL_PK
 PRIMARY KEY
 (CTL_NAME));


ALTER TABLE SBR.COMPLEX_DATA_ELEMENTS ADD (
  CONSTRAINT CDT_PK
 PRIMARY KEY
 (P_DE_IDSEQ));


ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDR_PK
 PRIMARY KEY
 (CDR_IDSEQ));

ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDE_UK
 UNIQUE (C_DE_IDSEQ, P_DE_IDSEQ));


ALTER TABLE SBR.COMPLEX_REP_TYPE_LOV ADD (
  CONSTRAINT CRV_PK
 PRIMARY KEY
 (CRTL_NAME));


ALTER TABLE SBR.DATATYPES_LOV ADD (
  CONSTRAINT DTL_PK
 PRIMARY KEY
 (DTL_NAME));


ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT AVCON_985893623_DELET_002
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT AVCON_985893623_LATES_000
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_PK
 PRIMARY KEY
 (DE_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_UK
 UNIQUE (VERSION, PREFERRED_NAME, CONTE_IDSEQ));


ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT AVCON_985893623_DELET_005
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT AVCON_985893623_LATES_003
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_PK
 PRIMARY KEY
 (DEC_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_UK
 UNIQUE (VERSION, PREFERRED_NAME, CONTE_IDSEQ));


ALTER TABLE SBR.DEC_RECS ADD (
  CONSTRAINT DRC_PK
 PRIMARY KEY
 (DEC_REC_IDSEQ));


ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEFIN_PK
 PRIMARY KEY
 (DEFIN_IDSEQ));


ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT AVCON_985893623_DELET_003
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT AVCON_985893623_LATES_001
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_PK
 PRIMARY KEY
 (CD_IDSEQ));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_UK
 UNIQUE (VERSION, PREFERRED_NAME, CONTE_IDSEQ));


ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_ORG_PER_CK
 CHECK ((org_idseq is null and per_idseq is not null) or
(org_idseq is not null and per_idseq is null)));

ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_PK
 PRIMARY KEY
 (CADDR_IDSEQ));


ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_ORG_PER_CK
 CHECK ((org_idseq is null and per_idseq is not null) or
(org_idseq is not null and per_idseq is null)));

ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_PK
 PRIMARY KEY
 (CCOMM_IDSEQ));


ALTER TABLE SBR.CONTEXTS ADD (
  CONSTRAINT CONTE_PK
 PRIMARY KEY
 (CONTE_IDSEQ));

ALTER TABLE SBR.CONTEXTS ADD (
  CONSTRAINT CONTE_UK
 UNIQUE (NAME, VERSION));


ALTER TABLE SBR.CSI_RECS ADD (
  CONSTRAINT CSX_PK
 PRIMARY KEY
 (CSI_REC_IDSEQ));


ALTER TABLE SBR.CSI_TYPES_LOV ADD (
  CONSTRAINT CSITL_PK
 PRIMARY KEY
 (CSITL_NAME));


ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT AVCON_995480377_CORE__002
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT UIILR_PK
 PRIMARY KEY
 (UIILR_IDSEQ));


ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT AVCON_995480257_CORE__002
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT UIIIM_PK
 PRIMARY KEY
 (UIIIM_IDSEQ));


ALTER TABLE SBR.UI_ITEM_HIERARCHIES ADD (
  CONSTRAINT AVCON_995480137_CORE__002
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_ITEM_HIERARCHIES ADD (
  CONSTRAINT UIIH_PK
 PRIMARY KEY
 (UIIH_IDSEQ));


ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT AVCON_995480020_CORE__000
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UII_UIEI_UIL_CONSTRAINT
 CHECK ((UII_IDSEQ IS NOT NULL AND UIEI_IDSEQ IS NULL AND UIL_IDSEQ IS NULL) OR (UII_IDSEQ IS NULL AND UIEI_IDSEQ IS NOT NULL AND UIL_IDSEQ IS NULL) OR (UII_IDSEQ IS NULL AND UIEI_IDSEQ IS NULL AND UIL_IDSEQ IS NOT NULL)));

ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UIG_PK
 PRIMARY KEY
 (UIG_IDSEQ));


ALTER TABLE SBR.UI_ITEMS ADD (
  CONSTRAINT AVCON_995479914_CORE__000
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_ITEMS ADD (
  CONSTRAINT UII_PK
 PRIMARY KEY
 (UII_IDSEQ));

ALTER TABLE SBR.UI_ITEMS ADD (
  CONSTRAINT UIM_UK
 UNIQUE (DISPLAY_TITLE, TOOL_TIP));


ALTER TABLE SBR.AC_CSI ADD (
  CONSTRAINT ACC_PK
 PRIMARY KEY
 (AC_CSI_IDSEQ));

ALTER TABLE SBR.AC_CSI ADD (
  CONSTRAINT ACC_UK
 UNIQUE (AC_IDSEQ, CS_CSI_IDSEQ));


ALTER TABLE SBR.ACTIONS_LOV ADD (
  CONSTRAINT AL_PK
 PRIMARY KEY
 (AL_NAME));


ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AVCON_985893623_CHECK_001
 CHECK (CHECKOUT_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AVCON_985893623_DELET_001
 CHECK (DELETE_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AVCON_985893623_READ__001
 CHECK (READ_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AVCON_985893623_UPDAT_001
 CHECK (UPDATE_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AVCON_985893623_VERSI_001
 CHECK (VERSION_ALLOWED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAX_PK
 PRIMARY KEY
 (AAM_IDSEQ));


ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_ARC
 CHECK ((AC_IDSEQ is not null and AR_IDSEQ is null and CS_CSI_IDSEQ is null) or (AC_IDSEQ is null and AR_IDSEQ is not null and CS_CSI_IDSEQ is null) or (AC_IDSEQ is null and AR_IDSEQ is null and CS_CSI_IDSEQ is not null) ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ORG_PER_CK
 CHECK ((org_idseq is null and per_idseq is not null) or
(org_idseq is not null and per_idseq is null)));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACT_PK
 PRIMARY KEY
 (ACC_IDSEQ));


ALTER TABLE SBR.AC_HISTORIES ADD (
  CONSTRAINT AH_PK
 PRIMARY KEY
 (ACH_IDSEQ));


ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT AVCON_998332508_OVRID_000
 CHECK (OVRID_DEFAULT_RPT_COL_VAL IN ('Y', 'N')));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT AVCON_998332508_OVRID_001
 CHECK (OVRID_MORE_ROW_RESTRICTIONS IN ('Y', 'N')));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT AVCON_998332508_OVRID_002
 CHECK (OVRID_DEFAULT_COL_MEANING IN ('Y', 'N')));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT ARS_PK
 PRIMARY KEY
 (ARS_IDSEQ));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT ARS_UK
 UNIQUE (AC_IDSEQ, OVRID_AC_IDSEQ));


ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT CRA_PK
 PRIMARY KEY
 (AR_IDSEQ));

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT CRA_UK
 UNIQUE (AC_IDSEQ, ORG_IDSEQ, SUB_IDSEQ));


ALTER TABLE SBR.FORMATS_LOV ADD (
  CONSTRAINT FORML_PK
 PRIMARY KEY
 (FORML_NAME));


ALTER TABLE SBR.GROUPS ADD (
  CONSTRAINT GRP_PK
 PRIMARY KEY
 (GRP_NAME));


ALTER TABLE SBR.GROUP_RECS ADD (
  CONSTRAINT GRCS_PARENT_CHILD_CHK
 CHECK (( PARENT_GRP_NAME != CHILD_GRP_NAME )));

ALTER TABLE SBR.GROUP_RECS ADD (
  CONSTRAINT GRCS_PK
 PRIMARY KEY
 (PARENT_GRP_NAME, CHILD_GRP_NAME));


ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_PK
 PRIMARY KEY
 (GBR_IDSEQ));

ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_UK
 UNIQUE (BRL_NAME, SCG_IDSEQ, ACTL_NAME));


ALTER TABLE SBR.LANGUAGES_LOV ADD (
  CONSTRAINT LAE_PK
 PRIMARY KEY
 (NAME));


ALTER TABLE SBR.LIFECYCLES_LOV ADD (
  CONSTRAINT LL_PK
 PRIMARY KEY
 (LL_NAME));


ALTER TABLE SBR.LOOKUP_LOV ADD (
  CONSTRAINT LLV_PK
 PRIMARY KEY
 (LOOKUP_NAME));


ALTER TABLE SBR.META_TEXT ADD (
  CONSTRAINT MT_PK
 PRIMARY KEY
 (MT_IDSEQ));


ALTER TABLE SBR.META_UTIL_STATUSES ADD (
  CONSTRAINT UTLSTAT_PK
 PRIMARY KEY
 (UTILITY_NAME));


ALTER TABLE SBR.OBJECT_CLASSES_LOV ADD (
  CONSTRAINT OC_PK
 PRIMARY KEY
 (OCL_NAME));


ALTER TABLE SBR.REFERENCE_BLOBS ADD (
  CONSTRAINT RB_PK
 PRIMARY KEY
 (NAME));


ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT REF_DOC_ARC
 CHECK ((AC_IDSEQ is not null and ACH_IDSEQ is null and AR_IDSEQ is null and CS_CSI_IDSEQ is null) or (AC_IDSEQ is null and ACH_IDSEQ is not null and AR_IDSEQ is null and CS_CSI_IDSEQ is null) or (AC_IDSEQ is null and ACH_IDSEQ is null and AR_IDSEQ is not null and CS_CSI_IDSEQ is null) or (AC_IDSEQ is null and ACH_IDSEQ is null and AR_IDSEQ is null and CS_CSI_IDSEQ is not null) ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_PK
 PRIMARY KEY
 (RD_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_UK
 UNIQUE (AC_IDSEQ, NAME, DCTL_NAME));


ALTER TABLE SBR.REFERENCE_FORMATS_LOV ADD (
  CONSTRAINT RFL_PK
 PRIMARY KEY
 (RFL_NAME));


ALTER TABLE SBR.REGISTRARS ADD (
  CONSTRAINT REGIS_PK
 PRIMARY KEY
 (REGIS_IDSEQ));


ALTER TABLE SBR.REG_STATUS_LOV ADD (
  CONSTRAINT RSL_PK
 PRIMARY KEY
 (REGISTRATION_STATUS));


ALTER TABLE SBR.RELATIONSHIPS_LOV ADD (
  CONSTRAINT RL_PK
 PRIMARY KEY
 (RL_NAME));


ALTER TABLE SBR.REL_USAGE_LOV ADD (
  CONSTRAINT RUL_PK
 PRIMARY KEY
 (RRL_NAME));


ALTER TABLE SBR.RL_RUL ADD (
  CONSTRAINT RRX_PK
 PRIMARY KEY
 (RRX_IDSEQ));

ALTER TABLE SBR.RL_RUL ADD (
  CONSTRAINT RRX_UK
 UNIQUE (RL_NAME, RRL_NAME));


ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT AVCON_1151930989_DELET_000
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT AVCON_1151930989_LATES_000
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VMV_PK
 PRIMARY KEY
 (VM_IDSEQ));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VMV_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT AVCON_995480727_CORE__001
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT UILL_PK
 PRIMARY KEY
 (UILL_IDSEQ));

ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT UILL_UK
 UNIQUE (PARENT_LINK_IDSEQ, CHILD_LINK_IDSEQ));


ALTER TABLE SBR.UI_LINKS ADD (
  CONSTRAINT AVCON_995480484_CORE__000
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_LINKS ADD (
  CONSTRAINT UIL_PK
 PRIMARY KEY
 (UIL_IDSEQ));

ALTER TABLE SBR.UI_LINKS ADD (
  CONSTRAINT ULK_UK
 UNIQUE (NAME, BASE_URL));


ALTER TABLE SBR.UNIT_OF_MEASURES_LOV ADD (
  CONSTRAINT UOML_PK
 PRIMARY KEY
 (UOML_NAME));


ALTER TABLE SBR.UI_TYPES_LOV ADD (
  CONSTRAINT AVCON_995480958_CORE__000
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_TYPES_LOV ADD (
  CONSTRAINT UIT_PK
 PRIMARY KEY
 (UITL_NAME));


ALTER TABLE SBR.UI_REFERENCE ADD (
  CONSTRAINT URE_PK
 PRIMARY KEY
 (UA_NAME, BRL_NAME));

ALTER TABLE SBR.UI_REFERENCE ADD (
  CONSTRAINT URE_UK
 UNIQUE (BRL_NAME, UA_NAME));


ALTER TABLE SBR.UI_LINK_FRAMESET_RECS ADD (
  CONSTRAINT AVCON_995480610_CORE__001
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_LINK_FRAMESET_RECS ADD (
  CONSTRAINT UILFR_PK
 PRIMARY KEY
 (UILF_IDSEQ));


ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT AVCON_995479539_CORE__001
 CHECK (CORE_IND IN ('Yes', 'No')));

ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT UIHLR_PK
 PRIMARY KEY
 (UIHLR_IDSEQ));


ALTER TABLE SBR.USER_ACCOUNTS ADD (
  CONSTRAINT UA_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));


ALTER TABLE SBR.AC_SUBJECTS ADD (
  CONSTRAINT ACSUB_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBR.AC_WF_BUSINESS_ROLES ADD (
  CONSTRAINT AWB_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME));

ALTER TABLE SBR.AC_WF_BUSINESS_ROLES ADD (
  CONSTRAINT AWB_AWM_FK 
 FOREIGN KEY (AWR_IDSEQ) 
 REFERENCES SBR.AC_WF_RULES (AWR_IDSEQ));


ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWR_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME));

ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWR_ASL_FROM_FK 
 FOREIGN KEY (FROM_ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWR_ASL_TO_FK 
 FOREIGN KEY (TO_ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));


ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_STEWA_FK 
 FOREIGN KEY (STEWA_IDSEQ) 
 REFERENCES SBR.STEWARDS (STEWA_IDSEQ));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_ACTL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_CSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME));


ALTER TABLE SBR.SC_CONTEXTS ADD (
  CONSTRAINT SCC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));


ALTER TABLE SBR.SC_GROUPS ADD (
  CONSTRAINT SCG_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME));

ALTER TABLE SBR.SC_GROUPS ADD (
  CONSTRAINT SCG_GRP_FK 
 FOREIGN KEY (GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));


ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT SCUA_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME));

ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT SCUA_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));


ALTER TABLE SBR.STEWARDS ADD (
  CONSTRAINT STEWA_ORGAN_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));


ALTER TABLE SBR.SUBJECTS ADD (
  CONSTRAINT SUBL_SUBL_FK 
 FOREIGN KEY (P_SUBJ_IDSEQ) 
 REFERENCES SBR.SUBJECTS (SUBJ_IDSEQ));


ALTER TABLE SBR.SUBMITTERS ADD (
  CONSTRAINT SUBMI_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ)
    ON DELETE CASCADE);


ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT P_CS_CSI_FK 
 FOREIGN KEY (P_CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT CS_CSI_CSI_FK 
 FOREIGN KEY (CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ));

ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT LINK_CS_CSI_FK 
 FOREIGN KEY (LINK_CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT CS_CSI_CS_FK 
 FOREIGN KEY (CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));


ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_CSITL_FK 
 FOREIGN KEY (CSITL_NAME) 
 REFERENCES SBR.CSI_TYPES_LOV (CSITL_NAME));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBR.CS_RECS ADD (
  CONSTRAINT P_CSR_CS_FK 
 FOREIGN KEY (P_CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));

ALTER TABLE SBR.CS_RECS ADD (
  CONSTRAINT CSR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.CS_RECS ADD (
  CONSTRAINT C_CSR_CS_FK 
 FOREIGN KEY (C_CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));


ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME));

ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_DT_FK 
 FOREIGN KEY (DETL_NAME) 
 REFERENCES SBR.DESIGNATION_TYPES_LOV (DETL_NAME));

ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_P_DE_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_C_DE_FK 
 FOREIGN KEY (C_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));


ALTER TABLE SBR.DOCUMENT_TYPES_LOV ADD (
  CONSTRAINT DCTL_ACTLV_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));


ALTER TABLE SBR.UI_LINK_PARAMS ADD (
  CONSTRAINT UILP_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));


ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_UOML_FK 
 FOREIGN KEY (UOML_NAME) 
 REFERENCES SBR.UNIT_OF_MEASURES_LOV (UOML_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_FORML_FK 
 FOREIGN KEY (FORML_NAME) 
 REFERENCES SBR.FORMATS_LOV (FORML_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_DTL_FK 
 FOREIGN KEY (DTL_NAME) 
 REFERENCES SBR.DATATYPES_LOV (DTL_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_CSV_FK 
 FOREIGN KEY (CHAR_SET_NAME) 
 REFERENCES SBR.CHARACTER_SET_LOV (CHAR_SET_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_QLV_FK 
 FOREIGN KEY (QUALIFIER_NAME) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_REP_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.REPRESENTATIONS_EXT (REP_IDSEQ));


ALTER TABLE SBR.USER_GROUPS ADD (
  CONSTRAINT UGP_GRP_FK 
 FOREIGN KEY (GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));

ALTER TABLE SBR.USER_GROUPS ADD (
  CONSTRAINT UGP_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));


ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_C_VD_FK 
 FOREIGN KEY (C_VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));

ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_P_VD_FK 
 FOREIGN KEY (P_VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));


ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_VDV_FK2 
 FOREIGN KEY (C_VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ));

ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_VDV_FK 
 FOREIGN KEY (P_VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ));

ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));


ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_VD_FK 
 FOREIGN KEY (VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_PV_FK 
 FOREIGN KEY (PV_IDSEQ) 
 REFERENCES SBR.PERMISSIBLE_VALUES (PV_IDSEQ));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VDPVS_CET_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));


ALTER TABLE SBR.PERMISSIBLE_VALUES ADD (
  CONSTRAINT PV_VM_FK 
 FOREIGN KEY (VM_IDSEQ) 
 REFERENCES SBR.VALUE_MEANINGS (VM_IDSEQ));


ALTER TABLE SBR.PERSONS ADD (
  CONSTRAINT PER_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));


ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_AOT_FK 
 FOREIGN KEY (APPOBJ_IDSEQ) 
 REFERENCES SBR.APP_OBJECTS (APPOBJ_IDSEQ));

ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_ARL_FK 
 FOREIGN KEY (ROLE_NAME) 
 REFERENCES SBR.APP_ROLES_LOV (ROLE_NAME));

ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_APV_FK 
 FOREIGN KEY (PRIV_NAME) 
 REFERENCES SBR.APP_PRIV_LOV (PRIV_NAME));


ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_DVN_FK 
 FOREIGN KEY (APP_VERSION) 
 REFERENCES SBR.APP_VERSIONS (APP_VERSION));

ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_AOV_FK 
 FOREIGN KEY (OBJ_NAME) 
 REFERENCES SBR.APP_OBJECTS_LOV (OBJ_NAME));

ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_APCTL_FK 
 FOREIGN KEY (APCTL_NAME) 
 REFERENCES SBR.APP_COMPONENT_TYPES_LOV (APCTL_NAME));


ALTER TABLE SBR.CD_VMS ADD (
  CONSTRAINT CV_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ));

ALTER TABLE SBR.CD_VMS ADD (
  CONSTRAINT CD_VM_FK 
 FOREIGN KEY (VM_IDSEQ) 
 REFERENCES SBR.VALUE_MEANINGS (VM_IDSEQ));


ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CMSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CSTL_FK 
 FOREIGN KEY (CSTL_NAME) 
 REFERENCES SBR.CS_TYPES_LOV (CSTL_NAME));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CS_FK 
 FOREIGN KEY (PAR_CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBR.COMPLEX_DATA_ELEMENTS ADD (
  CONSTRAINT CDT_CRV_FK 
 FOREIGN KEY (CRTL_NAME) 
 REFERENCES SBR.COMPLEX_REP_TYPE_LOV (CRTL_NAME));

ALTER TABLE SBR.COMPLEX_DATA_ELEMENTS ADD (
  CONSTRAINT CDT_DE_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));


ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDE_DE_FK 
 FOREIGN KEY (C_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDE_CDT_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.COMPLEX_DATA_ELEMENTS (P_DE_IDSEQ));

ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDP_RFT_FK 
 FOREIGN KEY (RF_IDSEQ) 
 REFERENCES SBREXT.RULE_FUNCTIONS_EXT (RF_IDSEQ));


ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_DEC_FK 
 FOREIGN KEY (DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_VD_FK 
 FOREIGN KEY (VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));


ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_PROPE_FK 
 FOREIGN KEY (PROPL_NAME) 
 REFERENCES SBR.PROPERTIES_LOV (PROPL_NAME));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_OC_FK 
 FOREIGN KEY (OCL_NAME) 
 REFERENCES SBR.OBJECT_CLASSES_LOV (OCL_NAME));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_PRO_FK 
 FOREIGN KEY (PROP_IDSEQ) 
 REFERENCES SBREXT.PROPERTIES_EXT (PROP_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_OCT_FK 
 FOREIGN KEY (OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_PROP_QUAL_FK 
 FOREIGN KEY (PROPERTY_QUALIFIER) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_OBJ_QUAL_FK 
 FOREIGN KEY (OBJ_CLASS_QUALIFIER) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME));


ALTER TABLE SBR.DEC_RECS ADD (
  CONSTRAINT DRC_C_DEC_FK 
 FOREIGN KEY (C_DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ));

ALTER TABLE SBR.DEC_RECS ADD (
  CONSTRAINT DRC_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.DEC_RECS ADD (
  CONSTRAINT DRC_P_DEC_FK 
 FOREIGN KEY (P_DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ));


ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEFIN_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEFIN_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME));

ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEFIN_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEF_DEFL_FK 
 FOREIGN KEY (DEFL_NAME) 
 REFERENCES SBREXT.DEFINITION_TYPES_LOV_EXT (DEFL_NAME));


ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ));

ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_ATL_FK 
 FOREIGN KEY (ATL_NAME) 
 REFERENCES SBR.ADDR_TYPES_LOV (ATL_NAME));


ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ));

ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_CTL_FK 
 FOREIGN KEY (CTL_NAME) 
 REFERENCES SBR.COMM_TYPES_LOV (CTL_NAME));


ALTER TABLE SBR.CONTEXTS ADD (
  CONSTRAINT CONTE_LL_FK 
 FOREIGN KEY (LL_NAME) 
 REFERENCES SBR.LIFECYCLES_LOV (LL_NAME));

ALTER TABLE SBR.CONTEXTS ADD (
  CONSTRAINT CONTE_PAL_FK 
 FOREIGN KEY (PAL_NAME) 
 REFERENCES SBR.PROGRAM_AREAS_LOV (PAL_NAME));


ALTER TABLE SBR.CSI_RECS ADD (
  CONSTRAINT P_CSIR_CSI_FK 
 FOREIGN KEY (P_CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ));

ALTER TABLE SBR.CSI_RECS ADD (
  CONSTRAINT CSIR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.CSI_RECS ADD (
  CONSTRAINT C_CSIR_CSI_FK 
 FOREIGN KEY (C_CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ));


ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT UIC_UAV_FK 
 FOREIGN KEY (UIAL_NAME) 
 REFERENCES SBR.UI_ACTIVITIES_LOV (UIAL_NAME));

ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT UIILR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT UIILR_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));


ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT UIE_UTV_FK 
 FOREIGN KEY (UITL_NAME) 
 REFERENCES SBR.UI_TYPES_LOV (UITL_NAME));

ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT UIIIM_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT UIIIM_UIIMG_FK 
 FOREIGN KEY (UIIMG_IDSEQ) 
 REFERENCES SBR.UI_IMAGES (UIIMG_IDSEQ));


ALTER TABLE SBR.UI_ITEM_HIERARCHIES ADD (
  CONSTRAINT P_UIIR_UII_FK 
 FOREIGN KEY (P_UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.UI_ITEM_HIERARCHIES ADD (
  CONSTRAINT C_UII_UII_FK 
 FOREIGN KEY (C_UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));


ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UIG_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UIG_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UIG_UIEI_FK 
 FOREIGN KEY (UIEI_IDSEQ) 
 REFERENCES SBR.UI_ELEMENTS_ITEMS (UIEI_IDSEQ));


ALTER TABLE SBR.AC_CSI ADD (
  CONSTRAINT AC_CSI_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBR.AC_CSI ADD (
  CONSTRAINT AC_CSI_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAM_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAM_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAM_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAM_CMSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME));


ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_CSCSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACT_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACT_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_AR_FK 
 FOREIGN KEY (AR_IDSEQ) 
 REFERENCES SBR.AC_REGISTRATIONS (AR_IDSEQ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_CR_FK 
 FOREIGN KEY (CONTACT_ROLE) 
 REFERENCES SBREXT.CONTACT_ROLES_EXT (CONTACT_ROLE));


ALTER TABLE SBR.AC_HISTORIES ADD (
  CONSTRAINT SOURCE_AC_FK 
 FOREIGN KEY (SOURCE_AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.AC_HISTORIES ADD (
  CONSTRAINT AH_AL_FK 
 FOREIGN KEY (AL_NAME) 
 REFERENCES SBR.ACTIONS_LOV (AL_NAME));

ALTER TABLE SBR.AC_HISTORIES ADD (
  CONSTRAINT AH_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT ARS_OVRID_AC_FK 
 FOREIGN KEY (OVRID_AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT ARS_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_SUB_FK 
 FOREIGN KEY (SUB_IDSEQ) 
 REFERENCES SBR.SUBMITTERS (SUB_IDSEQ));

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_RSL_FK 
 FOREIGN KEY (REGISTRATION_STATUS) 
 REFERENCES SBR.REG_STATUS_LOV (REGISTRATION_STATUS));

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_REGIS_FK 
 FOREIGN KEY (REGIS_IDSEQ) 
 REFERENCES SBR.REGISTRARS (REGIS_IDSEQ));

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));


ALTER TABLE SBR.GROUP_RECS ADD (
  CONSTRAINT PARENT_GRP_FK 
 FOREIGN KEY (PARENT_GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));

ALTER TABLE SBR.GROUP_RECS ADD (
  CONSTRAINT CHILD_GRP_FK 
 FOREIGN KEY (CHILD_GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));


ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_SCG_FK 
 FOREIGN KEY (SCG_IDSEQ) 
 REFERENCES SBR.SC_GROUPS (SCG_IDSEQ));

ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME));

ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_ATL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));


ALTER TABLE SBR.REFERENCE_BLOBS ADD (
  CONSTRAINT RB_RD_FK 
 FOREIGN KEY (RD_IDSEQ) 
 REFERENCES SBR.REFERENCE_DOCUMENTS (RD_IDSEQ));


ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_DCTL_FK 
 FOREIGN KEY (DCTL_NAME) 
 REFERENCES SBR.DOCUMENT_TYPES_LOV (DCTL_NAME));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_AR_FK 
 FOREIGN KEY (AR_IDSEQ) 
 REFERENCES SBR.AC_REGISTRATIONS (AR_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_ACH_FK 
 FOREIGN KEY (ACH_IDSEQ) 
 REFERENCES SBR.AC_HISTORIES (ACH_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));


ALTER TABLE SBR.REGISTRARS ADD (
  CONSTRAINT REGIS_ORGAN_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));


ALTER TABLE SBR.RL_RUL ADD (
  CONSTRAINT RRX_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.RL_RUL ADD (
  CONSTRAINT RRX_RUL_FK 
 FOREIGN KEY (RRL_NAME) 
 REFERENCES SBR.REL_USAGE_LOV (RRL_NAME));


ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VMV_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VMV_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VM_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT UILL_P_ULK_FK 
 FOREIGN KEY (PARENT_LINK_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT UILL_C_ULK_FK 
 FOREIGN KEY (CHILD_LINK_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));


ALTER TABLE SBR.UI_REFERENCE ADD (
  CONSTRAINT URE_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));


ALTER TABLE SBR.UI_LINK_FRAMESET_RECS ADD (
  CONSTRAINT UILFR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.UI_LINK_FRAMESET_RECS ADD (
  CONSTRAINT UILFR_UIFS_FK 
 FOREIGN KEY (UIFS_IDSEQ) 
 REFERENCES SBR.UI_FRAMESETS (UIFS_IDSEQ));


ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT UIHLR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT UIHLR_UIH_FK 
 FOREIGN KEY (UIH_IDSEQ) 
 REFERENCES SBR.UI_HIERARCHIES (UIH_IDSEQ));

ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT UIHLR_UIAL_FK 
 FOREIGN KEY (UIAL_NAME) 
 REFERENCES SBR.UI_ACTIVITIES_LOV (UIAL_NAME));


GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.ACTIONS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_ACTIONS_MATRIX TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CI_BU TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CONTACTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CSI TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CSI_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CSI_CAT_BU TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CSI_DISEASE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_CSI_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_HISTORIES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_REGISTRATIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_STATUS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_SUBJECTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_WF_BUSINESS_ROLES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.AC_WF_RULES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.ADDR_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.ADMINISTERED_COMPONENTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.ADVANCE_RPT_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_COMPONENT_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_GRANTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_OBJECTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_OBJECTS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_PRIV_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_ROLES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.APP_VERSIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.BUSINESS_ROLES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CD_VMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CHARACTER_SET_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CLASSIFICATION_SCHEMES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CLASSIFICATION_SCHEMES_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CLASS_SCHEME_ITEMS_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CM_STATES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.COMM_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.COMPLEX_DATA_ELEMENTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.COMPLEX_DE_RELATIONSHIPS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.COMPLEX_REP_TYPE_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CONCEPTS_STG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CONCEPTUAL_DOMAINS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CONTACT_ADDRESSES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CONTACT_COMMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CONTEXTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CSI_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CSI_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CSI_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CSI_TYPES_LOV_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_CSI TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_CSI_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_CSI_BU TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_ITEMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.CS_TYPES_LOV_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DATATYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DATA_ELEMENTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DATA_ELEMENTS_BU TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DATA_ELEMENT_CONCEPTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DEC_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DEFINITIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DESIGNATIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DESIGNATIONS_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DESIGNATIONS_BACKUP_NEW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DESIGNATION_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DESIG_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DE_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DE_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.DOCUMENT_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.FAILED_LOG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.FORMATS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.GROUPS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.GROUP_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.GRP_BUSINESS_ROLES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.LANGUAGES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.LIFECYCLES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.LOOKUP_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.META_TEXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.META_UTIL_STATUSES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.OBJECT_CLASSES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.OC_CADSR TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.OC_COMPRESULT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.OC_COMPRESULT2 TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.OC_VD TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.ORGANIZATIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.PERMISSIBLE_VALUES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.PERSONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.PROGRAMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.PROGRAM_AREAS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.PROPERTIES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REFERENCE_BLOBS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REFERENCE_DOCUMENTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REFERENCE_FORMATS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REGISTRARS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REG_STATUS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RELATIONSHIPS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.REL_USAGE_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RL_RUL TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RULES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_AC_CSI TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_CLASSIFICATION_SCHEM TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_CS_CSI TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_CS_ITEMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_DATA_ELEMENTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_DATA_ELEMENT_CONCEPT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_DEFINITIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_DESIGNATIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.RUPD$_VALUE_DOMAINS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SC_CONTEXTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SC_GROUPS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SC_USER_ACCOUNTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SECURITY_CONTEXTS_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.STEWARDS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SUBJECTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SUBMITTERS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.SUCCESS_LOG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_HIER_LINK_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_ITEMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_ITEM_GENERATORS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_ITEM_HIERARCHIES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_ITEM_IMAGES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_ITEM_LINK_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_LINKS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_LINK_FRAMESET_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_LINK_LINK_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_LINK_PARAMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_METADATA TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_REFERENCE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UI_TYPES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.UNIT_OF_MEASURES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.USER_ACCOUNTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.USER_GROUPS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.VALUE_DOMAINS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.VALUE_MEANINGS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.VD_PVS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.VD_PV_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.VD_RECS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.WSGSR_SESSIONS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBR.WSGSR_USERDATA TO MATHURA;



ALTER TABLE SBR.UA_BUSINESS_ROLES ADD (
  CONSTRAINT UBR_ATL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME),
  CONSTRAINT UBR_SCUA_FK 
 FOREIGN KEY (SCUA_IDSEQ) 
 REFERENCES SBR.SC_USER_ACCOUNTS (SCUA_IDSEQ),
  CONSTRAINT UBR_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME));

ALTER TABLE SBR.UI_HIER_LINK_RECS ADD (
  CONSTRAINT UIHLR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDE_DE_FK 
 FOREIGN KEY (C_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ),
  CONSTRAINT CDE_CDT_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.COMPLEX_DATA_ELEMENTS (P_DE_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_PROPE_FK 
 FOREIGN KEY (PROPL_NAME) 
 REFERENCES SBR.PROPERTIES_LOV (PROPL_NAME),
  CONSTRAINT DEC_OC_FK 
 FOREIGN KEY (OCL_NAME) 
 REFERENCES SBR.OBJECT_CLASSES_LOV (OCL_NAME),
  CONSTRAINT DEC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT DEC_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ),
  CONSTRAINT DEC_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.UI_REFERENCE ADD (
  CONSTRAINT URE_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));

ALTER TABLE SBR.UI_ITEM_HIERARCHIES ADD (
  CONSTRAINT P_UIIR_UII_FK 
 FOREIGN KEY (P_UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ),
  CONSTRAINT C_UII_UII_FK 
 FOREIGN KEY (C_UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.VD_PV_RECS ADD (
  CONSTRAINT VPR_VDV_FK2 
 FOREIGN KEY (C_VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ),
  CONSTRAINT VPR_VDV_FK 
 FOREIGN KEY (P_VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ),
  CONSTRAINT VPR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBR.UI_LINK_FRAMESET_RECS ADD (
  CONSTRAINT UILFR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.GROUP_RECS ADD (
  CONSTRAINT PARENT_GRP_FK 
 FOREIGN KEY (PARENT_GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME),
  CONSTRAINT CHILD_GRP_FK 
 FOREIGN KEY (CHILD_GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));

ALTER TABLE SBR.VD_RECS ADD (
  CONSTRAINT VR_C_VD_FK 
 FOREIGN KEY (C_VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ),
  CONSTRAINT VR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT VR_P_VD_FK 
 FOREIGN KEY (P_VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));

ALTER TABLE SBR.GRP_BUSINESS_ROLES ADD (
  CONSTRAINT GBR_SCG_FK 
 FOREIGN KEY (SCG_IDSEQ) 
 REFERENCES SBR.SC_GROUPS (SCG_IDSEQ),
  CONSTRAINT GBR_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME),
  CONSTRAINT GBR_ATL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));

ALTER TABLE SBR.DE_RECS ADD (
  CONSTRAINT DER_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT DER_P_DE_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ),
  CONSTRAINT DER_C_DE_FK 
 FOREIGN KEY (C_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_UOML_FK 
 FOREIGN KEY (UOML_NAME) 
 REFERENCES SBR.UNIT_OF_MEASURES_LOV (UOML_NAME),
  CONSTRAINT VD_FORML_FK 
 FOREIGN KEY (FORML_NAME) 
 REFERENCES SBR.FORMATS_LOV (FORML_NAME),
  CONSTRAINT VD_DTL_FK 
 FOREIGN KEY (DTL_NAME) 
 REFERENCES SBR.DATATYPES_LOV (DTL_NAME),
  CONSTRAINT VD_CSV_FK 
 FOREIGN KEY (CHAR_SET_NAME) 
 REFERENCES SBR.CHARACTER_SET_LOV (CHAR_SET_NAME),
  CONSTRAINT VD_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT VD_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ),
  CONSTRAINT VD_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.AC_REGISTRATIONS ADD (
  CONSTRAINT AR_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ)
    ON DELETE CASCADE,
  CONSTRAINT AR_SUB_FK 
 FOREIGN KEY (SUB_IDSEQ) 
 REFERENCES SBR.SUBMITTERS (SUB_IDSEQ),
  CONSTRAINT AR_RSL_FK 
 FOREIGN KEY (REGISTRATION_STATUS) 
 REFERENCES SBR.REG_STATUS_LOV (REGISTRATION_STATUS),
  CONSTRAINT AR_REGIS_FK 
 FOREIGN KEY (REGIS_IDSEQ) 
 REFERENCES SBR.REGISTRARS (REGIS_IDSEQ),
  CONSTRAINT AR_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.AC_CSI ADD (
  CONSTRAINT AC_CSI_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ),
  CONSTRAINT AC_CSI_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.UI_LINK_LINK_RECS ADD (
  CONSTRAINT UILL_P_ULK_FK 
 FOREIGN KEY (PARENT_LINK_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ),
  CONSTRAINT UILL_C_ULK_FK 
 FOREIGN KEY (CHILD_LINK_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_CSCSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ),
  CONSTRAINT ACT_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ),
  CONSTRAINT ACT_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ),
  CONSTRAINT ACC_AR_FK 
 FOREIGN KEY (AR_IDSEQ) 
 REFERENCES SBR.AC_REGISTRATIONS (AR_IDSEQ));

ALTER TABLE SBR.SC_CONTEXTS ADD (
  CONSTRAINT SCC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.AC_ACTIONS_MATRIX ADD (
  CONSTRAINT AAM_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME),
  CONSTRAINT AAM_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME),
  CONSTRAINT AAM_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT AAM_CMSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME));

ALTER TABLE SBR.SC_USER_ACCOUNTS ADD (
  CONSTRAINT SCUA_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME),
  CONSTRAINT SCUA_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));

ALTER TABLE SBR.RL_RUL ADD (
  CONSTRAINT RRX_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT RRX_RUL_FK 
 FOREIGN KEY (RRL_NAME) 
 REFERENCES SBR.REL_USAGE_LOV (RRL_NAME));

ALTER TABLE SBR.DESIGNATIONS ADD (
  CONSTRAINT DESIG_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME),
  CONSTRAINT DESIG_DT_FK 
 FOREIGN KEY (DETL_NAME) 
 REFERENCES SBR.DESIGNATION_TYPES_LOV (DETL_NAME),
  CONSTRAINT DESIG_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT DESIG_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.ADMINISTERED_COMPONENTS ADD (
  CONSTRAINT AC_STEWA_FK 
 FOREIGN KEY (STEWA_IDSEQ) 
 REFERENCES SBR.STEWARDS (STEWA_IDSEQ),
  CONSTRAINT AC_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT AC_ACTL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME),
  CONSTRAINT AC_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT AC_CSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME));

ALTER TABLE SBR.UI_ITEM_IMAGES ADD (
  CONSTRAINT UIE_UTV_FK 
 FOREIGN KEY (UITL_NAME) 
 REFERENCES SBR.UI_TYPES_LOV (UITL_NAME),
  CONSTRAINT UIIIM_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.UI_ELEMENTS_ITEMS ADD (
  CONSTRAINT UIEI_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.REFERENCE_DOCUMENTS ADD (
  CONSTRAINT RD_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT RD_DCTL_FK 
 FOREIGN KEY (DCTL_NAME) 
 REFERENCES SBR.DOCUMENT_TYPES_LOV (DCTL_NAME),
  CONSTRAINT RD_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME),
  CONSTRAINT RD_AR_FK 
 FOREIGN KEY (AR_IDSEQ) 
 REFERENCES SBR.AC_REGISTRATIONS (AR_IDSEQ),
  CONSTRAINT RD_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ),
  CONSTRAINT RD_ACH_FK 
 FOREIGN KEY (ACH_IDSEQ) 
 REFERENCES SBR.AC_HISTORIES (ACH_IDSEQ),
  CONSTRAINT RD_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VMV_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT VMV_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.COMPLEX_DATA_ELEMENTS ADD (
  CONSTRAINT CDT_CRV_FK 
 FOREIGN KEY (CRTL_NAME) 
 REFERENCES SBR.COMPLEX_REP_TYPE_LOV (CRTL_NAME),
  CONSTRAINT CDT_DE_FK 
 FOREIGN KEY (P_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBR.CS_RECS ADD (
  CONSTRAINT P_CSR_CS_FK 
 FOREIGN KEY (P_CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ),
  CONSTRAINT CSR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT C_CSR_CS_FK 
 FOREIGN KEY (C_CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT CS_CMSL_FK 
 FOREIGN KEY (CMSL_NAME) 
 REFERENCES SBR.CM_STATES_LOV (CMSL_NAME),
  CONSTRAINT CS_CSTL_FK 
 FOREIGN KEY (CSTL_NAME) 
 REFERENCES SBR.CS_TYPES_LOV (CSTL_NAME),
  CONSTRAINT CS_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.CSI_RECS ADD (
  CONSTRAINT P_CSIR_CSI_FK 
 FOREIGN KEY (P_CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ),
  CONSTRAINT CSIR_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT C_CSIR_CSI_FK 
 FOREIGN KEY (C_CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ));

ALTER TABLE SBR.AC_RECS ADD (
  CONSTRAINT ARS_OVRID_AC_FK 
 FOREIGN KEY (OVRID_AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ),
  CONSTRAINT ARS_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.AC_SUBJECTS ADD (
  CONSTRAINT ACSUB_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.DEC_RECS ADD (
  CONSTRAINT DRC_C_DEC_FK 
 FOREIGN KEY (C_DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ),
  CONSTRAINT DRC_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT DRC_P_DEC_FK 
 FOREIGN KEY (P_DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ));

ALTER TABLE SBR.REGISTRARS ADD (
  CONSTRAINT REGIS_ORGAN_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.USER_GROUPS ADD (
  CONSTRAINT UGP_GRP_FK 
 FOREIGN KEY (GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME),
  CONSTRAINT UGP_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));

ALTER TABLE SBR.REFERENCE_BLOBS ADD (
  CONSTRAINT RB_RD_FK 
 FOREIGN KEY (RD_IDSEQ) 
 REFERENCES SBR.REFERENCE_DOCUMENTS (RD_IDSEQ));

ALTER TABLE SBR.AC_WF_BUSINESS_ROLES ADD (
  CONSTRAINT AWB_BRL_FK 
 FOREIGN KEY (BRL_NAME) 
 REFERENCES SBR.BUSINESS_ROLES_LOV (BRL_NAME),
  CONSTRAINT AWB_AWM_FK 
 FOREIGN KEY (AWR_IDSEQ) 
 REFERENCES SBR.AC_WF_RULES (AWR_IDSEQ));

ALTER TABLE SBR.APP_GRANTS ADD (
  CONSTRAINT AGT_AOT_FK 
 FOREIGN KEY (APPOBJ_IDSEQ) 
 REFERENCES SBR.APP_OBJECTS (APPOBJ_IDSEQ),
  CONSTRAINT AGT_ARL_FK 
 FOREIGN KEY (ROLE_NAME) 
 REFERENCES SBR.APP_ROLES_LOV (ROLE_NAME),
  CONSTRAINT AGT_APV_FK 
 FOREIGN KEY (PRIV_NAME) 
 REFERENCES SBR.APP_PRIV_LOV (PRIV_NAME));

ALTER TABLE SBR.AC_HISTORIES ADD (
  CONSTRAINT SOURCE_AC_FK 
 FOREIGN KEY (SOURCE_AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ),
  CONSTRAINT AH_AL_FK 
 FOREIGN KEY (AL_NAME) 
 REFERENCES SBR.ACTIONS_LOV (AL_NAME),
  CONSTRAINT AH_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEFIN_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ),
  CONSTRAINT DEFIN_LAE_FK 
 FOREIGN KEY (LAE_NAME) 
 REFERENCES SBR.LANGUAGES_LOV (NAME),
  CONSTRAINT DEFIN_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBR.UI_ITEM_LINK_RECS ADD (
  CONSTRAINT UIILR_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ),
  CONSTRAINT UIILR_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT CSI_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT CSI_CSITL_FK 
 FOREIGN KEY (CSITL_NAME) 
 REFERENCES SBR.CSI_TYPES_LOV (CSITL_NAME));

ALTER TABLE SBR.UI_LINK_PARAMS ADD (
  CONSTRAINT UILP_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ));

ALTER TABLE SBR.USER_ACCOUNTS ADD (
  CONSTRAINT UA_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.PERSONS ADD (
  CONSTRAINT PER_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.STEWARDS ADD (
  CONSTRAINT STEWA_ORGAN_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENTS ADD (
  CONSTRAINT DE_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT DE_DEC_FK 
 FOREIGN KEY (DEC_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENT_CONCEPTS (DEC_IDSEQ),
  CONSTRAINT DE_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT DE_VD_FK 
 FOREIGN KEY (VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));

ALTER TABLE SBR.APP_OBJECTS ADD (
  CONSTRAINT AOT_DVN_FK 
 FOREIGN KEY (APP_VERSION) 
 REFERENCES SBR.APP_VERSIONS (APP_VERSION),
  CONSTRAINT AOT_AOV_FK 
 FOREIGN KEY (OBJ_NAME) 
 REFERENCES SBR.APP_OBJECTS_LOV (OBJ_NAME),
  CONSTRAINT AOT_APCTL_FK 
 FOREIGN KEY (APCTL_NAME) 
 REFERENCES SBR.APP_COMPONENT_TYPES_LOV (APCTL_NAME));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VP_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT VP_VD_FK 
 FOREIGN KEY (VD_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ),
  CONSTRAINT VP_PV_FK 
 FOREIGN KEY (PV_IDSEQ) 
 REFERENCES SBR.PERMISSIBLE_VALUES (PV_IDSEQ));

ALTER TABLE SBR.CS_CSI ADD (
  CONSTRAINT CS_CSI_CSI_FK 
 FOREIGN KEY (CSI_IDSEQ) 
 REFERENCES SBR.CS_ITEMS (CSI_IDSEQ),
  CONSTRAINT CS_CSI_CS_FK 
 FOREIGN KEY (CS_IDSEQ) 
 REFERENCES SBR.CLASSIFICATION_SCHEMES (CS_IDSEQ));

ALTER TABLE SBR.AC_WF_RULES ADD (
  CONSTRAINT AWR_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME),
  CONSTRAINT AWR_ASL_FROM_FK 
 FOREIGN KEY (FROM_ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT AWR_ASL_TO_FK 
 FOREIGN KEY (TO_ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.SUBMITTERS ADD (
  CONSTRAINT SUBMI_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBR.CD_VMS ADD (
  CONSTRAINT CV_CD_FK 
 FOREIGN KEY (CD_IDSEQ) 
 REFERENCES SBR.CONCEPTUAL_DOMAINS (CD_IDSEQ),
  CONSTRAINT CD_VM_FK 
 FOREIGN KEY (VM_IDSEQ) 
 REFERENCES SBR.VALUE_MEANINGS (VM_IDSEQ));

ALTER TABLE SBR.UI_ELEMENTS ADD (
  CONSTRAINT UIE_UITL_FK 
 FOREIGN KEY (UITL_NAME) 
 REFERENCES SBR.UI_TYPES_LOV (UITL_NAME));

ALTER TABLE SBR.UI_CONSTRAINTS ADD (
  CONSTRAINT UICON_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.UI_ITEM_GENERATORS ADD (
  CONSTRAINT UIG_UIL_FK 
 FOREIGN KEY (UIL_IDSEQ) 
 REFERENCES SBR.UI_LINKS (UIL_IDSEQ),
  CONSTRAINT UIG_UII_FK 
 FOREIGN KEY (UII_IDSEQ) 
 REFERENCES SBR.UI_ITEMS (UII_IDSEQ));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT CD_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBR.CONTEXTS ADD (
  CONSTRAINT CONTE_LL_FK 
 FOREIGN KEY (LL_NAME) 
 REFERENCES SBR.LIFECYCLES_LOV (LL_NAME),
  CONSTRAINT CONTE_PAL_FK 
 FOREIGN KEY (PAL_NAME) 
 REFERENCES SBR.PROGRAM_AREAS_LOV (PAL_NAME));

ALTER TABLE SBR.SC_GROUPS ADD (
  CONSTRAINT SCG_SCL_FK 
 FOREIGN KEY (SCL_NAME) 
 REFERENCES SBR.SECURITY_CONTEXTS_LOV (SCL_NAME),
  CONSTRAINT SCG_GRP_FK 
 FOREIGN KEY (GRP_NAME) 
 REFERENCES SBR.GROUPS (GRP_NAME));

ALTER TABLE SBR.CONTACT_ADDRESSES ADD (
  CONSTRAINT CADDR_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ),
  CONSTRAINT CADDR_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ),
  CONSTRAINT CADDR_ATL_FK 
 FOREIGN KEY (ATL_NAME) 
 REFERENCES SBR.ADDR_TYPES_LOV (ATL_NAME));

ALTER TABLE SBR.PERMISSIBLE_VALUES ADD (
  CONSTRAINT PV_VM_FK 
 FOREIGN KEY (VM_IDSEQ) 
 REFERENCES SBR.VALUE_MEANINGS (VM_IDSEQ));

ALTER TABLE SBR.CONTACT_COMMS ADD (
  CONSTRAINT CCOMM_PER_FK 
 FOREIGN KEY (PER_IDSEQ) 
 REFERENCES SBR.PERSONS (PER_IDSEQ),
  CONSTRAINT CCOMM_ORG_FK 
 FOREIGN KEY (ORG_IDSEQ) 
 REFERENCES SBR.ORGANIZATIONS (ORG_IDSEQ),
  CONSTRAINT CCOMM_CTL_FK 
 FOREIGN KEY (CTL_NAME) 
 REFERENCES SBR.COMM_TYPES_LOV (CTL_NAME));

ALTER TABLE SBR.DOCUMENT_TYPES_LOV ADD (
  CONSTRAINT DCTL_ACTLV_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));


ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QC_VPV_FK 
 FOREIGN KEY (VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ),
  CONSTRAINT QC_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT QC_DET_FK 
 FOREIGN KEY (DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ),
  CONSTRAINT QC_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.MATCH_RESULTS_EXT ADD (
  CONSTRAINT MRT_RDT_FK 
 FOREIGN KEY (RD_MATCH_IDSEQ) 
 REFERENCES SBR.REFERENCE_DOCUMENTS (RD_IDSEQ),
  CONSTRAINT MRT_DET_FK 
 FOREIGN KEY (DE_MATCH_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ),
  CONSTRAINT MRT_ASV_FK 
 FOREIGN KEY (ASL_NAME_OF_MATCH) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT MRT_VDN_FK 
 FOREIGN KEY (VD_MATCH_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_DE_FK 
 FOREIGN KEY (T_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBREXT.USERS_LOCKOUT ADD (
  CONSTRAINT UL_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT REP_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ)
    ON DELETE CASCADE,
  CONSTRAINT SPT_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.VD_PVS_SOURCES_EXT ADD (
  CONSTRAINT VPST_VPV_FK 
 FOREIGN KEY (VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME),
  CONSTRAINT ORT_AC_CSI_FK 
 FOREIGN KEY (T_AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ),
  CONSTRAINT ORT_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT ORT_AC_CSI_FK2 
 FOREIGN KEY (S_AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ),
  CONSTRAINT ORT_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.ASL_ACTL_EXT ADD (
  CONSTRAINT AAT_ATL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME),
  CONSTRAINT AAT_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.TOOL_OPTIONS_EXT ADD (
  CONSTRAINT TL_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_ACTL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME),
  CONSTRAINT CCM_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ),
  CONSTRAINT CCM_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));

ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_AC_CSI_FK 
 FOREIGN KEY (AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ));

ALTER TABLE SBREXT.CRF_TOOL_PARAMETER_EXT ADD (
  CONSTRAINT CTP_UAT_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));

ALTER TABLE SBREXT.QC_RECS_EXT ADD (
  CONSTRAINT QRS_RLV_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBREXT.AC_ATT_CSCSI_EXT ADD (
  CONSTRAINT AAI_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME),
  CONSTRAINT PROP_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT PROTO_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT PROTO_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.CONCEPTS_EXT ADD (
  CONSTRAINT CON_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT CON_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.CON_DERIVATION_RULES_EXT ADD (
  CONSTRAINT CONDR_CRV_FK 
 FOREIGN KEY (CRTL_NAME) 
 REFERENCES SBR.COMPLEX_REP_TYPE_LOV (CRTL_NAME));

ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ),
  CONSTRAINT OCT_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.AC_SOURCES_EXT ADD (
  CONSTRAINT AST_ACT_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


CREATE TABLE SBREXT.COMPONENT_CONCEPTS_EXT
(
  CC_IDSEQ          CHAR(36 BYTE)               NOT NULL,
  CONDR_IDSEQ       CHAR(36 BYTE)               NOT NULL,
  CON_IDSEQ         CHAR(36 BYTE)               NOT NULL,
  DISPLAY_ORDER     NUMBER                      NOT NULL,
  DATE_CREATED      DATE                            NULL,
  DATE_MODIFIED     DATE                            NULL,
  MODIFIED_BY       VARCHAR2(30 BYTE)               NULL,
  CREATED_BY        VARCHAR2(30 BYTE)               NULL,
  PRIMARY_FLAG_IND  VARCHAR2(3 BYTE)                NULL,
  CL_IDSEQ          CHAR(36 BYTE)                   NULL,
  CONCEPT_VALUE     VARCHAR2(255 BYTE)              NULL
);


CREATE TABLE SBREXT.SN_ALERT_EXT
(
  AL_IDSEQ         CHAR(36 BYTE)                NOT NULL,
  NAME             VARCHAR2(30 BYTE)            NOT NULL,
  LAST_AUTO_RUN    DATE                             NULL,
  LAST_MANUAL_RUN  DATE                             NULL,
  AUTO_FREQ_UNIT   VARCHAR2(30 BYTE)                NULL,
  AL_STATUS        CHAR(1 BYTE)                     NULL,
  BEGIN_DATE       DATE                             NULL,
  END_DATE         DATE                             NULL,
  STATUS_REASON    VARCHAR2(2000 BYTE)              NULL,
  DATE_CREATED     DATE                             NULL,
  DATE_MODIFIED    DATE                             NULL,
  MODIFIED_BY      VARCHAR2(30 BYTE)                NULL,
  CREATED_BY       VARCHAR2(30 BYTE)                NULL,
  AUTO_FREQ_VALUE  NUMBER                           NULL
);


CREATE TABLE SBREXT.OBJECT_CLASSES_STAGING
(
  ID                              NUMBER(11)    NOT NULL,
  SDE_ID                          NUMBER(11)    NOT NULL,
  OC_VERSION                      NUMBER(4,2)   NOT NULL,
  OC_PREFERRED_NAME               VARCHAR2(30 BYTE) NOT NULL,
  OC_CONTE_NAME                   VARCHAR2(30 BYTE) NOT NULL,
  OC_CONTE_VERSION                NUMBER(4,2)       NULL,
  OC_PREFERRED_DEFINITION         VARCHAR2(2000 BYTE) NOT NULL,
  OC_LONG_NAME                    VARCHAR2(255 BYTE)     NULL,
  OC_WORKFLOW_STATUS              VARCHAR2(20 BYTE) NOT NULL,
  BEGIN_DATE                      VARCHAR2(30 BYTE)     NULL,
  END_DATE                        VARCHAR2(30 BYTE)     NULL,
  ADMIN_NOTE                      VARCHAR2(2000 BYTE)     NULL,
  COMMENTS                        VARCHAR2(2000 BYTE)     NULL,
  ORIGIN                          VARCHAR2(240 BYTE)     NULL,
  UNRESOLVED_ISSUE                VARCHAR2(200 BYTE)     NULL,
  EXAMPLE                         VARCHAR2(2000 BYTE)     NULL,
  RECORD_STATUS                   VARCHAR2(50 BYTE)     NULL,
  DATE_CREATED                    DATE          NOT NULL,
  CREATED_BY                      VARCHAR2(30 BYTE) NOT NULL,
  OC_PREFERRED_DEFINITION_SOURCE  VARCHAR2(2000 BYTE)     NULL
);


CREATE TABLE SBREXT.QC_RECS_EXT
(
  QR_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  P_QC_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  C_QC_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DISPLAY_ORDER  NUMBER(6)                      NOT NULL,
  RL_NAME        VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.TMP_TAB
(
  COLUMN1  VARCHAR2(100 BYTE)                       NULL,
  COL2     VARCHAR2(100 BYTE)                       NULL
);


CREATE TABLE SBREXT.ICD
(
  SHORT_MEANING  VARCHAR2(500 BYTE)                 NULL,
  DESCRIPTION    VARCHAR2(500 BYTE)                 NULL,
  CODE           VARCHAR2(500 BYTE)                 NULL,
  VALUE_DOMAIN   VARCHAR2(500 BYTE)                 NULL
);


CREATE TABLE SBREXT.UML_LOADER_DEFAULTS
(
  ID                   NUMBER(11)               NOT NULL,
  CONTEXT_NAME         VARCHAR2(30 BYTE)            NULL,
  CONTEXT_VERSION      NUMBER(4,2)                  NULL,
  PROJECT_NAME         VARCHAR2(100 BYTE)           NULL,
  PROJECT_VERSION      NUMBER(4,2)                  NULL,
  VERSION              NUMBER(4,2)                  NULL,
  WORKFLOW_STATUS      VARCHAR2(20 BYTE)            NULL,
  CD_PREFERRED_NAME    VARCHAR2(30 BYTE)            NULL,
  CD_CONTE_NAME        VARCHAR2(30 BYTE)            NULL,
  PACKAGE_FILTER       VARCHAR2(2000 BYTE)          NULL,
  PROJECT_LONG_NAME    VARCHAR2(255 BYTE)           NULL,
  PROJECT_DESCRIPTION  VARCHAR2(1000 BYTE)          NULL
);


CREATE TABLE SBREXT.ESUPERTYPES_STAGING
(
  ID             NUMBER(11)                     NOT NULL,
  ECL_ID         NUMBER(11)                     NOT NULL,
  NAME           VARCHAR2(250 BYTE)             NOT NULL,
  INTERFACE_IND  VARCHAR2(3 BYTE)                   NULL,
  ABSTRACT_IND   VARCHAR2(3 BYTE)                   NULL
);


CREATE TABLE SBREXT.CONCEPTS_EXT
(
  CON_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                        NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CON_ID                NUMBER                  NOT NULL,
  EVS_SOURCE            VARCHAR2(255 BYTE)          NULL
);


CREATE TABLE SBREXT.DEC_STAGING
(
  ID                  NUMBER(11)                NOT NULL,
  SDE_ID              NUMBER(11)                NOT NULL,
  DEC_PREFERRED_NAME  VARCHAR2(30 BYTE)         NOT NULL,
  DEC_VERSION         NUMBER(4,2)               NOT NULL,
  DEC_CONTE_NAME      VARCHAR2(30 BYTE)         NOT NULL,
  DEC_CONTE_VERSION   NUMBER(4,2)               NOT NULL,
  RECORD_STATUS       VARCHAR2(50 BYTE)             NULL,
  DATE_CREATED        DATE                      NOT NULL,
  CREATED_BY          VARCHAR2(30 BYTE)         NOT NULL
);


CREATE TABLE SBREXT.TRIGGERED_ACTIONS_EXT
(
  TA_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  S_QC_IDSEQ            CHAR(36 BYTE)               NULL,
  T_QC_IDSEQ            CHAR(36 BYTE)               NULL,
  S_QR_IDSEQ            CHAR(36 BYTE)               NULL,
  T_QR_IDSEQ            CHAR(36 BYTE)               NULL,
  ACTION                VARCHAR2(100 BYTE)          NULL,
  CRITERION_VALUE       VARCHAR2(255 BYTE)          NULL,
  TRIGGER_REALTIONSHIP  VARCHAR2(255 BYTE)          NULL,
  FORCED_VALUE          VARCHAR2(255 BYTE)          NULL,
  DATE_CREATED          DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  TA_INSTRUCTION        VARCHAR2(2000 BYTE)         NULL,
  T_DE_IDSEQ            CHAR(36 BYTE)               NULL,
  T_MODULE_NAME         VARCHAR2(255 BYTE)          NULL,
  T_QUESTION_NAME       VARCHAR2(255 BYTE)          NULL,
  T_CDE_ID              NUMBER                      NULL,
  T_DE_VERSION          NUMBER                      NULL,
  T_QTL_NAME            VARCHAR2(30 BYTE)           NULL,
  S_QTL_NAME            VARCHAR2(30 BYTE)           NULL,
  FORCED_QCON_IDSEQ     CHAR(36 BYTE)               NULL,
  QCON_IDSEQ            CHAR(36 BYTE)               NULL
);


CREATE TABLE SBREXT.OBJECT_CLASSES_EXT
(
  OC_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                        NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  OC_ID                 NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL
);


CREATE TABLE SBREXT.CONCEPTUAL_DOMAINS_STAGING
(
  ID                       NUMBER(11)           NOT NULL,
  SDE_ID                   NUMBER(11)           NOT NULL,
  CD_VERSION               NUMBER(4,2)          NOT NULL,
  CD_PREFERRED_NAME        VARCHAR2(30 BYTE)    NOT NULL,
  CD_CONTE_NAME            VARCHAR2(30 BYTE)    NOT NULL,
  CD_CONTE_VERSION         NUMBER(4,2)              NULL,
  CD_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)  NOT NULL,
  DIMENSIONALITY           VARCHAR2(30 BYTE)        NULL,
  CD_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  CD_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  ADMIN_NOTE               VARCHAR2(2000 BYTE)      NULL,
  COMMENTS                 VARCHAR2(2000 BYTE)      NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  EXAMPLE                  VARCHAR2(2000 BYTE)      NULL,
  RECORD_STATUS            VARCHAR2(50 BYTE)        NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL
);


CREATE TABLE SBREXT.SOURCES_EXT
(
  SRC_NAME       VARCHAR2(240 BYTE)             NOT NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.QUEST_CONTENTS_EXT
(
  QC_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  VERSION                     NUMBER(4,2)       NOT NULL,
  QTL_NAME                    VARCHAR2(30 BYTE) NOT NULL,
  CONTE_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  ASL_NAME                    VARCHAR2(20 BYTE) NOT NULL,
  PREFERRED_NAME              VARCHAR2(30 BYTE) NOT NULL,
  PREFERRED_DEFINITION        VARCHAR2(2000 BYTE) NOT NULL,
  PROTO_IDSEQ                 CHAR(36 BYTE)         NULL,
  DE_IDSEQ                    CHAR(36 BYTE)         NULL,
  VP_IDSEQ                    CHAR(36 BYTE)         NULL,
  QC_MATCH_IDSEQ              CHAR(36 BYTE)         NULL,
  QC_IDENTIFIER               VARCHAR2(30 BYTE)     NULL,
  QCDL_NAME                   VARCHAR2(20 BYTE)     NULL,
  LONG_NAME                   VARCHAR2(255 BYTE)     NULL,
  LATEST_VERSION_IND          VARCHAR2(3 BYTE)      NULL,
  DELETED_IND                 VARCHAR2(3 BYTE)      NULL,
  BEGIN_DATE                  DATE                  NULL,
  END_DATE                    DATE                  NULL,
  MATCH_IND                   VARCHAR2(1 BYTE)      NULL,
  NEW_QC_IND                  VARCHAR2(3 BYTE)      NULL,
  HIGHLIGHT_IND               VARCHAR2(3 BYTE)      NULL,
  REVIEWER_FEEDBACK_ACTION    VARCHAR2(240 BYTE)     NULL,
  REVIEWER_FEEDBACK_INTERNAL  VARCHAR2(240 BYTE)     NULL,
  REVIEWER_FEEDBACK_EXTERNAL  VARCHAR2(240 BYTE)     NULL,
  SYSTEM_MSGS                 VARCHAR2(2000 BYTE)     NULL,
  REVIEWED_BY                 VARCHAR2(30 BYTE)     NULL,
  REVIEWED_DATE               DATE                  NULL,
  APPROVED_BY                 VARCHAR2(30 BYTE)     NULL,
  APPROVED_DATE               DATE                  NULL,
  CDE_DICTIONARY_ID           NUMBER(10)            NULL,
  DATE_CREATED                DATE              NOT NULL,
  CREATED_BY                  VARCHAR2(30 BYTE) NOT NULL,
  DATE_MODIFIED               DATE                  NULL,
  MODIFIED_BY                 VARCHAR2(30 BYTE)     NULL,
  CHANGE_NOTE                 VARCHAR2(2000 BYTE)     NULL,
  SUBMITTED_LONG_CDE_NAME     VARCHAR2(4000 BYTE)     NULL,
  GROUP_COMMENTS              VARCHAR2(4000 BYTE)     NULL,
  SRC_NAME                    VARCHAR2(100 BYTE)     NULL,
  P_MOD_IDSEQ                 CHAR(36 BYTE)         NULL,
  P_QST_IDSEQ                 CHAR(36 BYTE)         NULL,
  P_VAL_IDSEQ                 CHAR(36 BYTE)         NULL,
  DN_CRF_IDSEQ                CHAR(36 BYTE)         NULL,
  DN_VD_IDSEQ                 CHAR(36 BYTE)         NULL,
  DISPLAY_IND                 VARCHAR2(3 BYTE)      NULL,
  GROUP_ACTION                VARCHAR2(240 BYTE)     NULL,
  DE_LONG_NAME                VARCHAR2(255 BYTE)     NULL,
  VD_LONG_NAME                VARCHAR2(255 BYTE)     NULL,
  DEC_LONG_NAME               VARCHAR2(255 BYTE)     NULL,
  DISPLAY_ORDER               NUMBER(6)             NULL,
  ORIGIN                      VARCHAR2(240 BYTE)     NULL,
  QC_ID                       NUMBER            NOT NULL,
  GROUP_INTERNAL_COMMENTS     VARCHAR2(4000 BYTE)     NULL,
  REPEAT_NO                   NUMBER                NULL
);

COMMENT ON COLUMN SBREXT.QUEST_CONTENTS_EXT.QCDL_NAME IS 'Created to hold QC display types.  For CRFs, will hold types, such as "Registration", "Follow-up" forms etc.';


CREATE TABLE SBREXT.MATCH_RESULTS_EXT
(
  QC_CRF_IDSEQ       CHAR(36 BYTE)              NOT NULL,
  QC_SUBMIT_IDSEQ    CHAR(36 BYTE)              NOT NULL,
  RD_MATCH_IDSEQ     CHAR(36 BYTE)                  NULL,
  DE_MATCH_IDSEQ     CHAR(36 BYTE)                  NULL,
  QC_MATCH_IDSEQ     CHAR(36 BYTE)                  NULL,
  VD_MATCH_IDSEQ     CHAR(36 BYTE)                  NULL,
  PV_MATCH_IDSEQ     CHAR(36 BYTE)                  NULL,
  ASL_NAME_OF_MATCH  VARCHAR2(20 BYTE)              NULL,
  VERSION_OF_MATCH   NUMBER(4,2)                    NULL,
  MATCH_IND          VARCHAR2(1 BYTE)           NOT NULL,
  MATCH_SCORE        NUMBER(3)                      NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL,
  RANK_OF_MATCH      NUMBER(3)                      NULL,
  DESIG_MATCH_IDSEQ  CHAR(36 BYTE)                  NULL
);


CREATE TABLE SBREXT.QUAL_STG
(
  QUAL_NAME  VARCHAR2(255 BYTE)                     NULL,
  CON_IDSEQ  VARCHAR2(255 BYTE)                     NULL
);


CREATE TABLE SBREXT.PROPERTIES_EXT
(
  PROP_IDSEQ            CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  PROP_ID               NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL
);


CREATE TABLE SBREXT.SN_REP_CONTENTS_EXT
(
  CTT_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  REP_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  TABLE_NAME     VARCHAR2(30 BYTE)                  NULL,
  COLUMN_NAME    VARCHAR2(30 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.VD_PVS_HST
(
  VP_HST_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  VP_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  VD_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  PV_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                      NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.VALUE_DOMAINS_STAGING
(
  ID                       NUMBER(11)           NOT NULL,
  VD_VERSION               NUMBER(4,2)          NOT NULL,
  VD_PREFERRED_NAME        VARCHAR2(30 BYTE)        NULL,
  VD_CONTE_VERSION         NUMBER(4,2)          NOT NULL,
  VD_CONTE_NAME            VARCHAR2(30 BYTE)    NOT NULL,
  VD_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)  NOT NULL,
  DTL_NAME                 VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  VD_CD_CONTE_VERSION      NUMBER(4,2)          NOT NULL,
  VD_CD_CONTE_NAME         VARCHAR2(30 BYTE)    NOT NULL,
  VD_CD_PREFERRED_NAME     VARCHAR2(30 BYTE)    NOT NULL,
  VD_CD_VERSION            NUMBER(4,2)          NOT NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  VD_TYPE_FLAG             VARCHAR2(50 BYTE)    NOT NULL,
  VD_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  UOML_NAME                VARCHAR2(20 BYTE)        NULL,
  FORML_NAME               VARCHAR2(20 BYTE)        NULL,
  MAX_LENGTH_NUM           NUMBER(8)                NULL,
  MIN_LENGTH_NUM           NUMBER(8)                NULL,
  DECIMAL_PLACE            NUMBER(2)                NULL,
  CHAR_SET_NAME            VARCHAR2(20 BYTE)        NULL,
  HIGH_VALUE_NUM           VARCHAR2(255 BYTE)       NULL,
  LOW_VALUE_NUM            VARCHAR2(255 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL,
  VD_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  SDE_ID                   NUMBER(11)           NOT NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  ADMIN_NOTE               VARCHAR2(2000 BYTE)      NULL,
  COMMENTS                 VARCHAR2(2000 BYTE)      NULL,
  EXAMPLE                  VARCHAR2(2000 BYTE)      NULL,
  RECORD_STATUS            VARCHAR2(100 BYTE)       NULL,
  DATA_TYPE_DESCRIPTION    VARCHAR2(60 BYTE)        NULL,
  DATA_TYPE_COMMENTS       VARCHAR2(2000 BYTE)      NULL,
  FORMAT_COMMENTS          VARCHAR2(2000 BYTE)      NULL,
  FORMAT_DESCRIPTION       VARCHAR2(60 BYTE)        NULL,
  CHAR_SET_DESCRIPTION     VARCHAR2(60 BYTE)        NULL,
  UOML_PRECISION           VARCHAR2(30 BYTE)        NULL,
  UOML_DESCRIPTION         VARCHAR2(60 BYTE)        NULL,
  UOML_COMMENTS            VARCHAR2(2000 BYTE)      NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  REP_PREFERRED_NAME       VARCHAR2(30 BYTE)        NULL,
  REP_VERSION              NUMBER                   NULL,
  REP_CONTE_NAME           VARCHAR2(30 BYTE)        NULL,
  REP_CONTE_VERSION        NUMBER                   NULL,
  REP_QUALIFIER            VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBREXT.VALUE_DOMAINS_HST
(
  VD_HST_IDSEQ          CHAR(36 BYTE)           NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  DTL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  END_DATE              DATE                        NULL,
  VD_TYPE_FLAG          VARCHAR2(1 BYTE)        NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  UOML_NAME             VARCHAR2(20 BYTE)           NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  FORML_NAME            VARCHAR2(20 BYTE)           NULL,
  HIGH_VALUE_NUM        VARCHAR2(255 BYTE)          NULL,
  LOW_VALUE_NUM         VARCHAR2(255 BYTE)          NULL,
  MAX_LENGTH_NUM        NUMBER(8)               NOT NULL,
  MIN_LENGTH_NUM        NUMBER(8)                   NULL,
  DECIMAL_PLACE         NUMBER(2)                   NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHAR_SET_NAME         VARCHAR2(20 BYTE)           NULL
);


CREATE TABLE SBREXT.VALID_VALUES_ATT_EXT
(
  QC_IDSEQ          CHAR(36 BYTE)               NOT NULL,
  MEANING_TEXT      VARCHAR2(2000 BYTE)             NULL,
  DATE_CREATED      DATE                        NOT NULL,
  CREATED_BY        VARCHAR2(30 BYTE)           NOT NULL,
  DATE_MODIFIED     DATE                            NULL,
  MODIFIED_BY       VARCHAR2(30 BYTE)               NULL,
  DESCRIPTION_TEXT  VARCHAR2(2000 BYTE)             NULL
);


CREATE TABLE SBREXT.USERS_LOCKOUT
(
  UA_NAME          VARCHAR2(30 BYTE)            NOT NULL,
  LOCKOUT_COUNT    NUMBER                           NULL,
  VALIDATION_TIME  DATE                             NULL
);


CREATE TABLE SBREXT.MLOG$_CON_DERIVATION_RULES
(
  CONDR_IDSEQ      CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_CON_DERIVATION_RULES IS 'snapshot log for master table SBREXT.CON_DERIVATION_RULES_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_CON_DERIVATION_RULES
(
  CONDR_IDSEQ      CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_CON_DERIVATION_RULES IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.MLOG$_CONCEPTS_EXT
(
  CON_IDSEQ        CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_CONCEPTS_EXT IS 'snapshot log for master table SBREXT.CONCEPTS_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_CONCEPTS_EXT
(
  CON_IDSEQ        CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_CONCEPTS_EXT IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.MLOG$_COMPONENT_LEVELS_EXT
(
  CL_IDSEQ         CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_COMPONENT_LEVELS_EXT IS 'snapshot log for master table SBREXT.COMPONENT_LEVELS_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_COMPONENT_LEVELS_EXT
(
  CL_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_COMPONENT_LEVELS_EXT IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.MLOG$_COMPONENT_CONCEPTS_E
(
  CC_IDSEQ         CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_COMPONENT_CONCEPTS_E IS 'snapshot log for master table SBREXT.COMPONENT_CONCEPTS_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_COMPONENT_CONCEPTS_E
(
  CC_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_COMPONENT_CONCEPTS_E IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.MLOG$_AC_ATT_CSCSI_EXT
(
  ACA_IDSEQ        CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_AC_ATT_CSCSI_EXT IS 'snapshot log for master table SBREXT.AC_ATT_CSCSI_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_AC_ATT_CSCSI_EXT
(
  ACA_IDSEQ        CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_AC_ATT_CSCSI_EXT IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.CDE_CART_ITEMS
(
  CCM_IDSEQ     CHAR(36 BYTE)                   NOT NULL,
  AC_IDSEQ      CHAR(36 BYTE)                   NOT NULL,
  UA_NAME       VARCHAR2(30 BYTE)               NOT NULL,
  ACTL_NAME     VARCHAR2(20 BYTE)               NOT NULL,
  DATE_CREATED  DATE                            NOT NULL,
  CREATED_BY    VARCHAR2(30 BYTE)               NOT NULL
);


CREATE TABLE SBREXT.QUALIFIER_LOV_EXT
(
  QUALIFIER_NAME  VARCHAR2(30 BYTE)             NOT NULL,
  DESCRIPTION     VARCHAR2(60 BYTE)                 NULL,
  COMMENTS        VARCHAR2(200 BYTE)                NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_CREATED    DATE                          NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL,
  CON_IDSEQ       CHAR(36 BYTE)                     NULL,
  CONDR_IDSEQ     CHAR(36 BYTE)                     NULL
);


CREATE TABLE SBREXT.REPRESENTATIONS_STAGING
(
  ID                        NUMBER(11)          NOT NULL,
  SDE_ID                    NUMBER(11)          NOT NULL,
  REP_VERSION               NUMBER(4,2)         NOT NULL,
  REP_PREFERRED_NAME        VARCHAR2(30 BYTE)   NOT NULL,
  REP_CONTE_NAME            VARCHAR2(30 BYTE)   NOT NULL,
  REP_CONTE_VERSION         NUMBER(4,2)             NULL,
  REP_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE) NOT NULL,
  REP_LONG_NAME             VARCHAR2(255 BYTE)      NULL,
  REP_WORKFLOW_STATUS       VARCHAR2(20 BYTE)   NOT NULL,
  BEGIN_DATE                VARCHAR2(30 BYTE)       NULL,
  END_DATE                  VARCHAR2(30 BYTE)       NULL,
  ADMIN_NOTE                VARCHAR2(2000 BYTE)     NULL,
  COMMENTS                  VARCHAR2(2000 BYTE)     NULL,
  ORIGIN                    VARCHAR2(240 BYTE)      NULL,
  UNRESOLVED_ISSUE          VARCHAR2(200 BYTE)      NULL,
  EXAMPLE                   VARCHAR2(2000 BYTE)     NULL,
  RECORD_STATUS             VARCHAR2(50 BYTE)       NULL,
  DATE_CREATED              DATE                NOT NULL,
  CREATED_BY                VARCHAR2(30 BYTE)   NOT NULL,
  REP_PREFERRED_DEF_SOURCE  VARCHAR2(2000 BYTE)     NULL
);


CREATE TABLE SBREXT.UI_MENU_TREE_EXT
(
  NODE           VARCHAR2(20 BYTE)              NOT NULL,
  PARENT_NODE    VARCHAR2(20 BYTE)                  NULL,
  DISPLAY_NAME   VARCHAR2(20 BYTE)              NOT NULL,
  DISPLAY_ORDER  NUMBER(3)                      NOT NULL,
  IMAGE          VARCHAR2(255 BYTE)                 NULL,
  URL            VARCHAR2(255 BYTE)                 NULL,
  ENABLED        VARCHAR2(3 BYTE)                   NULL,
  COMMENTS       VARCHAR2(255 BYTE)                 NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.AC_CHANGE_HISTORY_EXT
(
  ACCH_IDSEQ            CHAR(36 BYTE)           NOT NULL,
  AC_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  CHANGE_DATETIMESTAMP  DATE                    NOT NULL,
  CHANGE_ACTION         VARCHAR2(10 BYTE)       NOT NULL,
  CHANGED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  CHANGED_TABLE         VARCHAR2(30 BYTE)       NOT NULL,
  CHANGED_TABLE_IDSEQ   CHAR(36 BYTE)           NOT NULL,
  CHANGED_COLUMN        VARCHAR2(30 BYTE)       NOT NULL,
  OLD_VALUE             VARCHAR2(4000 BYTE)         NULL,
  NEW_VALUE             VARCHAR2(4000 BYTE)         NULL
);


CREATE TABLE SBREXT.AC_SOURCES_HST
(
  ACS_HST_IDSEQ   CHAR(36 BYTE)                 NOT NULL,
  ACS_IDSEQ       VARCHAR2(36 BYTE)                 NULL,
  AC_IDSEQ        CHAR(36 BYTE)                     NULL,
  SRC_NAME        VARCHAR2(100 BYTE)            NOT NULL,
  DATE_SUBMITTED  DATE                              NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_CREATED    DATE                          NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.PROTOCOLS_EXT
(
  PROTO_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  PROTOCOL_ID           VARCHAR2(50 BYTE)           NULL,
  TYPE                  VARCHAR2(240 BYTE)          NULL,
  PHASE                 VARCHAR2(3 BYTE)            NULL,
  LEAD_ORG              VARCHAR2(30 BYTE)           NULL,
  CHANGE_TYPE           VARCHAR2(10 BYTE)           NULL,
  CHANGE_NUMBER         NUMBER                      NULL,
  REVIEWED_DATE         DATE                        NULL,
  REVIEWED_BY           VARCHAR2(30 BYTE)           NULL,
  APPROVED_DATE         DATE                        NULL,
  APPROVED_BY           VARCHAR2(30 BYTE)           NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  PROTO_ID              NUMBER                  NOT NULL
);


CREATE TABLE SBREXT.CONDITION_COMPONENTS_EXT
(
  VV_IDSEQ              CHAR(36 BYTE)               NULL,
  CMP_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  C_QCON_IDSEQ          CHAR(36 BYTE)               NULL,
  QC_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  P_QCON_IDSEQ          CHAR(36 BYTE)           NOT NULL,
  CONSTANT_VALUE        VARCHAR2(255 BYTE)          NULL,
  LEFT_LOGICAL_OPERAND  VARCHAR2(10 BYTE)           NULL,
  RIGHT_OPERAND         VARCHAR2(10 BYTE)           NULL,
  DISPLAY_ORDER         NUMBER                      NULL,
  RF_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL
);


CREATE TABLE SBREXT.TA_PROTO_CSI_EXT
(
  TP_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  PROTO_IDSEQ    CHAR(36 BYTE)                      NULL,
  AC_CSI_IDSEQ   CHAR(36 BYTE)                      NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  TA_IDSEQ       CHAR(36 BYTE)                  NOT NULL
);


CREATE TABLE SBREXT.ECLASSES_STAGING
(
  ID             NUMBER(11)                     NOT NULL,
  SDE_ID         NUMBER(11)                     NOT NULL,
  NAME           VARCHAR2(250 BYTE)             NOT NULL,
  PACKAGE_NAME   VARCHAR2(250 BYTE)                 NULL,
  INTERFACE_IND  VARCHAR2(3 BYTE)                   NULL,
  ABSTRACT_IND   VARCHAR2(3 BYTE)                   NULL
);


CREATE TABLE SBREXT.TS_TYPE_LOV_EXT
(
  TSTL_NAME      VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.DEFINITIONS_STAGING
(
  ID                 NUMBER(11)                 NOT NULL,
  AC_ID              NUMBER(11)                 NOT NULL,
  DEFINITION         VARCHAR2(2000 BYTE)        NOT NULL,
  DEF_CONTE_NAME     VARCHAR2(30 BYTE)          NOT NULL,
  DEF_CONTE_VERSION  NUMBER(4,2)                NOT NULL,
  LAE_NAME           VARCHAR2(30 BYTE)              NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBREXT.GUEST_LOG
(
  TAB_OWNER        VARCHAR2(30 BYTE)                NULL,
  TAB_NAME         VARCHAR2(30 BYTE)                NULL,
  ROW_COUNT        NUMBER(10)                       NULL,
  MIN_CREATE_DATE  DATE                             NULL
);


CREATE TABLE SBREXT.ADMIN_COMPONENTS_STAGING_BKUP
(
  ID                       NUMBER(11)           NOT NULL,
  ACTL_NAME                VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  AC_CONTE_PREFERRED_NAME  VARCHAR2(30 BYTE)    NOT NULL,
  AC_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  AC_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL,
  CHANGE_NOTE              VARCHAR2(2000 BYTE)      NULL,
  AC_CONTE_VERSION         NUMBER(4,2)              NULL,
  AC_PREFERRED_NAME        VARCHAR2(30 BYTE)    NOT NULL,
  AC_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)  NOT NULL
);


CREATE TABLE SBREXT.AC_ATT_TYPES_LOV_EXT
(
  ATL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.STAGE_LOAD_PDF
(
  CONTE           VARCHAR2(2000 BYTE)               NULL,
  CONTE_IDSEQ     CHAR(36 BYTE)                     NULL,
  PROTOCOL        VARCHAR2(2000 BYTE)               NULL,
  ORG             VARCHAR2(2000 BYTE)               NULL,
  PROTO_IDSEQ     CHAR(36 BYTE)                     NULL,
  PROTO_CRF       VARCHAR2(30 BYTE)                 NULL,
  NAME            VARCHAR2(2000 BYTE)               NULL,
  TYPE            VARCHAR2(30 BYTE)                 NULL,
  VERSION         NUMBER(5,2)                       NULL,
  LOAD_ORDER      NUMBER(10,2)                      NULL,
  DISEASE         VARCHAR2(50 BYTE)                 NULL,
  TTU             VARCHAR2(50 BYTE)                 NULL,
  CRF_CATEGORY    VARCHAR2(50 BYTE)                 NULL,
  TRANSACTION_ID  NUMBER(11)                        NULL
);


CREATE TABLE SBREXT.TOOL_PROPERTIES_EXT
(
  NAME    VARCHAR2(100 BYTE)                    NOT NULL,
  VALUE   VARCHAR2(2000 BYTE)                   NOT NULL,
  LOCALE  VARCHAR2(10 BYTE)                     NOT NULL,
  TOOL    VARCHAR2(20 BYTE)                         NULL
);


CREATE TABLE SBREXT.QUEST_VV_EXT
(
  QV_IDSEQ         CHAR(36 BYTE)                NOT NULL,
  QUEST_IDSEQ      CHAR(36 BYTE)                NOT NULL,
  VV_IDSEQ         CHAR(36 BYTE)                    NULL,
  VALUE            VARCHAR2(255 BYTE)               NULL,
  EDITABLE_IND     VARCHAR2(3 BYTE)                 NULL,
  REPEAT_SEQUENCE  NUMBER                           NULL,
  DATE_CREATED     DATE                             NULL,
  DATE_MODIFIED    DATE                             NULL,
  MODIFIED_BY      VARCHAR2(30 BYTE)                NULL,
  CREATED_BY       VARCHAR2(30 BYTE)                NULL
);


CREATE TABLE SBREXT.CLASS_SCHEME_ITEMS_STAGING
(
  ID                 NUMBER(11)                 NOT NULL,
  CS_ID              NUMBER(11)                     NULL,
  CSI_NAME           VARCHAR2(20 BYTE)          NOT NULL,
  CSITL_NAME         VARCHAR2(20 BYTE)          NOT NULL,
  DESCRIPTION        VARCHAR2(60 BYTE)              NULL,
  COMMENTS           VARCHAR2(2000 BYTE)            NULL,
  RECORD_STATUS      VARCHAR2(50 BYTE)              NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  LABEL              VARCHAR2(30 BYTE)              NULL,
  DISPLAY_ORDER      NUMBER(2)                      NULL,
  CS_VERSION         NUMBER(4,2)                    NULL,
  CS_PREFERRED_NAME  VARCHAR2(30 BYTE)              NULL,
  CS_CONTE_NAME      VARCHAR2(30 BYTE)              NULL,
  CS_CONTE_VERSION   NUMBER(4,2)                    NULL,
  SDE_ID             NUMBER(11)                     NULL
);


CREATE TABLE SBREXT.ADMIN_COMPONENTS_STAGING
(
  ID                       NUMBER(11)           NOT NULL,
  ACTL_NAME                VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  AC_CONTE_PREFERRED_NAME  VARCHAR2(30 BYTE)    NOT NULL,
  AC_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  AC_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL,
  CHANGE_NOTE              VARCHAR2(2000 BYTE)      NULL,
  AC_CONTE_VERSION         NUMBER(4,2)              NULL,
  AC_PREFERRED_NAME        VARCHAR2(30 BYTE)        NULL,
  AC_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)      NULL
);


CREATE TABLE SBREXT.REVIEWER_FEEDBACK_LOV_EXT
(
  REVIEWER_FEEDBACK       VARCHAR2(240 BYTE)    NOT NULL,
  REVIEWER_FEEDBACK_TYPE  VARCHAR2(30 BYTE)     NOT NULL,
  DESCRIPTION             VARCHAR2(240 BYTE)        NULL,
  DATE_CREATED            DATE                  NOT NULL,
  CREATED_BY              VARCHAR2(30 BYTE)     NOT NULL,
  DATE_MODIFIED           DATE                      NULL,
  MODIFIED_BY             VARCHAR2(30 BYTE)         NULL
);


CREATE TABLE SBREXT.SUBSTITUTIONS_EXT
(
  SUB_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  TYPE           VARCHAR2(30 BYTE)              NOT NULL,
  CONTENT        VARCHAR2(1000 BYTE)            NOT NULL,
  SUBSTITUTION   VARCHAR2(1000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.TEXT_STRINGS_EXT
(
  TS_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  QC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  TSTL_NAME      VARCHAR2(30 BYTE)              NOT NULL,
  TS_TEXT        VARCHAR2(2000 BYTE)            NOT NULL,
  TS_SEQ         NUMBER(3)                      NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.CONTACT_ROLES_EXT
(
  CONTACT_ROLE   VARCHAR2(30 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL
);


CREATE TABLE SBREXT.PLAN_TABLE
(
  STATEMENT_ID       VARCHAR2(30 BYTE)              NULL,
  TIMESTAMP          DATE                           NULL,
  REMARKS            VARCHAR2(80 BYTE)              NULL,
  OPERATION          VARCHAR2(30 BYTE)              NULL,
  OPTIONS            VARCHAR2(255 BYTE)             NULL,
  OBJECT_NODE        VARCHAR2(128 BYTE)             NULL,
  OBJECT_OWNER       VARCHAR2(30 BYTE)              NULL,
  OBJECT_NAME        VARCHAR2(30 BYTE)              NULL,
  OBJECT_INSTANCE    INTEGER                        NULL,
  OBJECT_TYPE        VARCHAR2(30 BYTE)              NULL,
  OPTIMIZER          VARCHAR2(255 BYTE)             NULL,
  SEARCH_COLUMNS     NUMBER                         NULL,
  ID                 INTEGER                        NULL,
  PARENT_ID          INTEGER                        NULL,
  POSITION           INTEGER                        NULL,
  COST               INTEGER                        NULL,
  CARDINALITY        INTEGER                        NULL,
  BYTES              INTEGER                        NULL,
  OTHER_TAG          VARCHAR2(255 BYTE)             NULL,
  PARTITION_START    VARCHAR2(255 BYTE)             NULL,
  PARTITION_STOP     VARCHAR2(255 BYTE)             NULL,
  PARTITION_ID       INTEGER                        NULL,
  OTHER              LONG                           NULL,
  DISTRIBUTION       VARCHAR2(30 BYTE)              NULL,
  CPU_COST           INTEGER                        NULL,
  IO_COST            INTEGER                        NULL,
  TEMP_SPACE         INTEGER                        NULL,
  ACCESS_PREDICATES  VARCHAR2(4000 BYTE)            NULL,
  FILTER_PREDICATES  VARCHAR2(4000 BYTE)            NULL
);


CREATE TABLE SBREXT.REF_DOCS_STAGING
(
  ID             NUMBER(11)                     NOT NULL,
  NAME           VARCHAR2(30 BYTE)                  NULL,
  ORG_NAME       VARCHAR2(80 BYTE)                  NULL,
  DCTL_NAME      VARCHAR2(60 BYTE)              NOT NULL,
  AC_ID          NUMBER(11)                         NULL,
  DOC_TEXT       VARCHAR2(4000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  URL            VARCHAR2(240 BYTE)                 NULL,
  LAE_NAME       VARCHAR2(30 BYTE)                  NULL,
  DISPLAY_ORDER  VARCHAR2(2 BYTE)                   NULL
);


CREATE TABLE SBREXT.EATTRIBUTES_STAGING
(
  ID             NUMBER(11)                     NOT NULL,
  ECL_ID         NUMBER(11)                     NOT NULL,
  NAME           VARCHAR2(250 BYTE)             NOT NULL,
  DATA_TYPE      VARCHAR2(100 BYTE)             NOT NULL,
  DEFAULT_VALUE  VARCHAR2(100 BYTE)                 NULL
);


CREATE TABLE SBREXT.QC_DISPLAY_LOV_EXT
(
  QCDL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  DISPLAY_ORDER  NUMBER(3)                      NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);

COMMENT ON TABLE SBREXT.QC_DISPLAY_LOV_EXT IS 'Created to hold QC display types.  For CRFs, will hold types, such as "Registration", "Follow-up" forms etc.';


CREATE TABLE SBREXT.QC_RECS_HST
(
  QRH_IDSEQ         CHAR(36 BYTE)               NOT NULL,
  QR_IDSEQ          CHAR(36 BYTE)               NOT NULL,
  P_QC_IDSEQ        CHAR(36 BYTE)               NOT NULL,
  C_QC_IDSEQ        CHAR(36 BYTE)               NOT NULL,
  DISPLAY_ORDER     NUMBER(3)                   NOT NULL,
  RL_NAME           VARCHAR2(20 BYTE)           NOT NULL,
  DATE_CREATED      DATE                        NOT NULL,
  CREATED_BY        VARCHAR2(30 BYTE)           NOT NULL,
  DATE_MODIFIED     DATE                            NULL,
  MODIFIED_BY       VARCHAR2(30 BYTE)               NULL,
  QRH_DATE_CREATED  DATE                            NULL,
  QRH_CREATED_BY    VARCHAR2(30 BYTE)               NULL
);


CREATE TABLE SBREXT.CONCEPT_SOURCES_LOV_EXT
(
  CONCEPT_SOURCE  VARCHAR2(255 BYTE)            NOT NULL,
  DESCRIPTION     VARCHAR2(2000 BYTE)               NULL,
  DATE_CREATED    DATE                              NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL,
  CREATED_BY      VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.ADMINISTERED_COMPONENTS_HST
(
  AC_HST_IDSEQ          CHAR(36 BYTE)           NOT NULL,
  AC_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  ACTL_NAME             VARCHAR2(20 BYTE)       NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  STEWA_IDSEQ           CHAR(36 BYTE)               NULL,
  CMSL_NAME             VARCHAR2(20 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  UNRESOLVED_ISSUE      VARCHAR2(200 BYTE)          NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL
);


CREATE TABLE SBREXT.RULE_FUNCTIONS_EXT
(
  RF_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  NAME           VARCHAR2(50 BYTE)                  NULL,
  SYMBOL         VARCHAR2(20 BYTE)                  NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  CONDR_IDSEQ    CHAR(36 BYTE)                      NULL
);


CREATE TABLE SBREXT.CONDITION_MESSAGE_EXT
(
  CM_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  QCON_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  MESSAGE_TEXT   VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  MT_NAME        VARCHAR2(50 BYTE)                  NULL
);


CREATE TABLE SBREXT.CRF_TOOL_PARAMETER_EXT
(
  PROTO_IDSEQ     CHAR(36 BYTE)                 NOT NULL,
  QC_IDSEQ        CHAR(36 BYTE)                 NOT NULL,
  CRF_STATUS_IND  VARCHAR2(20 BYTE)             NOT NULL,
  UA_NAME         VARCHAR2(30 BYTE)             NOT NULL,
  DATE_CREATED    DATE                          NOT NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.TOOL_OPTIONS_EXT
(
  TOOL_IDSEQ     VARCHAR2(36 BYTE)              NOT NULL,
  TOOL_NAME      VARCHAR2(30 BYTE)              NOT NULL,
  PROPERTY       VARCHAR2(255 BYTE)             NOT NULL,
  VALUE          VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                               NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  UA_NAME        VARCHAR2(30 BYTE)                  NULL,
  DESCRIPTION    VARCHAR2(2000 BYTE)                NULL,
  LOCALE         VARCHAR2(10 BYTE)                  NULL
);


CREATE TABLE SBREXT.EREFERENCES_STAGING
(
  ID                    NUMBER(11)              NOT NULL,
  ECL_ID                NUMBER(11)              NOT NULL,
  NAME                  VARCHAR2(250 BYTE)      NOT NULL,
  REFERENCE_CLASS_NAME  VARCHAR2(250 BYTE)      NOT NULL,
  LOWER_BOUND           NUMBER(1)                   NULL,
  UPPER_BOUND           NUMBER(1)                   NULL,
  BIDIRECTIONAL_IND     VARCHAR2(3 BYTE)            NULL
);


CREATE TABLE SBREXT.ERROR_LOG
(
  MSG   VARCHAR2(200 BYTE)                          NULL,
  TEXT  VARCHAR2(500 BYTE)                          NULL
);


CREATE TABLE SBREXT.DESIGNATIONS_STAGING
(
  ID                 NUMBER(11)                 NOT NULL,
  AC_ID              NUMBER(11)                 NOT NULL,
  DES_CONTE_NAME     VARCHAR2(30 BYTE)          NOT NULL,
  DES_CONTE_VERSION  NUMBER(4,2)                NOT NULL,
  NAME               VARCHAR2(30 BYTE)          NOT NULL,
  DETL_NAME          VARCHAR2(20 BYTE)          NOT NULL,
  LAE_NAME           VARCHAR2(30 BYTE)              NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL
);


CREATE TABLE SBREXT.ASL_ACTL_EXT
(
  ASL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  ACTL_NAME      VARCHAR2(20 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.QUEST_CONTENTS_HST
(
  QC_HST_IDSEQ                CHAR(36 BYTE)     NOT NULL,
  QC_IDSEQ                    CHAR(36 BYTE)         NULL,
  VERSION                     NUMBER(4,2)       NOT NULL,
  QTL_NAME                    VARCHAR2(20 BYTE) NOT NULL,
  CONTE_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  ASL_NAME                    VARCHAR2(20 BYTE) NOT NULL,
  PREFERRED_NAME              VARCHAR2(30 BYTE) NOT NULL,
  PREFERRED_DEFINITION        VARCHAR2(2000 BYTE) NOT NULL,
  PROTO_IDSEQ                 CHAR(36 BYTE)         NULL,
  DE_IDSEQ                    CHAR(36 BYTE)         NULL,
  QC_MATCH_IDSEQ              CHAR(36 BYTE)         NULL,
  VP_IDSEQ                    CHAR(36 BYTE)         NULL,
  QC_IDENTIFIER               VARCHAR2(30 BYTE)     NULL,
  QCDL_NAME                   VARCHAR2(20 BYTE)     NULL,
  LONG_NAME                   VARCHAR2(255 BYTE)     NULL,
  LATEST_VERSION_IND          VARCHAR2(3 BYTE)      NULL,
  DELETED_IND                 VARCHAR2(3 BYTE)      NULL,
  BEGIN_DATE                  DATE                  NULL,
  END_DATE                    DATE                  NULL,
  MATCH_IND                   VARCHAR2(1 BYTE)      NULL,
  NEW_QC_IND                  VARCHAR2(3 BYTE)      NULL,
  REVIEWER_FEEDBACK_ACTION    VARCHAR2(240 BYTE)     NULL,
  REVIEWER_FEEDBACK_INTERNAL  VARCHAR2(240 BYTE)     NULL,
  REVIEWER_FEEDBACK_EXTERNAL  VARCHAR2(240 BYTE)     NULL,
  SYSTEM_MSGS                 VARCHAR2(2000 BYTE)     NULL,
  REVIEWED_BY                 VARCHAR2(30 BYTE)     NULL,
  REVIEWED_DATE               DATE                  NULL,
  APPROVED_BY                 VARCHAR2(30 BYTE)     NULL,
  APPROVED_DATE               DATE                  NULL,
  SRC_NAME                    VARCHAR2(100 BYTE)     NULL,
  CDE_DICTIONARY_ID           NUMBER(10)            NULL,
  DATE_CREATED                DATE              NOT NULL,
  CREATED_BY                  VARCHAR2(30 BYTE) NOT NULL,
  DATE_MODIFIED               DATE                  NULL,
  MODIFIED_BY                 VARCHAR2(30 BYTE)     NULL,
  CHANGE_NOTE                 VARCHAR2(2000 BYTE)     NULL,
  HIGHLIGHT_IND               VARCHAR2(3 BYTE)      NULL,
  SUBMITTED_LONG_CDE_NAME     VARCHAR2(4000 BYTE)     NULL,
  GROUP_COMMENTS              VARCHAR2(4000 BYTE)     NULL,
  QCH_DATE_CREATED            DATE                  NULL,
  QCH_CREATED_BY              VARCHAR2(30 BYTE)     NULL
);


CREATE TABLE SBREXT.UP_ATTRIBUTE_METADATA_MVW
(
  AM_IDSEQ              CHAR(36 BYTE)               NULL,
  NAME                  VARCHAR2(2000 BYTE)         NULL,
  DESCRIPTION           VARCHAR2(2000 BYTE)     NOT NULL,
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  PG_IDSEQ              CHAR(36 BYTE)               NULL,
  CS_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DESIG_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  DEFIN_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  CS_CSI_IDSEQ          CHAR(36 BYTE)               NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  FULLY_QUALIFIED_NAME  VARCHAR2(2000 BYTE)     NOT NULL,
  CM_IDSEQ              CHAR(36 BYTE)               NULL,
  PROP_IDSEQ            CHAR(36 BYTE)               NULL,
  CP_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  AT_IDSEQ              CHAR(36 BYTE)               NULL,
  GMEXMLLOCREFERENCE    VARCHAR2(4000 BYTE)         NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  PUBLIC_ID             NUMBER                  NOT NULL,
  CLASS_GMEXMLELEMENT   VARCHAR2(4000 BYTE)         NULL,
  CLASS_GMENAMESPACE    VARCHAR2(4000 BYTE)         NULL
);


CREATE TABLE SBREXT.UP_TYPE_ENUMERATION_MVW
(
  TE_IDSEQ       CHAR(36 BYTE)                      NULL,
  AT_IDSEQ       CHAR(36 BYTE)                      NULL,
  VALUE          VARCHAR2(255 BYTE)             NOT NULL,
  SHORT_MEANING  VARCHAR2(255 BYTE)                 NULL,
  VP_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  PV_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  VM_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  VERSION        NUMBER(4,2)                    NOT NULL,
  PUBLIC_ID      NUMBER                         NOT NULL
);


CREATE TABLE SBREXT.UP_ASSOCIATIONS_METADATA_MVW
(
  ASM_IDSEQ                 CHAR(36 BYTE)           NULL,
  OCR_IDSEQ                 CHAR(36 BYTE)       NOT NULL,
  T_CM_IDSEQ                CHAR(36 BYTE)           NULL,
  S_CM_IDSEQ                CHAR(36 BYTE)           NULL,
  CS_IDSEQ                  CHAR(36 BYTE)       NOT NULL,
  CM_IDSEQ                  CHAR(36 BYTE)           NULL,
  CP_IDSEQ                  CHAR(36 BYTE)       NOT NULL,
  SOURCE_ROLE               VARCHAR2(255 BYTE)      NULL,
  TARGET_ROLE               VARCHAR2(255 BYTE)      NULL,
  SOURCE_LOW_MULTIPLICITY   NUMBER                  NULL,
  SOURCE_HIGH_MULTIPLICITY  NUMBER                  NULL,
  TARGET_LOW_MULTIPLICITY   NUMBER                  NULL,
  TARGET_HIGH_MULTIPLICITY  NUMBER                  NULL,
  ISBIDIRECTIONAL           NUMBER                  NULL,
  GMESOURCEXMLLOCREFERENCE  VARCHAR2(4000 BYTE)     NULL,
  GMETARGETXMLLOCREFERENCE  VARCHAR2(4000 BYTE)     NULL,
  VERSION                   NUMBER(4,2)             NULL,
  PUBLIC_ID                 NUMBER                  NULL,
  CS_CSI_IDSEQ              CHAR(36 BYTE)           NULL,
  SOURCE_GMEXMLELEMENT      VARCHAR2(4000 BYTE)     NULL,
  SOURCE_GMENAMESPACE       VARCHAR2(4000 BYTE)     NULL,
  TARGET_GMEXMLELEMENT      VARCHAR2(4000 BYTE)     NULL,
  TARGET_GMENAMESPACE       VARCHAR2(4000 BYTE)     NULL
);


CREATE TABLE SBREXT.UP_SEMANTIC_METADATA_MVW
(
  SM_IDSEQ            CHAR(36 BYTE)                 NULL,
  CON_IDSEQ           CHAR(36 BYTE)                 NULL,
  CONCEPT_CODE        VARCHAR2(30 BYTE)             NULL,
  CONCEPT_NAME        VARCHAR2(255 BYTE)            NULL,
  CONCEPT_DEFINITION  VARCHAR2(2000 BYTE)           NULL,
  PRIMARY_CONCEPT     NUMBER                        NULL,
  DISPLAY_ORDER       NUMBER                        NULL,
  COMPONENT_LEVEL     NUMBER                        NULL,
  CS_IDSEQ            CHAR(36 BYTE)                 NULL,
  CP_IDSEQ            CHAR(36 BYTE)                 NULL,
  CM_IDSEQ            VARCHAR2(36 BYTE)             NULL,
  AM_IDSEQ            VARCHAR2(36 BYTE)             NULL,
  AT_IDSEQ            VARCHAR2(36 BYTE)             NULL,
  TE_IDSEQ            VARCHAR2(36 BYTE)             NULL,
  ASM_IDSEQ           VARCHAR2(36 BYTE)             NULL,
  OCR_IDSEQ           CHAR(36 BYTE)                 NULL,
  OC_IDSEQ            CHAR(36 BYTE)                 NULL,
  PROP_IDSEQ          VARCHAR2(36 BYTE)             NULL,
  VD_IDSEQ            VARCHAR2(36 BYTE)             NULL,
  SHORT_MEANING       VARCHAR2(255 BYTE)            NULL,
  PUBLIC_ID           NUMBER                        NULL,
  VERSION             NUMBER                        NULL
);


CREATE TABLE SBREXT.SN_RECIPIENT_EXT
(
  REC_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  REP_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  EMAIL          VARCHAR2(255 BYTE)                 NULL,
  UA_NAME        VARCHAR2(30 BYTE)                  NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  CONTE_IDSEQ    CHAR(36 BYTE)                      NULL
);


CREATE TABLE SBREXT.PERMISSIBLE_VALUES_HST
(
  PV_HST_IDSEQ         CHAR(36 BYTE)            NOT NULL,
  PV_IDSEQ             CHAR(36 BYTE)            NOT NULL,
  VALUE                VARCHAR2(30 BYTE)        NOT NULL,
  SHORT_MEANING        VARCHAR2(255 BYTE)       NOT NULL,
  MEANING_DESCRIPTION  VARCHAR2(2000 BYTE)          NULL,
  BEGIN_DATE           DATE                     NOT NULL,
  END_DATE             DATE                         NULL,
  HIGH_VALUE_NUM       NUMBER(10)                   NULL,
  LOW_VALUE_NUM        NUMBER(10)                   NULL,
  DATE_CREATED         DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL
);


CREATE TABLE SBREXT.ERRORS_EXT
(
  ERROR_CODE    VARCHAR2(15 BYTE)               NOT NULL,
  ERROR_TEXT    VARCHAR2(255 BYTE)                  NULL,
  DATE_CREATED  DATE                            NOT NULL,
  CREATED_BY    VARCHAR2(240 BYTE)              NOT NULL
);


CREATE TABLE SBREXT.CONCEPTS_STAGING
(
  ID                        NUMBER              NOT NULL,
  SDE_ID                    NUMBER              NOT NULL,
  CON_VERSION               NUMBER                  NULL,
  CON_CONTE_NAME            VARCHAR2(30 BYTE)       NULL,
  CON_CONTE_VERSION         NUMBER                  NULL,
  CON_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NULL,
  CON_LONG_NAME             VARCHAR2(255 BYTE)  NOT NULL,
  CON_WORKFLOW_STATUS       VARCHAR2(20 BYTE)       NULL,
  BEGIN_DATE                DATE                    NULL,
  END_DATE                  DATE                    NULL,
  COMMENTS                  VARCHAR2(2000 BYTE)     NULL,
  ORIGIN                    VARCHAR2(240 BYTE)      NULL,
  UNRESOLVED_ISSURE         VARCHAR2(2000 BYTE)     NULL,
  EXAMPLE                   VARCHAR2(2000 BYTE)     NULL,
  RECORD_STATUS             VARCHAR2(50 BYTE)       NULL,
  DATE_CREATED              DATE                    NULL,
  CREATED_BY                VARCHAR2(30 BYTE)       NULL,
  CON_DEFINITION_SOURCE     VARCHAR2(255 BYTE)      NULL,
  CON_EVS_SOURCE            VARCHAR2(240 BYTE)      NULL,
  CON_PREFERRED_NAME        VARCHAR2(30 BYTE)   NOT NULL,
  CHANGE_NOTE               VARCHAR2(2000 BYTE)     NULL
);


CREATE TABLE SBREXT.DEFINITION_TYPES_LOV_EXT
(
  DEFL_NAME      VARCHAR2(50 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(255 BYTE)                 NULL,
  COMMENTS       VARCHAR2(2000 BYTE)                NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.DATA_ELEMENTS_STAGING
(
  ID                        NUMBER(11)          NOT NULL,
  SDE_ID                    NUMBER(11)          NOT NULL,
  DE_LONG_NAME              VARCHAR2(255 BYTE)      NULL,
  VD_PREFERRED_NAME         VARCHAR2(30 BYTE)   NOT NULL,
  VD_VERSION                NUMBER(4,2)         NOT NULL,
  DEC_PREFERRED_NAME        VARCHAR2(30 BYTE)   NOT NULL,
  DEC_VERSION               NUMBER(4,2)         NOT NULL,
  VD_CONTE_PREFERRED_NAME   VARCHAR2(30 BYTE)   NOT NULL,
  DEC_CONTE_PREFERRED_NAME  VARCHAR2(30 BYTE)   NOT NULL,
  ORIGIN                    VARCHAR2(240 BYTE)      NULL,
  BEGIN_DATE                VARCHAR2(30 BYTE)       NULL,
  END_DATE                  VARCHAR2(30 BYTE)       NULL,
  UNRESOLVED_ISSUE          VARCHAR2(200 BYTE)      NULL,
  WORKFLOW_STATUS           VARCHAR2(20 BYTE)       NULL,
  ADMIN_NOTE                VARCHAR2(2000 BYTE)     NULL,
  COMMENTS                  VARCHAR2(2000 BYTE)     NULL,
  EXAMPLE                   VARCHAR2(2000 BYTE)     NULL,
  DATE_CREATED              DATE                NOT NULL,
  CREATED_BY                VARCHAR2(30 BYTE)   NOT NULL,
  DATE_MODIFIED             DATE                    NULL,
  MODIFIED_BY               VARCHAR2(30 BYTE)       NULL,
  CHANGE_NOTE               VARCHAR2(2000 BYTE)     NULL,
  VD_CONTE_VERSION          NUMBER(4,2)         NOT NULL,
  DEC_CONTE_VERSION         NUMBER(4,2)         NOT NULL,
  DE_CONTE_VERSION          NUMBER(4,2)         NOT NULL,
  DE_CONTE_PREFERRED_NAME   VARCHAR2(30 BYTE)   NOT NULL,
  RECORD_STATUS             VARCHAR2(100 BYTE)      NULL,
  DE_VERSION                NUMBER(4,2)         NOT NULL,
  DE_PREFERRED_DEFINITION   VARCHAR2(2000 BYTE) NOT NULL,
  DE_PREFERRED_NAME         VARCHAR2(30 BYTE)   NOT NULL
);


CREATE TABLE SBREXT.CREATE$JAVA$LOB$TABLE
(
  NAME      VARCHAR2(700 BYTE)                      NULL,
  LOB       BLOB                                    NULL,
  LOADTIME  DATE                                    NULL
);


CREATE TABLE SBREXT.GS_TABLES_LOV
(
  AC_TABLE       NUMBER(2)                      NOT NULL,
  NAME           VARCHAR2(30 BYTE)              NOT NULL,
  ABBREV         VARCHAR2(6 BYTE)               NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  ACTL_NAME      VARCHAR2(20 BYTE)                  NULL
);


CREATE TABLE SBREXT.AC_SUBMITTERS_STAGING
(
  ID                       NUMBER(11)           NOT NULL,
  AC_ID                    NUMBER(11)           NOT NULL,
  NAME                     VARCHAR2(30 BYTE)    NOT NULL,
  ORG_NAME                 VARCHAR2(80 BYTE)    NOT NULL,
  TITLE                    VARCHAR2(240 BYTE)       NULL,
  PHONE_NUMBER             VARCHAR2(30 BYTE)        NULL,
  FAX_NUMBER               VARCHAR2(30 BYTE)        NULL,
  TELEX_NUMBER             VARCHAR2(30 BYTE)        NULL,
  MAIL_ADDRESS             VARCHAR2(240 BYTE)       NULL,
  ELECTRONIC_MAIL_ADDRESS  VARCHAR2(100 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  SUBMIT_DATE              VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBREXT.PV_STAGING_BKUP
(
  ID                   NUMBER(11)               NOT NULL,
  VD_ID                NUMBER(11)                   NULL,
  VALUE                VARCHAR2(255 BYTE)       NOT NULL,
  SHORT_MEANING        VARCHAR2(255 BYTE)       NOT NULL,
  MEANING_DESCRIPTION  VARCHAR2(2000 BYTE)          NULL,
  BEGIN_DATE           VARCHAR2(30 BYTE)        NOT NULL,
  END_DATE             VARCHAR2(30 BYTE)            NULL,
  HIGH_VALUE_NUM       NUMBER(10)                   NULL,
  LOW_VALUE_NUM        NUMBER(10)                   NULL,
  DATE_CREATED         DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL,
  ORIGIN               VARCHAR2(240 BYTE)           NULL,
  DISPLAY_ORDER        NUMBER(5)                    NULL,
  VD_PREFERRED_NAME    VARCHAR2(30 BYTE)            NULL,
  VD_VERSION           NUMBER(4,2)                  NULL,
  VD_CONTEXT_NAME      VARCHAR2(30 BYTE)            NULL,
  VD_CONTEXT_VERSION   NUMBER(4,2)                  NULL
);


CREATE TABLE SBREXT.XML_LOADER_ERRORS
(
  ID          NUMBER(11)                        NOT NULL,
  AC_ID       NUMBER(11)                        NOT NULL,
  ERROR_TEXT  VARCHAR2(2000 BYTE)               NOT NULL
);


CREATE TABLE SBREXT.VM_BACKUP
(
  VD_IDSEQ              CHAR(36 BYTE)               NULL,
  VP_IDSEQ              CHAR(36 BYTE)               NULL,
  PV_IDSEQ              CHAR(36 BYTE)               NULL,
  VALUE                 VARCHAR2(255 BYTE)          NULL,
  SHORT_MEANING         VARCHAR2(255 BYTE)          NULL,
  MEANING_DESCRIPTION   VARCHAR2(2000 BYTE)         NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL,
  COMMENTS              VARCHAR2(2000 BYTE)         NULL,
  UPDATE_DATE           DATE                        NULL,
  METHOD                VARCHAR2(30 BYTE)           NULL,
  PAR_DESCRIPTION       VARCHAR2(2000 BYTE)         NULL,
  PAR_CONDR_IDSEQ       VARCHAR2(36 BYTE)           NULL,
  NEW_SHORT_MEANING     VARCHAR2(255 BYTE)          NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  ALTERNATE_DEFINITION  VARCHAR2(2000 BYTE)         NULL,
  ALTERNATE_NAME        VARCHAR2(255 BYTE)          NULL,
  CV_IDSEQ              CHAR(36 BYTE)               NULL,
  HIGH_VALUE_NUM        NUMBER(10)                  NULL,
  LOW_VALUE_NUM         NUMBER(10)                  NULL,
  PUBLIC_ID             NUMBER                      NULL,
  VERSION               NUMBER                      NULL
);


CREATE TABLE SBREXT.VD_STAGING_BKUP
(
  ID                       NUMBER(11)           NOT NULL,
  VD_VERSION               NUMBER(4,2)          NOT NULL,
  VD_PREFERRED_NAME        VARCHAR2(30 BYTE)    NOT NULL,
  VD_CONTE_VERSION         NUMBER(4,2)          NOT NULL,
  VD_CONTE_NAME            VARCHAR2(30 BYTE)    NOT NULL,
  VD_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)  NOT NULL,
  DTL_NAME                 VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  VD_CD_CONTE_VERSION      NUMBER(4,2)          NOT NULL,
  VD_CD_CONTE_NAME         VARCHAR2(30 BYTE)    NOT NULL,
  VD_CD_PREFERRED_NAME     VARCHAR2(30 BYTE)    NOT NULL,
  VD_CD_VERSION            NUMBER(4,2)          NOT NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  VD_TYPE_FLAG             VARCHAR2(50 BYTE)    NOT NULL,
  VD_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  UOML_NAME                VARCHAR2(20 BYTE)        NULL,
  FORML_NAME               VARCHAR2(20 BYTE)        NULL,
  MAX_LENGTH_NUM           NUMBER(8)                NULL,
  MIN_LENGTH_NUM           NUMBER(8)                NULL,
  DECIMAL_PLACE            NUMBER(2)                NULL,
  CHAR_SET_NAME            VARCHAR2(20 BYTE)        NULL,
  HIGH_VALUE_NUM           VARCHAR2(255 BYTE)       NULL,
  LOW_VALUE_NUM            VARCHAR2(255 BYTE)       NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL,
  DATE_MODIFIED            DATE                     NULL,
  MODIFIED_BY              VARCHAR2(30 BYTE)        NULL,
  VD_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  SDE_ID                   NUMBER(11)           NOT NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  ADMIN_NOTE               VARCHAR2(2000 BYTE)      NULL,
  COMMENTS                 VARCHAR2(2000 BYTE)      NULL,
  EXAMPLE                  VARCHAR2(2000 BYTE)      NULL,
  RECORD_STATUS            VARCHAR2(100 BYTE)       NULL,
  DATA_TYPE_DESCRIPTION    VARCHAR2(60 BYTE)        NULL,
  DATA_TYPE_COMMENTS       VARCHAR2(2000 BYTE)      NULL,
  FORMAT_COMMENTS          VARCHAR2(2000 BYTE)      NULL,
  FORMAT_DESCRIPTION       VARCHAR2(60 BYTE)        NULL,
  CHAR_SET_DESCRIPTION     VARCHAR2(60 BYTE)        NULL,
  UOML_PRECISION           VARCHAR2(30 BYTE)        NULL,
  UOML_DESCRIPTION         VARCHAR2(60 BYTE)        NULL,
  UOML_COMMENTS            VARCHAR2(2000 BYTE)      NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  REP_PREFERRED_NAME       VARCHAR2(30 BYTE)        NULL,
  REP_VERSION              NUMBER                   NULL,
  REP_CONTE_NAME           VARCHAR2(30 BYTE)        NULL,
  REP_CONTE_VERSION        NUMBER                   NULL,
  REP_QUALIFIER            VARCHAR2(30 BYTE)        NULL
);


CREATE TABLE SBREXT.VD_PVS_SOURCES_HST
(
  VPS_HST_IDSEQ   CHAR(36 BYTE)                 NOT NULL,
  VPS_IDSEQ       CHAR(36 BYTE)                 NOT NULL,
  VP_IDSEQ        CHAR(36 BYTE)                 NOT NULL,
  SRC_NAME        VARCHAR2(100 BYTE)            NOT NULL,
  DATE_SUBMITTED  DATE                              NULL,
  COMMENTS        VARCHAR2(2000 BYTE)               NULL,
  DATE_CREATED    DATE                          NOT NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.VD_PVS_SOURCES_EXT
(
  VPS_IDSEQ       CHAR(36 BYTE)                 NOT NULL,
  VP_IDSEQ        CHAR(36 BYTE)                 NOT NULL,
  SRC_NAME        VARCHAR2(100 BYTE)            NOT NULL,
  DATE_SUBMITTED  DATE                              NULL,
  COMMENTS        VARCHAR2(2000 BYTE)               NULL,
  DATE_CREATED    DATE                          NOT NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.SN_QUERY_EXT
(
  QUR_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  RECORD_TYPE    CHAR(1 BYTE)                       NULL,
  DATA_TYPE      VARCHAR2(30 BYTE)                  NULL,
  PROPERTY       VARCHAR2(30 BYTE)                  NULL,
  VALUE          VARCHAR2(255 BYTE)                 NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  AL_IDSEQ       CHAR(36 BYTE)                      NULL
);


CREATE TABLE SBREXT.JAVA$CLASS$MD5$TABLE
(
  NAME  VARCHAR2(200 BYTE)                          NULL,
  MD5   RAW(16)                                     NULL
);


CREATE TABLE SBREXT.GS_COMPOSITE
(
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  AC_TABLE       NUMBER(2)                      NOT NULL,
  COMPOSITE      VARCHAR2(2000 BYTE)            NOT NULL,
  WFS_ORDER      NUMBER                         DEFAULT 0                     NOT NULL,
  REG_ORDER      NUMBER                         DEFAULT 0                     NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.CON_DERIVATION_RULES_EXT
(
  CONDR_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  METHODS        VARCHAR2(4000 BYTE)                NULL,
  RULE           VARCHAR2(4000 BYTE)                NULL,
  CONCAT_CHAR    CHAR(1 BYTE)                       NULL,
  CRTL_NAME      VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  NAME           VARCHAR2(255 BYTE)             NOT NULL
);


CREATE TABLE SBREXT.QUAL_MAP
(
  QUAL_NAME  VARCHAR2(255 BYTE)                     NULL,
  CON_NAME   VARCHAR2(255 BYTE)                     NULL
);


CREATE TABLE SBREXT.MESSAGE_TYPES_EXT
(
  MT_NAME        VARCHAR2(50 BYTE)              NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.GS_TOKENS
(
  AC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  AC_TABLE       NUMBER(2)                      NOT NULL,
  AC_COL         VARCHAR2(30 BYTE)              NOT NULL,
  TOKEN          VARCHAR2(256 BYTE)             NOT NULL,
  WEIGHT         NUMBER(6)                      DEFAULT 1                     NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.CLASS_SCHEMES_STAGING
(
  ID                       NUMBER(11)           NOT NULL,
  SDE_ID                   NUMBER(11)           NOT NULL,
  CS_VERSION               NUMBER(4,2)          NOT NULL,
  CS_PREFERRED_NAME        VARCHAR2(30 BYTE)    NOT NULL,
  CS_CONTE_NAME            VARCHAR2(30 BYTE)    NOT NULL,
  CS_CONTE_VERSION         NUMBER(4,2)              NULL,
  CS_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)  NOT NULL,
  CS_LONG_NAME             VARCHAR2(255 BYTE)       NULL,
  CS_WORKFLOW_STATUS       VARCHAR2(20 BYTE)    NOT NULL,
  BEGIN_DATE               VARCHAR2(30 BYTE)        NULL,
  END_DATE                 VARCHAR2(30 BYTE)        NULL,
  ADMIN_NOTE               VARCHAR2(2000 BYTE)      NULL,
  COMMENTS                 VARCHAR2(2000 BYTE)      NULL,
  ORIGIN                   VARCHAR2(240 BYTE)       NULL,
  UNRESOLVED_ISSUE         VARCHAR2(200 BYTE)       NULL,
  EXAMPLE                  VARCHAR2(2000 BYTE)      NULL,
  RECORD_STATUS            VARCHAR2(50 BYTE)        NULL,
  CSTL_NAME                VARCHAR2(20 BYTE)    NOT NULL,
  LABEL_TYPE_FLAG          VARCHAR2(100 BYTE)   NOT NULL,
  CMSL_NAME                VARCHAR2(20 BYTE)        NULL,
  DATE_CREATED             DATE                 NOT NULL,
  CREATED_BY               VARCHAR2(30 BYTE)    NOT NULL
);


CREATE TABLE SBREXT.QC_TYPE_LOV_EXT
(
  QTL_NAME       VARCHAR2(20 BYTE)              NOT NULL,
  DESCRIPTION    VARCHAR2(240 BYTE)                 NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.REPRESENTATION_LOV_EXT
(
  REPRESENTATION_NAME  VARCHAR2(30 BYTE)        NOT NULL,
  DESCRIPTION          VARCHAR2(60 BYTE)            NULL,
  COMMENTS             VARCHAR2(200 BYTE)           NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_CREATED         DATE                     NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL
);


CREATE TABLE SBREXT.AC_ATT_CSCSI_EXT
(
  ACA_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  CS_CSI_IDSEQ   CHAR(36 BYTE)                  NOT NULL,
  ATT_IDSEQ      CHAR(36 BYTE)                  NOT NULL,
  ATL_NAME       VARCHAR2(30 BYTE)              NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.SN_REPORT_EXT
(
  REP_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  AL_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  INCLUDE_PROPERTY_IND  VARCHAR2(3 BYTE)            NULL,
  STYLE                 VARCHAR2(1 BYTE)            NULL,
  SEND                  VARCHAR2(1 BYTE)            NULL,
  ACKNOWLEDGE_IND       VARCHAR2(3 BYTE)            NULL,
  COMMENTS              VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  ASSOC_LVL_NUM         NUMBER                      NULL,
  ASSOC_AC_TYPE         VARCHAR2(5 BYTE)            NULL
);


CREATE TABLE SBREXT.COMPONENT_LEVELS_EXT
(
  CL_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  COMPONENT_LEVEL       NUMBER                      NULL,
  CONCATENATION_STRING  VARCHAR2(30 BYTE)           NULL,
  DATE_CREATED          DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL
);


CREATE TABLE SBREXT.OC_RECS_EXT
(
  OCR_IDSEQ                 CHAR(36 BYTE)       NOT NULL,
  PREFERRED_NAME            VARCHAR2(30 BYTE)   NOT NULL,
  LONG_NAME                 VARCHAR2(255 BYTE)      NULL,
  PREFERRED_DEFINITION      VARCHAR2(2000 BYTE) NOT NULL,
  CONTE_IDSEQ               CHAR(36 BYTE)       NOT NULL,
  ASL_NAME                  VARCHAR2(20 BYTE)   NOT NULL,
  CHANGE_NOTE               VARCHAR2(2000 BYTE)     NULL,
  BEGIN_DATE                DATE                    NULL,
  END_DATE                  DATE                    NULL,
  ORIGIN                    VARCHAR2(240 BYTE)      NULL,
  VERSION                   NUMBER(4,2)         NOT NULL,
  T_OC_IDSEQ                CHAR(36 BYTE)       NOT NULL,
  S_OC_IDSEQ                CHAR(36 BYTE)       NOT NULL,
  RL_NAME                   VARCHAR2(20 BYTE)   NOT NULL,
  SOURCE_ROLE               VARCHAR2(255 BYTE)      NULL,
  TARGET_ROLE               VARCHAR2(255 BYTE)      NULL,
  DIRECTION                 VARCHAR2(20 BYTE)       NULL,
  SOURCE_LOW_MULTIPLICITY   NUMBER                  NULL,
  SOURCE_HIGH_MULTIPLICITY  NUMBER                  NULL,
  TARGET_LOW_MULTIPLICITY   NUMBER                  NULL,
  TARGET_HIGH_MULTIPLICITY  NUMBER                  NULL,
  LATEST_VERSION_IND        VARCHAR2(3 BYTE)        NULL,
  DELETED_IND               VARCHAR2(3 BYTE)        NULL,
  DATE_CREATED              DATE                    NULL,
  DATE_MODIFIED             DATE                    NULL,
  MODIFIED_BY               VARCHAR2(30 BYTE)       NULL,
  CREATED_BY                VARCHAR2(30 BYTE)       NULL,
  OCR_ID                    NUMBER              NOT NULL,
  DISPLAY_ORDER             NUMBER                  NULL,
  DIMENSIONALITY            NUMBER                  NULL,
  ARRAY_IND                 VARCHAR2(3 BYTE)        NULL,
  S_AC_CSI_IDSEQ            CHAR(36 BYTE)           NULL,
  T_AC_CSI_IDSEQ            CHAR(36 BYTE)           NULL,
  T_CONDR_IDSEQ             CHAR(36 BYTE)           NULL,
  S_CONDR_IDSEQ             CHAR(36 BYTE)           NULL,
  CONDR_IDSEQ               CHAR(36 BYTE)           NULL
);


CREATE TABLE SBREXT.PERMISSIBLE_VALUES_STAGING
(
  ID                   NUMBER(11)               NOT NULL,
  VD_ID                NUMBER(11)                   NULL,
  VALUE                VARCHAR2(255 BYTE)       NOT NULL,
  SHORT_MEANING        VARCHAR2(255 BYTE)       NOT NULL,
  MEANING_DESCRIPTION  VARCHAR2(2000 BYTE)          NULL,
  BEGIN_DATE           VARCHAR2(30 BYTE)        NOT NULL,
  END_DATE             VARCHAR2(30 BYTE)            NULL,
  HIGH_VALUE_NUM       NUMBER(10)                   NULL,
  LOW_VALUE_NUM        NUMBER(10)                   NULL,
  DATE_CREATED         DATE                     NOT NULL,
  CREATED_BY           VARCHAR2(30 BYTE)        NOT NULL,
  DATE_MODIFIED        DATE                         NULL,
  MODIFIED_BY          VARCHAR2(30 BYTE)            NULL,
  ORIGIN               VARCHAR2(240 BYTE)           NULL,
  DISPLAY_ORDER        NUMBER(5)                    NULL,
  VD_VERSION           NUMBER(4,2)                  NULL,
  VD_CONTEXT_NAME      VARCHAR2(30 BYTE)            NULL,
  VD_CONTEXT_VERSION   NUMBER(4,2)                  NULL,
  CON_ARRAY            VARCHAR2(200 BYTE)           NULL,
  VD_LONG_NAME         VARCHAR2(255 BYTE)           NULL
);


CREATE TABLE SBREXT.SOURCE_DATA_LOADS
(
  ID                      NUMBER(11)            NOT NULL,
  FILE_NAME               VARCHAR2(255 BYTE)    NOT NULL,
  STAGE_TIMESTAMP         DATE                  NOT NULL,
  FILE_DISPOSITION        VARCHAR2(50 BYTE)         NULL,
  DATA_TYPE               VARCHAR2(50 BYTE)         NULL,
  TOTAL_RECORDS_COUNT     NUMBER(11)                NULL,
  DATE_CREATED            DATE                  NOT NULL,
  CREATED_BY              VARCHAR2(30 BYTE)     NOT NULL,
  DATE_MODIFIED           DATE                      NULL,
  MODIFIED_BY             VARCHAR2(30 BYTE)         NULL,
  REJECTED_RECORDS_COUNT  NUMBER(11)                NULL
);


CREATE TABLE SBREXT.AC_CLASS_SCHEMES_STAGING
(
  ID                 NUMBER(11)                 NOT NULL,
  AC_ID              NUMBER(11)                 NOT NULL,
  CS_VERSION         NUMBER(4,2)                NOT NULL,
  CS_PREFERRED_NAME  VARCHAR2(30 BYTE)          NOT NULL,
  CONTE_NAME         VARCHAR2(30 BYTE)          NOT NULL,
  CONTE_VERSION      NUMBER(4,2)                NOT NULL,
  DATE_CREATED       DATE                       NOT NULL,
  CREATED_BY         VARCHAR2(30 BYTE)          NOT NULL,
  DATE_MODIFIED      DATE                           NULL,
  MODIFIED_BY        VARCHAR2(30 BYTE)              NULL,
  CHANGE_NOTE        VARCHAR2(2000 BYTE)            NULL,
  CSI_NAME           VARCHAR2(20 BYTE)          NOT NULL,
  CSI_TYPE           VARCHAR2(20 BYTE)              NULL
);


CREATE TABLE SBREXT.REPRESENTATIONS_EXT
(
  REP_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL,
  BEGIN_DATE            DATE                        NULL,
  END_DATE              DATE                        NULL,
  ORIGIN                VARCHAR2(240 BYTE)          NULL,
  DEFINITION_SOURCE     VARCHAR2(2000 BYTE)         NULL,
  DATE_CREATED          DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)           NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  REP_ID                NUMBER                  NOT NULL,
  CONDR_IDSEQ           CHAR(36 BYTE)               NULL
);


CREATE TABLE SBREXT.DEC_RELATIONSHIPS
(
  ID                      NUMBER(11)            NOT NULL,
  DEC_ID                  NUMBER(11)            NOT NULL,
  REL_DEC_VERSION         NUMBER(4,2)           NOT NULL,
  REL_DEC_PREFERRED_NAME  VARCHAR2(30 BYTE)     NOT NULL,
  REL_DEC_CONTE_NAME      VARCHAR2(30 BYTE)     NOT NULL,
  REL_DEC_CONTE_VERSION   NUMBER(4,2)           NOT NULL,
  REL_NAME                VARCHAR2(20 BYTE)     NOT NULL,
  DATE_CREATED            DATE                  NOT NULL,
  CREATED_BY              VARCHAR2(30 BYTE)     NOT NULL,
  RECORD_STATUS           VARCHAR2(50 BYTE)         NULL
);


CREATE TABLE SBREXT.PS_TXN
(
  ID             NUMBER(20)                         NULL,
  PARENTID       NUMBER(20)                         NULL,
  COLLID         NUMBER(10)                         NULL,
  CONTENT        BLOB                               NULL,
  CREATION_DATE  DATE                               NULL
);


CREATE TABLE SBREXT.PCOLL_CONTROL
(
  TABNAME        VARCHAR2(128 BYTE)             NOT NULL,
  ROWCREATEDATE  DATE                               NULL,
  CREATEDATE     DATE                               NULL,
  UPDATEDATE     DATE                               NULL
);


CREATE TABLE SBREXT.LOADER_DEFAULTS
(
  ID                 NUMBER(11)                 NOT NULL,
  SDE_ID             NUMBER(11)                 NOT NULL,
  CONTEXT_NAME       VARCHAR2(30 BYTE)              NULL,
  CONTEXT_VERSION    NUMBER(4,2)                    NULL,
  VERSION            NUMBER(4,2)                    NULL,
  WORKFLOW_STATUS    VARCHAR2(20 BYTE)              NULL,
  CD_VERSION         NUMBER(4,2)                    NULL,
  CD_PREFERRED_NAME  VARCHAR2(30 BYTE)              NULL,
  CD_CONTE_NAME      VARCHAR2(30 BYTE)              NULL,
  CD_CONTE_VERSION   NUMBER(4,2)                    NULL,
  VD_VERSION         NUMBER(4,2)                    NULL,
  VD_PREFERRED_NAME  VARCHAR2(30 BYTE)              NULL,
  VD_CONTE_NAME      VARCHAR2(30 BYTE)              NULL,
  VD_CONTE_VERSION   NUMBER(4,2)                    NULL,
  REF_DOC_NAME       VARCHAR2(30 BYTE)              NULL,
  REF_DOC_TYPE       VARCHAR2(20 BYTE)              NULL,
  REF_DOC_TEXT       VARCHAR2(2000 BYTE)            NULL,
  REF_DOC_URL        VARCHAR2(240 BYTE)             NULL,
  CS_CSI_IDSEQ       CHAR(36 BYTE)                  NULL
);


CREATE TABLE SBREXT.DATA_ELEMENTS_HST
(
  DE_HST_IDSEQ          CHAR(36 BYTE)           NOT NULL,
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  VERSION               NUMBER(4,2)             DEFAULT 1.0                   NOT NULL,
  CONTE_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  PREFERRED_NAME        VARCHAR2(30 BYTE)       NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  PREFERRED_DEFINITION  VARCHAR2(2000 BYTE)     NOT NULL,
  ASL_NAME              VARCHAR2(20 BYTE)       NOT NULL,
  LONG_NAME             VARCHAR2(255 BYTE)          NULL,
  LATEST_VERSION_IND    VARCHAR2(3 BYTE)            NULL,
  DELETED_IND           VARCHAR2(3 BYTE)            NULL,
  DATE_CREATED          DATE                    NOT NULL,
  BEGIN_DATE            DATE                        NULL,
  CREATED_BY            VARCHAR2(30 BYTE)       NOT NULL,
  END_DATE              DATE                        NULL,
  DATE_MODIFIED         DATE                        NULL,
  MODIFIED_BY           VARCHAR2(30 BYTE)           NULL,
  CHANGE_NOTE           VARCHAR2(2000 BYTE)         NULL
);


CREATE TABLE SBREXT.DATA_ELEMENT_CONCEPTS_STAGING
(
  ID                        NUMBER(11)          NOT NULL,
  SDE_ID                    NUMBER(11)          NOT NULL,
  DEC_VERSION               NUMBER(4,2)         NOT NULL,
  DEC_PREFERRED_NAME        VARCHAR2(30 BYTE)   NOT NULL,
  DEC_CONTE_NAME            VARCHAR2(30 BYTE)   NOT NULL,
  DEC_CONTE_VERSION         NUMBER(4,2)         NOT NULL,
  DEC_PREFERRED_DEFINITION  VARCHAR2(2000 BYTE) NOT NULL,
  DEC_LONG_NAME             VARCHAR2(255 BYTE)      NULL,
  WORKFLOW_STATUS           VARCHAR2(20 BYTE)   NOT NULL,
  BEGIN_DATE                VARCHAR2(30 BYTE)       NULL,
  END_DATE                  VARCHAR2(30 BYTE)       NULL,
  CD_VERSION                NUMBER(4,2)         NOT NULL,
  CD_PREFERRED_NAME         VARCHAR2(30 BYTE)   NOT NULL,
  CD_CONTE_NAME             VARCHAR2(30 BYTE)   NOT NULL,
  CD_CONTE_VERSION          NUMBER(4,2)         NOT NULL,
  ORIGIN                    VARCHAR2(240 BYTE)      NULL,
  UNRESOLVED_ISSUE          VARCHAR2(200 BYTE)      NULL,
  OBJ_CLASS_QUALIFIER       VARCHAR2(20 BYTE)       NULL,
  PROPERTY_QUALIFIER        VARCHAR2(20 BYTE)       NULL,
  ADMIN_NOTE                VARCHAR2(2000 BYTE)     NULL,
  COMMENTS                  VARCHAR2(2000 BYTE)     NULL,
  EXAMPLE                   VARCHAR2(2000 BYTE)     NULL,
  DATE_CREATED              DATE                NOT NULL,
  CREATED_BY                VARCHAR2(30 BYTE)   NOT NULL,
  RECORD_STATUS             VARCHAR2(50 BYTE)       NULL,
  OC_PREFERRED_NAME         VARCHAR2(30 BYTE)       NULL,
  OC_CONTE_NAME             VARCHAR2(30 BYTE)       NULL,
  OC_VERSION                NUMBER                  NULL,
  OC_CONTE_VERSION          NUMBER                  NULL,
  PROP_PREFERRED_NAME       VARCHAR2(30 BYTE)       NULL,
  PROP_CONTE_NAME           VARCHAR2(30 BYTE)       NULL,
  PROP_VERSION              NUMBER                  NULL,
  PROP_CONTE_VERSION        NUMBER                  NULL
);


CREATE TABLE SBREXT.QUESTION_CONDITIONS_EXT
(
  QCON_IDSEQ     CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.AC_SOURCES_EXT
(
  ACS_IDSEQ       CHAR(36 BYTE)                 NOT NULL,
  AC_IDSEQ        CHAR(36 BYTE)                 NOT NULL,
  SRC_NAME        VARCHAR2(100 BYTE)            NOT NULL,
  DATE_SUBMITTED  DATE                              NULL,
  CREATED_BY      VARCHAR2(30 BYTE)             NOT NULL,
  DATE_CREATED    DATE                          NOT NULL,
  DATE_MODIFIED   DATE                              NULL,
  MODIFIED_BY     VARCHAR2(30 BYTE)                 NULL
);


CREATE TABLE SBREXT.MLOG$_OC_RECS_EXT
(
  OCR_IDSEQ        CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_OC_RECS_EXT IS 'snapshot log for master table SBREXT.OC_RECS_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_OC_RECS_EXT
(
  OCR_IDSEQ        CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_OC_RECS_EXT IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.MLOG$_OBJECT_CLASSES_EXT
(
  OC_IDSEQ         CHAR(36 BYTE)                    NULL,
  SNAPTIME$$       DATE                             NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  OLD_NEW$$        VARCHAR2(1 BYTE)                 NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
);

COMMENT ON TABLE SBREXT.MLOG$_OBJECT_CLASSES_EXT IS 'snapshot log for master table SBREXT.OBJECT_CLASSES_EXT';


CREATE GLOBAL TEMPORARY TABLE SBREXT.RUPD$_OBJECT_CLASSES_EXT
(
  OC_IDSEQ         CHAR(36 BYTE)                    NULL,
  DMLTYPE$$        VARCHAR2(1 BYTE)                 NULL,
  SNAPID           INTEGER                          NULL,
  CHANGE_VECTOR$$  RAW(255)                         NULL
)
ON COMMIT PRESERVE ROWS;

COMMENT ON TABLE SBREXT.RUPD$_OBJECT_CLASSES_EXT IS 'temporary updatable snapshot log';


CREATE TABLE SBREXT.UP_CADSR_PROJECT_MVW
(
  CS_IDSEQ      CHAR(36 BYTE)                   NOT NULL,
  CP_IDSEQ      CHAR(36 BYTE)                   NOT NULL,
  SHORT_NAME    VARCHAR2(30 BYTE)               NOT NULL,
  LONG_NAME     VARCHAR2(255 BYTE)                  NULL,
  DESCRIPTION   VARCHAR2(2000 BYTE)             NOT NULL,
  VERSION       NUMBER(4,2)                     NOT NULL,
  GMENAMESPACE  VARCHAR2(2000 BYTE)                 NULL,
  PUBLIC_ID     NUMBER                          NOT NULL
);


CREATE TABLE SBREXT.UP_SUB_PROJECTS_MVW
(
  SP_IDSEQ                 CHAR(36 BYTE)            NULL,
  SUB_PROJECT_NAME         VARCHAR2(255 BYTE)   NOT NULL,
  SUB_PROJECT_DESCRIPTION  VARCHAR2(2000 BYTE)  NOT NULL,
  CS_IDSEQ                 CHAR(36 BYTE)        NOT NULL,
  CS_CSI_IDSEQ             CHAR(36 BYTE)        NOT NULL,
  CP_IDSEQ                 CHAR(36 BYTE)        NOT NULL,
  VERSION                  NUMBER               NOT NULL,
  PUBLIC_ID                NUMBER               NOT NULL
);


CREATE TABLE SBREXT.UP_PACKAGES_MVW_TEMP
(
  PG_IDSEQ        CHAR(36 BYTE)                     NULL,
  NAME            VARCHAR2(255 BYTE)                NULL,
  DESCRIPTION     VARCHAR2(2000 BYTE)               NULL,
  CS_IDSEQ        CHAR(36 BYTE)                     NULL,
  CS_CSI_IDSEQ    CHAR(36 BYTE)                     NULL,
  CP_IDSEQ        CHAR(36 BYTE)                     NULL,
  P_CS_CSI_IDSEQ  CHAR(36 BYTE)                     NULL,
  GMENAMESPACE    VARCHAR2(4000 BYTE)               NULL,
  VERSION         NUMBER                            NULL,
  PUBLIC_ID       NUMBER                            NULL,
  PACKAGE_TYPE    VARCHAR2(19 BYTE)                 NULL
);


CREATE TABLE SBREXT.UP_PACKAGES_MVW
(
  PG_IDSEQ      CHAR(36 BYTE)                       NULL,
  NAME          VARCHAR2(255 BYTE)                  NULL,
  DESCRIPTION   VARCHAR2(2000 BYTE)                 NULL,
  CS_IDSEQ      CHAR(36 BYTE)                       NULL,
  CS_CSI_IDSEQ  CHAR(36 BYTE)                       NULL,
  CP_IDSEQ      CHAR(36 BYTE)                       NULL,
  SP_IDSEQ      CHAR(36 BYTE)                       NULL,
  GMENAMESPACE  VARCHAR2(4000 BYTE)                 NULL,
  VERSION       NUMBER                              NULL,
  PUBLIC_ID     NUMBER                              NULL,
  PACKAGE_TYPE  VARCHAR2(19 BYTE)                   NULL
);


CREATE TABLE SBREXT.UP_CLASS_METADATA_MVW_TEMP
(
  CM_IDSEQ                    CHAR(36 BYTE)         NULL,
  NAME                        VARCHAR2(2000 BYTE) NOT NULL,
  DESCRIPTION                 VARCHAR2(2000 BYTE) NOT NULL,
  OC_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  PG_IDSEQ                    CHAR(36 BYTE)         NULL,
  CS_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  DESIG_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  DEFIN_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  CS_CSI_IDSEQ                CHAR(36 BYTE)         NULL,
  FULLY_QUALIFIED_CLASS_NAME  VARCHAR2(2256 BYTE)     NULL,
  CP_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  GMENAMESPACE                VARCHAR2(4000 BYTE)     NULL,
  GMEXMLELEMENT               VARCHAR2(4000 BYTE)     NULL,
  VERSION                     NUMBER(4,2)       NOT NULL,
  PUBLIC_ID                   NUMBER            NOT NULL
);


CREATE TABLE SBREXT.UP_GEN_METADATA_MVW
(
  GM_IDSEQ    CHAR(36 BYTE)                         NULL,
  OCR_IDSEQ   CHAR(36 BYTE)                     NOT NULL,
  T_CM_IDSEQ  CHAR(36 BYTE)                         NULL,
  S_CM_IDSEQ  CHAR(36 BYTE)                         NULL,
  CS_IDSEQ    CHAR(36 BYTE)                     NOT NULL,
  CP_IDSEQ    CHAR(36 BYTE)                     NOT NULL,
  VERSION     NUMBER(4,2)                       NOT NULL,
  PUBLIC_ID   NUMBER                                NULL
);


CREATE TABLE SBREXT.UP_CLASS_METADATA_MVW
(
  CM_IDSEQ                    CHAR(36 BYTE)         NULL,
  NAME                        VARCHAR2(2000 BYTE) NOT NULL,
  DESCRIPTION                 VARCHAR2(2000 BYTE) NOT NULL,
  OC_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  PG_IDSEQ                    CHAR(36 BYTE)         NULL,
  CS_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  DESIG_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  DEFIN_IDSEQ                 CHAR(36 BYTE)     NOT NULL,
  CS_CSI_IDSEQ                CHAR(36 BYTE)         NULL,
  FULLY_QUALIFIED_CLASS_NAME  VARCHAR2(2256 BYTE)     NULL,
  CP_IDSEQ                    CHAR(36 BYTE)     NOT NULL,
  GM_IDSEQ                    CHAR(36 BYTE)         NULL,
  GMENAMESPACE                VARCHAR2(4000 BYTE)     NULL,
  GMEXMLELEMENT               VARCHAR2(4000 BYTE)     NULL,
  VERSION                     NUMBER(4,2)       NOT NULL,
  PUBLIC_ID                   NUMBER            NOT NULL
);


CREATE TABLE SBREXT.UP_ATTRIBUTE_METADATA_MVW_TEMP
(
  AM_IDSEQ              CHAR(36 BYTE)               NULL,
  NAME                  VARCHAR2(2000 BYTE)         NULL,
  DESCRIPTION           VARCHAR2(2000 BYTE)     NOT NULL,
  DE_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  PG_IDSEQ              CHAR(36 BYTE)               NULL,
  CS_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  DESIG_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  DEFIN_IDSEQ           CHAR(36 BYTE)           NOT NULL,
  CS_CSI_IDSEQ          CHAR(36 BYTE)               NULL,
  DEC_IDSEQ             CHAR(36 BYTE)           NOT NULL,
  VD_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  FULLY_QUALIFIED_NAME  VARCHAR2(2000 BYTE)     NOT NULL,
  CM_IDSEQ              CHAR(36 BYTE)               NULL,
  PROP_IDSEQ            CHAR(36 BYTE)               NULL,
  CP_IDSEQ              CHAR(36 BYTE)           NOT NULL,
  GMEXMLLOCREFERENCE    VARCHAR2(4000 BYTE)         NULL,
  VERSION               NUMBER(4,2)             NOT NULL,
  PUBLIC_ID             NUMBER                  NOT NULL,
  CLASS_GMEXMLELEMENT   VARCHAR2(4000 BYTE)         NULL,
  CLASS_GMENAMESPACE    VARCHAR2(4000 BYTE)         NULL
);


CREATE TABLE SBREXT.UP_ATTRIBUTE_TYPE_METADATA_MVW
(
  AT_IDSEQ      CHAR(36 BYTE)                       NULL,
  AM_IDSEQ      CHAR(36 BYTE)                       NULL,
  DE_IDSEQ      CHAR(36 BYTE)                   NOT NULL,
  VD_IDSEQ      CHAR(36 BYTE)                   NOT NULL,
  VD_DATATYPE   VARCHAR2(20 BYTE)               NOT NULL,
  VD_LONG_NAME  VARCHAR2(255 BYTE)                  NULL,
  PUBLIC_ID     NUMBER                          NOT NULL,
  VERSION       NUMBER(4,2)                     NOT NULL
);


CREATE TABLE SBREXT.PROTOCOL_QC_EXT
(
  PQ_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  PROTO_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  QC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  DATE_CREATED   DATE                           NOT NULL,
  CREATED_BY     VARCHAR2(30 BYTE)              NOT NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL
);


CREATE TABLE SBREXT.QUEST_ATTRIBUTES_EXT
(
  VV_IDSEQ       CHAR(36 BYTE)                      NULL,
  QC_IDSEQ       CHAR(36 BYTE)                  NOT NULL,
  QUEST_IDSEQ    CHAR(36 BYTE)                  NOT NULL,
  EDITABLE_IND   CHAR(3 BYTE)                   NOT NULL,
  DATE_CREATED   DATE                               NULL,
  DATE_MODIFIED  DATE                               NULL,
  MODIFIED_BY    VARCHAR2(30 BYTE)                  NULL,
  CREATED_BY     VARCHAR2(30 BYTE)                  NULL,
  DEFAULT_VALUE  VARCHAR2(2000 BYTE)                NULL,
  QCON_IDSEQ     CHAR(36 BYTE)                      NULL,
  MANDATORY_IND  VARCHAR2(3 BYTE)                   NULL
);


CREATE TABLE SBREXT.PROPERTIES_STAGING
(
  ID                              NUMBER(11)    NOT NULL,
  SDE_ID                          NUMBER(11)    NOT NULL,
  PT_VERSION                      NUMBER(4,2)   NOT NULL,
  PT_PREFERRED_NAME               VARCHAR2(30 BYTE) NOT NULL,
  PT_CONTE_NAME                   VARCHAR2(30 BYTE) NOT NULL,
  PT_CONTE_VERSION                NUMBER(4,2)       NULL,
  PT_PREFERRED_DEFINITION         VARCHAR2(2000 BYTE) NOT NULL,
  PT_LONG_NAME                    VARCHAR2(255 BYTE)     NULL,
  PT_WORKFLOW_STATUS              VARCHAR2(20 BYTE) NOT NULL,
  BEGIN_DATE                      VARCHAR2(30 BYTE)     NULL,
  END_DATE                        VARCHAR2(30 BYTE)     NULL,
  ADMIN_NOTE                      VARCHAR2(2000 BYTE)     NULL,
  COMMENTS                        VARCHAR2(2000 BYTE)     NULL,
  ORIGIN                          VARCHAR2(240 BYTE)     NULL,
  UNRESOLVED_ISSUE                VARCHAR2(200 BYTE)     NULL,
  EXAMPLE                         VARCHAR2(2000 BYTE)     NULL,
  RECORD_STATUS                   VARCHAR2(50 BYTE)     NULL,
  DATE_CREATED                    DATE          NOT NULL,
  CREATED_BY                      VARCHAR2(30 BYTE) NOT NULL,
  PT_PREFERRED_DEFINITION_SOURCE  VARCHAR2(2000 BYTE)     NULL
);


CREATE TABLE SBREXT.DECR_STAGING
(
  ID                  NUMBER(11)                NOT NULL,
  DCS_ID              NUMBER(11)                NOT NULL,
  REL_NAME            VARCHAR2(20 BYTE)         NOT NULL,
  DEC_PREFERRED_NAME  VARCHAR2(30 BYTE)         NOT NULL,
  DEC_VERSION         NUMBER(4,2)               NOT NULL,
  DEC_CONTE_NAME      VARCHAR2(30 BYTE)         NOT NULL,
  DEC_CONTE_VERSION   NUMBER(4,2)               NOT NULL,
  RECORD_STATUS       VARCHAR2(50 BYTE)             NULL,
  DATE_CREATED        DATE                      NOT NULL,
  CREATED_BY          VARCHAR2(30 BYTE)         NOT NULL,
  DESCRIPTION         VARCHAR2(2000 BYTE)           NULL
);


CREATE UNIQUE INDEX SBREXT.AAI_PK ON SBREXT.AC_ATT_CSCSI_EXT
(ACA_IDSEQ);


CREATE UNIQUE INDEX SBREXT.AAL_PK ON SBREXT.AC_ATT_TYPES_LOV_EXT
(ATL_NAME);


CREATE UNIQUE INDEX SBREXT.ACCH_PK ON SBREXT.AC_CHANGE_HISTORY_EXT
(ACCH_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CS_PK ON SBREXT.AC_CLASS_SCHEMES_STAGING
(ID);


CREATE INDEX SBREXT.AST_ACT_FK_IDX ON SBREXT.AC_SOURCES_EXT
(AC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.ACS_PK ON SBREXT.AC_SOURCES_EXT
(ACS_IDSEQ);


CREATE UNIQUE INDEX SBREXT.ACS_UK ON SBREXT.AC_SOURCES_EXT
(AC_IDSEQ, SRC_NAME);


CREATE UNIQUE INDEX SBREXT.ACS_HST_PK ON SBREXT.AC_SOURCES_HST
(ACS_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.ASU_PK ON SBREXT.AC_SUBMITTERS_STAGING
(ID);


CREATE BITMAP INDEX SBREXT.AC_ACTL_NAME_BINDX ON SBREXT.ADMINISTERED_COMPONENTS_HST
(ACTL_NAME);


CREATE INDEX SBREXT.UPPERCASE_AC_IDX ON SBREXT.ADMINISTERED_COMPONENTS_HST
(PREFERRED_NAME);


CREATE BITMAP INDEX SBREXT.AC_LATEST_BINDX ON SBREXT.ADMINISTERED_COMPONENTS_HST
(LATEST_VERSION_IND);


CREATE UNIQUE INDEX SBREXT.AC_HST_PK ON SBREXT.ADMINISTERED_COMPONENTS_HST
(AC_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.AC_HST_UK ON SBREXT.ADMINISTERED_COMPONENTS_HST
(VERSION, PREFERRED_NAME, CONTE_IDSEQ, ACTL_NAME);


CREATE BITMAP INDEX SBREXT.AC_DELETED_BINDX ON SBREXT.ADMINISTERED_COMPONENTS_HST
(DELETED_IND);


CREATE INDEX SBREXT.AAT_ASL_FK_IDX ON SBREXT.ASL_ACTL_EXT
(ASL_NAME);


CREATE INDEX SBREXT.AAT_ATL_FK_IDX ON SBREXT.ASL_ACTL_EXT
(ACTL_NAME);


CREATE UNIQUE INDEX SBREXT.AAT_PK ON SBREXT.ASL_ACTL_EXT
(ASL_NAME, ACTL_NAME);


CREATE UNIQUE INDEX SBREXT.CCM_PK ON SBREXT.CDE_CART_ITEMS
(CCM_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CCM_UK ON SBREXT.CDE_CART_ITEMS
(UA_NAME, AC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CSC_PK ON SBREXT.CLASS_SCHEMES_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.CSI_PK ON SBREXT.CLASS_SCHEME_ITEMS_STAGING
(ID);


CREATE INDEX SBREXT.COMP_DO_IDX ON SBREXT.COMPONENT_CONCEPTS_EXT
(CONDR_IDSEQ, DISPLAY_ORDER, CON_IDSEQ);


CREATE INDEX SBREXT.COMP_CON_IDX ON SBREXT.COMPONENT_CONCEPTS_EXT
(CON_IDSEQ);


CREATE INDEX SBREXT.COMP_CONDR_IDX ON SBREXT.COMPONENT_CONCEPTS_EXT
(CONDR_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CCT_PK ON SBREXT.COMPONENT_CONCEPTS_EXT
(CC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CLT_PK ON SBREXT.COMPONENT_LEVELS_EXT
(CL_IDSEQ);


CREATE INDEX SBREXT.CON_LONG_NAME_IDX ON SBREXT.CONCEPTS_EXT
(LONG_NAME);


CREATE UNIQUE INDEX SBREXT.CON_PK ON SBREXT.CONCEPTS_EXT
(CON_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CON_UK ON SBREXT.CONCEPTS_EXT
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE UNIQUE INDEX SBREXT.CD_PK ON SBREXT.CONCEPTUAL_DOMAINS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.CONST_PK ON SBREXT.CONCEPT_SOURCES_LOV_EXT
(CONCEPT_SOURCE);


CREATE UNIQUE INDEX SBREXT.QCT_PK ON SBREXT.CONDITION_COMPONENTS_EXT
(CMP_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CMT_PK ON SBREXT.CONDITION_MESSAGE_EXT
(CM_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CRT_PK ON SBREXT.CONTACT_ROLES_EXT
(CONTACT_ROLE);


CREATE INDEX SBREXT.CONDR_NAME_IDX ON SBREXT.CON_DERIVATION_RULES_EXT
(NAME);


CREATE INDEX SBREXT.CONDR_CRTL_IDX ON SBREXT.CON_DERIVATION_RULES_EXT
(CRTL_NAME);


CREATE UNIQUE INDEX SBREXT.CONDR_PK ON SBREXT.CON_DERIVATION_RULES_EXT
(CONDR_IDSEQ);


CREATE UNIQUE INDEX SBREXT.CTP_PK ON SBREXT.CRF_TOOL_PARAMETER_EXT
(PROTO_IDSEQ, QC_IDSEQ);


CREATE BITMAP INDEX SBREXT.DE_LATEST_BINDX ON SBREXT.DATA_ELEMENTS_HST
(LATEST_VERSION_IND);


CREATE UNIQUE INDEX SBREXT.DE_HST_UK ON SBREXT.DATA_ELEMENTS_HST
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE UNIQUE INDEX SBREXT.DE_HST_PK ON SBREXT.DATA_ELEMENTS_HST
(DE_HST_IDSEQ);


CREATE BITMAP INDEX SBREXT.DE_DELETED_BINDX ON SBREXT.DATA_ELEMENTS_HST
(DELETED_IND);


CREATE UNIQUE INDEX SBREXT.DE_PK ON SBREXT.DATA_ELEMENTS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.DEC_PK ON SBREXT.DATA_ELEMENT_CONCEPTS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.DCR_PK ON SBREXT.DECR_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.DER_PK ON SBREXT.DEC_RELATIONSHIPS
(ID);


CREATE UNIQUE INDEX SBREXT.DCS_PK ON SBREXT.DEC_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.DEF_PK ON SBREXT.DEFINITIONS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.DTT_PK ON SBREXT.DEFINITION_TYPES_LOV_EXT
(DEFL_NAME);


CREATE UNIQUE INDEX SBREXT.DES_PK ON SBREXT.DESIGNATIONS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.EAT_PK ON SBREXT.EATTRIBUTES_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.ECL_PK ON SBREXT.ECLASSES_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.ERE_PK ON SBREXT.EREFERENCES_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.ERR_PK ON SBREXT.ERRORS_EXT
(ERROR_CODE);


CREATE UNIQUE INDEX SBREXT.ESU_PK ON SBREXT.ESUPERTYPES_STAGING
(ID);


CREATE INDEX SBREXT.GS_COMPOSITE_AC_TABLE_NDX ON SBREXT.GS_COMPOSITE
(AC_TABLE);


CREATE INDEX SBREXT.GS_COMPOSITE_AC_IDSEQ_NDX ON SBREXT.GS_COMPOSITE
(AC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.GS_TABLE_NAME_NDX ON SBREXT.GS_TABLES_LOV
(NAME);


CREATE UNIQUE INDEX SBREXT.GS_TABLE_ABBREV_NDX ON SBREXT.GS_TABLES_LOV
(ABBREV);


CREATE UNIQUE INDEX SBREXT.GTV_PK ON SBREXT.GS_TABLES_LOV
(AC_TABLE);


CREATE INDEX SBREXT.GS_TOKENS_AC_TABLE_NDX ON SBREXT.GS_TOKENS
(AC_TABLE);


CREATE UNIQUE INDEX SBREXT.LDT_PK ON SBREXT.LOADER_DEFAULTS
(ID);


CREATE INDEX SBREXT.MRT_VDN_FK ON SBREXT.MATCH_RESULTS_EXT
(VD_MATCH_IDSEQ);


CREATE INDEX SBREXT.MRT_RDT_FK ON SBREXT.MATCH_RESULTS_EXT
(RD_MATCH_IDSEQ);


CREATE INDEX SBREXT.MRT_QC_SUBMIT_FK ON SBREXT.MATCH_RESULTS_EXT
(QC_SUBMIT_IDSEQ);


CREATE INDEX SBREXT.MRT_QC_MATCH_FK ON SBREXT.MATCH_RESULTS_EXT
(QC_MATCH_IDSEQ);


CREATE INDEX SBREXT.MRT_QC_CRF_FK ON SBREXT.MATCH_RESULTS_EXT
(QC_CRF_IDSEQ);


CREATE INDEX SBREXT.MRT_PVE_FK ON SBREXT.MATCH_RESULTS_EXT
(PV_MATCH_IDSEQ);


CREATE INDEX SBREXT.MRT_DET_FK ON SBREXT.MATCH_RESULTS_EXT
(DE_MATCH_IDSEQ);


CREATE INDEX SBREXT.MRT_ASV_FK ON SBREXT.MATCH_RESULTS_EXT
(ASL_NAME_OF_MATCH);


CREATE UNIQUE INDEX SBREXT.MTT_PK ON SBREXT.MESSAGE_TYPES_EXT
(MT_NAME);


CREATE UNIQUE INDEX SBREXT.OCT_PK ON SBREXT.OBJECT_CLASSES_EXT
(OC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.OCT_UK ON SBREXT.OBJECT_CLASSES_EXT
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE UNIQUE INDEX SBREXT.OCS_PK ON SBREXT.OBJECT_CLASSES_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.ORT_PK ON SBREXT.OC_RECS_EXT
(OCR_IDSEQ);


CREATE UNIQUE INDEX SBREXT.OCR_UK ON SBREXT.OC_RECS_EXT
(CONTE_IDSEQ, PREFERRED_NAME, VERSION);


CREATE UNIQUE INDEX SBREXT.PCOLL_CONTROL_PK ON SBREXT.PCOLL_CONTROL
(TABNAME);


CREATE UNIQUE INDEX SBREXT.PV_HST_UK ON SBREXT.PERMISSIBLE_VALUES_HST
(VALUE, SHORT_MEANING);


CREATE UNIQUE INDEX SBREXT.PV_HST_PK ON SBREXT.PERMISSIBLE_VALUES_HST
(PV_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PROP_PK ON SBREXT.PROPERTIES_EXT
(PROP_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PROP_UK ON SBREXT.PROPERTIES_EXT
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE UNIQUE INDEX SBREXT.PT_PK ON SBREXT.PROPERTIES_STAGING
(ID);


CREATE INDEX SBREXT.PROTO_LONG_NAME ON SBREXT.PROTOCOLS_EXT
(LONG_NAME);


CREATE INDEX SBREXT.PROTO_ASV_FK_IDX ON SBREXT.PROTOCOLS_EXT
(ASL_NAME);


CREATE INDEX SBREXT.PROTO_COT_FK_IDX ON SBREXT.PROTOCOLS_EXT
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PRO_PK ON SBREXT.PROTOCOLS_EXT
(PROTO_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PROTO_UK ON SBREXT.PROTOCOLS_EXT
(VERSION, CONTE_IDSEQ, PREFERRED_NAME);


CREATE UNIQUE INDEX SBREXT.PK_PROTOCOL_QUEST_EXT ON SBREXT.PROTOCOL_QC_EXT
(PQ_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PS_TXN_PK ON SBREXT.PS_TXN
(ID, COLLID);


CREATE UNIQUE INDEX SBREXT.QCDL_PK ON SBREXT.QC_DISPLAY_LOV_EXT
(QCDL_NAME);


CREATE INDEX SBREXT.QRS_RLV_FK ON SBREXT.QC_RECS_EXT
(RL_NAME);


CREATE INDEX SBREXT.QRS_QC_FK2 ON SBREXT.QC_RECS_EXT
(C_QC_IDSEQ);


CREATE INDEX SBREXT.QRS_QC_FK1 ON SBREXT.QC_RECS_EXT
(P_QC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QQRS_PK ON SBREXT.QC_RECS_EXT
(QR_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QQRH_PK ON SBREXT.QC_RECS_HST
(QRH_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QTL_PK ON SBREXT.QC_TYPE_LOV_EXT
(QTL_NAME);


CREATE UNIQUE INDEX SBREXT.QLV_PK ON SBREXT.QUALIFIER_LOV_EXT
(QUALIFIER_NAME);


CREATE UNIQUE INDEX SBREXT.QCON_PK ON SBREXT.QUESTION_CONDITIONS_EXT
(QCON_IDSEQ);


CREATE INDEX SBREXT.QAT_QC_VV_FK ON SBREXT.QUEST_ATTRIBUTES_EXT
(VV_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QAT_PK ON SBREXT.QUEST_ATTRIBUTES_EXT
(QUEST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QAT_QC_UK ON SBREXT.QUEST_ATTRIBUTES_EXT
(QC_IDSEQ);


CREATE INDEX SBREXT.QC_VPV_FK ON SBREXT.QUEST_CONTENTS_EXT
(VP_IDSEQ);


CREATE INDEX SBREXT.QC_QTL_QC ON SBREXT.QUEST_CONTENTS_EXT
(QC_IDSEQ, QCDL_NAME);


CREATE INDEX SBREXT.QC_QTL_FK ON SBREXT.QUEST_CONTENTS_EXT
(QTL_NAME);


CREATE INDEX SBREXT.QC_QC2_IDX ON SBREXT.QUEST_CONTENTS_EXT
(QTL_NAME, DN_CRF_IDSEQ);


CREATE INDEX SBREXT.QC_PROTO_FK ON SBREXT.QUEST_CONTENTS_EXT
(PROTO_IDSEQ);


CREATE INDEX SBREXT.QC_DN_CRF ON SBREXT.QUEST_CONTENTS_EXT
(DN_CRF_IDSEQ);


CREATE INDEX SBREXT.QC_DET_FK ON SBREXT.QUEST_CONTENTS_EXT
(DE_IDSEQ);


CREATE INDEX SBREXT.QC_COT_FK ON SBREXT.QUEST_CONTENTS_EXT
(CONTE_IDSEQ);


CREATE INDEX SBREXT.QC_ASV_FK ON SBREXT.QUEST_CONTENTS_EXT
(ASL_NAME);


CREATE UNIQUE INDEX SBREXT.QCE_PK ON SBREXT.QUEST_CONTENTS_EXT
(QC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QC_HST_PK ON SBREXT.QUEST_CONTENTS_HST
(QC_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.QVT_PK ON SBREXT.QUEST_VV_EXT
(QV_IDSEQ);


CREATE UNIQUE INDEX SBREXT.RD_PK ON SBREXT.REF_DOCS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.REP_PK ON SBREXT.REPRESENTATIONS_EXT
(REP_IDSEQ);


CREATE UNIQUE INDEX SBREXT.REP_UK ON SBREXT.REPRESENTATIONS_EXT
(PREFERRED_NAME, CONTE_IDSEQ, VERSION);


CREATE UNIQUE INDEX SBREXT.REPR_PK ON SBREXT.REPRESENTATIONS_STAGING
(ID);


CREATE UNIQUE INDEX SBREXT.RET_PK ON SBREXT.REPRESENTATION_LOV_EXT
(REPRESENTATION_NAME);


CREATE UNIQUE INDEX SBREXT.RFL_PK ON SBREXT.REVIEWER_FEEDBACK_LOV_EXT
(REVIEWER_FEEDBACK);


CREATE UNIQUE INDEX SBREXT.RFT_PK ON SBREXT.RULE_FUNCTIONS_EXT
(RF_IDSEQ);


CREATE UNIQUE INDEX SBREXT.SAT_PK ON SBREXT.SN_ALERT_EXT
(AL_IDSEQ);


CREATE UNIQUE INDEX SBREXT.SQT_PK ON SBREXT.SN_QUERY_EXT
(QUR_IDSEQ);


CREATE UNIQUE INDEX SBREXT.SPT_PK ON SBREXT.SN_RECIPIENT_EXT
(REC_IDSEQ);


CREATE UNIQUE INDEX SBREXT.SRT_PK ON SBREXT.SN_REPORT_EXT
(REP_IDSEQ);


CREATE UNIQUE INDEX SBREXT.SCT_PK ON SBREXT.SN_REP_CONTENTS_EXT
(CTT_IDSEQ);


CREATE UNIQUE INDEX SBREXT.STL_PK ON SBREXT.SOURCES_EXT
(SRC_NAME);


CREATE UNIQUE INDEX SBREXT.SDE_PK ON SBREXT.SOURCE_DATA_LOADS
(ID);


CREATE UNIQUE INDEX SBREXT.SUB_PK ON SBREXT.SUBSTITUTIONS_EXT
(SUB_IDSEQ);


CREATE UNIQUE INDEX SBREXT.TPI_PK ON SBREXT.TA_PROTO_CSI_EXT
(TP_IDSEQ);


CREATE UNIQUE INDEX SBREXT.TS_PK ON SBREXT.TEXT_STRINGS_EXT
(TS_IDSEQ);


CREATE UNIQUE INDEX SBREXT.PK_TOOL_OPTIONS ON SBREXT.TOOL_OPTIONS_EXT
(TOOL_IDSEQ);


CREATE UNIQUE INDEX SBREXT.TOOL_OPTIONS_UNIQ ON SBREXT.TOOL_OPTIONS_EXT
(TOOL_NAME, PROPERTY);


CREATE UNIQUE INDEX SBREXT.TOOL_UK ON SBREXT.TOOL_PROPERTIES_EXT
(NAME, TOOL);


CREATE UNIQUE INDEX SBREXT.TAT_PK ON SBREXT.TRIGGERED_ACTIONS_EXT
(TA_IDSEQ);


CREATE UNIQUE INDEX SBREXT.TSTL_PK ON SBREXT.TS_TYPE_LOV_EXT
(TSTL_NAME);


CREATE UNIQUE INDEX SBREXT.UMT_PK ON SBREXT.UI_MENU_TREE_EXT
(NODE);


CREATE UNIQUE INDEX SBREXT.UML_LDT_PK ON SBREXT.UML_LOADER_DEFAULTS
(ID);


CREATE UNIQUE INDEX SBREXT.PROJ_NAME_VER_UK ON SBREXT.UML_LOADER_DEFAULTS
(PROJECT_NAME, PROJECT_VERSION);


CREATE UNIQUE INDEX SBREXT.UL_PK ON SBREXT.USERS_LOCKOUT
(UA_NAME);


CREATE UNIQUE INDEX SBREXT.VVT_PK ON SBREXT.VALID_VALUES_ATT_EXT
(QC_IDSEQ);


CREATE BITMAP INDEX SBREXT.VD_LATEST_BINDX ON SBREXT.VALUE_DOMAINS_HST
(LATEST_VERSION_IND);


CREATE UNIQUE INDEX SBREXT.VD_HST_UK ON SBREXT.VALUE_DOMAINS_HST
(VERSION, PREFERRED_NAME, CONTE_IDSEQ);


CREATE UNIQUE INDEX SBREXT.VD_HST_PK ON SBREXT.VALUE_DOMAINS_HST
(VD_HST_IDSEQ);


CREATE BITMAP INDEX SBREXT.VD_DELETED_BINDX ON SBREXT.VALUE_DOMAINS_HST
(DELETED_IND);


CREATE UNIQUE INDEX SBREXT.VP_HST_UK ON SBREXT.VD_PVS_HST
(CONTE_IDSEQ);


CREATE UNIQUE INDEX SBREXT.VP_HST_PK ON SBREXT.VD_PVS_HST
(VP_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.VPS_UK ON SBREXT.VD_PVS_SOURCES_EXT
(VP_IDSEQ, SRC_NAME);


CREATE UNIQUE INDEX SBREXT.VPS_PK ON SBREXT.VD_PVS_SOURCES_EXT
(VPS_IDSEQ);


CREATE UNIQUE INDEX SBREXT.VPS_HST_PK ON SBREXT.VD_PVS_SOURCES_HST
(VPS_HST_IDSEQ);


CREATE UNIQUE INDEX SBREXT.XLE_PK ON SBREXT.XML_LOADER_ERRORS
(ID);


CREATE PUBLIC SYNONYM AC_ATT_CSCSI_EXT FOR SBREXT.AC_ATT_CSCSI_EXT;


CREATE PUBLIC SYNONYM AC_ATT_TYPES_LOV_EXT FOR SBREXT.AC_ATT_TYPES_LOV_EXT;


CREATE PUBLIC SYNONYM AC_CHANGE_HISTORY_EXT FOR SBREXT.AC_CHANGE_HISTORY_EXT;


CREATE PUBLIC SYNONYM AC_CLASS_SCHEMES_STAGING FOR SBREXT.AC_CLASS_SCHEMES_STAGING;


CREATE PUBLIC SYNONYM AC_SOURCES_EXT FOR SBREXT.AC_SOURCES_EXT;


CREATE PUBLIC SYNONYM AC_SOURCES_HST FOR SBREXT.AC_SOURCES_HST;


CREATE PUBLIC SYNONYM AC_SUBMITTERS_STAGING FOR SBREXT.AC_SUBMITTERS_STAGING;


CREATE PUBLIC SYNONYM ADMINISTERED_COMPONENTS_HST FOR SBREXT.ADMINISTERED_COMPONENTS_HST;


CREATE PUBLIC SYNONYM ADMIN_COMPONENTS_STAGING FOR SBREXT.ADMIN_COMPONENTS_STAGING;


CREATE PUBLIC SYNONYM ADMIN_COMPONENTS_STAGING_BKUP FOR SBREXT.ADMIN_COMPONENTS_STAGING_BKUP;


CREATE PUBLIC SYNONYM ASL_ACTL_EXT FOR SBREXT.ASL_ACTL_EXT;


CREATE PUBLIC SYNONYM CDE_CART_ITEMS FOR SBREXT.CDE_CART_ITEMS;


CREATE PUBLIC SYNONYM CLASS_SCHEMES_STAGING FOR SBREXT.CLASS_SCHEMES_STAGING;


CREATE PUBLIC SYNONYM CLASS_SCHEME_ITEMS_STAGING FOR SBREXT.CLASS_SCHEME_ITEMS_STAGING;


CREATE PUBLIC SYNONYM COMPONENT_CONCEPTS_EXT FOR SBREXT.COMPONENT_CONCEPTS_EXT;


CREATE PUBLIC SYNONYM COMPONENT_LEVELS_EXT FOR SBREXT.COMPONENT_LEVELS_EXT;


CREATE PUBLIC SYNONYM CONCEPTS_EXT FOR SBREXT.CONCEPTS_EXT;


CREATE PUBLIC SYNONYM CONCEPTS_STAGING FOR SBREXT.CONCEPTS_STAGING;


CREATE PUBLIC SYNONYM CONCEPTUAL_DOMAINS_STAGING FOR SBREXT.CONCEPTUAL_DOMAINS_STAGING;


CREATE PUBLIC SYNONYM CONCEPT_SOURCES_LOV_EXT FOR SBREXT.CONCEPT_SOURCES_LOV_EXT;


CREATE PUBLIC SYNONYM CONDITION_COMPONENTS_EXT FOR SBREXT.CONDITION_COMPONENTS_EXT;


CREATE PUBLIC SYNONYM CONDITION_MESSAGE_EXT FOR SBREXT.CONDITION_MESSAGE_EXT;


CREATE PUBLIC SYNONYM CONTACT_ROLES_EXT FOR SBREXT.CONTACT_ROLES_EXT;


CREATE PUBLIC SYNONYM CON_DERIVATION_RULES_EXT FOR SBREXT.CON_DERIVATION_RULES_EXT;


CREATE PUBLIC SYNONYM CREATE$JAVA$LOB$TABLE FOR SBREXT.CREATE$JAVA$LOB$TABLE;


CREATE PUBLIC SYNONYM CRF_TOOL_PARAMETER_EXT FOR SBREXT.CRF_TOOL_PARAMETER_EXT;


CREATE PUBLIC SYNONYM DATA_ELEMENTS_HST FOR SBREXT.DATA_ELEMENTS_HST;


CREATE PUBLIC SYNONYM DATA_ELEMENTS_STAGING FOR SBREXT.DATA_ELEMENTS_STAGING;


CREATE PUBLIC SYNONYM DATA_ELEMENT_CONCEPTS_STAGING FOR SBREXT.DATA_ELEMENT_CONCEPTS_STAGING;


CREATE PUBLIC SYNONYM DECR_STAGING FOR SBREXT.DECR_STAGING;


CREATE PUBLIC SYNONYM DEC_RELATIONSHIPS FOR SBREXT.DEC_RELATIONSHIPS;


CREATE PUBLIC SYNONYM DEC_STAGING FOR SBREXT.DEC_STAGING;


CREATE PUBLIC SYNONYM DEFINITIONS_STAGING FOR SBREXT.DEFINITIONS_STAGING;


CREATE PUBLIC SYNONYM DEFINITION_TYPES_LOV_EXT FOR SBREXT.DEFINITION_TYPES_LOV_EXT;


CREATE PUBLIC SYNONYM DESIGNATIONS_STAGING FOR SBREXT.DESIGNATIONS_STAGING;


CREATE PUBLIC SYNONYM EATTRIBUTES_STAGING FOR SBREXT.EATTRIBUTES_STAGING;


CREATE PUBLIC SYNONYM ECLASSES_STAGING FOR SBREXT.ECLASSES_STAGING;


CREATE PUBLIC SYNONYM EREFERENCES_STAGING FOR SBREXT.EREFERENCES_STAGING;


CREATE PUBLIC SYNONYM ERRORS_EXT FOR SBREXT.ERRORS_EXT;


CREATE PUBLIC SYNONYM ERROR_LOG FOR SBREXT.ERROR_LOG;


CREATE PUBLIC SYNONYM ESUPERTYPES_STAGING FOR SBREXT.ESUPERTYPES_STAGING;


CREATE PUBLIC SYNONYM GS_COMPOSITE FOR SBREXT.GS_COMPOSITE;


CREATE PUBLIC SYNONYM GS_TABLES_LOV FOR SBREXT.GS_TABLES_LOV;


CREATE PUBLIC SYNONYM GS_TOKENS FOR SBREXT.GS_TOKENS;


CREATE PUBLIC SYNONYM GUEST_LOG FOR SBREXT.GUEST_LOG;


CREATE PUBLIC SYNONYM ICD FOR SBREXT.ICD;


CREATE PUBLIC SYNONYM JAVA$CLASS$MD5$TABLE FOR SBREXT.JAVA$CLASS$MD5$TABLE;


CREATE PUBLIC SYNONYM LOADER_DEFAULTS FOR SBREXT.LOADER_DEFAULTS;


CREATE PUBLIC SYNONYM MATCH_RESULTS_EXT FOR SBREXT.MATCH_RESULTS_EXT;


CREATE PUBLIC SYNONYM MESSAGE_TYPES_EXT FOR SBREXT.MESSAGE_TYPES_EXT;


CREATE PUBLIC SYNONYM MLOG$_AC_ATT_CSCSI_EXT FOR SBREXT.MLOG$_AC_ATT_CSCSI_EXT;


CREATE PUBLIC SYNONYM MLOG$_COMPONENT_CONCEPTS_E FOR SBREXT.MLOG$_COMPONENT_CONCEPTS_E;


CREATE PUBLIC SYNONYM MLOG$_COMPONENT_LEVELS_EXT FOR SBREXT.MLOG$_COMPONENT_LEVELS_EXT;


CREATE PUBLIC SYNONYM MLOG$_CONCEPTS_EXT FOR SBREXT.MLOG$_CONCEPTS_EXT;


CREATE PUBLIC SYNONYM MLOG$_CON_DERIVATION_RULES FOR SBREXT.MLOG$_CON_DERIVATION_RULES;


CREATE PUBLIC SYNONYM MLOG$_OBJECT_CLASSES_EXT FOR SBREXT.MLOG$_OBJECT_CLASSES_EXT;


CREATE PUBLIC SYNONYM MLOG$_OC_RECS_EXT FOR SBREXT.MLOG$_OC_RECS_EXT;


CREATE PUBLIC SYNONYM OBJECT_CLASSES_EXT FOR SBREXT.OBJECT_CLASSES_EXT;


CREATE PUBLIC SYNONYM OBJECT_CLASSES_STAGING FOR SBREXT.OBJECT_CLASSES_STAGING;


CREATE PUBLIC SYNONYM OC_RECS_EXT FOR SBREXT.OC_RECS_EXT;


CREATE PUBLIC SYNONYM PCOLL_CONTROL FOR SBREXT.PCOLL_CONTROL;


CREATE PUBLIC SYNONYM PERMISSIBLE_VALUES_HST FOR SBREXT.PERMISSIBLE_VALUES_HST;


CREATE PUBLIC SYNONYM PERMISSIBLE_VALUES_STAGING FOR SBREXT.PERMISSIBLE_VALUES_STAGING;


CREATE PUBLIC SYNONYM PLAN_TABLE FOR SBREXT.PLAN_TABLE;


CREATE PUBLIC SYNONYM PROPERTIES_EXT FOR SBREXT.PROPERTIES_EXT;


CREATE PUBLIC SYNONYM PROPERTIES_STAGING FOR SBREXT.PROPERTIES_STAGING;


CREATE PUBLIC SYNONYM PROTOCOLS_EXT FOR SBREXT.PROTOCOLS_EXT;


CREATE PUBLIC SYNONYM PROTOCOL_QC_EXT FOR SBREXT.PROTOCOL_QC_EXT;


CREATE PUBLIC SYNONYM PS_TXN FOR SBREXT.PS_TXN;


CREATE PUBLIC SYNONYM PV_STAGING_BKUP FOR SBREXT.PV_STAGING_BKUP;


CREATE PUBLIC SYNONYM QC_DISPLAY_LOV_EXT FOR SBREXT.QC_DISPLAY_LOV_EXT;


CREATE PUBLIC SYNONYM QC_RECS_EXT FOR SBREXT.QC_RECS_EXT;


CREATE PUBLIC SYNONYM QC_RECS_HST FOR SBREXT.QC_RECS_HST;


CREATE PUBLIC SYNONYM QC_TYPE_LOV_EXT FOR SBREXT.QC_TYPE_LOV_EXT;


CREATE PUBLIC SYNONYM QUALIFIER_LOV_EXT FOR SBREXT.QUALIFIER_LOV_EXT;


CREATE PUBLIC SYNONYM QUAL_MAP FOR SBREXT.QUAL_MAP;


CREATE PUBLIC SYNONYM QUAL_STG FOR SBREXT.QUAL_STG;


CREATE PUBLIC SYNONYM QUESTION_CONDITIONS_EXT FOR SBREXT.QUESTION_CONDITIONS_EXT;


CREATE PUBLIC SYNONYM QUEST_ATTRIBUTES_EXT FOR SBREXT.QUEST_ATTRIBUTES_EXT;


CREATE PUBLIC SYNONYM QUEST_CONTENTS_EXT FOR SBREXT.QUEST_CONTENTS_EXT;


CREATE PUBLIC SYNONYM QUEST_CONTENTS_HST FOR SBREXT.QUEST_CONTENTS_HST;


CREATE PUBLIC SYNONYM QUEST_VV_EXT FOR SBREXT.QUEST_VV_EXT;


CREATE PUBLIC SYNONYM REF_DOCS_STAGING FOR SBREXT.REF_DOCS_STAGING;


CREATE PUBLIC SYNONYM REPRESENTATIONS_EXT FOR SBREXT.REPRESENTATIONS_EXT;


CREATE PUBLIC SYNONYM REPRESENTATIONS_STAGING FOR SBREXT.REPRESENTATIONS_STAGING;


CREATE PUBLIC SYNONYM REPRESENTATION_LOV_EXT FOR SBREXT.REPRESENTATION_LOV_EXT;


CREATE PUBLIC SYNONYM REVIEWER_FEEDBACK_LOV_EXT FOR SBREXT.REVIEWER_FEEDBACK_LOV_EXT;


CREATE PUBLIC SYNONYM RULE_FUNCTIONS_EXT FOR SBREXT.RULE_FUNCTIONS_EXT;


CREATE PUBLIC SYNONYM RUPD$_AC_ATT_CSCSI_EXT FOR SBREXT.RUPD$_AC_ATT_CSCSI_EXT;


CREATE PUBLIC SYNONYM RUPD$_COMPONENT_CONCEPTS_E FOR SBREXT.RUPD$_COMPONENT_CONCEPTS_E;


CREATE PUBLIC SYNONYM RUPD$_COMPONENT_LEVELS_EXT FOR SBREXT.RUPD$_COMPONENT_LEVELS_EXT;


CREATE PUBLIC SYNONYM RUPD$_CONCEPTS_EXT FOR SBREXT.RUPD$_CONCEPTS_EXT;


CREATE PUBLIC SYNONYM RUPD$_CON_DERIVATION_RULES FOR SBREXT.RUPD$_CON_DERIVATION_RULES;


CREATE PUBLIC SYNONYM RUPD$_OBJECT_CLASSES_EXT FOR SBREXT.RUPD$_OBJECT_CLASSES_EXT;


CREATE PUBLIC SYNONYM RUPD$_OC_RECS_EXT FOR SBREXT.RUPD$_OC_RECS_EXT;


CREATE PUBLIC SYNONYM SN_ALERT_EXT FOR SBREXT.SN_ALERT_EXT;


CREATE PUBLIC SYNONYM SN_QUERY_EXT FOR SBREXT.SN_QUERY_EXT;


CREATE PUBLIC SYNONYM SN_RECIPIENT_EXT FOR SBREXT.SN_RECIPIENT_EXT;


CREATE PUBLIC SYNONYM SN_REPORT_EXT FOR SBREXT.SN_REPORT_EXT;


CREATE PUBLIC SYNONYM SN_REP_CONTENTS_EXT FOR SBREXT.SN_REP_CONTENTS_EXT;


CREATE PUBLIC SYNONYM SOURCES_EXT FOR SBREXT.SOURCES_EXT;


CREATE PUBLIC SYNONYM SOURCE_DATA_LOADS FOR SBREXT.SOURCE_DATA_LOADS;


CREATE PUBLIC SYNONYM STAGE_LOAD_PDF FOR SBREXT.STAGE_LOAD_PDF;


CREATE PUBLIC SYNONYM SUBSTITUTIONS_EXT FOR SBREXT.SUBSTITUTIONS_EXT;


CREATE PUBLIC SYNONYM TA_PROTO_CSI_EXT FOR SBREXT.TA_PROTO_CSI_EXT;


CREATE PUBLIC SYNONYM TEXT_STRINGS_EXT FOR SBREXT.TEXT_STRINGS_EXT;


CREATE PUBLIC SYNONYM TMP_TAB FOR SBREXT.TMP_TAB;


CREATE PUBLIC SYNONYM TOOL_OPTIONS_EXT FOR SBREXT.TOOL_OPTIONS_EXT;


CREATE PUBLIC SYNONYM TOOL_PROPERTIES_EXT FOR SBREXT.TOOL_PROPERTIES_EXT;


CREATE PUBLIC SYNONYM TRIGGERED_ACTIONS_EXT FOR SBREXT.TRIGGERED_ACTIONS_EXT;


CREATE PUBLIC SYNONYM TS_TYPE_LOV_EXT FOR SBREXT.TS_TYPE_LOV_EXT;


CREATE PUBLIC SYNONYM UI_MENU_TREE_EXT FOR SBREXT.UI_MENU_TREE_EXT;


CREATE PUBLIC SYNONYM UML_LOADER_DEFAULTS FOR SBREXT.UML_LOADER_DEFAULTS;


CREATE PUBLIC SYNONYM UP_ASSOCIATIONS_METADATA_MVW FOR SBREXT.UP_ASSOCIATIONS_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_ATTRIBUTE_METADATA_MVW FOR SBREXT.UP_ATTRIBUTE_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_ATTRIBUTE_METADATA_MVW_TEMP FOR SBREXT.UP_ATTRIBUTE_METADATA_MVW_TEMP;


CREATE PUBLIC SYNONYM UP_ATTRIBUTE_TYPE_METADATA_MVW FOR SBREXT.UP_ATTRIBUTE_TYPE_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_CADSR_PROJECT_MVW FOR SBREXT.UP_CADSR_PROJECT_MVW;


CREATE PUBLIC SYNONYM UP_CLASS_METADATA_MVW FOR SBREXT.UP_CLASS_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_CLASS_METADATA_MVW_TEMP FOR SBREXT.UP_CLASS_METADATA_MVW_TEMP;


CREATE PUBLIC SYNONYM UP_GEN_METADATA_MVW FOR SBREXT.UP_GEN_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_PACKAGES_MVW FOR SBREXT.UP_PACKAGES_MVW;


CREATE PUBLIC SYNONYM UP_PACKAGES_MVW_TEMP FOR SBREXT.UP_PACKAGES_MVW_TEMP;


CREATE PUBLIC SYNONYM UP_SEMANTIC_METADATA_MVW FOR SBREXT.UP_SEMANTIC_METADATA_MVW;


CREATE PUBLIC SYNONYM UP_SUB_PROJECTS_MVW FOR SBREXT.UP_SUB_PROJECTS_MVW;


CREATE PUBLIC SYNONYM UP_TYPE_ENUMERATION_MVW FOR SBREXT.UP_TYPE_ENUMERATION_MVW;


CREATE PUBLIC SYNONYM USERS_LOCKOUT FOR SBREXT.USERS_LOCKOUT;


CREATE PUBLIC SYNONYM VALID_VALUES_ATT_EXT FOR SBREXT.VALID_VALUES_ATT_EXT;


CREATE PUBLIC SYNONYM VALUE_DOMAINS_HST FOR SBREXT.VALUE_DOMAINS_HST;


CREATE PUBLIC SYNONYM VALUE_DOMAINS_STAGING FOR SBREXT.VALUE_DOMAINS_STAGING;


CREATE PUBLIC SYNONYM VD_PVS_HST FOR SBREXT.VD_PVS_HST;


CREATE PUBLIC SYNONYM VD_PVS_SOURCES_EXT FOR SBREXT.VD_PVS_SOURCES_EXT;


CREATE PUBLIC SYNONYM VD_PVS_SOURCES_HST FOR SBREXT.VD_PVS_SOURCES_HST;


CREATE PUBLIC SYNONYM VD_STAGING_BKUP FOR SBREXT.VD_STAGING_BKUP;


CREATE PUBLIC SYNONYM VM_BACKUP FOR SBREXT.VM_BACKUP;


CREATE PUBLIC SYNONYM XML_LOADER_ERRORS FOR SBREXT.XML_LOADER_ERRORS;


CREATE SYNONYM SBR.AC_ATT_CSCSI_EXT FOR SBREXT.AC_ATT_CSCSI_EXT;


CREATE SYNONYM SBR.AC_ATT_TYPES_LOV_EXT FOR SBREXT.AC_ATT_TYPES_LOV_EXT;


CREATE SYNONYM SBR.AC_CHANGE_HISTORY_EXT FOR SBREXT.AC_CHANGE_HISTORY_EXT;


CREATE SYNONYM SBR.AC_CLASS_SCHEMES_STAGING FOR SBREXT.AC_CLASS_SCHEMES_STAGING;


CREATE SYNONYM SBR.AC_SOURCES_EXT FOR SBREXT.AC_SOURCES_EXT;


CREATE SYNONYM SBR.AC_SOURCES_HST FOR SBREXT.AC_SOURCES_HST;


CREATE SYNONYM SBR.AC_SUBMITTERS_STAGING FOR SBREXT.AC_SUBMITTERS_STAGING;


CREATE SYNONYM SBR.ADMINISTERED_COMPONENTS_HST FOR SBREXT.ADMINISTERED_COMPONENTS_HST;


CREATE SYNONYM SBR.ADMIN_COMPONENTS_STAGING FOR SBREXT.ADMIN_COMPONENTS_STAGING;


CREATE SYNONYM SBR.ADMIN_COMPONENTS_STAGING_BKUP FOR SBREXT.ADMIN_COMPONENTS_STAGING_BKUP;


CREATE SYNONYM SBR.ASL_ACTL_EXT FOR SBREXT.ASL_ACTL_EXT;


CREATE SYNONYM SBR.CDE_CART_ITEMS FOR SBREXT.CDE_CART_ITEMS;


CREATE SYNONYM SBR.CLASS_SCHEMES_STAGING FOR SBREXT.CLASS_SCHEMES_STAGING;


CREATE SYNONYM SBR.CLASS_SCHEME_ITEMS_STAGING FOR SBREXT.CLASS_SCHEME_ITEMS_STAGING;


CREATE SYNONYM SBR.COMPONENT_CONCEPTS_EXT FOR SBREXT.COMPONENT_CONCEPTS_EXT;


CREATE SYNONYM SBR.COMPONENT_LEVELS_EXT FOR SBREXT.COMPONENT_LEVELS_EXT;


CREATE SYNONYM SBR.CONCEPTS_EXT FOR SBREXT.CONCEPTS_EXT;


CREATE SYNONYM SBR.CONCEPTS_STAGING FOR SBREXT.CONCEPTS_STAGING;


CREATE SYNONYM SBR.CONCEPTUAL_DOMAINS_STAGING FOR SBREXT.CONCEPTUAL_DOMAINS_STAGING;


CREATE SYNONYM SBR.CONCEPT_SOURCES_LOV_EXT FOR SBREXT.CONCEPT_SOURCES_LOV_EXT;


CREATE SYNONYM SBR.CONDITION_COMPONENTS_EXT FOR SBREXT.CONDITION_COMPONENTS_EXT;


CREATE SYNONYM SBR.CONDITION_MESSAGE_EXT FOR SBREXT.CONDITION_MESSAGE_EXT;


CREATE SYNONYM SBR.CONTACT_ROLES_EXT FOR SBREXT.CONTACT_ROLES_EXT;


CREATE SYNONYM SBR.CON_DERIVATION_RULES_EXT FOR SBREXT.CON_DERIVATION_RULES_EXT;


CREATE SYNONYM SBR.CREATE$JAVA$LOB$TABLE FOR SBREXT.CREATE$JAVA$LOB$TABLE;


CREATE SYNONYM SBR.CRF_TOOL_PARAMETER_EXT FOR SBREXT.CRF_TOOL_PARAMETER_EXT;


CREATE SYNONYM SBR.DATA_ELEMENTS_HST FOR SBREXT.DATA_ELEMENTS_HST;


CREATE SYNONYM SBR.DATA_ELEMENTS_STAGING FOR SBREXT.DATA_ELEMENTS_STAGING;


CREATE SYNONYM SBR.DATA_ELEMENT_CONCEPTS_STAGING FOR SBREXT.DATA_ELEMENT_CONCEPTS_STAGING;


CREATE SYNONYM SBR.DECR_STAGING FOR SBREXT.DECR_STAGING;


CREATE SYNONYM SBR.DEC_RELATIONSHIPS FOR SBREXT.DEC_RELATIONSHIPS;


CREATE SYNONYM SBR.DEC_STAGING FOR SBREXT.DEC_STAGING;


CREATE SYNONYM SBR.DEFINITIONS_STAGING FOR SBREXT.DEFINITIONS_STAGING;


CREATE SYNONYM SBR.DEFINITION_TYPES_LOV_EXT FOR SBREXT.DEFINITION_TYPES_LOV_EXT;


CREATE SYNONYM SBR.DESIGNATIONS_STAGING FOR SBREXT.DESIGNATIONS_STAGING;


CREATE SYNONYM SBR.EATTRIBUTES_STAGING FOR SBREXT.EATTRIBUTES_STAGING;


CREATE SYNONYM SBR.ECLASSES_STAGING FOR SBREXT.ECLASSES_STAGING;


CREATE SYNONYM SBR.EREFERENCES_STAGING FOR SBREXT.EREFERENCES_STAGING;


CREATE SYNONYM SBR.ERRORS_EXT FOR SBREXT.ERRORS_EXT;


CREATE SYNONYM SBR.ERROR_LOG FOR SBREXT.ERROR_LOG;


CREATE SYNONYM SBR.ESUPERTYPES_STAGING FOR SBREXT.ESUPERTYPES_STAGING;


CREATE SYNONYM SBR.GS_COMPOSITE FOR SBREXT.GS_COMPOSITE;


CREATE SYNONYM SBR.GS_TABLES_LOV FOR SBREXT.GS_TABLES_LOV;


CREATE SYNONYM SBR.GS_TOKENS FOR SBREXT.GS_TOKENS;


CREATE SYNONYM SBR.GUEST_LOG FOR SBREXT.GUEST_LOG;


CREATE SYNONYM SBR.ICD FOR SBREXT.ICD;


CREATE SYNONYM SBR.JAVA$CLASS$MD5$TABLE FOR SBREXT.JAVA$CLASS$MD5$TABLE;


CREATE SYNONYM SBR.LOADER_DEFAULTS FOR SBREXT.LOADER_DEFAULTS;


CREATE SYNONYM SBR.MATCH_RESULTS_EXT FOR SBREXT.MATCH_RESULTS_EXT;


CREATE SYNONYM SBR.MESSAGE_TYPES_EXT FOR SBREXT.MESSAGE_TYPES_EXT;


CREATE SYNONYM SBR.MLOG$_AC_ATT_CSCSI_EXT FOR SBREXT.MLOG$_AC_ATT_CSCSI_EXT;


CREATE SYNONYM SBR.MLOG$_COMPONENT_CONCEPTS_E FOR SBREXT.MLOG$_COMPONENT_CONCEPTS_E;


CREATE SYNONYM SBR.MLOG$_COMPONENT_LEVELS_EXT FOR SBREXT.MLOG$_COMPONENT_LEVELS_EXT;


CREATE SYNONYM SBR.MLOG$_CONCEPTS_EXT FOR SBREXT.MLOG$_CONCEPTS_EXT;


CREATE SYNONYM SBR.MLOG$_CON_DERIVATION_RULES FOR SBREXT.MLOG$_CON_DERIVATION_RULES;


CREATE SYNONYM SBR.MLOG$_OBJECT_CLASSES_EXT FOR SBREXT.MLOG$_OBJECT_CLASSES_EXT;


CREATE SYNONYM SBR.MLOG$_OC_RECS_EXT FOR SBREXT.MLOG$_OC_RECS_EXT;


CREATE SYNONYM SBR.OBJECT_CLASSES_EXT FOR SBREXT.OBJECT_CLASSES_EXT;


CREATE SYNONYM SBR.OBJECT_CLASSES_STAGING FOR SBREXT.OBJECT_CLASSES_STAGING;


CREATE SYNONYM SBR.OC_RECS_EXT FOR SBREXT.OC_RECS_EXT;


CREATE SYNONYM SBR.PCOLL_CONTROL FOR SBREXT.PCOLL_CONTROL;


CREATE SYNONYM SBR.PERMISSIBLE_VALUES_HST FOR SBREXT.PERMISSIBLE_VALUES_HST;


CREATE SYNONYM SBR.PERMISSIBLE_VALUES_STAGING FOR SBREXT.PERMISSIBLE_VALUES_STAGING;


CREATE SYNONYM SBR.PLAN_TABLE FOR SBREXT.PLAN_TABLE;


CREATE SYNONYM SBR.PROPERTIES_EXT FOR SBREXT.PROPERTIES_EXT;


CREATE SYNONYM SBR.PROPERTIES_STAGING FOR SBREXT.PROPERTIES_STAGING;


CREATE SYNONYM SBR.PROTOCOLS_EXT FOR SBREXT.PROTOCOLS_EXT;


CREATE SYNONYM SBR.PROTOCOL_QC_EXT FOR SBREXT.PROTOCOL_QC_EXT;


CREATE SYNONYM SBR.PS_TXN FOR SBREXT.PS_TXN;


CREATE SYNONYM SBR.PV_STAGING_BKUP FOR SBREXT.PV_STAGING_BKUP;


CREATE SYNONYM SBR.QC_DISPLAY_LOV_EXT FOR SBREXT.QC_DISPLAY_LOV_EXT;


CREATE SYNONYM SBR.QC_RECS_EXT FOR SBREXT.QC_RECS_EXT;


CREATE SYNONYM SBR.QC_RECS_HST FOR SBREXT.QC_RECS_HST;


CREATE SYNONYM SBR.QC_TYPE_LOV_EXT FOR SBREXT.QC_TYPE_LOV_EXT;


CREATE SYNONYM SBR.QUALIFIER_LOV_EXT FOR SBREXT.QUALIFIER_LOV_EXT;


CREATE SYNONYM SBR.QUAL_MAP FOR SBREXT.QUAL_MAP;


CREATE SYNONYM SBR.QUAL_STG FOR SBREXT.QUAL_STG;


CREATE SYNONYM SBR.QUESTION_CONDITIONS_EXT FOR SBREXT.QUESTION_CONDITIONS_EXT;


CREATE SYNONYM SBR.QUEST_ATTRIBUTES_EXT FOR SBREXT.QUEST_ATTRIBUTES_EXT;


CREATE SYNONYM SBR.QUEST_CONTENTS_EXT FOR SBREXT.QUEST_CONTENTS_EXT;


CREATE SYNONYM SBR.QUEST_CONTENTS_HST FOR SBREXT.QUEST_CONTENTS_HST;


CREATE SYNONYM SBR.QUEST_VV_EXT FOR SBREXT.QUEST_VV_EXT;


CREATE SYNONYM SBR.REF_DOCS_STAGING FOR SBREXT.REF_DOCS_STAGING;


CREATE SYNONYM SBR.REPRESENTATIONS_EXT FOR SBREXT.REPRESENTATIONS_EXT;


CREATE SYNONYM SBR.REPRESENTATIONS_STAGING FOR SBREXT.REPRESENTATIONS_STAGING;


CREATE SYNONYM SBR.REPRESENTATION_LOV_EXT FOR SBREXT.REPRESENTATION_LOV_EXT;


CREATE SYNONYM SBR.REVIEWER_FEEDBACK_LOV_EXT FOR SBREXT.REVIEWER_FEEDBACK_LOV_EXT;


CREATE SYNONYM SBR.RULE_FUNCTIONS_EXT FOR SBREXT.RULE_FUNCTIONS_EXT;


CREATE SYNONYM SBR.RUPD$_AC_ATT_CSCSI_EXT FOR SBREXT.RUPD$_AC_ATT_CSCSI_EXT;


CREATE SYNONYM SBR.RUPD$_COMPONENT_CONCEPTS_E FOR SBREXT.RUPD$_COMPONENT_CONCEPTS_E;


CREATE SYNONYM SBR.RUPD$_COMPONENT_LEVELS_EXT FOR SBREXT.RUPD$_COMPONENT_LEVELS_EXT;


CREATE SYNONYM SBR.RUPD$_CONCEPTS_EXT FOR SBREXT.RUPD$_CONCEPTS_EXT;


CREATE SYNONYM SBR.RUPD$_CON_DERIVATION_RULES FOR SBREXT.RUPD$_CON_DERIVATION_RULES;


CREATE SYNONYM SBR.RUPD$_OBJECT_CLASSES_EXT FOR SBREXT.RUPD$_OBJECT_CLASSES_EXT;


CREATE SYNONYM SBR.RUPD$_OC_RECS_EXT FOR SBREXT.RUPD$_OC_RECS_EXT;


CREATE SYNONYM SBR.SN_ALERT_EXT FOR SBREXT.SN_ALERT_EXT;


CREATE SYNONYM SBR.SN_QUERY_EXT FOR SBREXT.SN_QUERY_EXT;


CREATE SYNONYM SBR.SN_RECIPIENT_EXT FOR SBREXT.SN_RECIPIENT_EXT;


CREATE SYNONYM SBR.SN_REPORT_EXT FOR SBREXT.SN_REPORT_EXT;


CREATE SYNONYM SBR.SN_REP_CONTENTS_EXT FOR SBREXT.SN_REP_CONTENTS_EXT;


CREATE SYNONYM SBR.SOURCES_EXT FOR SBREXT.SOURCES_EXT;


CREATE SYNONYM SBR.SOURCE_DATA_LOADS FOR SBREXT.SOURCE_DATA_LOADS;


CREATE SYNONYM SBR.STAGE_LOAD_PDF FOR SBREXT.STAGE_LOAD_PDF;


CREATE SYNONYM SBR.SUBSTITUTIONS_EXT FOR SBREXT.SUBSTITUTIONS_EXT;


CREATE SYNONYM SBR.TA_PROTO_CSI_EXT FOR SBREXT.TA_PROTO_CSI_EXT;


CREATE SYNONYM SBR.TEXT_STRINGS_EXT FOR SBREXT.TEXT_STRINGS_EXT;


CREATE SYNONYM SBR.TMP_TAB FOR SBREXT.TMP_TAB;


CREATE SYNONYM SBR.TOOL_OPTIONS_EXT FOR SBREXT.TOOL_OPTIONS_EXT;


CREATE SYNONYM SBR.TOOL_PROPERTIES_EXT FOR SBREXT.TOOL_PROPERTIES_EXT;


CREATE SYNONYM SBR.TRIGGERED_ACTIONS_EXT FOR SBREXT.TRIGGERED_ACTIONS_EXT;


CREATE SYNONYM SBR.TS_TYPE_LOV_EXT FOR SBREXT.TS_TYPE_LOV_EXT;


CREATE SYNONYM SBR.UI_MENU_TREE_EXT FOR SBREXT.UI_MENU_TREE_EXT;


CREATE SYNONYM SBR.UML_LOADER_DEFAULTS FOR SBREXT.UML_LOADER_DEFAULTS;


CREATE SYNONYM SBR.UP_ASSOCIATIONS_METADATA_MVW FOR SBREXT.UP_ASSOCIATIONS_METADATA_MVW;


CREATE SYNONYM SBR.UP_ATTRIBUTE_METADATA_MVW FOR SBREXT.UP_ATTRIBUTE_METADATA_MVW;


CREATE SYNONYM SBR.UP_ATTRIBUTE_METADATA_MVW_TEMP FOR SBREXT.UP_ATTRIBUTE_METADATA_MVW_TEMP;


CREATE SYNONYM SBR.UP_ATTRIBUTE_TYPE_METADATA_MVW FOR SBREXT.UP_ATTRIBUTE_TYPE_METADATA_MVW;


CREATE SYNONYM SBR.UP_CADSR_PROJECT_MVW FOR SBREXT.UP_CADSR_PROJECT_MVW;


CREATE SYNONYM SBR.UP_CLASS_METADATA_MVW FOR SBREXT.UP_CLASS_METADATA_MVW;


CREATE SYNONYM SBR.UP_CLASS_METADATA_MVW_TEMP FOR SBREXT.UP_CLASS_METADATA_MVW_TEMP;


CREATE SYNONYM SBR.UP_GEN_METADATA_MVW FOR SBREXT.UP_GEN_METADATA_MVW;


CREATE SYNONYM SBR.UP_PACKAGES_MVW FOR SBREXT.UP_PACKAGES_MVW;


CREATE SYNONYM SBR.UP_PACKAGES_MVW_TEMP FOR SBREXT.UP_PACKAGES_MVW_TEMP;


CREATE SYNONYM SBR.UP_SEMANTIC_METADATA_MVW FOR SBREXT.UP_SEMANTIC_METADATA_MVW;


CREATE SYNONYM SBR.UP_SUB_PROJECTS_MVW FOR SBREXT.UP_SUB_PROJECTS_MVW;


CREATE SYNONYM SBR.UP_TYPE_ENUMERATION_MVW FOR SBREXT.UP_TYPE_ENUMERATION_MVW;


CREATE SYNONYM SBR.USERS_LOCKOUT FOR SBREXT.USERS_LOCKOUT;


CREATE SYNONYM SBR.VALID_VALUES_ATT_EXT FOR SBREXT.VALID_VALUES_ATT_EXT;


CREATE SYNONYM SBR.VALUE_DOMAINS_HST FOR SBREXT.VALUE_DOMAINS_HST;


CREATE SYNONYM SBR.VALUE_DOMAINS_STAGING FOR SBREXT.VALUE_DOMAINS_STAGING;


CREATE SYNONYM SBR.VD_PVS_HST FOR SBREXT.VD_PVS_HST;


CREATE SYNONYM SBR.VD_PVS_SOURCES_EXT FOR SBREXT.VD_PVS_SOURCES_EXT;


CREATE SYNONYM SBR.VD_PVS_SOURCES_HST FOR SBREXT.VD_PVS_SOURCES_HST;


CREATE SYNONYM SBR.VD_STAGING_BKUP FOR SBREXT.VD_STAGING_BKUP;


CREATE SYNONYM SBR.VM_BACKUP FOR SBREXT.VM_BACKUP;


CREATE SYNONYM SBR.XML_LOADER_ERRORS FOR SBREXT.XML_LOADER_ERRORS;


ALTER TABLE SBREXT.COMPONENT_CONCEPTS_EXT ADD (
  CONSTRAINT CCT_PK
 PRIMARY KEY
 (CC_IDSEQ));


ALTER TABLE SBREXT.SN_ALERT_EXT ADD (
  CONSTRAINT SAT_PK
 PRIMARY KEY
 (AL_IDSEQ));


ALTER TABLE SBREXT.OBJECT_CLASSES_STAGING ADD (
  CONSTRAINT OCS_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.UML_LOADER_DEFAULTS ADD (
  CONSTRAINT UML_LDT_PK
 PRIMARY KEY
 (ID));

ALTER TABLE SBREXT.UML_LOADER_DEFAULTS ADD (
  CONSTRAINT PROJ_NAME_VER_UK
 UNIQUE (PROJECT_NAME, PROJECT_VERSION));


ALTER TABLE SBREXT.ESUPERTYPES_STAGING ADD (
  CONSTRAINT ESU_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.CONCEPTS_EXT ADD (
  CONSTRAINT CON_PK
 PRIMARY KEY
 (CON_IDSEQ));

ALTER TABLE SBREXT.CONCEPTS_EXT ADD (
  CONSTRAINT CON_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBREXT.DEC_STAGING ADD (
  CONSTRAINT DCS_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT S_ARC
 CHECK ((S_QC_IDSEQ is not null
and S_QR_IDSEQ is null ) or (S_QC_IDSEQ is null
and S_QR_IDSEQ is not null ) ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT T_ARC
 CHECK ((T_QC_IDSEQ is not null
and T_QR_IDSEQ is null ) or (T_QC_IDSEQ is null
and T_QR_IDSEQ is not null ) ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_PK
 PRIMARY KEY
 (TA_IDSEQ));


ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_PK
 PRIMARY KEY
 (OC_IDSEQ));

ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBREXT.CONCEPTUAL_DOMAINS_STAGING ADD (
  CONSTRAINT CD_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QCE_PK
 PRIMARY KEY
 (QC_IDSEQ));


ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_PK
 PRIMARY KEY
 (PROP_IDSEQ));

ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBREXT.SN_REP_CONTENTS_EXT ADD (
  CONSTRAINT SCT_PK
 PRIMARY KEY
 (CTT_IDSEQ));


ALTER TABLE SBREXT.VALUE_DOMAINS_STAGING ADD (
  PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.VALID_VALUES_ATT_EXT ADD (
  CONSTRAINT VVT_PK
 PRIMARY KEY
 (QC_IDSEQ));


ALTER TABLE SBREXT.USERS_LOCKOUT ADD (
  CONSTRAINT UL_PK
 PRIMARY KEY
 (UA_NAME));


ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_PK
 PRIMARY KEY
 (CCM_IDSEQ));

ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_UK
 UNIQUE (UA_NAME, AC_IDSEQ));


ALTER TABLE SBREXT.QUALIFIER_LOV_EXT ADD (
  CONSTRAINT QLV_PK
 PRIMARY KEY
 (QUALIFIER_NAME));


ALTER TABLE SBREXT.REPRESENTATIONS_STAGING ADD (
  CONSTRAINT REPR_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.AC_CHANGE_HISTORY_EXT ADD (
  CONSTRAINT ACCH_PK
 PRIMARY KEY
 (ACCH_IDSEQ));


ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT AVCON_1013618862_TYPE_000
 CHECK (TYPE IN ('Treatment trials', 'Prevention trials', 'Screening trials', 'Quality of Life trials')));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT AVCON_1019241047_DELET_000
 CHECK (DELETED_IND IN ('Yes', 'No')));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT AVCON_1019241047_LATES_000
 CHECK (LATEST_VERSION_IND IN ('Yes', 'No')));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT PRO_PK
 PRIMARY KEY
 (PROTO_IDSEQ));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT PROTO_UK
 UNIQUE (VERSION, CONTE_IDSEQ, PREFERRED_NAME));


ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_PK
 PRIMARY KEY
 (CMP_IDSEQ));


ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_PK
 PRIMARY KEY
 (TP_IDSEQ));


ALTER TABLE SBREXT.ECLASSES_STAGING ADD (
  CONSTRAINT ECL_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.DEFINITIONS_STAGING ADD (
  CONSTRAINT DEF_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.AC_ATT_TYPES_LOV_EXT ADD (
  CONSTRAINT AAL_PK
 PRIMARY KEY
 (ATL_NAME));


ALTER TABLE SBREXT.TOOL_PROPERTIES_EXT ADD (
  CONSTRAINT TOOL_UK
 UNIQUE (NAME, TOOL));


ALTER TABLE SBREXT.QUEST_VV_EXT ADD (
  CONSTRAINT QVT_PK
 PRIMARY KEY
 (QV_IDSEQ));


ALTER TABLE SBREXT.CLASS_SCHEME_ITEMS_STAGING ADD (
  CONSTRAINT CSI_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.ADMIN_COMPONENTS_STAGING ADD (
  PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.REVIEWER_FEEDBACK_LOV_EXT ADD (
  CONSTRAINT AVCON_1013620752_REVIE_000
 CHECK (REVIEWER_FEEDBACK_TYPE
IN ('INTERNAL COMMENTS', 'EXTERNAL COMMENTS', 'REVIEWER ACTION')) DISABLE);


ALTER TABLE SBREXT.SUBSTITUTIONS_EXT ADD (
  CONSTRAINT AVCON_1008778886_TYPE_001
 CHECK (TYPE IN ('Abbreviation', 'Substitution', 'Accronym', 'Synonym')));

ALTER TABLE SBREXT.SUBSTITUTIONS_EXT ADD (
  CONSTRAINT AVCON_1013618862_TYPE_001
 CHECK (TYPE IN ('Abbreviation', 'Substitution', 'Accronym', 'Synonym')));


ALTER TABLE SBREXT.CONTACT_ROLES_EXT ADD (
  CONSTRAINT CRT_PK
 PRIMARY KEY
 (CONTACT_ROLE));


ALTER TABLE SBREXT.REF_DOCS_STAGING ADD (
  CONSTRAINT RD_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.EATTRIBUTES_STAGING ADD (
  CONSTRAINT EAT_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.CONCEPT_SOURCES_LOV_EXT ADD (
  CONSTRAINT CONST_PK
 PRIMARY KEY
 (CONCEPT_SOURCE));


ALTER TABLE SBREXT.RULE_FUNCTIONS_EXT ADD (
  CONSTRAINT RFT_PK
 PRIMARY KEY
 (RF_IDSEQ));


ALTER TABLE SBREXT.CONDITION_MESSAGE_EXT ADD (
  CONSTRAINT CMT_PK
 PRIMARY KEY
 (CM_IDSEQ));


ALTER TABLE SBREXT.CRF_TOOL_PARAMETER_EXT ADD (
  CONSTRAINT AVCON_1013618862_CRF_S_000
 CHECK (CRF_STATUS_IND IN ('SELECTED', 'WIP', 'DONE')));


ALTER TABLE SBREXT.TOOL_OPTIONS_EXT ADD (
  CONSTRAINT PK_TOOL_OPTIONS
 PRIMARY KEY
 (TOOL_IDSEQ));

ALTER TABLE SBREXT.TOOL_OPTIONS_EXT ADD (
  CONSTRAINT TOOL_OPTIONS_UNIQ
 UNIQUE (TOOL_NAME, PROPERTY));


ALTER TABLE SBREXT.EREFERENCES_STAGING ADD (
  CONSTRAINT ERE_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.DESIGNATIONS_STAGING ADD (
  CONSTRAINT DES_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.ASL_ACTL_EXT ADD (
  CONSTRAINT AAT_PK
 PRIMARY KEY
 (ASL_NAME, ACTL_NAME));


ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_PK
 PRIMARY KEY
 (REC_IDSEQ));


ALTER TABLE SBREXT.CONCEPTS_STAGING ADD (
  PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.DEFINITION_TYPES_LOV_EXT ADD (
  CONSTRAINT DTT_PK
 PRIMARY KEY
 (DEFL_NAME));


ALTER TABLE SBREXT.DATA_ELEMENTS_STAGING ADD (
  CONSTRAINT DE_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.CREATE$JAVA$LOB$TABLE ADD (
  UNIQUE (NAME));


ALTER TABLE SBREXT.GS_TABLES_LOV ADD (
  CONSTRAINT GTV_PK
 PRIMARY KEY
 (AC_TABLE));


ALTER TABLE SBREXT.AC_SUBMITTERS_STAGING ADD (
  CONSTRAINT ASU_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.XML_LOADER_ERRORS ADD (
  CONSTRAINT XLE_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.SN_QUERY_EXT ADD (
  CONSTRAINT SQT_PK
 PRIMARY KEY
 (QUR_IDSEQ));


ALTER TABLE SBREXT.JAVA$CLASS$MD5$TABLE ADD (
  UNIQUE (NAME));


ALTER TABLE SBREXT.CON_DERIVATION_RULES_EXT ADD (
  CONSTRAINT CONDR_PK
 PRIMARY KEY
 (CONDR_IDSEQ));


ALTER TABLE SBREXT.MESSAGE_TYPES_EXT ADD (
  CONSTRAINT MTT_PK
 PRIMARY KEY
 (MT_NAME));


ALTER TABLE SBREXT.CLASS_SCHEMES_STAGING ADD (
  CONSTRAINT CSC_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.REPRESENTATION_LOV_EXT ADD (
  CONSTRAINT RET_PK
 PRIMARY KEY
 (REPRESENTATION_NAME));


ALTER TABLE SBREXT.AC_ATT_CSCSI_EXT ADD (
  CONSTRAINT AAI_PK
 PRIMARY KEY
 (ACA_IDSEQ));


ALTER TABLE SBREXT.SN_REPORT_EXT ADD (
  CONSTRAINT SRT_PK
 PRIMARY KEY
 (REP_IDSEQ));


ALTER TABLE SBREXT.COMPONENT_LEVELS_EXT ADD (
  CONSTRAINT CLT_PK
 PRIMARY KEY
 (CL_IDSEQ));


ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT OCR_CK3
 CHECK (DIRECTION in ('Unspecified','Bi-Directional','Source->Destination',
'Destination-Source') ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_PK
 PRIMARY KEY
 (OCR_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT OCR_UK
 UNIQUE (CONTE_IDSEQ, PREFERRED_NAME, VERSION));


ALTER TABLE SBREXT.PERMISSIBLE_VALUES_STAGING ADD (
  PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.SOURCE_DATA_LOADS ADD (
  CONSTRAINT SDE_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.AC_CLASS_SCHEMES_STAGING ADD (
  CONSTRAINT CS_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_PK
 PRIMARY KEY
 (REP_IDSEQ));

ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_UK
 UNIQUE (PREFERRED_NAME, CONTE_IDSEQ, VERSION));


ALTER TABLE SBREXT.DEC_RELATIONSHIPS ADD (
  CONSTRAINT DER_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.PS_TXN ADD (
  CONSTRAINT PS_TXN_PK
 PRIMARY KEY
 (ID, COLLID));


ALTER TABLE SBREXT.LOADER_DEFAULTS ADD (
  CONSTRAINT LDT_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.DATA_ELEMENT_CONCEPTS_STAGING ADD (
  CONSTRAINT DEC_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.QUESTION_CONDITIONS_EXT ADD (
  CONSTRAINT QCON_PK
 PRIMARY KEY
 (QCON_IDSEQ));


ALTER TABLE SBREXT.PROTOCOL_QC_EXT ADD (
  CONSTRAINT PK_PROTOCOL_QUEST_EXT
 PRIMARY KEY
 (PQ_IDSEQ));


ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT AVCON_1152545259_EDITA_000
 CHECK (EDITABLE_IND IN ('Yes', 'No')));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT AVCON_1152545259_MANDA_000
 CHECK (MANDATORY_IND IN ('Yes', 'No')));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_PK
 PRIMARY KEY
 (QUEST_IDSEQ));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_QC_UK
 UNIQUE (QC_IDSEQ));


ALTER TABLE SBREXT.PROPERTIES_STAGING ADD (
  CONSTRAINT PT_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.DECR_STAGING ADD (
  CONSTRAINT DCR_PK
 PRIMARY KEY
 (ID));


ALTER TABLE SBREXT.COMPONENT_CONCEPTS_EXT ADD (
  CONSTRAINT CCT_CLT_FK 
 FOREIGN KEY (CL_IDSEQ) 
 REFERENCES SBREXT.COMPONENT_LEVELS_EXT (CL_IDSEQ));

ALTER TABLE SBREXT.COMPONENT_CONCEPTS_EXT ADD (
  CONSTRAINT CCT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.COMPONENT_CONCEPTS_EXT ADD (
  CONSTRAINT CCT_CET_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));


ALTER TABLE SBREXT.OBJECT_CLASSES_STAGING ADD (
  CONSTRAINT OCS_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.QC_RECS_EXT ADD (
  CONSTRAINT QRS_RLV_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));


ALTER TABLE SBREXT.ESUPERTYPES_STAGING ADD (
  CONSTRAINT ESU_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));


ALTER TABLE SBREXT.CONCEPTS_EXT ADD (
  CONSTRAINT CON_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.CONCEPTS_EXT ADD (
  CONSTRAINT CON_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));


ALTER TABLE SBREXT.DEC_STAGING ADD (
  CONSTRAINT DCS_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_DE_FK 
 FOREIGN KEY (T_DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QVT_FK 
 FOREIGN KEY (S_QR_IDSEQ) 
 REFERENCES SBREXT.QUEST_VV_EXT (QV_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QC_FK 
 FOREIGN KEY (S_QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QC_FK2 
 FOREIGN KEY (T_QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QVT_FK2 
 FOREIGN KEY (T_QR_IDSEQ) 
 REFERENCES SBREXT.QUEST_VV_EXT (QV_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QCON_FK2 
 FOREIGN KEY (FORCED_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));


ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBREXT.CONCEPTUAL_DOMAINS_STAGING ADD (
  CONSTRAINT CD_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QC_VPV_FK 
 FOREIGN KEY (VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ));

ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QC_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QC_DET_FK 
 FOREIGN KEY (DE_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBREXT.QUEST_CONTENTS_EXT ADD (
  CONSTRAINT QC_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));


ALTER TABLE SBREXT.MATCH_RESULTS_EXT ADD (
  CONSTRAINT MRT_RDT_FK 
 FOREIGN KEY (RD_MATCH_IDSEQ) 
 REFERENCES SBR.REFERENCE_DOCUMENTS (RD_IDSEQ));

ALTER TABLE SBREXT.MATCH_RESULTS_EXT ADD (
  CONSTRAINT MRT_DET_FK 
 FOREIGN KEY (DE_MATCH_IDSEQ) 
 REFERENCES SBR.DATA_ELEMENTS (DE_IDSEQ));

ALTER TABLE SBREXT.MATCH_RESULTS_EXT ADD (
  CONSTRAINT MRT_ASV_FK 
 FOREIGN KEY (ASL_NAME_OF_MATCH) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.MATCH_RESULTS_EXT ADD (
  CONSTRAINT MRT_VDN_FK 
 FOREIGN KEY (VD_MATCH_IDSEQ) 
 REFERENCES SBR.VALUE_DOMAINS (VD_IDSEQ));


ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBREXT.SN_REP_CONTENTS_EXT ADD (
  CONSTRAINT SCT_SRT_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.SN_REPORT_EXT (REP_IDSEQ)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.VALID_VALUES_ATT_EXT ADD (
  CONSTRAINT VVT_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));


ALTER TABLE SBREXT.USERS_LOCKOUT ADD (
  CONSTRAINT UL_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_ACTL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));

ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_AC_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));

ALTER TABLE SBREXT.CDE_CART_ITEMS ADD (
  CONSTRAINT CCM_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));


ALTER TABLE SBREXT.QUALIFIER_LOV_EXT ADD (
  CONSTRAINT QLV_CONDR_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));


ALTER TABLE SBREXT.REPRESENTATIONS_STAGING ADD (
  CONSTRAINT REPR_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT PROTO_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.PROTOCOLS_EXT ADD (
  CONSTRAINT PROTO_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));


ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_RFT_FK 
 FOREIGN KEY (RF_IDSEQ) 
 REFERENCES SBREXT.RULE_FUNCTIONS_EXT (RF_IDSEQ));

ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_QCON_FK 
 FOREIGN KEY (P_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_QCON_FK2 
 FOREIGN KEY (C_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_VVT_FK 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.VALID_VALUES_ATT_EXT (QC_IDSEQ));


ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_AC_CSI_FK 
 FOREIGN KEY (AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ));

ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_TAT_FK 
 FOREIGN KEY (TA_IDSEQ) 
 REFERENCES SBREXT.TRIGGERED_ACTIONS_EXT (TA_IDSEQ));

ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_PROTO_FK 
 FOREIGN KEY (PROTO_IDSEQ) 
 REFERENCES SBREXT.PROTOCOLS_EXT (PROTO_IDSEQ));


ALTER TABLE SBREXT.ECLASSES_STAGING ADD (
  CONSTRAINT ECL_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.QUEST_VV_EXT ADD (
  CONSTRAINT QVT_QVT_FK 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.QUEST_VV_EXT ADD (
  CONSTRAINT QVT_QC_FK 
 FOREIGN KEY (QUEST_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));


ALTER TABLE SBREXT.EATTRIBUTES_STAGING ADD (
  CONSTRAINT EAT_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));


ALTER TABLE SBREXT.RULE_FUNCTIONS_EXT ADD (
  CONSTRAINT RFT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBREXT.CONDITION_MESSAGE_EXT ADD (
  CONSTRAINT CMT_MTT_FK 
 FOREIGN KEY (MT_NAME) 
 REFERENCES SBREXT.MESSAGE_TYPES_EXT (MT_NAME));

ALTER TABLE SBREXT.CONDITION_MESSAGE_EXT ADD (
  CONSTRAINT CMT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));


ALTER TABLE SBREXT.CRF_TOOL_PARAMETER_EXT ADD (
  CONSTRAINT CTP_UAT_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME));


ALTER TABLE SBREXT.TOOL_OPTIONS_EXT ADD (
  CONSTRAINT TL_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.EREFERENCES_STAGING ADD (
  CONSTRAINT ERE_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));


ALTER TABLE SBREXT.ASL_ACTL_EXT ADD (
  CONSTRAINT AAT_ATL_FK 
 FOREIGN KEY (ACTL_NAME) 
 REFERENCES SBR.AC_TYPES_LOV (ACTL_NAME));

ALTER TABLE SBREXT.ASL_ACTL_EXT ADD (
  CONSTRAINT AAT_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));


ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_UA_FK 
 FOREIGN KEY (UA_NAME) 
 REFERENCES SBR.USER_ACCOUNTS (UA_NAME)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_SRT_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.SN_REPORT_EXT (REP_IDSEQ)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.DATA_ELEMENTS_STAGING ADD (
  CONSTRAINT DE_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.VD_PVS_SOURCES_EXT ADD (
  CONSTRAINT VPST_VPV_FK 
 FOREIGN KEY (VP_IDSEQ) 
 REFERENCES SBR.VD_PVS (VP_IDSEQ));


ALTER TABLE SBREXT.SN_QUERY_EXT ADD (
  CONSTRAINT SQT_SAT_FK 
 FOREIGN KEY (AL_IDSEQ) 
 REFERENCES SBREXT.SN_ALERT_EXT (AL_IDSEQ)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.GS_COMPOSITE ADD (
  CONSTRAINT GCE_GTV_FK 
 FOREIGN KEY (AC_TABLE) 
 REFERENCES SBREXT.GS_TABLES_LOV (AC_TABLE));


ALTER TABLE SBREXT.CON_DERIVATION_RULES_EXT ADD (
  CONSTRAINT CONDR_CRV_FK 
 FOREIGN KEY (CRTL_NAME) 
 REFERENCES SBR.COMPLEX_REP_TYPE_LOV (CRTL_NAME));


ALTER TABLE SBREXT.GS_TOKENS ADD (
  CONSTRAINT GTN_GTV_FK 
 FOREIGN KEY (AC_TABLE) 
 REFERENCES SBREXT.GS_TABLES_LOV (AC_TABLE));


ALTER TABLE SBREXT.CLASS_SCHEMES_STAGING ADD (
  CONSTRAINT CSC_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.AC_ATT_CSCSI_EXT ADD (
  CONSTRAINT AAI_CS_CSI_FK 
 FOREIGN KEY (CS_CSI_IDSEQ) 
 REFERENCES SBR.CS_CSI (CS_CSI_IDSEQ));

ALTER TABLE SBREXT.AC_ATT_CSCSI_EXT ADD (
  CONSTRAINT AAI_AAL_FK 
 FOREIGN KEY (ATL_NAME) 
 REFERENCES SBREXT.AC_ATT_TYPES_LOV_EXT (ATL_NAME));


ALTER TABLE SBREXT.SN_REPORT_EXT ADD (
  CONSTRAINT SRT_SAT_FK 
 FOREIGN KEY (AL_IDSEQ) 
 REFERENCES SBREXT.SN_ALERT_EXT (AL_IDSEQ)
    ON DELETE CASCADE);


ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_RL_FK 
 FOREIGN KEY (RL_NAME) 
 REFERENCES SBR.RELATIONSHIPS_LOV (RL_NAME));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_AC_CSI_FK 
 FOREIGN KEY (T_AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_CONTE_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_AC_CSI_FK2 
 FOREIGN KEY (S_AC_CSI_IDSEQ) 
 REFERENCES SBR.AC_CSI (AC_CSI_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_ASL_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_CONDR_FK2 
 FOREIGN KEY (T_CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_CONDR_FK1 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_OCT_FK2 
 FOREIGN KEY (S_OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_CONDR_FK 
 FOREIGN KEY (S_CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_OCT_FK 
 FOREIGN KEY (T_OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ));


ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_ASV_FK 
 FOREIGN KEY (ASL_NAME) 
 REFERENCES SBR.AC_STATUS_LOV (ASL_NAME));

ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_COT_FK 
 FOREIGN KEY (CONTE_IDSEQ) 
 REFERENCES SBR.CONTEXTS (CONTE_IDSEQ));

ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));


ALTER TABLE SBREXT.DEC_RELATIONSHIPS ADD (
  CONSTRAINT DER_DEC_FK 
 FOREIGN KEY (DEC_ID) 
 REFERENCES SBREXT.DATA_ELEMENT_CONCEPTS_STAGING (ID));


ALTER TABLE SBREXT.LOADER_DEFAULTS ADD (
  CONSTRAINT LDT_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.DATA_ELEMENT_CONCEPTS_STAGING ADD (
  CONSTRAINT DEC_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.AC_SOURCES_EXT ADD (
  CONSTRAINT AST_ACT_FK 
 FOREIGN KEY (AC_IDSEQ) 
 REFERENCES SBR.ADMINISTERED_COMPONENTS (AC_IDSEQ));


ALTER TABLE SBREXT.PROTOCOL_QC_EXT ADD (
  CONSTRAINT PQ_PROTO_FK 
 FOREIGN KEY (PROTO_IDSEQ) 
 REFERENCES SBREXT.PROTOCOLS_EXT (PROTO_IDSEQ));

ALTER TABLE SBREXT.PROTOCOL_QC_EXT ADD (
  CONSTRAINT PQ_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));


ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_QC_FK2 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));


ALTER TABLE SBREXT.PROPERTIES_STAGING ADD (
  CONSTRAINT PT_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));


ALTER TABLE SBREXT.DECR_STAGING ADD (
  CONSTRAINT DCR_DCS_FK 
 FOREIGN KEY (DCS_ID) 
 REFERENCES SBREXT.DEC_STAGING (ID));


GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_ATT_CSCSI_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_ATT_TYPES_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_CHANGE_HISTORY_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_CLASS_SCHEMES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_SOURCES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_SOURCES_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.AC_SUBMITTERS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ADMINISTERED_COMPONENTS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ADMIN_COMPONENTS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ADMIN_COMPONENTS_STAGING_BKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ASL_ACTL_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CDE_CART_ITEMS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CLASS_SCHEMES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CLASS_SCHEME_ITEMS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.COMPONENT_CONCEPTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.COMPONENT_LEVELS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONCEPTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONCEPTS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONCEPTUAL_DOMAINS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONCEPT_SOURCES_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONDITION_COMPONENTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONDITION_MESSAGE_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CONTACT_ROLES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CON_DERIVATION_RULES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CREATE$JAVA$LOB$TABLE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.CRF_TOOL_PARAMETER_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DATA_ELEMENTS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DATA_ELEMENTS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DATA_ELEMENT_CONCEPTS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DECR_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DEC_RELATIONSHIPS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DEC_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DEFINITIONS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DEFINITION_TYPES_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.DESIGNATIONS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.EATTRIBUTES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ECLASSES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.EREFERENCES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ERRORS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ERROR_LOG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ESUPERTYPES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.GS_COMPOSITE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.GS_TABLES_LOV TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.GS_TOKENS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.GUEST_LOG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.ICD TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.JAVA$CLASS$MD5$TABLE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.LOADER_DEFAULTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MATCH_RESULTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MESSAGE_TYPES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_AC_ATT_CSCSI_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_COMPONENT_CONCEPTS_E TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_COMPONENT_LEVELS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_CONCEPTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_CON_DERIVATION_RULES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_OBJECT_CLASSES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.MLOG$_OC_RECS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.OBJECT_CLASSES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.OBJECT_CLASSES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.OC_RECS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PCOLL_CONTROL TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PERMISSIBLE_VALUES_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PERMISSIBLE_VALUES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PLAN_TABLE TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PROPERTIES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PROPERTIES_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PROTOCOLS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PROTOCOL_QC_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PS_TXN TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.PV_STAGING_BKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QC_DISPLAY_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QC_RECS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QC_RECS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QC_TYPE_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUALIFIER_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUAL_MAP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUAL_STG TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUESTION_CONDITIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUEST_ATTRIBUTES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUEST_CONTENTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUEST_CONTENTS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.QUEST_VV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.REF_DOCS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.REPRESENTATIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.REPRESENTATIONS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.REPRESENTATION_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.REVIEWER_FEEDBACK_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RULE_FUNCTIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_AC_ATT_CSCSI_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_COMPONENT_CONCEPTS_E TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_COMPONENT_LEVELS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_CONCEPTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_CON_DERIVATION_RULES TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_OBJECT_CLASSES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.RUPD$_OC_RECS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SN_ALERT_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SN_QUERY_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SN_RECIPIENT_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SN_REPORT_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SN_REP_CONTENTS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SOURCES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SOURCE_DATA_LOADS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.STAGE_LOAD_PDF TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.SUBSTITUTIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TA_PROTO_CSI_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TEXT_STRINGS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TMP_TAB TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TOOL_OPTIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TOOL_PROPERTIES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TRIGGERED_ACTIONS_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.TS_TYPE_LOV_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UI_MENU_TREE_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UML_LOADER_DEFAULTS TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_ASSOCIATIONS_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_ATTRIBUTE_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_ATTRIBUTE_METADATA_MVW_TEMP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_ATTRIBUTE_TYPE_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_CADSR_PROJECT_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_CLASS_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_CLASS_METADATA_MVW_TEMP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_GEN_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_PACKAGES_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_PACKAGES_MVW_TEMP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_SEMANTIC_METADATA_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_SUB_PROJECTS_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.UP_TYPE_ENUMERATION_MVW TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.USERS_LOCKOUT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VALID_VALUES_ATT_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VALUE_DOMAINS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VALUE_DOMAINS_STAGING TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VD_PVS_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VD_PVS_SOURCES_EXT TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VD_PVS_SOURCES_HST TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VD_STAGING_BKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.VM_BACKUP TO MATHURA;

GRANT DELETE, INSERT, SELECT, UPDATE ON  SBREXT.XML_LOADER_ERRORS TO MATHURA;



ALTER TABLE SBREXT.REPRESENTATIONS_EXT ADD (
  CONSTRAINT REP_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.SN_REP_CONTENTS_EXT ADD (
  CONSTRAINT SCT_SRT_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.SN_REPORT_EXT (REP_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.QUEST_VV_EXT ADD (
  CONSTRAINT QVT_QVT_FK 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ),
  CONSTRAINT QVT_QC_FK 
 FOREIGN KEY (QUEST_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.PROPERTIES_STAGING ADD (
  CONSTRAINT PT_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.TRIGGERED_ACTIONS_EXT ADD (
  CONSTRAINT TAT_QVT_FK 
 FOREIGN KEY (S_QR_IDSEQ) 
 REFERENCES SBREXT.QUEST_VV_EXT (QV_IDSEQ),
  CONSTRAINT TAT_QC_FK 
 FOREIGN KEY (S_QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ),
  CONSTRAINT TAT_QC_FK2 
 FOREIGN KEY (T_QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ),
  CONSTRAINT TAT_QVT_FK2 
 FOREIGN KEY (T_QR_IDSEQ) 
 REFERENCES SBREXT.QUEST_VV_EXT (QV_IDSEQ),
  CONSTRAINT TAT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ),
  CONSTRAINT TAT_QCON_FK2 
 FOREIGN KEY (FORCED_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.SN_RECIPIENT_EXT ADD (
  CONSTRAINT SPT_SRT_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.SN_REPORT_EXT (REP_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.DATA_ELEMENTS_STAGING ADD (
  CONSTRAINT DE_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.GS_TOKENS ADD (
  CONSTRAINT GTN_GTV_FK 
 FOREIGN KEY (AC_TABLE) 
 REFERENCES SBREXT.GS_TABLES_LOV (AC_TABLE));

ALTER TABLE SBREXT.LOADER_DEFAULTS ADD (
  CONSTRAINT LDT_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.GS_COMPOSITE ADD (
  CONSTRAINT GCE_GTV_FK 
 FOREIGN KEY (AC_TABLE) 
 REFERENCES SBREXT.GS_TABLES_LOV (AC_TABLE));

ALTER TABLE SBREXT.CONDITION_COMPONENTS_EXT ADD (
  CONSTRAINT QCT_RFT_FK 
 FOREIGN KEY (RF_IDSEQ) 
 REFERENCES SBREXT.RULE_FUNCTIONS_EXT (RF_IDSEQ),
  CONSTRAINT QCT_QCON_FK 
 FOREIGN KEY (P_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ),
  CONSTRAINT QCT_QCON_FK2 
 FOREIGN KEY (C_QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ),
  CONSTRAINT QCT_VVT_FK 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.VALID_VALUES_ATT_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.DEC_STAGING ADD (
  CONSTRAINT DCS_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.VALID_VALUES_ATT_EXT ADD (
  CONSTRAINT VVT_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.SN_QUERY_EXT ADD (
  CONSTRAINT SQT_SAT_FK 
 FOREIGN KEY (AL_IDSEQ) 
 REFERENCES SBREXT.SN_ALERT_EXT (AL_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.OC_RECS_EXT ADD (
  CONSTRAINT ORT_CONDR_FK2 
 FOREIGN KEY (T_CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ),
  CONSTRAINT ORT_CONDR_FK1 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ),
  CONSTRAINT ORT_OCT_FK2 
 FOREIGN KEY (S_OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ),
  CONSTRAINT ORT_CONDR_FK 
 FOREIGN KEY (S_CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ),
  CONSTRAINT ORT_OCT_FK 
 FOREIGN KEY (T_OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ));

ALTER TABLE SBREXT.OBJECT_CLASSES_EXT ADD (
  CONSTRAINT OCT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.SN_REPORT_EXT ADD (
  CONSTRAINT SRT_SAT_FK 
 FOREIGN KEY (AL_IDSEQ) 
 REFERENCES SBREXT.SN_ALERT_EXT (AL_IDSEQ)
    ON DELETE CASCADE);

ALTER TABLE SBREXT.PROTOCOL_QC_EXT ADD (
  CONSTRAINT PQ_PROTO_FK 
 FOREIGN KEY (PROTO_IDSEQ) 
 REFERENCES SBREXT.PROTOCOLS_EXT (PROTO_IDSEQ),
  CONSTRAINT PQ_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ));

ALTER TABLE SBREXT.DEC_RELATIONSHIPS ADD (
  CONSTRAINT DER_DEC_FK 
 FOREIGN KEY (DEC_ID) 
 REFERENCES SBREXT.DATA_ELEMENT_CONCEPTS_STAGING (ID));

ALTER TABLE SBREXT.PROPERTIES_EXT ADD (
  CONSTRAINT PROP_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.ESUPERTYPES_STAGING ADD (
  CONSTRAINT ESU_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));

ALTER TABLE SBREXT.EREFERENCES_STAGING ADD (
  CONSTRAINT ERE_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));

ALTER TABLE SBREXT.CLASS_SCHEMES_STAGING ADD (
  CONSTRAINT CSC_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.EATTRIBUTES_STAGING ADD (
  CONSTRAINT EAT_ECL_FK 
 FOREIGN KEY (ECL_ID) 
 REFERENCES SBREXT.ECLASSES_STAGING (ID));

ALTER TABLE SBREXT.ECLASSES_STAGING ADD (
  CONSTRAINT ECL_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.RULE_FUNCTIONS_EXT ADD (
  CONSTRAINT RFT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBREXT.QUEST_ATTRIBUTES_EXT ADD (
  CONSTRAINT QAT_QC_FK2 
 FOREIGN KEY (VV_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ),
  CONSTRAINT QAT_QC_FK 
 FOREIGN KEY (QC_IDSEQ) 
 REFERENCES SBREXT.QUEST_CONTENTS_EXT (QC_IDSEQ),
  CONSTRAINT QAT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.AC_ATT_CSCSI_EXT ADD (
  CONSTRAINT AAI_AAL_FK 
 FOREIGN KEY (ATL_NAME) 
 REFERENCES SBREXT.AC_ATT_TYPES_LOV_EXT (ATL_NAME));

ALTER TABLE SBREXT.CONCEPTUAL_DOMAINS_STAGING ADD (
  CONSTRAINT CD_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.CONDITION_MESSAGE_EXT ADD (
  CONSTRAINT CMT_MTT_FK 
 FOREIGN KEY (MT_NAME) 
 REFERENCES SBREXT.MESSAGE_TYPES_EXT (MT_NAME),
  CONSTRAINT CMT_QCON_FK 
 FOREIGN KEY (QCON_IDSEQ) 
 REFERENCES SBREXT.QUESTION_CONDITIONS_EXT (QCON_IDSEQ));

ALTER TABLE SBREXT.OBJECT_CLASSES_STAGING ADD (
  CONSTRAINT OCS_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.DATA_ELEMENT_CONCEPTS_STAGING ADD (
  CONSTRAINT DEC_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.REPRESENTATIONS_STAGING ADD (
  CONSTRAINT REPR_SDL_FK 
 FOREIGN KEY (SDE_ID) 
 REFERENCES SBREXT.SOURCE_DATA_LOADS (ID));

ALTER TABLE SBREXT.COMPONENT_CONCEPTS_EXT ADD (
  CONSTRAINT CCT_CLT_FK 
 FOREIGN KEY (CL_IDSEQ) 
 REFERENCES SBREXT.COMPONENT_LEVELS_EXT (CL_IDSEQ),
  CONSTRAINT CCT_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ),
  CONSTRAINT CCT_CET_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));

ALTER TABLE SBREXT.TA_PROTO_CSI_EXT ADD (
  CONSTRAINT TPI_TAT_FK 
 FOREIGN KEY (TA_IDSEQ) 
 REFERENCES SBREXT.TRIGGERED_ACTIONS_EXT (TA_IDSEQ),
  CONSTRAINT TPI_PROTO_FK 
 FOREIGN KEY (PROTO_IDSEQ) 
 REFERENCES SBREXT.PROTOCOLS_EXT (PROTO_IDSEQ));

ALTER TABLE SBREXT.DECR_STAGING ADD (
  CONSTRAINT DCR_DCS_FK 
 FOREIGN KEY (DCS_ID) 
 REFERENCES SBREXT.DEC_STAGING (ID));

ALTER TABLE SBREXT.QUALIFIER_LOV_EXT ADD (
  CONSTRAINT QLV_CONDR_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));


ALTER TABLE SBR.AC_CONTACTS ADD (
  CONSTRAINT ACC_CR_FK 
 FOREIGN KEY (CONTACT_ROLE) 
 REFERENCES SBREXT.CONTACT_ROLES_EXT (CONTACT_ROLE));

ALTER TABLE SBR.VALUE_DOMAINS ADD (
  CONSTRAINT VD_REP_FK 
 FOREIGN KEY (REP_IDSEQ) 
 REFERENCES SBREXT.REPRESENTATIONS_EXT (REP_IDSEQ),
  CONSTRAINT VD_QLV_FK 
 FOREIGN KEY (QUALIFIER_NAME) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME),
  CONSTRAINT VD_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.DATA_ELEMENT_CONCEPTS ADD (
  CONSTRAINT DEC_OBJ_QUAL_FK 
 FOREIGN KEY (OBJ_CLASS_QUALIFIER) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME),
  CONSTRAINT DEC_PROP_QUAL_FK 
 FOREIGN KEY (PROPERTY_QUALIFIER) 
 REFERENCES SBREXT.QUALIFIER_LOV_EXT (QUALIFIER_NAME),
  CONSTRAINT DEC_OCT_FK 
 FOREIGN KEY (OC_IDSEQ) 
 REFERENCES SBREXT.OBJECT_CLASSES_EXT (OC_IDSEQ),
  CONSTRAINT DEC_PRO_FK 
 FOREIGN KEY (PROP_IDSEQ) 
 REFERENCES SBREXT.PROPERTIES_EXT (PROP_IDSEQ));

ALTER TABLE SBR.COMPLEX_DE_RELATIONSHIPS ADD (
  CONSTRAINT CDP_RFT_FK 
 FOREIGN KEY (RF_IDSEQ) 
 REFERENCES SBREXT.RULE_FUNCTIONS_EXT (RF_IDSEQ));

ALTER TABLE SBR.CONCEPTUAL_DOMAINS ADD (
  CONSTRAINT CD_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.CS_ITEMS ADD (
  CONSTRAINT CSI_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.CLASSIFICATION_SCHEMES ADD (
  CONSTRAINT CS_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.DEFINITIONS ADD (
  CONSTRAINT DEF_DEFL_FK 
 FOREIGN KEY (DEFL_NAME) 
 REFERENCES SBREXT.DEFINITION_TYPES_LOV_EXT (DEFL_NAME));

ALTER TABLE SBR.VALUE_MEANINGS ADD (
  CONSTRAINT VM_CONDR_FK 
 FOREIGN KEY (CONDR_IDSEQ) 
 REFERENCES SBREXT.CON_DERIVATION_RULES_EXT (CONDR_IDSEQ));

ALTER TABLE SBR.VD_PVS ADD (
  CONSTRAINT VDPVS_CET_FK 
 FOREIGN KEY (CON_IDSEQ) 
 REFERENCES SBREXT.CONCEPTS_EXT (CON_IDSEQ));

