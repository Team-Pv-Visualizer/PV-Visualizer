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
/*
SELECT id,`date`, value FROM TodayConsumption ORDER BY value DESC

SELECT `date`, value  FROM TodayConsumption WHERE value = (SELECT MAX(value) FROM TodayConsumption)GROUP BY date, value

SELECT x.`date` , tc.value
FROM TodayConsumption x
LEFT JOIN TodayConsumption tc
ON x.`date` = tc.`date` AND x.value < tc.value
GROUP BY x.`date`, tc.value
*/
