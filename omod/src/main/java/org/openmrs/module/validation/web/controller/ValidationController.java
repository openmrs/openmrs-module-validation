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
package org.openmrs.module.validation.web.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.api.ValidationService;
import org.openmrs.module.validation.utils.ValidationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The main controller.
 */
@Controller
public class ValidationController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	protected ValidationService getValidationService() {
		return Context.getService(ValidationService.class);
	}
	
	@RequestMapping(value = "/module/validation/list", method = RequestMethod.GET)
	public void showList(ModelMap model) throws Exception {
        model.addAttribute("objectTuples", ValidationUtils.getClassNamesToValidate());
	}
	
	@RequestMapping(value = "/module/validation/validate", method = RequestMethod.POST)
	public ModelAndView validate(@RequestParam("types") String types, ModelMap model) {
        String[] obtypes = ValidationUtils.getListOfObjectsToValidate(types);
		try {
            for(int i=0; i< obtypes.length; i++){
                if(!StringUtils.isBlank(obtypes[i])) {
                  log.info("Starting validation thread for " + obtypes[i]);
                  getValidationService().startNewValidationThread(obtypes[i]);
                }

            }
        model.addAttribute("listOfObjects", obtypes);

		}
		catch (Exception e) {
			log.error("Unable to start validation", e);
		}
		
		return new ModelAndView(new RedirectView("list.form"));
	}

}
