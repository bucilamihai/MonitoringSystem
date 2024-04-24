package controller;

import domain.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.MonitoringService;

public class ManagerController {
    @FXML
    public TextField employeeNameTextField;

    private MonitoringService monitoringService;
    private Manager loggedManager;

    public void setMonitoringService(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    public void setUser(Manager manager) {
        loggedManager = manager;
    }

    public void handleAddEmployee() {
        try {
            monitoringService.addEmployee(employeeNameTextField.getText());
            // update Table data
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    public void handleDeleteEmployee() {
        try {
            monitoringService.deleteEmployee(employeeNameTextField.getText());
            // update Table data
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}
