package com.scriptblocks.rgbtable.demo.controller;

import com.scriptblocks.rgbtable.demo.model.TableFrame;
import com.scriptblocks.rgbtable.demo.model.TablePixel;
import com.scriptblocks.rgbtable.demo.model.TableStatus;
import com.scriptblocks.rgbtable.demo.pi.table.Table;
import com.scriptblocks.rgbtable.demo.pi.table.TableSPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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


    @GetMapping("/tableStatus")
    @ResponseBody
    public TableStatus tableStatus(@RequestParam(name="name", required=false, defaultValue="solid") String name) {
        System.out.println("tableStatus Name = " + name);

            try {
                TableSPI table = TableSPI.getInstance();
                table.runPattern(name);

            } catch (IOException e) {
                e.printStackTrace();
                return new TableStatus(e.getLocalizedMessage());

            }
        
        return new TableStatus("Running " + name);
    }


}
