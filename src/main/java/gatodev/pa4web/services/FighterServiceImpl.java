package gatodev.pa4web.services;

import gatodev.pa4web.DAO.FighterDAO;
import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;
import gatodev.pa4web.models.League;

import java.sql.SQLException;
import java.util.List;

public class FighterServiceImpl implements FighterService {
    private final FighterDAO dao = FighterDAO.instance;
    public static final FighterServiceImpl instance = new FighterServiceImpl();

    @Override public Fighter addFighter(Fighter f) {
        try { return dao.save(f).orElseThrow(); } 
        catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public Fighter updateFighter(Fighter f) {
        try { return dao.update(f).orElseThrow(); } 
        catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public boolean deleteFighter(Integer id) {
        try { return dao.deleteById(id); } 
        catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public Fighter getFighter(Integer id) {
        try { return dao.findById(id).orElseThrow(); } 
        catch (SQLException e) { throw new RuntimeException(e); }
    }
    @Override public List<Fighter> getAllFighters() {
        try { return dao.findAll(); } 
        catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public FighterDTO convertToFighterDTO(Fighter f, Academy a, League l) {
        return FighterDTO.builder()
            .id(f.getId())
            .dni(f.getDni())
            .fullName(f.getFullName())
            .age(f.getAge())
            .weight(f.getWeight())
            .gender(f.getGender())
            .rank(f.getRank())
            .modality(f.getModality())
            .photo(f.getPhoto())
            .academy(a)
            .league(l)
            .build();
    }
}
