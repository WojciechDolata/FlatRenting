package agh.wd.flatrenting.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fr99_user_cred")
public class UserCredentials {

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

    @Column(name = "email", unique = true)
    @NonNull
    private String email;

    @Column(name = "password_hash")
    @NonNull
    private String passwordHash;

    public UserCredentials(@NonNull String nick, @NonNull String email, @NonNull String passwordHash) {
        this.nick = nick;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public List<String> getRoles() {
        return Collections.singletonList("USER");
    }
}
