package swu.musling.membership;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private String id;
    private String pwd;
    private String name;
}
