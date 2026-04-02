package com.dev.tickets.model;

import com.dev.tickets.libs.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String ticketNumber;
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;
    @Column(columnDefinition = "TEXT")
    private String qrValue;
    private String qrImage;
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketTypeEntity ticketType;
    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    private OrderDetailEntity orderDetail;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseEntity purchase;
    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketValidationEntity> validations = new ArrayList<>();
    public static TicketEntity create(TicketTypeEntity type, PurchaseEntity purchase){
        TicketEntity ticket = new TicketEntity();
        ticket.setStatus(TicketStatusEnum.VALID);
        ticket.setTicketType( type );
        ticket.setPurchase( purchase );
        ticket.setTicketNumber( Utils.generateCode() );
        ticket.setQrValue(UUID.randomUUID().toString() );
        return ticket;
    }
}
