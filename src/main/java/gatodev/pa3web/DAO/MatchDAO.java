package gatodev.pa3web.DAO;

import gatodev.pa3web.config.DBConnector;
import gatodev.pa3web.models.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchDAO implements DAO<Match, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final MatchDAO instance = new MatchDAO();

    private MatchDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS matches (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    phase VARCHAR(30),
                    state VARCHAR(20),
                    id_league BIGINT REFERENCES leagues,
                    first_fighter BIGINT REFERENCES fighters,
                    second_fighter BIGINT REFERENCES fighters,
                    winning_fighter BIGINT REFERENCES fighters
                );
                """;
        
        try(Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Match> save(Match match) throws SQLException {
        String query = """
                INSERT INTO matches(
                    phase,
                    state,
                    id_league,
                    first_fighter,
                    second_fighter,
                    winning_fighter
                )
                VALUES(?,?,?,?,?,?)
                """;
        
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setMatch(match, ps);

        if (ps.executeUpdate() == 0) return Optional.empty();
        
        ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()) match.setId(rs.getInt(1));
        
        return Optional.of(match);
    }

    private void setMatch(Match match, PreparedStatement ps) throws SQLException {
        ps.setString(1, match.getPhase());
        ps.setString(2, match.getState());
        ps.setInt(3, match.getIdLeague());
        ps.setInt(4, match.getIdFirstFighter());
        ps.setInt(5, match.getIdSecondFighter());
        ps.setInt(6, match.getIdWinningFighter());
    }

    @Override
    public Optional<Match> update(Match match) throws SQLException {
        String query = """
                UPDATE matches SET
                    phase = ?,
                    state = ?,
                    id_league = ?,
                    first_fighter = ?,
                    second_fighter = ?,
                    winning_fighter = ?
                WHERE id = ?
                """;
        
        PreparedStatement ps = con.prepareStatement(query);
        setMatch(match, ps);
        ps.setInt(7, match.getId());
        
        return ps.executeUpdate() == 0 ? Optional.empty() : Optional.of(match);
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String query = """
                DELETE FROM matches WHERE id = ?
                """;
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        
        return ps.executeUpdate() == 1;
    }

    @Override
    public List<Match> findAll() throws SQLException {
        List<Match> matches = new ArrayList<>();
        String query = """
                SELECT * FROM matches
                """;
        
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            matches.add(Match.builder()
                    .id(rs.getInt("id"))
                    .phase(rs.getString("phase"))
                    .state(rs.getString("state"))
                    .idLeague(rs.getInt("id_league"))
                    .idFirstFighter(rs.getInt("id_first_fighter"))
                    .idSecondFighter(rs.getInt("id_second_fighter"))
                    .build());
        }
        
        return matches;
    }

    @Override
    public Optional<Match> findById(Integer id) throws SQLException {
        String query = """
                SELECT * FROM matches WHERE id = ?
                """;
        
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        return !rs.next() ? Optional.empty() : Optional.of(Match.builder()
                .id(rs.getInt("id"))
                .phase(rs.getString("phase"))
                .state(rs.getString("state"))
                .idLeague(rs.getInt("id_league"))
                .idFirstFighter(rs.getInt("id_first_fighter"))
                .idSecondFighter(rs.getInt("id_second_fighter"))
                .build());
    }
}
