<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>角色管理</title>

	<link rel="shortcut icon" href="/sfpp-web/favicon.ico" />
    <!-- CSS -->
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
    
    <script src="/sfpp-web/assets/plugins/jquery/jquery-2.2.0.min.js"></script>
	<script src="/sfpp-web/assets/plugins/jQueryUI/jquery-ui.min.js"></script>
	<script src="/sfpp-web/assets/plugins/jquery-validate/jquery.validate.min.js"></script>
	<script src="/sfpp-web/assets/plugins/jquery-validate/messages_zh.min.js"></script>
	<script src="/sfpp-web/assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="/sfpp-web/assets/plugins/select2/js/select2.min.js"></script>
	<script src="/sfpp-web/assets/plugins/select2/js/i18n/zh-CN.js"></script>
	<script src="/sfpp-web/assets/plugins/tables/bootstrap-table.min.js"></script>
	<script src="/sfpp-web/assets/plugins/tables/bootstrap-table-zh-CN.min.js"></script>
	<script src="/sfpp-web/assets/plugins/tables/bootstrap-table-filter-control.js"></script>
	<script src="/sfpp-web/assets/plugins/bootstrap3-dialog/js/bootstrap-dialog.min.js"></script>
	<script src="/sfpp-web/assets/plugins/treeview/bootstrap-treeview.min.js"></script>
	<script src="/sfpp-web/assets/plugins/treeview/jquery.treegrid.min.js"></script>
	<script src="/sfpp-web/assets/plugins/treeview/jquery.treegrid.bootstrap3.js"></script>
	<script src="/sfpp-web/assets/plugins/ztree/js/jquery.ztree.all.min.js"></script>

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

        <a href="#" title="Go to application"  data-url="/system/roleManage" class="tip-bottom" >
            <i class="glyphicon glyphicon-stats"></i> 角色管理</a>

    </div>
</div>

<div id="inner-content">
	<div id="inner-row" class="row-fluid" >
			<!-- table --> 
            <div  class=" col-md-12 col-xs-12 col-lg-12 col-sm-12">

				<!-- 搜索框 --> 
				<div id="roleTableToolbar" class="form-inline marginLeft">
					<button type="button" class="btn btn-primary" style="margin-left: 20px;" onclick="$.role.addRole()" >新增角色</button>
				</div>
                <table id="roleTable" data-toggle="table"
                       data-page-list="[10,20,30,ALL]"
                       data-pagination="true"
                       data-cache="false"
                       data-filter-control="false"
                       data-detail-view="false"
                       data-detail-formatter="$.role.detail"
                       data-show-refresh="true"
                       data-show-toggle="true"
                       data-show-columns="true"
                       data-filter-show-clear="true"
                       data-toolbar="#roleTableToolbar"
                >
                    <thead>
                    <tr>
                        <th data-field="roleId" data-formatter="$.role.id" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">ID</th>
                        <th data-field="roleName"  data-filter-control="input" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">角色名</th>
                        <th data-field="remark" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">备注</th>
                        <th data-field="isDeleted" data-formatter="$.role.state" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">状态</th>
                        <th data-field="operation" data-formatter="$.role.operation" class="col-md-3 col-xs-3 col-lg-3 col-sm-3" data-align="center">操作</th>
                    </tr>
                    </thead>

                </table>

        </div>
	</div>
</div>

<!-- 新增角色对话框 -->
<div class="modal fade" id="addRoleDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	新增角色
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="addRoleForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">角色名:</div>
								<input type="text" class="form-control" name="roleName" required="required" placeholder="必填">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">备注:</div>
								<input  type="text"class="form-control" name="remark">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.role.submit.addRole()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>

<!-- 修改角色对话框 -->
<div class="modal fade" id="updateRoleDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	修改角色
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="updateRoleForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">角色名:</div>
								<input type="text" class="form-control" name="roleName" required="required" placeholder="必填">
								<input type="hidden" name="roleId">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">备注:</div>
								<input  type="text"class="form-control" name="remark">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.role.submit.updateRole()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>


<!-- 角色绑定资源对话框 -->
<div class="modal fade" id="changeRoleResourceDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: left;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	分配角色资源
                 </h4>
                 <input type="hidden" name="roleId">
             </div>

             <div class="modal-body">
				<div class="row-fluid">
					<ul id="roleResourceTree" class="ztree"></ul>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.role.saveRoleResource()">保存</button>
				</div>
			</div>

		</div>
     </div>
</div>

<script src="/sfpp-web/assets/js/matrix.js"></script>
<script src="/sfpp-web/assets/js/util.js"></script>
<script src="/sfpp-web/assets/js/system/roleManage.js"></script>

</body>

</html>
