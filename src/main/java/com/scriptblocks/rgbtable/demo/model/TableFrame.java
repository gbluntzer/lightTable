package com.scriptblocks.rgbtable.demo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Scriptblocks on 3/2/2018.
 */
public class TableFrame {
    String var1;
    List<TablePixel> tablePixelList ;

    public TableFrame() {
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public List<TablePixel> getTablePixelList() {
        return tablePixelList;
    }

    public void setTablePixelList(List<TablePixel> tablePixelList) {
        this.tablePixelList = tablePixelList;
    }

    @Override
    public String toString() {
        return "TableFrame{" +
                "tablePixelList=" + tablePixelList.get(0) +
                '}';
    }
}
