package bean;


import java.time.LocalDate;
import java.time.LocalTime;

public class BookingBean {
	private int id;

    private String     nomePrenotazione;
    private LocalDate  data;
    private LocalTime  ora;
    private int        numeroPartecipanti;
    private String email;

    /*======= mini-validazioni =======*/
    public boolean hasValidTitle()   {
    	return nomePrenotazione != null && !nomePrenotazione.isBlank(); 
    	}
    public boolean hasValidDates() {
        return data != null && ora != null
            && !data.isBefore(LocalDate.now());      // il giorno non è nel passato
                 // l’ora non è nel passato
    }

    public boolean hasValidSeats()   { return numeroPartecipanti > 0 && numeroPartecipanti <= 50; }

    /*======= getter / setter =======*/
    public int getId(){
    	return id;
    	}
    public void setId(int i) {
    	id=i;
    	}
    public String  getTitle()  { 
    	return nomePrenotazione;
    	}
    public void   setTitle(String t)    {
    	nomePrenotazione = t; 
    	}
    public LocalDate getDate()      {
    	return data; 
    	}
    public void  setDate(LocalDate d)   {
    	data = d; 
    	}
    public LocalTime getTime()     {
    	return ora; 
    	}
    public void setTime(LocalTime d)  {
    	ora = d; 
    	}
    public int   getSeats()     {
    	return numeroPartecipanti;
    	}
    public void   setSeats(int s)     {
    	numeroPartecipanti = s; 
    	}
    public String   getConfirmationEmail()      {
    	return email; 
    	}
    public void    setConfirmationEmail(String e)   { 
    	email = e; 
    	}
}