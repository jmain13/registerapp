package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.TransactionApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.fields.TransactionFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;

import edu.uark.uarkregisterapp.models.transition.TransactionTransition;

public class Transaction implements Serializable, ConvertToJsonInterface, LoadFromJsonInterface<Transaction> {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Transaction setId(UUID id) {
		this.id = id;
		return this;
	}

	private UUID cashierId;
	public UUID getCashierId() {
		return this.cashierId;
	}
	public Transaction setCashierId(UUID cashierId) {
		this.cashierId = cashierId;
		return this;
	}

	private int totalQuantity;
	public int getTotalQuantity() { return this.totalQuantity; }
	public Transaction setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
		return this;
	}

	private double totalPrice;
	public double getTotalPrice() { return this.totalPrice; }
	public Transaction setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	/*
	private TransactionClassification transactionType;
	public TransactionClassification getTransactionType() { return this.transactionType; }
	public Transaction setTransactionType(TransactionClassification transactionType) {
		this.transactionType = transactionType;
		return this;
	}*/

	private UUID referenceId;
	public UUID getReferenceId() {
		return this.referenceId;
	}
	public Transaction setReferenceId(UUID referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public Transaction setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	private ArrayList<TransactionEntry> entries;
	public ArrayList<TransactionEntry> getTransactionEntries() {
		return this.entries;
	}
	public Transaction setTransactionEntries(ArrayList<TransactionEntry> entries) {
		this.entries = entries;
		return this;
	}
	public Transaction addTransactionEntry(TransactionEntry entry) {
		this.entries.add(entry);
		return this;
	}

	private TransactionApiRequestStatus apiRequestStatus;
	public TransactionApiRequestStatus getApiRequestStatus() {
		return this.apiRequestStatus;
	}
	public Transaction setApiRequestStatus(TransactionApiRequestStatus apiRequestStatus) {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus;
		}

		return this;
	}

	private String apiRequestMessage;
	public String getApiRequestMessage() {
		return this.apiRequestMessage;
	}
	public Transaction setApiRequestMessage(String apiRequestMessage) {
		if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
			this.apiRequestMessage = apiRequestMessage;
		}

		return this;
	}

	@Override
	public Transaction loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(TransactionFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.cashierId = UUID.fromString(rawJsonObject.optString(TransactionFieldName.CASHIER_ID.getFieldName().toString()));
		this.totalQuantity = rawJsonObject.optInt(TransactionFieldName.TOTAL_QUANTITY.getFieldName());
		this.totalPrice = rawJsonObject.optDouble(TransactionFieldName.TOTAL_PRICE.getFieldName());
		this.referenceId = UUID.fromString(rawJsonObject.optString(TransactionFieldName.REFERENCE_ID.getFieldName().toString()));

		value = rawJsonObject.optString(TransactionFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		this.apiRequestMessage = rawJsonObject.optString(TransactionFieldName.API_REQUEST_MESSAGE.getFieldName());

		value = rawJsonObject.optString(TransactionFieldName.API_REQUEST_STATUS.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = TransactionApiRequestStatus.mapName(value);
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

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(TransactionFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(TransactionFieldName.CASHIER_ID.getFieldName(), this.cashierId.toString());
			jsonObject.put(TransactionFieldName.TOTAL_QUANTITY.getFieldName(), this.totalQuantity);
			jsonObject.put(TransactionFieldName.TOTAL_PRICE.getFieldName(), this.totalPrice);
			jsonObject.put(TransactionFieldName.REFERENCE_ID.getFieldName(), this.referenceId.toString());
			jsonObject.put(TransactionFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
			jsonObject.put(TransactionFieldName.API_REQUEST_MESSAGE.getFieldName(), this.apiRequestMessage);
			jsonObject.put(TransactionFieldName.API_REQUEST_STATUS.getFieldName(), this.apiRequestStatus.name());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Transaction() {
		this.id = UUID.randomUUID();
		this.cashierId = new UUID(0, 0);
		this.totalQuantity = -1;
		this.totalPrice = -1.00;
		this.referenceId = new UUID(0, 0);
		this.createdOn = new Date();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = TransactionApiRequestStatus.OK;
		this.entries = new ArrayList<TransactionEntry>();
	}

	public Transaction(TransactionTransition transactionTransition) {
		this.id = transactionTransition.getId();
		this.cashierId = transactionTransition.getId();
		this.totalQuantity = transactionTransition.getTotalQuantity();
		this.totalPrice = transactionTransition.getTotalPrice();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.createdOn = transactionTransition.getCreatedOn();
		this.apiRequestStatus = TransactionApiRequestStatus.OK;
		this.entries = new ArrayList<TransactionEntry>();
	}

}
