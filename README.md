# 운동 일정 스케줄러 (Exercise Schedule API)

## 프로젝트 개요

운동 일정을 효율적으로 관리할 수 있도록 설계된 RESTful API 서버입니다.  
사용자는 운동 주제(예: 가슴 운동, 등 운동 등), 운동 목록, 일정 날짜 등을 등록하고, 원하는 조건으로 일정을 조회하거나 수정 및 삭제할 수 있습니다.

기본적인 일정 CRUD 기능 외에도 작성자 정보를 별도 테이블(`Author`)로 분리하여 연관관계를 맺는 구조로 설계되었습니다.  
이 프로젝트는 3 Layer Architecture와 DTO 기반 응답 설계를 학습하고, JDBC 기반의 DB 연동 및 유효성 검증 기능을 구현하는 것을 목표로 진행되었습니다.

---

## 주요 기능

- 운동 일정 등록 (작성자와 연관)
- 전체 운동 일정 조건 조회 (`작성자 ID`, `수정일` 등 필터)
- 운동 일정 단건 조회
- 일정 수정 및 삭제 (비밀번호 확인 포함)
- 작성자 등록 및 일정과 연관관계 설정
- 입력값 유효성 검증 처리 (필수값, 이메일 형식 등)

---

## 기술 스택

- **Java 17**
- **Spring Boot 3**
- **JDBC + MySQL**
- **Lombok**
- **Postman** (API 테스트)
- **Gradle** (Build Tool)

---

## 프로젝트 구조 (3 Layer Architecture)

```text
com.example.schedule
│
├── controller        # REST API Controller
├── service           # 비즈니스 로직
├── repository        # DB 접근 (JDBC 사용)
├── dto               # 요청/응답 DTO
├── entity            # Schedule, Author 엔티티 정의
└── ScheduleApplication.java
```
---
##  설계 및 개발 포인트  
- DTO를 통한 응답 일관성 유지
모든 API 응답은 Entity가 아닌 DTO를 통해 반환하여, 계층 간 명확한 책임 분리를 구현하였습니다.

- 작성자와 일정 간 연관관계 구현 (1:N)
일정(Schedule)에는 작성자의 ID(authorId)를 외래키로 저장하고, 응답에는 작성자 정보 전체(name, email)를 포함합니다.

- ERD 설계의 중요성
프로젝트 초기에 단일 테이블로 시작하였으나, 작성자 테이블 추가와 함께 구조적 리팩토링이 필요했습니다. 이를 통해 데이터 모델링의 중요성을 실감하고, 연관관계 도입 시의 구조 변경 경험을 학습하였습니다.

- JDBC 기반 쿼리 작성 및 RowMapper 활용
JPA 없이 JDBC만으로 DB 연동 로직을 구현하였으며, 복잡한 JOIN 결과도 DTO로 매핑하여 반환하였습니다.

- 예외 처리 및 유효성 검사 적용
비밀번호 오류, 존재하지 않는 일정 ID 접근, 필수값 누락, 잘못된 이메일 형식 등에 대해 명확한 예외 메시지와 상태 코드를 반환합니다.

##  개발 완료 상태
- 일정 생성/조회/수정/삭제 API

- 작성자 테이블 및 연관관계 구현

- 유효성 검증 및 예외 처리

- README 및 명세 정리

- Postman 테스트 완료

---
# 운동 일정 스케줄러 API 명세서

## 기본 URL
```bash
/api/schedules
```
---

## 1️. 운동 일정 생성

Method: POST  
URL: /api/schedules  
필수값: exerciseDate,exercises, authorId, password

요청 본문 예시:
```json
{
  "exerciseDate" : "가슴 운동"
  "exercise": ["푸시업", "벤치프레스", "딥스"],
  "authorId": 1,
  "password": "1234"
}
```
응답:
```json
201 Created (성공)
{
   "id": 1,
  "exerciseDate": "가슴 운동",
  "exercises": ["푸시업", "벤치프레스", "딥스"],
  "author": {
    "id" : 1,
    "name" : "이희망",
    "email" : "lee@naver.com"
  },
  "createdAt": "2025-03-24T16:30:00",
  "modifiedAt": "2025-03-24T16:30:00"
}
```
```json
400 Bad Request (필수값 누락 시)
{
  "status": 400,
  "message": "운동 일자, 운동 목록, 작성자 ID, 비밀번호는 필수 입력 항목입니다."
}
```
---

## 2️. 전체 운동 일정 조회

Method: GET  
URL: /api/schedules  
쿼리 파라미터 (선택): modifiedAt=2025-03-24, authorId=1

응답:
```json
200 OK
[
  {
    "id": 1,
    "exerciseDate": "가슴 운동",
    "exercises": ["푸시업", "벤치프레스", "딥스"],
    "author": {
      "id" : 1,
      "name" : "이희망",
      "email" : "lee@naver.com"
    },
    "createdAt": "2025-03-24T16:30:00",
    "modifiedAt": "2025-03-24T16:30:00"
 }
]
```
조건 불충족 시에도 200 OK + 빈 배열 반환

---

## 3️. 단일 운동 일정 조회

Method: GET  
URL: /api/schedules/{id}

응답:
``` json
200 OK
{
  "id": 1,
  "exerciseDate": "가슴 운동",
  "exercises": ["푸시업", "벤치프레스", "딥스"],
  "author": {
    "id" : 1,
    "name" : "이희망",
    "email" : "lee@naver.com"
  },
  "createdAt": "2025-03-24T16:30:00",
  "modifiedAt": "2025-03-24T16:30:00"
}
```
```json
404 Not Found
{
  "status": 404,
  "message": "해당 운동 일정이 존재하지 않습니다."
}
```
---

## 4️. 운동 일정 수정

Method: PUT  
URL: /api/schedules/{id}  
필수값: exerciseDate, exercises, authorId , password

요청 본문 예시:
```json
{
  "exerciseDate": "등 운동",
  "exercises": ["풀업", "렛풀다운", "바벨로우"],
  "authorId": 1,
  "password": "1234"
}
```
응답:
```json
200 OK
{
  "id": 1,
  "exerciseDate": "등 운동",
  "exercises": ["풀업", "렛풀다운", "바벨로우"],
  "author": {
    "id" : 1,
    "name" : "이희망",
    "email" : "lee@naver.com"
  },
  "createdAt": "2025-03-24T16:30:00",
  "modifiedAt": "2025-03-25T08:00:00"
}
```
```json
400 Bad Request
{
  "status": 400,
  "message": "운동 일자, 운동 목록, 작성자명, 비밀번호는 필수 입력 항목입니다."
}
```
```json
403 Forbidden
{
  "status": 403,
  "message": "비밀번호가 일치하지 않습니다."
}
```
```json
404 Not Found
{
  "status": 404,
  "message": "해당 운동 일정이 존재하지 않습니다."
}
```
---

## 5️. 운동 일정 삭제

Method: DELETE  
URL: /api/schedules/{id}

요청 본문 예시:
```json
{
  "password": "1234"
}
```
응답:
```json
204 No Content (성공)
```
```json
403 Forbidden
{
  "status": 403,
  "message": "비밀번호가 일치하지 않습니다."
}
```
```json
404 Not Found
{
  "status": 404,
  "message": "해당 운동 일정이 존재하지 않습니다."
}
```

## 6 . 작성자 등록 

Method: POST  
URL: /api/authors
필수값: name,email  
  
요청 본문 예시  
```json
{
  "name": "이희망",
  "email": "lee@naver.com"
}
```  
응답:
```json
201 Created
{
  "id": 1,
  "name": "이희망",
  "email": "lee@naver.com",
  "createdAt": "2025-03-24T15:00:00",
  "modifiedAt": "2025-03-24T15:00:00"
}
```

---

##  필드 유효성 규칙

- exerciseDate: 필수
- exercises: 필수, 비어 있지 않아야 함 (예: ["푸시업", "벤치프레스"])
- writer: 필수
- password: 필수
- createdAt: 자동 생성 (최초 등록 시 현재 시간)
- modifiedAt: 자동 생성/갱신 (등록 시 createdAt과 동일, 수정 시 현재 시간으로 갱신)

# ERD 설계 (운동 일정 단일 테이블 기준)
```pgsql
┌────────────────────┐         ┌──────────────────────┐
│      Author        │         │      Schedule         │
├────────────────────┤         ├──────────────────────┤
│ id (PK)            │◄────────┤ author_id (FK)        │
│ name (VARCHAR)     │         │ id (PK)               │
│ email (VARCHAR)    │         │ exercise_date         │
│ created_at         │         │ exercises (TEXT/JSON) │
│ modified_at        │         │ password              │
└────────────────────┘         │ created_at            │
                               │ modified_at           │
                               └──────────────────────┘

```
## ERD 구조

###  author 테이블

| 컬럼명       | 타입      | 제약 조건                   |
|--------------|-----------|-----------------------------|
| id           | BIGINT    | PK, AUTO_INCREMENT          |
| name         | VARCHAR   | NOT NULL                    |
| email        | VARCHAR   | NOT NULL, UNIQUE            |
| created_at   | DATETIME  | NOT NULL                    |
| modified_at  | DATETIME  | NOT NULL                    |

###  schedule 테이블 (변경된 버전)

| 컬럼명        | 타입      | 제약 조건                            |
|---------------|-----------|--------------------------------------|
| id            | BIGINT    | PK, AUTO_INCREMENT                   |
| author_id     | BIGINT    | FK → author.id (NOT NULL)            |
| exercise_date | VARCHAR   | NOT NULL                             |
| exercises     | TEXT      | NOT NULL                             |
| password      | VARCHAR   | NOT NULL                             |
| created_at    | DATETIME  | NOT NULL                             |
| modified_at   | DATETIME  | NOT NULL                             |
