package controller;

import domain.Employee;
import domain.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.MonitoringService;
import utils.Observer;

import java.util.List;

public class EmployeeController implements Observer {

    @FXML
    public Button solveTaskButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, String> isSolvedColumn;

    private MonitoringService monitoringService;
    private Employee loggedEmployee;

    public void setMonitoringService(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
        this.monitoringService.addObserver(this);
        loadTaskData();
    }

    public void setUser(Employee employee) {
        loggedEmployee = employee;
    }

    @FXML
    public void initialize() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadTaskData() {
        List<Task> tasks = monitoringService.getAllTasksForEmployee(loggedEmployee);
        ObservableList<Task> observableList = FXCollections.observableArrayList(tasks);
        taskTable.setItems(observableList);
    }


    public void handleSolveTask() {
        Task taskToSolve = taskTable.getSelectionModel().getSelectedItem();
        monitoringService.solveTask(taskToSolve);
    }

    public void handleLogout() {
        monitoringService.employeeLogout(loggedEmployee);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update() {
        loadTaskData();
    }

}
