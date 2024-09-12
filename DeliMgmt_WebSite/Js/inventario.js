import { fetchProducts, fetchInventoryMovementsByProduct, createProduct, updateProduct, deleteProduct } from './api.js';

document.addEventListener('DOMContentLoaded', async () => {
  await updateTable();
});

async function updateTable() {
  const tbody = document.querySelector('table tbody');

  try {
    const products = await fetchProducts();
    console.log('Fetched products:', products); // Depura los productos

    tbody.innerHTML = ''; // Limpiar contenido anterior

    products.forEach(product => {
      const category = product.category ? product.category.name : 'Sin categoría';
      const supplier = product.supplier ? product.supplier.name : 'Sin proveedor';

      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${product.productCode || 'Sin código'}</td>
        <td>${product.name || 'Sin nombre'}</td>
        <td>${category}</td>
        <td>${product.price ? product.price.toFixed(2) : 'Sin precio'}</td>
        <td>${supplier}</td>
        <td>${product.stockQuantity || 0}</td>
        <td>${product.description || 'Sin descripción'}</td>
        <td>
          <button class="btn btn-outline-success btn-update" data-id="${product.productId}">Update</button>
          <button class="btn btn-outline-danger btn-delete" data-id="${product.productId}">Delete</button>
        </td>
      `;
      tbody.appendChild(row);
    });
  } catch (error) {
    console.error('Error loading products:', error);
  }
}

// Crear un nuevo producto
document.querySelector('.btn-add-product').addEventListener('click', async () => {
  const productName = document.querySelector('input[placeholder="Nombre"]').value;
  const category = document.querySelector('input[placeholder="Categoría"]').value;
  const price = parseFloat(document.querySelector('input[placeholder="Precio"]').value);
  const supplier = document.querySelector('input[placeholder="Proveedor"]').value;
  const stockQuantity = parseInt(document.querySelector('input[placeholder="Stock"]').value);
  const description = document.querySelector('input[placeholder="Descripción"]').value;

  const newProduct = { 
    name: productName || 'Sin nombre', 
    category: category ? { name: category } : null, 
    price: price || 0, 
    supplier: supplier ? { name: supplier } : null,
    stockQuantity: stockQuantity || 0,
    description: description || 'Sin descripción'
  };

  await createProduct(newProduct);
  await updateTable(); // Recargar productos después de agregar uno nuevo
});

// Actualizar un producto
document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-update')) {
    const id = event.target.dataset.id;
    const stockQuantity = prompt('Enter new stock quantity:');

    if (stockQuantity) {
      const updatedProduct = { stockQuantity: parseInt(stockQuantity) };
      await updateProduct(id, updatedProduct);
      await updateTable(); // Recargar productos después de actualizar
    }
  }
});

// Eliminar un producto
document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-delete')) {
    const id = event.target.dataset.id;

    if (confirm('Are you sure you want to delete this product?')) {
      await deleteProduct(id);
      await updateTable(); // Recargar productos después de eliminar
    }
  }
});
