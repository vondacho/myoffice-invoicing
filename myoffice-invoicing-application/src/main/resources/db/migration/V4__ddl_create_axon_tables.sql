create table t_domain_event_entry (
  global_index         bigint       not null AUTO_INCREMENT,
  event_identifier     varchar(255) not null,
  meta_data            blob(10000),
  payload              blob(10000)  not null,
  payload_revision     varchar(255),
  payload_type         varchar(255) not null,
  time_stamp           varchar(255) not null,
  aggregate_identifier varchar(255) not null,
  sequence_number      bigint       not null,
  type                 varchar(255),
  primary key (global_index)
);

create table t_snapshot_event_entry (
  aggregate_identifier varchar(255) not null,
  sequence_number      bigint       not null,
  type                 varchar(255) not null,
  event_identifier     varchar(255) not null,
  meta_data            blob(10000),
  payload              blob(10000)  not null,
  payload_revision     varchar(255),
  payload_type         varchar(255) not null,
  time_stamp           varchar(255) not null,
  primary key (aggregate_identifier, sequence_number, type)
);

create table t_association_value_entry (
  id                bigint       not null AUTO_INCREMENT,
  association_key   varchar(255) not null,
  association_value varchar(255),
  saga_id           varchar(255) not null,
  saga_type         varchar(255),
  primary key (id)
);

create table t_saga_entry (
  saga_id         varchar(255) not null,
  revision        varchar(255),
  saga_type       varchar(255),
  serialized_saga blob(10000),
  primary key (saga_id)
);

create index idx_association_value_entry_1
  on t_association_value_entry (saga_type, association_key, association_value);

create index idx_association_value_entry_2
  on t_association_value_entry (saga_id, saga_type);

alter table t_domain_event_entry
  add constraint uk_domain_event_entry_1 unique (aggregate_identifier, sequence_number);

alter table t_domain_event_entry
  add constraint uk_domain_event_entry_2 unique (event_identifier);

alter table t_snapshot_event_entry
  add constraint uk_snapshot_event_entry unique (event_identifier);





