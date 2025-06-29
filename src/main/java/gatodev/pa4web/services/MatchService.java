package gatodev.pa4web.services;

import gatodev.pa4web.DTO.MatchDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Match;

import java.util.List;

public interface MatchService {

    Match addMatch(Match match);

    boolean deleteMatch(int matchId);

    Match getMatch(int id);

    List<Match> getMatches();

    void generateRandomMatches(Integer idLeague, List<Integer> idParticipants);

    MatchDTO convertMatchToDTO(Match match, List<ParticipantDTO> participants, ParticipantDTO winner);
}
