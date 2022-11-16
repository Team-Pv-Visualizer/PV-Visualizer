package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TodayConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public Double value;
    @Column
    public String date;
}
