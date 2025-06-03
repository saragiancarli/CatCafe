package dao;

import entity.Adoption;
import exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DAO JDBC per la tabella <code>adoption_requests</code>. */
public class RequestAdoptionDaoDB implements BeanDao<Adoption> {

    private final Connection conn;

    public RequestAdoptionDaoDB(Connection c) {
        this.conn = c;
    }

    /* --------------------------create------------------------------ */

    @Override
    public void create(Adoption bean) throws SQLException {
        final String sql = """
            INSERT INTO adoption_requests
              (nameCat, phoneNumber, name, surname, email, address, stateAdoption)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bean.getNameCat());
            ps.setString(2, bean.getPhoneNumber());
            ps.setString(3, bean.getName());
            ps.setString(4, bean.getSurname());
            ps.setString(5, bean.getEmail());
            ps.setString(6, bean.getAddress());
            ps.setBoolean(7, bean.getStateAdoption());
            ps.executeUpdate();
        }
    }

    /* -------------------------read------------------------------- */

    @Override
    public Adoption read(Object... keys) throws SQLException {
        if (keys.length != 2 || !(keys[0] instanceof String email) || !(keys[1] instanceof String nameCat)) {
            throw new IllegalArgumentException("Keys must be email and nameCat");
        }

        final String sql = "SELECT * " +
                "FROM adoption_requests WHERE email = ? AND nameCat = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, nameCat);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /* -------------------------update------------------------------- */

    @Override
    public void update(Adoption bean) throws SQLException {
        final String sql = """
            UPDATE adoption_requests
    SET phoneNumber = ?, name = ?, surname = ?, address = ?, stateAdoption = ?
    WHERE email = ? AND nameCat = ?
""";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bean.getPhoneNumber());
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getSurname());
            ps.setString(4, bean.getAddress());
            ps.setBoolean(5, bean.getStateAdoption());
            ps.setString(6, bean.getEmail());
            ps.setString(7, bean.getNameCat());
            ps.executeUpdate();
        }
    }

    /* ------------------------delete-------------------------------- */

    @Override
    public void delete(Object... keys) throws SQLException {
        if (keys.length != 2 || !(keys[0] instanceof String email) || !(keys[1] instanceof String nameCat)) {
            throw new IllegalArgumentException("Keys must be email and nameCat");
        }

        final String sql = "DELETE FROM adoption_requests WHERE email = ? AND nameCat = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, nameCat);
            ps.executeUpdate();
        }
    }

    /* ------------------------show-details---------------------------- */

    @Override
    public List<Adoption> readAll() {
        List<Adoption> list = new ArrayList<>();

        final String sql = "SELECT * " + "FROM adoption_requests";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Errore nel recupero di tutte le richieste di adozione", e);
        }

        return list;
    }
    public boolean existsByEmailAndCat(String email, String nameCat) throws SQLException {
        String sql = "SELECT COUNT(*) FROM adoption_requests WHERE email = ? AND nameCat = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, nameCat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    /* -----------------------helper-method------------------------------- */

    private Adoption map(ResultSet rs) throws SQLException {
        Adoption bean = new Adoption();
        bean.setNameCat(rs.getString("nameCat"));
        bean.setPhoneNumber(rs.getString("phoneNumber"));
        bean.setName(rs.getString("name"));
        bean.setSurname(rs.getString("surname"));
        bean.setEmail(rs.getString("email"));
        bean.setAddress(rs.getString("address"));
        bean.setStateAdoption(rs.getBoolean("stateAdoption"));
        return bean;
    }
}
