/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.covid;

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.CohortReportDescriptor;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.builder.CalculationReportBuilder;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
<<<<<<< HEAD
<<<<<<< HEAD
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressNationalityCalculation;
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressSubCountyCalculation;
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressCountyCalculation;
import org.openmrs.module.kenyaemr.calculation.library.rdqa.PatientProgramEnrollmentCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.PatientProgramEnrollmentDateConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
=======
import org.openmrs.module.kenyaemr.calculation.library.hiv.Cd4DueDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastReturnVisitDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
=======
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressNationalityCalculation;
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressSubCountyCalculation;
import org.openmrs.module.kenyaemr.calculation.library.covid.PersonAddressCountyCalculation;
>>>>>>> add country, county, sub county address calculations
import org.openmrs.module.kenyaemr.calculation.library.rdqa.PatientProgramEnrollmentCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.PatientProgramEnrollmentDateConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@Builds({"kenyaemr.covid.report.confirmedCases"})
public class ConfirmedCovid19CasesReportBuilder extends CalculationReportBuilder {

    @Override
    protected void addColumns(CohortReportDescriptor report, PatientDataSetDefinition dsd) {
        PatientIdentifierType nationalIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_ID);
        PatientIdentifierType passportNumberType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PASSPORT_NUMBER);
        DataDefinition natIdDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nationalIdType.getName(), nationalIdType), new IdentifierConverter());
        DataDefinition passportDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(passportNumberType.getName(), passportNumberType), new IdentifierConverter());
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        String DATE_FORMAT = "dd/MM/yyyy";

        addStandardColumns(report, dsd);
        dsd.addColumn("National ID", natIdDef, "");
        dsd.addColumn("Passport Number", passportDef, "");
        dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
        dsd.addColumn("Nationality", new CalculationDataDefinition("Nationality", new PersonAddressNationalityCalculation()), "", new RDQACalculationResultConverter());
        dsd.addColumn("County", new CalculationDataDefinition("County", new PersonAddressCountyCalculation()), "", new RDQACalculationResultConverter());
        dsd.addColumn("Sub-County", new CalculationDataDefinition("Sub-County", new PersonAddressSubCountyCalculation()), "", new RDQACalculationResultConverter());
        dsd.addColumn("Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");

        dsd.addColumn("Case Reporting Date", new CalculationDataDefinition("Case Reporting Date", new PatientProgramEnrollmentCalculation()), "", new PatientProgramEnrollmentDateConverter());


    }
}
