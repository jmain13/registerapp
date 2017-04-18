package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Product;

public class ProductTransition implements Parcelable {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public ProductTransition setId(UUID id) {
		this.id = id;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public ProductTransition setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int quantity;
	public int getQuantity() {
		return this.quantity;
	}
	public ProductTransition setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	private double price;
	public double getPrice() {
		return this.price;
	}
	public ProductTransition setPrice(double price) {
		this.price = price;
		return this;
	}

	private boolean active;
	public boolean getActive() {
		return this.active;
	}
	public ProductTransition setActive(boolean active) {
		this.active = active;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public ProductTransition setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeString(this.lookupCode);
		destination.writeInt(this.quantity);
		destination.writeDouble(this.price);
		if (this.active) { destination.writeString("t"); }
        else if (!this.active) { destination.writeString("f"); }
        else { destination.writeString("n"); };
		destination.writeLong(this.createdOn.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ProductTransition> CREATOR = new Creator<ProductTransition>() {
		public ProductTransition createFromParcel(Parcel productTransitionParcel) {
			return new ProductTransition(productTransitionParcel);
		}

		public ProductTransition[] newArray(int size) {
			return new ProductTransition[size];
		}
	};

	public ProductTransition() {
		this.quantity = -1;
		this.price = -1.00;
		this.active = false;
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.lookupCode = StringUtils.EMPTY;
	}

	public ProductTransition(Product product) {
		this.id = product.getId();
		this.quantity = product.getQuantity();
		this.price = product.getPrice();
		this.active = product.getActive();
		this.createdOn = product.getCreatedOn();
		this.lookupCode = product.getLookupCode();
	}

	public ProductTransition(Parcel productTransitionParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(productTransitionParcel.createByteArray()).execute();
		this.lookupCode = productTransitionParcel.readString();
		this.quantity = productTransitionParcel.readInt();
		this.price = productTransitionParcel.readDouble();
		String active_parcel = productTransitionParcel.readString();
		if (active_parcel == "t") { this.active = true; }
		else if (active_parcel == "f") { this.active = false; }

		this.createdOn = new Date();
		this.createdOn.setTime(productTransitionParcel.readLong());
	}
}
