create table if not exists RESULT(
  ID varchar(255) not null,
  LEFT_NUMBER varchar(255) not null,
  RIGHT_NUMBER varchar(255) not null,
  OPERATOR varchar(255) not null,
  RESULT varchar(255) not null,
  EXECUTION_DATE timestamp not null,
  PRIMARY KEY ( ID )
);