-- MySQL Script generated by MySQL Workbench
-- Sat Mar 20 21:30:17 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rbac
-- -----------------------------------------------------
-- rbac

-- -----------------------------------------------------
-- Schema rbac
--
-- rbac
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rbac` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `rbac` ;

-- -----------------------------------------------------
-- Table `rbac`.`system_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(12) NOT NULL COMMENT '账号',
  `password` VARCHAR(128) NOT NULL COMMENT '密码',
  `salt` VARCHAR(64) NOT NULL COMMENT '盐',
  `name` VARCHAR(12) NOT NULL COMMENT '用户名称',
  `gender` INT NULL COMMENT '性别',
  `tel` VARCHAR(16) NULL COMMENT '手机号码',
  `email` VARCHAR(32) NULL COMMENT '电子邮件',
  `is_enabled` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '启用状态',
  `creator` BIGINT NULL COMMENT '创建者',
  `gmt_create` TIMESTAMP NOT NULL COMMENT '创建时间',
  `updater` BIGINT NULL COMMENT '修改者',
  `gmt_modified` TIMESTAMP NOT NULL COMMENT '修改时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rbac`.`system_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_role` (
  `id` BIGINT NOT NULL,
  `parent_id` BIGINT NOT NULL COMMENT '父ID',
  `name` VARCHAR(32) NULL COMMENT '名称',
  `description` VARCHAR(32) NULL COMMENT '描述',
  `sort` INT NULL COMMENT '排序码',
  `is_enabled` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '启用状态',
  `creator` BIGINT NULL COMMENT '创建者',
  `gmt_create` TIMESTAMP NOT NULL COMMENT '创建时间',
  `updater` BIGINT NULL COMMENT '修改者',
  `gmt_modified` TIMESTAMP NOT NULL COMMENT '修改时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rbac`.`system_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_authority` (
  `id` BIGINT NOT NULL,
  `parent_id` BIGINT NOT NULL COMMENT '父ID',
  `name` VARCHAR(32) NOT NULL COMMENT '名称\n',
  `description` VARCHAR(32) NULL COMMENT '描述',
  `menu_id` BIGINT NULL COMMENT '菜单ID',
  `sort` INT NULL COMMENT '排序码',
  `is_enabled` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '启用状态',
  `creator` BIGINT NULL COMMENT '创建者',
  `gmt_create` TIMESTAMP NOT NULL COMMENT '创建时间',
  `updater` BIGINT NULL COMMENT '修改者',
  `gmt_modified` TIMESTAMP NOT NULL COMMENT '修改时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rbac`.`system_menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_menu` (
  `id` BIGINT NOT NULL,
  `parent_id` BIGINT NOT NULL COMMENT '父ID',
  `title` VARCHAR(32) NOT NULL COMMENT '标题',
  `description` VARCHAR(32) NULL COMMENT '描述',
  `url` VARCHAR(32) NOT NULL COMMENT '路由',
  `icon_path` VARCHAR(64) NULL COMMENT '图标路径',
  `sort` INT NULL COMMENT '排序码',
  `is_enabled` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '启用状态',
  `creator` BIGINT NULL COMMENT '创建者',
  `gmt_create` TIMESTAMP NOT NULL COMMENT '创建时间',
  `updater` BIGINT NULL COMMENT '修改者',
  `gmt_modified` TIMESTAMP NOT NULL COMMENT '修改时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rbac`.`system_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_user_role` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rbac`.`system_role_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rbac`.`system_role_authority` (
  `role_id` BIGINT NOT NULL,
  `auth_id` BIGINT NOT NULL,
  PRIMARY KEY (`role_id`, `auth_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
