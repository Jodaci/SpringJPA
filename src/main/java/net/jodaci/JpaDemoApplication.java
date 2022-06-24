package net.jodaci;

import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;
import net.jodaci.model.Categoria;
import net.jodaci.model.Perfil;
import net.jodaci.model.Usuario;
import net.jodaci.model.Vacante;
import net.jodaci.repository.CategoriasRepository;
import net.jodaci.repository.PerfilesRepository;
import net.jodaci.repository.UsuariosRepository;
import net.jodaci.repository.VacantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

	@Autowired
	private CategoriasRepository repoCategorias;
	@Autowired
	private VacantesRepository repoVacantes;

	@Autowired
	private PerfilesRepository repoPerfiles;

	@Autowired
	private UsuariosRepository repoUsuarios;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//guardar();
		//buscarPorId();
		//modificar();
		//eliminar();
		//count();
		//deleteAll();
		//buscarAllporIds();
		//obntenTodas();
		//buscarTodosOrdenados();
		//buscarTodosPaginacion();
		//buscarTodosPaginacionOrdenados();
		//siExiste();
		//guardarTodas();

		//////////////////////////////////////
		//buscarVacantes();
		//guardarVacante();
		//CrearPerfilesApp();
		//crearUsuarioDosPerfil();
		//usuarioById();
		buscarVacantesVariosEstatus();
	}
	/** Query Method **/
	private void buscarVacantesVariosEstatus(){
		String[] estatus = new String[]{"Eliminada","Creada"};

		List<Vacante> lista = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista){
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus());
		}
	}

	private void buscarVacantesSalario(){
		List<Vacante> lista = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000,14000);
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista){
			System.out.println(v.getId() + ": "+v.getNombre()+": $"+v.getSalario());
		}
	}

	private  void buscarVacantesPorDestacadoEstatus(){
		List<Vacante> lista = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1,"Aprobada");
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista){
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus() + ": "+v.getDestacado());
		}
	}
	/** Query Method **/
	private void buscarVacantesporEstatus(){
		List<Vacante> lista = repoVacantes.findByEstatus("Aprobada");
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista){
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus());
		}
	}

	private void usuarioById(){
		Optional<Usuario> user = repoUsuarios.findById(1);
		if (user.isPresent()){
			Usuario u = user.get();
			System.out.println("Usuario: "+u.getNombre());
			System.out.println("Perfiles asignados: ");
			for (Perfil p : u.getPerfiles()){
				System.out.println(p.getPerfil());
			}
		} else {
			System.out.println("Usuario no encontrado");
		}

	}
	private void crearUsuarioDosPerfil(){
		Usuario user = new Usuario();
		user.setNombre("David Cifuentes");
		user.setEmail("algo@algo.com");
		user.setFechaRegistro(new Date());
		user.setUsername("dcifuentes");
		user.setPassword("12345");
		user.setEstatus(1);

		Perfil per1 = new Perfil();
		per1.setId(2);

		Perfil per2 = new Perfil();
		per2.setId(3);

		user.agregar(per1);
		user.agregar(per2);

		repoUsuarios.save(user);
	}
	private  void CrearPerfilesApp(){
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
	private void buscarVacantes(){
		List<Vacante> lista = repoVacantes.findAll();
		for (Vacante v : lista){
			System.out.println(v.getId() + " " +v.getNombre() + ", " + v.getCategoria().getNombre());
		}

	}

	private void guardarVacante(){
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de Matematicas");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de Matematicas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.0);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		vacante.setDetalles("<h1>Los requisitos para profesor de Matematicas</h1>");
		Categoria cat = new Categoria();
		cat.setId(15);
		vacante.setCategoria(cat);
		repoVacantes.save(vacante);

	}

	private void guardar (){
		Categoria cat = new Categoria();
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos realizados con finanzas y contabilidad");

		repoCategorias.save(cat);
		System.out.println(cat);
	}

	private void guardarTodas(){
		List<Categoria> categorias = getListaCategoriasNuevas();
		repoCategorias.saveAll(categorias);
	}

	private void buscarPorId (){
		Optional<Categoria> optional = repoCategorias.findById(1);
		if(optional.isPresent()){
			System.out.println(optional.get());
		} else {
			System.out.println("Categoria no encontrada");
		}
	}

	private void buscarAllporIds(){
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias = repoCategorias.findAllById(ids);
		for (Categoria cat : categorias){
			System.out.println(cat);
		}
	}

	private void buscarTodosOrdenados(){
		List<Categoria> categorias = repoCategorias.findAll(Sort.by("nombre").descending());
		for (Categoria cat : categorias){
			System.out.println(cat.getId() + " " + cat.getNombre());
		}
	}

	private void buscarTodosPaginacion(){
		Page<Categoria> page =  repoCategorias.findAll(PageRequest.of(0,5));
		System.out.println("Total de Registros: "+page.getTotalElements());
		System.out.println("Total de Paginas: "+page.getTotalPages());
		for (Categoria c : page.getContent()){
			System.out.println(c.getId() + " " +c.getNombre());
		}
	}

	private void buscarTodosPaginacionOrdenados(){
		Page<Categoria> page =  repoCategorias.findAll(PageRequest.of(0,5,Sort.by("nombre")));
		System.out.println("Total de Registros: "+page.getTotalElements());
		System.out.println("Total de Paginas: "+page.getTotalPages());
		for (Categoria c : page.getContent()){
			System.out.println(c.getId() + " " +c.getNombre());
		}
	}
	private void obntenTodas(){
		List<Categoria> categorias = repoCategorias.findAll();
		for (Categoria cat : categorias){
			System.out.println(cat.getId() + " " + cat.getNombre());
		}
	}

	private void siExiste(){
		Boolean existe = repoCategorias.existsById(35);
		// if de una sola linea se puede realizar sin llaves
		if (existe)
			System.out.println("Si existe");
		else
			System.out.println("No existe");
	}

	private void modificar(){
		Optional<Categoria> optional = repoCategorias.findById(2);
		if(optional.isPresent()){
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ingenieria de Software");
			catTmp.setDescripcion("Desarrollo de Sistemas");
			repoCategorias.save(catTmp);
			System.out.println(catTmp);
		} else {
			System.out.println("Categoria no encontrada");
		}
	}

	private void count(){
		long count = repoCategorias.count();
		System.out.println("Total de categorias: "+count);
	}

	private void eliminar (){
		int idCategoria = 1;
		repoCategorias.deleteById(idCategoria);
	}

	private void  deleteAll(){
		repoCategorias.deleteAll();
	}

	private void borraTodoenBloque(){
		repoCategorias.deleteAllInBatch();
	}

	private List<Categoria> getListaCategoriasNuevas(){
		List<Categoria> lista = new LinkedList<Categoria>();

		// Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador Blockchain");
		cat1.setDescripcion("Trabajos relacionados con Bitcoin y Criptomonedas");

		// Categoria 2
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldador/Pintura");
		cat2.setDescripcion("Trabajos relacionados con soldadura, pintura y enderezado");

		// Categoria 3
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero Industrial");
		cat3.setDescripcion("Trabajos relacionados con Ingenieria industrial.");

		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		return lista;

	}

	/**
	 * Metodo que regresa una lista de objetos de tipo Perfil que representa los diferentes PERFILES
	 * O ROLES que tendremos en nuestra aplicación de Empleos
	 * @return
	 */
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil per1 = new Perfil();
		per1.setPerfil("SUPERVISOR");

		Perfil per2 = new Perfil();
		per2.setPerfil("ADMINISTRADOR");

		Perfil per3 = new Perfil();
		per3.setPerfil("USUARIO");

		lista.add(per1);
		lista.add(per2);
		lista.add(per3);

		return lista;
	}
}
