+function ($) { "use strict";

  $(function(){
 	
	// sparkline
	var sr, sparkline = function($re){
		$(".sparkline").each(function(){
			var $data = $(this).data();
			if($re && !$data.resize) return;
			($data.type == 'pie') && $data.sliceColors && ($data.sliceColors = eval($data.sliceColors));
			($data.type == 'bar') && $data.stackedBarColor && ($data.stackedBarColor = eval($data.stackedBarColor));
			$data.valueSpots = {'0:': $data.spotColor};
			$(this).sparkline('html', $data);
		});
	};
	$(window).resize(function(e) {
		clearTimeout(sr);
		sr = setTimeout(function(){sparkline(true)}, 500);
	});
	sparkline(false);

	// easypie
	var easypie = function(){
	$('.easypiechart').each(function(){
		var $this = $(this), 
		$data = $this.data(), 
		$step = $this.find('.step'), 
		$target_value = parseInt($($data.target).text()),
		$value = 0;
		$data.barColor || ( $data.barColor = function($percent) {
	        $percent /= 100;
	        return "rgb(" + Math.round(200 * $percent) + ", 200, " + Math.round(200 * (1 - $percent)) + ")";
	    });
		$data.onStep =  function(value){
			$value = value;
			$step.text(parseInt(value));
			$data.target && $($data.target).text(parseInt(value) + $target_value);
		}
		$data.onStop =  function(){
			$target_value = parseInt($($data.target).text());
			$data.update && setTimeout(function() {
		        $this.data('easyPieChart').update(100 - $value);
		    }, $data.update);
		}
			$(this).easyPieChart($data);
		});
	};
	easypie();
  
	// datepicker
	$(".datepicker-input").each(function(){ $(this).datepicker();});

	// dropfile
	$('.dropfile').each(function(){
		var $dropbox = $(this);
		if (typeof window.FileReader === 'undefined') {
		  $('small',this).html('File API & FileReader API not supported').addClass('text-danger');
		  return;
		}

		this.ondragover = function () {$dropbox.addClass('hover'); return false; };
		this.ondragend = function () {$dropbox.removeClass('hover'); return false; };
		this.ondrop = function (e) {
		  e.preventDefault();
		  $dropbox.removeClass('hover').html('');
		  var file = e.dataTransfer.files[0],
		      reader = new FileReader();
		  reader.onload = function (event) {
		  	$dropbox.append($('<img>').attr('src', event.target.result));
		  };
		  reader.readAsDataURL(file);
		  return false;
		};
	});

	// slider
	$('.slider').each(function(){
		$(this).slider();
	});

	// sortable
	if ($.fn.sortable) {
	  $('.sortable').sortable();
	}

	// slim-scroll
	$('.no-touch .slim-scroll').each(function(){
		var $self = $(this), $data = $self.data(), $slimResize;
		$self.slimScroll($data);
		$(window).resize(function(e) {
			clearTimeout($slimResize);
			$slimResize = setTimeout(function(){$self.slimScroll($data);}, 500);
		});
    $(document).on('updateNav', function(){
      $self.slimScroll($data);
    });
	});	

	// portlet
	$('.portlet').each(function(){
		$(".portlet").sortable({
	        connectWith: '.portlet',
            iframeFix: false,
            items: '.portlet-item',
            opacity: 0.8,
            helper: 'original',
            revert: true,
            forceHelperSize: true,
            placeholder: 'sortable-box-placeholder round-all',
            forcePlaceholderSize: true,
            tolerance: 'pointer'
	    });
    });

	// docs
  $('#docs pre code').each(function(){
	    var $this = $(this);
	    var t = $this.html();
	    $this.html(t.replace(/</g, '&lt;').replace(/>/g, '&gt;'));
	});

	// table select/deselect all
	$(document).on('change', 'table thead [type="checkbox"]', function(e){
		e && e.preventDefault();
		var $table = $(e.target).closest('table'), $checked = $(e.target).is(':checked');
		$('tbody [type="checkbox"]',$table).prop('checked', $checked);
	});

	// random progress
	$(document).on('click', '[data-toggle^="progress"]', function(e){
		e && e.preventDefault();

		var $el = $(e.target),
		$target = $($el.data('target'));
		$('.progress', $target).each(
			function(){
				var $max = 50, $data, $ps = $('.progress-bar',this).last();
				($(this).hasClass('progress-xs') || $(this).hasClass('progress-sm')) && ($max = 100);
				$data = Math.floor(Math.random()*$max)+'%';
				$ps.css('width', $data).attr('data-original-title', $data);
			}
		);
	});
	
	// add notes
	function addMsg($msg){
		var $el = $('.nav-user'), $n = $('.count:first', $el), $v = parseInt($n.text());
		$('.count', $el).fadeOut().fadeIn().text($v+1);
		$($msg).hide().prependTo($el.find('.list-group')).slideDown().css('display','block');
	}
	var $msg = '<a href="#" class="media list-group-item">'+
                  '<span class="pull-left thumb-sm text-center">'+
                    '<i class="fa fa-envelope-o fa-2x text-success"></i>'+
                  '</span>'+
                  '<span class="media-body block m-b-none">'+
                    'Sophi sent you a email<br>'+
                    '<small class="text-muted">1 minutes ago</small>'+
                  '</span>'+
                '</a>';	
	
    //setTimeout(function(){addMsg($msg);}, 1500);

	//chosen
	$(".chosen-select").length && $(".chosen-select").chosen();
	
  });
 
}(window.jQuery);


// Controllo Login Modal
function controlLogin(usr, pwd) {
  //alert(usr+" "+pwd);
  window.location.href = "../../login-esegui.htm?username="+usr+"&password="+pwd;
}

//Controllo Login Modal
function controlLoginGem(usr, pwd) {
  //alert(usr+" "+pwd);
  window.location.href = "../../login-esegui.htm?tipoLogin=GEM&username="+usr+"&password="+pwd;
}



//--------------
function getClie(cdcli, tipo) {
	
	/*
	$.ajax({
	  type: "POST",
	  url: "clifa-dett.jsp",
	  data: "cdcli="+cdcli+"&tipo="+tipo,
	  dataType: "html",
	  success: function(msg){
		$("div#clie-dett").css('background-image', 'none');
	    $("div#clie-dett").html(msg);
	  },
	  error: function(){
	    alert("Chiamata fallita!!!");
	  }
	});
	*/
	
	// Setta il tipo
	$('#tipoForm').val(tipo);
	
	if(tipo=='I') {
		$('#idcli').html('<strong>Inserimento</strong> Nuovo Cliente di Fatturazione');
	}
	
	if(tipo=='V') {
		$('#idcli').html('<strong>Visualizza</strong> Cliente di Fatturazione');
	}
	
	if(tipo=='M') {
		$('#idcli').html('<strong>Modifica</strong> Cliente di Fatturazione');
	}
	
	if(tipo=='I') {
		
		// Setta il valore dei campi
		$('#cdage').val('');
		
		$('#cdcli').val('');
		$('#ragso').val('');
		$('#indcl').val('');
		$('#capcl').val('');
		$('#procl').val('');
		$('#loccl').val('');
		$('#catve').val('');
		$('#czona').val('');
		$('#cpaiv').val('');
		$('#cdfis').val('');
		$('#modco').val('');
		$('#copag').val('');
		$('#percl').val('');
		$('#ntecl').val('');
		$('#faxcl').val('');
		$('#inecl').val('');
		$('#catve').val('');
		$('#dscr2').val('');
		$('#pec').val('');
		$('#mailfa').prop( "checked", false );
		$('#inefat').val('');
		
		$('#msg').html('');
		$('#sped').html('');
		
	}
	else {
		
		$.ajax({
			url: 'clifa-dett.jsp?cdcli='+cdcli+'&tipo='+tipo,
			dataType: 'json',
			json: 'jsoncallback',
			timeout: 50000,
			beforeSend: function(){
				
			},
			complete: function(){
				
			},
			success: function(data, status){
				$.each(data, function(i,item){ 
					
					// Setta il valore dei campi
					$('#cdage').val(item.CDAGE);
					
					$('#cdcli').val(item.CDCLI);
					$('#ragso').val(item.RAGSO);
					$('#indcl').val(item.INDCL);
					$('#capcl').val(item.CAPCL);
					$('#procl').val(item.PROCL);
					$('#loccl').val(item.LOCCL);
					$('#catve').val(item.CATVE);
					$('#czona').val(item.CZONA);
					$('#cpaiv').val(item.CPAIV);
					$('#cdfis').val(item.CDFIS);
					$('#modco').val(item.MODCO);
					$('#copag').val(item.COPAG);
					$('#percl').val(item.PERCL);
					$('#ntecl').val(item.NTECL);
					$('#faxcl').val(item.FAXCL);
					$('#inecl').val(item.INECL);
					$('#catve').val(item.CATVE);
					$('#dscr2').val(item.DSCR2);
					$('#pec').val(item.PEC);
					
					if(item.MAILFA=='S') {
						$('#mailfa').prop( "checked", true );
					}
					$('#inefat').val(item.INEFAT);
					
					// Array Clienti di Spedizione
					getClifaClisp(item.CDCLI);
					
				});
				
			},
			error: function (xhr, ajaxOptions, thrownError) {
				
				alert(xhr.status);
		        alert(thrownError);
		        
			}
		});
	}
}

function getClifaClisp(clifa) {
	
	//alert(clifa);
	
	var $ul = $('#sped');
		html = "";
		
	$.ajax({
		url: 'clifa-clisp-dett.jsp?cdcli='+clifa,
		dataType: 'json',
		json: 'jsoncallback',
		timeout: 50000,
		beforeSend: function(){
			$ul.html("");
			
			$ul.html("<div class=\"wrapper\"><h3 class=\"font-thin m-t-xs\">Attendere... caricamento risultati in corso...</h3></div>");
			
		},
		complete: function(){
			
		},
		success: function(data, status){
			
			html = "";
			$ul.html("");
			
			html+="<ul class=\"list-group list-group-lg no-radius no-border no-bg m-t-n-xxs m-b-none auto\">";
			
			$.each(data, function(i,item){ 
				
				html += "<li class=\"list-group-item\">" +
                      	"<span class=\"pull-left thumb-xs m-t-xs avatar m-l-xs m-r-sm\">" +
                        "<img src=\"../../images/default-icon-user.png\" alt=\"...\">" + 
                        "</span>" + 
                        "<div class=\"clear\">" + 
                        "<div><a href=\"#\">"+item.CDCLI+"</a></div>" + 
                        "<small class=\"text-muted\">"+item.DSCR1+" "+item.RAGSO+"</small>" + 
                        "</div>" + 
                        "</li>";
				
			});
			
			html+="</ul>";
			
			$ul.html( html );
		},
		error: function (xhr, ajaxOptions, thrownError) {
			
			alert(xhr.status);
	        alert(thrownError);
	        
		}
	});
}




//ricerca all'interno della tabella
function jqueryRic() {
	
	$.expr[':'].containsIgnoreCase = function(n,i,m){
		return jQuery(n).text().toUpperCase().indexOf(m[3].toUpperCase())>=0;
	};
	 
	$("#searchInput").keyup(function(){

    	$("#fbody").find("tr").hide();
    	var data = this.value.split(" ");
    	var jo = $("#fbody").find("tr");
    	$.each(data, function(i, v){

       	//Use the new containsIgnoreCase function instead
        	jo = jo.filter("*:containsIgnoreCase('"+v+"')");
    	});
       	jo.show();

    }).focus(function(){
         this.value="";
         $(this).css({"color":"black"});
         $(this).unbind('focus');
    }).css({"color":"#C0C0C0"});
	
	return;
}

function post(path, params, method='post') {

  // The rest of this code assumes you are not using a library.
  // It can be made less wordy if you use one.
  const form = document.createElement('form');
  form.method = method;
  form.action = path;

  for (const key in params) {
    if (params.hasOwnProperty(key)) {
      const hiddenField = document.createElement('input');
      hiddenField.type = 'hidden';
      hiddenField.name = key;
      hiddenField.value = params[key];

      form.appendChild(hiddenField);
    }
  }

  document.body.appendChild(form);
  form.submit();
}



