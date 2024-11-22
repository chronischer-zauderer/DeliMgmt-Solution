import { fetchProducts,fetchProduct, createProduct, updateProduct, deleteProduct } from './api.js';
import { fetchCategories } from './category.js';
import { fetchSuppliers } from './supplier.js';

let currentProductId = null; // Para almacenar el ID del producto que se está actualizando

document.addEventListener('DOMContentLoaded', async () => {
  await updateTable();
  await updateLowStockTable();
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
      if(product.stockQuantity >= 10){
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

      // Agregar evento de clic para cambiar la imagen
      row.addEventListener('click', () => {
        const productImage = document.getElementById('product-image');
        console.log(productImage)
        productImage.src = `../RESURCES${product.imageUrl.replace('/api/images/', '').startsWith('/') ? '' : '/'}${product.imageUrl.replace('/api/images/', '')}`;
      });

      tbody.appendChild(row);
      }
    });
  } catch (error) {
    console.error('Error al cargar los productos:', error);
  }
}

async function updateLowStockTable() {
  const tbody = document.querySelector('.tableAlerta tbody');
  
  try {
    const products = await fetchProducts();
    console.log('Productos obtenidosss:', products);

    tbody.innerHTML = ''; // Limpiar tabla

    // Filtrar productos con stock menor a 10 y agregar filas a la tabla
    products.forEach(product => {
      if (product.stockQuantity < 10) {
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

        // Agregar evento de clic para cambiar la imagen del producto
        row.addEventListener('click', () => {
          const productImage = document.getElementById('product-image');

          productImage.src = `../RESURCES${product.imageUrl.replace('/api/images/', '').startsWith('/') ? '' : '/'}${product.imageUrl.replace('/api/images/', '')}`;
        });

        tbody.appendChild(row);
        console.log(tbody)
      }
    });

    // Verificar si no hay productos con bajo stock
    if (tbody.children.length === 0) {
      tbody.innerHTML = '<tr><td colspan="8">No hay productos con stock bajo</td></tr>';
    }

  } catch (error) {
    console.error('Error al cargar los productos de bajo stock:', error);
  }
}


async function loadCategoryOptions() {
  const categorySelect = document.querySelector('#Categoria');
  await loadOptions(categorySelect, fetchCategories, 'categoryId', 'name', 'Selecciona una categoría');
  
}

async function loadSupplierOptions() {
  const supplierSelect = document.querySelector('#Proveedor');
  await loadOptions(supplierSelect, fetchSuppliers, 'supplierId', 'name', 'Selecciona un proveedor');

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

  // Captura el archivo de imagen
  const imageInput = document.getElementById('imageInput');
  const imageFile = imageInput.files[0]; // Obtiene el archivo de imagen

  // Si hay un archivo de imagen, subirlo
  let imageUrl = '';
  if (imageFile) {
      try {
          imageUrl = await uploadImage(imageFile); // Llama a la función de subida de imagen
      } catch (error) {
          console.error('Error al subir la imagen:', error);
          return; // Detener el proceso si falla la subida de la imagen
      }
  }

  const newProduct = {
      productCode,
      name: productName || 'Sin nombre',
      category: categoryId !== null ? { categoryId: categoryId } : null,
      price: isNaN(price) ? 0 : price,
      supplier: supplierId !== null ? { supplierId: supplierId } : null,
      stockQuantity: isNaN(stockQuantity) ? 0 : stockQuantity,
      description: description || 'Sin descripción',
      imageUrl // Agregar la URL de la imagen aquí
  };

  try {
      await createProduct(newProduct);
      await updateTable();
      // Limpiar el campo de archivo
      imageInput.value = '';
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
      console.log(product.imageUrl)
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

  const currentProduct = await fetchProduct(currentProductId);  // Obtén el producto actual
  const currentImageUrl = currentProduct.imageUrl;  // Guarda la URL de la imagen actual

  // Recopilar la información del producto
  const updatedProduct = {
    productId: currentProductId,
    productCode: document.getElementById('updateProductCode').value,
    name: document.getElementById('updateProductName').value,
    category: { categoryId: document.getElementById('updateProductCategory').value },
    price: parseFloat(document.getElementById('updateProductPrice').value),
    supplier: { supplierId: document.getElementById('updateProductSupplier').value },
    stockQuantity: parseInt(document.getElementById('updateProductStock').value),
    description: document.getElementById('updateProductDescription').value,
    imageUrl: currentImageUrl  // Asignamos la URL actual de la imagen al principio
  };
  // Verifica si se ha seleccionado una nueva imagen
  const imageFile = document.getElementById('updateProductImage').files[0];
  console.log(imageFile);

  if (imageFile) {
    // Si se seleccionó una nueva imagen, subimos la imagen y asignamos la URL
    try {
      const imageUrl = await uploadImage(imageFile);  // Llama a la función para subir la imagen
      updatedProduct.imageUrl = imageUrl;  // Asigna la URL de la imagen al producto actualizado
    } catch (error) {
      console.error('Error al subir la imagen:', error);
      return;  // Detiene la actualización si hubo un error al subir la imagen
    }
  }

  try {
    // Actualiza el producto con la nueva información (incluyendo la posible nueva imagen)
    await updateProduct(currentProductId, updatedProduct);  
    await updateTable();  // Actualiza la tabla de productos
    const updateModal = bootstrap.Modal.getInstance(document.getElementById('updateProductModal'));
    updateModal.hide();  // Cierra el modal
    currentProductId = null;  // Resetear el ID del producto actual
  } catch (error) {
    console.error('Error al actualizar el producto:', error);
  }
});




// Evento para eliminar un producto
document.querySelector('table').addEventListener('click', async (event) => {
  if (event.target.classList.contains('btn-delete')) {
    const id = event.target.dataset.id; // Obtén el ID del producto
    const product = await fetchProduct(id); // Obtén el nombre del archivo de imagen
    const imageFilename = product.imageUrl.replace('/api/images/', '').replace(/^\/+/, '');
    console.log(imageFilename)
    if (confirm('¿Estás seguro de que quieres eliminar este producto?')) {
      try {
        console.log(`ID del producto: ${id}, Archivo de imagen: ${imageFilename}`);
        
        // Elimina la imagen asociada al producto
        if (imageFilename) {
          await deleteImage(imageFilename);
          console.log('Imagen eliminada correctamente');
        }

        // Elimina el producto
        await deleteProduct(id);
        console.log('Producto eliminado correctamente');

        // Actualiza la tabla
        await updateTable();
        console.log('Tabla actualizada');
      } catch (error) {
        console.error('Error al eliminar el producto:', error);
        alert('Hubo un problema al eliminar el producto. Inténtalo de nuevo.');
      }
    }
  }
});


document.getElementById('searchInput').addEventListener('input', function(event) {
  const searchTerm = event.target.value.toLowerCase();
  filterProducts(searchTerm);
});

async function filterProducts(searchTerm) {
  const tbody = document.querySelector('table tbody');

  try {
    const products = await fetchProducts(); // Obtener todos los productos
    tbody.innerHTML = ''; // Limpiar la tabla

    products
      .filter(product => {
        // Verificamos que `productCode` exista y sea una cadena antes de usar `toLowerCase()`
        const codeMatch = product.productCode && product.productCode.toLowerCase().includes(searchTerm.toLowerCase());
        const nameMatch = product.name.toLowerCase().includes(searchTerm.toLowerCase());
        const categoryMatch = product.category && product.category.name.toLowerCase().includes(searchTerm.toLowerCase());
        const supplierMatch = product.supplier && product.supplier.name.toLowerCase().includes(searchTerm.toLowerCase());
        const descriptionMatch = product.description.toLowerCase().includes(searchTerm.toLowerCase());

        // Filtrar por código de producto, nombre, categoría, proveedor o descripción
        return codeMatch || nameMatch || categoryMatch || supplierMatch || descriptionMatch;
      })
      .forEach(product => {
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
    console.error('Error al filtrar productos:', error);
  }
}

export async function uploadImage(imageFile) {
  const token = localStorage.getItem('token'); // Obtiene el token del almacenamiento local
  
  // Verificar que el usuario haya iniciado sesión
  if (!token) {
      alert('Por favor, inicia sesión antes de subir una imagen.');
      return;
  }

  // Verificar que se haya seleccionado un archivo de imagen
  if (!imageFile) {
      alert('Por favor, selecciona una imagen.');
      return;
  }

  // Verificar que el archivo no esté vacío
  if (imageFile.size === 0) {
      alert('El archivo está vacío. Por favor, selecciona una imagen válida.');
      return;
  }

  // Crear un FormData para enviar el archivo
  const formData = new FormData();
  formData.append('file', imageFile); // Cambia 'file' para que coincida con tu controlador
  try {
      const response = await fetch('http://localhost:8081/api/images/upload', {
          method: 'POST',
          headers: {
              'Authorization': `Bearer ${token}`, // Incluye el token en la cabecera
          },
          body: formData, // Envía el FormData con la imagen
      });

      const responseText = await response.text(); // Obtiene el cuerpo de la respuesta como texto
      if (!response.ok) {
          throw new Error(responseText); // Lanza un error con el mensaje
      }
      
      console.log(responseText);
      alert(responseText); // Muestra el mensaje de éxito
      return responseText;

  } catch (error) {
      console.error('Error:', error);
      alert(`Error al subir la imagen: ${error.message}`);
      throw error; // Lanza el error para manejarlo en el llamador
  }
}

async function deleteImage(imageFilename) {
  const token = localStorage.getItem('token');
  if (!token) {
    alert('Por favor, inicia sesión antes de eliminar una imagen.');
    return;
  }

  try {
    const response = await fetch(`http://localhost:8081/api/images/EliminarImagen/${imageFilename}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`, // Incluye el token en la cabecera
      },
    });

    // Manejar la respuesta según el estado HTTP
    const responseText = await response.text(); // Leer el mensaje de respuesta del backend
    if (!response.ok) {
      throw new Error(responseText); // Lanzar el mensaje de error
    }

    console.log(responseText);
    alert(responseText); // Mostrar el mensaje al usuario
  } catch (error) {
    console.error('Error al eliminar la imagen:', error);
    alert(`Error al eliminar la imagen: ${error.message}`);
  }
}

export async function updateImage(imageFile, productId) {
  const token = localStorage.getItem('token'); // Asegúrate de usar 'token' correctamente
  if (!token) {
      alert('Por favor, inicia sesión antes de subir una imagen.');
      return;
  }

  if (!productId) {
      alert('Por favor, proporciona un ID de producto válido.');
      return;
  }

  const formData = new FormData();
  formData.append('file', imageFile); // Adjunta la imagen
  formData.append('productId', productId); // Adjunta el ID del producto

  try {
      const response = await fetch(`http://localhost:8081/api/images/update/${productId}`, {
          method: 'POST',
          headers: {
              'Authorization': `Bearer ${token}` // Incluye el token en la cabecera
          },
          body: formData // Envía el FormData con la imagen y el ID del producto
      });

      const responseText = await response.text(); // Lee la respuesta como texto
      if (!response.ok) {
          throw new Error(responseText); // Lanza un error si la respuesta no es exitosa
      }

      console.log(responseText);
      alert(responseText);
      return responseText;

  } catch (error) {
      console.error('Error:', error);
      alert(`Error al actualizar la imagen: ${error.message}`);
      throw error; // Lanza el error para manejarlo en el llamador
  }
}
function handleImageUpload(event) {
  const imageInput = event.target;
  const file = imageInput.files[0];
  const productImage = document.getElementById('product-image');
  console.log(productImage)

  if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
          productImage.src = e.target.result; // Muestra la imagen seleccionada
          productImage.style.display = 'block'; // Muestra la imagen
      };
      reader.readAsDataURL(file);
  } else {
      productImage.src = ''; // Limpia la imagen si no se selecciona ninguna
      productImage.style.display = 'none'; // Oculta la imagen
  }
}


