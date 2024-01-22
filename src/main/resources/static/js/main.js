(function($) {

	"use strict";

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	$(".toggle-password").click(function() {
	  $(this).toggleClass("fa-eye fa-eye-slash");
	  var input = $($(this).attr("toggle"));
	  if (input.attr("type") == "password") {
	    input.attr("type", "text");
	  } else {
	    input.attr("type", "password");
	  }
	});
	$('#myForm').submit(function(event) {
      var password = $('#password').val();
      var confirmPassword = $('#confirmPassword').val();

      if (password != confirmPassword) {
        alert('Password and Confirm Password do not match');
        event.preventDefault(); // Prevent form submission
      }
    });

})(jQuery);
