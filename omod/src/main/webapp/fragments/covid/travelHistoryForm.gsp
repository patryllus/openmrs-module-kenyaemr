<%
	def appId = "kenyaemr.medicalEncounter"
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">

		<% if(encounters) { %>
		<table>
			<tr>

				<th>Arrival</th>
				<th>Mode</th>
				<th>Destination</th>
				<th>Fever</th>
				<th>Cough</th>
				<th>Difficulty<br/> breathing</th>
				<th> </th>
			</tr>

		<% encounters.each { %>
			<tr>
				<td>${it.arrivalDate}</td>
				<td>${it.transportMode}</td>
				<td>${it.destination}</td>
				<td>${it.fever}</td>
				<td>${it.cough}</td>
				<td>${it.difficultyBreathing}</td>
				<td><a href="${ui.pageLink("kenyaemr", "editForm", [patientId: currentPatient.patientId, appId: appId, encounterId: it.encId, returnUrl: ui.thisUrl()])}"> Edit </a> </td>
			</tr>
		<% } %>
		</table>
		<% } else { %>

		No information
		<% } %>
	</div>
</div>
