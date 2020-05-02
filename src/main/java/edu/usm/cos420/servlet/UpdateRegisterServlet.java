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


@WebServlet(urlPatterns = {"/update"})
public class UpdateRegisterServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterDao dao = null;
		
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			Register register = dao.readRegister(Long.decode(req.getParameter("id")));
			req.setAttribute("register", register);
			req.setAttribute("action", "Edit");
			req.setAttribute("destination", "update");
			req.setAttribute("page", "form");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error loading register for editing", e);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterDao dao = null;
		
		try {
			dao = new RegisterCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			Register register = new Register();
			register.setId(Integer.parseInt(req.getParameter("id")));
			register.setYear(req.getParameter("year"));
			register.setFacility(req.getParameter("facility"));
			register.setSubdistrict(req.getParameter("subdistrict"));
			register.setDistrict(req.getParameter("district"));

					
			dao.updateRegister(register);
			resp.sendRedirect("/read?id=" + req.getParameter("id"));
		} catch (Exception e) {
			throw new ServletException("Error updating register", e);
		}
	}
}
