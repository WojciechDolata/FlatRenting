package agh.wd.flatrenting.services;

import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    private EntityManager em;
    private UserRepository userRepository;

    @Autowired
    public UserService(EntityManager em, UserRepository userRepository) {
        this.em = em;
        this.userRepository = userRepository;
    }

    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        if(userRepository.existsByNick(user.getNick())) {
            throw new DataIntegrityViolationException("User already exists.");
        }
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User originalUser = userRepository.findById(user.getId()).orElseThrow();
        originalUser.setConversations(user.getConversations());
        originalUser.setOffers(user.getOffers());
        originalUser.setEmail(user.getEmail());
        originalUser.setPhoneNumber(user.getPhoneNumber());
        em.persist(user);
    }

    public void delete(User user) {
        get(user.getId()).ifPresent(userToBeDeleted -> userRepository.delete(userToBeDeleted));
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByNick(userName);
    }
}
