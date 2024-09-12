// api.js

// Función para obtener todos los productos
export async function fetchProducts() {
  const response = await fetch('http://localhost:8081/api/inventory/listarProductos'); // Cambia esta ruta si es necesario
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
}

// Función para obtener movimientos de inventario por producto
export async function fetchInventoryMovementsByProduct(productId) {
  const response = await fetch(`http://localhost:8081/api/inventory/listarPorProducto/${productId}`);
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
}

// Función para crear un nuevo producto
export async function createProduct(product) {
  const response = await fetch('http://localhost:8081/api/inventory/crearProducto', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(product),
  });
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
}

// Función para actualizar un producto
export async function updateProduct(id, product) {
  const response = await fetch(`http://localhost:8081/api/inventory/actualizarProducto/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(product),
  });
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
}

// Función para eliminar un producto
export async function deleteProduct(id) {
  const response = await fetch(`http://localhost:8081/api/inventory/eliminarProducto/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
}
