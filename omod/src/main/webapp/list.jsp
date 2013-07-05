<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h2>
    <spring:message code="validation.title"/>
</h2>
<br>
<script type="text/javascript" src=../../scripts/jquery/jquery.min.js></script>
<script type="text/javascript" src=../../scripts/jquery-ui/js/jquery-ui.custom.min.js></script>

<body>
<div id="dialog" title="Select Types to Validate">
    <table>
        <tr>
            <td colspan="3">
                <input type="checkbox" id="all_data_cbs" value=""> All Data
            </td>
        </tr>
        <c:forEach items="${objectTuples}" var="objectTuple" >
            <tr>
                <td>
                    <c:if test="${objectTuple.first != null}">
                        <input type="checkbox" name="type" id="${objectTuple.first.fullClassName}" value="${objectTuple.first.fullClassName}">${objectTuple.first.simpleClassName}
                    </c:if>
                </td>
                <td>
                    <c:if test="${objectTuple.second != null}">
                        <input type="checkbox" name="type" id="${objectTuple.second.fullClassName}" value="${objectTuple.second.fullClassName}">${objectTuple.second.simpleClassName}
                    </c:if>
                </td>
                <td>
                    <c:if test="${objectTuple.third != null}">
                        <input type="checkbox" name="type" id="${objectTuple.third.fullClassName}" value="${objectTuple.third.fullClassName}">${objectTuple.third.simpleClassName}
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

<div id="typesubmitform">
    <form method="post" action="validate.form">
      <input type="hidden" name="types" id="types" value=""/>
      <input type="button" name="select_button" id="select_button"  style="width:150px" value="Select Types"/>
      <input type="button" name="show_button" id="show_button" style="width:150px" value="Show Report"/>
      <input type="submit" name="stop_button" id="stop_button" style="width:150px" value="Stop Validation"/>
      <input type="submit" name="validate_button" id="validate_button" style="width:150px" value="Validate Types" onclick="getCombinedTypeList()"/>
    </form>
</div>

<div id="selectedTypes" class="selectedTypes">

</div>
</body>

<script>
    var values = new Array();
    var checkDoneButtonClicked = false;
    var typeChanged = false;
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
                var selecttag = '<h3>Selected Types to Validate...</h3>';
                jQuery('.selectedTypes').empty();
                jQuery("div#selectedTypes").append(breaktag).append(selecttag);
                for (var i = 0; i < values.length; i++) {
                    var item = "<li style=\"text-indent:5em;\">" + values[i] + "</li>";
                    jQuery('div#selectedTypes').append(item);
                }
                dialogOpen = false;
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
        typeChanged = false;
        var combinedTypeString = "";
        for (var i = 0; i < values.length; i++) {
            combinedTypeString =combinedTypeString.concat(values[i] + ',');
        }
        document.getElementById('types').value = combinedTypeString;

    }

</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>