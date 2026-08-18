package com.enterprise.platform.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Day 6 update: instead of a fixed host:port, Order Service now calls
 * User Service by its logical name (USER-SERVICE) through the discovery
 * server. @LoadBalanced makes RestClient resolve "http://USER-SERVICE"
 * into a real instance address behind the scenes.
 */
@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(3000);
        return RestClient.builder().requestFactory(factory);
    }

    @Bean
    public RestClient userRestClient(RestClient.Builder loadBalancedRestClientBuilder) {
        return loadBalancedRestClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }
}