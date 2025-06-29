package gatodev.pa4web.models;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Integer id;
    private String phase;
    private String state;
    private Integer idLeague;
    private Integer idFirstFighter;
    private Integer idSecondFighter;
    private Integer idWinningFighter;
}
