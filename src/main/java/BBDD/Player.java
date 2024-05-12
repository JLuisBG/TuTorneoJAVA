package BBDD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pass", nullable = false)
    private Integer pass;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "isentryfeepaid", nullable = false)
    private Boolean isentryfeepaid = false;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "telephoneno", nullable = false)
    private Integer telephoneno;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsentryfeepaid() {
        return isentryfeepaid;
    }

    public void setIsentryfeepaid(Boolean isentryfeepaid) {
        this.isentryfeepaid = isentryfeepaid;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(Integer telephoneno) {
        this.telephoneno = telephoneno;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}