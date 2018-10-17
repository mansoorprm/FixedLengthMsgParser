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
			testReadAFile();
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
    
    public static void testReadAFile() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
       // String text = "1mansoorparambathMabcdpgkhgfjfhjkjdhjkjfskfjhaskfjsakjfdshfkjsdkfjghjkdsfsfkjjsdfhasjfgdhfgsdhjfgsdhfgsdhjfgsdhfgsdhfgsdhfgsdhfgdshfgshd";
        String text = "1abcde1234567890Mtrue20192018-10-18";
//1abcde1234567890M123452019ABCDe
    	StringReader<PersonFlat> instance = new StringReader(PersonFlat.class, text);
        List<PersonFlat> result = instance.readString();
        System.out.println(result);
        System.out.println(result.get(0).getName());

        
    }
}
