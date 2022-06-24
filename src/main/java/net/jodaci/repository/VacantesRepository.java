package net.jodaci.repository;

import net.jodaci.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacantesRepository extends JpaRepository<Vacante,Integer> {

        // select * from Vacantes where estatus = ?
        List<Vacante> findByEstatus(String estatus);

        List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);

        List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);

        List<Vacante> findByEstatusIn(String[] estatus);

}
