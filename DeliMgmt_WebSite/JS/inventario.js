import { fetchProducts,fetchProduct, createProduct, updateProduct, deleteProduct } from './api.js';
import { fetchCategories } from './category.js';
import { fetchSuppliers } from './supplier.js';

let currentProductId = null; // Para almacenar el ID del producto que se está actualizando

document.addEventListener('DOMContentLoaded', async () => {
  await updateTable();
  await loadCategoryOptions();
  await loadSupplierOptions();
  await loadUpdateCategoryOptions();
  await loadUpdateSupplierOptions();
});

async function updateTable() {
  const tbody = document.querySelector('table tbody');
  
  try {
    const products = await fetchProducts();
    console.log('Productos obtenidos:', products);

    tbody.innerHTML = ''; // Limpiar tabla
    
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
    console.error('Error al cargar los productos:', error);
  }
}

async function loadCategoryOptions() {
  const categorySelect = document.querySelector('#Categoria');
  await loadOptions(categorySelect, fetchCategories, 'categoryId', 'name', 'Selecciona una categoría');
  
  const newCategoryOption = document.createElement('option');
  newCategoryOption.value = 'nueva-categoria';
  newCategoryOption.textContent = 'Crear nueva categoría';
  categorySelect.appendChild(newCategoryOption);
}

async function loadSupplierOptions() {
  const supplierSelect = document.querySelector('#Proveedor');
  await loadOptions(supplierSelect, fetchSuppliers, 'supplierId', 'name', 'Selecciona un proveedor');
  
  const newSupplierOption = document.createElement('option');
  newSupplierOption.value = 'nuevo-proveedor';
  newSupplierOption.textContent = 'Crear nuevo proveedor';
  supplierSelect.appendChild(newSupplierOption);
}

async function loadUpdateCategoryOptions() {
  const categorySelect = document.getElementById('updateProductCategory');
  await loadOptions(categorySelect, fetchCategories, 'categoryId', 'name', 'Selecciona una categoría');
}

async function loadUpdateSupplierOptions() {
  const supplierSelect = document.getElementById('updateProductSupplier');
  await loadOptions(supplierSelect, fetchSuppliers, 'supplierId', 'name', 'Selecciona un proveedor');
}

async function loadOptions(selectElement, fetchFunction, valueKey, textKey, defaultText) {
  try {
    const items = await fetchFunction();
    selectElement.innerHTML = '';
    
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.textContent = defaultText;
    selectElement.appendChild(defaultOption);
    
    items.forEach(item => {
      const option = document.createElement('option');
      option.value = item[valueKey];
      option.textContent = item[textKey];
      selectElement.appendChild(option);
    });
  } catch (error) {
    console.error(`Error al cargar opciones para ${selectElement.id}:`, error);
  }
}

// Evento para crear un nuevo producto
document.querySelector('.btn-outline-primary').addEventListener('click', async () => {
  const selectElementCategory = document.querySelector('#Categoria');
  const selectElementProveedor = document.querySelector('#Proveedor');

  const categoryId = selectElementCategory.value ? parseInt(selectElementCategory.value) : null;
  const supplierId = selectElementProveedor.value ? parseInt(selectElementProveedor.value) : null;

  const productCode = document.querySelector('input[placeholder="Codigo De Producto"]').value;
  const productName = document.querySelector('input[placeholder="Nombre"]').value;
  const price = parseFloat(document.querySelector('input[placeholder="Precio"]').value);
  const stockQuantity = parseInt(document.querySelector('input[placeholder="Stock"]').value);
  const description = document.querySelector('input[placeholder="Descripción"]').value;

  const newProduct = {
    productCode,
    name: productName || 'Sin nombre',
    category: categoryId !== null ? { categoryId: categoryId } : null,
    price: isNaN(price) ? 0 : price,
    supplier: supplierId !== null ? { supplierId: supplierId } : null,
    stockQuantity: isNaN(stockQuantity) ? 0 : stockQuantity,
    description: description || 'Sin descripción'
  };

  try {
    await createProduct(newProduct);
    await updateTable();
  } catch (error) {
    console.error('Error al crear el producto:', error);
  }
});

// Evento para abrir el modal de actualización
document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-update')) {
    const id = event.target.dataset.id;
    console.log(id)
    try {
      const product = await fetchProduct(id); // Asegúrate de que esta función esté implementada para obtener un solo producto
      console.log(fetchProduct(id))
      currentProductId = id;
      
      document.getElementById('updateProductCode').value = product.productCode || '';
      console.log(product.productCode)
      document.getElementById('updateProductName').value = product.name || '';
      document.getElementById('updateProductCategory').value = product.category ? product.category.categoryId : '';
      document.getElementById('updateProductPrice').value = product.price || '';
      document.getElementById('updateProductSupplier').value = product.supplier ? product.supplier.supplierId : '';
      document.getElementById('updateProductStock').value = product.stockQuantity || '';
      document.getElementById('updateProductDescription').value = product.description || '';

      // Mostrar el modal
      const updateModal = new bootstrap.Modal(document.getElementById('updateProductModal'));
      updateModal.show();
    } catch (error) {
      console.error('Error al obtener los detalles del producto:', error);
    }
  }
});

// Evento para confirmar la actualización del producto
document.getElementById('confirmUpdateProduct').addEventListener('click', async () => {
  if (currentProductId === null) {
    console.error('No se ha seleccionado ningún producto para actualizar');
    return;
  }

  const updatedProduct = {
    productId : currentProductId,
    productCode: document.getElementById('updateProductCode').value,
    name: document.getElementById('updateProductName').value,
    category: { categoryId: document.getElementById('updateProductCategory').value },
    price: parseFloat(document.getElementById('updateProductPrice').value),
    supplier: { supplierId: document.getElementById('updateProductSupplier').value },
    stockQuantity: parseInt(document.getElementById('updateProductStock').value),
    description: document.getElementById('updateProductDescription').value
    
  };

  try {
    await updateProduct(currentProductId, updatedProduct);
    await updateTable();
    const updateModal = bootstrap.Modal.getInstance(document.getElementById('updateProductModal'));
    updateModal.hide();
    currentProductId = null; // Resetear el ID del producto actual
  } catch (error) {
    console.error('Error al actualizar el producto:', error);
  }
});

// Evento para eliminar un producto
document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-delete')) {
    const id = event.target.dataset.id;

    if (confirm('¿Estás seguro de que quieres eliminar este producto?')) {
      try {
        console.log(id);
        await deleteProduct(id);
        await updateTable();
      } catch (error) {
        console.error('Error al eliminar el producto:', error);
      }
    }
  }
});
