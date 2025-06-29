package gatodev.pa3web.DTO;

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
    private Integer idLeague;
}
