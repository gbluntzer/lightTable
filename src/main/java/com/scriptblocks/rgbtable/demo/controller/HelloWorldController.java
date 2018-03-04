package com.scriptblocks.rgbtable.demo.controller;

import com.scriptblocks.rgbtable.demo.model.Greeting;
import com.scriptblocks.rgbtable.demo.model.TableFrame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/hello-world")
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Greeting> update(@RequestBody Greeting greeting) {

        if (greeting != null) {
            // car.setMiles(car.getMiles() + 100);
        }

        // TODO: call persistence layer to update
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

}
