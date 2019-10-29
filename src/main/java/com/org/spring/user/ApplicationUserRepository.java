package com.org.spring.user;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    ApplicationUser findByConfirmationToken(String confirmationToken);
    ApplicationUser findByUsernameAndEnabled(String username, boolean enabled);
    ApplicationUser findByUsernameAndType(String username, int type);
}