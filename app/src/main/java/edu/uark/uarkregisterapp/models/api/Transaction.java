package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.TransactionEntryFieldName;
import edu.uark.uarkregisterapp.models.api.fields.TransactionFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;

public class Transaction implements LoadFromJsonInterface<Transaction> {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Transaction setId(UUID id) {
		this.id = id;
		return this;
	}

	private List<TransactionEntry> entries;
	public List<TransactionEntry> getTransactionEntries() {
		return this.entries;
	}
	public Transaction setTransactionEntries(List<TransactionEntry> entries) {
		this.entries = entries;
		return this;
	}
	public Transaction addTransactionEntry(TransactionEntry entry) {
		this.entries.add(entry);
		return this;
	}

	@Override
	public Transaction loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(TransactionFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		JSONArray jsonActivities = rawJsonObject.optJSONArray(TransactionFieldName.ENTRIES.getFieldName());

		if (jsonActivities != null) {
			try {
				for (int i = 0; i < jsonActivities.length(); i++) {
					JSONObject jsonActivity = jsonActivities.getJSONObject(i);

					this.entries.add((new TransactionEntry()).loadFromJson(jsonActivity));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	public Transaction() {
		this.entries = new LinkedList<TransactionEntry>();
	}
}
