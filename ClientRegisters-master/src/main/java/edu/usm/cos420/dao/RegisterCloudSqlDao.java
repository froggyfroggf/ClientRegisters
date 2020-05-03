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
import edu.usm.cos420.domain.Result;

public class RegisterCloudSqlDao implements RegisterDao  {	
	String dbUrl;

	public RegisterCloudSqlDao(String dbUrl) throws SQLException {
		this.dbUrl = dbUrl;
		this.createRegisterTable();
	}

	public void createRegisterTable() throws SQLException {
		try(Connection conn = DriverManager.getConnection(this.dbUrl)){
			String createDbQuery =  "CREATE TABLE IF NOT EXISTS registers ( id SERIAL PRIMARY KEY, "
					+ "year VARCHAR(255), facility VARCHAR(255), subdistrict VARCHAR(255), district VARCHAR(255) )";

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(createDbQuery);

			if(conn != null)
				conn.close();
		}
	}

	@Override
	public Long createRegister(Register register) throws SQLException {
		Long id = 0L;
		final String createRegisterString = "INSERT INTO registers "
				+ "(year, facility, subdistrict, district) "
				+ "VALUES (?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				final PreparedStatement createRegisterStmt = conn.prepareStatement(createRegisterString,
						Statement.RETURN_GENERATED_KEYS)) {
			createRegisterStmt.setString(1, register.getYear());
			createRegisterStmt.setString(2, register.getFacility());
			createRegisterStmt.setString(3, register.getSubdistrict());
			createRegisterStmt.setString(4, register.getDistrict());
			

			createRegisterStmt.executeUpdate();
			try (ResultSet keys = createRegisterStmt.getGeneratedKeys()) {
				keys.next();
				id = keys.getLong(1);
			}
		}
		
		return id;
	}

	@Override
	public Register readRegister(Long registerId) throws SQLException {
		final String readRegisterString = "SELECT * FROM registers WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
		    PreparedStatement readRegisterStmt = conn.prepareStatement(readRegisterString)) {
			readRegisterStmt.setLong(1, registerId);
			try (ResultSet keys = readRegisterStmt.executeQuery()) {
				keys.next();
				Register register = new Register();
				register.setId(keys.getInt(1));
				register.setYear(keys.getString(2));
				register.setFacility(keys.getString(3));
				register.setSubdistrict(keys.getString(4));
				register.setDistrict(keys.getString(5));	
				

				return register;
			}
		}
	}

	@Override
	public void updateRegister(Register register) throws SQLException {
		final String updateRegisterString = "UPDATE registers SET year = ?, facility = ?, subdistrict = ?, district = ? WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement updateRegisterStmt = conn.prepareStatement(updateRegisterString)) {
			updateRegisterStmt.setString(1, register.getYear());
			updateRegisterStmt.setString(2, register.getFacility());
			updateRegisterStmt.setString(3, register.getSubdistrict());
			updateRegisterStmt.setString(4, register.getDistrict());
			
			updateRegisterStmt.setLong(6, register.getId());
			updateRegisterStmt.executeUpdate();
		}

	}

	@Override
	public void deleteRegister(Long registerId) throws SQLException {
		final String deleteRegisterString = "DELETE FROM registers WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
			PreparedStatement deleteRegisterStmt = conn.prepareStatement(deleteRegisterString)) {
			deleteRegisterStmt.setLong(1, registerId);
			deleteRegisterStmt.executeUpdate();
		}
	}

	@Override
	public Result<Register> listRegisters(String cursor) throws SQLException {
		int offset = 0;
		int totalNumRows = 0;
		if (cursor != null && !cursor.equals("")) {
			offset = Integer.parseInt(cursor);
		}
		
		final String listRegistersString = "SELECT id, year, facility, subdistrict, district, count(*) OVER() AS total_count FROM registers ORDER BY year, facility ASC "
				+ "LIMIT 10 OFFSET ?";
		try (Connection conn = DriverManager.getConnection(this.dbUrl);
				PreparedStatement listRegisterStmt = conn.prepareStatement(listRegistersString)) {
			listRegisterStmt.setInt(1, offset);
			List<Register> resultRegisters = new ArrayList<>();

			try (ResultSet rs = listRegisterStmt.executeQuery()) {
				while (rs.next()) {
					Register register = new Register();
					register.setId(rs.getInt(1));
					register.setYear(rs.getString(2));
					register.setFacility(rs.getString(3));
					register.setSubdistrict(rs.getString(4));
					register.setDistrict(rs.getString(5));
					

					resultRegisters.add(register);

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
