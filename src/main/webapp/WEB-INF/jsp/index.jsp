<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" href="${contextPath}/static/production/images/favicon.ico" type="image/ico" />

    <title><sitemesh:write property="title" default="dbunit-extractor-web"/></title>

    <!-- Bootstrap -->
    <link href="${contextPath}/static/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${contextPath}/static/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${contextPath}/static/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="${contextPath}/static/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
	
    <!-- bootstrap-progressbar -->
    <link href="${contextPath}/static/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="${contextPath}/static/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="${contextPath}/static/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${contextPath}/static/build/css/custom.min.css" rel="stylesheet">
    <sitemesh:write property="page.head"/>
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="<c:url value="/"/>" class="site_title"><i class="fa fa-paw"></i> <span>dbunit-extractor</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info 
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
              </div>
            </div> -->
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3><spring:message code="normal-menu" /></h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-table"></i><spring:message code="database" /><span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li id="list_configs_menu_item"><a href="<c:url value="/list_configs"/>"><spring:message code="database-connection-configs" /></a></li>
                      <li id="list_dataset_menu_item"><a href="<c:url value="/list_dataset"/>"><spring:message code="database-export" /></a></li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons 
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div> -->
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>
              
              <div class="nav toggle" style="width: 300px;">
              <form id="changeSessionLanguage-form" action="<c:url value="/change_session_language"/>" method="get">
	              <select id="lang" name="lang">
	                  <option value=""></option>
	                  <option value="en">English</option>
	                  <option value="zh">中文</option>
	              </select>
	              <button type="button" onclick="$('#changeSessionLanguage-form').submit()"><spring:message code="change" /></button>
              </form>
              </div>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->


        <!-- page content -->
        
        <div class="right_col" role="main">
			<sitemesh:write property="body"/>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="${contextPath}/static/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="${contextPath}/static/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="${contextPath}/static/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="${contextPath}/static/vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->
    <script src="${contextPath}/static/vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- gauge.js -->
    <script src="${contextPath}/static/vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="${contextPath}/static/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="${contextPath}/static/vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="${contextPath}/static/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="${contextPath}/static/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="${contextPath}/static/vendors/jszip/dist/jszip.min.js"></script>
    <script src="${contextPath}/static/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="${contextPath}/static/vendors/pdfmake/build/vfs_fonts.js"></script>
    
    <script type="text/javascript">
        <c:if test="${pageContext.response.locale == 'zh_CN'}">
    	$.extend(true, $.fn.dataTable.defaults, {
    		"oLanguage" : {
    			"oPaginate" : {
    				"sFirst": "首页",
    				"sPrevious": "上页",
    				"sNext": "下页",
    				"sLast": "末页"
    			},
    			"sEmptyTable":"找不到数据",
    			"sInfo":"共 _TOTAL_项,显示第_START_至_END_项结果",
    			"sInfoEmpty":"共 0条,显示第0至0项结果",
    			"sInfoFiltered":"(由 _MAX_ 项结果过滤)",
    			"sLengthMenu":"显示 _MENU_项",
    			"sLoadingRecords":"正在加载...",
    			"sProcessing":"正在处理...",
    			"sSearch":"查找:",
    			"sZeroRecords":"找不到数据"
    		}
    	});
    	</c:if>
    </script>
    
    <!-- Skycons -->
    <script src="${contextPath}/static/vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="${contextPath}/static/vendors/Flot/jquery.flot.js"></script>
    <script src="${contextPath}/static/vendors/Flot/jquery.flot.pie.js"></script>
    <script src="${contextPath}/static/vendors/Flot/jquery.flot.time.js"></script>
    <script src="${contextPath}/static/vendors/Flot/jquery.flot.stack.js"></script>
    <script src="${contextPath}/static/vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="${contextPath}/static/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="${contextPath}/static/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="${contextPath}/static/vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="${contextPath}/static/vendors/DateJS/build/date.js"></script>
    <!-- JQVMap -->
    <script src="${contextPath}/static/vendors/jqvmap/dist/jquery.vmap.js"></script>
    <script src="${contextPath}/static/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
    <script src="${contextPath}/static/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="${contextPath}/static/vendors/moment/min/moment.min.js"></script>
    <script src="${contextPath}/static/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

    <!-- Custom Theme Scripts  -->
    <script src="${contextPath}/static/build/js/custom.js"></script>
    
  </body>
  <sitemesh:write property="page.scripts"/>
</html>

