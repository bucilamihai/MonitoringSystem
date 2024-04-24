package service;

import domain.Employee;
import domain.Manager;
import repository.database.EmployeeDBRepository;
import repository.database.ManagerDBRepository;
import repository.database.TaskDBRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class MonitoringService {

    private final ManagerDBRepository managerDBRepository;
    private final EmployeeDBRepository employeeDBRepository;
    private final TaskDBRepository taskDBRepository;

    public MonitoringService(ManagerDBRepository managerDBRepository, EmployeeDBRepository employeeDBRepository, TaskDBRepository taskDBRepository) {
        this.managerDBRepository = managerDBRepository;
        this.employeeDBRepository = employeeDBRepository;
        this.taskDBRepository = taskDBRepository;
    }

    public Manager managerLogin(String name, String password) throws Exception {
        Manager manager = managerDBRepository.findByName(name);
        if(manager != null) {
            if(Objects.equals(manager.getPassword(), password)) {
                return manager;
            }
            else {
                throw new Exception("Wrong password!");
            }
        }
        else {
            throw new Exception("There's no manager with this name!");
        }
    }

    public Employee employeeLogin(String name, String password, LocalDateTime loginTime) throws Exception {
        Employee employee = employeeDBRepository.findByName(name);
        if(employee != null) {
            if(Objects.equals(employee.getPassword(), password)) {
                employee.setLogged(true);
                employee.setLastLogin(loginTime);
                employeeDBRepository.update(employee);
                return employee;
            }
            else {
                throw new Exception("Wrong password!");
            }
        }
        else {
            throw new Exception("There's no employee with this name!");
        }
    }

    public void addEmployee(String employeeName) {
        Employee newEmployee = new Employee(employeeName, employeeName);
        employeeDBRepository.save(newEmployee);
    }

    public void deleteEmployee(String employeeName) {
        Employee employee = employeeDBRepository.findByName(employeeName);
        employeeDBRepository.delete(employee.getId());
    }
}
