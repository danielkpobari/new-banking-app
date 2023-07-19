CREATE TABLE `admin`
(
    `id`         BIGINT                       NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(255)                 NOT NULL,
    `first_name` VARCHAR(255)                 NOT NULL,
    `last_name`  VARCHAR(255)                 NOT NULL,
    `password`   VARCHAR(255)                 NOT NULL,
    `role`       ENUM ('SUPER_ADMIN','ADMIN') NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKpojyedma2rei2cmugr29o3jdm` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `account`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name`  VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKpojyedma2rei2cmugr29o3jdm_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `wallet`
(
    `id`            BIGINT         NOT NULL AUTO_INCREMENT,
    `wallet_no`     VARCHAR(255)   NOT NULL,
    `currency_code` VARCHAR(3)     NOT NULL,
    `balance`       DECIMAL(18, 4) NOT NULL DEFAULT 0,
    `account_id`    BIGINT         NOT NULL,
    `created_at`    DATETIME(6)             DEFAULT NULL,
    `updated_at`    DATETIME(6)             DEFAULT NULL,
    `deleted_at`    DATETIME(6)             DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKpojyedma2rei2cmugr29o3jdm_wallet_no` (`wallet_no`),
    CONSTRAINT `FK_wallet_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `token`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `token`      VARCHAR(255) NOT NULL UNIQUE,
    `revoked`    BOOLEAN      NOT NULL,
    `expired`    BOOLEAN      NOT NULL,
    `admin_id`   BIGINT       NOT NULL,
    `created_at` DATETIME(6) DEFAULT NULL,
    `updated_at` DATETIME(6) DEFAULT NULL,
    `deleted_at` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_token_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `transaction`
(
    `id`                BIGINT                                     NOT NULL AUTO_INCREMENT,
    `type`              ENUM ('CREDIT','DEBIT')                    NOT NULL,
    `amount`            DECIMAL(18, 9)                             NOT NULL,
    `status`            ENUM ('SUCCESSFUL','PROCESSING', 'FAILED') NOT NULL,
    `currency_code`     VARCHAR(3)                                 NOT NULL,
    `admin_id`          BIGINT                                     NOT NULL,
    `wallet_id`         BIGINT                                     NOT NULL,
    `consort_wallet_id` BIGINT                                     NOT NULL,
    `created_at`        DATETIME(6) DEFAULT NULL,
    `updated_at`        DATETIME(6) DEFAULT NULL,
    `deleted_at`        DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_transaction_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`),
    CONSTRAINT `FK_transaction_wallet1` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`),
    CONSTRAINT `FK_transaction_wallet2` FOREIGN KEY (`consort_wallet_id`) REFERENCES `wallet` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `transaction_detail`
(
    `id`             BIGINT                  NOT NULL AUTO_INCREMENT,
    `type`           ENUM ('CREDIT','DEBIT') NOT NULL,
    `amount`         DECIMAL(18, 9)          NOT NULL,
    `old_balance`    DECIMAL(18, 9)          NOT NULL,
    `new_balance`    DECIMAL(18, 9)          NOT NULL,
    `wallet_id`      BIGINT                  NOT NULL,
    `transaction_id` BIGINT                  NOT NULL,
    `account_id`     BIGINT                  NOT NULL,
    `created_at`     DATETIME(6) DEFAULT NULL,
    `updated_at`     DATETIME(6) DEFAULT NULL,
    `deleted_at`     DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_transaction_detail_wallet` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`),
    CONSTRAINT `FK_transaction_detail_transaction` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`),
    CONSTRAINT `FK_transaction_detail_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
