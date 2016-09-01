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

$('#roleTable').bootstrapTable({
   url:getContextPath()+"/system/role/getAllRole",
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

$.role = {
	id : function(value, row, index) {
		return row.roleId;
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
		var html = '<button class="marginLeft btn btn-default " onclick="$.role.updateRole(\''+row.roleId+'\')">修改</button>';
		
		if (row.isDeleted == "0") {
			html += '<button class="marginLeft btn btn-default" onclick="$.role.lockRole(\''+row.roleId+'\')">锁定</button>';
		} else {
			html += '<button class="marginLeft btn btn-default" onclick="$.role.unLockRole(\''+row.roleId+'\')">解锁</button>';
		}
		if (row.isDeleted) {
			html += '<button class="marginLeft btn btn-default" title="已锁定的角色不能分配资源" disabled>分配资源</button>'
		} else {
			html += '<button class="marginLeft btn btn-default" onclick="$.role.changeRoleResource(\''+row.roleId+'\')">分配资源</button>'
		}
		return html;
	},
	updateRole : function(value) {
		$.ajax({
            url:getContextPath()+"/system/role/getRoleByRoleId",
            data:{roleId:value},
            success: function (response) {
                if(response.code !='000'){
                    $.util.alertError(response.message);
                }else{
                    var data= $.util.objToJson(response.data);
                    $('#updateRoleForm')[0].reset();
                    $('#updateRoleForm').jsonToForm(data);
                    $('#updateRoleDialog').modal('show');
                }
            }
        });
	},
	lockRole : function(value) {
		$.ajax({
			url:getContextPath()+"/system/role/changeRoleState",
			data : {roleId:value, lock: true},
			success: function (response) {
				if(response.code !='000'){
                    $.util.alertError(response.message);
                }else{
                	$('#roleTable').bootstrapTable('refresh');
                }
			}
		});
	},
	unLockRole : function(value) {
		$.ajax({
			url:getContextPath()+"/system/role/changeRoleState",
			data : {roleId:value, lock: false},
			success: function (response) {
				if(response.code !='000'){
					$.util.alertError(response.message);
				}else{
					$('#roleTable').bootstrapTable('refresh');
				}
			}
		});
	},
	addRole : function() {
		$('#addRoleForm')[0].reset();
		$("#addRoleDialog").modal('show');
	},
	changeRoleResource : function(value) {
		$("input[name='roleId']").val(value);
		initRoleResourceTree(value);
		$("#changeRoleResourceDialog").modal('show');
	},
	saveRoleResource : function() {
		var roleId = $("input[name='roleId']").val();
		console.log(roleId);
		
		var resTreeObj = $.fn.zTree.getZTreeObj("roleResourceTree");
		var nodes = resTreeObj.getCheckedNodes(true);
		//var str = document.getElementsByName("resbox");
		var objarray = nodes.length;
		var chestr = "";
		for (i=0;i<objarray;i++)
		{
//		  if(nodes[i].checked == true)
//		  {
		   chestr+=nodes[i].id+",";
//		  }
		}
		console.log(chestr);
		
		$.ajax({
			url:getContextPath()+"/system/resource/updateRoleResource",
	        data:{roleId:roleId, resList: chestr},
	        async: false,
	        success: function (response) {
	            if(response.code !='000'){
	                $.util.alertError(response.message);
	            }else{
	                var data= response.data;
	                $("#changeRoleResourceDialog").modal('hide');
	            }
	        }
		});
	}
}

$.role.submit = {
	addRole : function() {
		if($('#addRoleForm').valid()){
            $.util.submit('/system/role/addRole', '#addRoleForm', '#addRoleDialog', '新增角色出错~', null, '#roleTable', null, '新增角色成功~');
        }
    },
    updateRole : function() {
    	if($('#updateRoleForm').valid()){
    		$.util.submit('/system/role/updateRole', '#updateRoleForm', '#updateRoleDialog', '修改角色出错~', null, '#roleTable', null, '修改角色成功~');
    	}
    },
    deleted :function(){
        var id = $('#diagramId').val();
        var data={
            id:id
        };
        //$.util.submit('/diagram/deleteDiagram', null,'#deleteDiagramDialog', '删除六维图出错~', data, '#diagramTable', null, '删除六维图成功~');
    }
}

function initRoleResourceTree(value) {
	
	var setting = {
		check:{
			enable: true,
			chkStyle: "checkbox",
			chkboxType : { "Y": "ps", "N": "ps" }
		},
	}
	var nodes;
	$.ajax({
        url:getContextPath()+"/system/resource/getRoleResource",
        data:{roleId:value},
        async: false,
        success: function (response) {
            if(response.code !='000'){
                $.util.alertError(response.message);
            }else{
                nodes = response.data;
            }
        }
    });

//	var resTreeObj = 
		$.fn.zTree.init($("#roleResourceTree"), setting, nodes);
}