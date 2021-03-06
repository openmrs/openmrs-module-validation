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

import org.apache.commons.collections.MultiMap;

/**
 * Define a error entry to be added into error view tab of the validation report
 * The structure of the entry is like,e.g.
 * <ErrorName>
 * <ClassName 1>    ---> <Error 1 Description>
 * <ClassName 2>    ---> <Error 2 Description>
 *
 */
public class ValidationErrorEntryByError {

    private String errorname;
    private MultiMap errorsDetail;

    public String getErrorname() {
        return errorname;
    }

    public void setErrorname(String errorname) {
        this.errorname = errorname;
    }

    public MultiMap getErrorsDetail() {
        return errorsDetail;
    }

    public void setErrorsDetail(MultiMap errorsDetail) {
        this.errorsDetail = errorsDetail;
    }
}
