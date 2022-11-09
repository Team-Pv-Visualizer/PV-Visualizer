package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pvpower {
    @Id
    public Long id;
    @Column
    public String title;
    @Column
    public Double value;
    @Transient
    public Long energyflowId;
    @ManyToOne
    @JoinColumn(name = "energyflowId")
    EnergyFlow energyflow;
}
