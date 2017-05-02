package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.os.Parcel;
import android.os.Parcelable;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.enums.TransactionEntryApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.fields.TransactionEntryFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.TransactionEntryTransition;

public class TransactionEntry implements Parcelable, ConvertToJsonInterface, LoadFromJsonInterface<TransactionEntry> {
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

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<TransactionEntry> CREATOR = new Creator<TransactionEntry>() {
		public TransactionEntry createFromParcel(Parcel transactionEntryParcel) {
			return new TransactionEntry(transactionEntryParcel);
		}

		public TransactionEntry[] newArray(int size) {
			return new TransactionEntry[size];
		}
	};

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.fromTransaction).execute());
		destination.writeInt(this.quantity);
		destination.writeDouble(this.price);
		destination.writeLong(this.createdOn.getTime());
	}

	public TransactionEntry(Parcel transactionEntryParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionEntryParcel.createByteArray()).execute();
		this.fromTransaction = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionEntryParcel.createByteArray()).execute();
		this.quantity = transactionEntryParcel.readInt();
		this.price = transactionEntryParcel.readDouble();
		String active_parcel = transactionEntryParcel.readString();

		this.createdOn = new Date();
		this.createdOn.setTime(transactionEntryParcel.readLong());
	}

	public TransactionEntry() {
		this.id = UUID.randomUUID();
		this.fromTransaction = new UUID(0, 0);
		this.lookupCode = "";
		this.quantity = -1;
		this.price = -1.00;
		this.createdOn = new Date();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = TransactionEntryApiRequestStatus.OK;
	}

	public TransactionEntry(TransactionEntry transactionEntry) {
		this.id = transactionEntry.getId();
		this.fromTransaction = transactionEntry.getFromTransaction();
		this.lookupCode = transactionEntry.getLookupCode();
		this.quantity = transactionEntry.getQuantity();
		this.price = transactionEntry.getPrice();
		this.createdOn = transactionEntry.getCreatedOn();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = TransactionEntryApiRequestStatus.OK;
	}

	public TransactionEntry(TransactionEntryTransition transactionEntryTransition) {
		this.id = transactionEntryTransition.getId();
		this.fromTransaction = transactionEntryTransition.getFromTransaction();
		this.lookupCode = transactionEntryTransition.getLookupCode();
		this.quantity = transactionEntryTransition.getQuantity();
		this.price = transactionEntryTransition.getPrice();
		this.createdOn = transactionEntryTransition.getCreatedOn();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = TransactionEntryApiRequestStatus.OK;
	}
}
