CREATE TABLE PREPARATION_TASK(
  ID BIGSERIAL,
  QUEUE_TIME TIMESTAMP NOT NULL,
  START_TIME TIMESTAMP,
  STATUS VARCHAR(30) NOT NULL ,
  ORDER_LINE_ID BIGINT NOT NULL ,
  USER_ID BIGINT ,
  CONSTRAINT PK_PREP_TASK PRIMARY KEY (ID),
  CONSTRAINT FK_TASK_ORDER_LINE FOREIGN KEY (ORDER_LINE_ID) REFERENCES PRODUCT_ORDER_LINE(ID)
);