package dao;

import entity.Cat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatDaoMemory implements GenericDao<Cat> {
    private final List<Cat> storage;

    public CatDaoMemory() {
        storage = new ArrayList<>();

        // Gatti di esempio
        Cat c1 = new Cat();
        c1.setNameCat("Micio");
        c1.setRace("Siamese");
        c1.setAge(3);
        c1.setDescription("Gatto molto affettuoso");
        c1.setStateAdoption(false);
        storage.add(c1);

        Cat c2 = new Cat();
        c2.setNameCat("Luna");
        c2.setRace("Persiano");
        c2.setAge(2);
        c2.setDescription("Gatta tranquilla");
        c2.setStateAdoption(false);
        storage.add(c2);
    }

    @Override
    public void create(Cat cat) throws SQLException {
        if (cat == null) throw new SQLException("Cat cannot be null");
        storage.add(cat);
    }

    @Override
    public Cat read(Object... keys) {
        if (keys == null || keys.length == 0) return null;
        String name = (String) keys[0];
        return storage.stream()
                .filter(cat -> cat.getNameCat().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Cat cat) throws SQLException {
        if (cat == null) throw new SQLException("Cat cannot be null");
        Cat existing = read(cat.getNameCat());
        if (existing != null) {
            storage.remove(existing);
            storage.add(cat);
        } else {
            throw new SQLException("Cat not found");
        }
    }

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys == null || keys.length == 0) throw new SQLException("No key provided");
        String name = (String) keys[0];
        Cat existing = read(name);
        if (existing != null) {
            storage.remove(existing);
        } else {
            throw new SQLException("Cat not found");
        }
    }

    @Override
    public List<Cat> readAll() {
        return new ArrayList<>(storage);
    }

    public List<Cat> readAdoptableCats() {
        List<Cat> adoptable = new ArrayList<>();
        for (Cat cat : storage) {
            if (!cat.isStateAdoption()) {
                adoptable.add(cat);
            }
        }
        return adoptable;
    }
}
