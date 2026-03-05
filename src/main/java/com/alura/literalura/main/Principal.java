package com.alura.literalura.main;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    // 1. Añadimos el repositorio como atributo
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    // 2. Constructor para que Spring nos pase el repositorio desde LiteraluraApplication
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Mostrar estadisticas de los libros registrados
                                  
                    0 - Salir
                    """;
            System.out.println(menu);

            try {
                opcion = Integer.parseInt(lectura.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Por favor, ingresa un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroEnLaWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    estadisticas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private Datos obtenerDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        return conversor.obtenerDatos(json, Datos.class);
    }

    // Opcion 1 del menu

    private void buscarLibroEnLaWeb() {
        Datos datos = obtenerDatosLibro();

        if (datos != null && !datos.resultados().isEmpty()) {
            DatosLibro datosLibro = datos.resultados().get(0);

            // Existencia del libro
            Optional<Libro> libroExistente = libroRepositorio.findByTituloContainsIgnoreCase(datosLibro.titulo());

            if (libroExistente.isPresent()) {
                System.out.println("\n--- AVISO: El libro '" + datosLibro.titulo() + "' ya está registrado en tu base de datos. ---\n");
            } else {
                // Existencia del autor
                var datosAutor = datosLibro.autor().get(0);
                Autor autor = autorRepositorio.findByNombreContainsIgnoreCase(datosAutor.nombre())
                        .orElseGet(() -> autorRepositorio.save(new Autor(datosAutor)));

                // Asignar libro y autor a la lista
                Libro nuevoLibro = new Libro(datosLibro);
                nuevoLibro.setAutor(autor);

                // Guardar
                libroRepositorio.save(nuevoLibro);

                System.out.println("\n---------- LIBRO REGISTRADO ----------");
                System.out.println(nuevoLibro);
                System.out.println("--------------------------------------\n");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    // Opcion 2 del menu

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("\n--- LIBROS REGISTRADOS ---");
            libros.stream()
                    .forEach(l -> System.out.println(
                            "Título: " + l.getTitulo() +
                                    " | Idioma: " + l.getIdioma() +
                                    " | Descargas: " + l.getNumeroDeDescargas()
                    ));
            System.out.println("---------------------------\n");
        }
    }

    //Opcion 3 del menu

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("\n--- No hay autores registrados en la base de datos. ---\n");
        } else {
            System.out.println("\n--- LISTA DE AUTORES REGISTRADOS ---");
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre)) // Los ordenamos por nombre
                    .forEach(System.out::println); // Esto llama al toString() que creamos arriba
        }
    }

    //Opcion 4 del menu

    private void listarAutoresVivosEnDeterminadoAnio() {
        System.out.println("Ingrese el año que desea consultar:");
        try {
            var anio = Integer.parseInt(lectura.nextLine());

            List<Autor> autoresVivos = autorRepositorio.autorVivosEnDeterminadoAnio(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("\n--- No se encontraron autores vivos en el año " + anio + " ---\n");
            } else {
                System.out.println("\n--- AUTORES VIVOS EN EL AÑO " + anio + " ---");
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un año válido en formato numérico.");
        }
    }

    // Opcion 5 del menu

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (es, en, fr, pt):");
        var idioma = lectura.nextLine();

        List<Libro> libros = libroRepositorio.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(l -> System.out.println("Título: " + l.getTitulo()));
        }
    }

    //Opcion 6 del menu

    private void estadisticas() {
        List<Libro> libros = libroRepositorio.findAll();
        DoubleSummaryStatistics est = libros.stream()
                .filter(l -> l.getNumeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));

        System.out.println("\n--------- ESTADÍSTICAS ---------");
        System.out.println("Cantidad de libros: " + est.getCount());
        System.out.println("Promedio de descargas: " + est.getAverage());
        System.out.println("Máximo de descargas: " + est.getMax());
        System.out.println("--------------------------------\n");
    }

}