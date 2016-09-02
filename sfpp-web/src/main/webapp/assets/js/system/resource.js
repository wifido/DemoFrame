/**
 * 591791
 * 2016年7月26日
 */


$.resource = {
	resourceTree : null,
	init : function() {
		this.resourceTree = this.initResourceTree();
	},
	getResourceTreeNodes : function() {
		var nodes;
		$.ajax({
	        url:getContextPath()+"/system/resource/getResourceList",
	        async: false,
	        success: function (response) {
	            if(response.code !='000'){
	                $.util.alertError(response.message);
	            }else{
	            	nodes = response.data;
	            }
	        }
		});
		return nodes;
	},
	initResourceTree : function() {
		var zTreeObj;
		
		var nodes;
		$.ajax({
	        url:getContextPath()+"/system/resource/getResourceList",
	        async: false,
	        success: function (response) {
	            if(response.code !='000'){
	                $.util.alertError(response.message);
	            }else{
	            	nodes = response.data;
	            }
	        }
		});
		
		// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		var setting = {
			check:{
				enable: false,
				chkStyle: "checkbox",
				chkboxType : { "Y": "ps", "N": "ps" }
			},
			edit:{
				enable: true,
				showRemoveBtn : this.showRemoveBtn,
				showRenameBtn : true,
				removeTitle : "删除节点",
				renameTitle : "修改节点"
			},
			callback:{
				beforeEditName : this.editResource,
				beforeRemove : this.removeResource
			},
			view : {
				addHoverDom : this.addHoverDom,
				removeHoverDom : this.removeHoverDom
			}
		};
		
		zTreeObj = $.fn.zTree.init($("#resourceTree"), setting, nodes);
		return zTreeObj;
	},
	refreshTree : function() {
		$.fn.zTree.destroy(this.resourceTree);
		this.initResourceTree();
	},
	addHoverDom : function(treeId, treeNode) {
		if(treeNode.isParent){
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#"+treeNode.tId+"_add").length>0) return;
			var addStr = "<span class='button add' id='" + treeNode.tId
				+ "_add' title='添加节点' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#"+treeNode.tId+"_add");
			if (btn) btn.bind("click", function(){
//				console.log(treeNode.id);
				$("input[name='parentId']").val(treeNode.id);
				$("input[name='remark']").val(treeNode.resourceType);
				$("#addResourceDialog").modal("show");
				return false;
			});
		}
	},
	removeHoverDom : function(treeId, treeNode) {
		$("#"+treeNode.tId+"_add").unbind().remove();
	},
	showRemoveBtn : function(treeId, treeNode) {
		return !treeNode.isFirstNode;	// 第一个节点时，不能删除
	},
	updateResource : function() {
		// 保存更改，刷新tree
		if($('#updateResourceForm').valid()){
    		$.util.submit('/system/resource/updateResource', '#updateResourceForm', '#updateResourceDialog', '修改资源出错~', null, null, null, '修改资源成功~');
    		//setTimeout(this.init(), 2000); // 延迟1秒刷新
    		this.refreshTree();
    	}
	},
	editResource : function(treeId, treeNode) {
		var json = {
			resourceId : treeNode.id,
			resourceUrl : treeNode.resourceUrl,
			resourceName : treeNode.name
		}
		var data = $.util.objToJson(json);
		$('#updateResourceForm').jsonToForm(data);
		// 打开修改的对话框
		$("#updateResourceDialog").modal("show");
		return false;	// return false来阻止进入默认的节点编辑状态
	},
	removeResource : function(treeId, treeNode){
		$("#resourceNameD").text(treeNode.name);
		$("input[name='resourceId']").val(treeNode.id);
		$("#deleteResourceDialog").modal("show");
		return false; // return false 后页面上不会立即删除
	},
	deleteResource : function() {
		var value = $("input[name='resourceId']").val();
		$.ajax({
			url: getContextPath()+"/system/resource/deleteResource",
			data: {id:value},
			async: false,
			success: function (response) {
				if(response.code !='000'){
					$.util.alertError(response.message);
//					setTimeout(this.init, 2000); // 延迟2秒刷新
					$.resource.refreshTree();
				}else{
					$("#deleteResourceDialog").modal("hide");
					//setTimeout(this.init, 2000); // 延迟2秒刷新
					$.resource.refreshTree();
				}
			}
		});
	},
	saveResource : function() {
		if($('#addResourceForm').valid()){
    		$.util.submit('/system/resource/addResource', '#addResourceForm', '#addResourceDialog', '添加资源出错~', null, null, null, '添加资源成功~');
//    		setTimeout($.resource.init, 2000); // 延迟2秒刷新
    		this.refreshTree();
    	}
	}
}


$(document).ready(function()  {
    //为模态对话框添加拖拽  
    $(".modal-dialog").draggable();  
    //为模态对话框添加拖拽，拖拽区域只在顶部栏  
    $(".modal-dialog").draggable({  
        handle: ".modal-header"       
    }); 
    //禁止模态对话框的半透明背景滚动
    $(".modal-dialog").parent().css("overflow", "hidden");
    
    $.resource.init();
});