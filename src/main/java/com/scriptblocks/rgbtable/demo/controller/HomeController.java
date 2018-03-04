package com.scriptblocks.rgbtable.demo.controller;

import com.scriptblocks.rgbtable.demo.model.TableFrame;
import com.scriptblocks.rgbtable.demo.model.TablePixel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Scriptblocks on 3/2/2018.
 */

@RestController
public class HomeController {

    @RequestMapping(value = "/ledTable", method = RequestMethod.POST)
    public ResponseEntity<List<TablePixel>> update(@RequestBody List<TablePixel> tablePixels) {

        if (tablePixels != null) {
           // car.setMiles(car.getMiles() + 100);
        }

        // TODO: call persistence layer to update
        return new ResponseEntity<List<TablePixel>>(tablePixels, HttpStatus.OK);
    }
}
