package pl.bookworm.bookworm.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    //TODO: on production - change generation type to sequence
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_FULLNAME")
    private String userFullname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;

    @Column(name = "LAST_LOGIN_DATE")
    private Date lastLoginDate;

    private String userDescription;

    @ManyToOne
    private Book favouriteBook;

    /* This set will be sorted for client purposes
     * For example,
     * If client need user's top 5 - this will be sorted by ratings
     * If recent rated books - this one will be sorted by rating time
     * TODO: add rating time to make list sorting more specific
     */
    @OneToMany
    private Set<Book> usersRatedBooks;

    public User() {}
    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Book getFavouriteBook() {
        return favouriteBook;
    }

    public void setFavouriteBook(Book favouriteBook) {
        this.favouriteBook = favouriteBook;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Set<Book> getUsersRatedBooks() {
        return usersRatedBooks;
    }

    public void setUsersRatedBooks(Set<Book> usersRatedBooks) {
        this.usersRatedBooks = usersRatedBooks;
    }
}
