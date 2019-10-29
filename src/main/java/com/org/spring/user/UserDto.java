package com.org.spring.user;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.org.spring.validation.PasswordMatches;
import com.org.spring.validation.ValidEmail;
import com.org.spring.validation.ValidPassword;


@PasswordMatches
public class UserDto {
/*	
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;
*/
    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String confirmPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String username;

    private String lang;

    public String getUsername() {
        return username;
    }

    public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setUsername(final String email) {
        this.username = email;
    }

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }
/*
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
*/
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.confirmPassword = matchingPassword;
    }

	@Override
	public String toString() {
		return "UserDto [password=" + password + ", confirmPassword=" + confirmPassword + ", username=" + username
				+ ", lang=" + lang + ", role=" + role + "]";
	}



   
}

