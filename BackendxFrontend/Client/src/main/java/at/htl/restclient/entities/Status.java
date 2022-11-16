package at.htl.restclient.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public String code;
    @Column
    public String reason;
    @Column
    public String userMessage;
    @Transient
    public Long headId;

    @OneToOne
    @JoinColumn(name = "headId")
    public Head head;
}
