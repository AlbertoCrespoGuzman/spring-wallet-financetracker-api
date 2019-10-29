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
import com.org.spring.model.productprice.FixedVariableCost;
import com.org.spring.model.productprice.ProductPrice;
import com.org.spring.model.productprice.Raw;
import com.org.spring.model.serviceprice.FixedCost;
import com.org.spring.model.serviceprice.LabourCost;
import com.org.spring.model.serviceprice.LabourTax;
import com.org.spring.model.serviceprice.ServicePrice;
import com.org.spring.model.serviceprice.VariableCost;
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
public class ServicePriceApiController  {
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
    
    /*
     * 							SERVICE PRICE
     */
    
    @RequestMapping(value = "serviceprice", method = RequestMethod.POST)
    public @ResponseBody ServicePrice postServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody ServicePrice servicePrice)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	
    	// DELETING OLD INSTALLMENTS
    	System.out.println("ServicePrice POST " + servicePrice);
    	if(servicePrice.getId() != 0) {
    		ServicePrice servicePriceOld = servicePriceRepository.findById(servicePrice.getId());
    		if(servicePriceOld.getFixedcosts() != null && !servicePriceOld.getFixedcosts().isEmpty()) {
    			fixedCostServicePriceRepository.deleteAll(servicePriceOld.getFixedcosts());
    		}
    		if(servicePriceOld.getLabourcosts() != null && !servicePriceOld.getLabourcosts().isEmpty()) {
    			labourCostServicePriceRepository.deleteAll(servicePriceOld.getLabourcosts());
    		}
    		if(servicePriceOld.getLabourtaxs() != null && !servicePriceOld.getLabourtaxs().isEmpty()) {
    			labourTaxServicePriceRepository.deleteAll(servicePriceOld.getLabourtaxs());
    		}
    		if(servicePriceOld.getVariablecosts() != null && !servicePriceOld.getVariablecosts().isEmpty()) {
    			variableCostServicePriceRepository.deleteAll(servicePriceOld.getVariablecosts());
    		}
    	}
    	
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	servicePrice.setUserid(user.getId());
    	servicePrice.setUpdated(true);
    	
    	if(servicePrice.getId() == 0) servicePriceRepository.save(servicePrice);
    	
    	List<FixedCost> fixedCostDone = new ArrayList<>();
    	
    	for(FixedCost fixedCost : servicePrice.getFixedcosts()) {
    		fixedCost.setServicepriceid(servicePrice.getId());
    		fixedCost.setUpdated(true);
    		fixedCostDone.add(fixedCost);
    	}
    	
		List<LabourCost> labourCostDone = new ArrayList<>();
    	
    	for(LabourCost labourCost : servicePrice.getLabourcosts()) {
    		labourCost.setServicepriceid(servicePrice.getId());
    		labourCost.setUpdated(true);
    		labourCostDone.add(labourCost);
    	}
    	
    	List<LabourTax> labourTaxDone = new ArrayList<>();
    	
    	for(LabourTax labourTax : servicePrice.getLabourtaxs()) {
    		labourTax.setServicepriceid(servicePrice.getId());
    		labourTax.setUpdated(true);
    		labourTaxDone.add(labourTax);
    	}
    	
    	List<VariableCost> variableCostDone = new ArrayList<>();
    	
    	for(VariableCost variableCost : servicePrice.getVariablecosts()) {
    		variableCost.setServicepriceid(servicePrice.getId());
    		variableCost.setUpdated(true);
    		variableCostDone.add(variableCost);
    	}
    	
    	
    	servicePrice.setFixedcosts(new TreeSet<>(fixedCostDone));
    	servicePrice.setLabourcosts(new TreeSet<>(labourCostDone));
    	servicePrice.setLabourtaxs(new TreeSet<>(labourTaxDone));
    	servicePrice.setVariablecosts(new TreeSet<>(variableCostDone));
    	servicePriceRepository.save(servicePrice);
    	return servicePrice;
    }
    @RequestMapping(value = "serviceprice", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("serviceprice_id") long serviceprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("servicePrice delete " + serviceprice_id);
    	ServicePrice servicePrice  = servicePriceRepository.findById(serviceprice_id);
    	
    	if(servicePrice != null) {
    		if(servicePrice.getFixedcosts() != null && !servicePrice.getFixedcosts().isEmpty()) {
    			fixedCostServicePriceRepository.deleteAll(servicePrice.getFixedcosts());
    		}
    		if(servicePrice.getLabourcosts() != null && !servicePrice.getLabourcosts().isEmpty()) {
    			labourCostServicePriceRepository.deleteAll(servicePrice.getLabourcosts());
    		}
    		if(servicePrice.getLabourtaxs() != null && !servicePrice.getLabourtaxs().isEmpty()) {
    			labourTaxServicePriceRepository.deleteAll(servicePrice.getLabourtaxs());
    		}
    		if(servicePrice.getVariablecosts() != null && !servicePrice.getVariablecosts().isEmpty()) {
    			variableCostServicePriceRepository.deleteAll(servicePrice.getVariablecosts());
    		}
    		servicePriceRepository.delete(servicePrice);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "serviceprices", method = RequestMethod.GET)
    public @ResponseBody List<ServicePrice> getServicePrices(final HttpServletRequest req, final HttpServletResponse res,
    		Principal principal)  throws NoSuchMessageException, IOException{
    	
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("serviceprices get ");
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	System.out.println(servicePriceRepository.findByUserid(user.getId()).size());
    	return servicePriceRepository.findByUserid(user.getId());
    	
    }
    @RequestMapping(value = "serviceprice/fixedcost", method = RequestMethod.POST)
    public @ResponseBody FixedCost postFixedCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody FixedCost fixedCost)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("FixedCost post " + fixedCost.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	fixedCost.setUpdated(true);
    	
    	fixedCostServicePriceRepository.save(fixedCost);
    	
    	return fixedCost;
    }
    @RequestMapping(value = "serviceprice/fixedcost", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteFixedCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("fixedcost_serviceprice_id") long fixedcost_serviceprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("fixedcost_serviceprice delete " + fixedcost_serviceprice_id);
    	FixedCost fixedCost  = fixedCostServicePriceRepository.findById(fixedcost_serviceprice_id);
    	
    	if(fixedCost != null) {
    		
    		fixedCostServicePriceRepository.delete(fixedCost);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "serviceprice/labourcost", method = RequestMethod.POST)
    public @ResponseBody LabourCost postLabourCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody LabourCost labourCost)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("labourCost post " + labourCost.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	labourCost.setUpdated(true);
    	
    	labourCostServicePriceRepository.save(labourCost);
    	
    	return labourCost;
    }
    @RequestMapping(value = "serviceprice/labourcost", method = RequestMethod.DELETE)
    public @ResponseBody boolean deletLabourCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("labourcost_serviceprice_id") long labourcost_serviceprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("labourcost_serviceprice delete " + labourcost_serviceprice_id);
    	LabourCost labourCost  = labourCostServicePriceRepository.findById(labourcost_serviceprice_id);
    	
    	if(labourCost != null) {
    		
    		labourCostServicePriceRepository.delete(labourCost);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "serviceprice/labourtax", method = RequestMethod.POST)
    public @ResponseBody LabourTax postLabourTaxServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody LabourTax labourTax)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("labourTax post " + labourTax.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	labourTax.setUpdated(true);
    	
    	labourTaxServicePriceRepository.save(labourTax);
    	
    	return labourTax;
    }
    @RequestMapping(value = "serviceprice/labourtax", method = RequestMethod.DELETE)
    public @ResponseBody boolean deletLabourTaxServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("labourtax_serviceprice_id") long labourtax_serviceprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("labourTax_serviceprice delete " + labourtax_serviceprice_id);
    	LabourTax labourTax  = labourTaxServicePriceRepository.findById(labourtax_serviceprice_id);
    	
    	if(labourTax != null) {
    		
    		labourTaxServicePriceRepository.delete(labourTax);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "serviceprice/variablecost", method = RequestMethod.POST)
    public @ResponseBody VariableCost postVariableCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody VariableCost variableCost)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("VariableCost post " + variableCost.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	variableCost.setUpdated(true);
    	
    	variableCostServicePriceRepository.save(variableCost);
    	
    	return variableCost;
    }
    @RequestMapping(value = "serviceprice/variablecost", method = RequestMethod.DELETE)
    public @ResponseBody boolean deletVariableCostServicePrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("variablecost_serviceprice_id") long variablecost_serviceprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("variablecost_serviceprice_id delete " + variablecost_serviceprice_id);
    	VariableCost variableCost  = variableCostServicePriceRepository.findById(variablecost_serviceprice_id);
    	
    	if(variableCost != null) {
    		
    		variableCostServicePriceRepository.delete(variableCost);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }

}