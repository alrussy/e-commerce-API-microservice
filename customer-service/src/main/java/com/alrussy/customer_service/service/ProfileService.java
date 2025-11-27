package com.alrussy.customer_service.service;

import com.alrussy.customer_service.entity.Profile;

public interface ProfileService extends BaseService<String, Profile, Profile> {

    void deleteByUsername(String userName);

    Profile findById(String username);

    void deleteByIdAndUsername(Long id, String userName);
}
