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


import com.org.spring.model.productprice.FixedVariableCost;
import com.org.spring.model.productprice.ProductPrice;
import com.org.spring.model.productprice.Raw;


import com.org.spring.repository.UserDetailsRepository;
import com.org.spring.repository.productprice.FixedVariableCostProductPriceRepository;
import com.org.spring.repository.productprice.ProductPriceRepository;
import com.org.spring.repository.productprice.RawProductPriceRepository;
import com.org.spring.user.ApplicationUser;
import com.org.spring.user.ApplicationUserRepository;

@RestController
@RequestMapping("/api")
public class ProductPriceApiController {
    @Autowired
    private MessageSource messages;
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    
    @Autowired
    ProductPriceRepository productPriceRepository;
    @Autowired
    RawProductPriceRepository rawProductPriceRepository;
    @Autowired
    FixedVariableCostProductPriceRepository fixedVariableCostProductPriceRepository;
    

    /*
     * 							PRODUCT PRICE
     */
    
    @RequestMapping(value = "productprice", method = RequestMethod.POST)
    public @ResponseBody ProductPrice postProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody ProductPrice productPrice)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	
    	// DELETING OLD INSTALLMENTS
    	System.out.println("productPrice POST " + productPrice.toString());
    	if(productPrice.getId() != 0) {
    		ProductPrice productPriceOld = productPriceRepository.findById(productPrice.getId());
    		if(productPriceOld.getRaws() != null && !productPriceOld.getRaws().isEmpty()) {
    			rawProductPriceRepository.deleteAll(productPriceOld.getRaws());
    		}
    		if(productPriceOld.getFixedvariablecosts() != null && !productPriceOld.getFixedvariablecosts().isEmpty()) {
    			fixedVariableCostProductPriceRepository.deleteAll(productPriceOld.getFixedvariablecosts());
    		}
    		
    	}
    	
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	productPrice.setUserid(user.getId());
    	productPrice.setUpdated(true);
    	
    	if(productPrice.getId() == 0) productPriceRepository.save(productPrice);
    	
    	List<Raw> rawsDone = new ArrayList<>();
    	
    	for(Raw raw : productPrice.getRaws()) {
    		raw.setProductpriceid(productPrice.getId());
    		raw.setUpdated(true);
    		rawsDone.add(raw);
    	}
    	
		List<FixedVariableCost> fixedVariableCostsDone = new ArrayList<>();
    	
    	for(FixedVariableCost fixedVariableCost : productPrice.getFixedvariablecosts()) {
    		fixedVariableCost.setProductpriceid(productPrice.getId());
    		fixedVariableCost.setUpdated(true);
    		fixedVariableCostsDone.add(fixedVariableCost);
    	}
    	
    	
    	productPrice.setRaws(new TreeSet<>(rawsDone));
    	productPrice.setFixedvariablecosts(new TreeSet<>(fixedVariableCostsDone));
    	productPriceRepository.save(productPrice);
    	return productPrice;
    }
    @RequestMapping(value = "productprice", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("productprice_id") long productprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("productprice delete " + productprice_id);
    	ProductPrice productPrice  = productPriceRepository.findById(productprice_id);
    	
    	if(productPrice != null) {
    		if(productPrice.getRaws() != null && !productPrice.getRaws().isEmpty()) {
    			rawProductPriceRepository.deleteAll(productPrice.getRaws());
    		}
    		if(productPrice.getFixedvariablecosts() != null && !productPrice.getFixedvariablecosts().isEmpty()) {
    			fixedVariableCostProductPriceRepository.deleteAll(productPrice.getFixedvariablecosts());
    		}
    		productPriceRepository.delete(productPrice);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "productprices", method = RequestMethod.GET)
    public @ResponseBody List<ProductPrice> getProductPrices(final HttpServletRequest req, final HttpServletResponse res,
    		Principal principal)  throws NoSuchMessageException, IOException{
    	
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("ProductPrices get ");
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}
    	System.out.println(productPriceRepository.findByUserid(user.getId()).toString());
    	
    	return productPriceRepository.findByUserid(user.getId());
    	
    }
    @RequestMapping(value = "productprice/raw", method = RequestMethod.POST)
    public @ResponseBody Raw postRawProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody Raw raw)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("RawProductPrice post " + raw.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	raw.setUpdated(true);
    	
    	rawProductPriceRepository.save(raw);
    	
    	return raw;
    }
    @RequestMapping(value = "productprice/raw", method = RequestMethod.DELETE)
    public @ResponseBody boolean deletRawProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("raw_productprice_id") long raw_productprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("raw_productprice delete " + raw_productprice_id);
    	Raw raw  = rawProductPriceRepository.findById(raw_productprice_id);
    	
    	if(raw != null) {
    		
    		rawProductPriceRepository.delete(raw);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
    @RequestMapping(value = "productprice/fixedvariablecost", method = RequestMethod.POST)
    public @ResponseBody FixedVariableCost postFixedVariableCostProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    													@RequestBody FixedVariableCost fixedVariableCost)  throws NoSuchMessageException, IOException{
    	ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
    	System.out.println("fixedVariableCost post " + fixedVariableCost.toString());
    	
    	if(user == null) {
    		res.sendError(400,  messages.getMessage("message.badCredentials", null, req.getLocale()));
    		return null;
    	}

    	fixedVariableCost.setUpdated(true);
    	
    	fixedVariableCostProductPriceRepository.save(fixedVariableCost);
    	
    	return fixedVariableCost;	
    }
    @RequestMapping(value = "productprice/fixedvariablecost", method = RequestMethod.DELETE)
    public @ResponseBody boolean deletFixedVariableCostProductPrice(final HttpServletRequest req, final HttpServletResponse res, Principal principal, 
    		@RequestParam("fixedvariablecost_productprice_id") long fixedvariablecost_productprice_id)  throws NoSuchMessageException, IOException{
    	boolean ok = true;
    	System.out.println("fixedvariablecost_productprice delete " + fixedvariablecost_productprice_id);
    	FixedVariableCost fixedVariableCost  = fixedVariableCostProductPriceRepository.findById(fixedvariablecost_productprice_id);
    	
    	if(fixedVariableCost != null) {
    		
    		fixedVariableCostProductPriceRepository.delete(fixedVariableCost);
    		
    	}else {
    		res.sendError(400,  messages.getMessage("message.transactionnotfound", null, req.getLocale()));
    		return false;
    	}
    	
    	return ok;
    }
}