package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TenantRepository extends CrudRepository<Tenant, Integer> {
    List<Tenant> getTenantsByMotel(Motel motel);

    List<Tenant> getTenantsByUser(User user);
}
