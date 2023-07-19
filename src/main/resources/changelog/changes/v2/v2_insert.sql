INSERT INTO `admin` (`id`, `email`, `first_name`, `last_name`, `password`, `role`, `created_at`, `updated_at`,
                     `deleted_at`)
VALUES (1, 'jpeter@yassir.com', 'Peter', 'John', '$2a$10$9qRMXhN6x7LSbNXeyPDSAOCRscXT.5Dv8tzRWRfjF6LITwDdHiOKC',
        'ADMIN', NOW(), NOW(), NULL);

INSERT INTO `account` (`id`, `email`, `first_name`, `last_name`, `created_at`, `updated_at`, `deleted_at`)
VALUES (1, 'user1@test.com', 'Test', 'User1', NOW(), NOW(), NULL),
       (2, 'user2@test.com', 'Test', 'User2', NOW(), NOW(), NULL);

INSERT INTO `wallet` (`id`, `wallet_no`, `currency_code`, `balance`, `account_id`, `created_at`, `updated_at`,
                      `deleted_at`)
VALUES (1, '6163759971', 'EUR', 2149.5000, 1, NOW(), NOW(), NULL),
       (2, '2854004807', 'GBP', 500.0000, 1, NOW(), NOW(), NULL),
       (3, '5814533487', 'EUR', 3850.5000, 2, NOW(), NOW(), NULL),
       (4, '3112805941', 'GBP', 557500.0000, 2, NOW(), NOW(), NULL),
       (5, '7496493042', 'USD', 99250.0000, 1, NOW(), NOW(), NULL),
       (6, '2499962315', 'USD', 100750.0000, 1, NOW(), NOW(), NULL);
