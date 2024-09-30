package serverP.pointServer.controller;

import serverP.pointServer.entity.PointEntity;
import serverP.pointServer.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointService PointService;

    @Autowired
    public PointController(PointService PointService) {
        this.PointService = PointService;
    }

    // Добавление новой точки сбора    
    @PostMapping
    public ResponseEntity<?> addPoint(@RequestBody PointEntity Point) {
        try {
            PointEntity savedPoint = PointService.addPoint(Point);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPoint);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Получение всех точек сбора    
    @GetMapping
    public ResponseEntity<List<PointEntity>> getAllPoints() {
        List<PointEntity> points = PointService.getAllPoints();
        return ResponseEntity.ok(points);
    }

    // Поиск точек в заданном радиусе
    @GetMapping("/nearby")
    public ResponseEntity<?> getPointsWithinRadius(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "20") double radius) {
        try {
            List<PointEntity> points = PointService.getPointsWithinRadius(latitude, longitude, radius);
            return ResponseEntity.ok(points);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удаление точки сбора по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable Long id) {
        try {
            PointService.deletePoint(id);
            return ResponseEntity.ok().body("Точка сбора мусора успешно удалена");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
