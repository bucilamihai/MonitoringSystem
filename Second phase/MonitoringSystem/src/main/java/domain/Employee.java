package domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "EMPLOYEES")
public class Employee extends Entity<Long> {

    private String name;

    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private boolean isLogged;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> receivedTasks;


    public Employee() {}

    public Employee(String name, String password) {
        this.name = name;
        this.password = password;
        receivedTasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public List<Task> getReceivedTasks() {
        return receivedTasks;
    }

    public void setReceivedTasks(List<Task> receivedTasks) {
        this.receivedTasks = receivedTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        if (!super.equals(o)) return false;
        return isLogged == employee.isLogged && Objects.equals(name, employee.name) && Objects.equals(password, employee.password) && Objects.equals(lastLogin, employee.lastLogin) && Objects.equals(receivedTasks, employee.receivedTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, password, lastLogin, isLogged, receivedTasks);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                ", isLogged=" + isLogged +
                ", receivedTasks=" + receivedTasks +
                '}';
    }
}