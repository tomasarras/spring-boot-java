import TokenHelper from './TokenHelper.js';
let url = window.location.origin;
let usersGlobal;

getUsers((users)=> {
	usersGlobal = users;
	new Vue({
        el: "#users-table",
        data: {
            users: usersGlobal,
			userId : TokenHelper.getUserId()
        },
		methods: {
			toggle: toggleSwitch,
			showModalDeleteUser: showModalDeleteUser
		}
    });
});

async function getUsers(callback) {
	let t = TokenHelper.getToken();
	let response = await fetch(url + "/api/users", {
		"method": "GET",
		"headers": {
                "Content-Type": "application/json",
                "Authorization": t
            }
	});
	
	let jsonUsers = await response.json();
	
	callback(jsonUsers);
}

document.addEventListener("click", ()=> {
	let btnDeleteUser = document.querySelector("#btn-delete-user");
	btnDeleteUser.addEventListener("click", deleteUser);
	
	async function deleteUser() {
		let id = btnDeleteUser.getAttribute("name");
		let response = await fetch(url + "/api/users/" + id, {
			"method": "DELETE",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                }
		});
		
		onFinishDeleteUser(id);
	}
	
	function onFinishDeleteUser(id) {
		for (let i = 0; i < usersGlobal.length; i++) {
			if (usersGlobal[i].id == id) {
				usersGlobal.splice(i,1);
				document.querySelector("#close-delete-user").click();
			}	
		}
	}
});

async function toggleSwitch(e, user) {
	let toggle = e.target;
	let mainParent = toggle.parentElement;
	mainParent.classList.toggle("active");
	let response = await fetch(url + "/api/users/" + user.id, {
		"method": "PUT",
		"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                }
	});
}

function showModalDeleteUser(user) {
	document.querySelector("#modal-delete-user").innerHTML = user.username;
	document.querySelector("#btn-delete-user").setAttribute("name", user.id);
}