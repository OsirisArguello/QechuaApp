package com.tdp2.quechuaapp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CursoTest {

    Curso curso;

    @Before
    public void setUp() throws Exception {
        curso=new Curso();

        Alumno alumno1 = new Alumno();
        alumno1.id=1;

        Alumno alumno2 = new Alumno();
        alumno2.id=2;

        Alumno alumno3 = new Alumno();
        alumno3.id=3;

        Inscripcion inscripcion1=new Inscripcion();
        inscripcion1.alumno=alumno1;
        inscripcion1.estado="REGULAR";

        Inscripcion inscripcion2=new Inscripcion();
        inscripcion2.alumno=alumno2;
        inscripcion2.estado="REGULAR";

        Inscripcion inscripcion3=new Inscripcion();
        inscripcion3.alumno=alumno3;
        inscripcion3.estado="CONDICIONAL";

        List<Inscripcion> inscripciones = new ArrayList<>();
        inscripciones.add(inscripcion1);
        inscripciones.add(inscripcion2);
        inscripciones.add(inscripcion3);

        curso.inscripciones=inscripciones;
        curso.capacidadCurso=2;

    }

    @Test
    public void getInscriptosRegulares() {
        assertEquals(curso.getInscriptosRegulares().size(),2);
    }

    @Test
    public void getInscriptosCondicionales() {
        assertEquals(curso.getInscriptosCondicionales().size(),1);
    }

    @Test
    public void estaInscripto() {
        Alumno alumnoTest = new Alumno();
        alumnoTest.id=1;

        assertTrue(curso.estaInscripto(alumnoTest));
    }

    @Test
    public void getVacantes() {
        assertEquals(curso.getVacantes(),new Integer(0));
    }

    @Test
    public void getVacantesConDisponibles() {
        curso.capacidadCurso=3;
        assertEquals(curso.getVacantes(),new Integer(1));
    }
}