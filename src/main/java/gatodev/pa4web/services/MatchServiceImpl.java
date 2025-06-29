package gatodev.pa4web.services;

import gatodev.pa4web.DAO.MatchDAO;
import gatodev.pa4web.DTO.MatchDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Match;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MatchServiceImpl implements MatchService {
    private final MatchDAO matchDAO = MatchDAO.instance;
    public static final MatchServiceImpl instance = new MatchServiceImpl();

    @Override
    public Match addMatch(Match match) {
        try {
            return matchDAO.save(match)
                    .orElseThrow(() -> new RuntimeException("Llave no pudo ser creada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Match updateMatch(Match match) {
        try {
            return matchDAO.update(match)
                    .orElseThrow(() -> new RuntimeException("Llave no pudo ser actualizada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteMatch(int matchId) {
        try {
            return matchDAO.deleteById(matchId);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Match getMatch(int id) {
        try {
            return matchDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Llave no encontrada."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Match> getMatches() {
        try {
            return matchDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void generateRandomMatches(Integer idLeague, List<Integer> idParticipants) {
        int total = nextPowerOfTwo(idParticipants.size());

        while (idParticipants.size() < total) {
            idParticipants.add(null);
        }

        Collections.shuffle(idParticipants);

        createMatching(idLeague, idParticipants, 1);
    }

    @Override
    public boolean declareMatch(Integer idMatch, Integer idWinner) {
        Match match = getMatch(idMatch);

        if (match == null) return false;

        if (!match.getIdFirstParticipant().equals(idWinner) &&
                !match.getIdSecondParticipant().equals(idWinner)) return false;

        match.setIdWinningParticipant(idWinner);
        match.setState("Terminada");
        updateMatch(match);
        return true;
    }

    @Override
    public boolean nextPhaseMatches(Integer idLeague) {
        try {
            Integer currentPhase = matchDAO.maxPhase(idLeague)
                    .orElseThrow(() -> new RuntimeException("Phase no encontrada."));

            List<Match> matches = getMatches()
                    .stream()
                    .filter(m -> m.getIdLeague().equals(idLeague) &&
                            m.getPhase().equals(currentPhase))
                    .collect(Collectors.toList());

            if (matches.isEmpty() || matches.size() == 1) return false;

            boolean allFinished = matches.stream().allMatch(m ->
                    m.getState().equals("Terminada") || m.getState().equals("BYE"));

            if (allFinished) {
                List<Integer> participants = matches.stream()
                        .map(Match::getIdWinningParticipant)
                        .collect(Collectors.toList());
                createMatching(idLeague, participants, currentPhase + 1);
            }

            return allFinished;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }

    private void createMatching(int idLeague, List<Integer> idParticipants, int phase) {
        for (int i = 0; i < idParticipants.size(); i += 2) {
            Match match = new Match();
            match.setPhase(phase);
            match.setIdLeague(idLeague);
            match.setIdFirstParticipant(idParticipants.get(i));
            match.setIdSecondParticipant(idParticipants.get(i + 1));

            if (match.getIdFirstParticipant() == null || match.getIdSecondParticipant() == null) {
                match.setState("BYE");
                match.setIdWinningParticipant(null);
            }

            match.setState("En proceso");
            addMatch(match);
        }
    }

    @Override
    public MatchDTO convertMatchToDTO(Match match, List<ParticipantDTO> participants, ParticipantDTO winner) {
        return MatchDTO.builder()
                .id(match.getIdLeague())
                .phase(match.getPhase())
                .state(match.getState())
                .participants(participants)
                .winner(winner)
                .build();
    }
}
