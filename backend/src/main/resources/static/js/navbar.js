import TokenHelper from './TokenHelper.js';

const token = TokenHelper.getToken();
const isLogged = token != '';
const isAdmin = TokenHelper.isAdmin()

document.addEventListener("DOMContentLoaded",()=> {
	
	new Vue({
	el:'#navbar',
	data: {
		logged: isLogged,
		admin: isAdmin
		}
	});
	
	let btnSignOff = document.querySelector("#btn-sign-off");
	try {
		btnSignOff.addEventListener("click",signOff);
	} catch(e){}
	
	function signOff() {
		TokenHelper.deleteToken();
		window.location.replace(window.location.origin + "/login?logout");
	}
});