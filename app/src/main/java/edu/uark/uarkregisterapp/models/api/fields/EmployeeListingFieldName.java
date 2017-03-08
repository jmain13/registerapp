package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeListingFieldName implements FieldNameInterface {
	EMPLOYEES("employees");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	EmployeeListingFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
