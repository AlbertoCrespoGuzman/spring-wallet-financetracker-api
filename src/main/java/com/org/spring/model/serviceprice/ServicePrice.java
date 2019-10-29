package com.org.spring.model.serviceprice;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class ServicePrice {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	long userid;
	
	String name;
	double saleprice;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<LabourCost> labourcosts = new TreeSet<LabourCost>();
	double totallabourcost;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<LabourTax> labourtaxs = new TreeSet<LabourTax>();
	double totallabourtax;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<VariableCost> variablecosts = new TreeSet<VariableCost>();
	double totalvariablecost;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<FixedCost> fixedcosts = new TreeSet<FixedCost>();
	double totalfixedcost;
	
	double totalcost;
	double profit;
	double profitmargin;
	boolean updated;
	
	boolean finished;
	
	
	
	public ServicePrice() {
		
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}


	public double getTotallabourcost() {
		return totallabourcost;
	}

	public void setTotallabourcost(double totallabourcost) {
		this.totallabourcost = totallabourcost;
	}


	public double getTotallabourtax() {
		return totallabourtax;
	}

	public void setTotallabourtax(double totallabourtax) {
		this.totallabourtax = totallabourtax;
	}

	public double getTotalvariablecost() {
		return totalvariablecost;
	}

	public void setTotalvariablecost(double totalvariablecost) {
		this.totalvariablecost = totalvariablecost;
	}


	public SortedSet<LabourCost> getLabourcosts() {
		return labourcosts;
	}

	public void setLabourcosts(SortedSet<LabourCost> labourcosts) {
		this.labourcosts = labourcosts;
	}

	public SortedSet<LabourTax> getLabourtaxs() {
		return labourtaxs;
	}

	public void setLabourtaxs(SortedSet<LabourTax> labourtaxs) {
		this.labourtaxs = labourtaxs;
	}

	public SortedSet<VariableCost> getVariablecosts() {
		return variablecosts;
	}

	public void setVariablecosts(SortedSet<VariableCost> variablecosts) {
		this.variablecosts = variablecosts;
	}

	public SortedSet<FixedCost> getFixedcosts() {
		return fixedcosts;
	}

	public void setFixedcosts(SortedSet<FixedCost> fixedcosts) {
		this.fixedcosts = fixedcosts;
	}

	public double getTotalfixedcost() {
		return totalfixedcost;
	}

	public void setTotalfixedcost(double totalfixedcost) {
		this.totalfixedcost = totalfixedcost;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getProfitmargin() {
		return profitmargin;
	}

	public void setProfitmargin(double profitmargin) {
		this.profitmargin = profitmargin;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "ServicePrice [id=" + id + ", userid=" + userid + ", name=" + name + ", saleprice=" + saleprice
				+ ", labourcosts=" + labourcosts + ", totallabourcost=" + totallabourcost + ", labourtaxs=" + labourtaxs
				+ ", totallabourtax=" + totallabourtax + ", variablecosts=" + variablecosts + ", totalvariablecost="
				+ totalvariablecost + ", fixedcosts=" + fixedcosts + ", totalfixedcost=" + totalfixedcost
				+ ", totalcost=" + totalcost + ", profit=" + profit + ", profitmargin=" + profitmargin + ", updated="
				+ updated + ", finished=" + finished + "]";
	}

	
	
}
