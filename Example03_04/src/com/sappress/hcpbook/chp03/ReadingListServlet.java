package com.sappress.hcpbook.chp03;

import java.io.IOException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ReadingListServlet")
public class ReadingListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
//Konstruktor
    public ReadingListServlet() {
        super();
    }
    @EJB
    ReadingListManagerBeanLocal readingListManager;
//Methode doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
//Methode doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Den EJB-List-Manager aufrufen:
		ReadingListManagerBeanLocal readingListManager = getListManager(request.getSession());
		
		//Den ausgewählten Titel zur Liste hinzufuegen:
		String title = request.getParameter("title");
		if((title!=null)&&(! title.equals("")))
			readingListManager.addTitle(title);
		
		//Benutzer zur Hauptseite der Leseliste umleiten:
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);

	}
	// -----------------
	private ReadingListManagerBeanLocal getListManager(HttpSession session)
	  {
	    // Access/create the reading list manager EJB from the session context:   
	    ReadingListManagerBeanLocal readingListManager = null;
	    readingListManager = (ReadingListManagerBeanLocal) session.getAttribute("readingListManager");
	    if (readingListManager == null)
	    {
	      try
	      {
	        // If the EJB hasn't been created yet, go ahead and create it:
	        InitialContext ctx = new InitialContext();
	        readingListManager = (ReadingListManagerBeanLocal) ctx.lookup("java:comp/env/ejb/ReadingListManager");
	        
	        /*readingListManager.addTitle("OData and SAP NetWeaver Gateway");
	        readingListManager.addTitle("SAP HANA: An Introduction");
	        readingListManager.addTitle("SuccessFactors with SAP ERP HCM");*/
	        
	        session.setAttribute("readingListManager", readingListManager);
	      }
	      catch (NamingException ne)
	      {
	        ne.printStackTrace();
	      }
	    }
	    
	    return readingListManager;
	  }

}