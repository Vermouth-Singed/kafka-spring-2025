package com.example.kafka.consumer;

import com.example.kafka.dto.BoardRequest;
import com.example.kafka.model.Board;
import com.example.kafka.repository.BoardRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardConsumer {
    private final BoardRepository boardRepository;

    BoardConsumer(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic1}", groupId = "${spring.kafka.topic1}")
    public void consumeBoardEvent(BoardRequest req) {
        Board board = Board
                .builder()
                .title(req.getTitle())
                .content(req.getContent())
                .build();

        boardRepository.save(board);
    }

    @KafkaListener(topics = "${spring.kafka.topic3}", groupId = "${spring.kafka.topic3}")
    public void consumeBoardCountEvent(List<BoardRequest> list) {
        if (list == null) {
            System.out.println("list is null");
        } else {
            list.forEach(req -> System.out.println(req.getId() + ">>>>>>" + list.size()));
        }
    }
}
