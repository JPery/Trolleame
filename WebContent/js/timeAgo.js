'use strict';

var timeAgo = {};

timeAgo.setTimeAgoNoticia = function(noticia) {
	var timeAgo = "hace ";
	var dateString = noticia.dateStamp + "T" + noticia.timeStamp;
	var comment = Date.parse(dateString);
	comment = comment.getTime() / 1000;
	console.log(comment);
	console.log(dateString);
	var d = new Date();
	var current = d.getTime() / 1000;
	var difference = current - comment;
	if (difference < 60) {
		timeAgo += Math.floor(difference).toFixed(0);
		timeAgo += " segundo";
	} else if (difference < 3600) {
		difference /= 60;
		timeAgo += Math.floor(difference).toFixed(0);
		timeAgo += " minuto";
	} else if (difference < 86400) {
		difference /= 3600;
		timeAgo += Math.floor(difference).toFixed(0);
		timeAgo += " hora";
	} else {
		difference /= 86400;
		timeAgo += Math.floor(difference).toFixed(0);
		timeAgo += " día";
	}
	if (difference >= 2 || difference < 1)
		timeAgo += "s";
	noticia.timeAgo = timeAgo;
	var date = new Date(noticia.dateStamp);
	noticia.dateStamp = date.toString('d-MM-yyyy') ;
};