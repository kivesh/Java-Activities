package com.newthinktank;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/*
 * Create Eclipse JEE Web Project
 * Help -> Welcome
 * Create a new JEE Web Project
 * 
 * Right Click JEETut -> New -> Servlet
 * 
 * Servlets make it easy to develop web based applications
 * that provide access to things such as databases.
 * 
 * Servlets run on the server and process data for dynamic web pages
 * Servlets are found under Deployment Descriptor/Servlets
 * 
 * The Document Root Directory is where all other files are stored
 * 
 * The WEB-INF Directory is where you store files that you don't want
 * to be accessible on the web. Data files, web.xml, Java classes, Servlets
 * 
 * JSP Apps use the MVC Pattern where each part should largely function
 * on its own so if one part changes it has little effect on the others
 * 1. Model : The Java code that accesses/processes the data 
 * 2. View : The interface that the user sees
 * 3. Controller : Serves as a communicator between the Model and View
 */
 
// Used to send HTML and XML to the client
import java.io.PrintWriter;
 
// Matches up the servlet with a URL
@WebServlet("/SampServlet")
 
//HttpServlet provides methods for handling Get and
//Post requests and manage required resources
public class SampServlet extends HttpServlet {
	// Used to maintain compatibility between different
	// versions of this class. It is also a way to make
	// sure code is using the same versions of different
	// classes
	// Serializing involves converting objects into a form
	// that can be stored or transmitted
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SampServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	// Used by servlet to handle Get requests
    // HttpServletRequest : Request from the user such
    // as form data
    // HttpServletResponse : Response sent back to the user
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		// Send all get requests to doPost
		doPost(request, response);
	}
 
	// Used by servlet to handle Post requests
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		// Defines that you want to send HTML to the browser
		// Must be set before creating the PrintWriter so
		// we know what type of data will be written
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		try {
			out.println("<h3>Testing Servlets</h3>");
		}
		finally {
			out.close();
		}
		
		// Setup Servers
		// 1. Click Servers tab at bottom of screen
		// 2. If it says No Servers are available
		// 	a. Click on that -> Select installed Tomcat version
		// 3. Right click on Server -> Add Remove -> Select JEETut
		// 4. Click Start the Server in bottom right hand corner
		// 	a. If you get the Server at localhost already in use error
		// 	   you need to shutdown Tomcat
		// 5. Make sure Servers/Tomcat.../server.xml file has 
		// <Context docBase="JEETut" path="/JEETut"... at bottom of file
		// 6. In browser http://localhost:8080/JEETut/SampServlet
		
	}
 
}
 
----- index.jsp -----
 
<!-- 
Working JSP Files
1. Right click -> New -> JSP File
2. Create ProcessInfo.java Servlet to handle this
--> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Testing JSP</title>
</head>
<body>
 
<h3>Enter some Info</h3>
 
<!-- SIMPLE EXAMPLE
<form action="ProcessInfo" method="post">
<label>Name : </label>
<input type="text" name="name">
<input type="submit" value="Send">
</form>
-->
 
<form action="ProcessInfo" method="post">
<label>First Name : </label>
<input type="text" name="fname"><br>
<label>Last Name : </label>
<input type="text" name="lname"><br>
<label>Phone : </label>
<input type="text" name="phone"><br>
<input type="submit" value="Send">
</form>
 
</body>
</html>
 
----- ProcessInfo.java -----
 
package com.newthinktank;
 
import java.io.IOException;
 
import java.sql.*;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class ProcessInfo
 */
@WebServlet("/ProcessInfo")
public class ProcessInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProcessInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Send all get requests to doPost
		doPost(request, response);
	}
 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// The URL to send data to (JSP FILE)
		String url = "/DisplayInfo.jsp";
		
		// SIMPLE EXAMPLE
		// Get the data entered on index.jsp
		// String usersName = request.getParameter("name");
		// request.setAttribute("usersName", usersName);
		
		// DATABASE EXAMPLE
		// Get the data entered on index.jsp
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		String phone = request.getParameter("phone");
		
		// Update the DB
		updateDB(fName, lName, phone);
		
		// Create object to pass to DisplayInfo.jsp
		Customer cust = new Customer(fName, lName, phone);
		request.setAttribute("cust", cust);
		
		// Forward data to DisplayInfo.jsp
		getServletContext()
			.getRequestDispatcher(url)
			.forward(request, response);
	}
	
	// Setup MySQL Connector
	// Copy mysql-connector-java-8.0.15.jar into 
	// /WebContent/WEB-INF/lib/
	
	/*
	 * SETUP DB
	 * mysql -u root -p
	 * UPDATE mysql.user SET Password=PASSWORD('NEWPW') 
	 * WHERE User='root';
	 * CREATE DATABASE test1;
	 * USE test1;
	 * CREATE TABLE customer(
	 * first_name VARCHAR(30) NOT NULL,
	 * last_name VARCHAR(30) NOT NULL,
	 * phone VARCHAR(20) NOT NULL,
	 * cust_id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY);
	 * CREATE USER 'dbadmin'@'localhost' IDENTIFIED BY 'turtledove';
	 * GRANT ALL PRIVILEGES ON test1.customer TO 
	 * 'dbadmin'@'localhost' IDENTIFIED BY 'turtledove';
	 */
	
	// Adds users to the DB
		protected void updateDB(String fName, String lName, String phone) {
			// Connects to the DB
			Connection con;
			
			try {
				// Everything needed to connect to the DB
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost/test1";
		        String user = "dbadmin";
		        String pw = "turtledove";
		        
		        // Used to issue queries to the DB
		        con = DriverManager.getConnection(url, user, pw);
		        
		        // Sends queries to the DB for results
		        Statement s = con.createStatement();
		        
		        // Add a new entry
		        String query = "INSERT INTO CUSTOMER " + 
		        "(first_name, last_name, phone, cust_id) " + 
		        "VALUES ('" + fName + "', '" + lName + "', '" +
		        phone + "', NULL)";
		        
		        // Execute the Query
		        s.executeUpdate(query);
		        
		        // Close DB connection
		        con.close();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
 
}
 
----- DisplayInfo.jsp -----
 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello</title>
</head>
<body>
<!-- BASIC EXAMPLE
Hello ${usersName}<br>
 -->
 <!-- DATABASE EXAMPLE -->
 <h3>Thank you for the Info</h3>
 <label>First Name : </label>
 ${cust.fName}<br>
 <label>Last Name : </label>
 ${cust.lName}<br>
 <label>Phone : </label>
 ${cust.phone}<br>
</body>
</html>
 
----- Customer.java -----
 
package com.newthinktank;
 
public class Customer {
	private String fName;
	private String lName;
	private String phone;
	
	public Customer() {
		this.fName = "";
		this.lName = "";
		this.phone = "";
	}
	
	public Customer(String fName, String lName, String phone) {
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
	}
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
 
}
