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
</head>

<body>
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3><spring:message code="export-detail" /></h3>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<button type="button" class="btn btn-round btn-primary btn-lg" onclick="exportToFile()"><spring:message code="export-file" /></button>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
					  <!-- <pre lang="xml"> -->
					   <!--  </pre> -->
					    <xmp>
					    ${result}
					     </xmp>
					</div>
				</div>
			</div>
		</div>
</body>
<content tag="scripts" >
	<script type="text/javascript">
		function newInputField(name, value) {
	    	return $("<input>").attr("type", "hidden").attr("name", name).val(value);
	    }
	
	    function exportToFile() {
	    	var newForm = $("<form action='<c:url value="/extract_dataset_file" />' target='_blank'></form>");
	    	newForm.append(newInputField("dbName", "${searchDto.dbName}"));
	    	newForm.append(newInputField("sqlQuery", "${searchDto.sqlQuery}"));
	    	
	    	newForm.appendTo('body').submit();
	    	newForm.remove();
	    }
	    
	   $(function(){
		   $('#list_dataset_menu_item').addClass('current-page');
		   $BODY.toggleClass('nav-md nav-sm');
	   });
	</script>
</content>
</html>

