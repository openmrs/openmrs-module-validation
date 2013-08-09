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

import org.openmrs.annotation.Authorized;
import org.openmrs.module.validation.ValidationThread;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Allows to manage validation threads.
 * 
 * @see ValidationThread
 */
public interface ValidationService {
	
	@Authorized("Validate Objects")
	@Transactional(readOnly = true)
	void validate(String type, long firstObject, long maxObjects, Map<Object, Exception> errors);

    @Authorized("Validate Objects")
    @Transactional(readOnly = true)
    void startNewValidationThread(String type, Long firstObject, Long maxObjects);

	@Authorized("Validate Objects")
	@Transactional(readOnly = true)
	void startNewValidationThread(String type);
	
	@Authorized("Validate Objects")
	List<ValidationThread> getValidationThreads();
	
	@Authorized("Validate Objects")
	void removeValidationThread(int index);

    @Authorized("Validate Objects")
    void removeAllValidationThreads();
}
