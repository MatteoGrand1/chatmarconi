package models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOPICCHAT", schema = "USER01")
public class TopicchatEntity {

    @Id
    private int id;

    @Column
    private Integer userid;

    @Column
    private String topicname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicchatEntity that = (TopicchatEntity) o;
        return id == that.id &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(topicname, that.topicname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userid, topicname);
    }
}
