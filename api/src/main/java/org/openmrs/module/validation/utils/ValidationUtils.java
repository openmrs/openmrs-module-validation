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

import com.google.common.collect.ListMultimap;
import org.apache.commons.lang.StringUtils;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationErrorEntry;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ValidationUtils {

    /**
     * Gets the list of registered Validator classes in the system
     * @return  TreeMap of Validator classes
     * @throws Exception
     */
    public static Map<String, String> getClassNamesToValidate() throws Exception {
        Map<String,String> classNamesMap = new TreeMap<String,String>();
        List<Validator> validators =  Context.getRegisteredComponents(Validator.class);
        for (Validator validator: validators){
            Handler annotation = validator.getClass().getAnnotation(Handler.class);
            if (annotation != null){
                Class[] classNames = annotation.supports();
                for(Class classname: classNames){
                   classNamesMap.put(beautify(classname.getName()),classname.getName());
                }
            }
        }
        return classNamesMap;
    }

    public static String[] getListOfObjectsToValidate(String type) {
        if(StringUtils.isNotBlank(type)){
          return type.trim().split(",");
        } else {
            return new String[0];
        }

    }

    /**
     * gets the full class name of an object type and make its simple class name
     * @param section - full class name string
     * @return - simple class name
     */
    public static String beautify(String section) {

        section = section.substring(12);
        section = section.replace("_", " ");
        section = section.replace(".", " ");

        String[] sections = StringUtils.splitByCharacterTypeCamelCase(section);
        section = StringUtils.join(sections, " ");

        sections = StringUtils.split(section);
        for (int i = 0; i < sections.length; i++) {
            sections[i] = StringUtils.capitalize(sections[i]);
        }
        section = StringUtils.join(sections, " ");

        return section;
    }

    public static List<ValidationErrorEntry> prepareReportByClass(ListMultimap<String, Map<Object, Exception>> errorTypeValueMap) {
        List<ValidationErrorEntry> errorEntries = new ArrayList<ValidationErrorEntry>();
        for(String className: errorTypeValueMap.keySet()){
            Map<Object, Exception> allErrorsOfSingleClass = new ConcurrentHashMap<Object, Exception>();
            for(Map<Object, Exception> entryMap : errorTypeValueMap.get(className)){
                allErrorsOfSingleClass.putAll(entryMap);
            }
            ValidationErrorEntry entry = new ValidationErrorEntry();
            entry.setClassname(className);
            entry.setErrors(allErrorsOfSingleClass);
            errorEntries.add(entry);
        }

        return errorEntries;
    }
}
