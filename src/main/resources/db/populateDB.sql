DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2015-06-01 14:00', 'Admin dinner', 510, 100001),
  ('2015-06-01 21:00', 'Admin supper', 1500, 100001),
  ('2015-06-02 07:00', 'User breakfast', 200, 100000),
  ('2015-06-02 14:00', 'User dinner', 1500, 100000),
  ('2015-06-02 21:00', 'User supper', 300, 100000),
  ('2015-06-03 21:00', 'User supper', 2500, 100000);