# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table service (
  code                      varchar(255) not null,
  description               varchar(255),
  amount                    double,
  constraint pk_service primary key (code))
;

create sequence service_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists service;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists service_seq;

