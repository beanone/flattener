package org.beanone.flattener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<String> emptyList = new ArrayList<>();
	private Map<String, String> emptyMap = new HashMap<String, String>();

	public int[] getArrayOfInts() {
		return this.arrayOfInts;
	}

	public BigDecimal getBdVal() {
		return this.bdVal;
	}

	public BigInteger getBiVal() {
		return this.biVal;
	}

	public Boolean getBooleanValue() {
		return this.booleanValue;
	}

	public byte getByteVal() {
		return this.byteVal;
	}

	public Byte getByteValue() {
		return this.byteValue;
	}

	public char getCharVal() {
		return this.charVal;
	}

	public Character getCharValue() {
		return this.charValue;
	}

	public ColorEnum getColor() {
		return this.color;
	}

	public double getDoubleVal() {
		return this.doubleVal;
	}

	public Double getDoubleValue() {
		return this.doubleValue;
	}

	public List<String> getEmptyList() {
		return this.emptyList;
	}

	public Map<String, String> getEmptyMap() {
		return this.emptyMap;
	}

	public float getFloatVal() {
		return this.floatVal;
	}

	public Float getFloatValue() {
		return this.floatValue;
	}

	public int getIntVal() {
		return this.intVal;
	}

	public Integer getIntValue() {
		return this.intValue;
	}

	public long getLongVal() {
		return this.longVal;
	}

	public Long getLongValue() {
		return this.longValue;
	}

	public SimpleTestBean getSelfRef() {
		return this.selfRef;
	}

	public short getShortVal() {
		return this.shortVal;
	}

	public Short getShortValue() {
		return this.shortValue;
	}

	public java.sql.Date getSqlDateVal() {
		return this.sqlDateVal;
	}

	public String getStrVal() {
		return this.strVal;
	}

	public Time getTimeVal() {
		return this.timeVal;
	}

	public Timestamp getTsVal() {
		return this.tsVal;
	}

	public Date getUtilDateVal() {
		return this.utilDateVal;
	}

	public boolean isBooleanVal() {
		return this.booleanVal;
	}

	public void setArrayOfInts(int[] arrayOfInts) {
		this.arrayOfInts = arrayOfInts;
	}

	public void setBdVal(BigDecimal bdVal) {
		this.bdVal = bdVal;
	}

	public void setBiVal(BigInteger bi) {
		this.biVal = bi;
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

	public void setEmptyList(List<String> emptyList) {
		this.emptyList = emptyList;
	}

	public void setEmptyMap(Map<String, String> emptyMap) {
		this.emptyMap = emptyMap;
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
		this.sqlDateVal = sqlDateValue;
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
		this.utilDateVal = date;
	}
}
