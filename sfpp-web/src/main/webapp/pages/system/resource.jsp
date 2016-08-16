<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>资源管理</title>

	<link rel="shortcut icon" href="/sfpp-web/favicon.ico" />
    <link rel="stylesheet" href="/sfpp-web/assets/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/select2/css/select2.min.css"/>
    <link rel="stylesheet" href="/sfpp-web/assets/css/matrix-style.css" />
    <link rel="stylesheet" href="/sfpp-web/assets/css/matrix-media.css" />
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/tables/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/treeview/bootstrap-treeview.min.css"/>
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/treeview/bootstrap-treeview.css"/>
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/treeview/jquery.treegrid.css"/>
    <link rel="stylesheet" href="/sfpp-web/assets/plugins/ztree/css/zTreeStyle/zTreeStyle.css"/>
    
    <link rel="stylesheet" href="/sfpp-web/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/form-elements.css">
    <link rel="stylesheet" href="/sfpp-web/assets/css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/sfpp-web/assets/plugins/html5shiv.min.js"></script>
    <script src="/sfpp-web/assets/plugins/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="content-header">
    <div id="breadcrumb">

        <a href="#" title="Go to market"  data-url="/system/manage" class="tip-bottom" >
            <i class="glyphicon glyphicon-cloud"></i> 系统设置</a>

        <a href="#" title="Go to application"  data-url="/system/resource" class="tip-bottom" >
            <i class="glyphicon glyphicon-stats"></i> 资源管理</a>

    </div>
</div>

<div id="inner-content">
	<div id="inner-row" class="row-fluid" >
		<!-- table --> 
        <div  class=" col-md-6 col-xs-6 col-lg-6 col-sm-6" style="padding-left:100px;">
			<ul id="resourceTree" class="ztree"></ul>
    	</div>
        <div  class=" col-md-6 col-xs-6 col-lg-6 col-sm-6">
        
			
    	</div>
    </div>
</div>

<!-- 新增资源对话框 -->
<div class="modal fade" id="addResourceDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	新增资源
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="addResourceForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">资源类型:</div>
								<input type="radio"  name="resourceType" checked value="M">一级菜单</input>
								<input type="radio"  name="resourceType" value="S">二级菜单</input>
								<input type="radio"  name="resourceType" value="B">按钮</input>
							</div>
							<input type="hidden" name="parentId">
							<input type="hidden" name="remark">
						</div>
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">资源名:</div>
								<input type="text" class="form-control" name="resourceName" required="required" placeholder="必填">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">资源链接:</div>
								<input  type="text"class="form-control" name="resourceUrl" required="required" placeholder="必填">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.resource.saveResource()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>

<!-- 修改资源对话框 -->
<div class="modal fade" id="updateResourceDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	修改资源
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="updateResourceForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">资源名:</div>
								<input type="text" class="form-control" name="resourceName" required="required" placeholder="必填">
								<input type="hidden" name="resourceId">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">资源URL:</div>
								<input  type="text"class="form-control" name="resourceUrl" required="required" placeholder="必填">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.resource.updateResource()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>

<!-- 删除资源对话框 -->
<div class="modal fade" id="deleteResourceDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:25%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	删除资源 <label id="resourceNameD"></label>
                 </h4>
                 <input type="hidden" name="resoueceId">
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	               	<div style="color:gray;text-align: left;">
						<h3>提示：</h3>
						<h4>删除资源请慎重，操作不可逆。</h4>
						<h4>删除资源后所有用户都不可访问。</h4>
						<h4>删除资源后该资源及其下级资源都不能再访问。</h4>
					</div>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.resource.deleteResource()">确定删除</button>
				</div>
			</div>

		</div>
     </div>
</div>
<script src="/sfpp-web/assets/plugins/jquery/jquery-2.2.0.min.js"></script>
<script src="/sfpp-web/assets/plugins/jQueryUI/jquery-ui.min.js"></script>
<script src="/sfpp-web/assets/plugins/jquery-validate/jquery.validate.min.js"></script>
<script src="/sfpp-web/assets/plugins/jquery-validate/messages_zh.min.js"></script>
<script src="/sfpp-web/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/sfpp-web/assets/plugins/bootstrap3-dialog/js/bootstrap-dialog.min.js"></script>
<script src="/sfpp-web/assets/plugins/treeview/bootstrap-treeview.min.js"></script>
<script src="/sfpp-web/assets/plugins/treeview/jquery.treegrid.min.js"></script>
<script src="/sfpp-web/assets/plugins/treeview/jquery.treegrid.bootstrap3.js"></script>
<script src="/sfpp-web/assets/plugins/ztree/js/jquery.ztree.all.min.js"></script>

<script src="/sfpp-web/assets/js/matrix.js"></script>
<script src="/sfpp-web/assets/js/util.js"></script>
<script src="/sfpp-web/assets/js/system/resource.js"></script>

</body>

</html>
