package com.bt.creditappservices.container;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author msundara
 */
@Component
public class CustomHealthCheck implements HealthIndicator {

  @Override
  public Health health() {
    int errorCode = 1;
    if (errorCode != 1) {
      return Health.down().withDetail("Error Code", errorCode).build();
    }
    return Health.up().build();
  }
}