# 탈출 게임 MVC 패턴 구조 설명

## 목차
1. [MVC 패턴 개요](#mvc-패턴-개요)
2. [프로젝트 구조](#프로젝트-구조)
3. [Model 컴포넌트](#model-컴포넌트)
4. [View 컴포넌트](#view-컴포넌트)
5. [Controller 컴포넌트](#controller-컴포넌트)
6. [컴포넌트 간 상호작용](#컴포넌트-간-상호작용)
7. [다이어그램 참조](#다이어그램-참조)

## MVC 패턴 개요

MVC(Model-View-Controller) 패턴은 소프트웨어 디자인 패턴으로, 애플리케이션의 구성 요소를 세 가지 역할로 분리합니다 :

- **Model** : 데이터와 비즈니스 로직을 관리
- **View** : 사용자 인터페이스(UI) 표시
- **Controller** : 사용자 입력을 처리하고 Model과 View 사이의 중개자 역할

이러한 분리는 코드의 가독성, 재사용성, 유지보수성을 향상시키는 데 도움이 됩니다. 각 컴포넌트는 자신의 역할에만 집중하며, 다른 컴포넌트의 내부 구현에 의존하지 않습니다.

## 프로젝트 구조

탈출 게임 프로젝트는 MVC 패턴을 기반으로 다음과 같은 패키지 구조로 구성되어 있습니다.

```
survival/
├── Main.java                  # 애플리케이션 진입점
├── SurvivalGame.java          # 게임 관리 클래스
├── model/                     # 모델 (데이터와 비즈니스 로직)
│   ├── game/                  # 게임 관련 모델
│   │   ├── ActionType.java    # 행동 유형 열거형
│   │   ├── Event.java         # 이벤트 클래스
│   │   ├── EventType.java     # 이벤트 유형 열거형
│   │   ├── GameEndState.java  # 게임 종료 상태 열거형
│   │   ├── GameState.java     # 게임 상태 클래스
│   │   ├── Inventory.java     # 인벤토리 클래스
│   │   ├── Item.java          # 아이템 클래스
│   │   ├── ItemType.java      # 아이템 유형 열거형
│   │   ├── Player.java        # 플레이어 클래스
│   │   ├── Recipe.java        # 제작법 클래스
│   │   └── ResourceType.java  # 자원 유형 열거형
│   └── user/                  # 사용자 관련 모델
│       ├── Achievement.java   # 업적 클래스
│       └── User.java          # 사용자 클래스
├── view/                      # 뷰 (사용자 인터페이스)
│   ├── ConsoleUI.java         # 콘솔 UI 구현체
│   ├── GameView.java          # 게임 화면 인터페이스
│   └── UIConstants.java       # UI 상수
├── controller/                # 컨트롤러 (흐름 제어)
│   ├── game/                  # 게임 관련 컨트롤러
│   │   ├── ActionController.java    # 행동 컨트롤러
│   │   ├── CraftingController.java  # 제작 컨트롤러
│   │   ├── GameController.java      # 게임 컨트롤러
│   │   └── RandomGenerator.java     # 랜덤 생성기
│   └── user/                        # 사용자 관련 컨트롤러
│       ├── AchievementController.java  # 업적 컨트롤러
│       └── AuthController.java         # 인증 컨트롤러
├── dao/                       # 데이터 접근 객체
│   ├── AchievementDAO.java    # 업적 DAO
│   ├── DatabaseManager.java   # DB 관리자
│   └── UserDAO.java           # 사용자 DAO
├── dto/                       # 데이터 전송 객체
│   ├── GameEndDTO.java        # 게임 종료 DTO
│   ├── InventoryDTO.java      # 인벤토리 DTO
│   └── PlayerDTO.java         # 플레이어 DTO
└── util/                      # 유틸리티
    ├── Constants.java         # 상수
    └── DTOConverter.java      # DTO 변환기
```

## Model 컴포넌트

모델 컴포넌트는 애플리케이션의 데이터와 비즈니스 로직을 관리합니다. 주요 모델 클래스들은 다음과 같습니다 :

### 게임 모델 (`model.game` 패키지)

- **GameState** : 게임의 전반적인 상태를 관리하는 클래스. 일수, 플레이어 정보, 게임 종료 여부 등을 추적합니다.
- **Player** : 플레이어의 체력, 행동력, 인벤토리 등 플레이어 관련 데이터를 관리합니다.
- **Inventory** : 플레이어가 소유한 자원과 아이템을 관리합니다.
- **Item** : 게임 내 아이템의 속성과 기능을 정의합니다.
- **Recipe** : 아이템 제작에 필요한 재료와 조건을 정의합니다.
- **Event** : 게임 내에서 발생하는 이벤트의 종류와 효과를 정의합니다.

### 열거형 및 상수

- **ActionType** : 탐험, 제작, 휴식 등 플레이어의 행동 유형을 정의합니다.
- **EventType** : 이벤트의 종류(자원 획득, 피해, 회복 등)를 정의합니다.
- **ItemType** : 아이템의 종류(도구, 쉘터, 생존 도구, 뗏목 등)를 정의합니다.
- **ResourceType** : 게임 내 자원의 종류(나무, 돌, 식량, 물 등)를 정의합니다.
- **GameEndState** : 게임 종료 상태(승리, 사망, 포기)를 정의합니다.

### 사용자 모델 (`model.user` 패키지)

- **User** : 사용자 계정 정보를 관리합니다.
- **Achievement** : 게임 내 업적 시스템을 관리합니다.

## View 컴포넌트

뷰 컴포넌트는 사용자에게 정보를 표시하고 입력을 받는 역할을 합니다.

- **GameView** : 게임 화면 표시를 위한 인터페이스를 정의합니다. 메뉴 표시, 입력 처리, 플레이어 상태 표시, 오류 메시지 표시 등의 메서드를 포함합니다.
- **ConsoleUI** : GameView 인터페이스의 구현체. 콘솔 기반의 텍스트 인터페이스를 제공합니다.
- **UIConstants** : UI 관련 상수(메뉴 헤더, 구분선 등)를 정의합니다.

뷰 컴포넌트는 사용자 인터페이스만 담당하며, 직접적인 비즈니스 로직은 포함하지 않습니다. 모든 기능은 컨트롤러를 통해 요청되고, 모델로부터 데이터를 받아 표시합니다. 또한 시스템 오류와 같은 예외 상황에 대한 메시지도 사용자에게 보여주는 역할을 담당합니다.

## Controller 컴포넌트

컨트롤러 컴포넌트는 사용자 입력을 처리하고, 모델과 뷰 사이의 중개자 역할을 합니다.

### 게임 컨트롤러 (`controller.game` 패키지)

- **GameController** : 게임의 전체 흐름을 제어하는 핵심 클래스. 게임 시작, 진행, 종료 등을 관리합니다.
- **ActionController** : 플레이어의 행동(탐험, 제작, 휴식)을 처리합니다.
- **CraftingController** : 아이템 제작 시스템을 관리합니다.
- **RandomGenerator** : 게임 내 랜덤 요소(자원 획득, 이벤트 발생 등)를 생성합니다.

### 사용자 컨트롤러 (`controller.user` 패키지)

- **AuthController** : 사용자 인증(로그인, 회원가입)을 처리합니다.
- **AchievementController** : 업적 시스템을 관리합니다.

### 데이터 접근 객체 (`dao` 패키지)

- **DatabaseManager** : 데이터베이스 연결을 관리합니다.
- **UserDAO** : 사용자 데이터에 접근하는 객체입니다.
- **AchievementDAO** : 업적 데이터에 접근하는 객체입니다.

### 데이터 전송 객체 (`dto` 패키지)

- **PlayerDTO** : 플레이어 데이터를 뷰에 전달하기 위한 객체입니다.
- **InventoryDTO** : 인벤토리 데이터를 뷰에 전달하기 위한 객체입니다.
- **GameEndDTO** : 게임 종료 정보를 뷰에 전달하기 위한 객체입니다.

### 유틸리티 (`util` 패키지)

- **DTOConverter** : 모델 객체를 DTO로 변환하는 유틸리티 클래스입니다.
- **Constants** : 게임 전반에서 사용되는 상수를 정의합니다.

## 컴포넌트 간 상호작용

현재 프로젝트에서 MVC 패턴에서 각 컴포넌트는 다음과 같이 상호작용합니다 :

1. **Main → SurvivalGame** : 애플리케이션이 시작되면 Main 클래스가 SurvivalGame 객체를 생성하고 실행합니다. SurvivalGame은 시스템 초기화(데이터베이스 등) 및 게임 전체 흐름을 관리합니다.

2. **SurvivalGame → GameController** : SurvivalGame이 GameController를 생성하고 게임 로직 실행을 위임합니다.

3. **SurvivalGame → View** : SurvivalGame은 초기화 오류 등의 시스템 메시지를 View를 통해 사용자에게 표시합니다.

4. **Controller → Model** : 컨트롤러는 사용자 입력에 따라 모델의 데이터를 업데이트합니다.
   - GameController가 GameState를 관리
   - ActionController가 Player의 행동을 처리
   - CraftingController가 아이템 제작을 처리

5. **Controller → DTO → View** : 컨트롤러는 모델의 데이터를 DTO로 변환하여 뷰에 전달합니다.
   - DTOConverter를 사용하여 모델 객체를 DTO로 변환
   - 뷰는 DTO를 통해 받은 데이터를 화면에 표시

6. **View → Controller** : 사용자의 입력은 뷰를 통해 받아들여지고, 컨트롤러에 전달됩니다.
   - 메뉴 선택, 행동 선택 등의 입력이 뷰에서 컨트롤러로 전달

7. **Controller → DAO** : 컨트롤러는 DAO를 통해 데이터를 저장하고 불러옵니다.
   - AuthController가 UserDAO를 사용하여 사용자 데이터 관리
   - AchievementController가 AchievementDAO를 사용하여 업적 데이터 관리

이러한 상호작용을 통해 각 컴포넌트는 자신의 역할에만 집중하며, 느슨하게 결합된 구조를 유지합니다.

## 다이어그램 참조

프로젝트의 구조와 흐름을 더 자세히 이해하기 위해 다음 다이어그램을 참조할 수 있습니다 :

### 클래스 다이어그램

- `mermaid/classDiagram/0_시스템_전체_구조.mermaid` : 시스템 전체 구조를 보여주는 다이어그램
- `mermaid/classDiagram/1_도메인모델_다이어그램.mermaid` : 도메인 모델(Model) 클래스들의 구조를 보여주는 다이어그램
- `mermaid/classDiagram/2_컨트롤러_다이어그램.mermaid` : 컨트롤러(Controller) 클래스들의 구조를 보여주는 다이어그램
- `mermaid/classDiagram/3_UI_다이어그램.mermaid` : 뷰(View) 클래스들의 구조를 보여주는 다이어그램
- `mermaid/classDiagram/4_데이터접근_다이어그램.mermaid` : 데이터 접근 계층의 구조를 보여주는 다이어그램
- `mermaid/classDiagram/5_DTO유틸리티_다이어그램.mermaid` : DTO와 유틸리티의 구조를 보여주는 다이어그램

### 플로우차트

- `mermaid/flowchart/게임_시작_및_메인_흐름_플로우차트.mermaid` : 게임 시작과 메인 메뉴 흐름을 보여주는 다이어그램
- `mermaid/flowchart/게임_진행_흐름_플로우차트.mermaid` : 게임 진행의 전체 흐름을 보여주는 다이어그램
- `mermaid/flowchart/플레이어_행동_흐름_플로우차트.mermaid` : 플레이어 행동 처리 과정을 보여주는 다이어그램
- `mermaid/flowchart/아이템_제작_시스템_플로우차트.mermaid` : 아이템 제작 시스템의 흐름을 보여주는 다이어그램
- `mermaid/flowchart/이벤트_시스템_플로우차트.mermaid` : 이벤트 시스템의 처리 과정을 보여주는 다이어그램
- `mermaid/flowchart/업적_시스템_플로우차트.mermaid` : 업적 시스템의 처리 과정을 보여주는 다이어그램
- `mermaid/flowchart/데이터베이스_시스템_플로우차트.mermaid` : 데이터베이스 시스템의 처리 과정을 보여주는 다이어그램
- `mermaid/flowchart/전반적인_프로젝트_플로우차트.mermaid` : 프로젝트 전체의 흐름을 종합적으로 보여주는 다이어그램

위 다이어그램들은 프로젝트의 구조와 흐름을 시각적으로 이해하는 데 도움이 됩니다. 각 다이어그램의 .png 파일도 같은 경로에 제공되어 있어 편리하게 확인할 수 있습니다.