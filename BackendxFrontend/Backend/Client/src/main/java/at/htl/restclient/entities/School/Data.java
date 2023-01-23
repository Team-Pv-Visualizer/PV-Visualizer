package at.htl.restclient.entities.School;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

/**
 * Data Entity for Json File - Data List of all Devices
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Data {

    /**
     * Generated Id for each Data Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Value for each Data Entity - Value of Consumption
     */
    @Column
    public Double value;

    /**
     * Unit for each Data Entity - Unit of Value
     */
    @Column
    public String unit;

    /**
     * Id to connect with Head Entity
     */
    @Transient
    public Long headId;

    /**
     * Data List of Head Entity - Inheritance
     */
    @ManyToOne
    @JoinColumn(name = "headId")
    @JsonBackReference
    public Head head;
}
