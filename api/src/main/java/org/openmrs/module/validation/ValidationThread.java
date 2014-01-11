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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.validation.api.ValidationService;

/**
 * Represents a validation thread.
 */
public class ValidationThread extends Thread {

	private final UserContext userContext;
	
	private final String type;
	
	private long totalObjects ;

    private int batchSize;
	
	private final long startFrom;
	
	private final Map<Object, Exception> errors;
	
	private volatile long objectsLeftToProcess;

    private static boolean active = true;

    protected final Log log = LogFactory.getLog(getClass());

    public ValidationThread(String type, long startFrom, int batchSize, UserContext userContext) {
        this.userContext = userContext;
        this.type = type;
        this.startFrom = startFrom;
        this.batchSize=batchSize;
        errors = new ConcurrentHashMap<Object, Exception>();

    }

    @Deprecated
	public ValidationThread(String type, long startFrom, long totalObjects, UserContext userContext) {
        this.totalObjects = totalObjects;
        this.userContext = userContext;
		this.type = type;
		this.startFrom = startFrom;
		this.objectsLeftToProcess = totalObjects;
		errors = new ConcurrentHashMap<Object, Exception>();
	}

    /**
     *
     * @return  batch size of an object set to process at once
     */
    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
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
        try {
            Context.getService(ValidationService.class).validate(type, currentPosition, batchSize, errors);
        } catch (Exception ex) {
            log.error("Error occured in running validation thread ", ex);
        } finally {
            deactivateThread(true);
        }
    }
	
}
