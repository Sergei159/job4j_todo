package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemStore {

    private final SessionFactory sf;

    private final Map<Integer, Item> items = new ConcurrentHashMap<>();

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public  List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public  Item create(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ru.job4j.todo.model.Item where name = :keyName");
        query.setParameter("keyName", key);
        List<Item> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public Item findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public  void update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public List<Item> findCompleted() {
        return findIsDone(true);
    }

    public List<Item> findTodo() {
        return findIsDone(false);
    }

    private List<Item> findIsDone(boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ru.job4j.todo.model.Item where isDone = :condition");
        List<Item> result = query.setParameter("condition", condition).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void completeById(int id) {
        changeItemIsDone(id, true);
    }

    public void resumeById(int id) {
        changeItemIsDone(id, false);
    }

    public void changeItemIsDone(int id, boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update ru.job4j.todo.model.Item i set i.isDone = :isDone where i.id = :id");
        query.setParameter("isDone", condition);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

}
