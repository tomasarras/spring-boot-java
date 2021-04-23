import TokenHelper from './TokenHelper.js';

export default class Products {
	onFinishEditProduct;
	onFinishDeleteProduct;
	url = window.location.origin;
	
	constructor() {
		let btnDeleteProduct = document.querySelector("#btn-delete-product");
		btnDeleteProduct.addEventListener("click",this.deleteProduct.bind(this));
	}
	
	switchEditMode(e) {
		let tr = this.getTr(e);
			
		let elements = tr.querySelectorAll(".js-editMode");
		for (let i = 0; i < elements.length; i++) {
			elements[i].classList.toggle("hidden");
		}
	}
	
	getTr(e) {
		let elem = e.target;
		while (elem.tagName != "TR")
			elem = elem.parentElement;
			
		return elem;
	}
	
	showModal(product) {
		document.querySelector("#modal-delete-product").innerHTML = product.name;
		document.querySelector("#btn-delete-product").setAttribute("name", product.id);
	}
	
	async edit(e) {
		let tr = this.getTr(e);
		
		let name = tr.querySelector(".js-edit-name").value;
		let price = tr.querySelector(".js-edit-price").value;
		let idCategory = tr.querySelector(".js-idCategory").value;
		let id = tr.querySelector(".js-product-id").innerHTML;
		
		let json = {
			"name" : name,
			"price" : price,
			"idCategory" : idCategory
		}
		
		let response = await fetch(this.url + "/api/categories/products/" + id,{
			"method": "PUT",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                },
            "body" : JSON.stringify(json)
		});
		
		
		if (response.ok) {
			let productEdited = await response.json();
			this.switchEditMode(e);
			this.onFinishEditProduct(productEdited);
		}
	}
	
	async deleteProduct() {
		let id = document.querySelector("#btn-delete-product").getAttribute("name");
		let response = await fetch("/api/categories/products/" + id, {
			"method": "DELETE",
			"headers": {
                "Content-Type": "application/json",
                "Authorization": TokenHelper.getToken()
            }
		});
		
		if (response.ok) {
			this.onFinishDeleteProduct(id);
		}		
	}
	
	isLogged() {
		return TokenHelper.getToken() != ''
	}
}