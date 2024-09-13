import { fetchProducts, createProduct, updateProduct, deleteProduct } from './api.js';
import { fetchCategories } from './category.js';
import { fetchSuppliers } from './supplier.js';

document.addEventListener('DOMContentLoaded', async () => {
  await updateTable();
  await loadCategoryOptions(); // Cargar las categorías en el dropdown
  await loadSupplierOptions(); // Cargar los proveedores en el dropdown
});

async function updateTable() {
  const tbody = document.querySelector('table tbody');
  
  try {
    const products = await fetchProducts();
    console.log('Fetched products:', products); // Depuración
    
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

async function loadCategoryOptions() {
  const categorySelect = document.querySelector('#Categoria');
  
  if (!categorySelect) {
    console.error('Error: #Categoria no se encontró en el DOM.');
    return;
  }
  
  try {
    const categories = await fetchCategories();
    categorySelect.innerHTML = '';
    
    // Agregar la opción para crear una nueva categoría
    const newCategoryOption = document.createElement('option');
    newCategoryOption.value = 'nueva-categoria';
    newCategoryOption.textContent = 'Crear nueva categoría';
    categorySelect.appendChild(newCategoryOption);
    
    // Agregar las categorías obtenidas de la API
    categories.forEach(category => {
      const option = document.createElement('option');
      option.value = category.id;
      option.textContent = category.name;
      categorySelect.appendChild(option);
    });
  } catch (error) {
    console.error('Error loading categories:', error);
  }
}

async function loadSupplierOptions() {
  const supplierSelect = document.querySelector('#Proveedor');
  
  if (!supplierSelect) {
    console.error('Error: #Proveedor no se encontró en el DOM.');
    return;
  }
  
  try {
    const suppliers = await fetchSuppliers();
    supplierSelect.innerHTML = '';
    
    // Agregar la opción para crear un nuevo proveedor
    const newSupplierOption = document.createElement('option');
    newSupplierOption.value = 'nuevo-proveedor';
    newSupplierOption.textContent = 'Crear nuevo proveedor';
    supplierSelect.appendChild(newSupplierOption);
    
    // Agregar los proveedores obtenidos de la API
    suppliers.forEach(supplier => {
      const option = document.createElement('option');
      option.value = supplier.id;
      option.textContent = supplier.name;
      supplierSelect.appendChild(option);
    });
  } catch (error) {
    console.error('Error loading suppliers:', error);
  }
}

document.querySelector('.btn-add-product').addEventListener('click', async () => {
  // Obtén los valores de los campos de entrada
  const productName = document.querySelector('input[placeholder="Nombre"]').value;
  const categoryId = document.querySelector('#Categoria').value;
  const price = parseFloat(document.querySelector('input[placeholder="Precio"]').value);
  const supplierId = document.querySelector('#Proveedor').value;
  const stockQuantity = parseInt(document.querySelector('input[placeholder="Stock"]').value);
  const description = document.querySelector('input[placeholder="Descripción"]').value;

  // Validar y formatear los datos
  const newProduct = { 
    productCode: '', // Asume que el código del producto se generará o se ingresará en algún campo
    name: productName || 'Sin nombre', 
    category: categoryId && categoryId !== 'nueva-categoria' ? { categoryId: parseInt(categoryId) } : null,
    price: isNaN(price) ? 0 : price, 
    supplier: supplierId && supplierId !== 'nuevo-proveedor' ? { supplierId: parseInt(supplierId) } : null,
    stockQuantity: isNaN(stockQuantity) ? 0 : stockQuantity,
    description: description || 'Sin descripción'
  };

  // Imprimir el JSON antes de enviar
  console.log('Creating product:', newProduct);

  try {
    await createProduct(newProduct);
    await updateTable(); // Recargar productos después de agregar uno nuevo
  } catch (error) {
    console.error('Error adding product:', error);
  }
});


document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-update')) {
    const id = event.target.dataset.id;
    const stockQuantity = prompt('Enter new stock quantity:');

    if (stockQuantity) {
      const updatedProduct = { stockQuantity: parseInt(stockQuantity) };
      try {
        await updateProduct(id, updatedProduct);
        await updateTable(); // Recargar productos después de actualizar
      } catch (error) {
        console.error('Error updating product:', error);
      }
    }
  }
});

document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-delete')) {
    const id = event.target.dataset.id;

    if (confirm('Are you sure you want to delete this product?')) {
      try {
        await deleteProduct(id);
        await updateTable(); // Recargar productos después de eliminar
      } catch (error) {
        console.error('Error deleting product:', error);
      }
    }
  }
});
