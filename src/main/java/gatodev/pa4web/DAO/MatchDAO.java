package gatodev.pa4web.DAO;

import gatodev.pa4web.config.DBConnector;
import gatodev.pa4web.models.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchDAO implements DAO<Match, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public static final MatchDAO instance = new MatchDAO();

    private MatchDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS matches (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    phase INT,
                    state VARCHAR(20),
                    id_league BIGINT REFERENCES leagues,
                    first_participant BIGINT REFERENCES participants,
                    second_participant BIGINT REFERENCES participants,
                    winning_participant BIGINT REFERENCES participants
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
                    first_participant,
                    second_participant,
                    winning_participant
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
        ps.setInt(1, match.getPhase());
        ps.setString(2, match.getState());
        ps.setInt(3, match.getIdLeague());
        ps.setInt(4, match.getIdFirstParticipant());
        ps.setInt(5, match.getIdSecondParticipant());
        ps.setInt(6, match.getIdWinningParticipant());
    }

    @Override
    public Optional<Match> update(Match match) throws SQLException {
        String query = """
                UPDATE matches SET
                    phase = ?,
                    state = ?,
                    id_league = ?,
                    first_participant = ?,
                    second_participant = ?,
                    winning_participant = ?
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
                    .phase(rs.getInt("phase"))
                    .state(rs.getString("state"))
                    .idLeague(rs.getInt("id_league"))
                    .idFirstParticipant(rs.getInt("id_first_participant"))
                    .idSecondParticipant(rs.getInt("id_second_participant"))
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
                .phase(rs.getInt("phase"))
                .state(rs.getString("state"))
                .idLeague(rs.getInt("id_league"))
                .idFirstParticipant(rs.getInt("id_first_participant"))
                .idSecondParticipant(rs.getInt("id_second_participant"))
                .build());
    }

    public Optional<Integer> maxPhase(Integer idLeague) throws SQLException {
        String query = """
                SELECT phase FROM matches
                WHERE id_league = ?
                ORDER BY phase DESC LIMIT 1
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, idLeague);
        ResultSet rs = ps.executeQuery();

        return rs.next() ? Optional.of(rs.getInt(1)) : Optional.empty();
    }
}
