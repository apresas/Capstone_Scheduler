package com.example.coursescheduler.UI;

import static org.junit.Assert.*;

import android.graphics.Paint;

import com.example.coursescheduler.Entity.User;

import org.junit.Test;

public class LoginTest {
    private User user;
    private String username = "username";
    private String password = "password";
    private Exception exception;

    User u = new User(username, password);
    @Test
    public void should_return_user() {
        assertNotNull(u);
    }

    @Test
    public void should_return_correct_username_and_password() {
        assertEquals("username", u.getUsername());
        assertEquals("password", u.getPassword());
    }

    @Test
    public void should_not_throw_exception() {
        assertNull(exception);
    }
}