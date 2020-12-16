package com.liouxb.boot.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author liouwb
 */
@Data
@Accessors(chain = true)
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class TestEs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int testEsId;

    private String name;

    private int age;

    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;
}
