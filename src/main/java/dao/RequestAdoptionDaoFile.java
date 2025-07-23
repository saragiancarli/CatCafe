package dao;

import entity.Adoption;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RequestAdoptionDaoFile implements GenericDao<Adoption> {

    private static final String FILE_PATH = "request_adoptions.json";
    private final Gson gson;
    private List<Adoption> list;

    public RequestAdoptionDaoFile() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        list = load();
    }

    /* ---------- I/O ---------- */
    private static final Logger LOG = Logger.getLogger(RequestAdoptionDaoFile.class.getName());

    private List<Adoption> load() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type t = new TypeToken<List<Adoption>>() {}.getType();
            List<Adoption> l = gson.fromJson(r, t);
            return l != null ? l : new ArrayList<>();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Errore caricamento file " + FILE_PATH, e);
            return new ArrayList<>();
        }
    }

    private void save() {
        try (Writer w = new FileWriter(FILE_PATH)) {
            gson.toJson(list, w);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Errore salvataggio file " + FILE_PATH, e);
        }
    }

    /* ---------- CRUD ---------- */
    @Override
    public void create(Adoption bean) {
        // Prima verifica che non esista già una richiesta con la stessa chiave composta
        if (exists(bean.getNameCat(), bean.getEmail())) {
            throw new IllegalArgumentException("Request with same nomeGatto and email already exists");
        }
        list.add(bean);
        save();
    }

    @Override
    public Adoption read(Object... keys) {
        if (keys.length < 2) throw new IllegalArgumentException("Two keys required: nomeGatto and email");
        String nomeGatto = (String) keys[0];
        String email = (String) keys[1];
        return list.stream()
                .filter(b -> b.getNameCat().equalsIgnoreCase(nomeGatto) &&
                        b.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Adoption bean) {
        // Trova ed elimina la vecchia richiesta con la stessa chiave composta
        list = list.stream()
                .filter(b -> !(b.getNameCat().equalsIgnoreCase(bean.getNameCat()) &&
                        b.getEmail().equalsIgnoreCase(bean.getEmail())))
                .collect(Collectors.toList());
        // Aggiungi la versione aggiornata
        list.add(bean);
        save();
    }

    @Override
    public void delete(Object... keys) {
        if (keys.length < 2) throw new IllegalArgumentException("Two keys required: nomeGatto and email");
        String nomeGatto = (String) keys[0];
        String email = (String) keys[1];
        list.removeIf(b -> b.getNameCat().equalsIgnoreCase(nomeGatto) &&
                b.getEmail().equalsIgnoreCase(email));
        save();
    }

    @Override
    public List<Adoption> readAll() {
        return new ArrayList<>(list);
    }

    /* --- Metodi di utilità extra --- */
    public boolean exists(String nomeGatto, String email) {
        return list.stream().anyMatch(b -> b.getNameCat().equalsIgnoreCase(nomeGatto) &&
                b.getEmail().equalsIgnoreCase(email));
    }
}
