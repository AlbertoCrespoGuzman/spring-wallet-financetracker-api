package com.org.spring.model;

import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.context.MessageSource;

@Entity
public class UserDetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
	 
	    long userid;
	    boolean updated;


	    String name;
	    boolean socialname;
	    String cpf;
	    String birthday;
	    String mobilenumber;
	    String email;
	    int education;
	    int skincolor;
	    int genre;
	    // ENTERPRISE
	    boolean formalized;
	    int familyincome;
	    String familynumber;
	    boolean activated;
	    int maincategory;
	    int subcategory;
	    String othersubcategory;




	    public UserDetails(){
	    }
	    public void setBirthdayformated(){

	    }
	    public boolean isEmpty(){
	        boolean isEmpty = false;

	        if(name == null || cpf == null  || birthday == null || email == null ||
	                name == "" || cpf == "" || email == ""){
	            isEmpty = true;
	        }

	        return isEmpty;
	    }


	    public boolean isSocialname() {
	        return socialname;
	    }

	    public void setSocialname(boolean socialname) {
	        this.socialname = socialname;
	    }

	    public String getFamilynumber() {
	        return familynumber;
	    }

	    public void setFamilynumber(String familynumber) {
	        this.familynumber = familynumber;
	    }

	    public int getEducation() {
	        return education;
	    }

	    public void setEducation(int education) {
	        this.education = education;
	    }

	    public int getSkincolor() {
	        return skincolor;
	    }

	    public void setSkincolor(int skincolor) {
	        this.skincolor = skincolor;
	    }

	    public int getGenre() {
	        return genre;
	    }

	    public void setGenre(int genre) {
	        this.genre = genre;
	    }

	    public boolean isFormalized() {
	        return formalized;
	    }

	    public void setFormalized(boolean formalized) {
	        this.formalized = formalized;
	    }

	    public int getFamilyincome() {
	        return familyincome;
	    }

	    public void setFamilyincome(int familyincome) {
	        this.familyincome = familyincome;
	    }

	    public boolean isActivated() {
	        return activated;
	    }

	    public void setActivated(boolean activated) {
	        this.activated = activated;
	    }


	    public int getMaincategory() {
	        return maincategory;
	    }

	    public void setMaincategory(int maincategory) {
	        this.maincategory = maincategory;
	    }

	    public int getSubcategory() {
	        return subcategory;
	    }

	    public void setSubcategory(int subcategory) {
	        this.subcategory = subcategory;
	    }

	    public String getOthersubcategory() {
	        return othersubcategory;
	    }

	    public void setOthersubcategory(String othersubcategory) {
	        this.othersubcategory = othersubcategory;
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

	    public String getCpf() {
	        return cpf;
	    }

	    public void setCpf(String cpf) {
	        this.cpf = cpf;
	    }

	    public String getBirthday() {
	        return birthday;
	    }

	    public void setBirthday(String birthday) {
	        this.birthday = birthday;
	    }

	    public boolean isUpdated() {
	        return updated;
	    }

	    public void setUpdated(boolean updated) {
	        this.updated = updated;
	    }

	    public String getMobilenumber() {
	        return mobilenumber;
	    }

	    public void setMobilenumber(String mobilenumber) {
	        this.mobilenumber = mobilenumber;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public void showDatepicker(){

	    }

	    @Override
	    public String toString() {
	        return "UserDetails{" +
	                ", id=" + id +
	                ", userid=" + userid +
	                ", updated=" + updated +
	                ", name='" + name + '\'' +
	                ", socialname=" + socialname +
	                ", cpf='" + cpf + '\'' +
	                ", birthday=" + birthday +
	                ", mobilenumber='" + mobilenumber + '\'' +
	                ", email='" + email + '\'' +
	                ", education=" + education +
	                ", skincolor=" + skincolor +
	                ", genre=" + genre +
	                ", formalized=" + formalized +
	                ", familyincome=" + familyincome +
	                ", familynumber=" + familynumber +
	                ", activated=" + activated +
	                ", maincategory=" + maincategory +
	                ", subcategory=" + subcategory +
	                ", othersubcategory='" + othersubcategory + '\'' +
	                '}';
	    }
	   
	    public List<String> validator(List<String> errors, MessageSource messages, Locale locale){
	        
	        if(name == null || name.isEmpty() || name.length() < 5){
	        	errors.add(messages.getMessage("message.name_error", null, locale));
	        }else if(cpf == null || cpf.isEmpty() || cpf.length() != 11){
	        	errors.add(messages.getMessage("message.cpf_error", null, locale));
	        }else if(birthday == null || birthday.isEmpty() || birthday.length() <= 8){
	        	errors.add(messages.getMessage("message.birthday_error", null, locale));
	        }else if(email.isEmpty() || email.length() < 5|| !email.contains("@") || !email.contains(".")){
	        	errors.add(messages.getMessage("message.email_error", null, locale));
	        }else if(mobilenumber == null || mobilenumber.isEmpty() || mobilenumber.length() != 17){
	        	errors.add(messages.getMessage("message.mobile_error", null, locale));
	        }else if(education == 0){
	        	errors.add(messages.getMessage("message.education_error", null, locale));
	        }else if(skincolor == 0){
	        	errors.add(messages.getMessage("message.skincolor_error", null, locale));
	        }else if(familyincome == 0){
	        	errors.add(messages.getMessage("message.familyincome_error", null, locale));
	        }else if(familynumber == null || familynumber.isEmpty() || Integer.parseInt(familynumber) == 0){
	        	errors.add(messages.getMessage("message.familynumber_error", null, locale));
	        }else if(maincategory == 0){
	        	errors.add(messages.getMessage("message.maincategory_error", null, locale));
	        }else if(subcategory == 0){
	        	errors.add(messages.getMessage("message.subcategory_error", null, locale));
	        }

	        return errors;

	    }


}
