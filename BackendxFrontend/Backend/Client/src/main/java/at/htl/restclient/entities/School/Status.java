package at.htl.restclient.entities.School;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

/**
 * Status Entity for Json File - Status of Fronius API
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Status {

    /**
     * Generated Id for Status Entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * StatusCode of Fronius Code
     */
    @Column
    public String code;

    /**
     * Reason of Fronius Error
     */
    @Column
    public String reason;

    /**
     * User Message of Fronius Messages
     */
    @Column
    public String userMessage;

    /**
     * Id to connect with Head Entity
     */
    @Transient
    public Long headId;

    /**
     * Status of Head - Inheritance
     */
    @ManyToOne
    @JoinColumn(name = "headId")
    @JsonBackReference
    public Head head;
}
