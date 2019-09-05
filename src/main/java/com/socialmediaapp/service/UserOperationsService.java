package com.socialmediaapp.service;

import org.springframework.http.ResponseEntity;

public interface UserOperationsService {

    ResponseEntity createUser(String userId, String userName);

    ResponseEntity getAllUsers();
}
