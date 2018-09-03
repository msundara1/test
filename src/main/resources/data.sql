insert into TenantContract(tenantId, clientId, contractId, createdBy) values('1','1',1,'H2');
insert into TenantContract(tenantId, clientId, contractId, createdBy) values('2','2',2,'H2');
insert into TenantContract(tenantId, clientId, contractId, createdBy) values('3','3',3,'H2');

insert into Division(tenantId, name, displayName, description, createdBy) values('1','PBC US','PBC USA','PBC US description','H2');
insert into Division(tenantId, name, displayName, description, createdBy) values('2','PBC MX','PBC Mexica','PBC MX description','H2');
insert into Division(tenantId, name, displayName, description, createdBy) values('3','PBC CA','PBC Canada','PBC CA description','H2');

insert into Section(tenantId, name, displayName, description, createdBy) values('1','Company','Company information','Company informations','H2');
insert into Section(tenantId, name, displayName, description, createdBy) values('2','Operations','Operations data','Operations data','H2');
insert into Section(tenantId, name, displayName, description, createdBy) values('3','Payment','Payment section','Payment section data','H2');


insert into field(name, displayName, description, createdby) values('firstName','First Name','First Name','H2');
insert into field(name, displayName, description, createdby) values('lastName','Last Name','First Name','H2');
insert into field(name, displayName, description, createdby) values('age','Age','Age','H2');
insert into field(name, displayName, description, createdby) values('address','Address','Address','H2');
insert into field(name, displayName, description, createdby) values('phoneNumber','Phone number','Phone number','H2');
insert into field(name, displayName, description, createdby) values('email','Email ID','Email ID','H2');
