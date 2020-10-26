package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.UserPreferences;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserPreferencesRepository extends CrudRepository<UserPreferences, Integer> {

    @Override
    List<UserPreferences> findAll();

    Optional<UserPreferences> findByNick(String nick);

    boolean existsByNick(String nick);
}
