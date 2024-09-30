package serverP.pointServer.service;

import serverP.pointServer.entity.PointEntity;
import serverP.pointServer.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {

    private final PointRepository PointRepository;

    @Autowired
    public PointService(PointRepository PointRepository) {
        this.PointRepository = PointRepository;
    }

    // Добавление новой точки    
    @Transactional
    public PointEntity addPoint(PointEntity Point) {
        boolean exists = PointRepository.existsByLatitudeAndLongitude(Point.getLatitude(), Point.getLongitude());
        if (exists) {
            throw new IllegalArgumentException("Точка с такими координатами уже существует.");
        }
        return PointRepository.save(Point);
    }

    // Получение всех точек
    @Transactional(readOnly = true)
    public List<PointEntity> getAllPoints() {
        return PointRepository.findAll();
    }

    // Поиск точек в заданном радиусе
    @Transactional(readOnly = true)
    public List<PointEntity> getPointsWithinRadius(double latitude, double longitude, double radius) {
        double earthRadius = 6371; // км
        double angleDiff = radius / earthRadius;
    
        double minLat = latitude - Math.toDegrees(angleDiff);
        double maxLat = latitude + Math.toDegrees(angleDiff);
        double minLon = longitude - Math.toDegrees(angleDiff / Math.cos(Math.toRadians(latitude)));
        double maxLon = longitude + Math.toDegrees(angleDiff / Math.cos(Math.toRadians(latitude)));
    
        // Получаем точки в пределах ограничений широты и долготы
        List<PointEntity> points = PointRepository.findAllWithinRadius(minLat, maxLat, minLon, maxLon);
    
        // Фильтруем результаты по расстоянию
        return points.stream()
            .filter(point -> distance(latitude, longitude, point.getLatitude(), point.getLongitude()) <= radius)
            .collect(Collectors.toList());
    }

    // Вычисление расстояния между двумя точками на сфере
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // км
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }

    // Удаление точки по ID
    @Transactional
    public void deletePoint(Long id) {
        if (!PointRepository.existsById(id)) {
            throw new IllegalArgumentException("Точка сбора мусора с указанным ID не найдена");
        }
        PointRepository.deleteById(id);
    }
}
