/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.validation.utils;

import org.apache.commons.lang.StringUtils;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationObject;
import org.openmrs.module.validation.ValidationObjectTuple;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationUtils {

    /**
     * Gets the list of registered Validator classes in the system
     * @return  List of Validator classes grouped as tuples
     * @throws Exception
     */
    public static List<ValidationObjectTuple> getClassNamesToValidate() throws Exception {
        Set<ValidationObject> classSet = new HashSet<ValidationObject>();
        List<Validator> validators =  Context.getRegisteredComponents(Validator.class);
        for (Validator validator: validators){
            Handler annotation = validator.getClass().getAnnotation(Handler.class);
            if (annotation != null){
                Class[] classNames = annotation.supports();
                for(Class classname: classNames){
                    ValidationObject validationObject = new ValidationObject(classname.getName(),beautify(classname.getName()));
                    classSet.add(validationObject) ;
                }
            }

        }
        return groupObjectsIntoTuples(classSet);
    }

    /**
     * Gets the Validator classes and prepares tuples out of them
     * @param classSet - List of ValidationObjectTuples
     * @return
     */
    private static List<ValidationObjectTuple> groupObjectsIntoTuples(Set<ValidationObject> classSet) {
        List<ValidationObjectTuple> objectTuples = new ArrayList<ValidationObjectTuple>();
        int tuplesCount = classSet.size()/3;
        int remain = classSet.size()%3;
        Object[]  classArray = classSet.toArray();
        int index = 0;
        for(int i=0; i< tuplesCount; i ++){
            ValidationObjectTuple tuple = new ValidationObjectTuple();
            tuple.setFirst((ValidationObject) classArray[index]);
            tuple.setSecond((ValidationObject) classArray[index+1]);
            tuple.setThird((ValidationObject) classArray[index+2]);
            objectTuples.add(tuple);
            index = index+3;

        }

        // as we create tuples there can be only 1 or 2 objects remained after grouping above. we add the remaining objects
        // into another ValidationObjectTuple
        if(remain != 0){
            ValidationObjectTuple remaintuple = new ValidationObjectTuple();
            if (remain == 2) {
                remaintuple.setFirst((ValidationObject) classArray[index]);
                remaintuple.setSecond((ValidationObject) classArray[index + 1]);
                remaintuple.setThird(null);
            } else {
                remaintuple.setFirst((ValidationObject) classArray[index]);
                remaintuple.setSecond(null);
                remaintuple.setThird(null);
            }
            objectTuples.add(remaintuple);
        }
        return objectTuples;
    }

    public static String[] getListOfObjectsToValidate(String type) {
        if(type != null && !StringUtils.isBlank(type)){
          return type.trim().split(",");
        } else {
            return new String[0];
        }

    }

    /**
     * gets the full class name of an object type and make its simple class name
     *
     * @param section - full class name string
     * @return - simple class name
     */
    public static String beautify(String section) {
        section = section.replace(".", " ");

        String[] sections = StringUtils.split(section);
        String simpleClassName = "";
        // here we remove 'org.openmrs' section from the type and gets the short data type only
        for (int i = 0; i < sections.length; i++) {
            if(!sections[i].trim().equalsIgnoreCase("org") || !sections[i].trim().equalsIgnoreCase("openmrs") ) {
               simpleClassName = sections[i];
            }
        }
        return simpleClassName;
    }

}
