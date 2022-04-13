package ru.job4j.todo.persistence;


import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class UserStore implements Store {
    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> add(User user) {
        try {

            transaction(session -> session.save(user), sf);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmailAndPwd(User user) {
        List<User> users = transaction(session -> session.createQuery(
                        "from ru.job4j.todo.model.User "
                                + " where email = :email and password = :password")
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword()).list(), sf);
        return Optional.ofNullable(users.get(0));
    }
}
