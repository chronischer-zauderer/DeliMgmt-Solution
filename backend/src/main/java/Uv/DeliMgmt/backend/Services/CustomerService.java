package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Obtener todos los clientes
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Crear un cliente
    public void CreateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Obtener un cliente por ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Eliminar un cliente
    public void deleteCustomer(Long customerId) {
        // Busca el cliente por su ID
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isPresent()) {
            // Si el cliente existe, se elimina
            customerRepository.deleteById(customerId);
        } else {
            // Si no existe, lanza una excepción
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
    }
    public void UpdateCustomer(Customer updatedCustomer) {
        Optional<Customer> existingCustomerOpt = customerRepository.findById(updatedCustomer.getCustomerId());
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            // Actualiza los campos del producto existente con los del producto actualizado
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setAddress(updatedCustomer.getAddress());

            customerRepository.save(existingCustomer);  // Guardar los cambios
        } else {
            throw new RuntimeException("Product not found with id: " + updatedCustomer.getCustomerId());
        }
    }
}
