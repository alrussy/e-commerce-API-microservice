package com.alrussy.customer_service.service;

import com.alrussy.customer_service.entity.Favorite;

public interface FavoritesService extends BaseService<Long, Favorite, Favorite> {

	void deleteByIdAndUsername(String skuCode, String userName);

}
