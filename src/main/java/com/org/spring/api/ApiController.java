package com.org.spring.api;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.spring.model.Goal;
import com.org.spring.model.Installment;
import com.org.spring.model.Transaction;
import com.org.spring.model.UserDetails;
import com.org.spring.repository.GoalRepository;
import com.org.spring.repository.InstallmentRepository;
import com.org.spring.repository.TransactionRepository;
import com.org.spring.repository.UserDetailsRepository;
import com.org.spring.repository.productprice.FixedVariableCostProductPriceRepository;
import com.org.spring.repository.productprice.ProductPriceRepository;
import com.org.spring.repository.productprice.RawProductPriceRepository;
import com.org.spring.repository.serviceprice.FixedCostServicePriceRepository;
import com.org.spring.repository.serviceprice.LabourCostServicePriceRepository;
import com.org.spring.repository.serviceprice.LabourTaxServicePriceRepository;
import com.org.spring.repository.serviceprice.ServicePriceRepository;
import com.org.spring.repository.serviceprice.VariableCostServicePriceRepository;
import com.org.spring.user.ApplicationUser;
import com.org.spring.user.ApplicationUserRepository;


@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private MessageSource messages;
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    InstallmentRepository installmentRepository;
    @Autowired
    GoalRepository goalRepository;
    @Autowired
    ServicePriceRepository servicePriceRepository;
    @Autowired
    FixedCostServicePriceRepository fixedCostServicePriceRepository;
    @Autowired
    LabourCostServicePriceRepository labourCostServicePriceRepository;
    @Autowired
    LabourTaxServicePriceRepository labourTaxServicePriceRepository;
    @Autowired
    VariableCostServicePriceRepository variableCostServicePriceRepository;
    @Autowired
    ProductPriceRepository productPriceRepository;
    @Autowired
    RawProductPriceRepository rawProductPriceRepository;
    @Autowired
    FixedVariableCostProductPriceRepository fixedVariableCostProductPriceRepository;
    
    @RequestMapping(value = "/users/details", method = RequestMethod.GET)
    public @ResponseBody UserDetails getUserDetails(final HttpServletRequest req, final HttpServletResponse res, Principal principal) throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
		UserDetails userDetails = userDetailsRepository.findByUserid(user.getId());
		if(userDetails == null) {
			return new UserDetails();
		}
		return userDetails;
    }
    
    
    @RequestMapping(value = "/users/details", method = RequestMethod.POST)
    public @ResponseBody UserDetails postUserDetails(final HttpServletRequest req,final HttpServletResponse res, Principal principal, @RequestBody UserDetails userDetails) throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	List<String> errors = new ArrayList<>();
    	System.out.println("userDetails" + userDetails.toString());
    	errors = userDetails.validator(errors, messages,  req.getLocale());
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}else if(!errors.isEmpty()) {
    		res.sendError(400,  errors.get(0));
    		return null;
    	}else {
    		userDetails.setUserid(user.getId());
    		userDetails.setUpdated(true);
    		userDetails = userDetailsRepository.save(userDetails);
    		System.out.println(userDetails.toString());
        	return userDetails;
    	}
    	
    } 
    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    public @ResponseBody Transaction postTransaction(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody Transaction transaction)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	
    	// DELETING OLD INSTALLMENTS
    	System.out.println("transaction POST " + transaction);
    	if(transaction.getId() != 0) {
    		Transaction transactionOld = transactionRepository.findById(transaction.getId());
    		if(transactionOld.isInstallment() && transactionOld.getInstallments() != null && !transactionOld.getInstallments().isEmpty()) {
    			installmentRepository.deleteAll(transactionOld.getInstallments());
    		}
    	}
    	
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	transaction.setUserid(user.getId());
    	transaction.setUpdated(true);
    	
    	if(transaction.getId() == 0) transactionRepository.save(transaction);
    	
    	List<Installment> installmentsDone = new ArrayList<>();
    	
    	for(Installment installment : transaction.getInstallments()) {
    		installment.setUserid(user.getId());
    		installment.setTransactionid(transaction.getId());
    		installment.setUpdated(true);
    		installmentsDone.add(installment);
    	}
    	
    	transaction.setInstallments(installmentsDone);
    	transactionRepository.save(transaction);
    	return transaction;
    }
    @RequestMapping(value = "transaction", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteTransaction(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("transaction_id") long transaction_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("transaction delete " + transaction_id);
    	Transaction transaction  = transactionRepository.findById(transaction_id);
    	
    	if(transaction != null) {
    		if(transaction.isInstallment() && transaction.getInstallments() != null && !transaction.getInstallments().isEmpty()) {
    			installmentRepository.deleteAll(transaction.getInstallments());
    		}
    		transactionRepository.delete(transaction);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "transactions", method = RequestMethod.GET)
    public @ResponseBody List<Transaction> getTransactions(final HttpServletRequest req, final HttpServletResponse res,
    		Principal principal)  throws NoSuchMessageException, IOException{
    	
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("transactions get ");
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	System.out.println(transactionRepository.findByUserid(user.getId()).size());
    	return transactionRepository.findByUserid(user.getId());
    	
    }
    @RequestMapping(value = "installment", method = RequestMethod.POST)
    public @ResponseBody Installment postInstallment(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody Installment installment)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("Installment post " + installment.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	installment.setUserid(user.getId());
    	installment.setUpdated(true);
    	
    	installmentRepository.save(installment);
    	
    	return installment;
    }
    @RequestMapping(value = "installment", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteInstallment(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("installment_id") long installment_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("installment delete " + installment_id);
    	Installment installment  = installmentRepository.findById(installment_id);
    	
    	if(installment != null) {
    		
    		installmentRepository.delete(installment);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    
    @RequestMapping(value = "goal", method = RequestMethod.POST)
    public @ResponseBody Goal postGoal(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody Goal goal)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("goal post " + goal.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	

    	goal.setUserid(user.getId());
    	goal.setUpdated(true);
    	
    	Goal goalUpdated = goalRepository.save(goal);
    	System.out.println("goal after saved = " + goalUpdated.toString());
    	return goalUpdated;
    }
    @RequestMapping(value = "goal", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteGoal(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("goal_id") long goal_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("deleting goal id -> " + goal_id);
    	Goal goal  = goalRepository.findById(goal_id);
    	
    	if(goal != null) {
    		goalRepository.delete(goal);
    		System.out.println("goalRepository.delete(goal);");
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.goalnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "goals", method = RequestMethod.GET)
    public @ResponseBody List<Goal> getGoals(final HttpServletRequest req, final HttpServletResponse res,
    		Principal principal)  throws NoSuchMessageException, IOException{
    	
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("goals get ");
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	return goalRepository.findByUserid(user.getId());
    	
    }
}