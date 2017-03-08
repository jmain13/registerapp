package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeFieldName implements FieldNameInterface {
		ID("id"),
		FIRST_NAME("firstName"),
		LAST_NAME("lastName"),
		PASSWORD("password"),
		API_REQUEST_STATUS("apiRequestStatus"),
		API_REQUEST_MESSAGE("apiRequestMessage"),
		CREATED_ON("createdOn");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	EmployeeFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
