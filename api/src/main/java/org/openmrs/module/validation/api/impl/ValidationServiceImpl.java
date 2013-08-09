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
package org.openmrs.module.validation.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationThread;
import org.openmrs.module.validation.api.ValidationService;
import org.openmrs.validator.ValidateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public class ValidationServiceImpl implements ValidationService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;
	
	private List<ValidationThread> validationThreads = new CopyOnWriteArrayList<ValidationThread>();
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

    /**
     * @see org.openmrs.module.validation.api.ValidationService#startNewValidationThread(java.lang.String, java.lang.Long, java.lang.Long)
     * @deprecated
     */
    public void startNewValidationThread(String type, Long firstObject, Long lastObject) {
        Object result = sessionFactory.getCurrentSession().createCriteria(type).addOrder(Order.asc("uuid"))
                .setProjection(Projections.rowCount()).uniqueResult();

        Long totalObjects = ((Number) result).longValue();

        if (lastObject == null || lastObject > totalObjects) {
            lastObject = totalObjects;
        }

        if (firstObject == null || firstObject < 0) {
            firstObject = 0L;
        } else if (firstObject > lastObject) {
            firstObject = lastObject;
        }

        Long partition = 0L;
        if (totalObjects > 0) {
            partition = (lastObject - firstObject) / 10;
        }

        for (long i = firstObject; i < lastObject; i += partition + 1) {
            ValidationThread validationThread = new ValidationThread(type, i, partition, Context.getUserContext());
            validationThread.start();

            validationThreads.add(validationThread);
        }

    }

	/**
	 * @see org.openmrs.module.validation.api.ValidationService#startNewValidationThread(java.lang.String)
     * @should verify all validation threads have started
	 */
	public void startNewValidationThread(String type) {
		Object result = sessionFactory.getCurrentSession().createCriteria(type).setProjection(Projections.rowCount()).uniqueResult();
		
		Long totalObjects = ((Number) result).longValue();

		Long partition = 0L;
		if (totalObjects > 0) {
			partition = (totalObjects) / 10;
		}
		
		for (long i = 0; i < totalObjects; i += partition + 1) {
			ValidationThread validationThread = new ValidationThread(type, i, partition, Context.getUserContext());
			validationThread.start();
			
			validationThreads.add(validationThread);
		}
		
	}
	
	/**
	 * @see org.openmrs.module.validation.api.ValidationService#validate(java.lang.String, long,
	 *      long, java.util.Map)
     * @should verify validation is completed
     *
	 */
	public void validate(String type, long firstObject, long maxObjects, Map<Object, Exception> errors) {
		@SuppressWarnings("unchecked")
		List<Object> list = sessionFactory.getCurrentSession().createCriteria(type).addOrder(Order.asc("uuid"))
		        .setFirstResult((int) firstObject).setMaxResults((int) maxObjects).list();
		
		for (Object object : list) {
			try {
				log.info("Validating " + object);
				ValidateUtil.validate(object);
			}
			catch (Exception e) {
				log.error("Validation fails for:" + object + " as " + e.getMessage() );
				errors.put(object, e);
			}
		}
	}

	/**
	 * @see org.openmrs.module.validation.api.ValidationService#getValidationThreads()
     * @should verify thread count is not 0
	 */
	public List<ValidationThread> getValidationThreads() {
		return new ArrayList<ValidationThread>(validationThreads);
	}
	
	/**
	 * @see org.openmrs.module.validation.api.ValidationService#removeValidationThread(int)
	 */
	public void removeValidationThread(int index) {
		ValidationThread validationThread = validationThreads.get(index);
		validationThread.interrupt();
		validationThreads.remove(index);
	}

    /**
     * @see org.openmrs.module.validation.api.ValidationService#removeAllValidationThreads()
     */
    public void removeAllValidationThreads() {
        for (ValidationThread thread : validationThreads){
            thread.interrupt();
        }
        validationThreads.clear();
    }
	
}
