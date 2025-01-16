package com.rljj.switchswitchentity.chip;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chip", indexes = {
        @Index(name = "idx_name", columnList = "name")
})
public class Chip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    private String price;

    private String consoleModel;

    private LocalDateTime createdDate;
}
