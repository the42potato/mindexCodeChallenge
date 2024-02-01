package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating employee [{}]", compensation);
        compensation.setCompensationId(UUID.randomUUID().toString());
        Compensation compTest = compensationRepository.insert(compensation);

        return compTest;
    }

    @Override
    public List<Compensation> read(String id) {
        LOG.debug("Creating compensation with id [{}]", id);

        // for an employee to have a compensation, they must be present in the employeeRepository
        Employee searchEmployee = employeeRepository.findByEmployeeId(id);

        if (searchEmployee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return compensationRepository.findByEmployee(searchEmployee);
    }
}
