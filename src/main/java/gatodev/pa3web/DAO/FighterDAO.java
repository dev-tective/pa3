package gatodev.pa3web.DAO;

import gatodev.pa3web.config.DBConnector;
import gatodev.pa3web.models.Fighter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FighterDAO implements DAO<Fighter, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static FighterDAO instance = new FighterDAO();

    //Siempre instanciar primero academy al iniciar el programa
    private FighterDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS fighters (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    full_name VARCHAR(60),
                    age INT,
                    weight VARCHAR(10),
                    gender VARCHAR(10),
                    ranked VARCHAR(15),
                    modality VARCHAR(20),
                    id_academy BIGINT REFERENCES academies
                );
                """;

        try(Statement smt = con.createStatement()) {
            smt.execute(sql);
        }
        catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Fighter> save(Fighter fighter) throws SQLException {
        String query = """
                INSERT INTO fighters (
                    full_name,
                    age,
                    weight,
                    gender,
                    ranked,
                    modality,
                    id_academy
                )
                VALUES (?,?,?,?,?,?,?);
                """;

        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setFighter(fighter, ps);

        if (ps.executeUpdate() == 0) return Optional.empty();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) fighter.setId(rs.getInt(1));

        return Optional.of(fighter);
    }

    @Override
    public Optional<Fighter> update(Fighter fighter) throws SQLException {
        String query = """
                UPDATE fighters SET
                    full_name = ?,
                    age = ?,
                    weight = ?,
                    gender = ?,
                    ranked = ?,
                    modality = ?,
                    id_academy = ?
                WHERE id = ?;
                """;

        PreparedStatement ps = con.prepareStatement(query);
        setFighter(fighter, ps);
        ps.setInt(8, fighter.getId());

        return ps.executeUpdate() == 0 ? Optional.empty() : Optional.of(fighter);
    }

    private void setFighter(Fighter fighter, PreparedStatement ps) throws SQLException {
        ps.setString(1, fighter.getFullName());
        ps.setInt(2, fighter.getAge());
        ps.setString(3, fighter.getWeight());
        ps.setString(4, fighter.getGender());
        ps.setString(5, fighter.getRank());
        ps.setString(6, fighter.getModality());
        ps.setInt(7, fighter.getIdAcademy());
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String query = """
                DELETE FROM fighters WHERE id = ?;
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public List<Fighter> findAll() throws SQLException {
        List<Fighter> fighters = new ArrayList<>();
        String query = """
                SELECT * FROM fighters;
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            fighters.add(Fighter.builder()
                    .id(rs.getInt("id"))
                    .fullName(rs.getString("full_name"))
                    .age(rs.getInt("age"))
                    .weight(rs.getString("weight"))
                    .gender(rs.getString("gender"))
                    .rank(rs.getString("ranked"))
                    .modality(rs.getString("modality"))
                    .idAcademy(rs.getInt("id_academy"))
                    .build());
        }

        return fighters;
    }

    @Override
    public Optional<Fighter> findById(Integer id) throws SQLException {
        String query = """
                SELECT * FROM fighters WHERE id = ?;
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        return !rs.next() ? Optional.empty() : Optional.of(Fighter.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("full_name"))
                .age(rs.getInt("age"))
                .weight(rs.getString("weight"))
                .gender(rs.getString("gender"))
                .rank(rs.getString("ranked"))
                .modality(rs.getString("modality"))
                .idAcademy(rs.getInt("id_academy"))
                .build());
    }
}
