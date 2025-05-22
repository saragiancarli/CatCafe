package DAO;

import Entity.User;

public interface DaoFactoryInterface {
    
    GenericDao<User> getUserDao();
    
}