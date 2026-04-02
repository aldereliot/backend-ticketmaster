package com.dev.tickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_validations")
public class TicketValidationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TicketValidationStatusEnum status;
    @Enumerated(EnumType.STRING)
    private TicketValidationMethodEnum validationMethod;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticket;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
