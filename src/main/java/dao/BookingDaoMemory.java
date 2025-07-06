package dao;

import entity.Booking;
import entity.BookingStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAO in-memory (thread-safe).  Condivide la stessa API di BookingDaoDB
 * ma senza persistenza su disco; tutti i dati vivono nella mappa statica STORE.
 */
public class BookingDaoMemory implements GenericDao<Booking> {

    /* ========================== STORAGE ========================== */
    private static final Map<Integer, Booking> STORE   = new ConcurrentHashMap<>();
    private static final AtomicInteger         COUNTER = new AtomicInteger(0);

    /* ============================ CRUD =========================== */
    @Override
    public void create(Booking b) {
        int id = COUNTER.incrementAndGet();
        b.setId(id);
        STORE.put(id, deepCopy(b));
    }

    @Override
    public Booking read(Object... keys) {
        int id = keyToId(keys);
        return deepCopy(STORE.get(id));
    }

    @Override
    public void update(Booking b) {
        if (b.getId() == 0 || !STORE.containsKey(b.getId()))
            throw new IllegalArgumentException("Booking not found");
        STORE.put(b.getId(), deepCopy(b));
    }

    @Override
    public void delete(Object... keys) {
        int id = keyToId(keys);
        STORE.remove(id);
    }

    @Override
    public List<Booking> readAll() {
        return STORE.values().stream()
                    .map(this::deepCopy)
                    .toList();
    }

    /* ======================= EXTRA API =========================== */
    public boolean existsByUserAndCheckIn(String email, LocalDate day) {
        return STORE.values().stream()
                    .anyMatch(b -> emailEquals(b, email) && b.getDate().equals(day));
    }

    public Optional<Booking> findByUserAndCheckIn(String email, LocalDate day) {
        return STORE.values().stream()
                    .filter(b -> emailEquals(b, email) && b.getDate().equals(day))
                    .findFirst()
                    .map(this::deepCopy);
    }

    /** Restituisce tutte le prenotazioni che hanno lo stato indicato. */
    public List<Booking> findByStatus(BookingStatus status) {
        return STORE.values().stream()
                    .filter(b -> b.getStatus() == status)
                    .map(this::deepCopy)
                    .toList();
    }

    /* ===================== UTILITY PRIVATE ======================= */
    private int keyToId(Object... keys) {
        if (keys.length != 1 || !(keys[0] instanceof Integer id))
            throw new IllegalArgumentException("Key must be int id");
        return id;
    }

    /** Confronto case-insensitive che gestisce `null`. */
    private boolean emailEquals(Booking b, String email) {
        return email != null && email.equalsIgnoreCase(b.getConfirmationEmail());
    }

    /** Crea una copia “profonda” sufficiente per proteggere lo STORE. */
    private Booking deepCopy(Booking src) {
        if (src == null) return null;

        Booking b = new Booking();
        b.setId               (src.getId());
        b.setTitle            (src.getTitle());
        b.setDate             (src.getDate());
        b.setTime             (src.getTime());
        b.setSeats            (src.getSeats());
        b.setConfirmationEmail(src.getConfirmationEmail());
        b.setStatus           (src.getStatus());

        // copia difensiva della lista attività (mai null)
        List<String> acts = src.getFreeActivities();
        b.setFreeActivities(new ArrayList<>(acts));

        return b;
    }
}
