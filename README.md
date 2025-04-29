# 무인도 생존 게임 (Escape From Island)

무인도에 불시착한 플레이어가 생존을 위해 자원을 모으고, 도구를 제작하며, 궁극적으로 섬을 탈출하는 텍스트 기반 생존 게임입니다.

## 목차
- [무인도 생존 게임 (Escape From Island)](#무인도-생존-게임-escape-from-island)
  - [목차](#목차)
  - [프로젝트 개요](#프로젝트-개요)
  - [기술 스택](#기술-스택)
  - [주요 기능](#주요-기능)
    - [게임 핵심 기능](#게임-핵심-기능)
    - [추가 기능](#추가-기능)
  - [아키텍처](#아키텍처)
    - [주요 컴포넌트](#주요-컴포넌트)
  - [패키지 구조](#패키지-구조)
  - [게임 플레이 방법](#게임-플레이-방법)
  - [MVC 패턴 구현](#mvc-패턴-구현)
  - [데이터베이스 스키마](#데이터베이스-스키마)
    - [Users 테이블](#users-테이블)
    - [Achievements 테이블](#achievements-테이블)
    - [User\_Achievements 테이블](#user_achievements-테이블)
  - [프로젝트 문서](#프로젝트-문서)
  - [향후 개선 계획](#향후-개선-계획)

## 프로젝트 개요

- **게임 형식** : 텍스트 기반 콘솔 게임
- **주요 목표** : 자원 수집, 아이템 제작, 생존 관리를 통해 무인도 탈출
- **추가 기능** : 사용자 인증 시스템, 업적 시스템

## 기술 스택

- **언어** : Java
- **데이터베이스** : Oracle (JDBC 사용)
- **빌드 도구** : Maven
- **아키텍처 패턴** : MVC (Model-View-Controller)
- **데이터 전송** : DTO (Data Transfer Object) 패턴

## 주요 기능

### 게임 핵심 기능

1. **자원 수집** : 탐험을 통해 나무, 돌, 식량 등의 자원 획득
2. **아이템 제작** : 수집한 자원으로 도구, 쉘터, 뗏목 등 제작
3. **생존 관리** : 체력(HP)과 행동력(AP) 관리
4. **랜덤 이벤트** : 탐험 중 다양한 이벤트 발생

### 추가 기능

1. **사용자 관리**
   - 회원가입 및 로그인 시스템
   - 사용자 프로필 및 세션 관리

2. **업적 시스템**
   - 게임 진행에 따른 업적 달성
   - 사용자별 업적 저장 및 조회

## 아키텍처

프로젝트는 MVC 아키텍처를 기반으로 하며, 데이터 접근 계층과 DTO 패턴을 활용해 레이어 간 결합도를 낮추었습니다.

### 주요 컴포넌트

- **Model** : 게임 데이터 및 비즈니스 로직 (Player, Inventory, Item 등)
- **View** : 사용자 인터페이스 (GameView 인터페이스, ConsoleUI 구현체)
- **Controller** : 게임 로직 및 사용자 입력 처리 (GameController, ActionController 등)
- **DAO** : 데이터베이스 접근 계층 (UserDAO, AchievementDAO)
- **DTO** : 레이어 간 데이터 전송 객체 (PlayerDTO, InventoryDTO 등)

> 더 자세한 아키텍처 설명은 [MVC 패턴 구조 설명](현재_프로젝트_MVC패턴_구조_설명.md) 문서를 참조하세요.

## 패키지 구조

```
survival/
├── Main.java                  - 애플리케이션 진입점
├── SurvivalGame.java          - 게임 초기화 및 시작 로직
├── model/
│   ├── game/                  - 게임 관련 모델 클래스
│   │   ├── Player.java        - 플레이어 상태 관리
│   │   ├── Inventory.java     - 자원 및 아이템 관리
│   │   ├── Item.java          - 게임 아이템 정의
│   │   ├── ItemType.java      - 아이템 유형 열거형
│   │   ├── Recipe.java        - 아이템 제작 레시피
│   │   ├── GameState.java     - 게임 상태 관리
│   │   ├── Event.java         - 게임 이벤트 모델
│   │   ├── EventType.java     - 이벤트 유형 열거형
│   │   ├── ActionType.java    - 행동 유형 열거형
│   │   ├── ResourceType.java  - 자원 유형 열거형
│   │   └── GameEndState.java  - 게임 종료 상태 열거형
│   └── user/                  - 사용자 관련 모델 클래스
│       ├── User.java          - 사용자 정보 모델
│       └── Achievement.java   - 업적 모델
├── controller/
│   ├── game/                  - 게임 컨트롤러 클래스
│   │   ├── GameController.java     - 전체 게임 흐름 제어
│   │   ├── ActionController.java   - 플레이어 행동 처리
│   │   ├── CraftingController.java - 아이템 제작 시스템
│   │   └── RandomGenerator.java    - 랜덤 이벤트 및 자원 생성
│   └── user/                       - 사용자 컨트롤러 클래스
│       ├── AuthController.java     - 인증 관련 기능
│       └── AchievementController.java - 업적 관리 및 처리
├── view/
│   ├── GameView.java          - 게임 화면 인터페이스
│   ├── ConsoleUI.java         - 콘솔 UI 구현체
│   └── UIConstants.java       - UI 관련 상수 정의
├── dao/
│   ├── DatabaseManager.java   - DB 연결 관리 (싱글톤)
│   ├── UserDAO.java           - 사용자 데이터 접근
│   └── AchievementDAO.java    - 업적 데이터 접근
├── dto/
│   ├── PlayerDTO.java         - 플레이어 데이터 전송 객체
│   ├── InventoryDTO.java      - 인벤토리 데이터 전송 객체
│   └── GameEndDTO.java        - 게임 종료 데이터 전송 객체
└── util/
    ├── Constants.java         - 게임 상수 정의
    └── DTOConverter.java      - 모델-DTO 변환 유틸리티
```

> 전체 프로젝트 구조와 Maven 구조 채택 이유에 대한 자세한 설명은 [폴더 구조](폴더%20구조.md) 문서를 참조하세요.

## 게임 플레이 방법

1. **게임 시작** : 프로그램 실행 후 메인 메뉴에서 '게임 시작' 선택
2. **로그인/회원가입** : 사용자 계정으로 로그인하거나 신규 계정 생성
3. **게임 진행** :
   - **탐험하기** : 자원을 수집하고 랜덤 이벤트 발생
   - **아이템 제작** : 수집한 자원으로 생존에 필요한 아이템 제작
   - **휴식하기** : 체력 회복
4. **게임 승리 조건** : 뗏목을 제작하여 무인도 탈출

## MVC 패턴 구현

이 프로젝트는 MVC 패턴을 철저히 준수하려고 노력했습니다.

- **Model** : 데이터와 비즈니스 로직만 담당
- **View** : 사용자 인터페이스와 입출력만 담당 (내부 로직 없음)
- **Controller** : 모델과 뷰 사이의 중개 역할

특히 DTO 패턴을 활용하여 모델과 뷰 사이의 결합도를 낮추고, 각 계층이 자신의 역할에만 집중할 수 있도록 설계했습니다.

## 데이터베이스 스키마

### Users 테이블
```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);
```

### Achievements 테이블
```sql
CREATE TABLE achievements (
    achievement_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    condition_code VARCHAR(50) NOT NULL,
    achievement_type VARCHAR(50) NOT NULL
);
```

### User_Achievements 테이블
```sql
CREATE TABLE user_achievements (
    user_id INT,
    achievement_id INT,
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, achievement_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (achievement_id) REFERENCES achievements(achievement_id)
);
```

## 프로젝트 문서

프로젝트 개발과 관련된 자세한 내용은 다음 문서들을 참조하세요:

- [구현 계획](구현%20계획.md) : 팀원 역할, 일정 계획, 개발 접근 방식 등 상세 구현 계획
- [폴더 구조](폴더%20구조.md) : 프로젝트의 자세한 폴더 구조 및 Maven 구조 채택 이유
- [MVC 패턴 구조 설명](현재_프로젝트_MVC패턴_구조_설명.md) : 본 프로젝트에서 적용한 MVC 패턴 상세 설명
- [Maven 채택 이유 및 사용법](maven%20채택%20이유.md) : Maven의 장점 및 이클립스/VS Code에서의 상세 사용법

## 향후 개선 계획

1. 그래픽 사용자 인터페이스 (GUI) 추가
2. 게임 난이도 조절 기능
3. 더 다양한 이벤트와 아이템 추가

