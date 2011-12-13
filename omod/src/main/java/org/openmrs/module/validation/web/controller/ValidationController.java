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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.validation.ValidationThread;
import org.openmrs.module.validation.api.ValidationService;
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
	public void showList(ModelMap model) {
		model.addAttribute("validationThreads", getValidationService().getValidationThreads());
	}
	
	@RequestMapping(value = "/module/validation/remove", method = RequestMethod.GET)
	public ModelAndView clearList(@RequestParam("thread") Integer thread) {
		getValidationService().removeValidationThread(thread);
		
		return new ModelAndView(new RedirectView("list.form"));
	}
	
	@RequestMapping(value = "/module/validation/report", method = RequestMethod.GET)
	public void showReport(@RequestParam("thread") Integer thread, ModelMap model) {
		ValidationThread validationThread = getValidationService().getValidationThreads().get(thread);
		model.addAttribute("validationThread", validationThread);
	}
	
	@RequestMapping(value = "/module/validation/validate", method = RequestMethod.POST)
	public ModelAndView validate(@RequestParam("type") String type) {
		try {
			getValidationService().startNewValidationThread(type);
		}
		catch (Exception e) {
			log.error("Unable to start validation", e);
		}
		
		return new ModelAndView(new RedirectView("list.form"));
	}
}
