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

@WebServlet(urlPatterns = {"/createRow"})

public class CreateRegisterRowServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		req.setAttribute("action", "Add");          // Part of the Header in form.jsp
		req.setAttribute("destination", "createRow");  // The urlPattern to invoke (this Servlet)
		req.setAttribute("pageRow", "formRow");           // Tells base.jsp to include form.jsp
		req.getRequestDispatcher("/base.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
	IOException {
		RegisterRow registerRow = new RegisterRow();
		registerRow.setRegisterID(Integer.parseInt(req.getParameter("registerrow")));
		registerRow.setPatientID(Integer.parseInt(req.getParameter("patientid")));
		registerRow.setParity(Integer.parseInt(req.getParameter("parity")));
		registerRow.setMaritalStatus(req.getParameter("maritalstatus"));
		registerRow.setMethod(req.getParameter("method"));


		String dbUrl = this.getServletContext().getInitParameter("sql.urlRemote");

		RegisterRowDao dao = null;
		try {
			dao = new RegisterRowCloudSqlDao(dbUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Long id = dao.createRegisterRow(registerRow);
			resp.sendRedirect("/read?id=" + id.toString());   // read what we just wrote
		} catch (Exception e) {
			throw new ServletException("Error creating registerRow", e);
		}
	}
}
