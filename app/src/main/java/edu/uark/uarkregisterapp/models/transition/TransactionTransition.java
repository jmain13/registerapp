package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Transaction;

public class TransactionTransition implements Parcelable {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public TransactionTransition setId(UUID id) {
		this.id = id;
		return this;
	}

	private UUID cashierId;
	public UUID getCashierId() {
		return this.cashierId;
	}
	public TransactionTransition setCashierId(UUID cashierId) {
		this.cashierId = cashierId;
		return this;
	}

	private int totalQuantity;
	public int getTotalQuantity() {
		return this.totalQuantity;
	}
	public TransactionTransition setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
		return this;
	}

	private double totalPrice;
	public double getTotalPrice() {
		return this.totalPrice;
	}
	public TransactionTransition setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}

	private UUID referenceId;
	public UUID getReferenceId() {
		return this.referenceId;
	}
	public TransactionTransition setReferenceId(UUID referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public TransactionTransition setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.cashierId).execute());
		destination.writeInt(this.totalQuantity);
		destination.writeDouble(this.totalPrice);
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.referenceId).execute());
		destination.writeLong(this.createdOn.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<TransactionTransition> CREATOR = new Creator<TransactionTransition>() {
		public TransactionTransition createFromParcel(Parcel productTransitionParcel) {
			return new TransactionTransition(productTransitionParcel);
		}

		public TransactionTransition[] newArray(int size) {
			return new TransactionTransition[size];
		}
	};

	public TransactionTransition() {
		this.id = new UUID(0, 0);
		this.cashierId = new UUID(0, 0);
		this.totalQuantity = -1;
		this.totalPrice = -1.00;
		this.referenceId = new UUID(0, 0);
		this.createdOn = new Date();
	}

	public TransactionTransition(Transaction transaction) {
		this.id = transaction.getId();
		this.cashierId = transaction.getCashierId();
		this.totalQuantity = transaction.getTotalQuantity();
		this.totalPrice = transaction.getTotalPrice();
		this.referenceId = transaction.getReferenceId();
		this.createdOn = transaction.getCreatedOn();
	}

	public TransactionTransition(Parcel transactionTransitionParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionTransitionParcel.createByteArray()).execute();
		this.cashierId = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionTransitionParcel.createByteArray()).execute();
		this.totalQuantity = transactionTransitionParcel.readInt();
		this.totalPrice = transactionTransitionParcel.readDouble();
		this.referenceId = (new ByteToUUIDConverterCommand()).setValueToConvert(transactionTransitionParcel.createByteArray()).execute();
		String active_parcel = transactionTransitionParcel.readString();

		this.createdOn = new Date();
		this.createdOn.setTime(transactionTransitionParcel.readLong());
	}
}
