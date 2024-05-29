package service;

import domain.Employee;
import domain.Manager;
import domain.Task;
import repository.database.EmployeeDBRepository;
import repository.database.ManagerDBRepository;
import repository.database.TaskDBRepository;
import utils.Observable;
import utils.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonitoringService implements Observable {

    private final ManagerDBRepository managerDBRepository;
    private final EmployeeDBRepository employeeDBRepository;
    private final TaskDBRepository taskDBRepository;

    private List<Observer> observers;

    public MonitoringService(ManagerDBRepository managerDBRepository, EmployeeDBRepository employeeDBRepository, TaskDBRepository taskDBRepository) {
        this.managerDBRepository = managerDBRepository;
        this.employeeDBRepository = employeeDBRepository;
        this.taskDBRepository = taskDBRepository;
        observers = new ArrayList<>();
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
                notifyObservers();
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

    public void addEmployee(String employeeName) throws Exception {
        if(employeeName.isEmpty())
            throw new Exception("Employee name is empty!");
        Employee newEmployee = new Employee(employeeName, employeeName);
        employeeDBRepository.save(newEmployee);
    }

    public void deleteEmployee(String employeeName) throws Exception {
        if(employeeName.isEmpty())
            throw new Exception("Employee name is empty!");
        Employee employee = employeeDBRepository.findByName(employeeName);
        employeeDBRepository.delete(employee.getId());
    }

    public void updateEmployeePassword(String employeeName, String newPassword) throws Exception {
        if(employeeName.isEmpty())
            throw new Exception("Employee name is empty!");
        if(newPassword.isEmpty())
            throw new Exception("Employee password is empty!");
        Employee employee = employeeDBRepository.findByName(employeeName);
        employee.setPassword(newPassword);
        employeeDBRepository.update(employee);
    }

    public List<Employee> getAllLoggedEmployees() {
        List<Employee> allLoggedEmployees = employeeDBRepository.getAll();
        allLoggedEmployees.removeIf(employee -> !employee.isLogged());
        return allLoggedEmployees;
    }

    public List<Task> getAllTasksForEmployee(Employee loggedEmployee) {
        List<Task> allTasksForEmployee = taskDBRepository.getAll();
        allTasksForEmployee.removeIf(task -> !task.getEmployee().equals(loggedEmployee));
        return allTasksForEmployee;
    }

    public void employeeLogout(Employee loggedEmployee) {
        loggedEmployee.setLogged(false);
        employeeDBRepository.update(loggedEmployee);
        notifyObservers();
    }

    public void sendTaskToEmployee(String description, boolean isSolved, Employee employee, Manager manager) {
        Task task = new Task(description, isSolved, employee, manager);
        taskDBRepository.save(task);
        notifyObservers();
    }


    public void solveTask(Task taskToSolve) {
        taskToSolve.setSolved(true);
        taskDBRepository.update(taskToSolve);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
