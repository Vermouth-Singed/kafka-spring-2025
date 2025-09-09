package com.example.kafka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "test_board")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDttm;

    @Column
    private LocalDateTime updatedDttm;

    @PrePersist
    protected void onCreate() {
        createdDttm = LocalDateTime.now();
        updatedDttm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDttm = LocalDateTime.now();
    }
}
