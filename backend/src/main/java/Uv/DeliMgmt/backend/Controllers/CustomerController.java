package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Customer;
import Uv.DeliMgmt.backend.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

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

    @PutMapping(value = "Actualizar", headers = "Accept=application/json")
    public void updateCustomer(@RequestBody Customer customer) {
        customerService.UpdateCustomer(customer);
    }

    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
