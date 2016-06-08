package org.beanone.flattener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class SimpleTestBean {
	private int intVal = 1;
	private long longVal = 1L;
	private double doubleVal = 1.0;
	private float floatVal = 1.0F;
	private short shortVal = 1;
	private byte byteVal = 1;
	private boolean booleanVal = true;
	private char charVal = 'a';
	private Integer intValue = 1;
	private Long longValue = 1L;
	private Double doubleValue = 1.0;
	private Float floatValue = 1.0F;
	private Short shortValue = 1;
	private Byte byteValue = 1;
	private Boolean booleanValue = true;
	private Character charValue = 'A';
	private String strVal = "abc";
	private SimpleTestBean selfRef = this;
	private int[] arrayOfInts;
	private ColorEnum color = ColorEnum.RED;
	private Date utilDateVal = Calendar.getInstance().getTime();
	private java.sql.Date sqlDateVal = new java.sql.Date(
	        Calendar.getInstance().getTime().getTime());
	private Time timeVal = new Time(367890);
	private Timestamp tsVal = new Timestamp(367890);
	private BigInteger biVal = BigInteger.valueOf(Long.MAX_VALUE)
	        .nextProbablePrime();
	private BigDecimal bdVal = BigDecimal.valueOf(Double.MAX_VALUE).pow(2);

	public int[] getArrayOfInts() {
		return arrayOfInts;
	}

	public BigDecimal getBdVal() {
		return bdVal;
	}

	public BigInteger getBiVal() {
		return biVal;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public byte getByteVal() {
		return byteVal;
	}

	public Byte getByteValue() {
		return byteValue;
	}

	public char getCharVal() {
		return charVal;
	}

	public Character getCharValue() {
		return charValue;
	}

	public ColorEnum getColor() {
		return color;
	}

	public double getDoubleVal() {
		return doubleVal;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public float getFloatVal() {
		return floatVal;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public int getIntVal() {
		return intVal;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public long getLongVal() {
		return longVal;
	}

	public Long getLongValue() {
		return longValue;
	}

	public SimpleTestBean getSelfRef() {
		return selfRef;
	}

	public short getShortVal() {
		return shortVal;
	}

	public Short getShortValue() {
		return shortValue;
	}

	public java.sql.Date getSqlDateVal() {
		return sqlDateVal;
	}

	public String getStrVal() {
		return strVal;
	}

	public Time getTimeVal() {
		return timeVal;
	}

	public Timestamp getTsVal() {
		return tsVal;
	}

	public Date getUtilDateVal() {
		return utilDateVal;
	}

	public boolean isBooleanVal() {
		return booleanVal;
	}

	public void setArrayOfInts(int[] arrayOfInts) {
		this.arrayOfInts = arrayOfInts;
	}

	public void setBdVal(BigDecimal bdVal) {
		this.bdVal = bdVal;
	}

	public void setBiVal(BigInteger bi) {
		biVal = bi;
	}

	public void setBooleanVal(boolean booleanVal) {
		this.booleanVal = booleanVal;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void setByteVal(byte byteVal) {
		this.byteVal = byteVal;
	}

	public void setByteValue(Byte byteValue) {
		this.byteValue = byteValue;
	}

	public void setCharVal(char charVal) {
		this.charVal = charVal;
	}

	public void setCharValue(Character charValue) {
		this.charValue = charValue;
	}

	public void setColor(ColorEnum color) {
		this.color = color;
	}

	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public void setFloatVal(float floatVal) {
		this.floatVal = floatVal;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public void setSelfRef(SimpleTestBean selfRef) {
		this.selfRef = selfRef;
	}

	public void setShortVal(short shortVal) {
		this.shortVal = shortVal;
	}

	public void setShortValue(Short shortValue) {
		this.shortValue = shortValue;
	}

	public void setSqlDateVal(java.sql.Date sqlDateValue) {
		sqlDateVal = sqlDateValue;
	}

	public void setStrVal(String strVal) {
		this.strVal = strVal;
	}

	public void setTimeVal(Time timeVal) {
		this.timeVal = timeVal;
	}

	public void setTsVal(Timestamp tsVal) {
		this.tsVal = tsVal;
	}

	public void setUtilDateVal(Date date) {
		utilDateVal = date;
	}
}
