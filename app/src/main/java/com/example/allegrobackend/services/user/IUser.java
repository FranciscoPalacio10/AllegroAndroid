package com.example.allegrobackend.services.user;

import com.example.allegrobackend.models.InitActitvy;
import com.example.allegrobackend.models.user.User;
import com.example.allegrobackend.models.user.UserRequest;
import com.example.allegrobackend.models.user.get.UserResponse;
import com.example.allegrobackend.services.IAbm;

public interface IUser extends IAbm<UserResponse,String, UserRequest> {
    void add(UserResponse entity, InitActitvy initActitvy);

}
