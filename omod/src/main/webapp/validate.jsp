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

    <script type="text/javascript">

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
        <div id="classTableHeader">Errors sorted on Class Type</div>
        <table id="classTable">
            <c:forEach items="${allErrors}" var="error">
                <tr>
                    <td>${error.classname}</td>
                    <%--<td>${error.errors}</td>--%>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div id="errorView" style="display:none;">
        <div id="errorTableHeader">Errors sorted on Error Type</div>
        <table id="errorTable">
            <c:forEach items="${allErrors}" var="error">
                <tr>
                    <%--<td>${error.value}</td>--%>
                    <td>${error.classname}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>