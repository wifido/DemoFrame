
$(document).ready(function() {
	window.addEventListener('popstate',function (event) {

		var state = event.state;
		var json = window.history.state;

		if (state == null) {
			//if(state == null ) {alert("state null");}
			return;
		}
		if (state == "/main/index") {
			$.util.loadDashboard()
		} else {
			$.util.loadMain(state, false, false);
		}
	});

	
	// === Resize window related === //
	$(window).resize(function () {
		var ul = $('#sidebar > ul');

		if ($(window).width() < 481) {
			ul.css({'display': 'none'});
			fix_position();
		}
		if ($(window).width() > 480) {
			/*$('#user-nav > ul').css({width:'auto',margin:'0'});*/
			$('#content-header .btn-group').css({width: 'auto'});
			ul.css({'display': 'block'});

		}
	});

	var ul = $('#sidebar > ul');
	if ($(window).width() < 481) {
		ul.css({'display': 'none'});
		fix_position();
	}

	if ($(window).width() > 480) {

		$('#content-header .btn-group').css({width: 'auto'});
		ul.css({'display': 'block'});
	}

});


$(document).on('click','#sidebar > a ',function(){
	var ul = $('#sidebar > ul');

	event.preventDefault();
	var sidebar = $('#sidebar');
	if(sidebar.hasClass('open'))
	{
		sidebar.removeClass('open');
		ul.slideUp(250);
	} else
	{
		sidebar.addClass('open');
		ul.slideDown(250);
	}
});




$(document).on('click','.submenu > a',function(){
	
	event.preventDefault();
	var submenu = $(this).siblings('ul');
	var li = $(this).parents('li');
	var submenus = $('#sidebar li.submenu ul');
	var submenus_parents = $('#sidebar li.submenu');
	if(li.hasClass('open'))
	{
		if(($(window).width() > 768) || ($(window).width() < 479)) {
			submenu.slideUp();
		} else {
			submenu.fadeOut(250);
		}
		li.removeClass('open');
	} else
	{
		if(($(window).width() > 768) || ($(window).width() < 479)) {
			submenus.slideUp();
			submenu.slideDown();
		} else {
			submenus.fadeOut(250);
			submenu.fadeIn(250);
		}
		submenus_parents.removeClass('open');
		li.addClass('open');
	}

});

