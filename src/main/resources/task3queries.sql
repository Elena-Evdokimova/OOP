select agent_payment, r_broadcast_name, r_customer_name, advertising_date, advertising_duration
from agent inner join agent_adv_connection on agent.agent_id = agent_adv_connection.agent_to_adv
inner join advertising on agent_adv_connection.adv_to_agent = advertising.advertising_id;


select * from broadcast;

insert into broadcast(broadcast_name, broadcast_priority, minute_cost) values ('Hot news', 50, 750.99);

delete from broadcast where broadcast_name = 'Hot news';

select * from broadcast where broadcast_name = 'The Truman Show';


select * from customer;

insert into customer(customer_name, bank_requisites, c_phone_number, contact_person) values ('some', 'some requisites', 'some phone number', 'some person');

delete from customer where customer_name = 'some';

select * from customer where customer_name = 'test customer';

insert into advertising(r_broadcast_name, r_customer_name, advertising_date, advertising_duration) values ('Hot news', 'some', '2010-10-10', 3);

select * from advertising where r_broadcast_name = 'Hot news';

select * from advertising where r_customer_name = 'test customer';


select * from advertising;

select * from agent;

select * from agent_adv_connection;

insert into agent(agent_payment) values (300);

select * from agent;

delete from agent where agent_payment = 300;

--insert and connect agent to adv

insert into agent(agent_payment) values (300) returning agent_id;

select advertising_id from advertising where advertising_date = '2010-10-10';


--create agent and immediately connect him to adv with some date
with id_table as(
	insert into agent(agent_payment) values (300) returning agent_id
)
insert into agent_adv_connection select agent_id, advertising_id from id_table, advertising where advertising_date = '2010-10-10';

select * from agent;
insert into agent(agent_id, agent_payment) values (9, 100);
insert into agent(agent_payment) values (200);
select agent_id, advertising_id from id_table, advertising where advertising_date = '2010-10-10';

select * from agent_adv_connection;

select * from advertising;

--get all adv info by agent_id
select distinct advertising_id, r_broadcast_name, r_customer_name, advertising_date, advertising_duration from agent inner join agent_adv_connection on 7 = agent_adv_connection.agent_to_adv
inner join advertising on agent_adv_connection.adv_to_agent = advertising.advertising_id;

select agent_id, agent_payment from agent inner join agent_adv_connection on agent_id = agent_to_adv inner join advertising on adv_to_agent = advertising_id where advertising_date = '2010-10-10';
