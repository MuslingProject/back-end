package swu.musling.membership;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class MemberVo {
    @Id
    private String id;
    private String pwd;
    private String name;
}
