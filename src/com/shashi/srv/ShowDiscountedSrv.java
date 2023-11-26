package com.shashi.srv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shashi.service.impl.ProductServiceImpl;

@WebServlet("/ShowDiscountedSrv")
public class ShowDiscountedSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowDiscountedSrv() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userType = (String) session.getAttribute("usertype");
		String userName = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");

		if (userType == null || !userType.equals("admin")) {

			response.sendRedirect("login.jsp?message=Access Denied, Login As Admin!!");

		}

		else if (userName == null || password == null) {

			response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
		}

		// login checked

		ProductServiceImpl product = new ProductServiceImpl();
		List<ProductBean> discProductList = product.getDiscountedProducts(prodId);
	    	
		String status = "Discounted products found!";
	    	if(discProductList.isEmpty()) {
	    	  status = "Discounted products NOT found!";
	 	}
				
		RequestDispatcher rd = request.getRequestDispatcher("getDiscountedProducts.jsp?message=" + status);
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
