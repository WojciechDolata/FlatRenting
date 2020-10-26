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
@Table(name = "fr06_user_pref")
public class UserPreferences {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "creation_tsp")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(name = "nick", unique = true)
    @NonNull
    private String nick;

    @Column
    private Integer maxPrice;

    @Column
    private Integer minPrice;

    @Column
    private String location;

    @Column
    private Integer minNumberOfRooms;

    @Column
    private Integer maxNumberOfRooms;

    @Column
    private Integer minSize;

    @Column
    private Integer maxSize;

    @Column
    private Integer maxDaysAgo;

    public void copyValues (UserPreferences preferences) {
        maxPrice = preferences.getMaxPrice();
        minPrice = preferences.getMinPrice();
        location = preferences.getLocation();
        minNumberOfRooms = preferences.getMinNumberOfRooms();
        maxNumberOfRooms = preferences.getMaxNumberOfRooms();
        minSize = preferences.getMinSize();
        maxSize = preferences.getMaxSize();
        maxDaysAgo = preferences.getMaxDaysAgo();
    }

}

