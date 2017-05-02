package edu.uark.uarkregisterapp.models.api.enums;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public enum TransactionEntryApiRequestStatus {
	OK(0),
	INVALID_INPUT(1),
	UNKNOWN_ERROR(2),
	NOT_FOUND(3),
	LOOKUP_CODE_ALREADY_EXISTS(4);

	public int getValue() {
		return value;
	}

	public static TransactionEntryApiRequestStatus mapValue(int key) {
		if (valueMap == null) {
			valueMap = new SparseArray<>();

			for (TransactionEntryApiRequestStatus status : TransactionEntryApiRequestStatus.values()) {
				valueMap.put(status.getValue(), status);
			}
		}

		return ((valueMap.indexOfKey(key) >= 0) ? valueMap.get(key) : TransactionEntryApiRequestStatus.UNKNOWN_ERROR);
	}

	public static TransactionEntryApiRequestStatus mapName(String name) {
		if (nameMap == null) {
			nameMap = new HashMap<>();

			for (TransactionEntryApiRequestStatus status : TransactionEntryApiRequestStatus.values()) {
				nameMap.put(status.name(), status);
			}
		}

		return (nameMap.containsKey(name) ? nameMap.get(name) : TransactionEntryApiRequestStatus.UNKNOWN_ERROR);
	}

	private int value;

	private static Map<String, TransactionEntryApiRequestStatus> nameMap = null;
	private static SparseArray<TransactionEntryApiRequestStatus> valueMap = null;

	private TransactionEntryApiRequestStatus(int value) {
		this.value = value;
	}
}
