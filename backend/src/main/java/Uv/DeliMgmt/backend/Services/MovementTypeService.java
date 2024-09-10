package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.MovementType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MovementTypeService {
    // Asumiendo que los tipos de movimiento son estáticos
    public List<MovementType> getAllMovementTypes() {
        return Arrays.asList(MovementType.values());
    }
}
