package dao;

import java.time.LocalDate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import entity.Booking;
import entity.BookingStatus;

/**
 * DAO in-memory (thread-safe): tiene le prenotazioni dentro una mappa
 * statica.  Replica l’API di BookingDaoDB e aggiunge findByStatus().
 */
public class BookingDaoMemory implements GenericDao<Booking> {

    /* ---------------- storage condiviso fra tutte le istanze ----- */
    private static final Map<Integer, Booking> STORE   = new ConcurrentHashMap<>();
    private static final AtomicInteger         COUNTER = new AtomicInteger(0);
    


    /* ---------------- CRUD --------------------------------------- */
    @Override
    public void create(Booking b) {
        int id = COUNTER.incrementAndGet();
        b.setId(id);
        STORE.put(id, cloneOf(b));
    }

    @Override
    public Booking read(Object... keys) {
        int id = keyToId(keys);
        return cloneOf(STORE.get(id));
    }

    @Override
    public void update(Booking b) {
        if (b.getId() == 0 || !STORE.containsKey(b.getId()))
            throw new IllegalArgumentException("Booking not found");
        STORE.put(b.getId(), cloneOf(b));
    }

    @Override
    public void delete(Object... keys) {
        int id = keyToId(keys);
        STORE.remove(id);
    }

    @Override
    public List<Booking> readAll() {
        return STORE.values().stream()
                    .map(this::cloneOf)
                    .toList();
    }

    /* ---------------- metodi aggiuntivi paralleli al DB ---------- */
    public boolean existsByUserAndCheckIn(String email, LocalDate day) {
        return STORE.values().stream()
                    .anyMatch(b -> b.getConfirmationEmail().equalsIgnoreCase(email)
                                && b.getDate().equals(day));
    }

    public Optional<Booking> findByUserAndCheckIn(String email, LocalDate day) {
        return STORE.values().stream()
                    .filter(b -> b.getConfirmationEmail().equalsIgnoreCase(email)
                              && b.getDate().equals(day))
                    .findFirst()
                    .map(this::cloneOf);
    }

    /** Restituisce le prenotazioni con uno stato specifico. */
    public List<Booking> findByStatus(BookingStatus status) {
        return STORE.values().stream()
                    .filter(b -> b.getStatus() == status)
                    .map(this::cloneOf)
                    .toList();
    }

    /* ---------------- utilità interne ---------------------------- */
    private int keyToId(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer id))
            throw new IllegalArgumentException("Key must be int id");
        return id;
    }

    /** Copia difensiva per evitare modifiche esterne allo storage. */
    private Booking cloneOf(Booking src) {
        if (src == null) return null;
        Booking b = new Booking();
        b.setId               (src.getId());
        b.setTitle            (src.getTitle());
        b.setDate             (src.getDate());
        b.setTime             (src.getTime());
        b.setSeats            (src.getSeats());
        b.setConfirmationEmail(src.getConfirmationEmail());
        b.setStatus           (src.getStatus());      // ← preserva lo stato
        return b;
    }
}