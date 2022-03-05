package com.player.service;

import com.player.dto.LoginDTO;
import com.player.dto.RegisterDTO;
import com.player.model.AccessToken;
import com.player.model.Player;

public interface AuthRegister {
    Player registerUser(final RegisterDTO registerData);

    AccessToken authenticateUser(final LoginDTO loginDto);
}
