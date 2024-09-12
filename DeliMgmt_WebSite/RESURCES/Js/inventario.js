import { listProducts, createProduct, deleteProduct } from './api.js';

// Función para mostrar los productos en la tabla
function displayProducts(products) {
    const tbody = document.querySelector('tbody');
    tbody.innerHTML = ''; // Limpiar tabla antes de cargar los productos

    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td>${product.price}</td>
            <td>${product.supplier}</td>
            <td>${product.stock}</td>
            <td>
                <button class="btn-delete" data-id="${product.id}">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });

    // Agregar event listeners a los botones de eliminar
    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', async (e) => {
            const productId = e.target.getAttribute('data-id');
            await deleteProduct(productId);
            const updatedProducts = await listProducts();
            displayProducts(updatedProducts);
        });
    });
}

// Cargar productos al cargar la página
document.addEventListener('DOMContentLoaded', async function () {
    const products = await listProducts();
    displayProducts(products);

    // Agregar nuevo producto al inventario
    document.querySelector('.btn-add-product').addEventListener('click', async function (e) {
        e.preventDefault();
        const newProduct = {
            name: document.querySelector('input[placeholder="Nombre"]').value,
            category: document.querySelector('input[placeholder="Categoria"]').value,
            price: parseFloat(document.querySelector('input[placeholder="Precio"]').value),
            supplier: document.querySelector('input[placeholder="Proveedor"]').value,
            stock: parseInt(document.querySelector('input[placeholder="Stock"]').value),
        };
        await createProduct(newProduct);
        const updatedProducts = await listProducts();
        displayProducts(updatedProducts);
    });
});
// inventario.js

document.addEventListener('DOMContentLoaded', async () => {
    // Cargar categorías al cargar la página
    const categorySelect = document.getElementById('categorySelect');
    const categories = await fetchCategories();
    
    if (categories && categories.length > 0) {
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;  // Cambia según tu modelo
        option.textContent = category.nombre;  // Cambia según tu modelo
        categorySelect.appendChild(option);
      });
    }
  
    // Agregar una nueva categoría cuando el botón es presionado
    const addCategoryBtn = document.getElementById('addCategoryBtn');
    addCategoryBtn.addEventListener('click', async () => {
      const newCategoryInput = document.getElementById('newCategoryInput');
      const newCategoryName = newCategoryInput.value.trim();
  
      if (newCategoryName) {
        const newCategory = await createCategory(newCategoryName);
        if (newCategory) {
          // Agregar la nueva categoría al select
          const option = document.createElement('option');
          option.value = newCategory.id;  // Cambia según tu modelo
          option.textContent = newCategory.nombre;  // Cambia según tu modelo
          categorySelect.appendChild(option);
  
          // Seleccionar la nueva categoría
          categorySelect.value = newCategory.id;
          newCategoryInput.value = "";  // Limpiar el input
        }
      } else {
        alert("Debe ingresar un nombre de categoría");
      }
    });
  });
  document.addEventListener('DOMContentLoaded', () => {
    const addCategoryBtn = document.getElementById('addCategoryBtn');
    const newCategoryForm = document.getElementById('newCategoryForm');
    const cancelBtn = document.getElementById('cancelBtn');

    // Mostrar el formulario al hacer clic en "Agregar Categoría"
    addCategoryBtn.addEventListener('click', () => {
        newCategoryForm.style.display = 'block';
    });

    // Ocultar el formulario al hacer clic en "Cancelar"
    cancelBtn.addEventListener('click', () => {
        newCategoryForm.style.display = 'none';
    });

    // Opcional: Puedes agregar aquí el manejo del formulario de envío
    const categoryForm = document.getElementById('categoryForm');
    categoryForm.addEventListener('submit', (e) => {
        e.preventDefault();
        // Aquí puedes agregar el código para enviar los datos al servidor
        const categoryName = document.getElementById('categoryName').value;
        console.log('Nueva categoría:', categoryName);

        // Ocultar el formulario después de enviar
        newCategoryForm.style.display = 'none';
    });
});
  
