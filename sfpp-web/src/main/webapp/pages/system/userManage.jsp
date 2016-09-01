<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用户管理</title>

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

        <a href="#" title="Go to application"  data-url="/system/userManage" class="tip-bottom" >
            <i class="glyphicon glyphicon-stats"></i> 用户管理</a>

    </div>
</div>

<div id="inner-content">
	<div id="inner-row" class="row-fluid" >
			<!-- table --> 
            <div  class=" col-md-12 col-xs-12 col-lg-12 col-sm-12">

				<!-- 搜索框 --> 
				<!-- 
				-->
				<div id="userTableToolbar" class="form-inline marginLeft">
<!-- 					<div class="form-group"> -->
<!-- 						<div class="input-group col-sm-6 col-lg-6 col-xs-6 col-md-6" > -->
<!-- 							<div class="input-group-addon">工号:</div> -->
<!-- 							<input type="text" class="form-control" name="userNo"> -->
<!-- 						</div> -->
<!-- 						<button type="button" class="btn btn-default" style="margin-left: 20px;" onclick="$.user.search()" id="userSearchBtn">搜索</button> -->
<!-- 					</div> -->
					<button type="button" class="btn btn-primary" style="margin-left: 20px;" onclick="$.user.addUser()">新增用户</button>
				</div>
                <table id="userTable" data-toggle="table"
                       data-page-list="[10,20,30,ALL]"
                       data-pagination="true"
                       data-cache="false"
                       data-filter-control="true"
                       data-detail-view="false"
                       data-detail-formatter="$.user.detail"
                       data-show-refresh="true"
                       data-show-toggle="true"
                       data-show-columns="true"
                       data-filter-show-clear="true"
                       data-toolbar="#userTableToolbar"
                >
                    <thead>
                    <tr>
                        <th data-field="id" data-formatter="$.user.id" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">ID</th>
                        <th data-field="userNo"  data-filter-control="input" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">用户工号</th>
                        <th data-field="userName" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">姓名</th>
                        <th data-field="mobile" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">手机号</th>
                        <th data-field="isDeleted" data-formatter="$.user.state" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">状态</th>
                        <th data-field="operation" data-formatter="$.user.operation" class="col-md-3 col-xs-3 col-lg-3 col-sm-3" data-align="center">操作</th>
                    </tr>
                    </thead>

                </table>

        </div>
	</div>
</div>

<!-- 新增用户对话框 -->
<div class="modal fade" id="addUserDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	新增用户
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="addUserForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">用户工号:</div>
								<input type="text" class="form-control" name="userNo" required="required" placeholder="必填">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">用户姓名:</div>
								<input  type="text"class="form-control" name="userName">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">手机号码:</div>
								<input  type="text"class="form-control" name="mobile">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.user.submit.addUser()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>

<!-- 修改用户对话框 -->
<div class="modal fade" id="updateUserDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:20%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	修改用户
                 </h4>
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <form class="form-horizontal" id ="updateUserForm" style="margin:20px;">
						<div class="form-group">
							<div class="input-group " >
								<div class="input-group-addon">用户工号:</div>
								<input type="text" class="form-control" name="userNo" required="required" placeholder="必填">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">用户姓名:</div>
								<input  type="text"class="form-control" name="userName">
							</div>
						</div>
						<div class="form-group ">
							<div class="input-group " >
								<div class="input-group-addon">手机号码:</div>
								<input  type="text"class="form-control" name="mobile">
							</div>
						</div>
	                </form>
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="$.user.submit.updateUser()">确定</button>
				</div>
			</div>

		</div>
     </div>
</div>


<!-- 用户绑定角色对话框 -->
<div class="modal fade" id="changeUserRoleDialog" role="dialog" aria-hidden="true">
     <div class="modal-dialog" style="width:40%; margin-top: 200px;text-align: center;" >
		<div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                     &times;
                 </button>
                 <h4 class="modal-title">
                     	分配用户角色
                 </h4>
                 <input type="hidden" name="userNo">
             </div>

             <div class="modal-body">
				<div class="row-fluid">
	                <table id="userRoleTable" data-toggle="table"
                       data-pagination="false"
                       data-cache="false"
                       data-filter-control="false"
                       data-detail-view="false"
                       data-show-refresh="false"
                       data-show-toggle="false"
                       data-show-columns="false"
                       data-filter-show-clear="true"
                	>
                    <thead>
                    <tr>
                        <th data-field="roleId" data-formatter="$.userRole.id" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">ID</th>
                        <th data-field="roleName"  class="col-md-1 col-xs-1 col-lg-1 col-sm-1">角色名</th>
                        <th data-field="remark" class="col-md-1 col-xs-1 col-lg-1 col-sm-1">备注</th>
                        <th data-field="isDeleted" data-formatter="$.userRole.state" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">角色状态</th>
                        <th data-field="bindState" data-formatter="$.userRole.bindState" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">绑定状态</th>
                        <th data-field="operation" data-formatter="$.userRole.operation" class="col-md-1 col-xs-1 col-lg-1 col-sm-1" data-align="center">操作</th>
                    </tr>
                    </thead>

                	</table>
	                
                </div>
             </div>

			<div class="modal-footer">
				<div style="display: inline;">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>

		</div>
     </div>
</div>

<script src="/sfpp-web/assets/js/matrix.js"></script>
<script src="/sfpp-web/assets/js/util.js"></script>
<script src="/sfpp-web/assets/js/system/userManage.js"></script>

</body>

</html>