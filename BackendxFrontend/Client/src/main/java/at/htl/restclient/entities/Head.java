package at.htl.restclient.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Head {

    @Id
    public Long id;
    @Column
    public String timeStamp;

    @OneToMany(mappedBy = "head", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Data> data = new HashSet<>();

    @OneToOne(mappedBy = "head", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    public Status status;
}
