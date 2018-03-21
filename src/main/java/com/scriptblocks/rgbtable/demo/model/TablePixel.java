package com.scriptblocks.rgbtable.demo.model;

/**
 * Created by Scriptblocks on 3/2/2018.
 */
public class TablePixel {

    Integer alpha;
    Integer red;
    Integer green;
    Integer blue;

    public TablePixel() {
    }


    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "TablePixel{" +
                "alpha=" + alpha +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
