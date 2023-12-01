package com.shashi.service;

import java.io.InputStream;
import java.util.List;

import com.shashi.beans.ProductBean;

public interface ProductService {

	 String addProduct(String prodName, String prodType, String prodInfo, double prodPrice, int prodQuantity,
			InputStream prodImage, int amountSold, int discountPercent);

	 String addProduct(ProductBean product);

	 String removeProduct(String prodId);

	 String updateProduct(ProductBean prevProduct, ProductBean updatedProduct);

	 String updateProductPrice(String prodId, double updatedPrice);

	 List<ProductBean> getAllProducts();

	 List<ProductBean> getAllProductsByType(String type);

	 List<ProductBean> searchAllProducts(String search);

	 byte[] getImage(String prodId);

	 ProductBean getProductDetails(String prodId);

	 String updateProductWithoutImage(String prevProductId, ProductBean updatedProduct);

	 double getProductPrice(String prodId);

	 double getProductDiscountPercent(String prodId);

	 boolean sellNProduct(String prodId, int n);

	 int getProductQuantity(String prodId);
	
	 List<ProductBean> searchAllLowStockProducts(int threshold);
	 List<ProductBean> getAllLowStockProductsByType(int threshold, String type);
	 List<ProductBean> getUsedLowCostProducts(double maxCost);

	
	
	 List<ProductBean> getLeastSellingProducts();
	 List<ProductBean> getMostSellingProducts();

	double getSuggestedDiscount(double price, int amountSold);
	
}
