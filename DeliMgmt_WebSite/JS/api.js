// api.js

// Función para obtener todos los productos
export async function fetchProducts() {
  const response = await fetch('http://localhost:8081/api/inventory/listarProductos');
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
  console.log('Request body:', product); // Imprimir el cuerpo de la solicitud

  try {
    const response = await fetch('http://localhost:8081/api/inventory/crearProducto', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(product),
    });

    // Intentar obtener el contenido de la respuesta
    const contentType = response.headers.get('Content-Type');
    let responseBody = '';

    if (contentType && contentType.includes('application/json')) {
      // Intentar obtener JSON si el Content-Type es JSON
      responseBody = await response.json();
    } else {
      // Obtener el texto de la respuesta si no es JSON
      responseBody = await response.text();
    }

    // Imprimir el contenido de la respuesta
    console.log('Response body:', responseBody);

    // Verificar el estado de la respuesta
    if (!response.ok) {
      throw new Error(`Network response was not ok: ${response.status}`);
    }
  } catch (error) {
    console.error('Error adding product:', error);
  }
}

// Función para actualizar un producto
export async function updateProduct(id, product) {
  const response = await fetch(`http://localhost:8081/api/inventory/Actualizar`, {
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
  try {
      const response = await fetch(`http://localhost:8081/api/inventory/eliminarProducto/${id}`, {
          method: 'DELETE',
          headers: {
              'Content-Type': 'application/json'
          }
      });
      if (!response.ok) {
          throw new Error('Network response was not ok');
      }
      // Maneja la respuesta si es necesario
  } catch (error) {
      console.error('Error al eliminar el producto:', error);
  }
}

