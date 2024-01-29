package com.example.codestates.todolist.entity;

import com.example.codestates.todolist.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long todoId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)", length = 100)
    private String title;

    @Column(nullable = false)
    private Integer todoOrder;

    @Column(nullable = false)
    private Boolean completed = false;
}
