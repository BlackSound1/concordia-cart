package com.shashi.service.impl;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shashi.beans.DemandBean;
import com.shashi.beans.ProductBean;
import com.shashi.service.ProductService;
import com.shashi.utility.DBUtil;
import com.shashi.utility.IDUtil;
import com.shashi.utility.MailMessage;

public class ProductServiceImpl implements ProductService {

	@Override
	public String addProduct(String prodName, String prodType, String prodInfo, double prodPrice, int prodQuantity,
			InputStream prodImage, int amountSold, int discountPercent) {
		String status = null;
		String prodId = IDUtil.generateId();

		ProductBean product = new ProductBean(prodId, prodName, prodType, prodInfo, prodPrice, prodQuantity, prodImage, amountSold, discountPercent);

		status = addProduct(product);

		return status;
	}

	@Override
	public String addProduct(ProductBean product) {
		String status = "Product Registration Failed!";

		if (product.getProdId() == null)
			product.setProdId(IDUtil.generateId());

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into product values(?,?,?,?,?,?,?,?,?);");
			ps.setString(1, product.getProdId());
			ps.setString(2, product.getProdName());
			ps.setString(3, product.getProdType());
			ps.setString(4, product.getProdInfo());
			ps.setDouble(5, product.getProdPrice());
			ps.setInt(6, product.getProdQuantity());
			ps.setBlob(7, product.getProdImage());
			ps.setInt(8, product.getAmountSold());
			ps.setInt(10, product.getDiscountPercent());

			int k = ps.executeUpdate();

			if (k > 0) {

				status = "Product Added Successfully with Product Id: " + product.getProdId();

			} else {

				status = "Product Updation Failed!";
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return status;
	}

	@Override
	public String removeProduct(String prodId) {
		String status = "Product Removal Failed!";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try {
			ps = con.prepareStatement("delete from product where pid=?");
			ps.setString(1, prodId);

			int k = ps.executeUpdate();

			if (k > 0) {
				status = "Product Removed Successfully!";

				ps2 = con.prepareStatement("delete from usercart where prodid=?");

				ps2.setString(1, prodId);

				ps2.executeUpdate();

			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(ps2);

		return status;
	}

	@Override
	public String updateProduct(ProductBean prevProduct, ProductBean updatedProduct) {
		String status = "Product Updation Failed!";

		if (!prevProduct.getProdId().equals(updatedProduct.getProdId())) {

			status = "Both Products are Different, Updation Failed!";

			return status;
		}

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(
					"update product set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");

			ps.setString(1, updatedProduct.getProdName());
			ps.setString(2, updatedProduct.getProdType());
			ps.setString(3, updatedProduct.getProdInfo());
			ps.setDouble(4, updatedProduct.getProdPrice());
			ps.setInt(5, updatedProduct.getProdQuantity());
			ps.setBlob(6, updatedProduct.getProdImage());
			ps.setString(7, prevProduct.getProdId());

			int k = ps.executeUpdate();

			if (k > 0)
				status = "Product Updated Successfully!";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return status;
	}

	@Override
	public String updateProductPrice(String prodId, double updatedPrice) {
		String status = "Price Updation Failed!";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update product set pprice=? where pid=?");

			ps.setDouble(1, updatedPrice);
			ps.setString(2, prodId);

			int k = ps.executeUpdate();

			if (k > 0)
				status = "Price Updated Successfully!";
		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return status;
	}

	@Override
	public List<ProductBean> getAllProducts() {
		List<ProductBean> products = new ArrayList<ProductBean>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from product");

			rs = ps.executeQuery();

			while (rs.next()) {

				ProductBean product = new ProductBean();

				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));
				product.setAmountSold(rs.getInt(8));
				product.setDiscountPercent(rs.getInt(10));
				products.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return products;
	}

	@Override
	public List<ProductBean> getAllProductsByType(String type) {
		List<ProductBean> products = new ArrayList<ProductBean>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT * FROM `shopping-cart`.product where lower(ptype) like ?;");
			ps.setString(1, "%" + type + "%");
			rs = ps.executeQuery();

			while (rs.next()) {

				ProductBean product = new ProductBean();

				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));

				products.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return products;
	}

	@Override
	public List<ProductBean> searchAllProducts(String search) {
		List<ProductBean> products = new ArrayList<ProductBean>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"SELECT * FROM `shopping-cart`.product where lower(ptype) like ? or lower(pname) like ? or lower(pinfo) like ?");
			search = "%" + search + "%";
			ps.setString(1, search);
			ps.setString(2, search);
			ps.setString(3, search);
			rs = ps.executeQuery();

			while (rs.next()) {

				ProductBean product = new ProductBean();

				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));

				products.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return products;
	}

	@Override
	public byte[] getImage(String prodId) {
		byte[] image = null;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select image from product where  pid=?");

			ps.setString(1, prodId);

			rs = ps.executeQuery();

			if (rs.next())
				image = rs.getBytes("image");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return image;
	}

	@Override
	public ProductBean getProductDetails(String prodId) {
		ProductBean product = null;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from product where pid=?");

			ps.setString(1, prodId);
			rs = ps.executeQuery();

			if (rs.next()) {
				product = new ProductBean();
				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));
				product.setDiscountPercent(rs.getInt(10));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return product;
	}

	@Override
	public String updateProductWithoutImage(String prevProductId, ProductBean updatedProduct) {
		String status = "Product Updation Failed!";

		if (!prevProductId.equals(updatedProduct.getProdId())) {

			status = "Both Products are Different, Updation Failed!";

			return status;
		}

		int prevQuantity = new ProductServiceImpl().getProductQuantity(prevProductId);
		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update product set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,discountPercent=? where pid=?");

			ps.setString(1, updatedProduct.getProdName());
			ps.setString(2, updatedProduct.getProdType());
			ps.setString(3, updatedProduct.getProdInfo());
			ps.setDouble(4, updatedProduct.getProdPrice());
			ps.setInt(5, updatedProduct.getProdQuantity());
			ps.setInt(6, updatedProduct.getDiscountPercent());
			ps.setString(7, prevProductId);

			int k = ps.executeUpdate();
			// System.out.println("prevQuantity: "+prevQuantity);
			if ((k > 0) && (prevQuantity < updatedProduct.getProdQuantity())) {
				status = "Product Updated Successfully!";
				// System.out.println("updated!");
				List<DemandBean> demandList = new DemandServiceImpl().haveDemanded(prevProductId);

				for (DemandBean demand : demandList) {

					String userFName = new UserServiceImpl().getFName(demand.getUserName());
					try {
						MailMessage.productAvailableNow(demand.getUserName(), userFName, updatedProduct.getProdName(),
								prevProductId);
					} catch (Exception e) {
						System.out.println("Mail Sending Failed: " + e.getMessage());
					}
					boolean flag = new DemandServiceImpl().removeProduct(demand.getUserName(), prevProductId);

					if (flag)
						status += " And Mail Send to the customers who were waiting for this product!";
				}
			} else if (k > 0)
				status = "Product Updated Successfully!";
			else
				status = "Product Not available in the store!";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		// System.out.println("Prod Update status : "+status);

		return status;
	}

	@Override
	public double getProductPrice(String prodId) {
		double price = 0;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from product where pid=?");

			ps.setString(1, prodId);
			rs = ps.executeQuery();

			if (rs.next()) {
				price = rs.getDouble("pprice");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return price;
	}
	
	@Override
	public double getProductDiscountPercent(String prodId) {
		double price = 0;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from product where pid=?");

			ps.setString(1, prodId);
			rs = ps.executeQuery();

			if (rs.next()) {
				price = rs.getInt("discountPercent");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return price;
	}

	@Override
	public boolean sellNProduct(String prodId, int n) {
		boolean flag = false;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("update product set pquantity=(pquantity - ?) where pid=?");

			ps.setInt(1, n);

			ps.setString(2, prodId);

			int k = ps.executeUpdate();

			if (k > 0)
				flag = true;
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return flag;
	}

	@Override
	public int getProductQuantity(String prodId) {

		int quantity = 0;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from product where pid=?");

			ps.setString(1, prodId);
			rs = ps.executeQuery();

			if (rs.next()) {
				quantity = rs.getInt("pquantity");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);

		return quantity;
	}
	
	// Added by Johnny
	public List<ProductBean> searchAllLowStockProducts(int threshold) {
	    List<ProductBean> lowStockProducts = new ArrayList<>();
	    Connection con = DBUtil.provideConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        // Preparing and executing the SQL query
	        ps = con.prepareStatement("SELECT * FROM product WHERE pquantity <= ?");
	        ps.setInt(1, threshold);
	        rs = ps.executeQuery();
	        
	        System.out.println("------------searchAllLowStockProducts()------------");
	        while (rs.next()) {
	            ProductBean product = new ProductBean();
	           
	            // Set product properties from ResultSet
	            product.setProdId(rs.getString("pid")); // 'pid'
	            product.setProdName(rs.getString("pname")); // 'pname'
	            product.setProdType(rs.getString("ptype")); // 'ptype'
	            product.setProdInfo(rs.getString("pinfo")); // 'pinfo'
	            product.setProdPrice(rs.getDouble("pprice")); // 'pprice'
	            product.setProdQuantity(rs.getInt("pquantity")); // 'pquantity'

	            // Handling the product image
	            product.setProdImage(rs.getBinaryStream("image"));
	            System.out.println("Product found: Name = "+product.getProdName()+ ", ID = " + product.getProdId() + ", Quantity = " + product.getProdQuantity());
	            lowStockProducts.add(product);
	        }
	        
	        System.out.println("Number of low stock products found: " + lowStockProducts.size()+"\n\n");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return lowStockProducts;
	}

	// Added by Johnny
	public List<ProductBean> getAllLowStockProductsByType(int threshold, String type) {
	    List<ProductBean> lowStockProducts = new ArrayList<>();
	    Connection con = DBUtil.provideConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT * FROM product WHERE pquantity <= ? AND ptype = ?";
	        ps = con.prepareStatement(query);
	        ps.setInt(1, threshold);
	        ps.setString(2, type);
	        rs = ps.executeQuery();
	        
	        System.out.println("------------getAllLowStockProductsByType()------------");
	        while (rs.next()) {
	            ProductBean product = new ProductBean();
	            product.setProdId(rs.getString("pid")); // 'pid'
	            product.setProdName(rs.getString("pname")); // 'pname'
	            product.setProdType(rs.getString("ptype")); // 'ptype'
	            product.setProdInfo(rs.getString("pinfo")); // 'pinfo'
	            product.setProdPrice(rs.getDouble("pprice")); // 'pprice'
	            product.setProdQuantity(rs.getInt("pquantity")); // 'pquantity'

	            // Handling the product image
	            product.setProdImage(rs.getBinaryStream("image"));
	            System.out.println("Product found: Name = "+product.getProdName() + ", ID = " + product.getProdId() + ", Quantity = " + product.getProdQuantity() + ", Type = " + product.getProdType());
	            System.out.println();
	            lowStockProducts.add(product);
	        }
	        
	        System.out.println("Number of low stock products by type found: " + lowStockProducts.size()+"\n\n");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	 try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
	    }

	    return lowStockProducts;
	}
	
	public List<ProductBean> getUsedLowCostProducts(double maxCost) {
	    List<ProductBean> products = new ArrayList<>();
	    Connection con = DBUtil.provideConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        String query = "SELECT * FROM product WHERE isUsed = true AND pprice <= ?";
	        ps = con.prepareStatement(query);
	        ps.setDouble(1, maxCost);
	        rs = ps.executeQuery();
	        System.out.println("------------getUsedLowCostProducts()------------");
	        while (rs.next()) {
	            ProductBean product = new ProductBean();
	            product.setProdId(rs.getString("pid")); // 'pid'
	            product.setProdName(rs.getString("pname")); // 'pname'
	            product.setProdType(rs.getString("ptype")); // 'ptype'
	            product.setProdInfo(rs.getString("pinfo")); // 'pinfo'
	            product.setProdPrice(rs.getDouble("pprice")); // 'pprice'
	            product.setProdQuantity(rs.getInt("pquantity")); // 'pquantity'
	            product.setUsed(rs.getBoolean("isUsed")); // 'isUsd'

	            // Handling the product image
	            product.setProdImage(rs.getBinaryStream("image"));
	            
	            
	            System.out.println("Product found: Name = "+product.getProdName() + ", ID = " + product.getProdId() 
	            					+ ", Quantity = " + product.getProdQuantity() + ", Type = " + product.getProdType()
	            					+ ", Used = " + product.getUsed());
	      
	            products.add(product);
	        }
	        
	        System.out.println("Number of low stock products by type found: " + products.size()+"\n\n");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	 try {
		            if (rs != null) rs.close();
		            if (ps != null) ps.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
	    }

	    return products;
	}

	@Override
	public List<ProductBean> getLeastSellingProducts() {
		List<ProductBean> products = new ArrayList<ProductBean>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = con.prepareStatement("SELECT * FROM product WHERE amountSold <= (SELECT 0.3*AVG(amountSold) FROM product)");

			rs = ps.executeQuery();

			while (rs.next()) {

				ProductBean product = new ProductBean();

				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));

				products.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return products;
	}

	@Override
	public List<ProductBean> getMostSellingProducts() {
		List<ProductBean> products = new ArrayList<ProductBean>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		int threshold = 0;
		try {
			ps = con.prepareStatement("SELECT * FROM product WHERE amountSold >= (SELECT 1.3*AVG(amountSold) FROM product)");

			
			rs = ps.executeQuery();

			while (rs.next()) {

				ProductBean product = new ProductBean();

				product.setProdId(rs.getString(1));
				product.setProdName(rs.getString(2));
				product.setProdType(rs.getString(3));
				product.setProdInfo(rs.getString(4));
				product.setProdPrice(rs.getDouble(5));
				product.setProdQuantity(rs.getInt(6));
				product.setProdImage(rs.getAsciiStream(7));
				
				products.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtil.closeConnection(con);
		DBUtil.closeConnection(ps);
		DBUtil.closeConnection(rs);

		return products;
	}


	public double getSuggestedDiscount(double price, int amountSold) {
				
		return discountStrategy(price, amountSold);
	}

	@Override
	public double discountStrategy(double price, int amountSold) {
		double min = 0.1;
		double max = 0.7;
		double increment = 0.05;
		
		double discount = increment * amountSold;
		double totalDiscount = Math.min(discount, max) * 100;

		return Math.round(totalDiscount);
	}


	
}
