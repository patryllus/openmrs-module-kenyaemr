/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.IsBreastFeedingCalculation;
import org.openmrs.module.kenyaemr.calculation.library.IsPregnantCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.IPTMetadata;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.util.EncounterBasedRegimenUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.patient.definition.ProgramEnrollmentsForPatientDataDefinition;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Calculates a consolidation of greencard validations such as :
 * In tb program
 * Ever enrolled in HIV program
 * In IPT program
 * Completed IPT program
 * On ART
 * Has been on ART +3 months
 * ART Current Regimen
 * Viral Load
 *Is  pregnant
 * Is breastfeeding
 *
 */
public class GreencardUtilsFragmentController {

    protected static final Log log = LogFactory.getLog(GreencardUtilsFragmentController.class);
    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
     /*
    *  Check whether Patient ever enrolled in HIV program    *
     */

    public SimpleObject checkWhetherPatientEverEnrolledInHivProgram(@RequestParam("patientId") Patient patient) {
        boolean patientEverInHivProgram = false;
        EncounterService encounterService = Context.getEncounterService();
        FormService formService = Context.getFormService();
        PatientService patientService = Context.getPatientService();
        EncounterType et = encounterService.getEncounterTypeByUuid(HivMetadata._EncounterType.HIV_ENROLLMENT);
        Form form = formService.getFormByUuid(HivMetadata._Form.HIV_ENROLLMENT);
        Patient pt = patientService.getPatient(patient.getPatientId());
        Encounter lastHivEnrollmentEncounter = EmrUtils.lastEncounter(pt, et);
        if (lastHivEnrollmentEncounter != null) {
            patientEverInHivProgram = true;
        }
        return SimpleObject.create(
                "everEnrolledInHIV", patientEverInHivProgram
        );
    }

    /*
    *  Check whether in tb program    *
     */

    public SimpleObject checkWhetherInTbProgram(@RequestParam("patientId") Patient patient) {
        boolean patientInTBProgram = false;
        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        Program tbProgram = MetadataUtils.existing(Program.class, TbMetadata._Program.TB);
        //Enrolled In tb program
        CalculationResultMap enrolledInTb = Calculations.activeEnrollment(tbProgram, Arrays.asList(patient.getPatientId()), context);
        PatientProgram program = EmrCalculationUtils.resultForPatient(enrolledInTb, patient.getPatientId());
        if(program != null) {
            patientInTBProgram = true;
        }
            return SimpleObject.create(
                "inTB", patientInTBProgram
        );

    }

    /*
    *  Check whether in IPT program    *
     */

    public SimpleObject checkWhetherInIPTProgram(@RequestParam("patientId") Patient patient) {
        boolean inIptProgram = false;
        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        Program iptProgram = MetadataUtils.existing(Program.class, IPTMetadata._Program.IPT);
        //Enrolled In tb program
        CalculationResultMap enrolledInIpt = Calculations.activeEnrollment(iptProgram, Arrays.asList(patient.getPatientId()), context);
        PatientProgram program = EmrCalculationUtils.resultForPatient(enrolledInIpt, patient.getPatientId());
        if(program != null) {
            inIptProgram = true;
        }
        return SimpleObject.create(
                "inIPT", inIptProgram
        );

    }
    /*
    On ART -- find if client has active ART
     */
    public SimpleObject checkWhetherPatientOnART(@RequestParam("patientId") Patient patient) {
        boolean patientOnART = false;
        String regimenName = null;
        Encounter lastDrugRegimenEditorEncounter = EncounterBasedRegimenUtils.getLastEncounterForCategory(Context.getPatientService().getPatient(patient.getPatientId()), "ARV");   //last DRUG_REGIMEN_EDITOR encounter
        if (lastDrugRegimenEditorEncounter != null) {
            SimpleObject o = EncounterBasedRegimenUtils.buildRegimenChangeObject(lastDrugRegimenEditorEncounter.getAllObs(), lastDrugRegimenEditorEncounter);
            regimenName = o.get("regimenShortDisplay").toString();
            if (regimenName != null) {
                patientOnART = true;
            }
        }
        return SimpleObject.create(
                "onART", patientOnART
        );
    }
    /*
      Regimen name for clients on ART
    */
    public SimpleObject checkRegimenNameForPatientOnART(@RequestParam("patientId") Patient patient) {
        boolean patientOnART = false;
        String regimenName = null;
        String regimen = null;
        Encounter lastDrugRegimenEditorEncounter = EncounterBasedRegimenUtils.getLastEncounterForCategory(Context.getPatientService().getPatient(patient.getPatientId()), "ARV");   //last DRUG_REGIMEN_EDITOR encounter
        if (lastDrugRegimenEditorEncounter != null) {
            SimpleObject o = EncounterBasedRegimenUtils.buildRegimenChangeObject(lastDrugRegimenEditorEncounter.getAllObs(), lastDrugRegimenEditorEncounter);
            regimen = o.get("regimenShortDisplay").toString();
            if (regimen != null) {
                regimenName = regimen;
            }
        }
        return SimpleObject.create(
                "regimenName", regimenName
        );
    }

    /*
      Has Been On ART -- find if client has active ART
    */
    public SimpleObject checkWhetherPatientHasBeenOnART(@RequestParam("patientId") Patient patient) {
        //ART calculations
        boolean hasBeenOnART = false;
        String artStartObsDate = null;
        Date artStartDate = null;
        Date currentDate =new Date();
        Integer tbStartStopDiff = 0;
        Integer artStartCurrDiff = 0;
        String regimenName = null;
        Encounter firstDrugRegimenEditorEncounter = EncounterBasedRegimenUtils.getFirstEncounterForCategory(Context.getPatientService().getPatient(patient.getPatientId()), "ARV");   //first DRUG_REGIMEN_EDITOR encounter
        if (firstDrugRegimenEditorEncounter != null) {
            SimpleObject o = EncounterBasedRegimenUtils.buildRegimenChangeObject(firstDrugRegimenEditorEncounter.getAllObs(), firstDrugRegimenEditorEncounter);
            artStartObsDate =o.get("startDate").toString();
            if (artStartObsDate != null) {
                try {
                    artStartDate = DATE_FORMAT.parse(artStartObsDate);
                    artStartCurrDiff = monthsBetween(currentDate,artStartDate);
                    if (artStartCurrDiff > 3) {
                        hasBeenOnART = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        return SimpleObject.create(
                "hasBeenOnART", hasBeenOnART
        );
    }

    /*
     Duration since start ART
   */
    public SimpleObject checkDurationPatientHasBeenOnART(@RequestParam("patientId") Patient patient) {
        //ART calculations
        boolean hasBeenOnART = false;
        String artStartObsDate = null;
        Date artStartDate = null;
        Date currentDate = new Date();
        Integer artStartCurrDiff = 0;

        Encounter firstDrugRegimenEditorEncounter = EncounterBasedRegimenUtils.getFirstEncounterForCategory(Context.getPatientService().getPatient(patient.getPatientId()), "ARV");   //first DRUG_REGIMEN_EDITOR encounter
        if (firstDrugRegimenEditorEncounter != null) {
            SimpleObject o = EncounterBasedRegimenUtils.buildRegimenChangeObject(firstDrugRegimenEditorEncounter.getAllObs(), firstDrugRegimenEditorEncounter);
            artStartObsDate = o.get("startDate").toString();
            if (artStartObsDate != null) {
                try {
                    artStartDate = DATE_FORMAT.parse(artStartObsDate);
                    artStartCurrDiff = monthsBetween(currentDate, artStartDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return SimpleObject.create(
                "duration", artStartCurrDiff
        );
    }
    /*
    Check patients Adherence
     */
    public SimpleObject checkPatientAdherence(@RequestParam("patientId") Patient patient) {
        Integer  goodAdherenceAnswer = 159405;
        Date adherenceObsDate = null;
        Integer adherenceDiffDays = 0;
        Date currentDate = new Date();
        boolean adherence6Months = false;
        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        //Checking adherence
        Concept AdherenceQuestion = Context.getConceptService().getConcept(1658);
        CalculationResultMap lastAdherenceObs = Calculations.lastObs(AdherenceQuestion, Arrays.asList(patient.getPatientId()), context);
        Concept adherenceResults = EmrCalculationUtils.codedObsResultForPatient(lastAdherenceObs, patient.getPatientId());
        // Good adherence in the last 6 months
        if (adherenceResults != null) {
            if (adherenceResults != null && adherenceResults.getConceptId().equals(goodAdherenceAnswer)) {
                Obs adherenceObsResults = EmrCalculationUtils.obsResultForPatient(lastAdherenceObs, patient.getPatientId());
                adherenceObsDate = adherenceObsResults.getObsDatetime();
                adherenceDiffDays = daysBetween(currentDate, adherenceObsDate);
                if (adherenceDiffDays >= 0 && adherenceDiffDays <= 182) {
                    adherence6Months = true;
                }
            }
        }
        return SimpleObject.create(
                "adherence", adherence6Months
        );
    }

  /*
    Completed IPT 6 months cycle
     */
    public SimpleObject checkPatientHasCompeletedIPT(@RequestParam("patientId") Patient patient) {
        Integer  iptOutcomeQuestion = 161555;
        Integer  iptCompletionAnswer = 1267;
        boolean completed6MonthsIPT = false;
        ConceptService cs = Context.getConceptService();
        Concept IptOutcomeQuestionConcept = cs.getConcept(iptOutcomeQuestion);
        Concept IptCompletionOutcomeConcept = cs.getConcept(iptCompletionAnswer);

        Encounter lastIptOutcomeEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(patient.getPatientId()), Context.getEncounterService().getEncounterTypeByUuid(IPTMetadata._EncounterType.IPT_OUTCOME));   //last ipt outcome encounter
        boolean patientHasCompletedIPTOutcome = lastIptOutcomeEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastIptOutcomeEncounter, IptOutcomeQuestionConcept, IptCompletionOutcomeConcept) : false;

        if (patientHasCompletedIPTOutcome) {
            completed6MonthsIPT = true;
        }
        return SimpleObject.create(
                "iptCompleted", completed6MonthsIPT
        );
    }

    /*
       * Viral load Results
        */
    public SimpleObject checkPatientViralLoadResults(@RequestParam("patientId") Patient patient) {
        //Viral load
        Concept latestVL = Dictionary.getConcept(Dictionary.HIV_VIRAL_LOAD);
        Concept LDLQuestion = Context.getConceptService().getConcept(1305);
        Concept LDLAnswer = Context.getConceptService().getConcept(1302);

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());

        Obs lastVLObsResult = null;
        String ldlResult = null;
        Double vlResult = 0.0;

        CalculationResultMap lastVLObs = Calculations.lastObs(latestVL, Arrays.asList(patient.getPatientId()), context);
        CalculationResultMap lastLDLObs = Calculations.lastObs(LDLQuestion, Arrays.asList(patient.getPatientId()), context);

        //Viral Load
        Double vl = EmrCalculationUtils.numericObsResultForPatient(lastVLObs, patient.getPatientId());
        Concept ldl = EmrCalculationUtils.codedObsResultForPatient(lastLDLObs, patient.getPatientId());

        // get latest of ldl or vl
        if (ldl != null && vl != null) {
            Obs vlObs = EmrCalculationUtils.obsResultForPatient(lastVLObs, patient.getPatientId());
            Obs ldlObs = EmrCalculationUtils.obsResultForPatient(lastLDLObs, patient.getPatientId());
            lastVLObsResult = EmrCalculationUtils.findLastOnOrBefore(Arrays.asList(vlObs, ldlObs), context.getNow());
            if (lastVLObsResult != null && lastVLObsResult.getConcept() == latestVL) {
                vlResult = lastVLObsResult.getValueNumeric();
                ldlResult = null;
            } else if (lastVLObsResult != null && (lastVLObsResult.getConcept() == LDLQuestion && lastVLObsResult.getValueCoded() == LDLAnswer)) {
                ldlResult = "LDL";
                vlResult = 0.0;
            }
        } else if (ldl != null && vl == null) {
            lastVLObsResult = EmrCalculationUtils.obsResultForPatient(lastLDLObs, patient.getPatientId());
            if (lastVLObsResult != null && (lastVLObsResult.getConcept() == LDLQuestion && lastVLObsResult.getValueCoded() == LDLAnswer)) {
                ldlResult = "LDL";
                vlResult = 0.0;
            }
        } else if (ldl == null && vl != null) {
            lastVLObsResult = EmrCalculationUtils.obsResultForPatient(lastVLObs, patient.getPatientId());
            if (lastVLObsResult != null && lastVLObsResult.getConcept() == latestVL) {
                vlResult = lastVLObsResult.getValueNumeric();
                ldlResult = null;
            }
        } else if (ldl == null && vl == null) {
            vlResult = 0.0;
            ldlResult = null;
        }
        return SimpleObject.create(
                "vlResult", vlResult != null ? vlResult : ldlResult
        );
    }
    /*
    *  Check whether in patient is pregnant    *
     */

    public SimpleObject checkWhetherPatientIsPregnant(@RequestParam("patientId") Patient patient) {
        boolean isPregnant = false;

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        //find pregnant women

        CalculationResult result = EmrCalculationUtils.evaluateForPatient(IsPregnantCalculation.class, null, patient);
       if(result != null ){
           isPregnant = true;
       }
        return SimpleObject.create(
                "isPregnant", isPregnant
        );

    }

     /*
    *  Check whether in patient is breastfeeding    *
     */

    public SimpleObject checkWhetherPatientIsBreastFeeding(@RequestParam("patientId") Patient patient) {
        boolean isBreastFeeding = false;

        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        //find breastfeeding women

        CalculationResult result = EmrCalculationUtils.evaluateForPatient(IsBreastFeedingCalculation.class, null, patient);
        if(result != null ){
            isBreastFeeding = true;
        }
        return SimpleObject.create(
                "isBreastFeeding", isBreastFeeding
        );

    }


    private int daysBetween(Date date1, Date date2) {
        DateTime d1 = new DateTime(date1.getTime());
        DateTime d2 = new DateTime(date2.getTime());
        return Math.abs(Days.daysBetween(d1, d2).getDays());
    }
    private int monthsBetween(Date d1, Date d2) {
        DateTime dateTime1 = new DateTime(d1.getTime());
        DateTime dateTime2 = new DateTime(d2.getTime());
        return Math.abs(Months.monthsBetween(dateTime1, dateTime2).getMonths());
    }
}