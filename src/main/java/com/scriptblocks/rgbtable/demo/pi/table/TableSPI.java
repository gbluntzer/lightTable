package com.scriptblocks.rgbtable.demo.pi.table;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.io.spi.SpiMode;


import java.awt.*;
import java.io.IOException;

public class TableSPI extends Table {
	private final static String name = "LedTable_SPI";
	private static byte bAr[] = new byte[LedTable_Settings.ledY * LedTable_Settings.ledX * 3];
	
	private SpiDevice spi = null;
		
	public TableSPI() throws IOException {
		LOG.entering(TableSPI.name, "new");
		

			spi = SpiFactory.getInstance(SpiChannel.CS0, LedTable_Settings.spiSpeed, SpiMode.MODE_0);
		
		LOG.exiting(TableSPI.name, "new");
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
}
