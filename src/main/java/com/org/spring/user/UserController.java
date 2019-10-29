package com.org.spring.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.org.spring.service.IUserService;
import com.org.spring.util.JsonMessage;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUserService userService;
	
    @Autowired
    private MessageSource messages;

    @Autowired
    private Environment env;
    
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @RequestMapping(value = "/signin/facebook", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<JsonMessage> signinFacebook(@RequestBody UserDto accountDto, final HttpServletRequest request, final HttpServletResponse response) throws NoSuchMessageException, IOException {
    	ArrayList<JsonMessage> list = new ArrayList<>();
      
    	ApplicationUser facebookUser = applicationUserRepository.findByUsernameAndType(accountDto.getUsername(), 2);
    	if(accountDto == null || accountDto.getUsername() == null || accountDto.getUsername().length() == 0) {
    		response.sendError(400, messages.getMessage("message.badCredentials", null, request.getLocale()));
    		
    		JsonMessage message = new JsonMessage(false, messages.getMessage("message.badCredentials", null, request.getLocale()));
            list.add(message);
    	}else if(facebookUser != null){
    		response.setStatus(200);
    		
    		JsonMessage message = new JsonMessage(true, messages.getMessage("message.loginSucc", null, request.getLocale()));
            list.add(message);
    	}else {
    		ApplicationUser userToRegister = new ApplicationUser();
    		userToRegister.setUsername(accountDto.getUsername());
    		userToRegister.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
    		userToRegister.setEnabled(true);
    		userToRegister.setConfirmationToken("");
    		userToRegister.setType(2); // TYPE 2 IS FACEBOOK USER
    		userToRegister.setLang(accountDto.getLang() == null || accountDto.getLang().isEmpty() ? request.getLocale().toLanguageTag() : accountDto.getLang());
    		applicationUserRepository.save(userToRegister);
    		
    		JsonMessage message = new JsonMessage(true, messages.getMessage("message.regSucc", null, request.getLocale()));
            list.add(message);
    		response.setStatus(200);
    	}
    	
    	return list;
    }
    
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @ResponseBody	
    public ArrayList<JsonMessage> signUp(@RequestBody UserDto accountDto, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    	ArrayList<JsonMessage> list = new ArrayList<>();
      System.out.println("signup ");
    	list = validateUserRegistration(accountDto, request.getLocale());
    	
    	if(list.isEmpty()){
    		System.out.println("the list is empty");
    		String token  = UUID.randomUUID().toString();
    		ApplicationUser user = applicationUserRepository.findByConfirmationToken(token);
    		while(user != null) {
    			token  = UUID.randomUUID().toString();
        		user = applicationUserRepository.findByConfirmationToken(token);
    		}
    		ApplicationUser userToRegister = new ApplicationUser();
    		userToRegister.setUsername(accountDto.getUsername());
    		userToRegister.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
    		userToRegister.setEnabled(false);
    		userToRegister.setConfirmationToken(token);
    		userToRegister.setType(1);
    		System.out.println("request.getLocale().toLanguageTag() " + request.getLocale().toLanguageTag());
    		userToRegister.setLang(accountDto.getLang() == null || accountDto.getLang().isEmpty() ? request.getLocale().toLanguageTag() : accountDto.getLang());
    	//	final SimpleMailMessage email = constructEmailMessage(request.getLocale(), userToRegister, getAppUrl(request));
        //    mailSender.send(email);
    		final MimeMessage email = constructEmailMessageWithHTMLCODE(request.getLocale(), userToRegister, getAppUrl(request));
    		mailSender.send(email);
            applicationUserRepository.save(userToRegister);

           JsonMessage message = new JsonMessage(true, messages.getMessage("message.regSucc", null, request.getLocale()));
           list.add(message);
           response.setStatus(200);
           
          
    	}else {
    		response.sendError(400, list.get(0).getMessage());
    	}

    	return list;
    }
    
    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        System.out.println("confirmRegistration");
    	Locale locale = request.getLocale();
        
        ApplicationUser user = applicationUserRepository.findByConfirmationToken(token);
        
        if (user != null) {
        	user.setEnabled(true);
        	applicationUserRepository.save(user);
        	System.out.println("request.getLocale() = " + request.getLocale());
        	ModelAndView mv = new ModelAndView("redirect:/#/login/" + messages.getMessage("message.registerconfirmation", null, request.getLocale()));
        	return mv;
        }
        return null;
    }

    // user activation - verification
/*
    @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }
*/
    // Reset password
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<JsonMessage> resetPassword(final HttpServletRequest request, @RequestBody UserDto accountDto , final HttpServletResponse response) throws IOException {
        
    	ApplicationUser user = applicationUserRepository.findByUsername(accountDto.getUsername());
    	System.out.println("accountDto.getUsername() " + accountDto.getUsername());
    	System.out.println("accountDto.user() " + user);
        ArrayList<JsonMessage> list = new ArrayList<>();
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            user.setConfirmationToken(token);
            
            mailSender.send(constructResetTokenEmail(request.getLocale(), user,getAppUrl(request)));
            applicationUserRepository.save(user);
            
            JsonMessage message = new JsonMessage(true, messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
            list.add(message);
        }else {
        	JsonMessage message = new JsonMessage(true, messages.getMessage("message.badEmail", null, request.getLocale()));
            list.add(message);
            response.sendError(400, message.getMessage());
        }
        return list;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ModelAndView showChangePasswordPage(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) {
    	 ApplicationUser user  = applicationUserRepository.findByConfirmationToken(token);
        if (user != null) {
        	ModelAndView mv = new ModelAndView("redirect:/#/reset/" + token);
        	return mv;
        }
        return null;
    }
 
    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<JsonMessage> savePassword(final HttpServletResponse response,final HttpServletRequest request, @RequestBody UserDto accountDto, @RequestParam("token") final String token) throws IOException {
    	System.out.println("token " + token);
    	final ApplicationUser user = applicationUserRepository.findByConfirmationToken(token);
    	System.out.println("user " + user);
        ArrayList<JsonMessage> list = new ArrayList<>();
        list = validatePasswordChange(accountDto, request.getLocale());
        if(list.isEmpty() && user != null) {
        	user.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        	applicationUserRepository.save(user);
        	JsonMessage message = new JsonMessage(true, messages.getMessage("message.updatePasswordSuc", null, request.getLocale()));
            list.add(message);
        }else {
        	JsonMessage message = new JsonMessage(true, messages.getMessage("message.badEmail", null, request.getLocale()));
            list.add(message);
    		response.sendError(400, message.getMessage());
    	}
    	return list;
    }
    /*
    // change user password
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }
*/
    
    // NO API
    private ArrayList<JsonMessage> validateUserRegistration(UserDto userDto, Locale locale) {
    	ArrayList<JsonMessage> list = new ArrayList<>();
    	
    	
    	if(applicationUserRepository.findByUsername(userDto.getUsername()) != null) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("username");
    		message.setMessage(messages.getMessage("UniqueUsername.user.username", null, locale));
    		list.add(message);
    	}else if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("username");
    		message.setMessage(messages.getMessage("message.badEmail", null, locale));
    		list.add(message);
    	}else if(userDto.getUsername().length() < 3 
    			|| !userDto.getUsername().contains("@") || !userDto.getUsername().contains(".") ) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("username");
    		message.setMessage(messages.getMessage("message.badEmail", null, locale));
    		list.add(message);
    	}else if(userDto.getPassword() == null || userDto.getConfirmPassword() == null || 
    			userDto.getPassword().isEmpty() || userDto.getConfirmPassword().isEmpty()) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("NotNull.user.password", null, locale));
    		list.add(message);
    	}else if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("PasswordMatches.user", null, locale));
    		list.add(message);
    	}else if(userDto.getPassword().length() < 4) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("error.wordLength", null, locale));
    		list.add(message);
    	}
    	return list;
    }
    private ArrayList<JsonMessage> validatePasswordChange(UserDto userDto, Locale locale) {
    	ArrayList<JsonMessage> list = new ArrayList<>();
    	
    	
    	if(userDto.getPassword() == null || userDto.getConfirmPassword() == null || 
    			userDto.getPassword().isEmpty() || userDto.getConfirmPassword().isEmpty()) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("NotNull.user.password", null, locale));
    		list.add(message);
    	}else if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("PasswordMatches.user", null, locale));
    		list.add(message);
    	}else if(userDto.getPassword().length() < 4) {
    		JsonMessage message = new JsonMessage();
    		message.setError(true);
    		message.setParam("password");
    		message.setMessage(messages.getMessage("error.wordLength", null, locale));
    		list.add(message);
    	}
    	return list;
    }
    private final SimpleMailMessage constructEmailMessage(Locale locale, ApplicationUser user, String appUrl) {
        final String recipientAddress = user.getUsername();
        final String subject = "Tá na Mão - Cadastro";
        final String confirmationUrl = appUrl + "/users/registrationConfirm?token=" + user.getConfirmationToken();
        final String message = messages.getMessage("message.email.confirmation", null, locale);
        final String htmlAnchorButton= "<a style=\"background-color: #1eb1c3; color:white; border-radius: 5px;\""
        		+ "href=\"" + confirmationUrl + "\"> Confirmar Cadastro </a>";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + htmlAnchorButton);
        email.setFrom(env.getProperty("support.email"));
        
        return email;
    }
    private final MimeMessage constructEmailMessageWithHTMLCODE(Locale locale, ApplicationUser user, String appUrl) {
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
    	MimeMessageHelper helper;
    	final String recipientAddress = user.getUsername();
        final String subject = "TÁ NA MÃO - CONFIRMAÇÃO DE CADASTRO";
        final String confirmationUrl = appUrl + "/users/registrationConfirm?token=" + user.getConfirmationToken();
        //String message = messages.getMessage("message.email.confirmation", null, locale);
        String message = "Olá! <br> <br> Obrigado pelo interesse no app TÁ NA MÃO! <br>  <br> Para confirmar seu cadastro clique no botão abaixo. <br> <br>";
        final String htmlAnchorButton= "<br></br></br><a style=\"background-color: #1eb1c3; color:white; border-radius: 5px;"
        		+ "padding:8px;\""
        		+ "href=\"" + confirmationUrl + "\"> Aceito </a>";
        
		try {
			helper = new MimeMessageHelper(mimeMessage, false, "ISO-8859-1");
			
			

	    	mimeMessage.setContent("<html><body>" + message +  htmlAnchorButton + "</body></html>", "text/html");
	    	
	    	helper.setTo(recipientAddress);
	    	helper.setSubject(subject);
	    	helper.setFrom(env.getProperty("spring.mail.username"));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
    	
    	return mimeMessage;
    }
    private final SimpleMailMessage constructResetTokenEmail(Locale locale, ApplicationUser user, String appUrl) {
        final String recipientAddress = user.getUsername();
        final String subject = "Reset Password";
        final String confirmationUrl = appUrl + "/users/changePassword?token=" + user.getConfirmationToken();
        final String message = messages.getMessage("message.resetPassword", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}