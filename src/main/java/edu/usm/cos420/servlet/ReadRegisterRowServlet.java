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
import edu.usm.cos420.domain.RegisterRow;


@WebServlet(urlPatterns = {"/readRow"})
public class ReadRegisterRowServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Long id = Long.decode(req.getParameter("id"));
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");

		RegisterRowDao dao = null;
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			RegisterRow registerRow = dao.readRegisterRow(id);
			req.setAttribute("registerRow", registerRow);
			req.setAttribute("page", "viewRow");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error reading registerRow", e);
		}
	}
}
