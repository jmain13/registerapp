package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum TransactionFieldName implements FieldNameInterface {
	ID("id"),
	ENTRIES("entries");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	TransactionFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
