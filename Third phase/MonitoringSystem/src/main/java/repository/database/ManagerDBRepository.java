package repository.database;

import domain.Employee;
import domain.Manager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;

import java.util.List;

public class ManagerDBRepository {

    public Manager findByName(String name) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Manager> query = session.createQuery("FROM Manager WHERE name = :name", Manager.class);
        query.setParameter("name", name);
        Manager manager = query.uniqueResult();
        session.close();
        return manager;
    }

    public void save(Manager manager) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(manager);
        transaction.commit();
        session.close();
    }
}
