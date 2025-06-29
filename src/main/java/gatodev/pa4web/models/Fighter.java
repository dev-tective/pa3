package gatodev.pa4web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Fighter {
    private Integer id;
    private String fullName;
    private Integer age;
    private String weight;
    private String gender;
    private String rank;
    private String modality;
    private Integer idAcademy;
}
