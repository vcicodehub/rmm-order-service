package com.signet.controller;

import com.signet.config.RabbitMQConfig;
import com.signet.exceptions.SignetServiceException;
import com.signet.model.user.User;
import com.signet.service.UserService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/signet/api/v1/om")
public class AppController implements ApplicationContextAware {
  private ApplicationContext applicationContext = null; 

  RabbitTemplate rabbitTemplate;
  UserService userService;

  public AppController(RabbitTemplate rabbitTemplate, UserService userService) {
    this.rabbitTemplate = rabbitTemplate;
    this.userService = userService;
  }

  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getCurrentHealth() throws SignetServiceException {
    return "Hello";
  }

  @GetMapping(value = "/user/current", produces = MediaType.APPLICATION_JSON_VALUE)
  public User getCurrentUser() throws SignetServiceException {
    return this.userService.retrieveUserByID("admin");
  }

  @PostMapping(value = "/q/job", produces = MediaType.APPLICATION_JSON_VALUE)
  public String postMessageToQueueWithExchange() {
    rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "foo.bar.job", "[topic-exchange] Hello from RabbitMQ!");
    return null;
  }

  @PostMapping(value = "/q/fanout", produces = MediaType.APPLICATION_JSON_VALUE)
  public String postMessageToQueueWithFanoutExchange() {
    rabbitTemplate.convertAndSend(RabbitMQConfig.fanoutExchangeName, "", "[fanout-exchange] Hello from RabbitMQ!");
    return null;
  }

  @PostMapping(value = "/cache/send", produces = MediaType.APPLICATION_JSON_VALUE)
  public void sendToCache() {
		StringRedisTemplate template = applicationContext.getBean(StringRedisTemplate.class);

    log.info("Sending message...");
    template.convertAndSend("chat", "Hello from Redis!");
  }

}
