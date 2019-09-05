package com.socialmediaapp.serviceimpl;

import com.socialmediaapp.exception.CustomConflictException;
import com.socialmediaapp.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Junit/Mockito test cases to unit test the API all the methods and business logics.
 *
 * @author atulzambre
 */
@RunWith(SpringRunner.class)
public class UserOperationsServiceTest {
    @InjectMocks
    private UserOperationsServiceImpl userOperationsService;

    @Test
    public void createUsersTest() {
        ResponseEntity entityUser = userOperationsService.createUser("3", "test 3");
        UserModel user = (UserModel) entityUser.getBody();
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());

    }

    @Test(expected = CustomConflictException.class)
    public void createUsersAlreadyExistsTest() {
        userOperationsService.createUser("2", "test 2");
        ResponseEntity entityUser = userOperationsService.createUser("2", "test 2");
        UserModel user = (UserModel) entityUser.getBody();
        assertEquals(HttpStatus.CONFLICT.value(), entityUser.getStatusCodeValue());

    }

    @Test
    public void getAllUsersTest() {
        ResponseEntity entityUser = userOperationsService.getAllUsers();
        assertEquals(HttpStatus.OK.value(), entityUser.getStatusCodeValue());

    }
}
