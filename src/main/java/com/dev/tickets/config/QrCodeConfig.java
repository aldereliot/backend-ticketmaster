package com.dev.tickets.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QrCodeConfig {

    @Bean
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter();
    }

}
