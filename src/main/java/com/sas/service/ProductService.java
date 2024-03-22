package com.sas.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sas.repository.ProductRepository;
import com.sas.repository.TypeRepository;
import com.sas.entity.Product;
import com.sas.entity.Type;



@Service
public class ProductService {
	@Autowired
    private ProductRepository prepo;
	@Autowired
	private TypeRepository typeRepository;

	
	 public List<Type> getProductTypes() {
		 return typeRepository.findAll();
	 }
	
	public void insertProductFromCSV(MultipartFile file) throws IOException, CsvValidationException, NumberFormatException {
	    CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

	    String[] nextLine;

	    while ((nextLine = reader.readNext()) != null) {
	        Product product = new Product();
	        product.setProductId(nextLine[1]);
	        product.setDate(nextLine[2]);
	        double price = Double.parseDouble(nextLine[3]);
	        product.setPrice(price);
	        product.setAllocate(nextLine[4]);
	        product.setPurpose(nextLine[5]);
	        product.setCategory(nextLine[6]);
            Type type = typeRepository.findByName(nextLine[7]);
            product.setType(type);
	        product.setFunctionality(nextLine[8]);
	       
	        prepo.save(product);
	    }
	    reader.close();
	}
	public void insertProduct(Product product) {
		prepo.save(product);
	}
	public Product findProductById(String productId) {
		 return prepo.findById(productId).orElse(null);
	}
	
	public String updateProduct(Product updatedProduct) {
		

	    /* 
	     Product existingProduct = prepo.findById(productId)
	       .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
	     // Update the product record with the new data
	     existingProduct.setDate(updatedProduct.getDate());
	     existingProduct.setPrice(updatedProduct.getPrice());
	     existingProduct.setAllocate(updatedProduct.getAllocate());
	     existingProduct.setPurpose(updatedProduct.getPurpose());
	     existingProduct.setCategory(updatedProduct.getCategory());
	     existingProduct.setType(updatedProduct.getType());
	     existingProduct.setFunctionality(updatedProduct.getFunctionality());
	     // Save the updated product record by  prepo.save(existingProduct);*/
		
		
	     prepo.save(updatedProduct);
	     return "Product record updated successfully";
	}

	public List<Product> fetchAllProduct() {
		// TODO Auto-generated method stub
		return prepo.findAll();
	}
}

