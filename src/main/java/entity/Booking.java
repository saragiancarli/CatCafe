package entity;


import javafx.beans.property.*;


import java.time.*;

public class Booking  {

    private  final IntegerProperty            id    = new SimpleIntegerProperty();
    private final StringProperty             title = new SimpleStringProperty();
    private final ObjectProperty<LocalDate>  date  = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime>  time  = new SimpleObjectProperty<>();
    private final IntegerProperty            seats = new SimpleIntegerProperty();
    private final StringProperty             email = new SimpleStringProperty();
    private final ObjectProperty<BookingStatus> status =
            new SimpleObjectProperty<>(BookingStatus.PENDING);

    /* ---------------- stato ---------------- */
    public void confirm () {
    	status.set(BookingStatus.BOOKED   );
    	}
    public void cancel  () { 
    	status.set(BookingStatus.CANCELLED );
    	}

    /* ---------------- getter/setter/property ---------------- */
    public int getId() { 
    	return id.get(); 
    	}
    public void setId(int v){ 
    	id.set(v); 
    	}
    public IntegerProperty idProperty(){
    	return id; 
    	}

    public String getTitle(){
    	return title.get(); 
    	}
    public void setTitle(String v)  {
    	title.set(v);
    	}
    public StringProperty titleProperty()    { 
    	return title; 
    	}

    public LocalDate getDate() {
    	return date.get(); 
    	}
    public void setDate(LocalDate v) { 
    	date.set(v);
    	}
    public ObjectProperty<LocalDate> dateProperty() {
    	return date; 
    	}

    public LocalTime getTime()      {
    	return time.get();
    	}
    public void setTime(LocalTime v)  {
    	time.set(v);
    	}
    public ObjectProperty<LocalTime> timeProperty() {
    	return time;
    	}

    public int getSeats()    {
    	return seats.get(); 
    	}
    public void setSeats(int v)   {
    	seats.set(v); 
    	}
    public IntegerProperty seatsProperty()   {
    	return seats; 
    	}

    public String getConfirmationEmail()      {
    	return email.get();
    	}
    public void setConfirmationEmail(String v)     {
    	email.set(v);
    	}
    public StringProperty emailProperty()   {
    	return email; 
    	}

    public BookingStatus getStatus()  {
    	return status.get();
    	}
    public void setStatus(BookingStatus v)   {
    	status.set(v); 
    	}
    public ObjectProperty<BookingStatus> statusProperty() {
    	
    	return status; 
    	}

    @Override public String toString() { /* utile per debug */ return
        "Booking["+id.get()+", "+title.get()+", "+date.get()+" "+time.get()+
        ", "+seats.get()+", "+status.get()+"]";
    }
}