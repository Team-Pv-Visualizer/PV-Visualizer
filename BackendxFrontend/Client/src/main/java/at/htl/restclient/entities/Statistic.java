package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public Double maxValueForToday;
    @Column
    public String date;
}
