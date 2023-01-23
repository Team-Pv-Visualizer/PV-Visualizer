package at.htl.restclient.entities.Fronius;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
/**
 * Fronius Object for Fronius Json
 */
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
     * Date for the Consumption
     */
    @Column
    public String date;

    /**
     * Value for the Consumption
     */
    @Column
    public Double p_Load;
}
