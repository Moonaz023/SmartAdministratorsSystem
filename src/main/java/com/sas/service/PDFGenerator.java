package com.sas.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.sas.entity.Product;
import java.util.*;
import com.itextpdf.layout.element.Cell;

public class PDFGenerator {

    public static byte[] generatePDFWithQRCode(List<Product> productList, List<byte[]> qrCodeImageBytesList) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set up table with 2 columns
            Table table = new Table(new float[]{0.5f, 0.5f}).useAllAvailableWidth();

            // Iterate through products and add each to the table
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                byte[] qrCodeImageBytes = qrCodeImageBytesList.get(i);

                
                String formattedProductDetails = formatProductDetails(product);

                // Add product details to the left column
                Cell detailsCell = new Cell().add(new Paragraph(formattedProductDetails));
                table.addCell(detailsCell);

                // Add QR code image to the right column
                if (qrCodeImageBytes != null && qrCodeImageBytes.length > 0) {
                    Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeImageBytes));
                    Cell imageCell = new Cell().add(qrCodeImage);
                    table.addCell(imageCell);
                }

                // Check if we should start a new row after every two products
                if ((i + 1) % 2 == 0 && i != productList.size() - 1) {
                    table.startNewRow();
                }
            }

            // Add the table to the document
            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String formatProductDetails(Product product) {
       
        return "Product Id: " + product.getProductId() + ",\n" +
                "Date: " + product.getDate() + ",\n" +
                "Price: " + product.getPrice() + ",\n" +
                "Allocate: " + product.getAllocate() + ",\n" +
                "Purpose: " + product.getPurpose() + ",\n" +
                "Category: " + product.getCategory() + ",\n" +
                "Type: " + (product.getType() != null ? product.getType().getName() : "Unknown") + ",\n" + 
                "Functionality: " + product.getFunctionality() + ",\n" +
                "Ruin Date: " + product.getRuin_date();
    }

}