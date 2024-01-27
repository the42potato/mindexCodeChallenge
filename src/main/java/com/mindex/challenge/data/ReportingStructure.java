package com.mindex.challenge.data;

import com.mindex.challenge.dao.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class ReportingStructure {

    private EmployeeRepository employeeRepository;

    private final Employee employee;
    private final int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public String toString() {
        return "Name: " + employee.getFirstName() + " " + employee.getLastName() + "\n"
                + "Direct Reports: " + employee.getDirectReports() + "\n"
                + "Calculated Reports: " + numberOfReports;
    }
}
