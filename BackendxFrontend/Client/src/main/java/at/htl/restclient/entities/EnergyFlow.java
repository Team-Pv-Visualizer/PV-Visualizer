package at.htl.restclient.entities;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EnergyFlow {

    @Id
    public Long id;

    @Column
    public String date;

    @Column
    public Integer state;

    @OneToMany(mappedBy = "energyflow", fetch = FetchType.EAGER)
    Set<Pvpower> pvpowers = new HashSet<>();
}
