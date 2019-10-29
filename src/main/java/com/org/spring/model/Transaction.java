package com.org.spring.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    long userid;
    boolean updated;
    int category;
    boolean income;
   // @DatabaseField(dataType = DataType.DA                             Nov 20, 2018 12:00:00 AM"
    // "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"));
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date date;
    boolean installment;
    int totalinstallments;
    int frequencyinstallment;
    double value;
    double entrance_payment;
    double payment;
    boolean paid;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MMM dd',' yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    Date firstinstallment;
    

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Installment> installments = new ArrayList<>();
    
    public Transaction() {
    	
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public int getTotalinstallments() {
		return totalinstallments;
	}

	public void setTotalinstallments(int totalinstallments) {
		this.totalinstallments = totalinstallments;
	}

	public int getFrequencyinstallment() {
		return frequencyinstallment;
	}

	public void setFrequencyinstallment(int frequencyinstallment) {
		this.frequencyinstallment = frequencyinstallment;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getEntrance_payment() {
		return entrance_payment;
	}

	public void setEntrance_payment(double entrance_payment) {
		this.entrance_payment = entrance_payment;
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

	public Date getFirstinstallment() {
		return firstinstallment;
	}

	public void setFirstinstallment(Date firstinstallment) {
		this.firstinstallment = firstinstallment;
	}

	public List<Installment> getInstallments() {
		return installments;
	}

	public void setInstallments(List<Installment> installments) {
		this.installments = installments;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", userid=" + userid + ", updated=" + updated + ", category=" + category
				+ ", income=" + income + ", date=" + date + ", installment=" + installment + ", totalinstallments="
				+ totalinstallments + ", frequencyinstallment=" + frequencyinstallment + ", value=" + value
				+ ", entrance_payment=" + entrance_payment + ", payment=" + payment + ", paid=" + paid
				+ ", firstinstallment=" + firstinstallment + ", installments=" + installments + "]";
	}
    
}
