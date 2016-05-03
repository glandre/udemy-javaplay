/*
 * Function to handle deletion from the list
 */
function del(delUrl) {
	$.ajax( {
		url: delUrl,
		type: 'DELETE',
		success: function(results) {
			location.reload();
		}
	});
}