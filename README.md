# Kafka CRUD Demo

Spring Boot와 Kafka를 사용한 CRUD 작업 데모 프로젝트입니다.

## 기능

- **CREATE**: 사용자 생성 (Kafka 이벤트 발행)
- **READ**: 사용자 조회 (전체/개별)
- **UPDATE**: 사용자 수정 (Kafka 이벤트 발행)
- **DELETE**: 사용자 삭제 (Kafka 이벤트 발행)
- **Kafka 이벤트 처리**: 모든 CRUD 작업에 대한 이벤트 발행 및 소비

## 실행 방법

### 1. Kafka 실행
```bash
docker-compose up -d
```

### 2. 애플리케이션 실행
```bash
mvn spring-boot:run
```

## API 사용법

### 사용자 생성
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "홍길동",
    "email": "hong@example.com",
    "phone": "010-1234-5678"
  }'
```

### 전체 사용자 조회
```bash
curl -X GET http://localhost:8080/api/users
```

### 특정 사용자 조회
```bash
curl -X GET http://localhost:8080/api/users/1
```

### 사용자 수정
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "김철수",
    "email": "kim@example.com",
    "phone": "010-9876-5432"
  }'
```

### 사용자 삭제
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## Kafka 토픽

- **토픽명**: `user-events`
- **파티션**: 3
- **리플리카**: 1

## 이벤트 구조

```json
{
  "operation": "CREATE|UPDATE|DELETE",
  "user": {
    "id": 1,
    "name": "홍길동",
    "email": "hong@example.com",
    "phone": "010-1234-5678",
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  "timestamp": 1704096000000
}
```

## 모니터링

- **H2 콘솔**: http://localhost:8080/h2-console
- **Kafka 토픽 모니터링**: 애플리케이션 로그에서 확인

## 기술 스택

- Spring Boot 3.5.5
- Spring Kafka
- Spring Data JPA
- H2 Database
- Docker & Docker Compose
- Gradle