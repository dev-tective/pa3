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
    private Integer phase;
    private String state;
    private Integer idLeague;
    private Integer idFirstParticipant;
    private Integer idSecondParticipant;
    private Integer idWinningParticipant;
}
