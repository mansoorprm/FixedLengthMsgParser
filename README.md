# FixedLengthMsgParser

# USAGE

download the github package to the local and type below command

mvn clean package

use the jar file  target\fxLngthParser-0.0.1-SNAPSHOT.jar to your project.

# Sample

# POJO

package com.virtusa.xlabs.fxLngthParser.test;


import java.math.BigDecimal;
import java.util.Date;

import com.virtusa.xlabs.fxLngthParser.Fixed;

public class PersonFlat {
    @Fixed(pos = 1, length = 1)
    private byte id;
    @Fixed(pos = 2, length = 5)
    private String name;
    @Fixed(pos = 3, length = 10)
    private String surname;
    @Fixed(pos = 4, length = 1)
    private char gender;
    @Fixed(pos = 5, length = 4)
    private boolean positiveBalance;
    @Fixed(pos = 6, length = 4)
    private BigDecimal year;
    @Fixed(pos = 7, length = 10)
    private Date date;

    @Override
    public String toString() {
        return "PersonFlat{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", gender=" + gender + ", positiveBalance=" + positiveBalance + ", year=" + year + ", date=" + date + '}';
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

	public boolean isPositiveBalance() {
		return positiveBalance;
	}

	public void setPositiveBalance(boolean positiveBalance) {
		this.positiveBalance = positiveBalance;
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

# Implementation

package com.virtusa.xlabs.fxLngthParser.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.virtusa.xlabs.fxLngthParser.StringReader;


/**
 * @author Mansoor Parambath
 * @XLABS - Virtusa Corporation
 *
 */


public class App
{
	public static void main( String[] args )
    {
    	try {
			testReadAString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void testReadAString() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {

    	//replace your text with your message
    	String text = "1MansoorParambathMtrue20192018-10-18";

    	StringReader<PersonFlat> instance = new StringReader(PersonFlat.class, text);
        List<PersonFlat> result = instance.readString();
        System.out.println(result);
        System.out.println(result.get(0).getName());


    }
}
