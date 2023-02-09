package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.BadRequestException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        // this will need to add pagination and filtering
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        // walidate if the employee to be updated exist
        if(optEmp.isEmpty()){
            // throw exception to interrupt the process and return the message as the response in a custom message
            log.error("The employee doesn't exist");
            throw new NoSuchElementException("The employee with the id "+employeeId+" doesn't exist");
            
        }
        return optEmp.get();
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public Employee saveEmployee(Employee employee){
        // if there is exception will be catch by the CustomControllerAdvice
        return  employeeRepository.save(employee);
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public void deleteEmployee(Long employeeId){
        // walidate if the employee to be deleted exist
        if(!employeeRepository.existsById(employeeId)){
            // will need to throw exception
            log.error("The employee doesn't exist");
            getEmployee(employeeId);
        }
        employeeRepository.deleteById(employeeId);
        log.debug("Employee Deleted Successfully with ID:"+employeeId);
    }

    @Caching(evict = {
        @CacheEvict(value="employees",allEntries = true),
        @CacheEvict(value="employee",allEntries = true)
    })
    public Employee updateEmployee(Employee employee, Long employeeId) {
        Employee employeeResult = Employee.builder().build();
        if(employeeId != employee.getId() && employee.getId()!= null)
            throw new BadRequestException("The IDs does not match: "+employeeId+" != "+employee.getId());
        Employee existing = getEmployee(employeeId);
        existing.setName(employee.getName());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());
        employeeResult=employeeRepository.save(existing);
        log.debug("Employee Saved Successfully with ID:"+employeeResult.getId());
        
        return employeeResult;
    }
}