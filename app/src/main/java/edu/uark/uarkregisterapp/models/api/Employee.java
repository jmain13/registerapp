package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.fields.EmployeeFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

// Modeled after Product.java

public class Employee implements ConvertToJsonInterface, LoadFromJsonInterface<Employee> {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Employee setId(UUID id) {
		this.id = id;
		return this;
	}

	private String firstName;
	public String getFirstName() { return this.firstName; }
	public Employee setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	private String lastName;
	public String getLastName() { return this.lastName; }
	public Employee setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	private String password;
	public String getPassword() { return this.password; }
	public Employee setPassword(String password) {
		this.password = password;
		return this;
	}

	// Is this needed for employee?
	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public Employee setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	private EmployeeApiRequestStatus apiRequestStatus;
	public EmployeeApiRequestStatus getApiRequestStatus() {
		return this.apiRequestStatus;
	}
	public Employee setApiRequestStatus(EmployeeApiRequestStatus apiRequestStatus) {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus;
		}

		return this;
	}

	private String apiRequestMessage;
	public String getApiRequestMessage() {
		return this.apiRequestMessage;
	}
	public Employee setApiRequestMessage(String apiRequestMessage) {
		if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
			this.apiRequestMessage = apiRequestMessage;
		}

		return this;
	}

	@Override
	public Employee loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(EmployeeFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.firstName = rawJsonObject.optString(EmployeeFieldName.FIRST_NAME.getFieldName());
		this.lastName = rawJsonObject.optString(EmployeeFieldName.LAST_NAME.getFieldName());
		this.password = rawJsonObject.optString(EmployeeFieldName.PASSWORD.getFieldName());

		value = rawJsonObject.optString(EmployeeFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		this.apiRequestMessage = rawJsonObject.optString(EmployeeFieldName.API_REQUEST_MESSAGE.getFieldName());

		value = rawJsonObject.optString(EmployeeFieldName.API_REQUEST_STATUS.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = EmployeeApiRequestStatus.mapName(value);
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(EmployeeFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(EmployeeFieldName.FIRST_NAME.getFieldName(), this.firstName);
			jsonObject.put(EmployeeFieldName.LAST_NAME.getFieldName(), this.lastName);
			jsonObject.put(EmployeeFieldName.PASSWORD.getFieldName(), this.password);
			jsonObject.put(EmployeeFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
			jsonObject.put(EmployeeFieldName.API_REQUEST_MESSAGE.getFieldName(), this.apiRequestMessage);
			jsonObject.put(EmployeeFieldName.API_REQUEST_STATUS.getFieldName(), this.apiRequestStatus.name());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Employee() {
		this.firstName = "";
		this.lastName = "";
		this.password = "";
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = EmployeeApiRequestStatus.OK;
	}

	public Employee(EmployeeTransition employeeTransition) {
		this.id = employeeTransition.getId();
		this.firstName = employeeTransition.getFirstName();
		this.lastName = employeeTransition.getLastName();
		this.password = employeeTransition.getPassword();
		this.createdOn = employeeTransition.getCreatedOn();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = EmployeeApiRequestStatus.OK;
	}
}
