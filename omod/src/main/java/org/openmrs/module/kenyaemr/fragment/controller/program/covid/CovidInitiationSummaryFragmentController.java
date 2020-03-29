/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller.program.covid;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Patient program enrollment fragment
 */
public class CovidInitiationSummaryFragmentController {

	ConceptService conceptService = Context.getConceptService();
	ObsService obsService = Context.getObsService();
	String POINT_OF_DETECTION_CONCEPT = "161010AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String SYMPTOMATIC_CONCEPT = "1729AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String DATE_DETECTED_CONCEPT = "159948AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String STABILITY_CONCEPT = "159640AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String TRAVEL_HISTORY_CONCEPT = "162619AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String CONTACT_WITH_COVID_SUSPECT_CONCEPT = "162633AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	EncounterService encounterService = Context.getEncounterService();
	public String controller(@FragmentParam("patientProgram") PatientProgram patientProgram,
			@FragmentParam(value = "encounter", required = false) Encounter encounter,
			@FragmentParam("showClinicalData") boolean showClinicalData, FragmentModel model) {

		Map<String, Object> dataPoints = new LinkedHashMap<String, Object>();
		dataPoints.put("Date case reported", patientProgram.getDateEnrolled());


		if (encounter != null) {
			EncounterWrapper wrapper = new EncounterWrapper(encounter);

			Obs detectionPoint = wrapper.firstObs(Dictionary.getConcept(POINT_OF_DETECTION_CONCEPT));
			Obs symptomatic = wrapper.firstObs(Dictionary.getConcept(SYMPTOMATIC_CONCEPT));
			Obs dateDetected = wrapper.firstObs(Dictionary.getConcept(DATE_DETECTED_CONCEPT));
			Obs patientState = wrapper.firstObs(Dictionary.getConcept(STABILITY_CONCEPT));
			Obs travelHistory = wrapper.firstObs(Dictionary.getConcept(TRAVEL_HISTORY_CONCEPT));
			Obs contactWithSuspect = wrapper.firstObs(Dictionary.getConcept(CONTACT_WITH_COVID_SUSPECT_CONCEPT));

			if (detectionPoint != null) {
				dataPoints.put("Detection point", detectionPoint.getValueCoded());
			}

			if (symptomatic != null) {
				dataPoints.put("Symptomatic", symptomatic.getValueCoded());
			}

			if (dateDetected != null) {
				dataPoints.put("Date Detected", dateDetected.getValueDatetime());
			}

			if (patientState != null) {
				dataPoints.put("Stability of case at reporting", patientState.getValueCoded());
			}

			if (travelHistory != null) {
				dataPoints.put("Travel history", travelHistory.getValueCoded());
			}

			if (contactWithSuspect != null) {
				dataPoints.put("Contact with suspect", contactWithSuspect.getValueCoded());
			}

		}

		/*Enrollment enrollment = new Enrollment(patientProgram);

		List<Obs> obs = obsService.getObservations(
				Arrays.asList(Context.getPersonService().getPerson(patientProgram.getPatient().getPersonId())),
				Arrays.asList(enrollment.lastEncounter(encounterService.getEncounterTypeByUuid(CommonMetadata._EncounterType.CO))),
				Arrays.asList(groupingConcept),
				null,
				null,
				null,
				Arrays.asList("obsId"),
				null,
				null,
				null,
				null,
				false
		);*/


		model.put("dataPoints", dataPoints);
		return "view/dataPoints";
	}

	String booleanAnswerConverter (Concept key) {
		Map<Concept, String> booleanAnswerList = new HashMap<Concept, String>();
		booleanAnswerList.put(conceptService.getConcept(1065), "Yes");
		booleanAnswerList.put(conceptService.getConcept(1066), "No");
		booleanAnswerList.put(conceptService.getConcept(1067), "Unknown");
		return booleanAnswerList.get(key);
	}

	String pointofDetectionConverter (Concept key) {
		Map<Concept, String> pointofDetectionList = new HashMap<Concept, String>();
		pointofDetectionList.put(conceptService.getConcept(165651), "Point of Entry");
		pointofDetectionList.put(conceptService.getConcept(163488), "Community");
		pointofDetectionList.put(conceptService.getConcept(1067), "Unknown");
		return pointofDetectionList.get(key);
	}

	String healthStatusConverter (Concept key) {
		Map<Concept, String> pointofDetectionList = new HashMap<Concept, String>();
		pointofDetectionList.put(conceptService.getConcept(159405), "Stable");
		pointofDetectionList.put(conceptService.getConcept(159407), "Severely ill");
		pointofDetectionList.put(conceptService.getConcept(160432), "Dead");
		pointofDetectionList.put(conceptService.getConcept(1067), "Unknown");
		return pointofDetectionList.get(key);
	}
}
