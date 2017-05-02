package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.TransactionEntry;

public class TransactionEntryTransition implements Parcelable {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public TransactionEntryTransition setId(UUID id) {
		this.id = id;
		return this;
	}

	private UUID fromTransaction;
	public UUID getFromTransaction() {
		return this.fromTransaction;
	}
	public TransactionEntryTransition setFromTransaction(UUID fromTransaction) {
		this.fromTransaction = fromTransaction;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public TransactionEntryTransition setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int quantity;
	public int getQuantity() {
		return this.quantity;
	}
	public TransactionEntryTransition setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	private double price;
	public double getPrice() {
		return this.price;
	}
	public TransactionEntryTransition setPrice(double price) {
		this.price = price;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public TransactionEntryTransition setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.fromTransaction).execute());
		destination.writeString(this.lookupCode);
		destination.writeInt(this.quantity);
		destination.writeDouble(this.price);
		destination.writeLong(this.createdOn.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<TransactionEntryTransition> CREATOR = new Creator<TransactionEntryTransition>() {
		public TransactionEntryTransition createFromParcel(Parcel productTransitionParcel) {
			return new TransactionEntryTransition(productTransitionParcel);
		}

		public TransactionEntryTransition[] newArray(int size) {
			return new TransactionEntryTransition[size];
		}
	};

	public TransactionEntryTransition() {
		this.id = new UUID(0, 0);
		this.fromTransaction = new UUID(0, 0);
		this.lookupCode = StringUtils.EMPTY;
		this.quantity = -1;
		this.price = -1.00;
		this.createdOn = new Date();
	}

	public TransactionEntryTransition(TransactionEntry transactionEntry) {
		this.id = transactionEntry.getId();
		this.fromTransaction = transactionEntry.getFromTransaction();
		this.lookupCode = transactionEntry.getLookupCode();
		this.quantity = transactionEntry.getQuantity();
		this.price = transactionEntry.getPrice();
		this.createdOn = transactionEntry.getCreatedOn();
	}

	public TransactionEntryTransition(Parcel transactionEntryTransitionParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionEntryTransitionParcel.createByteArray()).execute();
		this.fromTransaction = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionEntryTransitionParcel.createByteArray()).execute();
		this.lookupCode = transactionEntryTransitionParcel.readString();
		this.quantity = transactionEntryTransitionParcel.readInt();
		this.price = transactionEntryTransitionParcel.readDouble();
		String active_parcel = transactionEntryTransitionParcel.readString();

		this.createdOn = new Date();
		this.createdOn.setTime(transactionEntryTransitionParcel.readLong());
	}
}
