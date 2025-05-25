package bean;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Booking;


public class ManageBookingBean {

    /* ---------- campi ---------- */
    private final List<Booking> bookings = new ArrayList<>();
    private Booking selected;

    /* ---------- costruttori ---------- */
    /** Costruttore vuoto (se ti serve ancora). */
    public ManageBookingBean() { }

    /** ❶ Costruttore che accetta subito la prenotazione
        – è quello richiesto dal riferimento  ManageBookingBean::new  */
    public ManageBookingBean(Booking booking) {
        this.selected = booking;
    }

    /* ---------- lista completa ---------- */
    public void setBookings(List<Booking> list) {
        bookings.clear();
        if (list != null) bookings.addAll(list);
    }
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    /* ---------- selezione ---------- */
    public void   setSelected(Booking b) {
    	this.selected = b;
    	}
    public Booking getSelected() {
    	return selected; 
    	}
    public boolean hasSelection() {
    	return selected != null; 
    	}
    
   
}