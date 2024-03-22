package com.sas.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sas.entity.Product;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProductListServiceImp implements ProductListService {
	@Autowired
    private ProductService serviceproduct;
	@Override
	public void downloadProductListPDF(HttpServletResponse response) {
        List<Product> productList = serviceproduct.fetchAllProduct();

     // Generate QR code images for each product
        List<byte[]> qrCodeImageBytesList = new ArrayList<>();
        for (Product product : productList) {
            String formattedText = formatProductDetails(product);
            byte[] qrCodeImageBytes = QRCodeGenerator.generateQRCode(formattedText).toByteArray();
            qrCodeImageBytesList.add(qrCodeImageBytes);
        }
        // Generate PDF with product details and QR codes
        byte[] pdfBytes = PDFGenerator.generatePDFWithQRCode(productList, qrCodeImageBytesList);

        if (pdfBytes != null && pdfBytes.length > 0) {
            try {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=products.pdf");
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Generated PDF is empty or null.");
            // Optionally, you can handle this case by redirecting or showing an error message to the user
        }
    }
    private String formatProductDetails(Product product) {
    	
        
        // Format the product details string
        String formattedText = "productId=" + product.getProductId() +
                ", date=" + product.getDate() +
                ", price=" + product.getPrice() +
                ", allocate=" + product.getAllocate() +
                ", purpose=" + product.getPurpose() +
                ", category=" + product.getCategory() +
                ", type=" + product.getType().getName() +  
                ", functionality=" + product.getFunctionality() +
                ", ruin_date=" + product.getRuin_date();
        
        return formattedText;
		
	}
	@Override
	public String generateQR(String text) {
		 ByteArrayOutputStream baos = QRCodeGenerator.generateQRCode(text);
	        return Base64.getEncoder().encodeToString(baos.toByteArray());
		
	}

	

}
