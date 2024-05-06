package com.divinonino.services;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divinonino.models.dao.INotasDao;
import com.divinonino.models.dao.NotasPeriodoDTO;
import com.divinonino.models.entities.Notas;

@Service
public class NotasServiceImpl implements INotasService {

    @Autowired
    private INotasDao notasDao;

    
    @Override
    public List<Notas> findByEstudianteCarnetAndIdperiodo(String carnetEstudiante, int idPeriodo) {
        return notasDao.findByEstudianteCarnetAndPeriodoIdPeriodo(carnetEstudiante, idPeriodo);
    }
    
    public List<NotasPeriodoDTO> calcularNotasPeriodo(String carnetEstudiante, int idPeriodo) {
        List<Notas> notas = findByEstudianteCarnetAndIdperiodo(carnetEstudiante, idPeriodo);
        Set<String> materiasSet = new HashSet<>();
        List<NotasPeriodoDTO> notasPeriodoDTOList = new ArrayList<>();

        for (Notas nota : notas) {
            String materiaNombre = nota.getMateria().getNombre();
            if (!materiasSet.contains(materiaNombre)) {
                materiasSet.add(materiaNombre);
                NotasPeriodoDTO notasPeriodoDTO = new NotasPeriodoDTO();
                notasPeriodoDTO.setMateria(materiaNombre);
                notasPeriodoDTOList.add(notasPeriodoDTO);
            }

            NotasPeriodoDTO notasPeriodoDTO = notasPeriodoDTOList.stream()
                .filter(dto -> dto.getMateria().equals(materiaNombre))
                .findFirst()
                .orElseThrow(); // Aquí debes manejar la excepción apropiadamente

            Double notaPonderada = nota.getNota();
            switch (nota.getTipoEvaluacion()) {
                case "Prom Guias":
                    notasPeriodoDTO.setNotaGuias(redondear(notaPonderada));
                    break;
                case "Laboratorio":
                    notasPeriodoDTO.setNotaLaboratorio(redondear(notaPonderada));
                    break;
                case "Evaluado":
                    notasPeriodoDTO.setNotaEvaluado(redondear(notaPonderada));
                    break;
                default:
                    break;
            }

            Double notaGuias = notasPeriodoDTO.getNotaGuias() != null ? notasPeriodoDTO.getNotaGuias() : 0.0;
            Double notaEvaluado = notasPeriodoDTO.getNotaEvaluado() != null ? notasPeriodoDTO.getNotaEvaluado() : 0.0;
            notasPeriodoDTO.setPromedioPeriodoMateria(redondear((notaGuias * 0.6) + (notaEvaluado * 0.4)));
        }

        return notasPeriodoDTOList;
    }

    private Double redondear(Double valor) {
        return Math.round(valor * 100.0) / 100.0; // Redondear a dos decimales
    }
}