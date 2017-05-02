package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum TransactionEntryFieldName implements FieldNameInterface {
	ID("id"),
	FROM_TRANSACTION("fromTransaction"),
	LOOKUP_CODE("lookupCode"),
	QUANTITY("quantity"),
	PRICE("price"),
	API_REQUEST_STATUS("apiRequestStatus"),
	API_REQUEST_MESSAGE("apiRequestMessage"),
	CREATED_ON("createdOn");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	TransactionEntryFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
