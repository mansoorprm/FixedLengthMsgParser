package com.virtusa.xlabs.fxLngthParser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mansoor Parambath
 * @XLABS - Virtusa Corporation
 * 
 */
public class StringReader<T> {
	
	
	
	HashMap<Integer,Integer> offsetMap = new HashMap<Integer,Integer>();
 
   
    private final Class<T> aClass;
    /**
     * String to be read
     */
    private final String text;
 
    /**
     * 
     * @param aClass a class that understands the type of format the file has. It has annotations to 
     * understand the format
     * @param text string to be read. 
     */
    public StringReader(Class<T> aClass, String text) {
        this.aClass = aClass;
        this.text = text;
    }
 
    /**
     * 
     * Read String text
     * 
     * @param lines list of lines from the String
     * @return list of lines in a form of type T
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException 
     */
    private List<T> readString(String line) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("No value to be read");
        }
        List<T> list = new ArrayList();
       
            Field[] fields = aClass.getDeclaredFields();
            T newInstance = aClass.getConstructor().newInstance();
            
            HashMap<Integer,Integer> fieldMap = new HashMap<Integer,Integer>();
            
            int offsetpos = 1;
            int length = 1;
            
            for (Field field : fields) {
            	field.setAccessible(true);
            	offsetpos = field.getAnnotation(Fixed.class).pos();
            	length = field.getAnnotation(Fixed.class).length();
            	fieldMap.put(offsetpos, length);
            }
            
            
            int pos = 1;
            int offsetStart =0;
            for(Map.Entry<Integer, Integer> entry:fieldMap.entrySet()){  
            	offsetMap.put(pos, offsetStart);
            	offsetStart = offsetStart + entry.getValue() - 1;
            	pos++;
                offsetStart = offsetStart +1;
             }    
            
            
            for (Field field : fields) {
            	  processFixedString(field, line, newInstance);
             }
            list.add(newInstance);
        
        if (list.isEmpty()) {
            throw new IllegalArgumentException("could not read the given text");
        }
        
        return list;
    }
 
    /**
     * 
     * This method only understands fixed file format based on annotation
     * 
     * @param field field to be modified so that it has correct data
     * @param line text line read from the file
     * @param newInstance type to cast/convert results to
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SecurityException 
     */
    private void processFixedString(Field field, String line, T newInstance) throws IllegalArgumentException, IllegalAccessException, SecurityException {
        field.setAccessible(true);
        Fixed fixed = field.getAnnotation(Fixed.class);
        int pos = fixed.pos();
        int length = fixed.length();
        int offsetFrom = offsetMap.get(pos);
        int offsetTo = offsetFrom + length ;
    
        Class<?> type = field.getType();
        String value = line.substring(offsetFrom, offsetTo).trim();
        setData(type, value, field, newInstance);
    }
 
    /**
     * 
     * Converts each line to a List<T> where <T> can be any type passed to constructor
     * 
     * @return List<T> from file.
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IOException 
     */
    public List<T> readString() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
        return readString(text);
    }
 
    /**
     * Sets the value of each field based on annotation. 
     * @param type return type of a field
     * @param value value to pass to the field
     * @param field to set the value for
     * @param newInstance instance has the annotation to understand the file.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException 
     */
    private void setData(Class<?> type, String value, Field field, T newInstance) throws IllegalAccessException, IllegalArgumentException {
        if (Number.class.isAssignableFrom(type)) {
            setNumericValue(value, type, field, newInstance);
        } else if (type.isPrimitive() && !type.isAssignableFrom(Character.TYPE) && !type.isAssignableFrom(Boolean.TYPE)) {
            setNumericValue(value, type, field, newInstance);
        } else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(Character.TYPE)) {
            field.set(newInstance, value.charAt(0));
        } else if (type.isAssignableFrom(Boolean.class) || type.equals(Boolean.TYPE)) {
            field.set(newInstance, Boolean.parseBoolean(value.trim()));
        } else if (type.isAssignableFrom(BigDecimal.class) || type.isInstance(BigDecimal.class)) {
            BigDecimal num = new BigDecimal(value);
            field.set(newInstance, num);
        } else if (type.isAssignableFrom(Date.class)) {
            if(value.length() < 10) {
                return;
            }
            try {
                String dateSeparater = value.contains("-") ? "-" : "/";
                String year = value.substring(0, 4);
                String month = value.substring(5, 7);
                String day = value.substring(8, 10);
                value = year + dateSeparater + month + dateSeparater + day;
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                field.set(newInstance, date);
            } catch (ParseException ex) {
                Logger.getLogger(StringReader.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        } else if (type.isAssignableFrom(String.class)) {
            field.set(newInstance, value);
        }
    }
 
    /**
     * 
     * Sets the value of each field based on annotation. This is only for numeric values
     * @param type return type of a field
     * @param value value to pass to the field
     * @param field to set the value for
     * @throws IllegalAccessException
     * @throws IllegalArgumentException 
     */
    private void setNumericValue(String value, Class<?> type, Field field, T newInstance) throws IllegalAccessException, IllegalArgumentException {
        if (value == null) {
            value = "0";
        } else if (value.startsWith("0")) {
            value = value.substring(value.indexOf("0"));
        }
        if (!Character.isDigit(value.charAt(0))) {
            return;
        }
        BigDecimal num = new BigDecimal(value);
         
        if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(Byte.TYPE)) {
            field.set(newInstance, num.byteValue());
        } else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(Short.TYPE)) {
            field.set(newInstance, num.shortValue());
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Integer.TYPE)) {
            field.set(newInstance, num.intValue());
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(Long.TYPE)) {
            field.set(newInstance, num.longValue());
        } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(Float.TYPE)) {
            field.set(newInstance, num.floatValue());
        } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(Double.TYPE)) {
            field.set(newInstance, num.longValue());
        } else {
            field.set(newInstance, num);
        }
    }
}