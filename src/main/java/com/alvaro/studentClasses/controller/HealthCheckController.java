package com.alvaro.studentClasses.controller;

import com.alvaro.studentClasses.dto.GreetingDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a health check service
 */
@RestController
public class HealthCheckController {

    /**
     * Health check endpoint, echo a "Hi, I'm alive!" message
     * @return GreetingDTO
     */
    @GetMapping("/healthCheck")
    @ApiOperation(value = "Return a greeting in order to confirm that the service is alive",
                  response = GreetingDTO.class)
    public GreetingDTO health() {
        return new GreetingDTO("Hi, I'm alive!");
    }
}
