<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h2>
    <spring:message code="validation.title"/>
</h2>
<br/>
<script type="text/javascript" src=../../scripts/jquery/jquery.min.js></script>
<script type="text/javascript" src=../../scripts/jquery-ui/js/jquery-ui.custom.min.js></script>

<body>

<div id="progressStatus">
    <table style="border: none">
        <tr>
            <td><h3><spring:message code="validation.is.starting"/></h3></td>
        </tr>
    </table>
</div>


<div id="dialog" title="Select Types to Validate">
    <table>
        <tr>
            <td colspan="3">
                <input type="checkbox" id="all_data_cbs" value=""><spring:message code="validation.all.data"/>
            </td>
        </tr>
        <tr>
            <c:forEach items="${classNamesMap}" var="className" varStatus="loop">
                <c:if test="${not loop.first and loop.index % 3 == 0}">
                    </tr><tr>
                </c:if>
        <td>
            <input type="checkbox" name="type" id="type" value="${className.value}">${className.key}
        </td>
        </c:forEach>
        </tr>
    </table>
</div>

<div id="typesubmitform">
    <form method="post" action="validate.form">
      <input type="hidden" name="types" id="types" value=""/>
      <button type="button" name="select_button" id="select_button"  style="width:150px"><spring:message code="validation.select.types"/></button>
      <button type="button" name="show_button" id="show_button" style="width:150px"><spring:message code="validation.show.report"/></button>
      <button type="submit" name="stop_button" id="stop_button" style="width:150px"><spring:message code="validation.stop.validation"/></button>
      <button type="submit" name="validate_button" id="validate_button" style="width:150px" onclick="getCombinedTypeList()"><spring:message code="validation.validate.types"/></button>
    </form>
</div>

<div id="selectedTypes" class="selectedTypes">

</div>
</body>

<script>
    jQuery("#progressStatus").hide();
    var values = new Array();
    var checkDoneButtonClicked = false;
    jQuery("div#dialog").dialog({
        autoOpen:false,
        height:600,
        width:600,
        buttons:{
            "Done":function () {
                if(jQuery('#all_data_cbs').is(':checked')) {
                    jQuery(this).find('input[type="checkbox"]').each(function () {
                        if (jQuery(this).val() != "") {
                            values.push(jQuery(this).val());
                        }

                    });
                } else{
                    jQuery(this).find('input[type="checkbox"]').each(function () {
                        if (jQuery(this).is(':checked')) {
                            values.push(jQuery(this).val());
                        }

                    });
                }
                checkDoneButtonClicked = true;
                jQuery(this).dialog("close");

            },
            Cancel:function () {
                checkDoneButtonClicked = false;
                jQuery(this).dialog("close");

            }
        },
        close:function (event, ui) {
            if (checkDoneButtonClicked) {
                var breaktag = '<br>';
                var selecttag = '<h3><spring:message code="validation.selected.types.to.validate"/></h3>';
                jQuery('.selectedTypes').empty();
                jQuery("div#selectedTypes").append(breaktag).append(selecttag);
                for (var i = 0; i < values.length; i++) {
                    var item = "<li style=\"text-indent:5em;\">" + values[i] + "</li>";
                    jQuery('div#selectedTypes').append(item);
                }
            }
        }
    });

    jQuery("#select_button").click(function (event) {
       /* we do not remove the previously selected items from the dialog window, hence types can be selected/removed
        *  from the list till it is sent to validation. once a single validate cycle is completed user will be redirected
        *  to new dialog window. we only empty the values array each time. */
        values.length=0;
        jQuery("div#dialog").dialog("open");
    });

    function getCombinedTypeList(){
        jQuery("#progressStatus").show();
        var combinedTypeString = "";
        for (var i = 0; i < values.length; i++) {
            combinedTypeString =combinedTypeString.concat(values[i] + ',');
        }
        document.getElementById('types').value = combinedTypeString;

    }

</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>