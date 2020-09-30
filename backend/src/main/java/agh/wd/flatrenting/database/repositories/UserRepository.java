package agh.wd.flatrenting.database.repositories;

import agh.wd.flatrenting.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    Optional<User> findByNick(String nick);
}
