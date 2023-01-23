package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;

/**
 * Calculated Value of Data Entity - Daily
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class TodayConsumption {

    /**
     * Generated Id for each MonthlyConsumption Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Calculated Value of Data Entity
     */
    @Column
    public Double value;

    /**
     * TimeStamp when the data is created
     */
    @Column
    public String date;
}
