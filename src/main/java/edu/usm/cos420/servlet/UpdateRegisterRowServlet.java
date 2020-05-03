package edu.usm.cos420.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.usm.cos420.dao.RegisterRowCloudSqlDao;
import edu.usm.cos420.dao.RegisterRowDao;
import edu.usm.cos420.domain.RegisterRow;


@WebServlet(urlPatterns = {"/updateRow"})
public class UpdateRegisterRowServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterRowDao dao = null;
		
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			RegisterRow registerRow = dao.readRegisterRow(Long.decode(req.getParameter("id")));
			req.setAttribute("registerRow", registerRow);
			req.setAttribute("action", "Edit");
			req.setAttribute("destination", "update");
			req.setAttribute("page", "formRow");
			req.getRequestDispatcher("/base.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException("Error loading registerRow for editing", e);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");
		RegisterRowDao dao = null;
		
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			RegisterRow registerRow = new RegisterRow();
			registerRow.setId(Integer.parseInt(req.getParameter("id")));
			registerRow.setPatientID(Integer.parseInt(req.getParameter("patientid")));
			registerRow.setRegisterID(Integer.parseInt(req.getParameter("registerid")));
			registerRow.setParity(Integer.parseInt(req.getParameter("parity")));
			registerRow.setMaritalStatus(req.getParameter("maritalstatus"));
			registerRow.setMethod(req.getParameter("method"));

					
			dao.updateRegisterRow(registerRow);
			resp.sendRedirect("/read?id=" + req.getParameter("id"));
		} catch (Exception e) {
			throw new ServletException("Error updating registerRow", e);
		}
	}
}
