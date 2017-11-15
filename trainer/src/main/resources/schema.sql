CREATE TABLE IF NOT EXISTS train_sample (
  id BIGINT PRIMARY KEY, 
  image ARRAY NOT NULL,
  label INT
);

DELETE FROM train_sample;

CREATE TABLE IF NOT EXISTS test_sample (
  id BIGINT PRIMARY KEY, 
  image ARRAY NOT NULL,
  label INT
);

DELETE FROM test_sample;

CREATE TABLE IF NOT EXISTS dev_sample (
  id BIGINT PRIMARY KEY, 
  image ARRAY NOT NULL,
  label INT
);

DELETE FROM dev_sample;