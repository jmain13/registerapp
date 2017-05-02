package edu.uark.uarkregisterapp.models.api.services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.Transaction;
import edu.uark.uarkregisterapp.models.api.enums.ApiLevel;
import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiMethod;
import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class TransactionEntryService extends BaseRemoteService {
	public TransactionEntry getTransactionEntry(UUID transactionEntryId) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { TransactionEntryApiMethod.ENTRY, ApiLevel.ONE }), transactionEntryId
		);

		if (rawJsonObject != null) {
			return (new TransactionEntry()).loadFromJson(rawJsonObject);
		} else {
			return new TransactionEntry().setApiRequestStatus(TransactionEntryApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public TransactionEntry getTransactionEntryByLookupCode(String transactionEntryLookupCode) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { TransactionEntryApiMethod.ENTRY, ApiLevel.ONE, TransactionEntryApiMethod.BY_LOOKUP_CODE }), transactionEntryLookupCode
		);

		if (rawJsonObject != null) {
			return (new TransactionEntry()).loadFromJson(rawJsonObject);
		} else {
			return new TransactionEntry().setApiRequestStatus(TransactionEntryApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public List<TransactionEntry> getTransactionEntries() {
		List<TransactionEntry> activities;
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { TransactionEntryApiMethod.ENTRY, ApiLevel.ONE, TransactionEntryApiMethod.ENTRIES })
		);

		if (rawJsonObject != null) {
			activities = (new Transaction()).loadFromJson(rawJsonObject).getTransactionEntries();
		} else {
			activities = new ArrayList<>(0);
		}

		return activities;
	}

	public TransactionEntry putTransactionEntry(TransactionEntry transactionEntry) {
		JSONObject rawJsonObject = this.putSingle(
			(new PathElementInterface[]{ TransactionEntryApiMethod.ENTRY, ApiLevel.ONE }), transactionEntry.convertToJson()
		);

		if (rawJsonObject != null) {
			return (new TransactionEntry()).loadFromJson(rawJsonObject);
		} else {
			return new TransactionEntry().setApiRequestStatus(TransactionEntryApiRequestStatus.UNKNOWN_ERROR);
		}
	}
}
