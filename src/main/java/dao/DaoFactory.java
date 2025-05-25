package dao;

import entity.Booking;
import entity.Staf;
import entity.User;

public class DaoFactory implements DaoFactoryInterface {

    /* ---------- singleton ---------- */
    private static final DaoFactory INSTANCE = new DaoFactory();
    public  static DaoFactory getInstance() { return INSTANCE; }

    /* ---------- configurazione ---------- */
    public enum Store { DATABASE, FILE, STATELESS }
    private static Store storageOption = Store.STATELESS;      // default
    public static void setStorageOption(Store opt) { storageOption = opt; }

    /* ---------- cache DAO in-memory ---------- */
    private static UserDaoMemory userDaoMemoryInstance;
    private static StafDaoMemory stafDaoMemoryInstance;
    private static BookingDaoMemory bookingDaoMemoryInstance;
    
    private static GenericDao<User> userDaoFileInstance;
    private static GenericDao<Staf> stafDaoFileInstance;
    private static GenericDao<Booking> bookingDaoFileInstance;

    /* ---------- costruttore privato ---------- */
    public DaoFactory() { 
    	
    	// Costruttore privato per nascondere quello pubblico implicito
    }

    /* ---------- DAO di istanza ---------- */
    public GenericDao<User> getUserDao() {
        return switch (storageOption) {
            case DATABASE -> new UserDaoDB(DatabaseConnectionManager.getConnection());
            case FILE   -> getUserFileInstance();
            default       -> getUserMemoryInstance();
        };
    }

    public GenericDao<Staf> getStafDao() {
        return switch (storageOption) {
            case DATABASE -> new StafDaoDB(DatabaseConnectionManager.getConnection());
            case FILE   -> getStafFileInstance();
            default       -> getStafMemoryInstance();
        };
    }
    
    private static GenericDao<User> getUserFileInstance() {
        if (userDaoFileInstance == null)
        	userDaoFileInstance = new UserDaoFile();
        return userDaoFileInstance;
    }

    /* ---------- helper per cache ---------- */
    private static UserDaoMemory getUserMemoryInstance() {
        if (userDaoMemoryInstance == null)
            userDaoMemoryInstance = new UserDaoMemory();
        return userDaoMemoryInstance;
    }
    
    private static GenericDao<Staf> getStafFileInstance() {
        if (stafDaoFileInstance == null)
        	stafDaoFileInstance = new StafDaoFile();
        return stafDaoFileInstance;
    }
    
    private static StafDaoMemory getStafMemoryInstance() {
        if (stafDaoMemoryInstance == null)
            stafDaoMemoryInstance = new StafDaoMemory();
        return stafDaoMemoryInstance;
    }
    public GenericDao<Booking> getBookingDao() {
    	
        switch (storageOption) {

            case DATABASE -> {
               return new BookingDaoDB( DatabaseConnectionManager.getConnection());
                
            }

             case FILE -> {
            	 return getBookingFileInstance();
             }

            default  -> {                              // STATELESS
                return getBookingMemoryInstance();
            }
        }
    }
    
    private static GenericDao<Booking> getBookingFileInstance() {
        if (bookingDaoFileInstance == null)
        	bookingDaoFileInstance = new BookingDaoFile();
        return bookingDaoFileInstance;
    }

    /* ---- singleton in-memory --------------------------------- */
    private static BookingDaoMemory getBookingMemoryInstance() {
        if (bookingDaoMemoryInstance == null)
            bookingDaoMemoryInstance = new BookingDaoMemory();
        return bookingDaoMemoryInstance;
    }
    
}