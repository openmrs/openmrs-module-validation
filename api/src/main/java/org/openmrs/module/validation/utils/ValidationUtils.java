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

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {

    public static List<String> getObjectTypes() throws Exception {
        List<String> obs = new ArrayList<String>();
        List<Validator> validators =  Context.getRegisteredComponents(Validator.class);
        for (Validator validator: validators){
            Handler annotation = validator.getClass().getAnnotation(Handler.class);
            if (annotation != null){
                Class[] classNames = annotation.supports();
                for(Class classname: classNames){
                    obs.add(classname.getName()) ;
                }
            }

        }
        return obs;
    }

    public static String[] getListOfObjectsToValidate(String type) {
        if(type != null && !type.equals("")){
          return type.trim().split(",");
        } else {
            return new String[0];
        }

    }
}
