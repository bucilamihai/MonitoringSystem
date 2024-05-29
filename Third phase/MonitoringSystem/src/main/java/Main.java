import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repository.database.EmployeeDBRepository;
import repository.database.ManagerDBRepository;
import repository.database.TaskDBRepository;
import service.MonitoringService;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        initView(stage);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("Monitoring system - login");
        stage.show();
    }

    private void initView(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("views/login-view.fxml"));
        StackPane loginLayout = loginLoader.load();
        stage.setScene(new Scene(loginLayout));

        MonitoringService monitoringService = new MonitoringService(
                new ManagerDBRepository(),
                new EmployeeDBRepository(),
                new TaskDBRepository()
        );
        LoginController loginController = loginLoader.getController();
        loginController.setMonitoringService(monitoringService);
    }
}