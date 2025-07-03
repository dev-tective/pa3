package gatodev.pa4web.services;

import gatodev.pa4web.DAO.ParticipantDAO;
import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.DTO.ParticipantDTO;
import gatodev.pa4web.models.Participant;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantDAO participantDAO = ParticipantDAO.instance;
    public final static ParticipantServiceImpl instance = new ParticipantServiceImpl();

    @Override
    public Participant addParticipant(Participant participant) {
        try {
            return participantDAO.save(participant)
                    .orElseThrow(() -> new RuntimeException("Participante no pudo ser añadido."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        try {
            return participantDAO.update(participant)
                    .orElseThrow(() -> new RuntimeException("Participante no pudo ser actualizado."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteParticipant(Integer id) {
        try {
            return participantDAO.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Participant getParticipant(Integer id) {
        try {
            return participantDAO.findById(id)
                    .orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Participant> getParticipants(Integer idLeague) {
        try {
            return participantDAO.findAll()
                    .stream()
                    .filter(p -> p.getIdLeague().equals(idLeague))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ParticipantDTO convertToParticipantDTO(Participant participant, FighterDTO fighter) {
        return ParticipantDTO.builder()
                .id(participant.getId())
                .leagueId(participant.getIdLeague())
                .fighter(fighter)
                .build();
    }
}
