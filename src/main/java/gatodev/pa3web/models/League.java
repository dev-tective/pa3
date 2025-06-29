package gatodev.pa3web.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class League {
    private Integer id;
    private String name;
}
