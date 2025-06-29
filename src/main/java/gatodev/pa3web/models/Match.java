package gatodev.pa3web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Match {
    private Integer id;
    private String phase;
    private String state;
    private Integer idLeague;
    private Integer idFirstFighter;
    private Integer idSecondFighter;
    private Integer idWinningFighter;
}
