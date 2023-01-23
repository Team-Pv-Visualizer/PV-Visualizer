package at.htl.restclient.entities.School;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Head Entity for Json File - Head for timeStamp and identify the json file
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Head {

    /**
     * Id of Head Entity - Id is in the Json File
     */
    @Id
    public Long id;

    /**
     * TimeStamp of Head Entity - TimeStamp of the Date
     */
    @Column
    public String timeStamp;

    /**
     * Data List of Head Entity - Inheritance
     */
    @OneToMany(mappedBy = "head", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Data> data = new HashSet<>();

    /**
     * Status List of Head - Inheritance
     */
    @OneToMany(mappedBy = "head", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Status> status = new HashSet<>();
}
