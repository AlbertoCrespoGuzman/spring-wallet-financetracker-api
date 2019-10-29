package com.org.spring.model.serviceprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LabourCost implements Comparable<LabourCost>{


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	boolean updated;
	long servicepriceid;
	String description;
	int hours;
	double rate;
	double totalcost;
	
	public LabourCost() {
		
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

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	@Override
	public String toString() {
		return "LabourCost [id=" + id + ", updated=" + updated + ", servicepriceid=" + servicepriceid + ", description="
				+ description + ", hours=" + hours + ", rate=" + rate + ", totalcost=" + totalcost + "]";
	}

	@Override
	public int compareTo(LabourCost o) {
		// TODO Auto-generated method stub
		return (int)(id - o.getId());
	}
	
	
}
