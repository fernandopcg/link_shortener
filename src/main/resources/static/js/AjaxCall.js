
    function createUrl() {

        var rest_endpoint = "urls/";
        /*var dataToBeSent = { originalUrl: $('#url-input').val(),
        					 customUrl : $('#custom-short-url').val() };*/
        
        var dataToBeSent = "originalUrl=" + $('#url-input').val().replace("&", "\&") + "&" + "customUrl=" + $('#custom-short-url').val();
        console.og

        $.ajax({
            type: "POST",
            timeout: 3000,
            url: rest_endpoint,
            //dataType: "json",
            //contentType: "application/json",
            data: dataToBeSent,
            success: function (data, text, jqXHR ) {
            	console.log(data);
            	console.log(text);
            	console.log(jqXHR);
                var json = JSON.parse(JSON.stringify(data));
              
               
	
	                base_url = window.location.href; 
					
	                $('#original-url-placeholder').text(json['originalUrl']);
	                $('#original-url-placeholder-link').attr('href', json['originalUrl']);
	                $('#short-url-creation-date').text(new Date(json['creationDate']));
	                $('#short-url-placeholder').text(base_url + json['id']);
	                $('#short-url-placeholder-link').attr('href', base_url + json['id']);
	                
	                
	                $('#myModal').modal({
	                    keyboard: true,
	                });
                
            },
        	complete: function(jqXHR, textStatus){
        		console.log("Finished request");
        		console.log(jqXHR);
        		
        		console.log(textStatus);
        		
        	}}).fail( function( jqXHR, textStatus, errorThrown ) {

            if (jqXHR.status === 0) {
                alert('Internal error: Database not reachable.');
            } else if (jqXHR.status == 404) {
                alert('Requested page not found [404]');
            } else if (jqXHR.status == 409) {
    			
    			$('#myErrorModal').modal({
                    keyboard: true,
                });
    			
    			
            } else if (jqXHR.status == 500) {
                alert('Internal Server Error [500].');
            } else if (textStatus === 'parsererror') {
                alert('Requested JSON parse failed.');
            } else if (textStatus === 'timeout') {
                alert('Time out error.');
            } else if (textStatus === 'abort') {
                alert('Ajax request aborted.');
            } else {
                alert('Uncaught Error: ' + jqXHR.responseText);
            }
        });
    }

    $(document).ready(function () {

        $("#shorten-button").click(function () {
            createUrl();
        });

        $("#url-input").keypress(function (e) {
            if (e.which == 13) {
                createUrl();
            }
        });

    });