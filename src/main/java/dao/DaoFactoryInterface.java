package dao;

import Entity.User;

public interface DaoFactoryInterface {
    
    GenericDao<User> getUserDao();
    
}