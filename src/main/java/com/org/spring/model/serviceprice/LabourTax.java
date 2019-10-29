package com.org.spring.model.serviceprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LabourTax implements Comparable<LabourTax>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	boolean updated;
	long servicepriceid;
	String name;
	double percentage;
	double value;
	
	public LabourTax(){

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "LabourTax [id=" + id + ", updated=" + updated + ", servicepriceid=" + servicepriceid + ", name=" + name
				+ ", percentage=" + percentage + ", value=" + value + "]";
	}

	@Override
	public int compareTo(LabourTax o) {
		// TODO Auto-generated method stub
		return (int)(id - o.getId());
	}
	
	
}
