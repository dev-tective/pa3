package gatodev.pa4web.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MatchDTO {
    private Integer id;
    private String phase;
    private String state;
    private List<ParticipantDTO> participants;
    private ParticipantDTO winner;
}
