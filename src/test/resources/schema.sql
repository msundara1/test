
/*
   Table for storing credit2B contractId of a client
 */
CREATE TABLE IF NOT EXISTS TenantContract
(
   tenantContractKey UUID DEFAULT random_uuid() PRIMARY KEY,
   tenantId varchar(255) NOT NULL,
   tenantName varchar(255) NOT NULL,
   clientId long NOT NULL,
   contractId bigint DEFAULT 0,
   createdBy varchar(255) NOT NULL,
   createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   lastModifiedBy varchar(255),
   lastModifiedDate TIMESTAMP,
--    primary key(tenantContractKey)
);

/*
   Table for storing details division as per client
 */
CREATE TABLE IF NOT EXISTS Division
(
   divisionkey UUID DEFAULT random_uuid(),
   tenantId varchar(255) NOT NULL,
   parentDivisionKey UUID,
   name varchar(255) NOT NULL,
   displayName varchar(255) NOT NULL,
   description varchar(255) NOT NULL,
   isActive boolean DEFAULT TRUE,
   createdBy varchar(255) NOT NULL,
   createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   lastModifiedBy varchar(255),
   lastModifiedDate TIMESTAMP,
   primary key(divisionkey)
);

/*
   Table for storing details sections to group fields into sections / tabs as per client
 */
-- CREATE TABLE IF NOT EXISTS Section
-- (
--    sectionKey UUID DEFAULT random_uuid(),
--    tenantId varchar(255) NOT NULL,
--    sectionId bigint AUTO_INCREMENT,
--    parentSectionId bigint,
--    name varchar(255) NOT NULL,
--    displayName varchar(255) NOT NULL,
--    description varchar(255) NOT NULL,
--    isActive boolean DEFAULT TRUE,
--    isDeleted boolean DEFAULT FALSE,
--    createdBy varchar(255) NOT NULL,
--    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    lastModifiedBy varchar(255),
--    lastModifiedDate TIMESTAMP,
--    primary key(sectionKey)
-- );
