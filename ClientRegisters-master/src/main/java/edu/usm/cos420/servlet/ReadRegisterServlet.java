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
import edu.usm.cos420.domain.Register;


@WebServlet(urlPatterns = {"/read"})
public class ReadRegisterServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Long id = Long.decode(req.getParameter("id"));
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");

		RegisterDao dao = null;
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			Register register = dao.readRegister(id);
			req.setAttribute("register", register);
			req.setAttribute("page", "view");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error reading register", e);
		}
	}
}
