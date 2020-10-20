package agh.wd.flatrenting.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fr04_conversation")
public class Conversation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creation_tsp")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Offer offer;

    @OneToMany(mappedBy = "conversation", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Message> messages;
}
