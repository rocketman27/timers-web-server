-- Seed Timers (replacing templates)
INSERT INTO timers (id, name, description, cron_expression, zone_id, trigger_time, suspended)
VALUES
  ('tm-eur-morning', 'European Morning Batch', 'European demo timer', NULL, 'Europe/London', '09:00', FALSE),
  ('tm-eur-noon', 'European Noon Batch', 'European demo timer', NULL, 'Europe/Berlin', '12:00', FALSE),
  ('tm-apac-morning', 'APAC Morning Batch', 'APAC demo timer', NULL, 'Asia/Tokyo', '09:00', FALSE);

-- Countries
INSERT INTO timer_countries (timer_id, country) VALUES
  ('tm-eur-morning', 'GB'),
  ('tm-eur-morning', 'DE'),
  ('tm-eur-morning', 'FR'),
  ('tm-eur-noon', 'IT'),
  ('tm-eur-noon', 'ES'),
  ('tm-eur-noon', 'CZ'),
  ('tm-eur-noon', 'PL'),
  ('tm-apac-morning', 'JP'),
  ('tm-apac-morning', 'CN'),
  ('tm-apac-morning', 'HK'),
  ('tm-apac-morning', 'SG'),
  ('tm-apac-morning', 'KR'),
  ('tm-apac-morning', 'AU');

-- Regions
INSERT INTO timer_regions (timer_id, region) VALUES
  ('tm-eur-morning', 'EMEA'),
  ('tm-eur-noon', 'EMEA'),
  ('tm-apac-morning', 'APAC');

-- Flow types
INSERT INTO timer_flow_types (timer_id, flow_type) VALUES
  ('tm-eur-morning', 'FX'),
  ('tm-eur-morning', 'SWAP'),
  ('tm-eur-morning', 'CASH'),
  ('tm-eur-noon', 'FX'),
  ('tm-eur-noon', 'SWAP'),
  ('tm-apac-morning', 'FX'),
  ('tm-apac-morning', 'SWAP'),
  ('tm-apac-morning', 'CASH');

-- Client IDs
INSERT INTO timer_client_ids (timer_id, client_id) VALUES
  ('tm-eur-morning', 'CLIENT_GB001'),
  ('tm-eur-morning', 'CLIENT_DE001'),
  ('tm-eur-morning', 'CLIENT_FR001'),
  ('tm-eur-noon', 'CLIENT_IT001'),
  ('tm-eur-noon', 'CLIENT_ES001'),
  ('tm-eur-noon', 'CLIENT_CZ001'),
  ('tm-eur-noon', 'CLIENT_PL001'),
  ('tm-apac-morning', 'CLIENT_JP001'),
  ('tm-apac-morning', 'CLIENT_CN001'),
  ('tm-apac-morning', 'CLIENT_HK001');

-- Product types
INSERT INTO timer_product_types (timer_id, product_type) VALUES
  ('tm-eur-morning', 'CASH'),
  ('tm-eur-noon', 'SWAP'),
  ('tm-apac-morning', 'CASH'),
  ('tm-apac-morning', 'SWAP');
