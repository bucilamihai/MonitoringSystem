package repository.database;

import domain.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;

import java.util.List;

public class EmployeeDBRepository {

    public Employee findByName(String name) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Employee> query = session.createQuery("FROM Employee WHERE name = :name", Employee.class);
        query.setParameter("name", name);
        Employee employee = query.uniqueResult();
        session.close();
        return employee;
    }

/*    public List<Employee> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Employee> query = session.createQuery("FROM Employee", Employee.class);
        List<Employee> employees = query.list();
        session.close();
        return employees;
    }*/

    public void save(Employee employee) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(employee);
        transaction.commit();
        session.close();
    }

    public void update(Employee employee) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(employee);
        transaction.commit();
        session.close();
    }

    public void delete(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.find(Employee.class, id);
        if (employee != null) {
            session.remove(employee);
        }
        transaction.commit();
        session.close();
    }
}