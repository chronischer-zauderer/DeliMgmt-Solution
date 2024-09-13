const apiUrl = 'http://localhost:8081/api/category'; // URL base de la API de categorías

// Función para obtener todas las categorías
export async function fetchCategories() {
  try {
    const response = await fetch(`${apiUrl}/Listar`, {
      headers: {
        'Accept': 'application/json'
      }
    });
    if (!response.ok) {
      throw new Error('Error al obtener las categorías');
    }
    const categories = await response.json();
    return categories;
  } catch (error) {
    console.error('Error fetching categories:', error);
    return [];
  }
}

// Función para crear una nueva categoría
export async function createCategory(category) {
  try {
    const response = await fetch(`${apiUrl}/crear`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(category)
    });
    if (!response.ok) {
      throw new Error('Error al crear la categoría');
    }
    return await response.json();
  } catch (error) {
    console.error('Error creating category:', error);
  }
}

// Función para actualizar una categoría
export async function updateCategory(id, category) {
  try {
    const response = await fetch(`${apiUrl}/Actualizar`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(category)
    });
    if (!response.ok) {
      throw new Error('Error al actualizar la categoría');
    }
    return await response.json();
  } catch (error) {
    console.error('Error updating category:', error);
  }
}

// Función para eliminar una categoría
export async function deleteCategory(id) {
  try {
    const response = await fetch(`${apiUrl}/eliminar/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json'
      }
    });
    if (!response.ok) {
      throw new Error('Error al eliminar la categoría');
    }
  } catch (error) {
    console.error('Error deleting category:', error);
  }
}
