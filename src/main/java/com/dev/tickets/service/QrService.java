package com.dev.tickets.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrService {

    private final QRCodeWriter qrCodeWriter;

    public QrService(QRCodeWriter qrCodeWriter) {
        this.qrCodeWriter = qrCodeWriter;
    }

    public String generateQrCodeImage(String uniqueId) throws WriterException, IOException {
        BitMatrix matrix = qrCodeWriter
                .encode(uniqueId, BarcodeFormat.QR_CODE, 300, 300);
        var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream() ){

            ImageIO.write(bufferedImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }

}
