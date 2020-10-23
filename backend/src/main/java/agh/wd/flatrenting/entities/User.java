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
@Table(name = "fr03_user")
public class User {

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

//    @Column(name = "email", unique = true)
//    @NonNull
//    private String email;
//
//    @Column(name = "password_hash")
//    @NonNull
//    private String passwordHash;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany
    private List<Offer> offers;

    @OneToMany
    private List<Conversation> conversations;

    public User(@NonNull String nick, String phoneNumber) {
        this.nick = nick;
        this.phoneNumber = phoneNumber;
    }
}
