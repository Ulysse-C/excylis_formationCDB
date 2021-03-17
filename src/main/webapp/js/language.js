$('#language').change(function() {
	var url = new URL(window.location.href);
	url.searchParams.set('lang', $(this).val());
	window.location.replace(url);
})
