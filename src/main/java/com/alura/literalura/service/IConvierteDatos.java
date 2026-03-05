package com.alura.literalura.service;

public interface IConvierteDatos {
    // Manipulacion del JSON
    <T> T obtenerDatos(String json, Class<T> clase);
}
