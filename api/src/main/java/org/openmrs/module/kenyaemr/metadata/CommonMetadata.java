/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.metadata;

import org.openmrs.PatientIdentifierType.LocationBehavior;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.idgen.validator.LuhnMod25IdentifierValidator;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.datatype.FormDatatype;
import org.openmrs.module.kenyaemr.datatype.LocationDatatype;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.encounterType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.form;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.globalProperty;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.patientIdentifierType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.personAttributeType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.relationshipType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.visitAttributeType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.visitType;

/**
 * Common metadata bundle
 */
@Component
public class CommonMetadata extends AbstractMetadataBundle {

	public static final class _EncounterType {
		public static final String CONSULTATION = "465a92f2-baf8-42e9-9612-53064be868e8";
		public static final String LAB_RESULTS = "17a381d1-7e29-406a-b782-aa903b963c28";
		public static final String REGISTRATION = "de1f9d67-b73e-4e1b-90d0-036166fc6995";
		public static final String TRIAGE = "d1059fb9-a079-4feb-a749-eedd709ae542";
		public static final String HTS = "9c0a7a57-62ff-4f75-babe-5835b0e921b7";
		public static final String DRUG_REGIMEN_EDITOR = "7dffc392-13e7-11e9-ab14-d663bd873d93";
		public static final String COVID_19_CASE_INVESTIGATION = "a4414aee-6832-11ea-bc55-0242ac130003";
		public static final String COVID_19_CONTACT_TRACING = "6dd1ace2-6ce2-11ea-bc55-0242ac130003";
		public static final String COVID_19_TRAVEL_HISTORY = "50a59411-921b-435a-9109-42aa68ee7aa7";
		public static final String COVID_OUTCOME = "7b118dac-6f61-4466-ad1a-7e01aca077ad";

	}

	public static final class _Form {
		public static final String CLINICAL_ENCOUNTER = Metadata.Form.CLINICAL_ENCOUNTER;
		public static final String LAB_RESULTS = Metadata.Form.LAB_RESULTS;
		public static final String OBSTETRIC_HISTORY = Metadata.Form.OBSTETRIC_HISTORY;
		public static final String OTHER_MEDICATIONS = Metadata.Form.OTHER_MEDICATIONS;
		public static final String PROGRESS_NOTE = Metadata.Form.PROGRESS_NOTE;
		public static final String SURGICAL_AND_MEDICAL_HISTORY = Metadata.Form.SURGICAL_AND_MEDICAL_HISTORY;
		public static final String TRIAGE = Metadata.Form.TRIAGE;
		public static final String HTS_INITIAL_TEST = "402dc5d7-46da-42d4-b2be-f43ea4ad87b0";
		public static final String HTS_CONFIRMATORY_TEST = "b08471f6-0892-4bf7-ab2b-bf79797b8ea4";
		public static final String HTS_LINKAGE = "050a7f12-5c52-4cad-8834-863695af335d";
		public static final String CONTACT_LISTING = "d4493a7c-49fc-11e8-842f-0ed5f89f718b";
		public static final String BASIC_REGISTRATION = "add7abdc-59d1-11e8-9c2d-fa7ae01bbebc";
		public static final String DRUG_REGIMEN_EDITOR = "da687480-e197-11e8-9f32-f2801f1b9fd1";
		public static final String HTS_CLIENT_TRACING = "15ed03d2-c972-11e9-a32f-2a2ae2dbcce4";
		public static final String HTS_REFERRAL = "9284828e-ce55-11e9-a32f-2a2ae2dbcce4";
		public static final String COVID_19_CARE_FORM = "0fe60b26-8648-438b-afea-8841dcd993c6";
		public static final String COVID_19_CONTACT_TRACING_FORM = "37ef8f3c-6cd2-11ea-bc55-0242ac130003";
		public static final String COVID_19_TRAVEL_HISTORY = "87513b50-6ced-11ea-bc55-0242ac130003";
		public static final String COVID_OUTCOME = "8f4e3e83-c597-47ad-8999-b788e8255d20";

	}

	public static final class _OrderType {
		public static final String DRUG = "131168f4-15f5-102d-96e4-000c29c2a5d7";
	}


	public static final class _PatientIdentifierType {
		public static final String NATIONAL_ID = Metadata.IdentifierType.NATIONAL_ID;
		public static final String OLD_ID = Metadata.IdentifierType.OLD;
		public static final String OPENMRS_ID = Metadata.IdentifierType.MEDICAL_RECORD_NUMBER;
		public static final String PATIENT_CLINIC_NUMBER = Metadata.IdentifierType.PATIENT_CLINIC_NUMBER;
		public static final String NATIONAL_UNIQUE_PATIENT_IDENTIFIER = Metadata.IdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER;
		public static final String CWC_NUMBER = Metadata.IdentifierType.CWC_NUMBER;
		public static final String ALIEN_NUMBER = "e1e80b5c-6d7e-11ea-bc55-0242ac130003"; // this takes care of migration
		public static final String PASSPORT_NUMBER = "e1e80daa-6d7e-11ea-bc55-0242ac130003"; // this takes care of migration
	}

	public static final class _PersonAttributeType {
		public static final String NEXT_OF_KIN_ADDRESS = "7cf22bec-d90a-46ad-9f48-035952261294";
		public static final String NEXT_OF_KIN_CONTACT = "342a1d39-c541-4b29-8818-930916f4c2dc";
		public static final String NEXT_OF_KIN_NAME = "830bef6d-b01f-449d-9f8d-ac0fede8dbd3";
		public static final String NEXT_OF_KIN_RELATIONSHIP = "d0aa9fd1-2ac5-45d8-9c5e-4317c622c8f5";
		public static final String SUBCHIEF_NAME = "40fa0c9c-7415-43ff-a4eb-c7c73d7b1a7a";
		public static final String TELEPHONE_CONTACT = "b2c38640-2603-4629-aebd-3b54f33f1e3a";
		public static final String EMAIL_ADDRESS = "b8d0b331-1d2d-4a9a-b741-1816f498bdb6";
		public static final String ALTERNATE_PHONE_CONTACT = "94614350-84c8-41e0-ac29-86bc107069be";
		public static final String NEAREST_HEALTH_CENTER = "27573398-4651-4ce5-89d8-abec5998165c";
		public static final String GUARDIAN_FIRST_NAME = "8caf6d06-9070-49a5-b715-98b45e5d427b";
		public static final String GUARDIAN_LAST_NAME = "0803abbd-2be4-4091-80b3-80c6940303df";
		public static final String COUNTY = "3b2d1ef0-204b-4493-95f4-fcfdb4c4f7fa";
	}

	public static final class _Provider {
		public static final String UNKNOWN = "ae01b8ff-a4cc-4012-bcf7-72359e852e14";
	}

	public static final class _RelationshipType {
		public static final String SPOUSE = "d6895098-5d8d-11e3-94ee-b35a4132a5e3";
		public static final String GUARDIAN_DEPENDANT = "5f115f62-68b7-11e3-94ee-6bef9086de92";
		public static final String PARTNER = "007b765f-6725-4ae9-afee-9966302bace4";
		public static final String CO_WIFE = "2ac0d501-eadc-4624-b982-563c70035d46";


		public static final String WORKING_TOGETHER_WITH_COVD_CASE = "8ea9902c-6ed3-11ea-bc55-0242ac130003";
		public static final String TRAVELING_TOGETHER_WITH_COVD_CASE = "8ea992ac-6ed3-11ea-bc55-0242ac130003";
		public static final String LIVING_TOGETHER_WITH_COVD_CASE = "8ea993ba-6ed3-11ea-bc55-0242ac130003";
		public static final String HEALTHCARE_ASSOCIATED_EXPOSURE_TO_COVD = "8ea99662-6ed3-11ea-bc55-0242ac130003";

	}

	public static final class _VisitAttributeType {
		public static final String SOURCE_FORM = "8bfab185-6947-4958-b7ab-dfafae1a3e3d";
	}

	public static final class _VisitType {
		public static final String OUTPATIENT = "3371a4d4-f66f-4454-a86d-92c7b3da990c";
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("Consultation", "Collection of clinical data during the main consultation", _EncounterType.CONSULTATION));
		install(encounterType("Lab Results", "Collection of laboratory results", _EncounterType.LAB_RESULTS));
		install(encounterType("Registration", "Initial data collection for a patient, not specific to any program", _EncounterType.REGISTRATION));
		install(encounterType("Triage", "Collection of limited data prior to a more thorough examination", _EncounterType.TRIAGE));
		install(encounterType("HTS", "HTS Services", _EncounterType.HTS));
		install(encounterType("Drug Regimen Editor", "Handles patient regimen events", _EncounterType.DRUG_REGIMEN_EDITOR));
		install(encounterType("COVID-19 Case Investigation", "COVID-19 Case Investigation", _EncounterType.COVID_19_CASE_INVESTIGATION));
		install(encounterType("COVID-19 Contact tracing", "COVID-19 treatment form", _EncounterType.COVID_19_CONTACT_TRACING));
		install(encounterType("COVID-19 Travel", "COVID-19 travel", _EncounterType.COVID_19_TRAVEL_HISTORY));
		install(encounterType("COVID-19 Outcome", "COVID-19 travel", _EncounterType.COVID_OUTCOME));

		install(form("Clinical Encounter", null, _EncounterType.CONSULTATION, "1", _Form.CLINICAL_ENCOUNTER));
		install(form("Lab Results", null, _EncounterType.LAB_RESULTS, "1", _Form.LAB_RESULTS));
		install(form("Obstetric History", null, _EncounterType.REGISTRATION, "1", _Form.OBSTETRIC_HISTORY));
		install(form("Medications", "Recording of non-regimen medications", _EncounterType.CONSULTATION, "1", _Form.OTHER_MEDICATIONS));
		install(form("Progress Note", "For additional information - mostly complaints and examination findings.", _EncounterType.CONSULTATION, "1", _Form.PROGRESS_NOTE));
		install(form("Surgical and Medical History", null, _EncounterType.REGISTRATION, "1", _Form.SURGICAL_AND_MEDICAL_HISTORY));
		install(form("Triage", null, _EncounterType.TRIAGE, "1", _Form.TRIAGE));
		install(form("HTS Initial Form", "Form for HTS testing services ", _EncounterType.HTS, "1", _Form.HTS_INITIAL_TEST));
		install(form("HTS Retest Form", "Form for HTS retest Services", _EncounterType.HTS, "1", _Form.HTS_CONFIRMATORY_TEST));
		install(form("HTS Linkage Form", "Form for HTS linkage", _EncounterType.HTS, "1", _Form.HTS_LINKAGE));
		install(form("Contact Listing Form", "Lists all contacts for a patient", _EncounterType.HTS, "1", _Form.CONTACT_LISTING));
		install(form("Registration Form", "Initial data collection for a patient/client, not specific to any program", _EncounterType.REGISTRATION, "1", _Form.BASIC_REGISTRATION));
		install(form("Drug Regimen Editor", null, _EncounterType.DRUG_REGIMEN_EDITOR, "1", _Form.DRUG_REGIMEN_EDITOR));
		install(form("HTS Client Tracing Form", "Form for tracing hts clients", _EncounterType.HTS, "1", _Form.HTS_CLIENT_TRACING));
		install(form("HTS Client Referral Form", "Form for HTS linkage referral", _EncounterType.HTS, "1", _Form.HTS_REFERRAL));
		install(form("COVID-19 Case Investigation Form", "A form to record treatment for covid-19 PUI ", _EncounterType.COVID_19_CASE_INVESTIGATION, "1", _Form.COVID_19_CARE_FORM));
		install(form("COVID-19 Travel History", "A form to record travel history for covid-19 PUI ", _EncounterType.COVID_19_TRAVEL_HISTORY, "1", _Form.COVID_19_TRAVEL_HISTORY));
		install(form("COVID-19 Monitored Reporting Form", "A form to trace contacts for covid-19 ", _EncounterType.COVID_19_CONTACT_TRACING, "1", _Form.COVID_19_CONTACT_TRACING_FORM));

		install(form("COVID Outcome", "Covid outcome form ", _EncounterType.COVID_OUTCOME, "1", _Form.COVID_OUTCOME));



		install(globalProperty(EmrConstants.GP_DEFAULT_LOCATION, "The facility for which this installation is configured",
				LocationDatatype.class, null, null));

		String adxMappingString = "[{\"reportName\":\"MOH 731\",\"prefix\":\"Y18_\",\"datasets\":[{\"name\":\"2\",\"dhisName\":\"xUesg8lcmDs\"},{\"name\":\"1\",\"dhisName\":\"ptIUGFkE6jn\"},{\"name\":\"3\",\"dhisName\":\"Vo4KDrUFwnA\"}]}]";

		install(globalProperty(EmrConstants.GP_DHIS2_DATASET_MAPPING, "ADX Mapping for KenyaEMR and DHIS2 datasets", adxMappingString));
		install(globalProperty("order.drugDosingUnitsConceptUuid", "Drug dosing units concept", "162384AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

		install(patientIdentifierType("Old Identification Number", "Identifier given out prior to OpenMRS",
				null, null, null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.OLD_ID));
		install(patientIdentifierType("OpenMRS ID", "Medical Record Number generated by OpenMRS for every patient",
				null, null, LuhnMod25IdentifierValidator.class,
				LocationBehavior.REQUIRED, false, _PatientIdentifierType.OPENMRS_ID));
		install(patientIdentifierType("Patient Clinic Number", "Assigned to the patient at a clinic service (not globally unique)",
				".{1,15}", "At most 15 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.PATIENT_CLINIC_NUMBER));
		install(patientIdentifierType("National ID", "Kenyan national identity card number",
				"\\d{5,10}", "Between 5 and 10 consecutive digits", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.NATIONAL_ID));
		install(patientIdentifierType("National Unique patient identifier", "National Unique patient identifier",
				".{1,14}", "At most 14 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER));
		install(patientIdentifierType("CWC Number", "Assigned to a child patient when enrolling into the Child Welfare Clinic (CWC)",
				".{1,14}", "Should take the format (CWC-MFL code-serial number) e.g CWC-15007-00001", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.CWC_NUMBER));

		install(patientIdentifierType("Alien Number", "Alien Number",
				".{1,14}", "At most 14 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.ALIEN_NUMBER));

		install(patientIdentifierType("Passport Number", "Passport Number",
				".{1,14}", "At most 14 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.PASSPORT_NUMBER));
		
		install(personAttributeType("Telephone contact", "Telephone contact number",
				String.class, null, false, 1.0, _PersonAttributeType.TELEPHONE_CONTACT));
		install(personAttributeType("Email address", "Email address of person",
				String.class, null, false, 2.0, _PersonAttributeType.EMAIL_ADDRESS));

		// Patient only person attributes..
		install(personAttributeType("Subchief name", "Name of subchief or chief of patient's area",
				String.class, null, false, 3.0, _PersonAttributeType.SUBCHIEF_NAME));
		install(personAttributeType("Next of kin name", "Name of patient's next of kin",
				String.class, null, false, 4.0, _PersonAttributeType.NEXT_OF_KIN_NAME));
		install(personAttributeType("Next of kin relationship", "Next of kin relationship to the patient",
				String.class, null, false, 4.1, _PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP));
		install(personAttributeType("Next of kin contact", "Telephone contact of patient's next of kin",
				String.class, null, false, 4.2, _PersonAttributeType.NEXT_OF_KIN_CONTACT));
		install(personAttributeType("Next of kin address", "Address of patient's next of kin",
				String.class, null, false, 4.3, _PersonAttributeType.NEXT_OF_KIN_ADDRESS));
		install(personAttributeType("Alternate Phone Number", "Patient's alternate phone number",
				String.class, null, false, 4.3, _PersonAttributeType.ALTERNATE_PHONE_CONTACT));
		install(personAttributeType("Nearest Health Facility", "Patient's nearest Health Facility",
				String.class, null, false, 4.3, _PersonAttributeType.NEAREST_HEALTH_CENTER));
		// guardian properties
		install(personAttributeType("Guardian First Name", "Guardian's first name",
				String.class, null, false, 4.3, _PersonAttributeType.GUARDIAN_FIRST_NAME));
		install(personAttributeType("Guardian Last Name", "Guardian's last name",
				String.class, null, false, 4.3, _PersonAttributeType.GUARDIAN_LAST_NAME));
		install(personAttributeType("County Name", "Name of the county user resides",
				String.class, null, false, 4.3, _PersonAttributeType.COUNTY));

		/*install(relationshipType("Guardian", "Dependant", "One that guards, watches over, or protects", _RelationshipType.GUARDIAN_DEPENDANT));
		install(relationshipType("Spouse", "Spouse", "A spouse is a partner in a marriage, civil union, domestic partnership or common-law marriage a male spouse is a husband and a female spouse is a wife", _RelationshipType.SPOUSE));
		install(relationshipType("Partner", "Partner", "Someone I had sex with for fun without commitment to a relationship", _RelationshipType.PARTNER));
		install(relationshipType("Co-wife", "Co-wife", "Female member spouse in a polygamist household", _RelationshipType.CO_WIFE));
*/
		install(relationshipType("Work together", "Work together", "Work together with a nCoV patient", _RelationshipType.WORKING_TOGETHER_WITH_COVD_CASE));
		install(relationshipType("Traveled together", "Traveled together", "Traveled together with a nCoV patient", _RelationshipType.TRAVELING_TOGETHER_WITH_COVD_CASE));
		install(relationshipType("Live together", "Live together", "Live together with a nCoV patient", _RelationshipType.LIVING_TOGETHER_WITH_COVD_CASE));
		install(relationshipType("Patient", "Health care provider", "Health care associated exposure", _RelationshipType.HEALTHCARE_ASSOCIATED_EXPOSURE_TO_COVD));

		install(visitAttributeType("Source form", "The form whose submission created the visit",
				FormDatatype.class, null, 0, 1, _VisitAttributeType.SOURCE_FORM));

		install(visitType("Outpatient", "Visit where the patient is not admitted to the hospital", _VisitType.OUTPATIENT));
		uninstall(possible(PersonAttributeType.class, "73d34479-2f9e-4de3-a5e6-1f79a17459bb"), "Became patient identifier"); // National ID attribute type
	}
}