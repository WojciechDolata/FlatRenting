package agh.wd.flatrenting.services;

import agh.wd.flatrenting.database.UserCredentialsRepository;
import agh.wd.flatrenting.database.UserPreferencesRepository;
import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.entities.UserCredentials;
import agh.wd.flatrenting.entities.UserPreferences;
import agh.wd.flatrenting.exceptions.UserNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    private EntityManager em;
    private UserRepository userRepository;
    private UserPreferencesRepository userPreferencesRepository;
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public UserService(EntityManager em,
                       UserRepository userRepository,
                       UserPreferencesRepository userPreferencesRepository,
                       UserCredentialsRepository userCredentialsRepository) {
        this.em = em;
        this.userRepository = userRepository;
        this.userPreferencesRepository = userPreferencesRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public void save(User user, UserCredentials userCredentials) {
        if(userRepository.existsByNick(user.getNick()) || userCredentialsRepository.existsByNick(user.getNick())) {
            throw new DataIntegrityViolationException("User already exists.");
        }
        userRepository.save(user);
        userCredentialsRepository.save(userCredentials);
    }

    @Transactional
    public void updateEmail(String email, String nick) {
        UserCredentials user = userCredentialsRepository.findByNick(nick).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User " + nick + " not found.");
        }

        user.setEmail(email);
        userCredentialsRepository.save(user);
    }

    @Transactional
    public void updatePassword(String passwordHash, String nick) {
        UserCredentials user = userCredentialsRepository.findByNick(nick).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User " + nick + " not found.");
        }

        user.setPasswordHash(passwordHash);
        userCredentialsRepository.save(user);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByNick(userName);
    }

    @Transactional
    public void updatePhoneNumber(String phoneNumber, String nick) {
        User user = userRepository.findByNick(nick).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User " + nick + " not found.");
        }

        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    public String findEmail(String nick) {
        return userCredentialsRepository.findByNick(nick)
                .orElseThrow(() -> new UserNotFoundException("User " + nick + " not found."))
                .getEmail();
    }

    @Transactional
    public void updatePreferences(UserPreferences preferences) {
        userPreferencesRepository.findByNick(preferences.getNick())
                .ifPresentOrElse(
                        preferencesFromDb -> {
                            preferencesFromDb.copyValues(preferences);
                            em.persist(preferencesFromDb);
                        },
                        () -> userPreferencesRepository.save(preferences)
                );
    }

    public UserPreferences getPreferences(String nick) {
        return userPreferencesRepository.findByNick(nick)
                .orElseThrow(
                        () -> new UserNotFoundException(nick)
                );
    }
}
