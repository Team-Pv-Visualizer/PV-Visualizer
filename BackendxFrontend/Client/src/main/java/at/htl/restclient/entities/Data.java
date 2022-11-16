package at.htl.restclient.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public String title;
    @Column
    public Double value;
    @Column
    public String unit;
    @Transient
    public Long headId;

    @ManyToOne
    @JoinColumn(name = "headId")
    @JsonBackReference
    public Head head;
}
