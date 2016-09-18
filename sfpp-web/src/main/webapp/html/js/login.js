
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
//		return unescape(arr[2]);
		return decodeURI(arr[2]);
	else
		return null;
}


$(document).ready(function() {
    // Fullscreen background
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    // Form validation
    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    	$('#info').hide();
    });
    
    var message = getCookie("loginMessage");
    if ( message != '""') {
    	$("#info").html(message);
    	$("#info").show();
    	window.history.pushState('login', '',null )//getBasePath()+ 'login')
    }
    
//    var loginFlag = getCookie("loginFlag");
//    if ( loginFlag == "true") {
//    	document.location = "main/index";		//最前面不写'/'，表示为相对路径，写了表示为绝对路径
//	}
    
    
});


$.login = {
		submit : function() {
			var ok = true;
			$('.login-form').find('input[type="text"], input[type="password"], textarea').each(function(){
	    		if( $(this).val() == "" ) {
	    			$(this).addClass('input-error');
	    			ok = false;
	    		} else {
	    			$(this).removeClass('input-error');
	    		}
	    	});
			return ok;
		},
		
}
