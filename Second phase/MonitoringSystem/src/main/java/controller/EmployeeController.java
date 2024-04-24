package controller;

import domain.Employee;
import service.MonitoringService;

public class EmployeeController {
    private MonitoringService monitoringService;
    private Employee loggedEmployee;

    public void setMonitoringService(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    public void setUser(Employee employee) {
        loggedEmployee = employee;
    }
}
