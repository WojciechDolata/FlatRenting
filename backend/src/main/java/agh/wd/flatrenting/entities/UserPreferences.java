package agh.wd.flatrenting.entities;

import agh.wd.flatrenting.exceptions.PreferencesNotFoundException;
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

    public void copyValues(UserPreferences preferences) {
        if (preferences.getMinPrice() > 0 || preferences.getMinPrice() == null) {
            minPrice = preferences.getMinPrice();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMaxPrice() > 0 || preferences.getMaxPrice() == null) {
            maxPrice = preferences.getMaxPrice();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMinSize() > 0 || preferences.getMinSize() == null) {
            minSize = preferences.getMinSize();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMaxSize() > 0 || preferences.getMaxSize() == null) {
            maxSize = preferences.getMaxSize();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMinNumberOfRooms() > 0 || preferences.getMinNumberOfRooms() == null) {
            minNumberOfRooms = preferences.getMinNumberOfRooms();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMaxNumberOfRooms() > 0 || preferences.getMaxNumberOfRooms() == null) {
            maxNumberOfRooms = preferences.getMaxNumberOfRooms();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        if (preferences.getMaxDaysAgo() > 0 || preferences.getMaxDaysAgo() == null) {
            maxDaysAgo = preferences.getMaxDaysAgo();
        } else {
            throw new PreferencesNotFoundException(preferences.getNick());
        }

        location = preferences.getLocation();
    }

    public boolean isCorrect() {
        return getMaxDaysAgo() >= 0 &&
                getMaxNumberOfRooms() >= 1 &&
                getMinNumberOfRooms() >= 1 &&
                getMaxPrice() >= 0 &&
                getMinPrice() >= 0 &&
                getMaxSize() >= 1 &&
                getMinSize() >= 1;

    }

}

