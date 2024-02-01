package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private Double salary;
    private Date effectiveDate;
    private String compensationId;

    public Compensation() {
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCompensationId() {
        return compensationId;
    }

    public void setCompensationId(String id) {
        compensationId = id;
    }

    public String toString() {
        return "Compensation ID: " + this.getCompensationId() + "\n"
                + "Employee ID: " + this.getEmployee().getEmployeeId() + "\n"
                + "Employee Name: " + this.getEmployee().getFirstName() + " " + this.getEmployee().getLastName() + "\n"
                + "Salary: " + this.getSalary() + "\n"
                + "Effective Date: " + this.getEffectiveDate();
    }
}
