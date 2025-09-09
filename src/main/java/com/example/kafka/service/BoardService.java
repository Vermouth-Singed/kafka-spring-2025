package com.example.kafka.service;

import com.example.kafka.dto.BoardRequest;
import com.example.kafka.dto.BoardResponse;
import com.example.kafka.enums.KafkaType;
import com.example.kafka.producer.BoardProducer;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardProducer boardProducer;

    public BoardService(BoardProducer boardProducer) {
        this.boardProducer = boardProducer;
    }

    public BoardResponse createBoard(BoardRequest request) {
        boardProducer.sendBoardEvent(KafkaType.CREATE.name(), request);

        return convertToResponse(request);
    }

    private BoardResponse convertToResponse(BoardRequest request) {
        return BoardResponse
            .builder()
            .title(request.getTitle())
            .build();
    }
}
