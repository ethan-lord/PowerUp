package org.usfirst.frc.team3042.lib;

import edu.wpi.first.wpilibj.I2C;

public class I2CRangeSensor {

	public static final int DEFAULT_I2C_ADDR = 0x14;
	public static final int ULTRASONIC_ADDR = 0x04;
	public static final int OPTICAL_ADDR = 0x05;
	
	I2C i2c;
	
	public I2CRangeSensor(I2C.Port port) {
		this(port, DEFAULT_I2C_ADDR);
	}
	
	public I2CRangeSensor(I2C.Port port, int address) {
		i2c = new I2C(port, address);
	}
	
	public Byte readUltrasonic() {
		byte[] data = new byte[1];
		
		//Read 1 byte from the I2C bus and return it
		i2c.read(ULTRASONIC_ADDR, 1, data);
		
		return data[0];
	}
	
	public Byte readOptical() {
		byte[] data = new byte[1];
		
		//Read 1 byte from the I2C bus and return it
		i2c.read(OPTICAL_ADDR, 1, data);
		
		return data[0];
	}
	
	
}
