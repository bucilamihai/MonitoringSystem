package controller;

import domain.Employee;
import domain.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.MonitoringService;

import java.time.LocalDateTime;

public class LoginController {
    private MonitoringService monitoringService;

    @FXML
    public TextField nameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public DatePicker dateDatePicker;
    @FXML
    public TextField hourTextField;
    @FXML
    public TextField minuteTextField;
    @FXML
    public RadioButton managerRadioButton;
    @FXML
    public RadioButton employeeRadioButton;
    private ToggleGroup toggleGroup;

    public void setMonitoringService(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        managerRadioButton.setToggleGroup(toggleGroup);
        employeeRadioButton.setToggleGroup(toggleGroup);
    }

    public void handleLogin() {
        String name = nameField.getText();
        String password = passwordField.getText();
        if(managerRadioButton.isSelected()) {
            try {
                Manager manager = monitoringService.managerLogin(name, password);

                FXMLLoader managerLoader = new FXMLLoader();
                managerLoader.setLocation(getClass().getResource("/views/manager-view.fxml"));
                StackPane managerLayout = managerLoader.load();

                Stage managerStage = new Stage();
                managerStage.setScene(new Scene(managerLayout));
                managerStage.setWidth(1000);
                managerStage.setHeight(600);
                managerStage.setTitle("Monitoring system - manager");

                ManagerController managerController = managerLoader.getController();
                managerController.setUser(manager);
                managerController.setMonitoringService(monitoringService);
                managerStage.show();
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.toString());
                alert.show();
            }
        }
        else {
            if(employeeRadioButton.isSelected()) {
                try {
                    int hour = Integer.parseInt(hourTextField.getText());
                    int minute = Integer.parseInt(minuteTextField.getText());
                    LocalDateTime loginTime = dateDatePicker.getValue().atTime(hour, minute);
                    Employee employee = monitoringService.employeeLogin(name, password, loginTime);

                    FXMLLoader employeeLoader = new FXMLLoader();
                    employeeLoader.setLocation(getClass().getResource("/views/employee-view.fxml"));
                    StackPane employeeLayout = employeeLoader.load();

                    Stage employeeStage = new Stage();
                    employeeStage.setScene(new Scene(employeeLayout));
                    employeeStage.setWidth(1000);
                    employeeStage.setHeight(600);
                    employeeStage.setTitle("Monitoring system - employee");

                    EmployeeController employeeController = employeeLoader.getController();
                    employeeController.setUser(employee);
                    employeeController.setMonitoringService(monitoringService);
                    employeeStage.show();
                }
                catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(e.toString());
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please select an option");
                alert.show();
            }
        }
    }
}
