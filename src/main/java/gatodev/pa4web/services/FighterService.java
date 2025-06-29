package gatodev.pa4web.services;

import gatodev.pa4web.DTO.FighterDTO;
import gatodev.pa4web.models.Academy;
import gatodev.pa4web.models.Fighter;

import java.util.List;

public interface FighterService {

    Fighter addFighter(Fighter fighter);

    Fighter updateFighter(Fighter fighter);

    boolean deleteFighter(Integer id);

    Fighter getFighter(Integer id);

    List<Fighter> getAllFighters();

    FighterDTO convertToFighterDTO(Fighter fighter, Academy academy);
}
