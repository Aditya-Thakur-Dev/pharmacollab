create table application_users(
user_id varchar2(50),
user_name varchar2(50),
password varchar2(50),
user_role varchar2(20),
department_id varchar2(50),
created timestamp(9),
updated timestamp(9),
constraint application_users_pk primary key (user_id)
);

create table topics(
topic_id varchar2(50),
title varchar2(500),
short_description varchar2(500),
disease_id varchar2(50),
department_id varchar2(50),
status varchar2(5),
created timestamp(9),
updated timestamp(9),
constraint topics_pk primary key (topic_id)
);

create table topic_allocations(
topic_id varchar2(50),
user_id varchar2(50),
topic_role varchar2(50),
created timestamp(9),
updated timestamp(9),
constraint topic_allocations_pk primary key (topic_id, user_id),
constraint topic_allocations_user_fk foreign key (user_id) references application_users(user_id),
constraint topic_allocations_topic_fk foreign key (topic_id) references topics(topic_id)
);

create table journals(
journal_id varchar2(50),
topic_id varchar2(50),
user_id varchar2(50),
journal_name varchar2(150),
type varchar2(50),
created timestamp(9),
updated timestamp(9),
constraint journals_pk primary key (journal_id),
constraint journals_user_fk foreign key (user_id) references application_users(user_id),
constraint journals_topic_fk foreign key (topic_id) references topics(topic_id)
);

create table exploratory_discovery(
exploratory_discovery_id varchar2(50),
journal_id varchar2(50),
disease_operation_file varchar(500),
target_file varchar2(500),
hypothesis_file varchar2(500),
status varchar2(5),
created timestamp(9),
updated timestamp(9),
constraint exploratory_discovery_pk primary key (exploratory_discovery_id),
constraint exploratory_discovery_fk foreign key (journal_id) references journals(journal_id)
);

create table later_stage_discovery(
later_stage_discovery_id varchar2(50),
journal_id varchar2(50),
compounds_file varchar2(50),
fit_for_target varchar2(1),
status varchar2(5),
created timestamp(9),
updated timestamp(9),
constraint later_stage_discovery_pk primary key (later_stage_discovery_id),
constraint later_stage_discovery_fk foreign key (journal_id) references journals(journal_id)
);

create table preclinical_trials(
trial_id varchar2(50),
journal_id varchar2(50),
sample_id varchar2(50),
acceptance_criteria_file varchar2(500),
trial_method varchar2(50),
result varchar2(50),
status varchar2(5),
created timestamp(9),
updated timestamp(9),
constraint preclinical_trials_pk primary key (trial_id),
constraint preclinical_trials_fk foreign key (journal_id) references journals(journal_id)
);

create table disease_details(
disease_id varchar2(50),
disease_name varchar2(500),
description varchar2(500),
disease_operation_file varchar2(500),
targets_file varchar2(500),
hypothesis_file varchar2(500),
created timestamp(9),
updated timestamp(9),
constraint disease_details_pk primary key (disease_id)
);

create table compounds(
compound_id varchar2(50),
compound_name varchar2(500),
disease_ids varchar2(1000),
description_file varchar2(500),
created timestamp(9),
updated timestamp(9),
constraint compounds_pk primary key (compound_id)
);

create sequence user_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence topic_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence journal_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence exploratory_discovery_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence later_stage_discovery_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence trial_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence disease_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

create sequence compound_id_seq minvalue 1 maxvalue 999999999999
start with 1 increment by 1;

commit;

--**************************************rollback scripts*****************************
--drop table compounds;
--drop table disease_details;
--drop table preclinical_trials;
--drop table later_stage_discovery;
--drop table exploratory_discovery;
--drop table journals;
--drop table topic_allocations;
--drop table topics;
--drop table application_users;
--commit;
--************************************************************************************