package com.uttara.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        System.out.println("in constr of CS");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in doGet() of CS");
		process(request,response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in process() of CS");
		
		String uri = request.getRequestURI();
		
		System.out.println("uri = "+uri);
		
		RequestDispatcher rd = null;
		Model model = new Model();
		if(uri.contains("/openRegisterView.do"))
		{
			System.out.println("in uri.contains(/openRegisterView.do)");
			//forward to Register.jsp
			rd = request.getRequestDispatcher("Register.jsp");
			rd.forward(request, response);
			
		}
		if(uri.contains("/openLoginView.do"))
		{
			System.out.println("in uri.contains(/openLoginView.do)");
			//forward to Login.jsp
			rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
			
		}
		if(uri.contains("/register.do"))
		{
			System.out.println("in uri.contains(/register.do)");
			/*
			 * 1) get the bean from scope
			 * 2) invoke model and pass bean to register
			 * 3) if registration succeeds, forward to Success.jsp
			 * 4) if registration fails, forward to Register.jsp with error msg
			 */
			
			RegBean bean = (RegBean)request.getAttribute("reg");
			System.out.println("got bean from request scope = "+bean);
			System.out.println("going to invoke models register()");
			String result = model.register(bean);
			System.out.println("result = "+result);
			if(result.equals(Constants.SUCCESS))
			{
				//registration succeeded!
				//store msg to be displayed
				//forward to Success.jsp
				
				request.setAttribute("msg", "Your registration has succeeded!");
				
				rd = request.getRequestDispatcher("Success.jsp");
				rd.forward(request, response);
				
			}
			else
			{
				//registration failed!
				request.setAttribute("errorMsg", result);
				
				//forward to Register.jsp
				rd = request.getRequestDispatcher("Register.jsp");
				rd.forward(request, response);
			}
		}
		if(uri.contains("/login.do"))
		{
			/*
			 * 1) get the bean
			 * 2) pass bean to model method
			 * 3) depending success/failure, forward
			 */
			LoginBean bean = (LoginBean) request.getAttribute("log");
					
			String msg = model.authenticate(bean);
			if(msg.equals(Constants.SUCCESS))
			{
				/*
				 * user is authentic!
				 * create session for user and store his data
				 * 
				 */
				HttpSession session = request.getSession(true);
				session.setAttribute("user", bean.getEmail());
				
				// ask Model to get all contacts data!
				List<ContactBean> beans = model.getContacts(bean.getEmail());
				
				request.setAttribute("contacts", beans);
				
				rd = request.getRequestDispatcher("Menu.jsp");
				rd.forward(request, response);
			}
			else
			{
				request.setAttribute("errorMsg", msg);
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
				
			}

		}
		if(uri.contains("/openAddContactView.do"))
		{
			rd = request.getRequestDispatcher("AddContact.jsp");
			rd.forward(request, response);
		}
		if(uri.contains("/addContact.do"))
		{
			ContactBean con = (ContactBean) request.getAttribute("con");
			HttpSession session = request.getSession(false);
			if(session==null)
			{
				request.setAttribute("errorMsg", "We do not know who you are! Login again!!");
				rd = request.getRequestDispatcher("Login.jsp");
				rd.forward(request, response);
				return;
			}
			String email = (String) session.getAttribute("user");
			
			String emsg = model.addContact(con,email);
			if(emsg.equals(Constants.SUCCESS))
			{
				request.setAttribute("msg", "Contact added successfully!");
				rd = request.getRequestDispatcher("Success.jsp");
				rd.forward(request, response);
			}
			else
			{
				request.setAttribute("errorMsg", emsg);
				rd = request.getRequestDispatcher("AddContact.jsp");
				rd.forward(request, response);
			
			}
		}
		if(uri.contains("/logout.do"))
		{
			HttpSession session = request.getSession(false);
			if(session!=null)
			{
				session.removeAttribute("user");
				session.invalidate();
			}
			response.sendRedirect("HomePage.html");
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in doPost() of CS");
		process(request,response);
	}

}
