/**
 * 591791
 * 2016年7月26日
 */

$(document).ready(function()  {
    //为模态对话框添加拖拽  
    $(".modal-dialog").draggable();  
    //为模态对话框添加拖拽，拖拽区域只在顶部栏  
    $(".modal-dialog").draggable({  
        handle: ".modal-header"       
    }); 
    //禁止模态对话框的半透明背景滚动
    $(".modal-dialog").parent().css("overflow", "hidden");
});

$('#userTable').bootstrapTable({
   url:getContextPath()+"/system/user/getAllUser",
   responseHandler:function(response){
       if(response.code !='000'){
           $.util.alertError(response.message);
           return null;
       }else{
           if(response.data != null){
               return response.data;
           }else{
               return null;
           }
       }
   }
});

$("#userRoleTable").bootstrapTable({
	   url:getContextPath()+"/system/userRole/getAllRoleByUserNo",
	   cache: false,
	   responseHandler:function(response){
	       if(response.code !='000'){
	           $.util.alertError(response.message);
	           return null;
	       }else{
	           if(response.data != null){
	               return response.data;
	           }else{
	               return null;
	           }
	       }
	   }
	});

$.user = {
	id : function(value, row, index) {
		return row.id;
	},
	detail : function(value, row) {
		
	},
	state : function(value) {
		if (value == "0") {
			return "<span class='paddingLeft' style='color:green'>"+"正常"+"</span>";
		} else {
			return "<span class='paddingLeft' style='color:red'>"+"停用"+"</span>";
		}
	},
	operation : function(value, row) {
		var html = '<button class="marginLeft btn btn-default " onclick="$.user.updateUser(\''+row.userNo+'\')">修改</button>';
		
		if (row.isDeleted == "0") {
			html += '<button class="marginLeft btn btn-default" onclick="$.user.lockUser(\''+row.userNo+'\')">锁定</button>';
		} else {
			html += '<button class="marginLeft btn btn-default" onclick="$.user.unLockUser(\''+row.userNo+'\')">解锁</button>';
		}
		if (row.isDeleted) {
			html += '<button class="marginLeft btn btn-default" title="已锁定的用户不能分配角色" disabled>分配角色</button>'
		} else {
			html += '<button class="marginLeft btn btn-default" onclick="$.user.changeUserRole(\''+row.userNo+'\')">分配角色</button>'
		}
		return html;
	},
	updateUser : function(value) {
		$.ajax({
            url:getContextPath()+"/system/user/getUsersByUserNo",
            data:{userNo:value},
            success: function (response) {
                if(response.code !='000'){
                    $.util.alertError(response.message);
                }else{
                    var data= $.util.objToJson(response.data);
                    $('#updateUserForm')[0].reset();
                    $('#updateUserForm').jsonToForm(data);
                    $('#updateUserDialog').modal('show');
                }
            }
        });
	},
	lockUser : function(userNo) {
		$.ajax({
			url:getContextPath()+"/system/user/changeUserState",
			data : {userNo:userNo, lock: true},
			success: function (response) {
				if(response.code !='000'){
                    $.util.alertError(response.message);
                }else{
                	$('#userTable').bootstrapTable('refresh');
                }
			}
		});
	},
	unLockUser : function(userNo) {
		$.ajax({
			url:getContextPath()+"/system/user/changeUserState",
			data : {userNo:userNo, lock: false},
			success: function (response) {
				if(response.code !='000'){
					$.util.alertError(response.message);
				}else{
					$('#userTable').bootstrapTable('refresh');
				}
			}
		});
	},
	addUser : function() {
		$('#addUserForm')[0].reset();
		$("#addUserDialog").modal('show');
	},
	changeUserRole : function(value) {
		$("input[name='userNo']").val(value);
		$("#userRoleTable").bootstrapTable('refresh',{
			query:{userNo:value}
		});
		$("#changeUserRoleDialog").modal('show');
	},
}

$.user.submit = {
	addUser : function() {
		if($('#addUserForm').valid()){
            $.util.submit('/system/user/addUser', '#addUserForm', '#addUserDialog', '新增用户出错~', null, '#userTable', null, '新增用户成功~');
        }
    },
    updateUser : function() {
    	if($('#addUserForm').valid()){
    		$.util.submit('/system/user/updateUser', '#updateUserForm', '#updateUserDialog', '修改用户出错~', null, '#userTable', null, '修改用户成功~');
    	}
    },
    deleted :function(){
        var id = $('#diagramId').val();
        var data={
            id:id
        };

      //  $.util.submit('/diagram/deleteDiagram', null,'#deleteDiagramDialog', '删除六维图出错~', data, '#diagramTable', null, '删除六维图成功~');
    }
}

$.userRole = {
	id : function(value, row, index) {
		return row.roleId;
	},
	state : function(value) {
		if (value == "0") {
			return "<span class='paddingLeft' style='color:green'>"+"正常"+"</span>";
		} else {
			return "<span class='paddingLeft' style='color:red'>"+"停用"+"</span>";
		}
	},
	bindState : function(value) {
		if (value) {
			return "<span class='paddingLeft' style='color:green'>"+"已绑定"+"</span>";
		} else {
			return "<span class='paddingLeft' style='color:red'>"+"未绑定"+"</span>";
		}
	},
	operation :  function(value, row) {
		var userNo = $("input[name='userNo']").val();
		if (row.isDeleted) {
			if (row.bindState == true) {
				var html = '<button class="marginLeft btn btn-default" title="已锁定的角色不能操作" disabled>取消绑定</button>';
			} else {
				var html = '<button class="marginLeft btn btn-default" title="已锁定的角色不能操作" disabled>绑定角色</button>';
			}
		} else {
			if (row.bindState == true) {
				var html = '<button class="marginLeft btn btn-default" onclick="$.userRole.bindUser(\''+row.roleId +'\','+ false +',\''+ userNo +'\')">取消绑定</button>';
			} else {
				var html = '<button class="marginLeft btn btn-default" onclick="$.userRole.bindUser(\''+row.roleId +'\','+ true +',\''+ userNo +'\')">绑定角色</button>';
			}
		}
		return html;
	},
	bindUser : function(roleId, state, userNo) {
		$.ajax({
			url:getContextPath()+"/system/userRole/changeUserRole",
			data : {roleId: roleId, state: state, userNo:userNo},
			cache : false,
			success: function (response) {
				if(response.code !='000'){
                    $.util.alertError(response.message);
                }else{
                	$('#userRoleTable').bootstrapTable('refresh',{
                		query :{userNo:userNo}
                	});
                }
			}
		});
	}
}