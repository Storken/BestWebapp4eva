package se.chalmers.bestwebapp4eva.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import se.chalmers.bestwebapp4eva.entity.AbstractDBObject;

/**
 * User table in database
 *
 * NOTE : User is a reserved word in SQL
 * 
 * @author hajo
 */
@Entity
@Table(name="USER_")
public class User_ extends AbstractDBObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected long id;
    @Column(name="USERNAME")  // unique is implied
    protected String username;
    @Column(name="PASSWORD")
    protected String passwd;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_GROUPS", 
            joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    protected List<Groups> groups = new ArrayList<>();

    public User_() {
    }
    
    public User_(String username, String passwd){
        this.username = username;
        this.passwd = passwd;
        groups.add(Groups.USER);
    }

    public User_(String username, String passwd, Groups group) {
        this.username = username;
        this.passwd = passwd;
        groups.add(group);
    }

    public void addGroup(Groups group) {
        groups.add(group);
    }

    public void removeGroup(Groups group) {
        groups.remove(group);
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User_ other = (User_) obj;
        return Objects.equals(this.username, other.username);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
