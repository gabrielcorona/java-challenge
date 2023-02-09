package jp.co.axa.apidemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.BadRequestException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;

@SpringBootTest
public class EmployeeServiceImplTest {
    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @Mock
    EmployeeRepository employeeRepository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); //without this you will get NPE
    }

    // Ideal successful GET employees test
    @Test
    public void getEmployeesTest(){
        when(employeeRepository.findAll()).thenReturn(getTestEmployees());
        List<Employee> employees = employeeServiceImpl.retrieveEmployees();
        assertNotNull(employees);
        assertEquals(employees.size(), 2);
        assertEquals(employees.get(0).getName(), "Gabriel");
        assertEquals(employees.get(0).getDepartment(), "IT");
    }

    // Ideal successful GET employee by Id test
    @Test
    public void getEmployeeTest(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(getTestEmployee()));
        Employee employee = employeeServiceImpl.getEmployee(1L);
        assertNotNull(employee);
        assertEquals(employee.getName(), "Gabriel");
        assertEquals(employee.getDepartment(), "IT");
    }

    // Ideal successful POST employee test
    @Test
    public void saveEmployeeTest(){
        Employee employeeReq = getTestEmployee();
        Employee employeeRes = getTestEmployee();
        employeeReq.setId(null);
        when(employeeRepository.save(employeeReq)).thenReturn(employeeRes);
        employeeServiceImpl.saveEmployee(employeeReq);
    }

    // Ideal successful PUT employee test
    @Test
    public void updateEmployeeTest(){
        Employee employeeReq = getTestEmployee();
        Employee employeeRes = getTestEmployee();
        employeeReq.setId(null);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeRes));
        when(employeeRepository.save(employeeRes)).thenReturn(employeeRes);
        Employee employee = employeeServiceImpl.updateEmployee(employeeReq,1L);
        assertNotNull(employee);
        assertEquals(employee.getName(), "Gabriel");
        assertEquals(employee.getDepartment(), "IT");
    }

    // Ideal successful DELETE employee test
    @Test
    public void deleteEmployeeTest(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(getTestEmployee()));
        employeeServiceImpl.deleteEmployee(1L);
    }

    // Bad Request GET employee by Id test
    @Test
    public void getEmployeeBadRequestTest(){
        exceptionRule.expect(NoSuchElementException.class);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(getTestEmployee()));
        employeeServiceImpl.getEmployee(2L);
        
    }

    // Bad Request POST employee test
    @Test
    public void saveEmployeeBadRequestTest(){
        exceptionRule.expect(IllegalArgumentException.class);
        Employee employeeReq = getTestEmployee();        
        employeeReq.setId(null);
        when(employeeRepository.save(employeeReq)).thenThrow(new IllegalArgumentException());
        employeeServiceImpl.saveEmployee(employeeReq);
    }

    // Bad Request PUT employee test 1
    // This test the case in which the id provided as a parameter doesn't match the id provided in the body request
    @Test
    public void updateEmployeeBadRequestTest1(){
        exceptionRule.expect(BadRequestException.class);
        Employee employeeReq = getTestEmployee();
        Employee employeeRes = getTestEmployee();
        employeeReq.setId(2L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeRes));
        when(employeeRepository.save(employeeRes)).thenReturn(employeeRes);
        employeeServiceImpl.updateEmployee(employeeReq,1L);
    }

    // Bad Request PUT employee test 2
    // This test the case in which at least one of the employee fields is not provided
    @Test
    public void updateEmployeeBadRequestTest2(){
        exceptionRule.expect(ConstraintViolationException.class);
        Employee employeeReq = getTestEmployee();
        Employee employeeRes = getTestEmployee();
        employeeReq.setId(1L);
        employeeReq.setName(null);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeRes));
        when(employeeRepository.save(employeeRes)).thenThrow(new ConstraintViolationException("One of the expected properties has been set to null", null) {
            
        });
        employeeServiceImpl.updateEmployee(employeeReq,1L);
    }

    // Not found employee to DELETE test
    @Test
    public void deleteEmployeeNotFoundTest(){
        exceptionRule.expect(NoSuchElementException.class);
        employeeServiceImpl.deleteEmployee(1L);
    }

    // Data provider method employee by Id
    private Employee getTestEmployee(){
        Employee employee = Employee.builder().id(1L).name("Gabriel").salary(new BigDecimal(123.20)).department("IT").build();
        return employee;
    }

    // Data provider method employees list
    private List<Employee> getTestEmployees(){
        List<Employee> employees = new ArrayList<>();
        Employee employee = Employee.builder().id(1L).name("Gabriel").salary(new BigDecimal(123.2)).department("IT").build();
        employees.add(employee);
        employee = Employee.builder().id(2L).name("John").salary(new BigDecimal(125.5)).department("HR").build();
        employees.add(employee);
        return employees;
    }
}