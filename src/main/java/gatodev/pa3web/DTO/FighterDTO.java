package gatodev.pa3web.DTO;

import gatodev.pa3web.models.Academy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class FighterDTO {
    private Integer id;
    private String fullName;
    private Integer age;
    private String weight;
    private String gender;
    private String rank;
    private String modality;
    private Academy academy;
}
