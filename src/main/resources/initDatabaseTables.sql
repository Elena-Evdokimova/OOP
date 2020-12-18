create table customer(
	customer_name varchar(100) not null primary key,
	bank_requisites varchar(100) not null,
	c_phone_number varchar(18) not null,
	contact_person varchar(100)
);

create table broadcast(
	broadcast_name varchar(100) not null primary key,
	broadcast_priority integer not null,
	minute_cost numeric not null
);

create table advertising(
	advertising_id serial primary key,
	r_broadcast_name varchar(100) references broadcast(broadcast_name),
	r_customer_name varchar(100) references customer(customer_name),
	advertising_date date,
	advertising_duration integer
);

create table agent(
	agent_id serial primary key,
	agent_payment numeric not null
);

create table agent_adv_connection(
	agent_to_adv integer references agent(agent_id),
	avd_to_agent integer references advertising(advertising_id)
);

insert into broadcast(broadcast_name, broadcast_priority, minute_cost) values ('The Truman Show', 30, 500.99);

insert into customer(customer_name, bank_requisites, c_phone_number, contact_person) values ('test customer', 'XXXX-XXXX-XXXX-XXXX', '+7-XXX-XXX-XX-XX', 'test person');

insert into advertising(r_broadcast_name, r_customer_name, advertising_date, advertising_duration) values ('The Truman Show', 'test customer', '2020-10-20', 2);

insert into agent(agent_payment) values (123.12), (99.99);

insert into agent_adv_connection(agent_to_adv, adv_to_agent) values (2, 1);

select * from agent_adv_connection;

delete from agent_adv_connection;

select * from advertising;

select * from agent;

select agent_payment, r_broadcast_name, r_customer_name, advertising_date, advertising_duration
from agent inner join agent_adv_connection on agent.agent_id = agent_adv_connection.agent_to_adv
inner join advertising on agent_adv_connection.adv_to_agent = advertising.advertising_id;