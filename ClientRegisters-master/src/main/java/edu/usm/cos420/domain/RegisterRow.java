package edu.usm.cos420.domain;

public class RegisterRow {
	int id;
	int registerID;
	int patientID;
	int parity;
	String maritalStatus;
	String method;
	
	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setRegisterID(int registerID) {
		this.registerID = registerID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getId()  {
		return id;
	}
	
	public void setId(int id)  {
		this.id = id;
	}
	
	public int getPatientID() {
		// TODO Auto-generated method stub
		return patientID;
	}

	public int getRegisterID() {
		// TODO Auto-generated method stub
		return registerID;
	}

	
	
}
