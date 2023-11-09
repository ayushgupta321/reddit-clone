package com.ayush.reddit.clone.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestPojo {
    private String username;
    private String email;
    private String password;
}