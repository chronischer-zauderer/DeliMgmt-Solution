// api.js

// Obtener todos los clientes
// Función para obtener un cliente por su ID
let currentCustomerId = null;
async function fetchCustomer(id) {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Token is missing. Please authenticate first.');
    }

    try {
        // Realiza la petición a la API utilizando el ID del cliente
        const response = await fetch(`http://localhost:8081/api/customer/listarPorId/${id}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        // Si la respuesta no es OK, lanza un error con el texto de la respuesta
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Network response was not ok: ${response.status} - ${errorText}`);
        }

        // Si la respuesta es exitosa, convierte la respuesta en JSON
        return await response.json();
    } catch (error) {
        // Loguea el error para la depuración
        console.error('Error fetching customer:', error);

        // Lanza el error para que pueda ser manejado donde se llame a esta función
        throw error;
    }
}

// Función para obtener los clientes desde la API
async function fetchCustomers() {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Token is missing. Please authenticate first.');
    }

    try {
        const response = await fetch('http://localhost:8081/api/customer/Listar', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`Error fetching customers: ${response.status}`);
        }

        const customers = await response.json();

        // Llamar a la función para mostrar los clientes
        displayCustomers(customers);

        // Contar la cantidad total de clientes
        const totalClients = customers.length;

        // Filtrar los clientes activos e inactivos (si se requiere esa información)
        const activeClients = customers.filter(customer => customer.isActive).length;
        const inactiveClients = totalClients - activeClients;

        // Llamar a la función para actualizar las estadísticas
        updateStats({
            totalClients,
            activeClients,
            inactiveClients
        });

    } catch (error) {
        console.error(error);
    }
}

// Función para generar los elementos de cliente en el HTML
function displayCustomers(customers) {
    const customersGrid = document.getElementById('customers-grid');
    customersGrid.innerHTML = ''; // Limpiar la lista de clientes actual

    customers.forEach(customer => {
        console.log('ID del cliente recibido:', customer.customerId); // Asegúrate de que este log muestre valores válidos

        const customerCard = document.createElement('div');
        customerCard.classList.add('customer-card');

        customerCard.innerHTML = `
            <div class="customer-info">
                <div class="customer-name">${customer.name}</div>
                <div class="customer-contact">
                    <div>${customer.email || 'No email'}</div>
                    <div>${customer.phone || 'No phone'}</div>
                </div>
                <div>${customer.address || ''}</div>
            </div>
            <div class="button-group">
                <button class="edit-btn" data-id="${customer.customerId}">Editar</button>
                <button class="delete-btn" data-id="${customer.customerId}">Eliminar</button>
            </div>
        `;

        // Agregar el cliente a la cuadrícula de clientes
        customersGrid.appendChild(customerCard);
    });
}



// Función para actualizar las estadísticas
function updateStats(data) {
    const totalClientsElement = document.getElementById('total-clients');
    if (totalClientsElement) totalClientsElement.textContent = data.totalClients;
}

// Llamada a la función para obtener y actualizar estadísticas
fetchCustomers();


  
  // Obtener cliente por ID
    async function fetchCustomerById(id) {
    const token = localStorage.getItem('token');
    if (!token) throw new Error('Token is missing. Please authenticate first.');
  
    try {
      const response = await fetch(`http://localhost:8081/api/customer/listarPorId/${id}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
  
      if (!response.ok) throw new Error(`Error fetching customer: ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  }
  async function createClient(client) {
    const token = localStorage.getItem('token'); // Obtén el token del almacenamiento local
  
    if (!token) {
      console.error('Token is missing. Please authenticate first.');
      return;
    }
  
    console.log('Request body:', client); // Depuración: Imprimir el cuerpo de la solicitud
  
    try {
      const response = await fetch('http://localhost:8081/api/customer/crear', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`, // Agregado encabezado de autenticación
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(client),
      });
  
      const contentType = response.headers.get('Content-Type');
      let responseBody = '';
  
      if (contentType && contentType.includes('application/json')) {
        responseBody = await response.json();
      } else {
        responseBody = await response.text();
      }
  
      console.log('Response body:', responseBody);
  
      if (!response.ok) {
        throw new Error(`Network response was not ok: ${response.status} - ${responseBody}`);
      }
  
      console.log('Client created successfully:', responseBody);
    } catch (error) {
      console.error('Error adding client:', error);
    }
  }
  
  // Crear un cliente
  document.querySelector('.btn-add').addEventListener('click', async () => {
    const clientName = document.querySelector('input[placeholder="Nombre"]').value.trim();
    const clientEmail = document.querySelector('input[placeholder="Email"]').value.trim() || 'Sin email';
    const clientTel = document.querySelector('input[placeholder="Teléfono"]').value.trim() || 'Sin telefono';
    const clientAdress = ''; // Actualízalo según sea necesario
  
    const newClient = {
      name: clientName,
      email: clientEmail,
      phone: clientTel,
      address: clientAdress,
    };
  
    try {
      await createClient(newClient);
      await fetchCustomers();
    } catch (error) {
      console.error('Error:', error);
    }
  });
  
  
  
  
// Función para eliminar un cliente
async function deleteCustomer(id) {
    const token = localStorage.getItem('token');
    if (!token) throw new Error('Token is missing. Please authenticate first.');

    try {
        const response = await fetch(`http://localhost:8081/api/customer/eliminar/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error(`Error deleting customer: ${response.status}`);
    } catch (error) {
        console.error(error);
        throw error;
    }
}


document.getElementById('customers-grid').addEventListener('click', async (event) => {
    const editButton = event.target.closest('.edit-btn');
    if (!editButton) return; // Si el clic no fue sobre un botón de editar, salimos
  
    const id = editButton.dataset.id; // Obtén el ID del cliente
    console.log('ID recibido:', id);
  
    try {
      const customer = await fetchCustomer(id);  // Obtén el cliente
      currentCustomerId = id;
      console.log('Cliente recibido:', customer);
    
  
      // Llena el formulario con la información del cliente
      document.getElementById('edit-name').value = customer.name || '';
      document.getElementById('edit-email').value = customer.email || '';
      document.getElementById('edit-phone').value = customer.phone || '';
  
      // Mostrar el modal
      const updateModal = new bootstrap.Modal(document.getElementById('editCustomerModal'));
      updateModal.show();
    } catch (error) {
      console.error('Error al obtener los detalles del cliente:', error);
    }
  });
  
  

  document.getElementById('editCustomerForm').addEventListener('submit', async (event) => {
    event.preventDefault(); // Prevenir el comportamiento predeterminado del formulario (recarga de página)
  
    const updatedCustomer = {
      customerId: currentCustomerId,  // Asegúrate de que `currentCustomerId` esté definido
      name: document.getElementById('edit-name').value,
      email: document.getElementById('edit-email').value,
      phone: document.getElementById('edit-phone').value,
      address: '',
    };
  
    try {
      // Realiza la actualización del cliente con los datos del formulario
      await updateCustomer(currentCustomerId, updatedCustomer);
      await fetchCustomers();  // Recarga la lista de clientes para reflejar la actualización
  
      const updateModal = bootstrap.Modal.getInstance(document.getElementById('editCustomerModal'));
      updateModal.hide();  // Cierra el modal
      currentCustomerId = null;  // Restablece el ID del cliente actual
    } catch (error) {
      console.error('Error al actualizar el cliente:', error);
    }
  });
  
  
  async function updateCustomer(id, customerData) {
    const token = localStorage.getItem('token');
    if (!token) {
      throw new Error('Token is missing. Please authenticate first.');
    }
    try {
      const response = await fetch(`http://localhost:8081/api/customer/editar/${id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(customerData),
      });
    
      if (!response.ok) {
        const errorDetails = await response.json();
        throw new Error(`Error al actualizar el cliente: ${response.status} - ${errorDetails.message}`);
      }
    } catch (error) {
      console.error('Error al actualizar el cliente:', error.message);
      throw error;
    }
    
}
document.getElementById('cancel-edit').addEventListener('click', () => {
    const updateModal = bootstrap.Modal.getInstance(document.getElementById('editCustomerModal'));
    updateModal.hide();  // Cierra el modal
    currentCustomerId = null;  // Restablece el ID del cliente actual
  });
  


  