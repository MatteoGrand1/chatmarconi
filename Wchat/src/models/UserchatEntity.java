package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USERCHAT", schema = "USER01")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
public class UserchatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    private int id;

    private String username;

    private String username_H;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUsernameH() {
        return username_H;
    }

    public void setUsernameH(String username_H) {
        this.username_H = username_H;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserchatEntity that = (UserchatEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(username_H, that.username_H);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, username_H);
    }
}
