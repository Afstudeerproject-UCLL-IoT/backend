/* DROP SCHEMA AND CREATE */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

/* CREATE TABLES AND RELATIONS */
CREATE TABLE device (
  "id" SERIAL PRIMARY KEY,
  "type" varchar NOT NULL
);

CREATE TABLE connection_activity (
  "id" SERIAL PRIMARY KEY ,
  "online" timestamp NOT NULL,
  "offline" timestamp,
  "device_id" integer NOT NULL
);

CREATE TABLE metric (
  "id" SERIAL PRIMARY KEY,
  "cpu_usage" numeric NOT NULL,
  "used_memory" numeric NOT NULL,
  "temperature" numeric NOT NULL,
  "device_id" integer NOT NULL
);

CREATE TABLE puzzle (
  "name" varchar PRIMARY KEY,
  "device_owner_id" integer NOT NULL,
  "solution" varchar
);

CREATE TABLE puzzle_attempt (
  "id" SERIAL PRIMARY KEY,
  "start" timestamp NOT NULL,
  "end" timestamp,
  "puzzle_name" varchar NOT NULL,
  "game_session_id" integer NOT NULL
);

CREATE TABLE puzzle_subscriber (
  "subscriber_device_id" integer NOT NULL,
  "subscribed_to_puzzle_name" varchar,
  "game_name" varchar NOT NULL,
  "position" integer NOT NULL
);

CREATE TABLE game (
  "name" varchar PRIMARY KEY
);

CREATE TABLE game_session (
  "id" SERIAL PRIMARY KEY,
  "start" timestamp NOT NULL,
  "end" timestamp,
  "game_name" varchar NOT NULL
);

ALTER TABLE connection_activity ADD FOREIGN KEY ("device_id") REFERENCES device ("id");

ALTER TABLE metric ADD FOREIGN KEY ("device_id") REFERENCES device ("id");

ALTER TABLE puzzle ADD FOREIGN KEY ("device_owner_id") REFERENCES device ("id");

ALTER TABLE puzzle_attempt ADD FOREIGN KEY ("puzzle_name") REFERENCES puzzle ("name");

ALTER TABLE puzzle_attempt ADD FOREIGN KEY ("game_session_id") REFERENCES game_session ("id");

ALTER TABLE puzzle_subscriber ADD FOREIGN KEY ("subscriber_device_id") REFERENCES device ("id");

ALTER TABLE puzzle_subscriber ADD FOREIGN KEY ("subscribed_to_puzzle_name") REFERENCES puzzle ("name");

ALTER TABLE puzzle_subscriber ADD FOREIGN KEY ("game_name") REFERENCES game ("name");

ALTER TABLE game_session ADD FOREIGN KEY ("game_name") REFERENCES game ("name");
