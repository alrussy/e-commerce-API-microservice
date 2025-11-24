package com.alrussy_dev.order_service.client.keyclock;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KeyclockService {
    private RestClient client;

    public KeyclockService(@Qualifier("keyclockClient") RestClient client) {
        this.client = client;
        // TODO Auto-generated constructor stub
    }
}
