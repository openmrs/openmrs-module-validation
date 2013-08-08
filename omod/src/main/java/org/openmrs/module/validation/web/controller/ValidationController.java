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
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
        model.addAttribute("classNamesMap", ValidationUtils.getClassNamesToValidate());
	}
	
	@RequestMapping(value = "/module/validation/validate", params = "validate_button" , method = RequestMethod.POST)
    public ModelAndView validate(@RequestParam("types") String types, HttpServletRequest request, ModelMap model) {
        HttpSession httpSession = request.getSession();
        String[] obtypes = ValidationUtils.getListOfObjectsToValidate(types);
		try {
            for(int i=0; i< obtypes.length; i++){
                if(!StringUtils.isBlank(obtypes[i])) {
                  log.info("Starting validation thread for " + obtypes[i]);
                  getValidationService().startNewValidationThread(obtypes[i]);
                }

            }
        model.addAttribute("listOfObjects", obtypes);
        httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "validation.started");
		}
		catch (Exception e) {
			log.error("Unable to start validation", e);
		}
		return new ModelAndView(new RedirectView("list.form"));
	}

    @RequestMapping(value = "/module/validation/validate", params = "stop_button", method = RequestMethod.POST)
    public ModelAndView stopValidation(HttpServletRequest request) throws Exception {
        try{
            HttpSession httpSession = request.getSession();
            getValidationService().removeAllValidationThreads();
            log.info("Stopped the currently running validation process ");
            httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "validation.stopped");
        }catch (Exception e){
            log.error("Unable to stop validation", e);
        }

        return new ModelAndView(new RedirectView("list.form"));
    }
}
