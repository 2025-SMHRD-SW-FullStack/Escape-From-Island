-- 생존 게임 데이터베이스 초기화 스크립트

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS survival_game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 선택
USE survival_game;

-- 기존 테이블이 있다면 삭제
DROP TABLE IF EXISTS USER_ACHIEVEMENTS;
DROP TABLE IF EXISTS USER_STATISTICS;
DROP TABLE IF EXISTS ACHIEVEMENTS;
DROP TABLE IF EXISTS USERS;

-- 테이블 생성 및 초기 데이터 삽입은 별도 스크립트로 실행
-- source create_tables.sql
-- source init_data.sql 