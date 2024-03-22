package com.sas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
    @Id
    private String productId;
    
    private String date;
    private Double price;
    private String allocate;
    private String purpose;
    private String category;
    
    
    @ManyToOne
    @JoinColumn(name = "type")
    private Type type;
    private String functionality;
    private String  ruin_date;
    
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAllocate() {
		return allocate;
	}
	public void setAllocate(String allocate) {
		this.allocate = allocate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getFunctionality() {
		return functionality;
	}
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	public String getRuin_date() {
		return ruin_date;
	}
	public void setRuin_date(String ruin_date) {
		this.ruin_date = ruin_date;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", date=" + date + ", price=" + price + ", allocate=" + allocate
				+ ", purpose=" + purpose + ", category=" + category + ", type=" + type + ", functionality="
				+ functionality + ", ruin_date=" + ruin_date + "]";
	}
    
    
    
    
   
   
}