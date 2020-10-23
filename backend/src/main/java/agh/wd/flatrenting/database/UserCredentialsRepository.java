package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.UserCredentials;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Integer> {

    @Override
    List<UserCredentials> findAll();

    Optional<UserCredentials> findByNick(String nick);

    boolean existsByNick(String nick);
}
