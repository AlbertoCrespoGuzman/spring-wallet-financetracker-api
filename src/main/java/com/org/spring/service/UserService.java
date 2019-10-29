package com.org.spring.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.spring.error.UserAlreadyExistException;
import com.org.spring.user.ApplicationUser;
import com.org.spring.user.ApplicationUserRepository;
import com.org.spring.user.UserDto;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;
/*
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
*/
    @Autowired
    private PasswordEncoder passwordEncoder;
/*
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SessionRegistry sessionRegistry;
*/
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    // API

    @Override
    public ApplicationUser registerNewUserAccount(final UserDto accountDto, boolean admin) {
        if (emailExist(accountDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getUsername());
        }
        final ApplicationUser user = new ApplicationUser();

     //   user.setFirstName(accountDto.getFirstName());
     //   user.setLastName(accountDto.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        user.setUsername(accountDto.getUsername());
   //     user.setUsing2FA(accountDto.isUsing2FA());
        /*
        if(admin) {
        	user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        }else {
        	user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        } */
        
        return applicationUserRepository.save(user);
    }

    @Override
    public ApplicationUser getUser( String verificationToken) {
         ApplicationUser token = applicationUserRepository.findByConfirmationToken(verificationToken);
        if (token != null) {
            return token;
        }
        return null;
    }
/*
    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return applicationUserRepository.findByToken(VerificationToken);
    }
*/
    @Override
    public void saveRegisteredUser(final ApplicationUser user) {
    	applicationUserRepository.save(user);
    }
/*
    @Override
    public void deleteUser(final User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        repository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public ApplicationUser findUserByEmail(final String email) {
        return applicationUserRepository.findByUsername(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(final long id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
*/
    @Override
    public String validateVerificationToken(String token) {
         ApplicationUser user = applicationUserRepository.findByConfirmationToken(token);
        if (user == null) {
            return TOKEN_INVALID;
        }
/*
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        } */

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        applicationUserRepository.save(user);
        return TOKEN_VALID;
    }
/*
    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
    }

    @Override
    public User updateUser2FA(boolean use2FA) {
        final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) curAuth.getPrincipal();
        currentUser.setUsing2FA(use2FA);
        currentUser = repository.save(currentUser);
        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return currentUser;
    }
*/
    
    private boolean emailExist(final String email) {
        return applicationUserRepository.findByUsername(email) != null;
    }
/*
    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals().stream().filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(Object::toString).collect(Collectors.toList());
    }
    */

	@Override
	public void deleteUser(ApplicationUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createVerificationTokenForUser(ApplicationUser user, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPasswordResetTokenForUser(ApplicationUser user, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ApplicationUser findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUser getUserByPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUser getUserByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeUserPassword(ApplicationUser user, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkIfValidOldPassword(ApplicationUser user, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String generateQRUrl(ApplicationUser user) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUser updateUser2FA(boolean use2fa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsersFromSessionRegistry() {
		// TODO Auto-generated method stub
		return null;
	}
}