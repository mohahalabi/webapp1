package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id
    private String username;

    private String password;

    private String name;

    private String lastName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Role role;

    @OneToMany
    private List<Item> favoriti = new ArrayList<>();


    public Author(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public Author(String username, String password, String name, String lastName, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
    }

    public Author(String username) {
        this.username = username;
    }

    public Author() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return username.equals(author.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }

    public List<Item> getFavoriti() {
        return favoriti;
    }

    public void addFavorito(Item item) {
        favoriti.add(item);
    }

    public boolean containsFavorito(Item item) {
        return favoriti.contains(item);
    }

    public void removeFavorito(Item item) {
        favoriti.remove(item);
    }
}
