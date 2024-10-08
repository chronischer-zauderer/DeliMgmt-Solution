package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.Sale;
import Uv.DeliMgmt.backend.Repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // Create
    public void createSale(Sale sale) {
        saleRepository.save(sale);
    }

    // Get all
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Get one by ID
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    // Update
    public void updateSale(Sale updatedSale) {
        Optional<Sale> existingSaleOpt = saleRepository.findById(updatedSale.getSaleId());

        if (existingSaleOpt.isPresent()) {
            Sale existingSale = existingSaleOpt.get();
            existingSale.setCustomer(updatedSale.getCustomer());
            existingSale.setSaleDate(updatedSale.getSaleDate());
            existingSale.setTotalAmount(updatedSale.getTotalAmount());
            saleRepository.save(existingSale);
        } else {
            throw new RuntimeException("Sale not found with id: " + updatedSale.getSaleId());
        }
    }

    // Delete
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
