# --- First database schema

# --- !Ups

create table notice (
  id bigint not null auto_increment,
  title varchar(255) not null,
  content varchar(255) not null,
  createdDate datetime,
  modifiedDate datetime,
  constraint pk_notice primary key (id))
;


# --- !Downs


drop table if exists notice;

