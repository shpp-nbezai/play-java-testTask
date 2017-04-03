# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cache_data (
  access_token                  serial not null,
  response_body                 varchar(255),
  constraint pk_cache_data primary key (access_token)
);


# --- !Downs

drop table if exists cache_data cascade;

