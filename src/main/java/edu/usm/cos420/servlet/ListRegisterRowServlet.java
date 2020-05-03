package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterRowCloudSqlDao;
import edu.usm.cos420.dao.RegisterRowDao;
import edu.usm.cos420.domain.RegisterRow;
import edu.usm.cos420.domain.Result;

@WebServlet(urlPatterns = {"/listRow"})
public class ListRegisterRowServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		//Get DB information
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		System.out.println("Database URL: " + dbUrl );
		RegisterRowDao dao = null;
		
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	
		String startCursor = req.getParameter("cursor");
		List<RegisterRow> registerRows = null;
		String endCursor = null;
		
		try {
			Result<RegisterRow> result = dao.listRegisterRows(startCursor);
			registerRows = result.result;
			endCursor = result.cursor;
		} catch (Exception e) {
			throw new ServletException("Error listing registerRows", e);
		}
		
		req.getSession().getServletContext().setAttribute("registerRows", registerRows);
		
		req.setAttribute("cursor", endCursor);
	    req.setAttribute("page", "listRow");

		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

}
