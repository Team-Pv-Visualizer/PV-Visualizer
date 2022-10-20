package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Energyflow {

    @Id
    public Long id;

    @Column
    public String date;

    @OneToMany(mappedBy = "energyflow", fetch = FetchType.EAGER)
    Set<Pvpower> pvpowers = new HashSet<>();
}
