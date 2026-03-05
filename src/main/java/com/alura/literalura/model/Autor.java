package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        // Nombres de los libros
        String nombresLibros = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", "));

        return "---------- AUTOR ----------\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha de nacimiento: " + (fechaDeNacimiento != null ? fechaDeNacimiento : "N/A") + "\n" +
                "Fecha de fallecimiento: " + (fechaDeFallecimiento != null ? fechaDeFallecimiento : "N/A") + "\n" +
                "Libros: [ " + nombresLibros + " ]\n" +
                "---------------------------\n";
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public Integer getFechaDeNacimiento() { return fechaDeNacimiento; }
    public Integer getFechaDeFallecimiento() { return fechaDeFallecimiento; }
}