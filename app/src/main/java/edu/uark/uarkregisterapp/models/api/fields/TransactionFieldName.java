package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum TransactionFieldName implements FieldNameInterface {
	ID("id"),
	CASHIER_ID("cashierId"),
	TOTAL_QUANTITY("totalQuantity"),
	TOTAL_PRICE("totalPrice"),
	REFERENCE_ID("referenceId"),
	ENTRIES("entries"),
	API_REQUEST_STATUS("apiRequestStatus"),
	API_REQUEST_MESSAGE("apiRequestMessage"),
	CREATED_ON("createdOn");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	TransactionFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
