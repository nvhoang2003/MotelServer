package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TenantRepository extends CrudRepository<Tenant, Integer> {
    List<Tenant> getTenantsByMotel(Motel motel);

    List<Tenant> getTenantsByUser(User user);

    List<Tenant> getTenantsByMotel_Id(int motel_id);

    List<Tenant> getTenantsByUser_Id(int User_Id);
}
