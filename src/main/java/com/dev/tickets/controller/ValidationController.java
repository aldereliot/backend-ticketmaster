package com.dev.tickets.controller;

import com.dev.tickets.dto.validation.ValidationRequest;
import com.dev.tickets.dto.validation.ValidationResponse;
import com.dev.tickets.mapper.ValidationMapper;
import com.dev.tickets.model.TicketValidationEntity;
import com.dev.tickets.model.TicketValidationMethodEnum;
import com.dev.tickets.service.ValidationService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket-validation")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping
    public ResponseEntity<?> validateTicket(@RequestBody ValidationRequest request){

        try{
            TicketValidationMethodEnum method = TicketValidationMethodEnum.valueOf(request.method().toString());
            TicketValidationEntity validation;
            if( TicketValidationMethodEnum.QR_CODE.equals(method) ){
                validation = validationService.validateTicketByQrCode(request);
            }else {
                validation = validationService.validateTicketManually(request);
            }
            ValidationResponse res = ValidationMapper.toDto(validation);
            return ResponseEntity.ok().body(res);

        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", "Metodo de validación no válido"));
        }
    }

}
