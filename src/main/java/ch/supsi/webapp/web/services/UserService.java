package ch.supsi.webapp.web.services;

import ch.supsi.webapp.web.model.Author;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(Author author) {
        userRepository.save(author);
    }

    public Author findUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }
}
