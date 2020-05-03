package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterCloudSqlDao;
import edu.usm.cos420.dao.RegisterDao;

@WebServlet(urlPatterns = {"/delete"})
public class DeleteRegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.decode(req.getParameter("id"));
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterDao dao = null;
		
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			dao.deleteRegister(id);
			resp.sendRedirect("/list");
		} catch (Exception e) {
			throw new ServletException("Error deleting register", e);
		}
	}
}
