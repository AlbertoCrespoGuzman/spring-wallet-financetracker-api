package com.org.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Installment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    long userid;
    boolean updated;
    long transactionid;
    boolean income;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date date;
    boolean installment;
    double value;
    double payment;
    boolean paid;
    int number;


//    Transaction transaction;
    
    public Installment() {
    	
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


	public long getTransactionid() {
		return transactionid;
	}


	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
	}


	public boolean isIncome() {
		return income;
	}


	public void setIncome(boolean income) {
		this.income = income;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public boolean isInstallment() {
		return installment;
	}


	public void setInstallment(boolean installment) {
		this.installment = installment;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	public double getPayment() {
		return payment;
	}


	public void setPayment(double payment) {
		this.payment = payment;
	}


	public boolean isPaid() {
		return paid;
	}


	public void setPaid(boolean paid) {
		this.paid = paid;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	@Override
	public String toString() {
		return "Installment [id=" + id + ", userid=" + userid + ", updated=" + updated + ", transactionid="
				+ transactionid + ", income=" + income + ", date=" + date + ", installment=" + installment + ", value="
				+ value + ", payment=" + payment + ", paid=" + paid + ", number=" + number + "]";
	}
    
}
