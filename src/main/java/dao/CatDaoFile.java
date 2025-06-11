package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import entity.Cat;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatDaoFile implements GenericDao<Cat> {
    private static final String FILE_PATH = "cats.json";
    private final Gson gson;
    private final List<Cat> cats;

    public CatDaoFile() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.cats = loadFromFile();
    }

    /* ----------- I/O ----------- */
    private List<Cat> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Cat>>() {}.getType();
            List<Cat> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cats, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ----------- CRUD ----------- */

    public void create(Cat cat) {
        if (read(cat.getNameCat()) != null) {
            throw new IllegalArgumentException("Gatto già presente: " + cat.getNameCat());
        }
        cats.add(cat);
        saveToFile();
    }
    @Override 
    public Cat read(Object...k){ 
    	 return cats.stream()
                 .filter(cat -> cat.getNameCat().equals(cat.getNameCat()))
                 .findFirst()
                 .orElse(null);
        }
    @Override public void delete(Object...k){
    	
        cats.removeIf(cat -> cat.getNameCat().equals(cat.getNameCat()));
        saveToFile();
        }

    public Cat read(String name) {
        return cats.stream()
                .filter(cat -> cat.getNameCat().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void update(Cat cat) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getNameCat().equals(cat.getNameCat())) {
                cats.set(i, cat);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Gatto non trovato: " + cat.getNameCat());
    }

    public void delete(String name) {
        cats.removeIf(cat -> cat.getNameCat().equals(name));
        saveToFile();
    }

    public List<Cat> readAll() {
        return new ArrayList<>(cats);
    }

    public List<Cat> readAdoptableCats() {
        List<Cat> adoptable = new ArrayList<>();
        for (Cat cat : cats) {
            if (!cat.isStateAdoption()) {
                adoptable.add(cat);
            }
        }
        return adoptable;
    }
}
