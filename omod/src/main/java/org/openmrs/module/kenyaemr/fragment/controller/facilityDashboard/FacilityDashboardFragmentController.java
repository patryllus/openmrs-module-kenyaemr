/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller.facilityDashboard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyaemr.reporting.builder.hiv.DashBoardCohorts;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Facility dashboard page controller
 */
public class FacilityDashboardFragmentController {

	private final Log log = LogFactory.getLog(this.getClass());
	
	public String controller(FragmentModel model, UiUtils ui, HttpSession session, @SpringBean KenyaUiUtils kenyaUi) {


		Integer reportedCasesofCovid19 = 0,
				totalContactsListed =0,exposureFromTravelingTogether =0,totalContactsReached =0,coworkerAssociatedExposure = 0,
				healthcareAssociatedExposure = 0, totalContactsUnderCovid19Followup = 0, exposureFromLivingTogether = 0;
		EvaluationContext evaluationContext = new EvaluationContext();
		Calendar calendar = Calendar.getInstance();
		int thisMonth = calendar.get(calendar.MONTH);


		SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Date> dateMap = getReportDates(thisMonth - 1);
		Date startDate = dateMap.get("startDate");
		Date endDate = dateMap.get("endDate");
		Date todaysDate = todaysDate();
		SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
		String reportingPeriod = df.format(endDate);


		evaluationContext.addParameterValue("startDate", startDate);
		evaluationContext.addParameterValue("endDate", endDate);
		evaluationContext.addParameterValue("enrolledOnOrBefore", endDate);


		Set<Integer> totalReportedCases = DashBoardCohorts.reportedCasesofCovid19(evaluationContext).getMemberIds();
		reportedCasesofCovid19 = totalReportedCases != null? totalReportedCases.size(): 0;

		Set<Integer> totalListedContacts = DashBoardCohorts.totalListedContacts(evaluationContext).getMemberIds();
		totalContactsListed = totalListedContacts != null? totalListedContacts.size(): 0;

		Set<Integer> contactsUnderCovid19Followup = DashBoardCohorts.contactsUnderCovid19Followup(evaluationContext).getMemberIds();
		totalContactsUnderCovid19Followup = contactsUnderCovid19Followup != null? contactsUnderCovid19Followup.size(): 0;

		Set<Integer> exposedFromTravelingTogetherList = DashBoardCohorts.exposureFromTravelingTogether(evaluationContext).getMemberIds();
		exposureFromTravelingTogether = exposedFromTravelingTogetherList != null? exposedFromTravelingTogetherList.size(): 0;

		Set<Integer> exposureFromWorkingTogether = DashBoardCohorts.exposureFromWorkingTogether(evaluationContext).getMemberIds();
		coworkerAssociatedExposure = exposureFromWorkingTogether != null? exposureFromWorkingTogether.size(): 0;

		Set<Integer> contactsReached = DashBoardCohorts.totalContactsReached(evaluationContext).getMemberIds();
		totalContactsReached = contactsReached != null? contactsReached.size(): 0;

		Set<Integer> exposureFromHealthcareWork = DashBoardCohorts.healthcareAssociatedExposure(evaluationContext).getMemberIds();
		healthcareAssociatedExposure = exposureFromHealthcareWork != null? exposureFromHealthcareWork.size(): 0;

		Set<Integer> exposureFromLivingTogetherList = DashBoardCohorts.exposureFromLivingTogether(evaluationContext).getMemberIds();
		exposureFromLivingTogether = exposureFromLivingTogetherList != null? exposureFromLivingTogetherList.size(): 0;

		model.addAttribute("reportedCasesofCovid19", reportedCasesofCovid19);
		model.addAttribute("reportPeriod", reportingPeriod);
		model.addAttribute("totalContactsListed", totalContactsListed);
		model.addAttribute("contactsUnderCovid19Followup", totalContactsUnderCovid19Followup);
		model.addAttribute("exposureFromTravelingTogether", exposureFromTravelingTogether);
		model.addAttribute("coworkerAssociatedExposure", coworkerAssociatedExposure);
		model.addAttribute("totalContactsReached", totalContactsReached);
		model.addAttribute("healthcareAssociatedExposure", healthcareAssociatedExposure);
		model.addAttribute("exposureFromLivingTogether", exposureFromLivingTogether);


		return null;
	}

	private Map<String, Date> getReportDates(int month){
		Map<String, Date> reportDates = new HashMap<String, Date>();
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.MONTH, month);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.clear(Calendar.HOUR);
		gc.clear(Calendar.HOUR_OF_DAY);
		gc.clear(Calendar.MINUTE);
		gc.clear(Calendar.SECOND);
		gc.clear(Calendar.MILLISECOND);
		Date monthStart = gc.getTime();
		reportDates.put("startDate", monthStart);
		gc.add(Calendar.MONTH, 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		Date monthEnd = gc.getTime();
		reportDates.put("endDate", monthEnd);
		return reportDates;
	}

	private Date todaysDate(){
		Calendar gc = new GregorianCalendar();
		gc.clear(Calendar.HOUR);
		gc.clear(Calendar.HOUR_OF_DAY);
		gc.clear(Calendar.MINUTE);
		gc.clear(Calendar.SECOND);
		gc.clear(Calendar.MILLISECOND);
		Date today = gc.getTime();

		return today;
	}

}