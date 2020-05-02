package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterCloudSqlDao;
import edu.usm.cos420.dao.RegisterDao;
import edu.usm.cos420.domain.Register;

@WebServlet(urlPatterns = {"/create"})

public class CreateRegisterServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		req.setAttribute("action", "Add");          // Part of the Header in form.jsp
		req.setAttribute("destination", "create");  // The urlPattern to invoke (this Servlet)
		req.setAttribute("page", "form");           // Tells base.jsp to include form.jsp
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		Register register = new Register();
		register.setYear(req.getParameter("year"));
		register.setFacility(req.getParameter("facility"));
		register.setSubdistrict(req.getParameter("subdistrict"));
		register.setDistrict(req.getParameter("district"));


		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");

		RegisterDao dao = null;
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Long id = dao.createRegister(register);
			resp.sendRedirect("/read?id=" + id.toString());   // read what we just wrote
		} catch (Exception e) {
			throw new ServletException("Error creating register", e);
		}
	}
}
