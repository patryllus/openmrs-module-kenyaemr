<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
    ui.includeCss("kenyaemr", "referenceapplication.css", 100)
    ui.includeCss("kenyaemrorderentry", "font-awesome.css")
    ui.includeCss("kenyaemrorderentry", "font-awesome.min.css")
    ui.includeCss("kenyaemrorderentry", "font-awesome.css.map")
    ui.includeCss("kenyaemrorderentry", "../fontawesome-webfont.svg")
    def triageUuid ="37f6bd8d-586a-4169-95fa-5781f987fe62";
    def addTriageFormLink = ui.pageLink("kenyaemr", "enterForm", [patientId: currentPatient.patientId, formUuid: triageUuid, appId:currentApp.id, returnUrl: ui.thisUrl()])

    def travelHistoryFormUUID ="87513b50-6ced-11ea-bc55-0242ac130003";
    def addTravelHistoryFormLink = ui.pageLink("kenyaemr", "enterForm", [patientId: currentPatient.patientId, formUuid: travelHistoryFormUUID, appId:currentApp.id, returnUrl: ui.thisUrl()])

    def contactTracingUuid ="37ef8f3c-6cd2-11ea-bc55-0242ac130003";
    def addTracingFormLink = ui.pageLink("kenyaemr", "enterForm", [patientId: currentPatient.patientId, formUuid: contactTracingUuid, appId:currentApp.id, returnUrl: ui.thisUrl()])


%>

<script type="text/javascript">


    jq(function () {

    });


</script>
<style>
.dashboard .info-header {
    border-bottom: 6px solid #7f7b72 !important;
}
.dashboard .info-header i {
    font-size: 1.3em;
    color: #7f7b72 !important;
}
.dashboard .info-header h3 {
    display: inline-block;
    font-family: "OpenSansBold";
    font-size: 1em;
    margin: 0;
}
</style>




<div id="content" class="container">

    <div class="clear"></div>

    <div class="container">
        <div class="dashboard clear">
            <div class="info-container column">

                <div class="info-section">
                    <div class="info-header">
                        <i class="icon-diagnosis"></i>

                        <h3>Registration Info</h3>
                    </div>

                    <div class="info-body">
                        ${ ui.includeFragment("kenyaemr", "patient/patientSummary", [ patient: currentPatient ]) }
                    </div>
                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-users fa-2x"></i>

                        <h3>Travel History</h3>
                        <i class="fa fa-plus-square right" style="color: steelblue" title="Add History"
                           onclick="location.href = '${addTravelHistoryFormLink}'"></i>
                    </div>
                    <div class="info-body">
                        ${ ui.includeFragment("kenyaemr", "covid/travelHistoryForm", [ patient: currentPatient ]) }
                    </div>


                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-file-o"></i>

                        <h3>Enrollment status</h3>
                    </div>

                    ${ ui.includeFragment("kenyaemr", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-users fa-2x"></i>

                        <h3>Contacts under followup</h3>
                    </div>
                    <div class="info-body">

                    ${ ui.includeFragment("kenyaemr", "patient/patientRelationships", [ patient: currentPatient ]) }
                    </div>
                </div>

            </div>

            <div class="info-container column">
                <div class="info-section">
                    <div class="info-header">
                        <i class="icon-stethoscope"></i>

                        <h3>Tracing/Followup</h3>
                        <i class="fa fa-plus-square right" style="color: steelblue" title="Add Trace"
                           onclick="location.href = '${addTracingFormLink}'"></i>
                    </div>

                    <div class="info-body">
                        ${ ui.includeFragment("kenyaemr", "covid/contactFollowupForm", [ patient: currentPatient ]) }
                    </div>
                </div>

            </div>
            ${ui.includeFragment("kenyaemr", "patient/surveillancePanel", [visit: visit])}

        </div>
    </div>
</div>