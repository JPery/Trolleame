var visual = {};
visual.enabled = true;
visual.funciones = {
	showEditForm : function(e) {
		if (visual.enabled) {
			var commentWrapper = $(e).closest(".comentariotexto");
			$(commentWrapper).toggle();
			$(commentWrapper).siblings(".formularioEditar").toggle();
			commentWrapper = $(commentWrapper).siblings(".formularioEditar");
			$(commentWrapper).children(".edit-comment-form").children(
					"textarea").toggle();
			$(commentWrapper).children(".edit-comment-form").children(
					".formbutton").children("input").toggle();
			$(commentWrapper).children(".edit-comment-form").children(
					".formbutton").css("display", "block");
			$(commentWrapper).children(".edit-comment-form").children(
					"textarea").css("display", "block");
		}
	},
	enable : function() {
		visual.enabled = true;
	},
	disable : function(){
		visual.enabled = false;
	}
}