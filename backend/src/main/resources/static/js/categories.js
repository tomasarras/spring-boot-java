import TokenHelper from './TokenHelper.js';
import Products from './Products.js';

document.addEventListener("DOMContentLoaded",()=> {
	let url = window.location.origin;
	let categoriesJson;
	let p = new Products();
	p.onFinishEditProduct = onFinishEditProduct;
	p.onFinishDeleteProduct = onFinishDeleteProduct;
	
	let inputNameCategory = document.querySelector("#js-input-name-category");;
	let inputEditNameCategory = document.querySelector("#edit-name-category");;
	let inputCreateProductPrice = document.querySelector("#create-category-price");
	let btnCreateCategory = document.querySelector("#js-btn-create-category");
	let btnDeleteCategory = document.querySelector("#btn-delete-category");
	let btnEditCategory = document.querySelector("#js-btn-edit-category");
	let btnCreateProduct = document.querySelector("#btn-create-product");
	
	btnDeleteCategory.addEventListener("click",deleteCategory);
	btnCreateProduct.addEventListener("click", createProduct);
	inputNameCategory.addEventListener("keypress", ()=> { btnCreateCategory.disabled = false; });
	inputEditNameCategory.addEventListener("keypress", ()=> { btnEditCategory.disabled = false; });
	inputCreateProductPrice.addEventListener("keypress", ()=> { btnCreateProduct.disabled = false; });
	btnCreateCategory.addEventListener("click", createCategory);
	btnEditCategory.addEventListener("click",editCategory);
	
	if (!p.isLogged()) {
		document.querySelector("#accordion-create").hidden = true;
	}
	
	getCategories();
	
	async function getCategories() {
		let response = await fetch(url + "/api/categories");
		categoriesJson = await response.json();
		for (let i = 0; i < categoriesJson.length; i++) {
			categoriesJson[i].products = [];
		}
		
		new Vue({
			el:'#accordion-categories',
			data: {
				categories: categoriesJson
				},
			methods: {
				expand: getProductsByIdCategory,
				showModalDelete: showModalDelete,
				showModalEdit: showModalEdit,
				showModalCreateProduct: showModalCreateProduct,
				switchEditMode: (e)=> p.switchEditMode(e),
				edit: (e)=> p.edit(e),
				showModal: p.showModal,
				isLogged: p.isLogged
				}
		});
	}
	
	async function getProductsByIdCategory(idCategory) {
		let response = await fetch(url + "/api/categories/" + idCategory + "/products");
		let productsJson = await response.json();
		for (let i = 0; i < categoriesJson.length; i++) {
			if (categoriesJson[i].id == idCategory) {
				categoriesJson[i].products = productsJson;								
			}
		}
	}
	
	async function createCategory() {
		let json = {
			"name" : inputNameCategory.value
		};
		
		let response = await fetch(url + "/api/categories",{
			"method": "POST",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                },
            "body" : JSON.stringify(json)
		});
		
		let categoryCreated = await response.json();
		categoriesJson.push(categoryCreated);
		inputNameCategory.value = '';
		btnCreateCategory.disabled = true;
	}
	
	async function createProduct() {
		let nameProduct = document.querySelector("#create-category-name").value;
		let idCategory = btnCreateProduct.getAttribute("name");
		
		let json = {
			"name" : nameProduct,
			"price" : inputCreateProductPrice.value
		};
		
		let response = await fetch(url + "/api/categories/" + idCategory + "/products",{
			"method": "POST",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                },
            "body" : JSON.stringify(json)
		});
		
		if (response.ok) {
			let productCreated = await response.json();
			onFinishCreateProduct(productCreated);
		}
	}
	
	async function editCategory() {
		let id = btnEditCategory.getAttribute("name");
		let json = {
			"name": inputEditNameCategory.value
		}
		
		let response = await fetch(url + "/api/categories/" + id,{
			"method": "PUT",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                },
			"body" : JSON.stringify(json)
		});
		
		if (response.ok) {
			json.id = id;
			onFinishEditCategory(json);
		}
	}
	
	async function deleteCategory() {
		let id = document.querySelector("#btn-delete-category").getAttribute("name");
		let response = await fetch(url + "/api/categories/" + id, {
			"method": "DELETE",
			"headers": {
                    "Content-Type": "application/json",
                    "Authorization": TokenHelper.getToken()
                }
		});
		
		if (response.ok) {
			onFinishDeleteCategory(id);
		}
	}
	
	function showModalDelete(category) {
		document.querySelector("#modal-delete-category").innerHTML = category.name;
		btnDeleteCategory.setAttribute("name",category.id);
	}
	
	function showModalEdit(category) {
		inputEditNameCategory.value = category.name;
		btnEditCategory.setAttribute("name",category.id);
	}
	
	function showModalCreateProduct(category) {
		btnCreateProduct.setAttribute("name",category.id);
	}
	
	function onFinishCreateProduct(product) {
		for (let i = 0; i < categoriesJson.length; i++) {
			if (product.category.id == categoriesJson[i].id) {
				categoriesJson[i].products.push(product);
				document.querySelector("#close-create-product").click();
			}	
		}
	}
	
	function onFinishEditCategory(category) {
		for (let i = 0; i < categoriesJson.length; i++) {
			if (category.id == categoriesJson[i].id) {
				categoriesJson[i].name = category.name;
				document.querySelector("#close-edit-category").click();
			}	
		}
	}
	
	function onFinishDeleteCategory(id) {
		for (let i = 0; i < categoriesJson.length; i++) {
			if (id == categoriesJson[i].id) {
				categoriesJson.splice(i,1);
				document.querySelector("#close-delete-category").click();
			}	
		}
	}
	
	function onFinishEditProduct(product) {
		for (let i = 0; i < categoriesJson.length; i++) {
			for (let j = 0; j < categoriesJson[i].products.length; j++) {
				if (categoriesJson[i].products[j].id == product.id) {
					categoriesJson[i].products[j].id = product.id;
					categoriesJson[i].products[j].name = product.name;
					categoriesJson[i].products[j].price = product.price;
				}
			}	
		}
	}
	
	function onFinishDeleteProduct(id) {
		for (let i = 0; i < categoriesJson.length; i++) {
			for (let j = 0; j < categoriesJson[i].products.length; j++) {
				if (categoriesJson[i].products[j].id == id) {
					categoriesJson[i].products.splice(j,1);
					document.querySelector("#close-delete-product").click();
				}
			}	
		}
	}
});