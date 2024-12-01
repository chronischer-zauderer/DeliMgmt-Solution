package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Repositories.CustomerRepository;
import Uv.DeliMgmt.backend.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void createCustomer(@RequestBody Customer customer) {
        customerService.CreateCustomer(customer);
    }

    @GetMapping(value = "Listar", headers = "Accept=application/json")
    public List<Customer> listCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "listarPorId/{id}", headers = "Accept=application/json")
    public Optional<Customer> listCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }

        Customer customer = customerOpt.get();
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddress(updatedCustomer.getAddress());

        customerRepository.save(customer);
        return ResponseEntity.ok("Cliente actualizado con éxito.");
    }

    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
