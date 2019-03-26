USE vmw01_service_subscription;

CREATE TABLE IF NOT EXISTS user (
	id int(11) auto_increment NOT NULL,
	name VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS service (
	id int(11) auto_increment NOT NULL,
	name VARCHAR(255),
	url VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  subscription (
	  id int(11) auto_increment not null,
	  userid int(11) not null,
	  serviceid int(11) not null,
	  name varchar(255),
	  primary key (id),
	  foreign key (userid) references user(id),
	  foreign key (serviceid) references service(id)
);