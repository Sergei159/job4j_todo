package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@ThreadSafe
@Repository
public class ItemStore implements Store {

    private final SessionFactory sf;

    private final Map<Integer, Item> items = new ConcurrentHashMap<>();

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }


    public  List<Item> findAll() {
        return transaction(session -> session.createQuery(
                "from ru.job4j.todo.model.Item").list(), sf);
    }

    public  Item create(Item item) {
         transaction(session -> session.save(item), sf);
         return item;
    }

    public List<Item> findByName(String key) {
        return transaction(session -> session.createQuery(
                "from ru.job4j.todo.model.Item where name = :keyName")
                .setParameter("keyName", key).list(), sf);
    }

    public Item findById(Integer id) {
        return transaction(session -> session.get(Item.class, id), sf);
    }

    public  void update(Item item) {
        transaction(session -> {
            session.update(item);
            return new Object();
        }, sf);
    }

    public void delete(int id) {
        Item item = new Item();
        item.setId(id);
        transaction(session -> {
            session.delete(item);
            return new Object();
        }, sf);
    }

    public List<Item> findCompleted() {
        return findIsDone(true);
    }

    public List<Item> findTodo() {
        return findIsDone(false);
    }

    private List<Item> findIsDone(boolean condition) {
        return transaction(session -> session.createQuery(
                "from ru.job4j.todo.model.Item where isDone = :condition")
                .setParameter("condition", condition).list(), sf);
    }

    public void completeById(int id) {
        changeItemIsDone(id, true);
    }

    public void resumeById(int id) {
        changeItemIsDone(id, false);
    }

    public void changeItemIsDone(int id, boolean condition) {
        transaction(session -> session.createQuery(
                "update ru.job4j.todo.model.Item i set i.isDone = :isDone where i.id = :id")
                .setParameter("isDone", condition)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }

}
