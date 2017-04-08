package edu.uark.uarkregisterapp.models.api.services;

import org.json.JSONObject;

import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ActiveEmployeeCounts;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.EmployeeLogin;
import edu.uark.uarkregisterapp.models.api.enums.ApiLevel;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiMethod;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class EmployeeService extends BaseRemoteService {
	public Employee getEmployee(UUID employeeId) {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { EmployeeApiMethod.EMPLOYEE, ApiLevel.ONE }), employeeId
		);

		if (rawJsonObject != null) {
			return (new Employee()).loadFromJson(rawJsonObject);
		} else {
			return new Employee().setApiRequestStatus(EmployeeApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public ActiveEmployeeCounts activeEmployeeCounts() {
		JSONObject rawJsonObject = this.requestSingle(
			(new PathElementInterface[] { EmployeeApiMethod.EMPLOYEE, ApiLevel.ONE, EmployeeApiMethod.ACTIVE_COUNTS })
		);

		if (rawJsonObject != null) {
			return (new ActiveEmployeeCounts()).loadFromJson(rawJsonObject);
		} else {
			return new ActiveEmployeeCounts();
		}
	}

	public Employee logIn(EmployeeLogin employeeLogin) {
		JSONObject rawJsonObject = this.putSingle(
			(new PathElementInterface[] { EmployeeApiMethod.EMPLOYEE, ApiLevel.ONE, EmployeeApiMethod.LOGIN }),
			employeeLogin.convertToJson()
		);

		if (rawJsonObject != null) {
			return (new Employee()).loadFromJson(rawJsonObject);
		} else {
			return new Employee().setApiRequestStatus(EmployeeApiRequestStatus.UNKNOWN_ERROR);
		}
	}

	public Employee putEmployee(Employee employee) {
		JSONObject rawJsonObject = this.putSingle(
			(new PathElementInterface[]{ EmployeeApiMethod.EMPLOYEE, ApiLevel.ONE }), employee.convertToJson()
		);

		if (rawJsonObject != null) {
			return (new Employee()).loadFromJson(rawJsonObject);
		} else {
			return new Employee().setApiRequestStatus(EmployeeApiRequestStatus.UNKNOWN_ERROR);
		}
	}
}
