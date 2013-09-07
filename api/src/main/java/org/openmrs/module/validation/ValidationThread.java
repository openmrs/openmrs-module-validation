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
import java.util.concurrent.ConcurrentHashMap;

import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.validation.api.ValidationService;

/**
 * Represents a validation thread.
 */
public class ValidationThread extends Thread {
	
	private final static int BATCH_SIZE = 200;
	
	private final UserContext userContext;
	
	private final String type;
	
	private final long totalObjects;
	
	private final long startFrom;
	
	private final Map<Object, Exception> errors;
	
	private volatile long objectsLeftToProcess;

    private static boolean active = true;

	
	public ValidationThread(String type, long startFrom, long totalObjects, UserContext userContext) {
		this.userContext = userContext;
		this.type = type;
		this.totalObjects = totalObjects;
		this.startFrom = startFrom;
		this.objectsLeftToProcess = totalObjects;
		errors = new ConcurrentHashMap<Object, Exception>();
	}
	
	/**
	 * @return the totalObjects
	 */
	public long getTotalObjects() {
		return totalObjects;
	}
	
	/**
	 * @return the objectsLeftToProcess
	 */
	public long getObjectsLeftToProcess() {
		return objectsLeftToProcess;
	}
	
	/**
	 * @return the startFrom
	 */
	public long getStartFrom() {
		return startFrom;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the errors
	 */
	public Map<Object, Exception> getErrors() {
		return errors;
	}

    public boolean isActive() {
        return ValidationThread.active;
    }

    private void setActive(boolean active) {
        ValidationThread.active = active;
    }

    public void deactivateThread(boolean active){
        setActive(!active);
    }

    /**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Context.setUserContext(userContext);
		
		long currentPosition = startFrom;
        try{
            while (objectsLeftToProcess > 0 && !isInterrupted()) {
                Context.getService(ValidationService.class).validate(type, currentPosition, BATCH_SIZE, errors);

                currentPosition += BATCH_SIZE;

                if (objectsLeftToProcess - BATCH_SIZE > 0) {
                    objectsLeftToProcess -= BATCH_SIZE;
                } else {
                    objectsLeftToProcess = 0;
                }
            }
        } finally {
            deactivateThread(true);
//            System.out.println("Thread is dead: " + this.isActive());
        }


	}
	
}
