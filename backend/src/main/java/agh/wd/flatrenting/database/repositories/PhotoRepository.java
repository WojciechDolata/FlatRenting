package agh.wd.flatrenting.database.repositories;

import agh.wd.flatrenting.entities.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    @Override
    List<Photo> findAll();
}
