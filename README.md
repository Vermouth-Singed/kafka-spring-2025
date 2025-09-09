#### [Spring Initializer](https://start.spring.io)로 프로젝트 생성
![](https://velog.velcdn.com/images/vermouth-singed/post/7536cc45-dc95-4e23-b6d3-bc3437ffd12f/image.png)
#### board-topic 생성
```shell
// Kafka 컨테이너에 접속
docker exec -it kafka_test-kafka-1 bash

//Kafka 토픽 board-topic 생성
kafka-topics --create --topic board-topic --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1
```
#### H2 Database 연결
```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: "jdbc:h2:mem:test_db;MODE=MYSQL;DB_CLOSE_DELAY=-1"
    username: sa
  h2:
    console:
      enabled: true
      path: /h2-console
```
#### http://localhost:8080/h2-console 접속화면
![](https://velog.velcdn.com/images/vermouth-singed/post/cec2c904-5e4e-42e2-b4a3-a9e5a20d4095/image.png)
#### 초기 데이터 세팅
```sql
-- schema.sql
DROP TABLE IF EXISTS test_board CASCADE;

CREATE TABLE test_board (
    id              bigint          AUTO_INCREMENT,	    -- 사용자 PK
    title           varchar(30),		                -- 제목
    content         varchar(100),			            -- 내용
    cnt             int             DEFAULT 0,          -- 조회수
    created_dttm    datetime		DEFAULT NOW(),      -- 생성날짜
    updated_dttm    datetime		DEFAULT NOW(),      -- 수정날짜
    PRIMARY KEY (id)
);

-- data.sql
INSERT INTO test_board(title, content) VALUES('제목01', '내용01');
INSERT INTO test_board(title, content) VALUES('제목02', '내용02');
INSERT INTO test_board(title, content) VALUES('제목03', '내용03');
INSERT INTO test_board(title, content) VALUES('제목04', '내용04');
INSERT INTO test_board(title, content) VALUES('제목05', '내용05');
INSERT INTO test_board(title, content) VALUES('제목06', '내용06');
INSERT INTO test_board(title, content) VALUES('제목07', '내용07');
INSERT INTO test_board(title, content) VALUES('제목08', '내용08');
INSERT INTO test_board(title, content) VALUES('제목09', '내용09');
INSERT INTO test_board(title, content) VALUES('제목10', '내용10');
INSERT INTO test_board(title, content) VALUES('제목11', '내용11');
INSERT INTO test_board(title, content) VALUES('제목12', '내용12');
```
#### Kafka 연동
```yaml
spring:
    kafka:
    bootstrap-servers: localhost:29092 #도커 카프카 접속 주소
    topic: board-topic #변수 통일 목적으로 세팅
    consumer:
      group-id: test-group #컨슈머와 일치
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer #키 복호화
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer #값 복호화
      properties:
        spring.json.trusted.packages: "*"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer #키 암호화
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer #키 복호화
```
#### 포스트맨으로 생성 테스트
![](https://velog.velcdn.com/images/vermouth-singed/post/77726e03-ef06-4663-9058-84708a16027c/image.png)
#### 정상 동작
![](https://velog.velcdn.com/images/vermouth-singed/post/6a2be7b7-d33b-4a41-8ad9-1bedaf4de464/image.png)
