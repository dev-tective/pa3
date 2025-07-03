package gatodev.pa4web.services;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Participant;

import java.util.List;

public interface ParticipantService {

    Participant addParticipant(Participant participant);

    Participant updateParticipant(Participant participant);

    boolean deleteParticipant(Integer id);

    Participant getParticipant(Integer id);

    List<Participant> getParticipants(Integer idLeague);

    ParticipantDTO convertToParticipantDTO(Participant participant, FighterDTO fighter);
}
