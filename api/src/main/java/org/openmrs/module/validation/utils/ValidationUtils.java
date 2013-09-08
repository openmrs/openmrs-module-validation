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

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.OpenmrsMetadata;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationErrorEntryByClass;
import org.openmrs.module.validation.ValidationErrorEntryByError;
import org.springframework.validation.Validator;

import java.util.*;

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

    public static List<ValidationErrorEntryByClass> prepareReportByClass(MultiMap errorTypeValueMap) {
        List<ValidationErrorEntryByClass> errorEntries = new ArrayList<ValidationErrorEntryByClass>();
        for(Object className: errorTypeValueMap.keySet()){
            Map<String, Exception> allErrorsOfSingleClass = new HashMap<String, Exception>();
            List<Map<Object, Exception>> entries = (List<Map<Object, Exception>>) errorTypeValueMap.get(className);

            for(Map<Object, Exception> entryMap : entries){
                for(Object obj :entryMap.keySet() ){
//                    Here we get the Id and UUID of the object and display it as the key
                    BaseOpenmrsObject openmrsObject = (BaseOpenmrsObject) obj;
                    String errorIdUuid = openmrsObject.getId() + ": " + openmrsObject.getUuid();
                    allErrorsOfSingleClass.put(errorIdUuid,entryMap.get(obj));
                }

            }

            ValidationErrorEntryByClass entryByClass = new ValidationErrorEntryByClass();
            entryByClass.setClassname((String) className);
            entryByClass.setErrors(allErrorsOfSingleClass);
            errorEntries.add(entryByClass);
        }

        return errorEntries;
    }

    public static ValidationErrorEntryByError prepareEntryByError(Exception exception, String type) {
        ValidationErrorEntryByError entry = new ValidationErrorEntryByError();
        MultiMap entryVal = new MultiValueMap();
           entryVal.put(ValidationUtils.beautify(type),exception.getMessage());
            entry.setErrorname(exception.getClass().getName());
            entry.setErrorsDetail(entryVal);

        return entry ;
    }

    public static List<ValidationErrorEntryByError> prepareReportByError(MultiMap errorWithTypeMap) {
        List<ValidationErrorEntryByError> errorEntries = new ArrayList<ValidationErrorEntryByError>();
        for(Object errorName: errorWithTypeMap.keySet()){
            MultiMap allInfoOfSingleError = new MultiValueMap();
            List<MultiMap> entries = (List<MultiMap>) errorWithTypeMap.get(errorName);
            for (MultiMap entryMap: entries){
                allInfoOfSingleError.putAll(entryMap);
            }
            ValidationErrorEntryByError entryByError = new ValidationErrorEntryByError();
            entryByError.setErrorname((String) errorName);
            entryByError.setErrorsDetail(allInfoOfSingleError);
            errorEntries.add(entryByError);
        }
        return errorEntries;
    }
}
