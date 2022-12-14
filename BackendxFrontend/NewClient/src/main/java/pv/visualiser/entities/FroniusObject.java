package pv.visualiser.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FroniusObject {

    /**
     * Generated Id for each Data Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Status of the Fronius API
     */
    @Column
    public Boolean isOnline;

    /**
     * Value for the Consumption
     */
    @Column
    public Double p_Load;
}
