const apiUrl = 'http://localhost:8081/api/inventory';

// Obtener la lista de productos
export async function listProducts() {
    const response = await fetch(`${apiUrl}/Listar`);
    if (!response.ok) {
        throw new Error('Error fetching products');
    }
    return await response.json();
}

// Crear un nuevo producto en el inventario
export async function createProduct(product) {
    const response = await fetch(`${apiUrl}/crear`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(product),
    });
    if (!response.ok) {
        throw new Error('Error creating product');
    }
    return await response.json();
}

// Eliminar un producto del inventario
export async function deleteProduct(productId) {
    const response = await fetch(`${apiUrl}/eliminar/${productId}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Error deleting product');
    }
}
// api.js

// Obtener categorías desde la API
async function fetchCategories() {
    try {
      const response = await fetch('http://localhost:8080/api/category/Listar');
      if (response.ok) {
        const categories = await response.json();
        return categories;
      } else {
        console.error("Error al obtener categorías");
      }
    } catch (error) {
      console.error("Error de red:", error);
    }
  }
  
  // Crear una nueva categoría a través de la API
  async function createCategory(categoryName) {
    try {
      const response = await fetch('http://localhost:8080/api/category/crear', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
        body: JSON.stringify({ nombre: categoryName })  // Cambia el campo según tu modelo de categoría
      });
  
      if (response.ok) {
        console.log("Categoría creada exitosamente");
        return await response.json();
      } else {
        console.error("Error al crear la categoría");
      }
    } catch (error) {
      console.error("Error de red:", error);
    }
  }
  
