create table region
(
	username varchar not null primary key,
	pass varchar not null
);

create table pharmacies
(
	id SERIAL primary key,
	name varchar not null unique,
	address varchar,
	tel varchar(10),
	unique(name, address, tel)
);

create table personnel
(
	cf char(16) primary key not null,
	idpharm integer,
	role char(2) not null,
	username varchar not null unique,
	pass varchar not null,
	name varchar not null,
	surname varchar not null,
	bdate date
);

create table warehouse
(
	idpharm integer,
	codprod varchar,
	availqty integer,
	primary key (idpharm, codprod)
);

create table products
(
	codprod varchar not null primary key,
	descr varchar not null,
	name varchar not null unique,
	price real not null,
	needspres boolean not null
);

create table purchases
(
	codpurch SERIAL primary key not null,
	cfpers char(16) not null,
	total real,
	datep timestamp,
	completed boolean not null
);

create table cart
(
	id SERIAL primary key,
	codprod varchar not null,
	qty integer not null,
	codpurch integer not null
);

create table patiets
(
	cf char(16) primary key not null,
	codpurch integer not null,
	name varchar not null,
	surname varchar not null,
	bdate date
);

create table prescriptions
(
	codpres varchar not null primary key,
	idcart integer not null,
	codReg varchar not null,
	datepr date
);

create table messages
(
	id SERIAL not null primary key,
	datesent timestamp not null,
	fromReg varchar,
	toReg varchar,
	fromOp varchar,
	toOp varchar,
	obj varchar not null,
	msg varchar not null
);

create table doctors
(
	codreg varchar not null primary key,
	name varchar not null,
	surname varchar not null,
	address varchar
);

alter table personnel
add foreign key (idpharm) references pharmacies(id);

alter table purchases
add foreign key (cfpers) references personnel(cf);

alter table cart
add foreign key (codpurch) references purchases(codpurch);

alter table patients
add foreign key (codpurch) references purchases(codpurch);

alter table warehouse
add foreign key (idpharm) references pharmacies(id),
add foreign key (codprod) references products (codprod);

alter table prescriptions
add foreign key (idcart) references cart(id),
add foreign key (codreg) references doctors (codreg);

alter table messages
add foreign key (fromReg) references region (username),
add foreign key (toReg) references region (username),
add foreign key (fromOp) references personnel (username),
add foreign key (toOp) references personnel (username);
