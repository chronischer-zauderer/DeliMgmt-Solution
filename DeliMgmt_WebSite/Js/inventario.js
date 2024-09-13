import { fetchProducts, createProduct, updateProduct, deleteProduct } from './api.js';
import { fetchCategories } from './category.js';
import { fetchSuppliers } from './supplier.js';

document.addEventListener('DOMContentLoaded', async () => {
  await updateTable();
  await loadCategoryOptions();
  await loadSupplierOptions();
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
    
    // Agregar una opción por defecto
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.textContent = 'Selecciona una categoría';
    categorySelect.appendChild(defaultOption);
    
    // Agregar las categorías obtenidas de la API
    categories.forEach(category => {
      const option = document.createElement('option');
      option.value = category.categoryId;
      option.textContent = category.name;
      categorySelect.appendChild(option);
    });

    // Agregar la opción para crear una nueva categoría al final
    const newCategoryOption = document.createElement('option');
    newCategoryOption.value = 'nueva-categoria';
    newCategoryOption.textContent = 'Crear nueva categoría';
    categorySelect.appendChild(newCategoryOption);

    console.log('Categorías cargadas:', categories);
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
      option.value = supplier.supplierId;
      console.log(option);
      option.textContent = supplier.name;
      supplierSelect.appendChild(option);
    });
  } catch (error) {
    console.error('Error loading suppliers:', error);
  }
}

document.querySelector('.btn-add-product').addEventListener('click', async () => {
  const selectElementCategory = document.querySelector('#Categoria');
  const selectElementProveedor = document.querySelector('#Proveedor');

  console.log('Elemento select de Categoría:', selectElementCategory);
  console.log('Elemento select de Proveedor:', selectElementProveedor);

  if (!selectElementCategory || !selectElementProveedor) {
    console.error('No se encontraron los elementos select. Verifica los IDs en el HTML.');
    return;
  }

  console.log('Valor actual del select de Categoría:', selectElementCategory.value);
  console.log('Valor actual del select de Proveedor:', selectElementProveedor.value);

  const categoryId = selectElementCategory.value ? parseInt(selectElementCategory.value) : null;
  const supplierId = selectElementProveedor.value ? parseInt(selectElementProveedor.value) : null;

  console.log('categoryId después de parseInt:', categoryId);
  console.log('supplierId después de parseInt:', supplierId);

  // Obtener otros valores del formulario
  const productName = document.querySelector('input[placeholder="Nombre"]').value;
  const price = parseFloat(document.querySelector('input[placeholder="Precio"]').value);
  const stockQuantity = parseInt(document.querySelector('input[placeholder="Stock"]').value);
  const description = document.querySelector('input[placeholder="Descripción"]').value;

  // Crear el objeto del nuevo producto
  const newProduct = {
    productCode: '', // Asumiendo que se generará automáticamente
    name: productName || 'Sin nombre',
    category: categoryId !== null ? { categoryId: categoryId } : null,
    price: isNaN(price) ? 0 : price,
    supplier: supplierId !== null ? { supplierId: supplierId } : null,
    stockQuantity: isNaN(stockQuantity) ? 0 : stockQuantity,
    description: description || 'Sin descripción'
  };

  console.log('Objeto newProduct completo:', newProduct);

  try {
    const createdProduct = await createProduct(newProduct);
    console.log('Producto creado exitosamente:', createdProduct);
    await updateTable();
  } catch (error) {
    console.error('Error al crear el producto:', error);
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
