package com.talentsprint.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talentsprint.beans.CabBean;
import com.talentsprint.beans.RideBean;
import com.talentsprint.dbconnection.RideDAO;
import com.talentsprint.dbconnection.RideStatusChecker;

/**
 * Servlet implementation class RideController
 */
@WebServlet("/RideController")
public class RideController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RideController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in RideController");
		HttpSession session = request.getSession();
		RideBean rideBean = new RideBean();
		rideBean.setSource((String)request.getParameter("source"));
		System.out.println(request.getParameter("source"));
		rideBean.setDestination((String)request.getParameter("destination"));
		String customerID = (String) (session.getAttribute("customerID"));
		rideBean.setCustomerId(customerID);
		System.out.println(request.getParameter("costEstimate"));
		rideBean.setAmount(Double.parseDouble(request.getParameter("costEstimate")));
		rideBean.setStatus("Waiting");
		String val = request.getParameter("type");
		session.setAttribute("RideBean", rideBean);
		System.out.println(val);
		String type = null;
			if(val.equals("6")){
				type = "Micro";
			}if(val.equals("8")){
				type = "Mini";
			}if(val.equals("18")){
				type ="SUV";
			}
			System.out.println(type);
		rideBean.setCarType(type);
		try {
			
			int flag = RideDAO.bookARide(rideBean);
			System.out.println(flag);
			if(flag != -1){
				System.out.println(flag);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoadingPage.jsp");
				requestDispatcher.forward(request, response);
			}else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("HomePage.jsp");
				requestDispatcher.forward(request, response);
			}
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
