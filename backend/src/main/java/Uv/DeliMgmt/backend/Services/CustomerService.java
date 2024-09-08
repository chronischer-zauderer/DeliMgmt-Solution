package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Exception.ResourceNotFoundException;
import Uv.DeliMgmt.backend.Models.Customer;
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
        // Set customer details
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhone(customerDetails.getPhone());
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

    // Removed the incorrect `updatedCustomer(Long id, Product updateCustomer)` method
}
