package serverP.pointServer.repository;

import serverP.pointServer.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Репозиторий для работы с точками сбора в базе данных
@Repository
public interface PointRepository extends JpaRepository<PointEntity, Long> {

    // Поиск точек в заданном прямоугольнике 
    // Узнал, что так эффективнее + 
    // Прямоугольник, описанный вокруг круга, всегда будет содержать все точки, которые находятся внутри этого круга.
    @Query("SELECT p FROM PointEntity  p WHERE " +
           "p.latitude BETWEEN :minLat AND :maxLat AND " +
           "p.longitude BETWEEN :minLon AND :maxLon")
    List<PointEntity> findAllWithinRadius(
        @Param("minLat") double minLat,
        @Param("maxLat") double maxLat,
        @Param("minLon") double minLon,
        @Param("maxLon") double maxLon
    );

    // Проверка существования точки с заданными координатами
    boolean existsByLatitudeAndLongitude(double latitude, double longitude);
}
