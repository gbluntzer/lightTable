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
        if(name == "random"){
            try {
                TableSPI table = new TableSPI();
                Random random = new Random();

                while (true){
                    byte r = (byte) (random.nextInt()*255);
                    byte g = (byte) (random.nextInt()*255);
                    byte b = (byte) (random.nextInt()*255);
                    byte bAr[] = table.getSolid(r,g,b);
                    table.write(bAr);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new TableStatus(e.getLocalizedMessage());

            }
        }else {
            try {
                TableSPI table = new TableSPI();
                byte bAr[] = table.getSolid((byte)255, (byte)0,(byte)0);
                table.write(bAr);
            } catch (IOException e) {
                e.printStackTrace();
                return new TableStatus(e.getLocalizedMessage());

            }
        }
        return new TableStatus("SUCCESS");
    }
}
