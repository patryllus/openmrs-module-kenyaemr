/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller.patient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * Visit summary fragment
 */
public class PatientComorbiditiesFragmentController {
	
	protected static final Log log = LogFactory.getLog(PatientComorbiditiesFragmentController.class);
	
	ObsService obsService = Context.getObsService();
	
	ConceptService conceptService = Context.getConceptService();
	
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


	public void controller(@FragmentParam("patient") Patient patient, FragmentModel model) {


		Form cifForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.COVID_19_CARE_FORM);
		EncounterType cifEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.COVID_19_CASE_INVESTIGATION);

		Encounter lastCifEnc = EmrUtils.lastEncounter(patient, cifEncType, cifForm);

		if (lastCifEnc != null && lastCifEnc.getObs() != null) {
			
			model.addAttribute("comorbidities", processCormobidities(lastCifEnc.getObs()));
		} else {
			model.addAttribute("comorbidities", null);

		}
	}
	
	private SimpleObject processCormobidities(Set<Obs> obsList) {
		String pregnant = null;
		String trimester = null;
		String cardiovascularDisease = null;
		String diabetes = null;
		String liverDisease = null;
		String immunodeficiency = null;
		String renalDisease = null;
		String chronicLungDisease = null;
		String malignancy = null;

		Integer PREGNANCY = 1434;//1434 - 1065, 1066
		Integer PREGNANCY_POSITIVE = 1065;

		Integer TRIMESTER = 160665;//160665 - 1721,1722,1723
		Integer FIRST_TRIMESTER = 1721;//160665 - 1721,1722,1723
		Integer SECOND_TRIMESTER = 1722;//160665 - 1721,1722,1723
		Integer THIRD_TRIMESTER = 1723;//160665 - 1721,1722,1723

		Integer CARDIOVASCULAR_DISEASES = 162747;//162747 - 119270, NO
		Integer HAS_CARDIOVASCULAR_DISEASES = 119270;//162747 - 119270, NO

		Integer DIABETES = 162747;// 162747 - 119481, no
		Integer HAS_DIABETES = 119481;// 162747 - 119481, no

		Integer LIVER_DISEASE = 162747;//162747 - 6032, NO
		Integer HAS_LIVER_DISEASE = 6032;//162747 - 6032, NO

		Integer IMMUNODEFICIENCY = 162747;//162747 - 162695, NO
		Integer HAS_IMMUNODEFICIENCY = 162695;//162747 - 162695, NO

		Integer RENAL_DISEASE = 162747;//162747 - 6033
		Integer HAS_RENAL_DISEASE = 6033;//162747 - 6033

		Integer CHRONIC_LUNG_DISEASE = 162747;//162747 - 155569
		Integer HAS_CHRONIC_LUNG_DISEASE = 155569;//162747 - 155569

		Integer MALIGNANCY = 162747;//162747 - 116031
		Integer HAS_MALIGNANCY = 116031;//162747 - 116031

		for (Obs obs : obsList) {
			if (obs.getConcept().getConceptId().equals(PREGNANCY) && obs.getValueCoded().getConceptId().equals(PREGNANCY_POSITIVE)) {
				pregnant = "Yes";
			} else if ((obs.getConcept().getConceptId().equals(TRIMESTER))) {
				if (obs.getValueCoded().getConceptId().equals(FIRST_TRIMESTER)) {
					trimester = "First Trimester";
				} else if (obs.getValueCoded().getConceptId().equals(SECOND_TRIMESTER)) {
					trimester = "Second Trimester";
				} else if (obs.getValueCoded().getConceptId().equals(THIRD_TRIMESTER)) {
					trimester = "Third Trimester";
				}
			} else if (obs.getConcept().getConceptId().equals(CARDIOVASCULAR_DISEASES) && obs.getValueCoded().getConceptId().equals(HAS_CARDIOVASCULAR_DISEASES)) {
				cardiovascularDisease = "Yes";
			} else if (obs.getConcept().getConceptId().equals(DIABETES) && obs.getValueCoded().getConceptId().equals(HAS_DIABETES)) {
				diabetes = "Yes";
			} else if (obs.getConcept().getConceptId().equals(LIVER_DISEASE) && obs.getValueCoded().getConceptId().equals(HAS_LIVER_DISEASE)) {
				liverDisease = "Yes";
			} else if (obs.getConcept().getConceptId().equals(IMMUNODEFICIENCY) && obs.getValueCoded().getConceptId().equals(HAS_IMMUNODEFICIENCY)) {
				immunodeficiency = "Yes";
			} else if (obs.getConcept().getConceptId().equals(RENAL_DISEASE) && obs.getValueCoded().getConceptId().equals(HAS_RENAL_DISEASE)) {
				renalDisease = "Yes";
			} else if (obs.getConcept().getConceptId().equals(CHRONIC_LUNG_DISEASE) && obs.getValueCoded().getConceptId().equals(HAS_CHRONIC_LUNG_DISEASE)) {
				chronicLungDisease = "Yes";
			} else if (obs.getConcept().getConceptId().equals(MALIGNANCY) && obs.getValueCoded().getConceptId().equals(HAS_MALIGNANCY)) {
				malignancy = "Yes";
			}
		}

		if (pregnant != null || cardiovascularDisease != null || diabetes != null || liverDisease != null || immunodeficiency != null
				|| renalDisease != null || chronicLungDisease != null || malignancy != null) {
			return SimpleObject.create(
					"pregnant", pregnant != null ? pregnant : "",
					"trimester", trimester != null ? trimester : "",
					"cardiovasculardisease", cardiovascularDisease != null ? cardiovascularDisease : "",
					"diabetes", diabetes != null ? diabetes : "",
					"liverDisease", liverDisease != null ? liverDisease : "",
					"immunodeficiency", immunodeficiency != null ? immunodeficiency : "",
					"renalDisease", renalDisease != null ? renalDisease : "",
					"chronicLungDisease", chronicLungDisease != null ? chronicLungDisease : "",
					"malignancy", malignancy != null ? malignancy : "");
		}
		return null;
	}
}
