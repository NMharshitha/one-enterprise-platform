package com.enterprise.platform.order.client;

import com.enterprise.platform.order.dto.UserResponse;
import com.enterprise.platform.order.exception.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {
    private final RestClient restClient;

    public UserClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public UserResponse getUser(Long id) {
        return restClient.get()
            .uri("/api/users/{id}", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                throw new UserNotFoundException("User not found: " + id);
            })
            .body(UserResponse.class);
    }
}
