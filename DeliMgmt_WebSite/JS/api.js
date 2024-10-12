// api.js

// Función para obtener todos los productos
export async function fetchProducts() {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch('http://localhost:8081/api/inventory/listarProductos', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error(`Network response was not ok: ${response.status} - ${response.statusText}`);
    }

    return response.json();
  } catch (error) {
    console.error('Error fetching products:', error);
    throw error; // Re-lanza el error para que pueda ser manejado donde se llama a esta función
  }
}

export async function fetchProduct(id) {
  const token = localStorage.getItem('token'); // Asegúrate de que el token esté disponible
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`http://localhost:8081/api/inventory/listarPorId/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Network response was not ok: ${response.status} - ${errorText}`);
    }

    return await response.json();
  } catch (error) {
    console.error('Error fetching product:', error);
    throw error; // Re-lanza el error para que pueda ser manejado donde se llama a esta función
  }
}



// Función para obtener movimientos de inventario por producto
export async function fetchInventoryMovementsByProduct(productId) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`http://localhost:8081/api/inventory/listarPorProducto/${productId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error(`Network response was not ok: ${response.status} - ${response.statusText}`);
    }

    return response.json();
  } catch (error) {
    console.error('Error fetching inventory movements by product:', error);
    throw error; // Re-lanza el error para que pueda ser manejado donde se llama a esta función
  }
}


// Función para crear un nuevo producto
export async function createProduct(product) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  console.log('Request body:', product); // Imprimir el cuerpo de la solicitud

  try {
    const response = await fetch('http://localhost:8081/api/inventory/crearProducto', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`, // Agregado encabezado de autenticación
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
      throw new Error(`Network response was not ok: ${response.status} - ${responseBody}`);
    }
  } catch (error) {
    console.error('Error adding product:', error);
  }
}


// Función para actualizar un producto
export async function updateProduct(id, product) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`http://localhost:8081/api/inventory/actualizarProducto`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`, // Agregado encabezado de autenticación
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(product),
    });

    if (!response.ok) {
      throw new Error(`Error al actualizar el producto: ${response.status} - ${response.statusText}`);
    }
  } catch (error) {
    console.error('Error updating product:', error);
    throw error; // Lanza el error para manejarlo en la llamada de la función
  }
}


// Función para eliminar un producto
export async function deleteProduct(id) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`http://localhost:8081/api/inventory/eliminarProducto/${id}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`, // Agregado encabezado de autenticación
        'Content-Type': 'application/json'
      }
    });
    
    if (!response.ok) {
      throw new Error(`Network response was not ok: ${response.status}`);
    }
    
    // Maneja la respuesta si es necesario
  } catch (error) {
    console.error('Error al eliminar el producto:', error);
  }
}


