package com.scriptblocks.rgbtable.demo.pi.table;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.io.spi.SpiMode;
import com.scriptblocks.rgbtable.demo.DemoApplication;
import com.scriptblocks.rgbtable.demo.model.TableFrame;
import com.scriptblocks.rgbtable.demo.model.TablePixel;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
        } else if (pattern != null && pattern.equals("image")) {
            runImage();
        } else {
            runBlack();
        }
    }

    public void runRaw(TableFrame tableFrame) {
        if (tableFrame != null && tableFrame.getTablePixelList() != null && tableFrame.getTablePixelList().size() > 0) {
            TablePixel tablePixel = tableFrame.getTablePixelList().get(0);

            byte bAr[] = getSolid(tablePixel.getRed().byteValue(), tablePixel.getGreen().byteValue(), tablePixel.getBlue().byteValue());
            this.write(bAr);
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

    private void runImage() {
        byte bAr[] = getRGBArrayFromImage("10bit.png");
        this.write(bAr);
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


    private static List<TablePixel> pixelList = new LinkedList<>();

    public static byte[] getRGBArrayX(int r, int g, int b) {
        return getRGBArrayX((byte) r, (byte) g, (byte) b);
    }

    public static TablePixel getPixelARGB(int pixel) {
        TablePixel result = new TablePixel();
        Integer alpha = (pixel >> 24) & 0xff;
        Integer red = (pixel >> 16) & 0xff;
        Integer green = (pixel >> 8) & 0xff;
        Integer blue = (pixel) & 0xff;
        result.setAlpha(alpha);
        result.setRed(red);
        result.setGreen(green);
        result.setBlue(blue);

        return result;
    }


    public static byte[] getRGBArrayFromImage(String imageName) {
        int p = 0;
        pixelList = new LinkedList<>();
        while (p < bAr.length) {
            bAr[p++] = (byte) 0;
            bAr[p++] = (byte) 0;
            bAr[p++] = (byte) 0;
        }


        try {
            // get the BufferedImage, using the ImageIO class
            BufferedImage image =
                    ImageIO.read(DemoApplication.class.getResource(imageName));


            int w = image.getWidth();
            int h = image.getHeight();
            // System.out.println("width, height: " + w + ", " + h);

            int z = 0;
            for (int j = 0; j < w; j++) {
                for (int i = 0; i < h; i++) {

                    if (z >= bAr.length) {
                        System.out.println("exceeded maximum");
                        break;
                    }
                    int pixelInt = image.getRGB(j, i);
                    TablePixel pixel = getPixelARGB(pixelInt);
                    pixelList.add(pixel);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


        snakePixel();

        return bAr;
    }


    /*
Take the Pixel List and reorder it so that all odd colums are flipped then fill in the bAr array
 */
    public static void snakePixel() {

        boolean flip = false;
        int rowCount = 12;
        for (int column = 0; column < 8; column++) {
            if (column % 2 > 0) { //if odd column
                int columnLeftCounter = 0;
                int columnRightCounter = 11;
                for (int h = 0; h < rowCount / 2; h++) { //only need to process half
                    int offset = column * 12;
                    int tempOne = offset + columnLeftCounter;
                    int tempTwo = offset + columnRightCounter;
                    TablePixel pixelTemp = pixelList.get(tempOne);
                    pixelList.set(tempOne, pixelList.get(tempTwo));
                    pixelList.set(tempTwo, pixelTemp);
                    columnLeftCounter++;
                    columnRightCounter--;
                }
            }
        }
        int z = 0;
        int pixelListCounter = 0;
        for (TablePixel pixel : pixelList) {
            bAr[z++] = pixel.getRed().byteValue();
            bAr[z++] = pixel.getGreen().byteValue();
            bAr[z++] = pixel.getBlue().byteValue();
        }


    }

}
