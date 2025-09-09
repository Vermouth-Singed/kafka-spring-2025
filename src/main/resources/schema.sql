DROP TABLE IF EXISTS test_board CASCADE;

CREATE TABLE test_board
(
    id              bigint          AUTO_INCREMENT,	    -- 사용자 PK
    title           varchar(30),		                -- 제목
    content         varchar(100),			            -- 내용
    cnt             int             DEFAULT 0,          -- 조회수
    created_dttm    datetime		DEFAULT NOW(),      -- 생성날짜
    updated_dttm    datetime		DEFAULT NOW(),      -- 수정날짜
    PRIMARY KEY (id)
);