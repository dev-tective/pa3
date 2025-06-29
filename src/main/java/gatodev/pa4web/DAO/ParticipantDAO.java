package gatodev.pa4web.DAO;

import gatodev.pa4web.config.DBConnector;
import gatodev.pa4web.models.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipantDAO implements DAO<Participant, Integer> {
    private final Connection con = DBConnector.dbConnector.getCon();
    public final static ParticipantDAO instance = new ParticipantDAO();

    //Siempre instanciar después de leagues y fighters
    private ParticipantDAO() {
        String sql = """
                CREATE TABLE IF NOT EXISTS participants (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    place INT,
                    state VARCHAR(20),
                    id_fighter BIGINT REFERENCES fighters,
                    id_league BIGINT REFERENCES leagues
                );
                """;

        try(Statement st = con.createStatement()) {
            st.execute(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Participant> save(Participant participant) throws SQLException {
        String query = """
                INSERT INTO participants (place, state, id_fighter, id_league)
                VALUES (?, ?, ?, ?)
                """;

        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, participant.getPlace());
        ps.setString(2, participant.getState());
        ps.setInt(3, participant.getIdFighter());
        ps.setInt(4, participant.getIdLeague());

        if (ps.executeUpdate() == 0) return Optional.empty();

        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) participant.setId(keys.getInt(1));

        return Optional.of(participant);
    }

    @Override
    public Optional<Participant> update(Participant participant) throws SQLException {
        String query = """
                UPDATE participants SET
                    place = ?,
                    state = ?,
                    id_fighter = ?,
                    id_league = ?
                WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, participant.getPlace());
        ps.setString(2, participant.getState());
        ps.setInt(3, participant.getIdFighter());
        ps.setInt(4, participant.getIdLeague());
        ps.setInt(5, participant.getId());

        return ps.executeUpdate() == 0 ? Optional.empty() : Optional.of(participant);
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String query = """
                DELETE FROM participants WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        return ps.executeUpdate() == 1;
    }

    @Override
    public List<Participant> findAll() throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String query = """
                SELECT * FROM participants
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            participants.add(Participant.builder()
                    .id(rs.getInt("id"))
                    .place(rs.getInt("place"))
                    .state(rs.getString("state"))
                    .idFighter(rs.getInt("id_fighter"))
                    .idLeague(rs.getInt("id_league"))
                    .build());
        }

        return participants;
    }

    @Override
    public Optional<Participant> findById(Integer id) throws SQLException {
        String query = """
                SELECT * FROM participants WHERE id = ?
                """;

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        return !rs.next() ? Optional.empty() : Optional.of(Participant.builder()
                .id(rs.getInt("id"))
                .place(rs.getInt("place"))
                .state(rs.getString("state"))
                .idFighter(rs.getInt("id_fighter"))
                .idLeague(rs.getInt("id_league"))
                .build());
    }
}
