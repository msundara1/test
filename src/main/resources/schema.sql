/*
   Table for storing credit2B contractId of a client
 */
CREATE TABLE IF NOT EXISTS TenantContract
(
  tenantContractKey UUID DEFAULT random_uuid(),
  tenantId         VARCHAR(255) NOT NULL,
  clientId         LONG         NOT NULL,
  contractId       BIGINT    DEFAULT 0,
  createdBy        VARCHAR(255) NOT NULL,
  createdDate      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lastModifiedBy   VARCHAR(255),
  lastModifiedDate TIMESTAMP,
  PRIMARY KEY (tenantContractKey)
);

/*
   Table for storing details division as per client
 */
CREATE TABLE IF NOT EXISTS Division
(
  divisionkey UUID DEFAULT random_uuid(),
  tenantId         VARCHAR(255) NOT NULL,
  parentDivisionKey UUID,
  name             VARCHAR(255) NOT NULL,
  displayName      VARCHAR(255) NOT NULL,
  description      VARCHAR(255) NOT NULL,
  isActive         BOOLEAN   DEFAULT TRUE,
  createdBy        VARCHAR(255) NOT NULL,
  createdDate      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lastModifiedBy   VARCHAR(255),
  lastModifiedDate TIMESTAMP,
  PRIMARY KEY (divisionkey)
);

/*
   Table for storing details sections to group fields into sections / tabs as per client
 */
CREATE TABLE IF NOT EXISTS Section
(
  sectionKey UUID DEFAULT random_uuid(),
  tenantId         VARCHAR(255) NOT NULL,
  parentSectionKey UUID,
  name             VARCHAR(255) NOT NULL,
  displayName      VARCHAR(255) NOT NULL,
  description      VARCHAR(255) NOT NULL,
  isActive         BOOLEAN   DEFAULT TRUE,
  createdBy        VARCHAR(255) NOT NULL,
  createdDate      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lastModifiedBy   VARCHAR(255),
  lastModifiedDate TIMESTAMP,
  PRIMARY KEY (sectionKey)
);

/*
   Table for storing Form Fields per client
 */
CREATE TABLE IF NOT EXISTS field
(
  fieldkey UUID DEFAULT random_uuid(),
  parentfieldkey  UUID,
  name            VARCHAR(255) NOT NULL,
  displayname     VARCHAR(255) NOT NULL,
  description     VARCHAR(255) NOT NULL,
  position        VARCHAR(255),
  type            VARCHAR(255), --radio, checkbox
  value           VARCHAR(255),
  defaultvalue    VARCHAR(255),
  format          VARCHAR(255), -- mm/dd/yy..
  rules           VARCHAR(255),
  documents       VARCHAR(255),
  isoptional      BOOLEAN  DEFAULT TRUE,
  isvisible       BOOLEAN  DEFAULT TRUE,
  isactive        BOOLEAN  DEFAULT TRUE,
  createdby       VARCHAR(255) NOT NULL,
  createdon       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lastmodifiedby  VARCHAR(255),
  lastmodifiedon  TIMESTAMP,
  PRIMARY KEY (fieldkey)
);


--   tenantid        VARCHAR(255) NOT NULL,
--   divisionid      VARCHAR(255) NOT NULL,
