import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    int id;

    String email;

    @JsonSetter("first_name")
    private String firstName;

    @JsonSetter("last_name")
    private String lastName;

    String avatar;
}
