package com.org.spring.model.productprice;

import java.util.List;
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
public class ProductPrice {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	boolean updated;
	long userid;
	
	String name;
	double unitsaleprice;
	int quantity;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<Raw> raws = new TreeSet<Raw>();
	double unitrawcost;
	double totalrawcost;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy("id")
	SortedSet<FixedVariableCost> fixedvariablecosts = new TreeSet<FixedVariableCost>();
	double unitfixedvariablecost;
	double totalfixedvariablecost;
	
	double fixedvariablepercentage;
	double unitcost;
	double unitprofit;
	double totalcost;
	double totalsaleprice;
	double totalprofit;
	double totalprofitmargin;
	
	boolean finished;
	
	public ProductPrice() {
		
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

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitsaleprice() {
		return unitsaleprice;
	}

	public void setUnitsaleprice(double unitsaleprice) {
		this.unitsaleprice = unitsaleprice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getUnitrawcost() {
		return unitrawcost;
	}

	public void setUnitrawcost(double unitrawcost) {
		this.unitrawcost = unitrawcost;
	}

	public double getTotalrawcost() {
		return totalrawcost;
	}

	public void setTotalrawcost(double totalrawcost) {
		this.totalrawcost = totalrawcost;
	}


	public SortedSet<Raw> getRaws() {
		return raws;
	}

	public void setRaws(SortedSet<Raw> raws) {
		this.raws = raws;
	}

	public SortedSet<FixedVariableCost> getFixedvariablecosts() {
		return fixedvariablecosts;
	}

	public void setFixedvariablecosts(SortedSet<FixedVariableCost> fixedvariablecosts) {
		this.fixedvariablecosts = fixedvariablecosts;
	}

	public double getUnitfixedvariablecost() {
		return unitfixedvariablecost;
	}

	public void setUnitfixedvariablecost(double unitfixedvariablecost) {
		this.unitfixedvariablecost = unitfixedvariablecost;
	}

	public double getTotalfixedvariablecost() {
		return totalfixedvariablecost;
	}

	public void setTotalfixedvariablecost(double totalfixedvariablecost) {
		this.totalfixedvariablecost = totalfixedvariablecost;
	}

	public double getFixedvariablepercentage() {
		return fixedvariablepercentage;
	}

	public void setFixedvariablepercentage(double fixedvariablepercentage) {
		this.fixedvariablepercentage = fixedvariablepercentage;
	}

	public double getUnitcost() {
		return unitcost;
	}

	public void setUnitcost(double unitcost) {
		this.unitcost = unitcost;
	}

	public double getUnitprofit() {
		return unitprofit;
	}

	public void setUnitprofit(double unitprofit) {
		this.unitprofit = unitprofit;
	}

	public double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	public double getTotalsaleprice() {
		return totalsaleprice;
	}

	public void setTotalsaleprice(double totalsaleprice) {
		this.totalsaleprice = totalsaleprice;
	}

	public double getTotalprofit() {
		return totalprofit;
	}

	public void setTotalprofit(double totalprofit) {
		this.totalprofit = totalprofit;
	}

	public double getTotalprofitmargin() {
		return totalprofitmargin;
	}

	public void setTotalprofitmargin(double totalprofitmargin) {
		this.totalprofitmargin = totalprofitmargin;
	}

	@Override
	public String toString() {
		return "ProductPrice [id=" + id + ", updated=" + updated + ", userid=" + userid + ", name=" + name
				+ ", unitsaleprice=" + unitsaleprice + ", quantity=" + quantity + ", raws=" + raws + ", unitrawcost="
				+ unitrawcost + ", totalrawcost=" + totalrawcost + ", fixedvariablecosts=" + fixedvariablecosts
				+ ", unitfixedvariablecost=" + unitfixedvariablecost + ", totalfixedvariablecost="
				+ totalfixedvariablecost + ", fixedvariablepercentage=" + fixedvariablepercentage + ", unitcost="
				+ unitcost + ", unitprofit=" + unitprofit + ", totalcost=" + totalcost + ", totalsaleprice="
				+ totalsaleprice + ", totalprofit=" + totalprofit + ", totalprofitmargin=" + totalprofitmargin
				+ ", finished=" + finished + "]";
	}

	
	
	
	
}
