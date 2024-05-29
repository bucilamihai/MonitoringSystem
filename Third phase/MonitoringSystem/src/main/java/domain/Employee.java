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


    public Employee() {}

    public Employee(String name, String password) {
        this.name = name;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        if (!super.equals(o)) return false;
        return isLogged == employee.isLogged && Objects.equals(name, employee.name) && Objects.equals(password, employee.password) && Objects.equals(lastLogin, employee.lastLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, password, lastLogin, isLogged);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                ", isLogged=" + isLogged +
                /*", receivedTasks=" + receivedTasks +*/
                '}';
    }
}