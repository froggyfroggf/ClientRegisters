package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterRowCloudSqlDao;
import edu.usm.cos420.dao.RegisterRowDao;

@WebServlet(urlPatterns = {"/deleteRow"})
public class DeleteRegisterRowServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.decode(req.getParameter("id"));
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterRowDao dao = null;
		
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			dao.deleteRegisterRow(id);
			resp.sendRedirect("/listRow");
		} catch (Exception e) {
			throw new ServletException("Error deleting registerRow", e);
		}
	}
}
