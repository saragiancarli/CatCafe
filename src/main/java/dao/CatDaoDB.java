package dao;

import entity.Cat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatDaoDB {

    private static final Logger logger = Logger.getLogger(CatDaoDB.class.getName());

    private final Connection conn;

    public CatDaoDB(Connection c) {
        this.conn = c;
    }

    public void create(Cat cat) throws SQLException {
        final String sql = """
            INSERT INTO Cat
              (nameCat, race, description, age, stateAdoption)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getNameCat());
            ps.setString(2, cat.getRace());
            ps.setString(3, cat.getDescription());
            ps.setInt(4, cat.getAge());
            ps.setBoolean(5, cat.isStateAdoption());
            ps.executeUpdate();
        }
    }

    public void update(Cat cat) throws SQLException {
        final String sql = """
            UPDATE Cat
               SET nameCat = ?,
                   race = ?,
                   description = ?,
                   age = ?,
                   stateAdoption = ?
             WHERE idCat = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cat.getNameCat());       // <-- nameCat prima
            ps.setString(2, cat.getRace());
            ps.setString(3, cat.getDescription());
            ps.setInt(4, cat.getAge());
            ps.setBoolean(5, cat.isStateAdoption());
            ps.setInt(6, cat.getIdCat());            // <-- idCat alla fine
            ps.executeUpdate();
        }
    }

    public void delete(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof Integer idCat)) {
            throw new IllegalArgumentException("Key must be an Integer idCat");
        }

        final String sql = "DELETE FROM Cat WHERE idCat = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCat);
            int rowsDeleted = ps.executeUpdate();
            logger.log(Level.INFO, "Righe eliminate: {0}", rowsDeleted);

        }
    }

    public Cat read(Object... keys) throws SQLException {
        if (keys.length != 1 || !(keys[0] instanceof String nameCat)) {
            throw new IllegalArgumentException("Key must be nameCat String");
        }

        final String sql = "SELECT *"+" FROM Cat WHERE nameCat = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nameCat);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /* -------------------------- helper map -------------------------- */
    private Cat map(ResultSet rs) throws SQLException {
        Cat cat = new Cat();
        cat.setIdCat(rs.getInt("idCat"));
        cat.setNameCat(rs.getString("nameCat"));
        cat.setRace(rs.getString("race"));
        cat.setDescription(rs.getString("description"));
        cat.setAge(rs.getInt("age"));
        cat.setStateAdoption(rs.getBoolean("stateAdoption"));
        return cat;
    }

    public List<Cat> readAdoptableCats() {
        List<Cat> cats = new ArrayList<>();
        String sql = "SELECT *"+" FROM Cat WHERE stateAdoption = false";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                cats.add(map(rs));
            }
        } catch (SQLException e) {
            throw new exception.CatDaoException("Errore nel recupero dei gatti adottabili", e);
        }

        return cats;
    }
}
