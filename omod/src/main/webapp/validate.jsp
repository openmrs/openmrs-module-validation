<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<head>
    <openmrs:htmlInclude file="/moduleResources/validation/validation.css" />

    <h2>
        <spring:message code="validation.report" />
    </h2>
    <script type="text/javascript" src=../../scripts/jquery/jquery.min.js></script>
    <script type="text/javascript" src=../../scripts/jquery-ui/js/jquery-ui.custom.min.js></script>

    <p><a href="list.form">Back to list</a></p>
</head>

<body>

<div id="errorTabs" >
    <ul>
        <li><a id="classViewTab" href="#" onclick="return changeTab(this);" hidefocus="hidefocus"><spring:message code="validation.class.view" /></a></li>
        <li><a id="errorViewTab" href="#" onclick="return changeTab(this);" hidefocus="hidefocus"><spring:message code="validation.error.view" /></a></li>

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
                            <td width="10%"><a href="#">Fix</a></td>
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
                    <tr>
                        <td>${errorEntry.key}</td>
                        <td></td>
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

    //initTabs
    $j(document).ready(function() {
        var c = getTabCookie();
        if (c == null || (!document.getElementById(c))) {
            var tabs = document.getElementById("errorTabs").getElementsByTagName("a");
            if (tabs.length && tabs[0].id)
                c = tabs[0].id;
        }
        changeTab(c);
    });

    function changeTab(tabObj) {

        if (!document.getElementById || !document.createTextNode) {return;}

        if (typeof tabObj == "string")
            tabObj = document.getElementById(tabObj);

        if (tabObj) {
            var tabs = tabObj.parentNode.parentNode.getElementsByTagName('a');
            for (var i=0; i<tabs.length; i++) {
                if (tabs[i].className.indexOf('current') != -1) {
                    manipulateClass('remove', tabs[i], 'current');
                }
                var divId = tabs[i].id.substring(0, tabs[i].id.lastIndexOf("Tab"));
                var divObj = document.getElementById(divId);
                if (divObj) {
                    if (tabs[i].id == tabObj.id)
                        divObj.style.display = "";
                    else
                        divObj.style.display = "none";
                }
            }
            addClass(tabObj, 'current');

            setTabCookie(tabObj.id);
        }
        return false;
    }

    function setTabCookie(tabType) {
        document.cookie = "dashboardTab-" + userId + "="+escape(tabType);
    }

</script>

<script type="text/javascript">
    jQuery("#errorViewTab").click(function(){
        jQuery("#errorView").show();
    });
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>