package com.virtusa.xlabs.fxLngthParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author Mansoor Parambath
 * @XLABS - Virtusa Corporation
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Fixed {
    /**
     * 
     * @return position of the field
     */
    int pos();
    /**
     * 
     * @return length of the field
     */
    int length();
}
