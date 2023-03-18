package pv.visualizer.Entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
/**
 * Fronius Object for Fronius Json
 */
public class FroniusLogin {

    /**
     * Generated Id for each Data Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Login Id
     */
    @Column
    public String pvSystemId;

    /**
     * OneToMany Beziehung
     */
    @OneToMany(mappedBy = "login")
    public List<FroniusObject> objects;
}
