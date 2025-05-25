package dao;

import entity.Booking;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingDaoFile implements GenericDao<Booking> {

    private static final String FILE_PATH = "bookings.json";
    private final Gson gson;
    private List<Booking> list;
    private final AtomicInteger cOUNTER;              // per id auto-increment

    public BookingDaoFile() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .setPrettyPrinting().create();
        list = load();
        cOUNTER = new AtomicInteger(list.stream()
                                        .mapToInt(Booking::getId)
                                        .max().orElse(0));
    }

    /* ---------- I/O ---------- */
    private List<Booking> load() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type t = new TypeToken<List<Booking>>(){}.getType();
            List<Booking> l = gson.fromJson(r,t);
            return l!=null?l:new ArrayList<>();
        } catch (IOException e){ e.printStackTrace(); return new ArrayList<>(); }
    }
    private void save(){ try(Writer w=new FileWriter(FILE_PATH)){ gson.toJson(list,w);}catch(IOException e){e.printStackTrace();}}

    /* ---------- CRUD ---------- */
    @Override public void create(Booking b){
        b.setId(cOUNTER.incrementAndGet());
        list.add(b); save();
    }
    @Override public Booking read(Object...k){ 
    	int id=(int)k[0];
        return list.stream().filter(b->b.getId()==id).findFirst().orElse(null);
        }
    
    @Override public void update(Booking b){ delete(b.getId());
    list.add(b); 
    save();
    }
    @Override public void delete(Object...k){
    	int id=(int)k[0];
        list.removeIf(b->b.getId()==id);
        save();
        }
    @Override public List<Booking> readAll(){
    	return new ArrayList<>(list); 
    	}

    
    public boolean existsByUserAndCheckIn(String mail, LocalDate d){
        return list.stream().anyMatch(b->b.getConfirmationEmail().equalsIgnoreCase(mail)
                                     && b.getDate().equals(d));
    }
}