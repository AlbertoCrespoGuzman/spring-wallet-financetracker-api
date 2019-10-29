package com.org.spring.model.productprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FixedVariableCost implements Comparable<FixedVariableCost>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	boolean updated;
	long productpriceid;
	
	String description;
	double percentage;
	double value;
	
	public FixedVariableCost() {
		
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

	public long getProductpriceid() {
		return productpriceid;
	}

	public void setProductpriceid(long productpriceid) {
		this.productpriceid = productpriceid;
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
		return "FixedVariableCost [id=" + id + ", updated=" + updated + ", productpriceid=" + productpriceid
				+ ", description=" + description + ", percentage=" + percentage + ", value=" + value + "]";
	}


	@Override
	public int compareTo(FixedVariableCost o) {
		// TODO Auto-generated method stub
		return (int) (id - o.getId());
	}
	
	
}
