package edu.eci.arsw.digitalqueue.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attention_points")
public class AttentionPoint {

    private @Id @GeneratedValue Long id;
    private String code;
    private Boolean enable;
    @OneToOne
    private User user;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService(){
        return service;
    }

    public void setService(Service service){
        this.service = service;
    }

}
