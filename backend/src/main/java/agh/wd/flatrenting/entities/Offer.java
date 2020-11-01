package agh.wd.flatrenting.entities;

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
@Table(name = "fr01_offer")
public class Offer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creation_tsp")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column
    private String title;

    @Column
    private String location;

    @Column
    private Integer price;

    @Column(name = "flat_size")
    private Integer size;

    @Column
    private Integer roomCount;

    @Column
    private Integer visitCount;

    @Column(length = 1023)
    private String description;

    @Column
    private Long locationX;

    @Column
    private Long locationY;

    @ManyToOne
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<Photo> photos;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<Conversation> conversations;

    public void addVisit() {
        if(visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
    }
}
