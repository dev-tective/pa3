package gatodev.pa4web.services;

import gatodev.pa4web.DTO.MatchDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Match;

import java.util.List;

public interface MatchService {

    Match addMatch(Match match);

    Match updateMatch(Match match);

    boolean deleteMatch(int matchId);

    Match getMatch(int id);

    List<Match> getMatches(Integer idLeague);

    void generateRandomMatches(Integer idLeague, List<Integer> idParticipants);

    boolean declareMatch(Integer idMatch, Integer idWinner);

    boolean nextPhaseMatches(Integer idLeague);

    MatchDTO convertMatchToDTO(Match match, ParticipantDTO first, ParticipantDTO second, ParticipantDTO winner);
}
