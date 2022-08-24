package com.blackdiz.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import utils.StringUtils;

import java.util.function.Function;

@SpringBootApplication
public class HelloWorld1 {

  @Value("${testText}")
  private String testText;

  /*
   * You need this main method (empty) or explicit <start-class>example.FunctionConfiguration</start-class>
   * in the POM to ensure boot plug-in makes the correct entry
   */
  public static void main(String[] args) {
    // empty unless using Custom runtime at which point it should include
    //		 SpringApplication.run(FunctionConfiguration.class, args);
  }

  @Bean
  public Function<String, String> processStatus() {
    return event -> {
      StringUtils.test();
      System.out.println(testText);
      return event.toUpperCase();
    };
  }
}
