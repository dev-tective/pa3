package gatodev.pa4web.DAO;

import gatodev.pa4web.config.DBConnector;
import gatodev.pa4web.models.Fighter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FighterDAO implements DAO<Fighter, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static FighterDAO instance = new FighterDAO();

    private FighterDAO() {
        String sql = """
            CREATE TABLE IF NOT EXISTS fighters (
                id INT AUTO_INCREMENT PRIMARY KEY,
                dni VARCHAR(15) NOT NULL UNIQUE,
                full_name VARCHAR(60) NOT NULL,
                age INT NOT NULL,
                weight DOUBLE NOT NULL,
                gender VARCHAR(10) NOT NULL,
                rank VARCHAR(15) NOT NULL,
                modality VARCHAR(20) NOT NULL,
                id_academy INT NOT NULL,
                id_league INT NOT NULL,
                photo VARCHAR(255),
                FOREIGN KEY (id_academy) REFERENCES academies(id),
                FOREIGN KEY (id_league) REFERENCES leagues(id)
            );
            """;
        try (Statement smt = con.createStatement()) {
            smt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Fighter> save(Fighter f) throws SQLException {
        String query = """
            INSERT INTO fighters (
                dni, full_name, age, weight, gender,
                rank, modality, id_academy, id_league, photo
            ) VALUES (?,?,?,?,?,?,?,?,?,?);
        """;
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setFighter(ps, f);
        if (ps.executeUpdate() == 0) return Optional.empty();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) f.setId(rs.getInt(1));
        return Optional.of(f);
    }

    @Override
    public Optional<Fighter> update(Fighter f) throws SQLException {
        String query = """
            UPDATE fighters SET
                dni=?, full_name=?, age=?, weight=?, gender=?,
                rank=?, modality=?, id_academy=?, id_league=?, photo=?
            WHERE id = ?;
        """;
        PreparedStatement ps = con.prepareStatement(query);
        setFighter(ps, f);
        ps.setInt(11, f.getId());
        if (ps.executeUpdate() == 0) return Optional.empty();
        return Optional.of(f);
    }

    private void setFighter(PreparedStatement ps, Fighter f) throws SQLException {
        ps.setString(1, f.getDni());
        ps.setString(2, f.getFullName());
        ps.setInt(3, f.getAge());
        ps.setDouble(4, f.getWeight());
        ps.setString(5, f.getGender());
        ps.setString(6, f.getRank());
        ps.setString(7, f.getModality());
        ps.setInt(8, f.getIdAcademy());
        ps.setInt(9, f.getIdLeague());
        ps.setString(10, f.getPhoto());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM fighters WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public List<Fighter> findAll() throws SQLException {
        List<Fighter> list = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM fighters;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(map(rs));
        }
        return list;
    }

    @Override
    public Optional<Fighter> findById(Integer id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM fighters WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? Optional.of(map(rs)) : Optional.empty();
    }

    private Fighter map(ResultSet rs) throws SQLException {
        return Fighter.builder()
            .id(rs.getInt("id"))
            .dni(rs.getString("dni"))
            .fullName(rs.getString("full_name"))
            .age(rs.getInt("age"))
            .weight(rs.getDouble("weight"))
            .gender(rs.getString("gender"))
            .rank(rs.getString("rank"))
            .modality(rs.getString("modality"))
            .idAcademy(rs.getInt("id_academy"))
            .idLeague(rs.getInt("id_league"))
            .photo(rs.getString("photo"))
            .build();
    }
}

