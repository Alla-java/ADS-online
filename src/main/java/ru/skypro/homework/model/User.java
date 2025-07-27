package ru.skypro.homework.model;

import lombok.ToString;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;

import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "image_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Image image;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Ad> ads = new ArrayList<>();

    public User(String email, String firstName, String lastName, String password, String phone, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone) && role == user.role && Objects.equals(image, user.image) && Objects.equals(ads, user.ads);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, password, phone, role, image, ads);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", image=" + image +
                ", ads=" + ads +
                '}';
    }
}
