package com.org.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Goal {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	long userid;
    boolean updated;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date firstdate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date lastdate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date notificationdate;
    boolean income;
    double value;

    int category;
    
    boolean notificated;
    boolean previewNotificated;
    
    public Goal() {
    	
    }

	public boolean isNotificated() {
		return notificated;
	}

	public void setNotificated(boolean notificated) {
		this.notificated = notificated;
	}

	public boolean isPreviewNotificated() {
		return previewNotificated;
	}

	public void setPreviewNotificated(boolean previewNotificated) {
		this.previewNotificated = previewNotificated;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public Date getFirstdate() {
		return firstdate;
	}

	public void setFirstdate(Date firstdate) {
		this.firstdate = firstdate;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public Date getNotificationdate() {
		return notificationdate;
	}

	public void setNotificationdate(Date notificationdate) {
		this.notificationdate = notificationdate;
	}

	public boolean isIncome() {
		return income;
	}

	public void setIncome(boolean income) {
		this.income = income;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Goal [id=" + id + ", userid=" + userid + ", updated=" + updated + ", firstdate=" + firstdate
				+ ", lastdate=" + lastdate + ", notificationdate=" + notificationdate + ", income=" + income
				+ ", value=" + value + ", category=" + category + ", notificated=" + notificated
				+ ", previewNotificated=" + previewNotificated + "]";
	}

	
    
    
}
