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
<title><spring:message code="database-export" /></title>
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
				<h3><spring:message code="database-export" /></h3>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2><spring:message code="search-condition" /></h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<br />
						<form id="search-dataset-form" data-parsley-validate
							class="form-horizontal form-label-left"
							action="<c:url value="/search_dataset" />" name="SearchDTO">
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="tableName"><spring:message code="config-name" /></label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<select id="dbName" class="form-control" name="dbName"
										required="required">
										<option value=""><spring:message code="option-empty" /></option>
										<c:forEach items="${configs}" var="aConfig">
											<option value="${aConfig.name}"
												<c:if test="${searchDto.dbName == aConfig.name}"> selected="selected" </c:if>>${aConfig.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="tableName"><spring:message code="search-sql" /> </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<textarea id="sqlQuery" required="required" id="sqlQuery"
										class="form-control" name="sqlQuery" rows="5"
										placeholder="<spring:message code="input-multiply" />">${searchDto.sqlQuery}</textarea>
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success"><spring:message code="search" /></button>
									<button class="btn btn-primary" type="reset"><spring:message code="reset" /></button>
									<button class="btn btn-primary" type="button" onclick="exportDataset()"><spring:message code="export" /></button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2><spring:message code="search-result" /><span class="label label-danger">(<spring:message code="max-100-rows" />)</span></h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
					    <c:if test="${fn:length(result.tables) > 1}">
					    <div class="col-xs-2">
					       <!-- required for floating -->
					       <!-- Nav tabs -->
					       <ul id="myTab" class="nav nav-tabs tabs-left" role="tablist">
								<c:forEach items="${result.tables}" var="aDto"
									varStatus="status">
									<li role="presentation"
										<c:if test="${status.first}">class="active"</c:if>><a
										href="#tab_content${status.index}"
										id="profile-tab${status.index}" role="tab" data-toggle="tab"
										aria-expanded="false">${aDto.tableName}</a></li>
								</c:forEach>
							</ul>
					    </div>
					    </c:if>
					    <div <c:if test="${fn:length(result.tables) > 1}">class="col-xs-10"</c:if>
							 <c:if test="${fn:length(result.tables) == 1}">class="col-xs-12"</c:if>>
                        <!-- Tab panes -->
                        <div class="tab-content" role="tabpanel" data-example-id="togglable-tabs">
							<c:forEach items="${result.tables}" var="aDto"
								varStatus="status">
								<div role="tabpanel"
									class="tab-pane <c:if test="${status.first}">active in</c:if>"
									id="tab_content${status.index}" aria-labelledby="profile-tab">
									<table id="my-datatable${status.index}"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<c:forEach items="${aDto.columnNames}" var="aColumn">
													<th>${aColumn}</th>
												</c:forEach>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${aDto.rows}" var="aRow">
												<tr>
													<c:forEach items="${aRow.columns}" var="aColumnValue">
														<td><c:out value="${aColumnValue}"></c:out></td>
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:forEach>
						</div>
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
    
    function newInputField(name, value) {
    	return $("<input>").attr("type", "hidden").attr("name", name).val(value);
    }

    function exportDataset() {
    	if(!$("#search-dataset-form").parsley().validate())
    		return ;
    	
    	var newForm = $("<form action='<c:url value="/extract_dataset" />' target='_blank'></form>");
    	newForm.append(newInputField("dbName", $("#dbName").val()));
    	newForm.append(newInputField("sqlQuery", $("#sqlQuery").val()));
    	
    	newForm.appendTo('body').submit();
    	newForm.remove();
    }

	$(function() {
		<c:forEach items="${result.tables}" var="aDto"
			varStatus="status">
		$('#my-datatable${status.index}').dataTable({
			'paginng' : true,
			'pageLength' : 50,
			'bSort' : false,
		});
		</c:forEach>
		$('#list_dataset_menu_item').addClass('current-page');
		<c:if test="${opResult != null}">
		if(${opResult} == false) {
			new PNotify({
		        title: '<spring:message code="result" />',
		        text: '${msg}',
		        type: 'info',
		        hide: false,
		        styling: 'bootstrap3'
		    });
		}
		</c:if>
	});
</script> 
</content>
</html>

