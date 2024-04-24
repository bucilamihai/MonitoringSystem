package repository.database;

import domain.Task;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;

import org.hibernate.Session;

import java.util.List;

public class TaskDBRepository {

    public List<Task> findAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Task> query = session.createQuery("FROM Task", Task.class);
        List<Task> tasks = query.list();
        session.close();
        return tasks;
    }

    public void save(Task task) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();
        session.close();
    }

    public void update(Task task) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(task);
        transaction.commit();
        session.close();
    }
}
