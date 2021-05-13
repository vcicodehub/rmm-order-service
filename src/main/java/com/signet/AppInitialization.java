package com.signet;

import java.util.Arrays;

import com.signet.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppInitialization implements CommandLineRunner {

  UserService userService;

  AppInitialization(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Hello... initializing service with: " + Arrays.toString(args));
  }
    
}
