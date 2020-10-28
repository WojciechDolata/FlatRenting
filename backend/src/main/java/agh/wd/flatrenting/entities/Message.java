package agh.wd.flatrenting.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fr05_message")
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creation_tsp")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(name = "content")
    private String content;

    @Column
    private boolean wasReadByOfferOwner;

    @Column
    private boolean wasReadBySecondUser;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Conversation conversation;

}
