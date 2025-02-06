package com.arianledo.customers.services;

import com.arianledo.customers.entities.BusinessEntity;
import com.arianledo.customers.entities.UserEntity;
import com.arianledo.customers.repository.BusinessEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BusinessEntityServiceImp implements BusinessEntityService {

    @Autowired
    private BusinessEntityRepository repository;

    @Autowired
    private UserService userService;

    public BusinessEntity getBusinessEntityWithCustomers(Long id) {
        return repository.findByIdWithCustomers(id);
    }

    public BusinessEntity addBusinessEntity(BusinessEntity businessEntity) {
        BusinessEntity businessEntityCeated = repository.save(businessEntity);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userEntity = userService.findByUsername(principal.toString());
        userEntity.setBusinessEntity(businessEntityCeated);
        userService.updateUser(userEntity.getId(), userEntity);

        return businessEntityCeated;
    }

}
