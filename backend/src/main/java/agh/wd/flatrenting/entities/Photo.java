package agh.wd.flatrenting.entities;

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
@Table(name = "fr02_photo")
public class Photo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creation_tsp")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(name = "data", length = 2047)
    private byte[] data;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Offer origin;


}
