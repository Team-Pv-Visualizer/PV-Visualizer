package pv.visualizer.Entities;

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
     * Date for the Consumption
     */
    @Column
    public String date;

    /**
     * Value for the Consumption
     */
    @Column
    public Double p_Load;

    /**
     * OneToMany Beziehung
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    public FroniusLogin login;
}
