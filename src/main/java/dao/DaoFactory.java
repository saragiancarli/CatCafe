package dao;

import java.util.List;

import entity.*;

public class DaoFactory implements DaoFactoryInterface {

    /* ---------- singleton ---------- */
    private static final DaoFactory INSTANCE = new DaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    /* ---------- configurazione ---------- */
    public enum Store {DATABASE, FILE, STATELESS}

    private static Store storageOption = Store.STATELESS;      // default

    public static void setStorageOption(Store opt) {
        storageOption = opt;
    }

    /* ---------- cache DAO in-memory ---------- */
    private static UserDaoMemory userDaoMemoryInstance;
    private static StafDaoMemory stafDaoMemoryInstance;
    private static BookingDaoMemory bookingDaoMemoryInstance;
    private static CatDaoMemory catDaoMemoryInstance;
    private static RequestAdoptionDaoMemory requestAdoptionMemoryInstance;
    private static GenericDao<Activity> activityDaoMemoryInstance;
   


    private static GenericDao<User> userDaoFileInstance;
    private static GenericDao<Staf> stafDaoFileInstance;
    private static GenericDao<Booking> bookingDaoFileInstance;
    private static GenericDao<Cat> catDaoFileInstance;
    private static BeanDao<Adoption> requestAdoptionDaoFileInstance;
    private static GenericDao<Activity> activityDaoFileInstance;

    /* ---------- costruttore privato ---------- */
    public DaoFactory() {

        // Costruttore privato per nascondere quello pubblico implicito
    }

    /* ---------- DAO di istanza ---------- */
    public GenericDao<User> getUserDao() {
        return switch (storageOption) {
            case DATABASE -> new UserDaoDB(DatabaseConnectionManager.getConnection());
            case FILE -> getUserFileInstance();
            default -> getUserMemoryInstance();
        };
    }

    public GenericDao<Staf> getStafDao() {
        return switch (storageOption) {
            case DATABASE -> new StafDaoDB(DatabaseConnectionManager.getConnection());
            case FILE -> getStafFileInstance();
            default -> getStafMemoryInstance();
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
                return new BookingDaoDB(DatabaseConnectionManager.getConnection());

            }

            case FILE -> {
                return getBookingFileInstance();
            }

            default -> {                              // STATELESS
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

    public BeanDao<Adoption> getRequestAdoptionDao() {
        switch (storageOption) {
            case DATABASE -> {return new RequestAdoptionDaoDB(DatabaseConnectionManager.getConnection());}
            case FILE -> {return getRequestAdoptionFileInstance();}
            default -> {return getRequestAdoptionMemoryInstance();}
        }
    }

    private static BeanDao<Adoption> getRequestAdoptionFileInstance() {
        if (requestAdoptionDaoFileInstance == null)
            requestAdoptionDaoFileInstance = new RequestAdoptionDaoFile();
        return requestAdoptionDaoFileInstance;
    }

    private static RequestAdoptionDaoMemory getRequestAdoptionMemoryInstance() {
        if (requestAdoptionMemoryInstance == null)
            requestAdoptionMemoryInstance = new RequestAdoptionDaoMemory();
        return requestAdoptionMemoryInstance;
    }
    public GenericDao<Cat> getCatDao() {
        return switch (storageOption) {
            case DATABASE -> new CatDaoDB(DatabaseConnectionManager.getConnection());
            case FILE -> getCatFileInstance(); // solo se esiste CatDaoFile
            default -> getCatMemoryInstance();
        };
    }
    private static GenericDao<Cat> getCatFileInstance() {
        if (catDaoFileInstance == null)
            catDaoFileInstance = new CatDaoFile(); // implementare se serve
        return catDaoFileInstance;
    }
    private static CatDaoMemory getCatMemoryInstance() {
        if (catDaoMemoryInstance == null)
            catDaoMemoryInstance = new CatDaoMemory();
        return catDaoMemoryInstance;
    }
    
    public GenericDao<Activity> getActivityDao() {
        return switch (storageOption) {
            case DATABASE -> new ActivityDaoDB(DatabaseConnectionManager.getConnection());
            case FILE -> getActivityFileInstance(); 
            default -> getActivityMemoryInstance();
        };
    }
    
    
    
    
    

    public static GenericDao<Activity> getActivityFileInstance() {
        if (activityDaoFileInstance == null) {
            activityDaoFileInstance = new ActivityDaoFile();
        }
        return activityDaoFileInstance;
    }

    public static ActivityDaoMemory getActivityMemoryInstance(){
        if (activityDaoMemoryInstance == null) {
            activityDaoMemoryInstance = new ActivityDaoMemory();
        }
        return (ActivityDaoMemory) activityDaoMemoryInstance;
    }
    public static List<Activity> getAvailableActivities() {
        DaoFactory daoFactory = new DaoFactory();
        GenericDao<Activity> activityDao = daoFactory.getActivityDao();
        return activityDao.readAll(); // Metodo da implementare nel DAO
    }

}
