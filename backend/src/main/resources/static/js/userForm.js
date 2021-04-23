import TokenHelper from './TokenHelper.js';

document.addEventListener("DOMContentLoaded",()=>{
    let url = window.location.origin + "/";
    let inputUsername = document.querySelector("#username");
    let inputPassword = document.querySelector("#password");
    let submitButton = document.querySelector("#submit");
	let action = document.querySelector("#form").getAttribute("action");

    inputPassword.addEventListener("keypress",keyPressPassword);
    inputUsername.addEventListener("focusout",checkUsername);
    submitButton.addEventListener("click",(e)=> submitFunction(e));

    async function submitFunction(e) {
        e.preventDefault();
		let fetchUrl = url;
		
		if (action == "register") {
			fetchUrl += "api/users/register";
		} else {
			fetchUrl += "api/users/login";
		}

        let userJson = {
            "username" : inputUsername.value,
            "password" : inputPassword.value
        };

        if (username != "" && password != "") {
            let response = await fetch(fetchUrl, {
                "method": "POST",
                "headers": { "Content-Type": "application/json" },
                "body": JSON.stringify(userJson)
            });

            if (response.ok) {
                let token = await response.text();
				TokenHelper.saveToken(token);
				window.location.replace(url);
            } else {
				inputUsername.classList.add("is-invalid");
			}
        } else {
            inputPassword.classList.remove("is-valid");
            inputPassword.classList.add("is-invalid");
        }
    }

    async function checkUsername() {
		if (action == "register") {
	        let username = inputUsername.value;
	        let response = await fetch(url + "api/users/username/" + username);

			if (!response.ok) {
	            inputUsername.classList.remove("is-invalid");
	            inputUsername.classList.add("is-valid");
	        } else {
	            inputUsername.classList.remove("is-valid");
	            inputUsername.classList.add("is-invalid");
	        }
		}
    }

    function keyPressPassword() {
        enableSubmitButton();
		if (action == "register")
        	inputPassword.classList.add("is-valid");
    }

    function enableSubmitButton() {
        submitButton.disabled = false;
    }

});