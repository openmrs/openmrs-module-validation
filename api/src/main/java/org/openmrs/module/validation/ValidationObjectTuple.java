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

public class ValidationObjectTuple {
    ValidationObject first;
    ValidationObject second;
    ValidationObject third;

    public ValidationObjectTuple() {
    }

    public ValidationObject getFirst() {
        return first;
    }

    public void setFirst(ValidationObject first) {
        this.first = first;
    }

    public ValidationObject getSecond() {
        return second;
    }

    public void setSecond(ValidationObject second) {
        this.second = second;
    }

    public ValidationObject getThird() {
        return third;
    }

    public void setThird(ValidationObject third) {
        this.third = third;
    }

}
