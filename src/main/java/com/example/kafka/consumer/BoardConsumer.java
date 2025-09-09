package com.example.kafka.consumer;

import com.example.kafka.dto.BoardRequest;
import com.example.kafka.model.Board;
import com.example.kafka.repository.BoardRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BoardConsumer {
    private final BoardRepository boardRepository;

    BoardConsumer(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeBoardEvent(BoardRequest req) {
        Board board = Board
            .builder()
            .title(req.getTitle())
            .content(req.getContent())
            .build();

        boardRepository.save(board);
    }
}
