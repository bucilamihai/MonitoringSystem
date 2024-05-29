package domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "MANAGERS")
public class Manager extends Entity<Long> {

    private String name;

    private String password;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> sentTasks;


    public Manager() {}

    public Manager(String name, String password) {
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

    public List<Task> getSentTasks() {
        return sentTasks;
    }

    public void setSentTasks(List<Task> sentTasks) {
        this.sentTasks = sentTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager manager)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, manager.name) && Objects.equals(password, manager.password) && Objects.equals(sentTasks, manager.sentTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, password, sentTasks);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sentTasks=" + sentTasks +
                '}';
    }
}