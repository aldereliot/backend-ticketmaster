package com.dev.tickets.service;

import com.dev.tickets.dto.purchase.PurchaseItemRequest;
import com.dev.tickets.dto.purchase.PurchaseRequest;
import com.dev.tickets.dto.purchase.PurchaseResponse;
import com.dev.tickets.dto.purchase.TicketTypePurchase;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.libs.Utils;
import com.dev.tickets.mapper.PurchaseMapper;
import com.dev.tickets.model.*;
import com.dev.tickets.repository.PurchaseRepository;
import com.dev.tickets.repository.TicketRepository;
import com.dev.tickets.repository.TicketTypeRepository;
import com.dev.tickets.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrService qrService;

    public PurchaseService(PurchaseRepository purchaseRepository, TicketRepository ticketRepository, UserService userService, TicketTypeRepository ticketTypeRepository, QrService qrService) {
        this.purchaseRepository = purchaseRepository;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.ticketTypeRepository = ticketTypeRepository;
        this.qrService = qrService;
    }

    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest request){
        UserEntity loggedUser = userService.getUserLogged();
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setBuyer(loggedUser);

        for( PurchaseItemRequest item : request.getTickets() ){
            TicketTypeEntity type = ticketTypeRepository
                    .findByIdWithLock(item.getTicketTypeId())
                    .orElseThrow( () -> new AppException("Ticket type no existe") );
            purchase.addTickets( type, item.getQuantity() );
        }
        PurchaseEntity purchaseSaved = purchaseRepository.save(purchase);
        return PurchaseMapper.toDto(purchaseSaved);
    }


    public List<PurchaseEntity> getUserPurchases() {
        UserEntity loggedUser = userService.getUserLogged();
        return purchaseRepository.findByBuyer(loggedUser);
    }

    public PurchaseEntity getPurchaseById(Integer id) {
        return purchaseRepository.findById(id).orElseThrow( () -> new AppException("Purchase not found") );
    }
}
