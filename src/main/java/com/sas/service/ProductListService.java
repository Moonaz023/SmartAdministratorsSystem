package com.sas.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ProductListService {

	void downloadProductListPDF(HttpServletResponse response);

	String generateQR(String text);

}
