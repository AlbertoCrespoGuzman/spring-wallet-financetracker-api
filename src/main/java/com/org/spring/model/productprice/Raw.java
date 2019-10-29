package com.org.spring.model.productprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Raw implements Comparable<Raw>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	boolean updated;
	long productpriceid;
	
	String description;
	int quantity;
	double unitcost;
	double totalcost;
	
	public Raw() {
		
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitcost() {
		return unitcost;
	}

	public void setUnitcost(double unitcost) {
		this.unitcost = unitcost;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	@Override
	public String toString() {
		return "Raw [id=" + id + ", updated=" + updated + ", productpriceid=" + productpriceid + ", description="
				+ description + ", quantity=" + quantity + ", unitcost=" + unitcost + ", totalcost=" + totalcost + "]";
	}

	@Override
	public int compareTo(Raw o) {
		// TODO Auto-generated method stub
		return (int)(id - o.getId());
	}
	
	
}
