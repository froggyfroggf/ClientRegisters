package edu.usm.cos420.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.usm.cos420.domain.Register;
import edu.usm.cos420.domain.RegisterRow;
import edu.usm.cos420.domain.Result;

public class RegisterRowCloudSqlDao implements RegisterRowDao  {	
	String dbUrl;

	public RegisterRowCloudSqlDao(String dbUrl) throws SQLException {
		this.dbUrl = dbUrl;
		this.createRegisterRowTable();
	}

	/*
	 * 	private long entityId;

	Patient Specific
	private long NHISnum;
	private long clientCardNum;
	private String firstName;
	private String lastName;
	private int maritalStatus;
	private String gender;
	private String address;
	private int age;
	
	RegRow Specific
	private int numOfChildren;
	private boolean ageCircle;
	private boolean parity;
	
	 */
	public void createRegisterRowTable() throws SQLException {
		try(Connection conn = DriverManager.getConnection(this.dbUrl)){
			String createDbQuery =  "CREATE TABLE IF NOT EXISTS registerrows ( id SERIAL PRIMARY KEY, "
					+ "registerID int, patientID int, parity int, maritalStatus VARCHAR(255), method VARCHAR(255))";

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(createDbQuery);

			if(conn != null)
				conn.close();
		}
	}

	@Override
	public Long createRegisterRow(RegisterRow registerRow) throws SQLException {
		Long id = 0L;
		final String createRegisterString = "INSERT INTO registerrowss "
				+ "(registerID, patientID, parity, maritalStatus, method) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				final PreparedStatement createRegisterStmt = conn.prepareStatement(createRegisterString,
						Statement.RETURN_GENERATED_KEYS)) {
			createRegisterStmt.setInt(1, registerRow.getRegisterID());
			createRegisterStmt.setInt(2, registerRow.getPatientID());
			createRegisterStmt.setInt(3, registerRow.getParity());
			createRegisterStmt.setString(4, registerRow.getMaritalStatus());
			createRegisterStmt.setString(5, registerRow.getMethod());
			

			createRegisterStmt.executeUpdate();
			try (ResultSet keys = createRegisterStmt.getGeneratedKeys()) {
				keys.next();
				id = keys.getLong(1);
			}
		}
		
		return id;
	}

	@Override
	public RegisterRow readRegisterRow(Long registerRowId) throws SQLException {
		final String readRegisterRowString = "SELECT * FROM registers WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
		    PreparedStatement readRegisterStmt = conn.prepareStatement(readRegisterRowString)) {
			readRegisterStmt.setLong(1, registerRowId);
			try (ResultSet keys = readRegisterStmt.executeQuery()) {
				keys.next();
				RegisterRow registerRow = new RegisterRow();
				registerRow.setId(keys.getInt(1));
				registerRow.setRegisterID(keys.getInt(2));
				registerRow.setPatientID(keys.getInt(3));
				registerRow.setParity(keys.getInt(4));
				registerRow.setMaritalStatus(keys.getString(5));
				registerRow.setMethod(keys.getString(6));
				

				return registerRow;
			}
		}
	}

	@Override
	public void updateRegisterRow(RegisterRow register) throws SQLException {
		final String updateRegisterString = "UPDATE registers SET registerID = ?, patientID = ?, parity = ?, maritalStatus = ?, method = ? WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement updateRegisterStmt = conn.prepareStatement(updateRegisterString)) {
			updateRegisterStmt.setInt(1, register.getRegisterID());
			updateRegisterStmt.setInt(2, register.getPatientID());
			updateRegisterStmt.setInt(3, register.getParity());
			updateRegisterStmt.setString(4, register.getMaritalStatus());
			updateRegisterStmt.setString(5, register.getMethod());
			
			updateRegisterStmt.setLong(6, register.getId());
			updateRegisterStmt.executeUpdate();
		}

	}

	@Override
	public void deleteRegisterRow(Long registerId) throws SQLException {
		final String deleteRegisterString = "DELETE FROM registers WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement deleteRegisterStmt = conn.prepareStatement(deleteRegisterString)) {
			deleteRegisterStmt.setLong(1, registerId);
			deleteRegisterStmt.executeUpdate();
		}
	}

	@Override
	public Result<RegisterRow> listRegisterRows(String cursor) throws SQLException {
		int offset = 0;
		int totalNumRows = 0;
		if (cursor != null && !cursor.equals("")) {
			offset = Integer.parseInt(cursor);
		}
		
		final String listRegistersString = "SELECT id, registerID, patientID, parity, maritalStatus, method, count(*) OVER() AS total_count FROM registers ORDER BY year, facility ASC "
				+ "LIMIT 10 OFFSET ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				PreparedStatement listRegisterStmt = conn.prepareStatement(listRegistersString)) {
			listRegisterStmt.setInt(1, offset);
			List<RegisterRow> resultRegisters = new ArrayList<>();

			try (ResultSet rs = listRegisterStmt.executeQuery()) {
				while (rs.next()) {
					RegisterRow registerRow = new RegisterRow();
					registerRow.setId(rs.getInt(1));
					registerRow.setRegisterID(rs.getInt(2));
					registerRow.setPatientID(rs.getInt(3));
					registerRow.setParity(rs.getInt(4));
					registerRow.setMaritalStatus(rs.getString(5));
					registerRow.setMethod(rs.getString(6));
					

					resultRegisters.add(registerRow);

					totalNumRows = rs.getInt("total_count");
				}
			}

			if (totalNumRows > offset + 10) {
				return new Result<>(resultRegisters, Integer.toString(offset + 10));
			} else {
				return new Result<>(resultRegisters);
			}
		}
	}


}
