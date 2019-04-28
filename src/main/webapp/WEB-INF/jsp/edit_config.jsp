<%@ page contentType="text/html;charset=UTF-8" language="java"
	session="false"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
<title><spring:message code="edit-db-config" /></title>
<content tag="head"> 
<!-- PNotify -->
<link href="${contextPath}/static/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
<link href="${contextPath}/static/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
<link href="${contextPath}/static/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">
</content>
</head>
<body>
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2><spring:message code="edit-db-config" /></h2>
			</div>
		</div>
		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2><spring:message code="database-connection-configs" /></h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br>
                    <form id="dbConfig-form" name="DBConfigDTO" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="" action="<c:url value="/edit_config"/>">
                      <input type="hidden" value="edit" name="operation" id="operation"/>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name"><spring:message code="config-name" /></span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text" disabled="disabled" value="${DBConfigDTO.name}" name="name_display" id="name_display" class="form-control col-md-7 col-xs-12" data-parsley-id="5" />
                          <input type="hidden" value="${DBConfigDTO.name}" name="name" id="name"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="dbType"><spring:message code="db-type" /><span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <select id="dbType" class="form-control" name="dbType" required="required">
                            <option value=""><spring:message code="option-empty" /></option>
                            <c:forEach items="${dbTypes}" var="aDBTypes">
                            <option value="${aDBTypes}" <c:if test="${DBConfigDTO.dbType.toString() == aDBTypes.toString()}"> selected="selected" </c:if>>${aDBTypes}</option>
							</c:forEach>
                          </select>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="url" class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code="url" /><span class="required">*</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="url" value="${DBConfigDTO.url}" class="form-control col-md-7 col-xs-12" type="text" name="url" required="required" data-parsley-id="6" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="user"  class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code="user-name" /><span class="required">*</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="user" value="${DBConfigDTO.user}" class="form-control col-md-7 col-xs-12" type="text" name="user" required="required" data-parsley-id="7" />
                        </div>
                       </div>
                      <div class="form-group">
                        <label for="password" class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code="password" /><span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="password" value="${DBConfigDTO.password}" class="form-control col-md-7 col-xs-12" type="text" name="password" required="required" data-parsley-id="8" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="driverName" class="control-label col-md-3 col-sm-3 col-xs-12"><spring:message code="driver-name" /><span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="driverName" value="${DBConfigDTO.driverName}" class="form-control col-md-7 col-xs-12" type="text" name="driverName" required="required" data-parsley-id="9" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
						  <button class="btn btn-primary" type="reset"><spring:message code="reset" /></button>
                          <button type="submit" class="btn btn-success"><spring:message code="submit" /></button>
                          <button type="button" onclick="testDBConfig()" class="btn btn-danger"><spring:message code="test" /></button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
		</div>
</body>
<content tag="scripts"> 
<!-- PNotify -->
<script src="${contextPath}/static/vendors/pnotify/dist/pnotify.js"></script>
<script src="${contextPath}/static/vendors/pnotify/dist/pnotify.buttons.js"></script>
<script src="${contextPath}/static/vendors/pnotify/dist/pnotify.nonblock.js"></script>
<!-- Parsley -->
<script src="${contextPath}/static/vendors/parsleyjs/dist/parsley.min.js"></script>
<c:if test="${pageContext.response.locale == 'zh_CN'}">
<script src="${contextPath}/static/vendors/parsleyjs/dist/i18n/zh_cn.js"></script>
</c:if>
<script type="text/javascript">
<c:if test="${pageContext.response.locale == 'zh_CN'}">
	window.ParsleyValidator.setLocale('zh-cn');
</c:if>
	$(function() {
		$('#list_configs_menu_item').addClass('current-page');
		<c:if test="${opResult != null}">
		if(${opResult} == false) {
			new PNotify({
		        title: '<spring:message code="fail" />',
		        text: '${msg}',
		        type: 'error',
		        hide: false,
		        styling: 'bootstrap3'
		    });
		} else {
			new PNotify({
		        title: '<spring:message code="success" />',
		        text: '${msg}',
		        type: 'success',
		        hide: false,
		        styling: 'bootstrap3'
		    });
		}
		</c:if>
	});
	
	function testDBConfig() {
		$('#dbConfig-form').attr('action',"<c:url value="/test_config"/>");
		$('#dbConfig-form').submit();
	}
</script> 
</content>
</html>

