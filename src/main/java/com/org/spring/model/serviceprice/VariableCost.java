package com.org.spring.model.serviceprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VariableCost implements Comparable<VariableCost>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	boolean updated;
	long servicepriceid;
	
	String description;
	double percentage;
	double value;
	
	public VariableCost() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public long getServicepriceid() {
		return servicepriceid;
	}

	public void setServicepriceid(long servicepriceid) {
		this.servicepriceid = servicepriceid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "VariableCost [id=" + id + ", updated=" + updated + ", servicepriceid=" + servicepriceid
				+ ", description=" + description + ", percentage=" + percentage + ", value=" + value + "]";
	}

	@Override
	public int compareTo(VariableCost o) {
		// TODO Auto-generated method stub
		return (int)(id - o.getId());
	}
	
	
}
