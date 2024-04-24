package com.example.ec.dto;

import org.junit.jupiter.api.Test;

import com.example.ec.dto.LoginDto;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class LoginDtoTest {

    @Test
    public void testAll() {
        LoginDto dto = new LoginDto("user","pwd");
        assertThat(dto.getUsername(), is("user"));
        assertThat(dto.getPassword(), is("pwd"));
    }
}