/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.dmi;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.NextAppointmentDateWithComplaintsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.VisitDateWithComplaintsDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates a NextAppointmentDateDataDefinition
 */
@Handler(supports= NextAppointmentDateWithComplaintsDataDefinition.class, order=50)
public class NextAppointmentDateWithComplaintsDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select a.patient_id, a.next_appointment_date\n" +
			"        from (select\n" +
			"                  pp.patient_id,\n" +
			"                  date(mid(max(concat(pp.date_created,pp.start_date_time, '' )),20)) as next_appointment_date\n" +
			"              from openmrs.patient_appointment pp where  date(pp.date_created) <= date(:endDate)\n" +
			"              group by pp.patient_id,pp.start_date_time) a\n" +
			"              inner join kenyaemr_etl.etl_allergy_chronic_illness c on a.patient_id=c.patient_id\n" +
			"                where date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
			"group by patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
