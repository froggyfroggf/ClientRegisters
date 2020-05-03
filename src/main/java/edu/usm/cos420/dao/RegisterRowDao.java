package edu.usm.cos420.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.usm.cos420.domain.Register;
import edu.usm.cos420.domain.RegisterRow;
import edu.usm.cos420.domain.Result;

public interface RegisterRowDao {
	Long createRegisterRow(RegisterRow registerRow) throws SQLException;

	RegisterRow readRegisterRow(Long registerRowId) throws SQLException;

	void updateRegisterRow(RegisterRow registerRow) throws SQLException;

	void deleteRegisterRow(Long registerRowId) throws SQLException;

	Result<RegisterRow> listRegisterRows(String startCursor) throws SQLException;

}
