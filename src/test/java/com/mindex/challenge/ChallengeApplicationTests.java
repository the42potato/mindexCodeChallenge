package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {

	private String rsUrl;

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
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void ReportingStructureTest() {
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
		ReportingStructure rs = restTemplate.postForEntity(rsUrl, employee, ReportingStructure.class).getBody();
		assertEquals(4, rs.getNumberOfReports());
	}

}
