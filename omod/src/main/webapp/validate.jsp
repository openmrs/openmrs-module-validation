<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<head>
    <openmrs:htmlInclude file="/moduleResources/validation/validation.css" />

    <h2>
        <spring:message code="validation.report"/>
    </h2>
    <script type="text/javascript" src=../../scripts/jquery/jquery.min.js></script>
    <script type="text/javascript" src=../../scripts/jquery-ui/js/jquery-ui.custom.min.js></script>

    <h4><a href="list.form"><spring:message code="validation.back.to.home"/></a></h4>
</head>

<body>

<div id="errorTabs" >
    <ul>
        <li><a id="classViewTab" href="#"  hidefocus="hidefocus"><spring:message code="validation.class.view" /></a></li>
        <li><a id="errorViewTab" href="#"  hidefocus="hidefocus"><spring:message code="validation.error.view" /></a></li>

    </ul>
</div>

<div id="errorSections">
    <div id="classView">
        <table id="classTable">
            <c:forEach items="${allErrorsByClass}" var="errorClass">
                <tr id="classNameRow">
                    <td>${errorClass.classname}</td>
                </tr>
                <c:forEach items="${errorClass.errors}" var="mapEntry">
                        <tr>
                            <td>${mapEntry.key}</td>
                            <td>${mapEntry.value}</td>
                            <c:if test="${errorClass.classname == 'Concept'}">
                                <c:set var="conceptIds" value="${fn:split(mapEntry.key, ':')}" />
                                <td><button type="button" name="fix_button" id="fix_button_${conceptIds[0]}"><spring:message code="validation.fix"/></button></td>
                                <div id="dialog_${conceptIds[0]}">
                                    <iframe src ="/openmrs/dictionary/concept.form?conceptId=${conceptIds[0]}" height="800" width="1450">
                                        <p>Your browser does not support Iframes.</p>
                                    </iframe>
                                </div>
                            </c:if>
                        </tr>
                </c:forEach>
            </c:forEach>
        </table>
    </div>
    <div id="errorView">
        <table id="errorTable">
            <c:forEach items="${allErrorsByError}" var="error">
                <tr id="errorNameRow">
                    <td>${error.errorname}</td>
                </tr>
                <c:forEach items="${error.errorsDetail}" var="errorEntry">
                    <tr  id="errorKeyRow">
                        <td>${errorEntry.key}</td>
                    </tr>
                        <c:forEach items="${errorEntry.value}" var="errorDes">
                            <tr>
                                <td></td>
                                <td>${errorDes}</td>
                            </tr>
                        </c:forEach>
                </c:forEach>
            </c:forEach>
        </table>
    </div>
</div>


</body>


<script type="text/javascript">
    jQuery("#errorView").hide();

    /* This section does the toggling between ClassView and ErrorView tag*/
    jQuery("#errorViewTab").click(function(){
        jQuery("#classView").hide();
        jQuery("#errorView").show();

    });
    jQuery("#classViewTab").click(function(){
        jQuery("#errorView").hide();
        jQuery("#classView").show();
    });

    /* This dialog window is used to keep the edit concept page for each concept that has errors*/
    jQuery("div[id^='dialog_']").dialog({
        autoOpen:false,
        height:950,
        width:1500,
        buttons:{
            "Done":function () {
                jQuery(this).dialog("close");

            },
            Cancel:function () {
                jQuery(this).dialog("close");

            }
        }
    });

    jQuery("td button[id^='fix_button']").click(function (event) {
        var idArr=jQuery(this).attr('id').split('fix_button');
        var dialogWindowId = '#dialog'+ idArr[1];
        jQuery(dialogWindowId).dialog("open");

    });

</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>