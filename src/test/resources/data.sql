insert into TenantContract(tenantId, tenantName, clientId, contractId, createdBy) values('1','Test tenant1','1',1,'H2');
insert into TenantContract(tenantId, tenantName, clientId, contractId, createdBy) values('1','Test tenant1','2',2,'H2');
insert into TenantContract(tenantId, tenantName, clientId, contractId, createdBy) values('1','Test tenant1','3',3,'H2');

insert into Division(tenantId, name, displayName, description, createdBy) values('1','PBC US','PBC USA','PBC US description','H2');
insert into Division(tenantId, name, displayName, description, createdBy) values('1','PBC MX','PBC Mexica','PBC MX description','H2');
insert into Division(tenantId, name, displayName, description, createdBy) values('1','PBC CA','PBC Canada','PBC CA description','H2');

-- insert into Section(tenantId, name, displayName, description, createdBy) values('1','Company','Company information','Company informations','H2');
-- insert into Section(tenantId, name, displayName, description, createdBy) values('1','Operations','Operations data','Operations data','H2');
-- insert into Section(tenantId, name, displayName, description, createdBy) values('1','Payment','Payment section','Payment section data','H2');
