package controller;

import domain.Employee;
import domain.Manager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.MonitoringService;
import utils.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ManagerController implements Observer {
    @FXML
    public TextField employeeNameTextField;
    @FXML
    public Button logoutButton;
    @FXML
    public TextArea descriptionText;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
    @FXML
    private TableColumn<Employee, String> lastLoginColumn;

    private MonitoringService monitoringService;
    private Manager loggedManager;


    public void setMonitoringService(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
        this.monitoringService.addObserver(this);
        loadEmployeeData();
    }

    public void setUser(Manager manager) {
        loggedManager = manager;
    }

    @FXML
    public void initialize() {
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
    }

    private void loadEmployeeData() {
        List<Employee> employees = monitoringService.getAllLoggedEmployees();
        ObservableList<Employee> observableList = FXCollections.observableArrayList(employees);
        employeeTable.setItems(observableList);
    }

    public void handleAddEmployee() {
        try {
            monitoringService.addEmployee(employeeNameTextField.getText());
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
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    public void handleUpdateEmployeePassword() {
        try {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Update employee password");
            textInputDialog.setContentText("Enter employee password");
            Optional<String> newPassword = textInputDialog.showAndWait();
            if(newPassword.isPresent() && !newPassword.get().isEmpty()) {
                monitoringService.updateEmployeePassword(employeeNameTextField.getText(), newPassword.get());
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    public void handleSendTask() {
        String description = descriptionText.getText();
        boolean isSolved = false;
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        monitoringService.sendTaskToEmployee(description, isSolved, selectedEmployee, loggedManager);
    }

    public void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update() {
        loadEmployeeData();
    }
}
