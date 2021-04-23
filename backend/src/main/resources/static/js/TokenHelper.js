export default class TokenHelper {
	static roleAdmin = 'ROLE_ADMIN';
	
    static saveToken(token) {
        document.cookie = "access_token=" + token;
    }

    static getToken() {
		try {
			const token = document.cookie
	            .split('; ')
	            .find(row => row.startsWith('access_token='))
	            .split('=')[1];

	        return token; 
		} catch (err) {
			return '';
		}

    }

    static deleteToken() {
        document.cookie = "access_token=;";
    }

	static getUserId() {
		let payload = TokenHelper.getJsonPayload();
		if (payload != null) {
			return payload.jti;
		} else {
			return -1;
		}
	}
	
	static getJsonPayload() {
		try {
			let base64Url = TokenHelper.getToken().split('.')[1];
	        let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
	        let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
	            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	        }).join(''));
			return JSON.parse(jsonPayload)
		} catch(e) {
			return null;
		}
	}
	
	static isAdmin() {
		let payload = TokenHelper.getJsonPayload();
		if (payload != null) {
			return payload.authorities.includes(TokenHelper.roleAdmin);
		} else {
			return false;
		}
	}
}