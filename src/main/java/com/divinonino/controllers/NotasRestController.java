package com.divinonino.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divinonino.dto.NotaDTO;
import com.divinonino.models.dao.NotasPeriodoDTO;
import com.divinonino.services.INotasService;

@RestController
@RequestMapping("api/notas/")
@CrossOrigin

public class NotasRestController {

	@Autowired
    private INotasService notasService;
	
	 @GetMapping("/estudiante/{carnetEstudiante}/periodo/{idPeriodo}")
	 public ResponseEntity<List<NotasPeriodoDTO>> getNotasByEstudianteAndPeriodo(@PathVariable String carnetEstudiante, @PathVariable int idPeriodo) {
		List<NotasPeriodoDTO> notasPeriodoDTOs = notasService.calcularNotasPeriodo(carnetEstudiante, idPeriodo);
		if (notasPeriodoDTOs.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(notasPeriodoDTOs);
		}
	}
	
	@PostMapping("/editar/{id}")
	public ResponseEntity<String> editarNota(@PathVariable Long id, @RequestBody NotaDTO nuevaNota) throws Exception {
		boolean actualizacionExitosa = notasService.actualizarNota(id, nuevaNota);
		if (actualizacionExitosa) {
			return ResponseEntity.ok("Nota actualizada correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la nota");
		}
	}
	
 }