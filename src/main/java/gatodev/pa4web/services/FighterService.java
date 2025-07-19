package gatodev.pa4web.services;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;
import gatodev.pa4web.models.League;

import java.util.List;

public interface FighterService {

    Fighter addFighter(Fighter f);
    Fighter updateFighter(Fighter f);
    boolean deleteFighter(Integer id);
    Fighter getFighter(Integer id);
    List<Fighter> getAllFighters();

    FighterDTO convertToFighterDTO(Fighter f, Academy academy, League league);
}

