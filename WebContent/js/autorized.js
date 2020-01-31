function Autorized(usr,link){

		$.ajax({
			url : "../../smautorized",
			data : {
				usr : usr,
				link : link
			},
			success : function(responseText) {
				if (responseText == "NOTAUTOR"){
					alertify.error(err);
					return;
				}

				location.href=link;
				nascondi_loading_screen();
				mostra_loading_screen();
			}
		});
}

function Caricamento(){
	nascondi_loading_screen();
	mostra_loading_screen();
}


