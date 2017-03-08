package edu.uark.uarkregisterapp.models.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import edu.uark.uarkregisterapp.models.api.fields.EmployeeListingFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;

// Modeled after ProductListing.java

public class EmployeeListing implements LoadFromJsonInterface<EmployeeListing> {
	private List<Employee> employees;
	public List<Employee> getEmployees() {
		return this.employees;
	}
	public EmployeeListing setProducts(List<Employee> employees) {
		this.employees = employees;
		return this;
	}
	public EmployeeListing addEmployee(Employee employee) {
		this.employees.add(employee);
		return this;
	}

	@Override
	public EmployeeListing loadFromJson(JSONObject rawJsonObject) {
		JSONArray jsonActivities = rawJsonObject.optJSONArray(EmployeeListingFieldName.EMPLOYEES.getFieldName());

		if (jsonActivities != null) {
			try {
				for (int i = 0; i < jsonActivities.length(); i++) {
					JSONObject jsonActivity = jsonActivities.getJSONObject(i);

					this.employees.add((new Employee()).loadFromJson(jsonActivity));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	public EmployeeListing() { this.employees = new LinkedList<Employee>(); }
}
