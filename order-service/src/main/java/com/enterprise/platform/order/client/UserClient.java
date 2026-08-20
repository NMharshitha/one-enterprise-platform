package com.enterprise.platform.order.client;

import com.enterprise.platform.order.dto.UserResponse;
import com.enterprise.platform.order.exception.UserNotFoundException;
import com.enterprise.platform.order.exception.UserServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);

    private final RestClient restClient;

    public UserClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retry(name = "userService", fallbackMethod = "fallback")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallback")
    public UserResponse getUser(Long id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserNotFoundException("User not found: " + id);
                })
                .body(UserResponse.class);
    }

    private UserResponse fallback(Long id, Throwable throwable) {
        log.warn("Fallback triggered for user {}: {}", id, throwable.getMessage());
        throw new UserServiceUnavailableException("User service is unavailable", throwable);
    }
}