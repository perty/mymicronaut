create table person
(
  id             char(36) primary key not null,
  sex            varchar(10)          not null,
  year_of_birth  smallint,
  height         smallint,
  weight         smallint,
  body           varchar(10)          not null,
  eye_color      varchar(10)          not null,
  orientation    varchar(10)          not null,
  smoking        varchar(10)          not null,
  drinking       varchar(10)          not null,
  martial_status varchar(10)          not null,
  description    varchar(2000)        not null
);

create table profile
(
  id          char(36) primary key            not null,
  user_name   varchar(30) unique              not null,
  person1     char(36) references person (id) not null,
  person2     char(36) references person (id),
  description varchar(2000)                   not null
);

create table member
(
  id       char(36) primary key             not null,
  email    varchar(254) unique              not null,
  password varchar(254)                     not null,
  profile  char(36) references profile (id) not null
);

create table ad
(
  id      char(36) primary key not null,
  ad_text varchar(280)         not null,
  profile char(36) references profile (id)
);

create table session
(
  id        char(36) primary key not null,
  user_name varchar(30)          not null,
  created   timestamp            not null,
  verified  timestamp            not null
)
