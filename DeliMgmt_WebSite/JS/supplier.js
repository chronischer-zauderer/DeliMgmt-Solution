// supplier.js

// URL base de la API (ajusta según sea necesario)
const API_BASE_URL = 'http://localhost:8081/api/supplier';

// Función para obtener todos los proveedores
export async function fetchSuppliers() {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`${API_BASE_URL}/Listar`, {
      headers: {
        'Authorization': `Bearer ${token}`, // Agregado encabezado de autenticación
        'Accept': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error(`Error al obtener los proveedores: ${response.status} - ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error('Error fetching suppliers:', error);
    return []; // Devuelve un arreglo vacío en caso de error
  }
}


// Función para crear un nuevo proveedor
export async function createSupplier(supplier) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`${API_BASE_URL}/crear`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}` // Agregado encabezado de autenticación
      },
      body: JSON.stringify(supplier)
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return await response.json();
  } catch (error) {
    console.error('Error creating supplier:', error);
  }
}


// Función para actualizar un proveedor existente
export async function updateSupplier(supplier) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`${API_BASE_URL}/Actualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}` // Agregado encabezado de autenticación
      },
      body: JSON.stringify(supplier)
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return await response.json();
  } catch (error) {
    console.error('Error updating supplier:', error);
  }
}


// Función para eliminar un proveedor
export async function deleteSupplier(id) {
  const token = localStorage.getItem('token'); // O donde almacenes el token
  if (!token) {
    throw new Error('Token is missing. Please authenticate first.');
  }

  try {
    const response = await fetch(`${API_BASE_URL}/eliminar/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Authorization': `Bearer ${token}` // Agregado encabezado de autenticación
      }
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
  } catch (error) {
    console.error('Error deleting supplier:', error);
  }
}

