package edu.usm.cos420.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.usm.cos420.domain.Register;
import edu.usm.cos420.domain.Result;

public interface RegisterDao {
	Long createRegister(Register register) throws SQLException;

	Register readRegister(Long registertId) throws SQLException;

	void updateRegister(Register register) throws SQLException;

	void deleteRegister(Long registerId) throws SQLException;

	Result<Register> listRegisters(String startCursor) throws SQLException;

}
