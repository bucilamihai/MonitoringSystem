package domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "TASKS")
public class Task extends Entity<Long> {

    private String description;
    private boolean isSolved;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;


    public Task() {}

    public Task(String description, boolean isSolved) {
        this.description = description;
        this.isSolved = isSolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        if (!super.equals(o)) return false;
        return isSolved == task.isSolved && Objects.equals(description, task.description) && Objects.equals(employee, task.employee) && Objects.equals(manager, task.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, isSolved, employee, manager);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", isSolved=" + isSolved +
                ", employee=" + employee +
                ", manager=" + manager +
                '}';
    }
}