package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.fields.TransactionEntryFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.TransactionEntryTransition;

public class TransactionEntry implements ConvertToJsonInterface, LoadFromJsonInterface<TransactionEntry> {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public TransactionEntry setId(UUID id) {
		this.id = id;
		return this;
	}

	private UUID fromTransaction;
	public UUID getFromTransaction() {
		return this.fromTransaction;
	}
	public TransactionEntry setFromTransaction(UUID fromTransaction) {
		this.fromTransaction = fromTransaction;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public TransactionEntry setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int quantity;
	public int getQuantity() {
		return this.quantity;
	}
	public TransactionEntry setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	private double price;
	public double getPrice() {
		return this.price;
	}
	public TransactionEntry setPrice(double price) {
		this.price = price;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public TransactionEntry setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	private TransactionEntryApiRequestStatus apiRequestStatus;
	public TransactionEntryApiRequestStatus getApiRequestStatus() {
		return this.apiRequestStatus;
	}
	public TransactionEntry setApiRequestStatus(TransactionEntryApiRequestStatus apiRequestStatus) {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus;
		}

		return this;
	}

	private String apiRequestMessage;
	public String getApiRequestMessage() {
		return this.apiRequestMessage;
	}
	public TransactionEntry setApiRequestMessage(String apiRequestMessage) {
		if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
			this.apiRequestMessage = apiRequestMessage;
		}

		return this;
	}

	@Override
	public TransactionEntry loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(TransactionEntryFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		value = rawJsonObject.optString(TransactionEntryFieldName.FROM_TRANSACTION.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.lookupCode = rawJsonObject.optString(TransactionEntryFieldName.LOOKUP_CODE.getFieldName());
		this.quantity = rawJsonObject.optInt(TransactionEntryFieldName.QUANTITY.getFieldName());
		this.price = rawJsonObject.optDouble(TransactionEntryFieldName.PRICE.getFieldName());

		value = rawJsonObject.optString(TransactionEntryFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		this.apiRequestMessage = rawJsonObject.optString(TransactionEntryFieldName.API_REQUEST_MESSAGE.getFieldName());

		value = rawJsonObject.optString(TransactionEntryFieldName.API_REQUEST_STATUS.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = TransactionEntryApiRequestStatus.mapName(value);
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(TransactionEntryFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(TransactionEntryFieldName.FROM_TRANSACTION.getFieldName(), this.fromTransaction.toString());
			jsonObject.put(TransactionEntryFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(TransactionEntryFieldName.QUANTITY.getFieldName(), this.quantity);
			jsonObject.put(TransactionEntryFieldName.PRICE.getFieldName(), this.price);
			jsonObject.put(TransactionEntryFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
			jsonObject.put(TransactionEntryFieldName.API_REQUEST_MESSAGE.getFieldName(), this.apiRequestMessage);
			jsonObject.put(TransactionEntryFieldName.API_REQUEST_STATUS.getFieldName(), this.apiRequestStatus.name());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public TransactionEntry() {
		this.quantity = -1;
		this.price = -1.00;
		this.lookupCode = "";
		this.id = new UUID(0, 0);
		this.fromTransaction = new UUID(0, 0);
		this.createdOn = new Date();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = TransactionEntryApiRequestStatus.OK;
	}

	public TransactionEntry(TransactionEntryTransition transactionEntryTransition) {
		this.id = transactionEntryTransition.getId();
		this.fromTransaction = transactionEntryTransition.getFromTransaction();
		this.quantity = transactionEntryTransition.getQuantity();
		this.price = transactionEntryTransition.getPrice();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.createdOn = transactionEntryTransition.getCreatedOn();
		this.apiRequestStatus = TransactionEntryApiRequestStatus.OK;
		this.lookupCode = transactionEntryTransition.getLookupCode();
	}
}
