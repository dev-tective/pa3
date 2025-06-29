package gatodev.pa4web.DAO;

import gatodev.pa4web.config.DBConnector;
import gatodev.pa4web.models.League;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeagueDAO implements DAO<League, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static LeagueDAO instance = new LeagueDAO();

    private LeagueDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS leagues (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(60)
                );
                """;

        try(Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<League> save(League league) throws SQLException {
        String query = """
                INSERT INTO leagues (name)
                VALUES (?)
                """;

        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, league.getName());

        if(ps.executeUpdate() == 0) return Optional.empty();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) league.setId(keys.getInt(1));

        return Optional.of(league);
    }

    @Override
    public Optional<League> update(League league) throws SQLException {
        String query = """
                UPDATE leagues SET name = ?
                WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, league.getName());
        ps.setInt(2, league.getId());

        return ps.executeUpdate() > 0 ? Optional.of(league) : Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String query = """
                DELETE FROM leagues WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public List<League> findAll() throws SQLException {
        List<League> leagues = new ArrayList<>();
        String query = """
                SELECT * FROM leagues
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            leagues.add(League.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build());
        }

        return leagues;
    }

    @Override
    public Optional<League> findById(Integer id) throws SQLException {
        String query = """
                SELECT * FROM leagues WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        return !rs.next() ? Optional.empty() : Optional.of(League.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }
}
