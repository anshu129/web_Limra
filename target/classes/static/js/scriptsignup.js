document.getElementById("myForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
    
    // Retrieve form values
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var contactNo = document.getElementById("contactNo").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var referralId = document.getElementById("referralId").value;
  
    
    // Display form values (for demonstration purposes)
    console.log("First Name:", firstName);
    console.log("Last Name:", lastName);
    console.log("Contact No:", contactNo);
    console.log("Email:", email);
    console.log("Password:", password);
    console.log("Confirm Password:", confirmPassword);
    console.log("Referral ID:", referralId);
  });
  