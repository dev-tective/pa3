package gatodev.pa4web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Participant {
    private Integer id;
    private Integer idFighter;
    private Integer idLeague;
}
