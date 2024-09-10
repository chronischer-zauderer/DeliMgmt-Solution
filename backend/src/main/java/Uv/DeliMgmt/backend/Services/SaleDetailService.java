package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.SaleDetail;
import Uv.DeliMgmt.backend.Repositories.SaleDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;

    @Autowired
    public SaleDetailService(SaleDetailRepository saleDetailRepository) {
        this.saleDetailRepository = saleDetailRepository;
    }

    // Create
    public void createSaleDetail(SaleDetail saleDetail) {
        saleDetailRepository.save(saleDetail);
    }

    // Get all
    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.findAll();
    }

    // Get one by ID
    public Optional<SaleDetail> getSaleDetailById(Long id) {
        return saleDetailRepository.findById(id);
    }

    // Update
    public void updateSaleDetail(SaleDetail updatedSaleDetail) {
        Optional<SaleDetail> existingSaleDetailOpt = saleDetailRepository.findById(updatedSaleDetail.getDetailId());

        if (existingSaleDetailOpt.isPresent()) {
            SaleDetail existingSaleDetail = existingSaleDetailOpt.get();
            existingSaleDetail.setSale(updatedSaleDetail.getSale());
            existingSaleDetail.setProduct(updatedSaleDetail.getProduct());
            existingSaleDetail.setQuantity(updatedSaleDetail.getQuantity());
            existingSaleDetail.setUnitPrice(updatedSaleDetail.getUnitPrice());
            saleDetailRepository.save(existingSaleDetail);
        } else {
            throw new RuntimeException("SaleDetail not found with id: " + updatedSaleDetail.getDetailId());
        }
    }

    // Delete
    public void deleteSaleDetail(Long id) {
        saleDetailRepository.deleteById(id);
    }
}
