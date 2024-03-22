package com.sas.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.common.BitMatrix;

public class QRCodeGenerator {

    public static ByteArrayOutputStream generateQRCode(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int width = 200;
        int height = 200;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L);

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos;
    }
}
