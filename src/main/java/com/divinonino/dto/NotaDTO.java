package com.divinonino.dto;

public class NotaDTO {
    private String carnetEstudiante;
    private String materia;
    private double nota;
    
    public NotaDTO(String carnetEstudiante, String materia, double nota) {
        this.carnetEstudiante = carnetEstudiante;
        this.materia = materia;
        this.nota = nota;
    }

    public String getCarnetEstudiante() {
        return carnetEstudiante;
    }

    public void setCarnetEstudiante(String carnetEstudiante) {
        this.carnetEstudiante = carnetEstudiante;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Long getId() {
        // Implementación para devolver el ID si es relevante para tu aplicación
        // De lo contrario, puedes eliminar este método o devolver null
        return null;
    }
}

