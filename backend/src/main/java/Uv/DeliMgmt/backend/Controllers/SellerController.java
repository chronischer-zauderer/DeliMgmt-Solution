package Uv.DeliMgmt.backend.Controllers;

import Uv.DeliMgmt.backend.Models.Seller;
import Uv.DeliMgmt.backend.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<Void> createSeller(@RequestBody Seller seller) {
        sellerService.createSeller(seller);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public ResponseEntity<List<Seller>> listSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<Seller> getSellerById(@PathVariable long id) {
        Optional<Seller> seller = sellerService.getSellerById(id);
        return seller.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<Void> updateSeller(@RequestBody Seller seller) {
        sellerService.updateSeller(seller);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> deleteSeller(@PathVariable long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}