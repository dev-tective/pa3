package gatodev.pa4web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fighter {
    private Integer id;
    private String dni;
    private String fullName;
    private Integer age;
    private Double weight;
    private String gender;
    private String rank;        
    private String modality;    
    private Academy academy;     
    private League league;      
    private String photo;      
}
