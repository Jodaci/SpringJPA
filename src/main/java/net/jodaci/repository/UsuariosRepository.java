package net.jodaci.repository;

import net.jodaci.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
