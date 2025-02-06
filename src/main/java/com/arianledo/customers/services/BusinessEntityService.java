package com.arianledo.customers.services;

import com.arianledo.customers.entities.BusinessEntity;

public interface BusinessEntityService {

    BusinessEntity getBusinessEntityWithCustomers(Long id);

    BusinessEntity addBusinessEntity(BusinessEntity businessEntity);
}
