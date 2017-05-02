package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum TransactionListingFieldName implements FieldNameInterface {
	TRANSACTIONS("transactions");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	TransactionListingFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
