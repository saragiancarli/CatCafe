package dao;

import bean.RequestAdoptionBean;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DAO in-memory (thread-safe) per RequestAdoptionBean,
 * usa come chiave composta nomeGatto + email concatenati.
 */
public class RequestAdoptionDaoMemory implements BeanDao<RequestAdoptionBean> {

    private static final Map<String, RequestAdoptionBean> STORE = new ConcurrentHashMap<>();

    /* ---------------- CRUD --------------------------------------- */
    @Override
    public void create(RequestAdoptionBean bean) {
        String key = keyOf(bean.getNameCat(), bean.getEmail());
        if (STORE.containsKey(key)) {
            throw new IllegalArgumentException("RequestAdoption already exists for this nomeGatto and email");
        }
        STORE.put(key, cloneOf(bean));
    }

    @Override
    public RequestAdoptionBean read(Object... keys) {
        String key = keyFromKeys(keys);
        return cloneOf(STORE.get(key));
    }

    @Override
    public void update(RequestAdoptionBean bean) {
        String key = keyOf(bean.getNameCat(), bean.getEmail());
        if (!STORE.containsKey(key)) {
            throw new IllegalArgumentException("RequestAdoption not found");
        }
        STORE.put(key, cloneOf(bean));
    }

    @Override
    public void delete(Object... keys) {
        String key = keyFromKeys(keys);
        STORE.remove(key);
    }

    @Override
    public List<RequestAdoptionBean> readAll() {
        List<RequestAdoptionBean> list = new ArrayList<>();
        for (RequestAdoptionBean bean : STORE.values()) {
            list.add(cloneOf(bean));
        }
        return list;
    }

    /* ------------- Metodi di utilità ------------------- */
    private String keyOf(String nomeGatto, String email) {
        if (nomeGatto == null || email == null) {
            throw new IllegalArgumentException("nomeGatto and email must be not null");
        }
        return nomeGatto.toLowerCase() + "::" + email.toLowerCase();
    }

    private String keyFromKeys(Object... keys) {
        if (keys.length < 2)
            throw new IllegalArgumentException("Two keys required: nomeGatto and email");
        if (!(keys[0] instanceof String) || !(keys[1] instanceof String))
            throw new IllegalArgumentException("Keys must be String nomeGatto and String email");
        return keyOf((String) keys[0], (String) keys[1]);
    }

    private RequestAdoptionBean cloneOf(RequestAdoptionBean src) {
        if (src == null) return null;
        RequestAdoptionBean copy = new RequestAdoptionBean();
        copy.setNameCat(src.getNameCat());
        copy.setPhoneNumber(src.getPhoneNumber());
        copy.setName(src.getName());
        copy.setSurname(src.getSurname());
        copy.setEmail(src.getEmail());
        copy.setAddress(src.getAddress());
        copy.setStateAdoption(src.isStateAdoption());
        return copy;
    }
}
