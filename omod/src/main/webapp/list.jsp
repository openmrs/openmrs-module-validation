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
    <c:forEach items="${objectTypes}" var="object" varStatus="objects">
        <input type="checkbox" id="${object}" value="${object}">${object}<br>
    </c:forEach>
</div>

<div id="typesubmitform">
    <form method="post" action="validate.form">
      <input type="hidden" name="type" id="type" value=""/>
      <input type="button" name="select_button" id="select_button"  style="width:150px" value="Select Types"/>
      <input type="button" name="show_button" id="show_button" style="width:150px" value="Show Report"/>
      <input type="button" name="stop_button" id="stop_button" style="width:150px" value="Stop Validation"/>
      <input type="submit" name="validate_button" id="validate_button" style="width:150px" value="Validate Types" onclick="getCombinedTypeList()"/>
    </form>
</div>

<div id="selectedTypes" class="selectedTypes">

</div>
</body>

<script>
    var values = new Array();
    var dialogOpen = false;
    var typeChanged = false;
    jQuery("div#dialog").dialog({
        autoOpen:false,
        height:600,
        width:800,
        buttons:{
            "Done":function () {
                jQuery(this).find('input[type="checkbox"]').each(function () {
                    if (jQuery(this).is(':checked')) {
                        values.push(jQuery(this).val());
                    }

                });
                dialogOpen = true;
                jQuery(this).dialog("close");

            },
            Cancel:function () {
                jQuery(this).dialog("close");
            }
        },
        close:function (event, ui) {
            if (dialogOpen) {
                var breaktag = '<br>';
                var selecttag = '<h3>Selected types to validate ...</h3>';
                jQuery('.selectedTypes').empty();
                jQuery("div#selectedTypes").append(breaktag).append(selecttag);
                for (var i = 0; i < values.length; i++) {
                    var item = "<p>" + values[i] + "</p>";
                       jQuery("div#selectedTypes").append(i+1 + ".       ").append(values[i]);
                       jQuery("div#selectedTypes").append(breaktag);
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
        var combinesTypeString = "";
        for (var i = 0; i < values.length; i++) {
            combinesTypeString =combinesTypeString.concat(values[i] + ',');
        }
        document.getElementById('type').value = combinesTypeString;

    }

    /*function removeType(typeToRemove){
       typeChanged = true;
       var contentString = ":contains(" + typeToRemove + ")";
        var index = values.indexOf(typeToRemove);
        if(index!=-1){
           values.splice(index, 1);
        }
        jQuery("p").remove(contentString);

    }*/


</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>