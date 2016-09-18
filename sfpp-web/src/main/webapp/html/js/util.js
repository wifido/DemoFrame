/**
 * Created by 594829 on 5/17/16.
 *
 * Helper类,用来加载主页面和菜单
 */


function getContextPath() {
	var pathName = document.location.pathname;

	var curWwwPath = window.document.location.href;
	var pos = curWwwPath.indexOf(pathName);

	var localhostPath = curWwwPath.substring(0, pos);

	var length = pathName.split("/");

	var result = '';
	if (length.length > 3) {
		if (length[1] != 'index') {
			result = '/' + length[1];
		}
	}

	return (localhostPath + result);
}

function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";path="+";expires=" + exp.toGMTString();
}

function getBasePath() {
	var pathName = document.location.pathname;

	var length = pathName.split("/");

	var result = '';
	if (length.length > 3) {
		if (length[1] != 'index') {
			result = '/' + length[1];
		}
	}
	return result;
}

$.util = {
	yesterday : function() {
		var yesterday = new Date();
		yesterday.setDate(yesterday.getDate() - 1);
		return yesterday;
	},
	day0clock:function(mydate){
		mydate.setHours(00);
		mydate.setMinutes(00);
		mydate.setSeconds(00);
		return mydate;
	},
	/**
	 * 获取N天前的日期 days 为数值类型
	 *
	 * @param days
	 * @returns {Date}
	 */
	NdayAgo : function(days) {
		var day = new Date();
		day.setDate(day.getDate() - days);
		return day;
	},
	/**
	 * 时间格式化 formatStr 为“yyyy-MM-dd” dateTime 为日期类型
	 */
	dateFormat : function(formatStr, dateTime) {

		var str = formatStr;
		var Week = [ '日', '一', '二', '三', '四', '五', '六' ];

		str = str.replace(/yyyy|YYYY/, dateTime.getFullYear());
		str = str
			.replace(/yy|YY/, (dateTime.getYear() % 100) > 9 ? (dateTime
				.getYear() % 100).toString() : '0'
			+ (dateTime.getYear() % 100));

		str = str.replace(/MM/,
			dateTime.getMonth() > 8 ? dateTime.getMonth() + 1 : '0'
			+ (dateTime.getMonth() + 1));
		str = str.replace(/M/g, dateTime.getMonth() + 1);

		str = str.replace(/w|W/g, Week[dateTime.getDay()]);

		str = str.replace(/dd|DD/, dateTime.getDate() > 9 ? dateTime.getDate()
			.toString() : '0' + dateTime.getDate());
		str = str.replace(/d|D/g, dateTime.getDate());

		str = str.replace(/hh|HH/, dateTime.getHours() > 9 ? dateTime
			.getHours().toString() : '0' + dateTime.getHours());
		str = str.replace(/h|H/g, dateTime.getHours());
		str = str.replace(/mm/, dateTime.getMinutes() > 9 ? dateTime
			.getMinutes().toString() : '0' + dateTime.getMinutes());
		str = str.replace(/m/g, dateTime.getMinutes());

		str = str.replace(/ss|SS/, dateTime.getSeconds() > 9 ? dateTime
			.getSeconds().toString() : '0' + dateTime.getSeconds());
		str = str.replace(/s|S/g, dateTime.getSeconds());
		return str;
	},
	init : function() {
		this.loadMainMenu();	// 载入主界面菜单
		
		var path = sessionStorage.getItem('path'); // 主页面路径（子菜单路径）
		var topPath = sessionStorage.getItem('topPath'); // 顶部菜单路径
		if (topPath == null || topPath == '/main/index') {
			$.util.loadDashboard();
		} else {
			$.util.loadMain(topPath, false, true);
		}
		this.loadUserName();
	},
	loadUserName : function() {
		$.ajax({
			url : getContextPath() + "/userInfo",
			dataType : 'json',
			type : 'get',
			success : function(response) {
				if (response.code === '000') {
					$("#userName").text(response.data.userName);
				} else {

				}
			}
		});
	},
	loadMainMenu : function() {
		var mainMenu = $("#userMainMenu");
		mainMenu.html('');
		
		$.ajax({
			url : getContextPath() + "/index/loadMainMenu",
			dataType : 'json',
			type : 'get',
			async: false,
			success : function(response) {
				if (response.code === '000') {
					var data = response.data;
					var html = '';
					html += '<ul class="nav navbar-nav">';
					data.forEach(function(value, index) {
						html += '<li><a class="top-menu" data-url="'+value.resourceUrl+'" href="#">'+value.resourceName+'</a></li>';
					});
					html += '</ul>';
					mainMenu.append(html);
				} else {
					$.util.alertError(response.message);
				}
			}
		});
	},
	/**
	 * 加载首页
	 */
	loadDashboard : function() {
		var url = getContextPath() + "/index";
		var path = "/main/dashboard";
		$.ajax({
			url : url + path,
			async : false,
			dataType : "html",
			cache : false,
			success : function(data) {

				$('#sidebar').html('');
				$('#content').html('');
				$('#content').html(data);

				$("a[class='top-menu active']").removeClass('active');
				$("a[data-url='" + "/main/index" + "']").addClass('active');

				sessionStorage.setItem('path', '/main/index');
				sessionStorage.setItem('topPath', '/main/index');
				window.history.pushState('/main/index', '', getBasePath()
					+ '/main/index');

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status == '401'){
					window.location.href = getContextPath();
				}else{
					$.util.alertError("首页加载失败");
				}
			}
		});

	},
	/**
	 * 加载子菜单
	 */
	loadSideBar : function(path) {
		var icons = [ "flash", "tag", "cloud", "link", "map-marker",
			"paperclip", "fire", "send", "tree-deciduous", "heart-empty" ];

		var url = getContextPath() + "/index/loadSideMenu";
		var firstMenu = null;
		$.ajax({
			url : url,
			data : {
				path : path
			},
			dataType : "json",
			async : false,		// 子菜单需要同步加载
			cache : true,		// 可以使用缓存
			success : function(response) {
				var html = '<ul>';
				if (response.code != '000') {
					$.util.alertError(response.message);
				} else {
					var resArr = response.data;
					var i = 0;
					for ( var k in resArr) {
						if (firstMenu == null) {
							firstMenu = resArr[k];
						}
						html += "<li><a class='side-menu' data-url='"
							+ resArr[k]
							+ "' href='#'><i class='glyphicon glyphicon-"
							+ icons[i] + "'></i>" + "<span>" + k
							+ "</span></a></li>";
						i++;
						if (i >= icons.length) {
							i = 0
						}
					}
				}
				html += '</ul>';

				$('#sidebar').html(html);

				$("#sidebar >ul :first").addClass("active");

				var ul = $('#sidebar > ul');
				if ($(window).width() < 481) {
					ul.css({
						'display' : 'none'
					});
				}

				if ($(window).width() > 480) {
					ul.css({
						'display' : 'block'
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {

				if(XMLHttpRequest.status == '401'){
					firstMenu = "-1";

				}


			}
		});
		return firstMenu;
	},


	/**
	 * 加载主页面
	 * @param path 页面路径
	 * @param historyFlag 是否加入到历史记录中,true 为是,false为否
	 * @param sessionFlag 是否需要清除session,true 为是, false 为否
	 * @param json 需要存入到session中的json数据
	 */
	loadModule : function(path, historyFlag, sessionFlag, json) {


		var url = getContextPath() + "/index" + path;
		$.ajax({
			url : url,
			async : true, // 需要异步加载
			dataType : "html",
			cache : false,
			success : function(data) {


				var topPath = sessionStorage.getItem("topPath");
				var state = topPath + "*" + path;
				// 存入拼装而成历史记录
				if (historyFlag) {
					window.history.pushState(state, '', getBasePath() + '/main/index');
				}

				if (sessionFlag) {
					sessionStorage.clear();
				}

				if (json) {
					var jsonArr = JSON.parse(json);
					for ( var k in jsonArr) {
						sessionStorage.setItem(k, jsonArr[k]);
					}
				}

				$('#content').html('');
				$('#content').html(data);

				sessionStorage.setItem('path', path);
				sessionStorage.setItem('topPath', topPath);

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status == '401'){
					window.location.href = getContextPath();

				}
			}
		});

	},

	/**
	 * 从顶部菜单加载子菜单，并且加载子菜单中第一项到主页面 除了首页dashboard，其它的页面都需要有子菜单
	 */
	loadMain : function(path, historyFlag, sessionFlag, json) {

		// 点击头部菜单的时候，path可能是有topPath+path组成(形式: /topPath*/path)
		// loadMain的时候不加历史记录，因为每个loadMain都会LoadModule, 所有在loadModule中加入历史记录，历史记录有module的path和toppath组成上面所说的形式
		// 在这里需要判断path是否为拼装而成：正常点击时path不会为拼装，前进后退时就会为拼装的，需要拆分
		var contentPath = null;
		if (path.indexOf("*") != -1) {
			var pArr = path.split("*");
			path = pArr[0];
			contentPath = pArr[1];
		} else {
			contentPath = sessionStorage.getItem("path");
		}

		sessionStorage.setItem('topPath', path); // 存储顶部菜单路径


		var firstMenu = $.util.loadSideBar(path);


		//为null,说明没有加载成功
		if(firstMenu == "-1"){
			sessionStorage.removeItem("path");
			window.location.href = getContextPath();
			return false;
		}

		$("a[class='top-menu active']").removeClass('active');
		$("a[class='top-menu'][data-url='" + path + "']").addClass('active');

		if (null != contentPath && !historyFlag) { // 刷新的时候保持原位， 刷新和前进后退的时候history为false，且contentPath一定有值
			$.util.loadModule(contentPath, historyFlag, sessionFlag, json);
			$("li[class=active]").removeClass("active")
			$("a[class='side-menu'][data-url='" + contentPath + "']").parent()
				.addClass('active');
		} else {									// 从顶部点击的时候加载第一项, 顶部点击时history为ture
			$.util.loadModule(firstMenu, historyFlag, sessionFlag, json);
		}



	},

	submit : function(controllerURL, formId, dialogId, errMsg, data,
					  refreshTable, dialogShow, sucMsg) {

		var reqData = null;
		var that = this;

		if (formId) {
			reqData = $(formId).serialize();
		} else {
			reqData = data;
		}
		$.ajax({
			type : "post",
			url : getContextPath() + controllerURL,
			dataType : 'json',
			data : reqData,
			async : true,
			success : function(response) {
				if (response.code === '000') {
					if (response.data === true) {
						if (refreshTable) {
							$(refreshTable).bootstrapTable('refresh');
						}
						if (!dialogShow) {
							$(dialogId).modal('hide');
						}
						if (sucMsg) {
							that.alertSuccess(sucMsg);
						}
					} else {
						$.util.alertWarning("操作失败!");
					}

				} else {
					if (response.message.length == 0) {
						$.util.alertWarning(errMsg);
					} else {
						$.util.alertWarning(response.message);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('AJAX出错');
			}
		});
	},
	alertError : function(msg, options) {
		BootstrapDialog.alert({
			type : 'type-danger',
			title : '错误',
			buttonLabel : '确定',
			message : msg
		});
	},
	alertSuccess : function(msg, options) {
		BootstrapDialog.alert({
			type : 'type-success',
			title : '正确',
			buttonLabel : '确定',
			message : msg
		})
	},
	alertWarning : function(msg, options) {
		BootstrapDialog.alert({
			type : 'type-warning',
			title : '警告',
			buttonLabel : '确定',
			message : msg
		});
	},
	alertInfo : function(msg, options) {
		BootstrapDialog.alert({
			type : 'type-info',
			title : '信息',
			buttonLabel : '确定',
			message : msg
		});
	},
	objToJson : function(obj) {
		return JSON.stringify(obj);

	},
	logout : function() {
		sessionStorage.clear();
		window.location.href = "/smp-web/logout";
	},
	scoreToStatus:function(score){
		var status = "正常";
		if (score>=0&& score<=40){
			status = '非常差';
		}else if(score>40&& score<=60){
			status = '较差';
		}else if(score>60&& score<=70){
			status = '正常'
		}else if(score>70&& score<=80){
			status = '较好'
		}else if(score>80&& score<=90){
			status = '好'
		}else{
			status = '非常好'
		}

		return status;
	}

};

$.fn.jsonToForm = function(jsonString) {
	var json = eval("(" + jsonString + ")");

	$.each(json, function(index, element) {

		$("input[name=" + index + "]").val(element);
	});
}

/**
 * 点击主界面中路径导航
 */
$(document).on('click', '.tip-bottom', function() {
	var path = $(this).attr('data-url');

	$("#sidebar > ul > li[class='active']").removeClass('active');
	$("a[class='side-menu'][data-url='" + path + "']").parent().addClass('active');

	event.preventDefault();
	$.util.loadModule(path, true, true);

});

/**
 * 点击头部菜单
 */
$(document).on('click', '.top-menu', function() {
	var path = $(this).attr('data-url');
	event.preventDefault();

	if ($(window).width() < 768) {
		$('#navbar').removeClass('in');
		$('#navbar').attr("aria-expanded", "false");
	}

	if (path != undefined) {
		if (path == "/main/index") {
			$.util.loadDashboard();
		} else {
			$.util.loadMain(path, true, true);
		}

	} else {
		$.util.alertError("页面加载失败,请检查是否配置正确的路径!");
	}

});





$(document).on('click', '.top-wait', function() {

	event.preventDefault();

	if ($(window).width() < 768) {
		$('#navbar').removeClass('in');
		$('#navbar').attr("aria-expanded", "false");
	}


	$.util.alertInfo("功能开发中!");


});

/**
 * 点击左边菜单
 */
$(document).on('click', '.side-menu', function() {
	var path = $(this).attr('data-url');

	event.preventDefault();

	$("#sidebar > ul > li[class='active']").removeClass('active');

	var li = $(this).parents('li');
	li.addClass("active");
	var submenus = $('#sidebar ul');

	if ($(window).width() < 481) {
		submenus.slideUp();
	}
	if (path != undefined) {
		$.util.loadModule(path, true, true);

	} else {
		$.util.alert("页面加载失败,请检查是否配置正确的路径!");
	}

});

/**
 * select2帮助类
 *
 * @param repo
 * @returns {string}
 */
function formatRepo(repo) {
	var markup = "";
	if (repo.id != null) {
		markup += "<div class='select2-results__options'>" + repo.name
			+ "</div>";
	}
	return markup;
}


function formatRepoSelectionSys(repo) {
	$("input[name='systemCode']").val(repo.id);
	$("input[name='systemCode']").text(repo.name);
	return repo.name || repo.text;
}

function formatRepoSelectionApp(repo) {
	$("input[name='appName']").val(repo.id);
	return repo.name || repo.text;
}

function formatRepoSelectionIp(repo) {
	$("input[name='ip']").val(repo.id);
	return repo.name || repo.text;
}

function formatRepoSelectionClass(repo) {
	$("input[name='className']").val(repo.id);
	return repo.name || repo.text;
}

function formatRepoSelectionMethod(repo){
	$("input[name='methodName']").val(repo.id);
	return repo.name || repo.text;
}

function initSystemSelect(systemName,systemCode) {
	$('#systemSelect').empty().trigger("change");

	var select = $('#systemSelect').select2({
		placeholder : '默认全部',
		language : "zh-CN",
		//allowClear: true,
		ajax : {
			url : getContextPath() + "/common/getSystemList",
			dataType : 'json',
			data : function(term) {
				return term;
			},
			processResults : function(data) {
				return {
					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo,
		templateSelection : formatRepoSelectionSys,
		escapeMarkup : function(markup) {
			return markup;
		}

	}).on('select2:select', function (evt) {
		var systemCode = $('#systemSelect').val();
		initAppSelect(null, systemCode);
	});

	if (systemCode&& systemName) {
		var $option = $("<option selected>" + systemName + "</option>").val(
			systemCode);
		select.append($option).trigger('change');
	}

}

function initAppSelect(appName, systemCode) {
	$('#appSelect').empty().trigger("change");
	$('#classSelect').empty().trigger('change');
	$('#ipSelect').empty().trigger('change');
	$('#methodSelect').empty().trigger('change');

	var select = $('#appSelect').select2({
		placeholder : '默认全部',
		ajax : {
			url : getContextPath() + "/common/getAppList",
			dataType : 'json',
			data : function(term) {

				term.systemCode = systemCode;
				return term;
			},
			processResults : function(data) {
				return {
					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo,
		templateSelection : formatRepoSelectionApp,
		escapeMarkup : function(markup) {
			return markup;
		}
	}).on('select2:select', function (evt) {
		var appCode = $('#appSelect').val();
		initClassSelect(null, appCode);
		initMSIpSelect(null,appCode);
	});

	if (appName && appName != "") {
		var $option = $("<option selected>" + appName + "</option>").val(
			appName);
		select.append($option).trigger('change');
	}

}

function initClassSelect(className,appName) {
	$('#classSelect').empty().trigger('change');
	$('#methodSelect').empty().trigger('change');

	var dateFrom = $("input[name='datepicker1']").val();
	var dateTo = $("input[name='datepicker2']").val();
	if(!dateFrom){
		dateFrom = $.util.NdayAgo(3);
	}
	if(!dateTo){
		dateTo = new Date();
	}

	var select = $('#classSelect').select2({
		placeholder : '默认全部',
		tags:true,
		ajax : {
			url : getContextPath() + "/common/getClassList",
			dataType : 'json',
			data : function(term) {

				term.appCode = appName;
				term.dateFrom = dateFrom;
				term.dateTo = dateTo;
				return term;
			},
			processResults : function(data) {
				return {
					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo,
		templateSelection : formatRepoSelectionClass,
		escapeMarkup : function(markup) {
			return markup;
		}

	}).on('select2:select', function (evt) {
		var className = $('#classSelect').val();
		initMethodSelect(null,appName,className);

	});

	if (className && className != "") {
		var $option = $("<option selected>" + className + "</option>").val(
			className);
		select.append($option).trigger('change');
	}

}


function initMethodSelect(methodName,appName,className){
	$('#methodSelect').empty().trigger('change');

	var dateFrom = $("input[name='datepicker1']").val();
	var dateTo = $("input[name='datepicker2']").val();
	if(!dateFrom){
		dateFrom = $.util.NdayAgo(3);
	}
	if(!dateTo){
		dateTo = new Date();
	}

	var select = $('#methodSelect').select2({
		placeholder : '默认全部',
		ajax : {
			url : getContextPath() + "/common/getMethodList",
			dataType : 'json',
			data : function(term) {
				term.appCode = appName;
				term.className = className;
				term.dateFrom = dateFrom;
				term.dateTo = dateTo;
				return term;
			},
			processResults : function(data) {
				return {
					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo,
		templateSelection : formatRepoSelectionMethod,
		escapeMarkup : function(markup) {
			return markup;
		}

	});

	if (methodName && methodName != "") {
		var $option = $("<option selected>" + methodName + "</option>").val(
			methodName);
		select.append($option).trigger('change');
	}

}

function initIpSelect(ip) {
	$('#ipSelect').empty().trigger('change');

	var select = $('#ipSelect').select2({
		placeholder : '默认全部',
		ajax : {
			url : getContextPath() + "/mock/ip.json",
			dataType : 'json',
			data : function(term) {
				return term;
			},
			processResults : function(data) {
				return {

					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo, // omitted for brevity, see the source of this page
		templateSelection : formatRepoSelectionIp, // omitted for brevity, see the source of this page
		escapeMarkup : function(markup) {
			return markup;
		}

	});

	if (ip) {
		var $option = $("<option selected>" + ip + "</option>").val(ip);
		select.append($option).trigger('change');
	}
}

function initGWIpSelect(ip,componentName) {

	var select = $('#ipSelect').select2({
		placeholder : '默认全部',
		ajax : {
			url : getContextPath() + "/responseTime/getAllIP",
			dataType : 'json',
			data : function(term) {
				term.componentName = componentName;
				return term;
			},
			processResults : function(data) {

				return {

					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo, // omitted for brevity, see the source of this page
		templateSelection : formatRepoSelectionIp, // omitted for brevity, see the source of this page
		escapeMarkup : function(markup) {
			return markup;
		}

	});

	if (ip) {
		var $option = $("<option selected>" + ip + "</option>").val(ip);
		select.append($option).trigger('change');
	}
}


function initMSIpSelect(ip,appName) {

	$('#ipSelect').empty().trigger('change');
	var dateFrom = $("input[name='datepicker1']").val();
	var dateTo = $("input[name='datepicker2']").val();
	if(!dateFrom){
		dateFrom = $.util.NdayAgo(3);
	}
	if(!dateTo){
		dateTo = new Date();
	}
	var select = $('#ipSelect').select2({
		placeholder : '默认全部',
		ajax : {
			url : getContextPath() + "/common/getMSIpList",
			dataType : 'json',
			data : function(term) {
				term.appName = appName;
				term.dateFrom = dateFrom;
				term.dateTo = dateTo;
				return term;
			},
			processResults : function(data) {

				return {

					results : data.data
				}
			},
			cache : true

		},
		templateResult : formatRepo,
		templateSelection : formatRepoSelectionIp,
		escapeMarkup : function(markup) {
			return markup;
		}

	});

	if (ip && ip != "") {
		var $option = $("<option selected>" + ip + "</option>").val(ip);
		select.append($option).trigger('change');
	}
}
