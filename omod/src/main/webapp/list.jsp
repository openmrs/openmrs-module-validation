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

<div id="buttonlist">
    <input type="button" name="select_button" id="select_button" value="Select Types"/>
    <input type="button" name="validate_button" id="validate_button" value="Validate"/>
    <input type="button" name="show_button" id="show_button" value="Show Report"/>
    <input type="button" name="stop_button" id="stop_button" value="Stop"/>
</div>
</body>

<script>
    var values = new Array();
    var dialogOpen = false;
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
                jQuery("div#buttonlist").append(breaktag).append(breaktag);
                for (var i = 0; i < values.length; i++) {
                    var item = "<p>" + values[i] + "</p>";
                    jQuery("div#buttonlist").append(item);
                }
                dialogOpen = false;
            }

        }
    });

    jQuery("#select_button").click(function (event) {
        jQuery("div#dialog").dialog("open");
    });

</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>