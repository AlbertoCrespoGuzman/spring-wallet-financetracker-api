package com.org.spring.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.org.spring.error.UserAlreadyExistException;
import com.org.spring.user.ApplicationUser;
import com.org.spring.user.UserDto;

public interface IUserService {

    ApplicationUser registerNewUserAccount(UserDto accountDto, boolean admin) throws UserAlreadyExistException;

    ApplicationUser getUser(String verificationToken);

    void saveRegisteredUser(ApplicationUser user);

    void deleteUser(ApplicationUser user);

    void createVerificationTokenForUser(ApplicationUser user, String token);

  //  VerificationToken getVerificationToken(String VerificationToken);

 //   VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(ApplicationUser user, String token);

    ApplicationUser findUserByEmail(String email);

  //  PasswordResetToken getPasswordResetToken(String token);

    ApplicationUser getUserByPasswordResetToken(String token);

    ApplicationUser getUserByID(long id);

    void changeUserPassword(ApplicationUser user, String password);

    boolean checkIfValidOldPassword(ApplicationUser user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(ApplicationUser user) throws UnsupportedEncodingException;

    ApplicationUser updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

}
