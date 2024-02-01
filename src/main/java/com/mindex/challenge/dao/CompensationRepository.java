package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    Compensation findByCompensationId(String compensationId);
    Compensation findFirstByEmployee(Employee employee);
    List<Compensation> findByEmployee(Employee employee);
}
