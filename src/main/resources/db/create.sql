SET MODE PostgreSQL;

CREATE DATABASE jadle;
\c jadle ;
CREATE TABLE IF NOT EXISTS restaurants (
  id SERIAL PRIMARY KEY,
 name VARCHAR,
 address VARCHAR,
 zipcode VARCHAR,
 phone VARCHAR,
 website VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS foodtypes (
  id SERIAL PRIMARY KEY,
 name VARCHAR,
  foodtypeid INTEGER,
  restaurantid INTEGER
);

CREATE TABLE IF NOT EXISTS reviews (
  id SERIAL PRIMARY KEY,
  writtenby VARCHAR,
  rating VARCHAR,
  content VARCHAR,
  restaurantid INTEGER,
  createdat BIGINT
);
CREATE TABLE IF NOT EXISTS restaurants_foodtypes (
  id SERIAL PRIMARY KEY,
 foodtypeid INTEGER,
 restaurantid INTEGER
);