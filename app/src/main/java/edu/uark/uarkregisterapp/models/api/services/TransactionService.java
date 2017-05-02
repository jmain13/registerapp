package edu.uark.uarkregisterapp.models.api.services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.Transaction;
import edu.uark.uarkregisterapp.models.api.TransactionEntry;
import edu.uark.uarkregisterapp.models.api.enums.ApiLevel;
import edu.uark.uarkregisterapp.models.api.enums.TransactionApiMethod;
import edu.uark.uarkregisterapp.models.api.enums.TransactionApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class TransactionService extends BaseRemoteService {
	public Transaction getTransaction(UUID transactionEntryId) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { TransactionApiMethod.TRANSACTION, ApiLevel.ONE }), transactionEntryId
		);

		if (rawJsonObject != null) {
			return (new Transaction()).loadFromJson(rawJsonObject);
		} else {
			return new Transaction().setApiRequestStatus(TransactionApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public List<TransactionEntry> getTransactionEntries() {
		List<TransactionEntry> activities;
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { TransactionApiMethod.TRANSACTION, ApiLevel.ONE, TransactionApiMethod.TRANSACTIONS })
		);

		if (rawJsonObject != null) {
			activities = (new Transaction()).loadFromJson(rawJsonObject).getTransactionEntries();
		} else {
			activities = new ArrayList<>(0);
		}

		return activities;
	}

	public Transaction putTransaction(Transaction transaction) {
		JSONObject rawJsonObject = this.putSingle(
			(new PathElementInterface[]{ TransactionApiMethod.TRANSACTION, ApiLevel.ONE }), transaction.convertToJson()
		);

		if (rawJsonObject != null) {
			return (new Transaction()).loadFromJson(rawJsonObject);
		} else {
			return new Transaction().setApiRequestStatus(TransactionApiRequestStatus.UNKNOWN_ERROR);
		}
	}
}
