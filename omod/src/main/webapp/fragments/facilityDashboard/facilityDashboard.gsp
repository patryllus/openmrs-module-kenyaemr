<%
    ui.includeCss("kenyaemr", "referenceapplication.css", 100)
    ui.includeJavascript("kenyaemr", "highcharts.js")
    ui.includeJavascript("kenyaemr", "highcharts-grouped-categories.js")
%>
<style>
.alignLeft {
    text-align: left;
}
</style>
<script>
    jQuery(function () {
        jQuery('#care_and_treatment_chart').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Cases'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Covid-19 Cases',
                colorByPoint: true,
                data: [{

                    name: 'Total Reported',
                    y:${reportedCasesofCovid19},

                }, {
                    name: 'Total Tested',
                    y: 0,

                }, {
                    name: 'Awaiting Lab Results',
                    y: 0,

                }, {
                    name: 'Negative Cases',
                    y: 0,

                }, {
                    name: 'Confirmed Cases',
                    y: 0,

                }, {
                    name: 'Recovered Cases',
                    y: 0,
                }, {
                    name: 'Deaths',
                    y: 0,
                }]
            }],
        });
    });


    jQuery(function () {
        jQuery('#contact_tracing_tracker').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Contacts'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                data: [{y:${totalContactsListed},color:"#003f5c"},
                    {y:${totalContactsReached},color:"#444e86"},
                    {y:${contactsUnderCovid19Followup},color:"#955196"}]
            }],
            xAxis: {
                categories: [{
                    name: "Total Contacts Listed",
                }, {
                    name: "Contacts Reached",
                }, {
                    name: "Contacts under Followup",
                }
                ]
            },
        });
    });

    jQuery(function () {
        jQuery('#contact_exposure').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Contacts'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                data: [{y:${totalContactsListed},color:"#003f5c"},
                    {y:${exposureFromLivingTogether},color:"#444e86"},
                    {y:${coworkerAssociatedExposure},color:"#955196"},
                    {y:${exposureFromTravelingTogether},color:"#dd5182"},
                    {y:${healthcareAssociatedExposure},color:"#ff6e54"}]
            }],
            xAxis: {
                categories: [{
                    name: "Total Contacts Listed",
                }, {
                    name: "Living together",
                }, {
                    name: "Co-workers",
                }, {
                    name: "Traveled together",
                }, {
                    name: "Healthcare exposure",
                }
                ]
            },
        });
    });

    /*var colors = ['#2f7ed8', '#0d233a', '#8bbc21', '#910000', '#1aadce', '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a'] ;
*/
</script>

<div class="ke-page-content">
    <div style="font-size: 18px; color: #006056; font-style: normal; font-weight: bold">Dashboard</div>

    <div id="program-tabs" class="ke-tabs">
        <div class="ke-tabmenu">
            <div class="ke-tabmenu-item" data-tabid="care_and_treatment">Case Surveillance</div>

            <div class="ke-tabmenu-item" data-tabid="hts">Contact Tracing</div>
        </div>

        <div class="ke-tab" data-tabid="care_and_treatment">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading"></div>

                        </div>

                        <div id="care_and_treatment_chart" style="min-width: 450px; height: 300px; margin: 0 auto"></div>
                    </td>
                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading"></div>

                            <div class="ke-panel-content">

                            </div>
                        </div>

                        <div id="viral_load_tracker" style="min-width: 450px; height: 300px; margin: 0 auto"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="ke-tab" data-tabid="hts">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Contact exposure</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>
                                    <tr>
                                        <th>Listed Contacts</th>
                                        <th>Living together</th>
                                        <th>Co-workers</th>
                                        <th>Traveled together</th>
                                        <th>Healthcare exposure</th>
                                    </tr>

                                    <tr>
                                        <td>${totalContactsListed}</td>
                                        <td>${exposureFromLivingTogether}</td>
                                        <td>${coworkerAssociatedExposure}</td>
                                        <td>${exposureFromTravelingTogether}</td>
                                        <td>${healthcareAssociatedExposure}</td>
                                    </tr>

                                </table>
                            </div>
                        </div>
                        <div id="contact_exposure" style="min-width: 700px; height: 350px; margin: 0 auto"></div>
                    </td>
                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Contact Tracing</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>
                                    <tr>
                                        <th>Listed Contacts</th>
                                        <th>Contacts Reached</th>
                                        <th>Contacts under followup</th>
                                    </tr>

                                    <tr>
                                        <td>${totalContactsListed}</td>
                                        <td>${totalContactsReached}</td>
                                        <td>${contactsUnderCovid19Followup}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div id="contact_tracing_tracker" style="min-width: 700px; height: 350px; margin: 0 auto"></div>
                    </td>
                </tr>
            </table>
        </div>

        <br/>
       <br/>
    </div>
   </div>
