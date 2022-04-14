package edu.eci.arsw.digitalqueue.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "turns")
public class Turn {

    private @Id String code;
    private String clientName;
    private Timestamp requestedDateTime;
    private Timestamp attendedDateTime;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service service;
    private Boolean attended;
    private Boolean cancelled;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AttentionPoint attentionPoint;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Timestamp getRequestedDateTime() {
        return requestedDateTime;
    }

    public void setRequestedDateTime(Timestamp requestedDateTime) {
        this.requestedDateTime = requestedDateTime;
    }

    public Timestamp getAttendedDateTime() {
        return attendedDateTime;
    }

    public void setAttendedDateTime(Timestamp attendedDateTime) {
        this.attendedDateTime = attendedDateTime;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Boolean getAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public AttentionPoint getAttentionPoint() {
        return attentionPoint;
    }

    public void setAttentionPoint(AttentionPoint attentionPoint) {
        this.attentionPoint = attentionPoint;
    }

}
