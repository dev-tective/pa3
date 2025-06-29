package gatodev.pa4web.DTO;

import gatodev.pa4web.models.League;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ParticipantDTO {
    private Integer id;
    private Integer place;
    private String state;
    private FighterDTO fighter;
    private League league;
}
