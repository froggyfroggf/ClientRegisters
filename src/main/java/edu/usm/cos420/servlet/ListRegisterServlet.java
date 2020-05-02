package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterCloudSqlDao;
import edu.usm.cos420.dao.RegisterDao;
import edu.usm.cos420.domain.Register;
import edu.usm.cos420.domain.Result;

@WebServlet(urlPatterns = {"/list"})
public class ListRegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		//Get DB information
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		System.out.println("Database URL: " + dbUrl );
		RegisterDao dao = null;
		
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	
		String startCursor = req.getParameter("cursor");
		List<Register> registers = null;
		String endCursor = null;
		
		try {
			Result<Register> result = dao.listRegisters(startCursor);
			registers = result.result;
			endCursor = result.cursor;
		} catch (Exception e) {
			throw new ServletException("Error listing registers", e);
		}
		
		req.getSession().getServletContext().setAttribute("registers", registers);
		
		req.setAttribute("cursor", endCursor);
	    req.setAttribute("page", "list");

		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

}
