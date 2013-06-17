<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>
    <spring:message code="validation.title"/>
</h2>

<%--<script type="text/javascript" src = WEB-INF/scripts/jquery/></script>
<script type="text/javascript" src = WEB-INF/scripts/jquery/jquery-ui.custom.min.js></script>--%>

<div id="dialog" title="Select Types to Validate">
    <c:forEach items="${objectTypes}" var="object" varStatus="objects">
      <input type="checkbox" id="${object}" value="${object}">${object}<br>
    </c:forEach>
</div>

<div>
    <input type="button" name="select_button" id="select_button" value="Select Types" />
    <input type="button" name="validate_button" id="validate_button" value="Validate"/>
    <input type="button" name="show_button" id="show_button" value="Show Report"/>
    <input type="button" name="stop_button" id="stop_button" value="Stop"/>
</div>

<script>
    jQuery("div#dialog").dialog ({
        autoOpen : false,
        buttons: {
            "Done": function() {
                var values =[];
                jQuery(this).find('input[type="checkbox"]').each(function(){
                    if(jQuery(this).is(':checked')) {
                        values[jQuery(this).prop("name")] = jQuery(this).val();
                        alert(jQuery(this).val());
                    }

                });
                jQuery( this ).dialog( "close" );
            },
            Cancel: function() {
                jQuery( this ).dialog( "close" );
            }
        }
    });

    jQuery("#select_button").click (function (event)    // Open button Treatment
    {
        jQuery("div#dialog").dialog ("open");
    });
</script>














<%@ include file="/WEB-INF/template/footer.jsp"%>