package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Category;
import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Models.Product;
import Uv.DeliMgmt.backend.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Find a customer by ID
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update an existing customer
    public Customer updateCustomer(Long customerId, Customer customerDetails) {
        Customer existingCustomer = findById(customerId);

        existingCustomer.setName(customerDetails.getFirstName());
        existingCustomer.setName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhone(customerDetails.getPhoneNumber());
        existingCustomer.setAddress(customerDetails.getAddress());

        return customerRepository.save(existingCustomer);
    }

    // Delete a customer by ID
    public void deleteCustomer(Long customerId) {
        Customer customer = findById(customerId);
        customerRepository.delete(customer);
    }

    // List all customers
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updatedCustomer(Long id, Product updateCustomer) {
        return customerRepository.findById(id).map(product -> {
            product.setName(customerRepository.getName());
            product.setEmail(customerRepository.getDescription());
            product.setName(customerRepository.getPrice());

            // Update other fields as needed
            return customerRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
}
