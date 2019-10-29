package com.org.spring.model.serviceprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.org.spring.model.productprice.Raw;

@Entity
public class FixedCost implements Comparable<FixedCost>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	boolean updated;
	long servicepriceid;
	
	String description;
	double value;
	
	public FixedCost() {
		
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FixedCost [id=" + id + ", updated=" + updated + ", servicepriceid=" + servicepriceid + ", description="
				+ description + ", value=" + value + "]";
	}
	@Override
	public int compareTo(FixedCost o) {
		// TODO Auto-generated method stub
		return (int)(id - o.getId());
	}
	
	
}
