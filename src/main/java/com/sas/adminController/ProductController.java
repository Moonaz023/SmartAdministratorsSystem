package com.sas.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sas.entity.Product;
import com.sas.entity.Type;
import com.sas.service.ProductListService;
import com.sas.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
    private ProductService serviceproduct;
	@Autowired
    private ProductListService productListService;
	
	 @GetMapping("/types")
	    @ResponseBody
	    public List<Type> getProductTypes() {
	        return serviceproduct.getProductTypes();
	    }
	
	 
	@PostMapping("/insertproduct")
	@ResponseBody
	public String insert(@ModelAttribute Product u,HttpSession session )
	{
		System.out.println(u);
		serviceproduct.insertProduct(u);
		session.setAttribute("message", "Producttttttttttttttt");
		return "save";
	}
	
	
	
	 @PostMapping("/insert_product_csv")
	    @ResponseBody
	    public String uploadProductCSVFile(@RequestParam("file") MultipartFile file) {
	        try {
	        	serviceproduct.insertProductFromCSV(file);
	            return "save";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	    }
	 
	 @GetMapping("/fetchproduct/{productId}")
	    @ResponseBody
	    public Product fetchProduct(@PathVariable("productId") String productId) {
		 
		 return serviceproduct.findProductById(productId);
	         //prepo.findById(productId).orElse(null);
	    }
	 
	 @PostMapping("/updateproduct")
	 @ResponseBody
	 public String updateProduct( @ModelAttribute Product updatedProduct) {
	     
	     
	     return serviceproduct.updateProduct(updatedProduct);
	 }
	 
		@GetMapping("/fetchAllProduct")
		@ResponseBody
		public List<Product> getAllproducts(Model m, HttpSession session) {

		    List<Product> listOfproduct = serviceproduct.fetchAllProduct();

		    return listOfproduct;
		}
		
		
		@PostMapping("/generateQR")
	    @ResponseBody
	    public String generateQR(@RequestParam String text) {
	    	return productListService.generateQR(text);
	        
	    }
		 @GetMapping("/downloadProductList")
		    public void DownloadProductListPDF(HttpServletResponse response) {
		    	productListService.downloadProductListPDF(response);
		    }


}
