package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.exception.CustomNotFoundException;
import com.socialmediaapp.model.ErrorMessageConstantModel;
import com.socialmediaapp.model.UserModel;
import com.socialmediaapp.service.UserOperationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserOperationsServiceImpl implements UserOperationsService {
    /**
     * createUser method stores the userID and userName in the collection. Initially it checks for the conditions.
     * for every User userID must me unique and userName should not me empty.
     * if the conditions are not satisfied then it throw exceptions which is then catch in CustomExceptionHandler.
     *
     * @param userId
     * @param userName
     * @return Successfully stored user object wrapping in ResponseEntity.
     */
    public ResponseEntity createUser(String userId, String userName) {
        if (Objects.nonNull(SocialMediaAppServiceImpl.userIdIndex.get(userId)))
            throw new CustomConflictException(ErrorMessageConstantModel.USER_ALREADY_EXISTS);
        UserModel newUser = new UserModel();
        newUser.setUserId(userId);
        newUser.setUserName(userName);
        newUser.getFollowees().add(userId);
        synchronized (this) {
            SocialMediaAppServiceImpl.userIdIndex.put(userId, newUser);
        }
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    /**
     * getAllUsers method retrieves all the available Users in the collection. Initially it checks for the collection emptiness.
     * if the collection is empty then it throws exceptions which is then catch in CustomExceptionHandler.
     *
     * @return List of Users available in the collection.
     */
    @Override
    public ResponseEntity getAllUsers() {
        if (SocialMediaAppServiceImpl.userIdIndex.isEmpty()) {
            throw new CustomNotFoundException(ErrorMessageConstantModel.USER_DOES_NOT_EXISTS);
        }
        return new ResponseEntity<>(SocialMediaAppServiceImpl.userIdIndex, HttpStatus.OK);
    }
}
