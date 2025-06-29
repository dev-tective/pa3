package gatodev.pa4web.services;

import gatodev.pa4web.DAO.FighterDAO;
import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;

import java.sql.SQLException;
import java.util.List;

public class FighterServiceImpl implements FighterService {
    private final FighterDAO fighterDAO = FighterDAO.instance;
    public final static FighterServiceImpl instance = new FighterServiceImpl();

    @Override
    public Fighter addFighter(Fighter fighter) {
        try {
            return fighterDAO.save(fighter)
                    .orElseThrow(() -> new RuntimeException("No se pudo agregar al luchador."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Fighter updateFighter(Fighter fighter) {
        try {
            return fighterDAO.update(fighter)
                    .orElseThrow(() -> new RuntimeException("No se pudo actualizar al luchador."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean deleteFighter(Integer id) {
        try {
            return fighterDAO.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Fighter getFighter(Integer id) {
        try {
            return fighterDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Luchador no encontrado."));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Fighter> getAllFighters() {
        try {
            return fighterDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FighterDTO convertToFighterDTO(Fighter fighter, Academy academy) {
        return FighterDTO.builder()
                .id(fighter.getId())
                .fullName(fighter.getFullName())
                .age(fighter.getAge())
                .gender(fighter.getGender())
                .rank(fighter.getRank())
                .weight(fighter.getWeight())
                .modality(fighter.getModality())
                .academy(academy)
                .build();
    }
}
