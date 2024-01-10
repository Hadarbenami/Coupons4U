package com.Coupon4.U.models;

import com.Coupon4.U.clients.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
public class User {
    private String email;
    private String password;
    private ClientType clientType;

    public User(String email, String password, String clientType) {
        this.email = email;
        this.password = password;
        this.clientType = ClientType.valueOf(clientType);
    }
}
