create table CAR (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  power DOUBLE,
  year_of_issue YEAR
);

create table AIRPLANE (
  id IDENTITY primary key,
  brand VARCHAR2(150),
  model VARCHAR2(200),
  manufacturer VARCHAR2(500),
  year_of_issue YEAR,
  fuelCapacity INT,
  seats INT
);

create table CAR_ASSESSMENT (
  assessment_id IDENTITY primary key,
  car_id INT,
  value DEC(20),
  date DATE
);

create table AIRPLANE_ASSESSMENT (
  assessment_id IDENTITY primary key,
  airplane_id INT,
  value DEC(20),
  date DATE
);