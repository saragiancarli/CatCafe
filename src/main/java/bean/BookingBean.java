package bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class BookingBean {

    private int          id;
    private String       nomePrenotazione;
    private LocalDate    data;
    private LocalTime    ora;
    private int          numeroPartecipanti;
    private String       email;
    private List<String> freeActivities = List.of();

    /* ---------- getter / setter ---------- */
    public int getId() {
    	return id; 
    	}
    public void setId(int id) {
    	this.id = id; 
    	}

    public String getTitle()  { 
    	return nomePrenotazione; 
    	}
    public void   setTitle(String t) {
    	this.nomePrenotazione = t; 
    	}

    public LocalDate getDate() {
    	return data; 
    	}
    public void      setDate(LocalDate d) {
    	this.data = d; 
    	}

    public LocalTime getTime()   {
    	return ora; 
    	}
    public void      setTime(LocalTime t)  {
    	this.ora = t; 
    	}

    public int getSeats()  {
    	return numeroPartecipanti;
    	}
    public void setSeats(int s) {
this.numeroPartecipanti = s;
}

    public String getConfirmationEmail()  {
    	return email; 
    	}
    public void   setConfirmationEmail(String e){
    	this.email = e; 
    	}

    public List<String> getFreeActivities()   {
    	return freeActivities; 
    	}
    public void setFreeActivities(List<String> l){
        this.freeActivities = l == null ? List.of() : List.copyOf(l);
    }
}