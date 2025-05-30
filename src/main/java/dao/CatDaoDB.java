package dao;

import entity.Cat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatDaoDB {
    private final Connection conn;

    public CatDaoDB(Connection conn) {
        this.conn = conn;
    }

    public List<Cat> readAdoptableCats() {
        List<Cat> cats = new ArrayList<>();
        String sql = "SELECT * " + "FROM Cat WHERE stateAdoption = false";  // o 0 se booleano è int

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cat cat = new Cat();
                cat.setNameCat(rs.getString("nameCat"));
                cat.setRace(rs.getString("race"));
                cat.setAge(rs.getInt("age"));
                cat.setDescription(rs.getString("description"));
                cat.setStateAdoption(rs.getBoolean("stateAdoption"));
                cats.add(cat);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dei gatti adottabili", e);
        }

        return cats;
    }
}

