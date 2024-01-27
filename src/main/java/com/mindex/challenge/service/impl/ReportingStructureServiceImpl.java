package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure create(Employee employee) {
        LOG.debug("Creating reporting structure for employee [{}]", employee);

        int reportNum = 0;
        if(employee.getDirectReports() != null) {
            List<Employee> reportedEmployees = employee.getDirectReports();
            // initial number of reports is existing number of candidate employees
            reportNum = reportedEmployees.size();
            if (!reportedEmployees.isEmpty()) {
                // if there are employees in the employee's direct reports, iterate through and
                // add their number of direct reports to this employee's total
                for (Employee e: reportedEmployees) {
                    Employee populatedEmployee = employeeRepository.findByEmployeeId(e.getEmployeeId());
                    // check the direct reports of employees within this employee's direct reports
                    ReportingStructure rs = create(populatedEmployee);
                    reportNum += rs.getNumberOfReports();
                }
            }
        }
        return new ReportingStructure(employee, reportNum);
    }
}
