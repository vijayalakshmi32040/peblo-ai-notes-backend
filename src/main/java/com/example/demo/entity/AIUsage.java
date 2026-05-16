package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AIUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int summaryCount;

    private int actionItemCount;

    private int titleSuggestionCount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}