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

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.program;


/**
 * IPT metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class COVIDMetadata extends AbstractMetadataBundle {




	public static final class _Program {
		public static final String COVID = "e7ee7548-6958-4361-bed9-ee2614423947";
	}

	public static final class _Concept {

		public static final String COVID = "113021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}

	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {


		install(program("COVID-19 Case Investigation", "Isoniazid Preventive Therapy (IPT)", _Concept.COVID, _Program.COVID));
	}
}