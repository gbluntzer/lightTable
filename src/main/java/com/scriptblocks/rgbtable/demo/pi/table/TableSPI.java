package com.scriptblocks.rgbtable.demo.pi.table;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.io.spi.SpiMode;


import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class TableSPI extends Table {
    private final static String name = "LedTable_SPI";
    private static byte bAr[] = new byte[LedTable_Settings.ledY * LedTable_Settings.ledX * 3];

    private static TableSPI instance;
    private static String pattern = null;

    private static int changeSpeed = 1000;

    private SpiDevice spi = null;

    public TableSPI() throws IOException {
        LOG.entering(TableSPI.name, "new");


        spi = SpiFactory.getInstance(SpiChannel.CS0, LedTable_Settings.spiSpeed, SpiMode.MODE_0);

        LOG.exiting(TableSPI.name, "new");
    }

    public static TableSPI getInstance() throws IOException {
        if (instance == null) {
            instance = new TableSPI();
        }
        return instance;
    }

    public synchronized void write(byte[] bAr) {
        LOG.entering(TableSPI.name, "write", bAr);
        //LOG.finest(LedTable_Util.bArToString(bAr));
        //long t = System.currentTimeMillis();
        super.write(bAr);


        try {
            spi.write(bAr);
            Thread.sleep(10);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        //LOG.finest("Write Time: " + (System.currentTimeMillis() - t));
        LOG.exiting(TableSPI.name, "write", bAr.length + " bytes written");
    }

    public byte[] getSolid(byte r, byte g, byte b) {
        LOG.entering(name, "solid");

        int i = 0;
        while (i < bAr.length) {
            bAr[i++] = r;
            bAr[i++] = g;
            bAr[i++] = b;
        }


        LOG.exiting(name, "solid");
        return bAr;
    }

    public byte[] getRandom() {
        LOG.entering(name, "random");

        int i = 0;
        while (i < bAr.length) {

            byte r = (byte) getRandomNumberInRange(0, 255);
            byte g = (byte) getRandomNumberInRange(0, 255);
            byte b = (byte) getRandomNumberInRange(0, 255);
            bAr[i++] = r;
            bAr[i++] = g;
            bAr[i++] = b;
        }


        LOG.exiting(name, "random");
        return bAr;
    }

    public byte[] getRandomRGB() {
        LOG.entering(name, "random");

        int i = 0;
        while (i < bAr.length) {
            byte r = (byte) 0;
            byte g = (byte) 0;
            byte b = (byte) 0;
            switch (getRandomNumberInRange(0, 3)) {
                case 0: {
                    r = (byte) 255;
                    break;
                }
                case 1: {
                    g = (byte) 255;
                    break;
                }
                default: {
                    b = (byte) 255;
                    break;
                }
            }
            bAr[i++] = r;
            bAr[i++] = g;
            bAr[i++] = b;
        }


        LOG.exiting(name, "random");
        return bAr;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void runPattern(String name) {
        pattern = name;
        if (pattern != null && pattern.equals("random")) {
            runRandom();
        } else if (pattern != null && pattern.equals("rgb")) {
            runRandomRGB();
        } else if (pattern != null && pattern.equals("solid")) {
            runSolid();
        } else {
            runBlack();
        }
    }

    private void runRandom() {
        while (pattern.equals("random")) {
            byte bAr[] = getRandom();
            this.write(bAr);
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(changeSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runRandomRGB() {
        while (pattern.equals("rgb")) {
            byte bAr[] = getRandomRGB();
            this.write(bAr);
            sleep();
        }
    }

    private void runSolid() {
        byte bAr[] = getSolid((byte) 255, (byte) 0, (byte) 0);
        write(bAr);
    }

    private void runBlack() {
        byte bAr[] = getSolid((byte) 0, (byte) 0, (byte) 0);
        write(bAr);
    }

}
