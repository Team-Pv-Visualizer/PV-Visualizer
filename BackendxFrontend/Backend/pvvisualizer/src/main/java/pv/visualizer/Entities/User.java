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
public class User {

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
}
