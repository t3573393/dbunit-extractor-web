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
<title><spring:message code="database-connection-configs" /></title>
</head>

<body>
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3><spring:message code="database-connection-configs" /></h3>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<!-- <div class="x_title">
						<h2>查询条件</h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div> -->
					<div class="x_content">
						<button type="button" onclick="forward('<c:url value="/to_add_config"/>')" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg"><spring:message code="add" /></button>
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2><spring:message code="search-result" /></h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<table id="my-datatable" class="table table-striped table-bordered">
							<thead>
								<tr>
									<th><spring:message code="config-name" /></th>
									<th><spring:message code="db-type" /></th>
									<th><spring:message code="url" /></th>
									<th><spring:message code="user-name" /></th>
									<th><spring:message code="password" /></th>
									<th><spring:message code="driver-name" /></th>
									<th><spring:message code="operation" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${result}" var="aDto">
									<tr>
										<td><c:out value="${aDto.name}"></c:out></td>
										<td><c:out value="${aDto.dbType}"></c:out></td>
										<td><c:out value="${aDto.url}"></c:out></td>
										<td><c:out value="${aDto.user}"></c:out></td>
										<td><c:out value="${aDto.password}"></c:out></td>
										<td><c:out value="${aDto.driverName}"></c:out></td>
										<td>
										<a href="<c:url value="/to_edit_config"/>?name=<c:out value="${aDto.name}" />"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
										|
										<a onclick="return confirm('<spring:message code="delete-ask" />?')" href="<c:url value="/remove_config"/>?name=<c:out value="${aDto.name}" />"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

		</div>
</body>
<content tag="scripts" >
	<script type="text/javascript">
	function forward(newUrl) {
		location.href = newUrl;
	}
	
	$(document).ready(function() {
        var stringify_aoData = function (aoData) {
            var o = {};
            var modifiers = ['mDataProp_', 'sSearch_', 'iSortCol_', 'bSortable_', 'bRegex_', 'bSearchable_', 'sSortDir_'];
            jQuery.each(aoData, function(idx,obj) {
                if (obj.name) {
                    for (var i=0; i < modifiers.length; i++) {
                        if (obj.name.substring(0, modifiers[i].length) == modifiers[i]) {
                            var index = parseInt(obj.name.substring(modifiers[i].length));
                            var key = 'a' + modifiers[i].substring(0, modifiers[i].length-1);
                            if (!o[key]) {
                                o[key] = [];
                            }
                            console.log('index=' + index);
                            o[key][index] = obj.value;
                            //console.log(key + ".push(" + obj.value + ")");
                            return;
                        }
                    }
                    //console.log(obj.name+"=" + obj.value);
                    o[obj.name] = obj.value;
                }
                else {
                    o[idx] = obj;
                }
            });
            return JSON.stringify(o);
        };

        $('#my-datatable').dataTable( {
            "bProcessing": true,
            "bServerSide": true,
            "pageLength":10,
            "sAjaxSource": "<c:url value="/ajax_list_configs"/>",
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                $.ajax( {
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    type: 'POST',
                    url: sSource,
                    data: stringify_aoData(aoData),
                    success: fnCallback,
                    error : function (e) {
                        alert (e);
                    }
                } );
            }
        } );
        
        $('#list_configs_menu_item').addClass('current-page');
        
    } );
	</script>
</content>
</html>

