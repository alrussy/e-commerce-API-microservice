package com.alrussy.customer_service.service;

import com.alrussy.customer_service.entity.Favorite;
import com.alrussy.customer_service.repository.FavoriteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoriteRepository repository;

    @Override
    public List<Favorite> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void deleteByIdAndUsername(String skuCode, String userName) {
        repository.deleteBySkuCodeAndUsername(skuCode, userName);
    }

    @Override
    public Long save(Favorite request) {
        return repository.save(request).getId();
    }

    @Override
    public void deleteByIdAndUsername(Long id, String userName) {
        // TODO Auto-generated method stub

    }
}
