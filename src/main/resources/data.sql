-- Timer Templates
INSERT INTO timer_templates (id, name, description, cron_expression, zone_id, trigger_time, suspended)
VALUES
  ('tpl-us-demo', 'European Morning Batch', 'European demo template', NULL, 'Europe/London', '09:00', FALSE),
  ('tpl-eu-demo', 'European Noon Batch', 'European demo template', NULL, 'Europe/Berlin', '12:00', FALSE),
  ('tpl-apac-demo', 'APAC Morning Batch', 'APAC demo template', NULL, 'Asia/Tokyo', '09:00', FALSE);

-- Countries
INSERT INTO timer_template_countries (template_id, country) VALUES
  ('tpl-us-demo', 'GB'),
  ('tpl-us-demo', 'DE'),
  ('tpl-us-demo', 'FR'),
  ('tpl-eu-demo', 'IT'),
  ('tpl-eu-demo', 'ES'),
  ('tpl-eu-demo', 'CZ'),
  ('tpl-eu-demo', 'PL'),
  ('tpl-apac-demo', 'JP'),
  ('tpl-apac-demo', 'CN'),
  ('tpl-apac-demo', 'HK'),
  ('tpl-apac-demo', 'SG'),
  ('tpl-apac-demo', 'KR'),
  ('tpl-apac-demo', 'AU');

-- Regions (simplified to 3 main regions)
INSERT INTO timer_template_regions (template_id, region) VALUES
  ('tpl-us-demo', 'EMEA'),
  ('tpl-eu-demo', 'EMEA'),
  ('tpl-apac-demo', 'APAC');

-- Flow types
INSERT INTO timer_template_flow_types (template_id, flow_type) VALUES
  ('tpl-us-demo', 'FX'),
  ('tpl-us-demo', 'SWAP'),
  ('tpl-us-demo', 'CASH'),
  ('tpl-eu-demo', 'FX'),
  ('tpl-eu-demo', 'SWAP'),
  ('tpl-apac-demo', 'FX'),
  ('tpl-apac-demo', 'SWAP'),
  ('tpl-apac-demo', 'CASH');

-- Client IDs
INSERT INTO timer_template_client_ids (template_id, client_id) VALUES
  ('tpl-us-demo', 'CLIENT_GB001'),
  ('tpl-us-demo', 'CLIENT_DE001'),
  ('tpl-us-demo', 'CLIENT_FR001'),
  ('tpl-eu-demo', 'CLIENT_IT001'),
  ('tpl-eu-demo', 'CLIENT_ES001'),
  ('tpl-eu-demo', 'CLIENT_CZ001'),
  ('tpl-eu-demo', 'CLIENT_PL001'),
  ('tpl-apac-demo', 'CLIENT_JP001'),
  ('tpl-apac-demo', 'CLIENT_CN001'),
  ('tpl-apac-demo', 'CLIENT_HK001');

-- Product types
INSERT INTO timer_template_product_types (template_id, product_type) VALUES
  ('tpl-us-demo', 'CASH'),
  ('tpl-eu-demo', 'SWAP'),
  ('tpl-apac-demo', 'CASH'),
  ('tpl-apac-demo', 'SWAP');
