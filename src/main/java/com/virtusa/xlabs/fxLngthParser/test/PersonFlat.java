package com.virtusa.xlabs.fxLngthParser.test;


import java.math.BigDecimal;
import java.util.Date;

import com.virtusa.xlabs.fxLngthParser.Fixed;

public class PersonFlat {
    @Fixed(pos = 1, length = 1)
    private byte id;
    @Fixed(pos = 2, length = 7)
    private String name;
    @Fixed(pos = 3, length = 9)
    private String surname;
    @Fixed(pos = 4, length = 1)
    private char gender;
    @Fixed(pos = 5, length = 4)
    private boolean positive;
    @Fixed(pos = 6, length = 4)
    private BigDecimal year;
    @Fixed(pos = 7, length = 10)
    private Date date;
 
    @Override
    public String toString() {
        return "PersonFlat{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", gender=" + gender + ", positive=" + positive + ", year=" + year + ", date=" + date + '}';
    }

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	

	public BigDecimal getYear() {
		return year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
}