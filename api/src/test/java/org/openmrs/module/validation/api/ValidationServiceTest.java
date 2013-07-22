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
 package org.openmrs.module.validation.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationThread;
import org.openmrs.module.validation.api.ValidationService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

 public class ValidationServiceTest extends BaseModuleContextSensitiveTest {

     private ValidationService validationService;

    @Before
    public void before() throws Exception {
        validationService = Context.getService(ValidationService.class);
    }

     /**
     * @verifies verify all validation threads have started
     * @see org.openmrs.module.validation.api.impl.ValidationServiceImpl#startNewValidationThread(String)
     */
    @Test
    public void startNewValidationThread_shouldVerifyAllValidationThreadsHaveStarted() throws Exception {
        String objectType = "org.openmrs.Concept";
        validationService.startNewValidationThread(objectType);
        for(ValidationThread validationThread : validationService.getValidationThreads()){
            Assert.assertTrue(!validationThread.getState().equals(Thread.State.NEW));
        }
    }

    /**
     * @verifies verify validation is completed
     * @see org.openmrs.module.validation.api.impl.ValidationServiceImpl#validate(String, long, long, java.util.Map)
     */
    @Test
    public void validate_shouldVerifyValidationIsCompleted() throws Exception {
        String objectType = "org.openmrs.Concept";
        Map<Object, Exception> errors = new ConcurrentHashMap<Object, Exception>();
        validationService.validate(objectType, 0, 200, errors);
        //Assert.assertTrue(!errors.isEmpty());
    }

    /**
     * @verifies verify thread count is not 0
     * @see org.openmrs.module.validation.api.impl.ValidationServiceImpl#getValidationThreads()
     */
    @Test
    public void getValidationThreads_shouldVerifyThreadCountIsNotZero() throws Exception {
        String objectType = "org.openmrs.Concept";
        validationService.startNewValidationThread(objectType);
        List<ValidationThread> threadList = validationService.getValidationThreads();
        Assert.assertTrue(!threadList.isEmpty());
    }
}
