import Products from './Products.js';

document.addEventListener("DOMContentLoaded", ()=> {
	let p = new Products();
	p.onFinishEditProduct = onFinishEditProduct;
	p.onFinishDeleteProduct = onFinishDeleteProduct;
	
	const url = window.location.origin;
	let products;
	
	getProducts();
	
	async function getProducts() {
		let response = await fetch(url + "/api/categories/products");
		let productsJson = await response.json();
		
		products = { "products": productsJson };
		new Vue({
			el: "#table-products",
			data: {
				category: products
				},
			methods: {
				switchEditMode: (e)=> p.switchEditMode(e),
				edit: (e)=> p.edit(e),
				showModal: p.showModal,
				isLogged: p.isLogged
			}
		});
	}
	
	function onFinishEditProduct(product) {
		for (let i = 0; i < products.products.length; i++) {
			if (products.products[i].id == product.id) {
				products.products[i].name = product.name;
				products.products[i].price = product.price;
			}
		}
	}
	
	function onFinishDeleteProduct(id) {
		for (let i = 0; i < products.products.length; i++) {
			if (products.products[i].id == id) {
				products.products.splice(i,1);
				document.querySelector("#close-delete-product").click();
			}
		}
	}
	
});