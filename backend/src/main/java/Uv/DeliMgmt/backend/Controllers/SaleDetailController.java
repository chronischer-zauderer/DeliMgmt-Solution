package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.SaleDetail;
import Uv.DeliMgmt.backend.Services.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailController {

    private final SaleDetailService saleDetailService;

    @Autowired
    public SaleDetailController(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<Void> createSaleDetail(@RequestBody SaleDetail saleDetail) {
        saleDetailService.createSaleDetail(saleDetail);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public ResponseEntity<List<SaleDetail>> listSaleDetails() {
        List<SaleDetail> saleDetails = saleDetailService.getAllSaleDetails();
        return ResponseEntity.ok(saleDetails);
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<SaleDetail> getSaleDetailById(@PathVariable long id) {
        Optional<SaleDetail> saleDetail = saleDetailService.getSaleDetailById(id);
        return saleDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<Void> updateSaleDetail(@RequestBody SaleDetail saleDetail) {
        saleDetailService.updateSaleDetail(saleDetail);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deleteSaleDetail(@PathVariable long id) {
        saleDetailService.deleteSaleDetail(id);
        return ResponseEntity.noContent().build();
    }
}