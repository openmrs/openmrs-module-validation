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
package org.openmrs.module.validation;

import java.util.Map;

/**
 * Define a error entry to be added into class view tab of the validation report
 * The structure of the entry is like,e.g.
 * <ClassName> ---> <Error 1 Description>
 *                  <Error 2 Description>
 *
 */
public class ValidationErrorEntryByClass {

    private String classname;
    private Map<String, Exception> errors;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Map<String, Exception> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Exception> errors) {
        this.errors = errors;
    }
}


