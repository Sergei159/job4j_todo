package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.util.List;

@ThreadSafe
@Service
public class ItemService {


    private final ItemStore store;

    public ItemService (ItemStore store) {
        this.store = store;
    }

    public  List<Item> findAll() {
        return store.findAll();
    }

    public Item create(Item item){
        return store.create(item);
    }

    public  List<Item> findByName(String key) {
        return store.findByName(key);
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public void update(Item item) {
        store.update(item);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public  List<Item> findCompleted() {
        return store.findCompleted();
    }

    public  List<Item> findTodo() {
        return store.findTodo();
    }

    public void resumeById(int id) {
        store.resumeById(id);
    }

    public void completeById(int id) {
        store.completeById(id);
    }

}
