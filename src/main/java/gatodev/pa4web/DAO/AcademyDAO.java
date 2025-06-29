package gatodev.pa4web.DAO;

import gatodev.pa4web.config.DBConnector;
import gatodev.pa4web.models.Academy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcademyDAO implements DAO<Academy, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static AcademyDAO instance = new AcademyDAO();

    private AcademyDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS academies (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(30),
                    ruc VARCHAR(12)
                );
                """;

        try(Statement smt = con.createStatement()) {
            smt.execute(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Academy> save(Academy academy) throws SQLException {
        String query = """
                INSERT INTO academies (name, ruc)
                VALUES (?, ?)
                """;

        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, academy.getName());
        ps.setString(2, academy.getRuc());

        if(ps.executeUpdate() == 0) return Optional.empty();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) academy.setId(keys.getInt(1));

        return Optional.of(academy);
    }

    @Override
    public Optional<Academy> update(Academy academy) throws SQLException {
        String query = """
                UPDATE academies SET name = ?, ruc = ?
                WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, academy.getName());
        ps.setString(2, academy.getRuc());
        ps.setInt(3, academy.getId());

        return ps.executeUpdate() > 0 ? Optional.of(academy) : Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String query = """
                DELETE FROM academies WHERE id = ?
                """;
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public List<Academy> findAll() throws SQLException {
        List<Academy> academies = new ArrayList<>();
        String query = """
                SELECT * FROM academies
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            academies.add(Academy.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .ruc(rs.getString("ruc"))
                    .build());
        }

        return academies;
    }

    @Override
    public Optional<Academy> findById(Integer id) throws SQLException {
        String query = """
                SELECT * FROM academies WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        return !rs.next() ? Optional.empty() : Optional.of(Academy.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .ruc(rs.getString("ruc"))
                .build()
        );
    }
}
