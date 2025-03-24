# 운동 일정 스케줄러 API 명세서

## 기본 URL
```bash
/api/schedules
```
---

## 1️. 운동 일정 생성

Method: POST  
URL: /api/schedules  
필수값: exerciseDate,exercises, writer, password

요청 본문 예시:
```json
{
  "exerciseDate" : "가슴 운동"
  "exercise": ["푸시업", "벤치프레스", "딥스"],
  "writer": "홍길동",
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
  "writer": "홍길동",
  "createdAt": "2025-03-24T16:30:00",
  "modifiedAt": "2025-03-24T16:30:00"
}
```
```json
400 Bad Request (필수값 누락 시)
{
  "status": 400,
  "message": "운동 일자, 운동 목록, 작성자명, 비밀번호는 필수 입력 항목입니다."
}
```
---

## 2️. 전체 운동 일정 조회

Method: GET  
URL: /api/schedules  
쿼리 파라미터 (선택): modifiedAt=2025-03-24, writer=홍길동

응답:
```json
200 OK
[
  {
    "id": 1,
    "exerciseDate": "가슴 운동",
    "exercises": ["푸시업", "벤치프레스", "딥스"],
    "writer": "홍길동",
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
  "writer": "홍길동",
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
필수값: exercise, writer, password

요청 본문 예시:
```json
{
  "exerciseDate": "등 운동",
  "exercises": ["풀업", "렛풀다운", "바벨로우"],
  "writer": "홍길동",
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
  "writer": "홍길동",
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
┌────────────────────────┐
│       Schedule         │
├────────────────────────┤
│ id (PK)                │  ← 자동 생성되는 고유 식별자
│ exercise_date (VARCHAR)│  ← 운동 날짜 또는 주제 (ex: "가슴 운동")
│ exercises (TEXT/JSON)  │  ← ["푸시업", "벤치프레스", "딥스"]
│ writer (VARCHAR)       │  ← 작성자 이름
│ password (VARCHAR)     │  ← 수정/삭제 시 필요
│ created_at (DATETIME)  │  ← 최초 작성일
│ modified_at (DATETIME) │  ← 마지막 수정일
└────────────────────────┘
```
