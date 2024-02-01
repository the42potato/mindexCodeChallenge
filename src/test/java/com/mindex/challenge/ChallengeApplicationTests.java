package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {

	private String rsUrl;
	private String compUrl;
	private String compIdUrl;

	@LocalServerPort
	private int port;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	ReportingStructureService reportingStructureService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		rsUrl = "http://localhost:" + port + "/reportingStructure";
		compUrl = "http://localhost:" + port + "/compensation";
		compIdUrl = "http://localhost:" + port + "/compensation/{id}";
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void reportingStructureTest() {
		// create
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
		ReportingStructure rs = restTemplate.postForEntity(rsUrl, employee, ReportingStructure.class).getBody();
		assertEquals(4, rs.getNumberOfReports());
	}

	@Test
	public void compensationServiceTest() {
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

		Compensation testComp = new Compensation();
		testComp.setEmployee(employee);
		testComp.setSalary(250000.00);
		testComp.setEffectiveDate(new Date());

		// create
		Compensation createdComp = restTemplate.postForEntity(compUrl, testComp, Compensation.class).getBody();
		assertNotNull(createdComp.getCompensationId());
		assertCompensationEquivalence(testComp, createdComp);

		// read
		List<Compensation> readCompensation = restTemplate.exchange(compIdUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Compensation>>() {},
				createdComp.getEmployee().getEmployeeId()).getBody();
		assertNotNull(readCompensation);
		assertEquals(1, readCompensation.size());
		assertCompensationEquivalence(createdComp, readCompensation.get(0));
	}

	/***
	 * test for when an employee has multiple compensations
	 */
	@Test
	public void compensationServiceMultipleCompensationsTest() {
		Employee employee = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");

		Compensation testComp = new Compensation();
		testComp.setEmployee(employee);
		testComp.setSalary(250000.00);
		testComp.setEffectiveDate(new Date());

		// two compensations added to repository with the same employee
		Compensation createdComp = restTemplate.postForEntity(compUrl, testComp, Compensation.class).getBody();
		Compensation createdComp1 = restTemplate.postForEntity(compUrl, testComp, Compensation.class).getBody();
		assertNotEquals(createdComp.getCompensationId(), createdComp1.getCompensationId());

		List<Compensation> readCompensation = restTemplate.exchange(compIdUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Compensation>>() {},
				createdComp.getEmployee().getEmployeeId()).getBody();
		assertNotNull(readCompensation);
		assertEquals(2, readCompensation.size());
		assertCompensationEquivalence(createdComp, readCompensation.get(0));
		assertCompensationEquivalence(createdComp1, readCompensation.get(1));
	}

	private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
		assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
		assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
		assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
		assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());

		assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
		assertEquals(expected.getSalary(), actual.getSalary());
	}

}
