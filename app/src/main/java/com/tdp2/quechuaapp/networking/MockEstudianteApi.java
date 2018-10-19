package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.Materia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockEstudianteApi implements EstudianteApi {

    private final BehaviorDelegate<EstudianteApi> delegate;

    public MockEstudianteApi(BehaviorDelegate<EstudianteApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<ArrayList<Curso>> getCursos() {
        Curso curso1 = new Curso();
        curso1.id=1;

        Curso curso2 = new Curso();
        curso2.id=2;

        ArrayList<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso1);
        listaCursos.add(curso2);

        return delegate.returningResponse(listaCursos).getCursos();

    }

    @Override
    public Call<ArrayList<Cursada>> getCursadas(String token) {
        Curso curso1 = new Curso();
        curso1.id=1;

        Curso curso2 = new Curso();
        curso2.id=2;

        ArrayList<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso1);
        listaCursos.add(curso2);

        return delegate.returningResponse(listaCursos).getCursadas("h");

    }

    @Override
    public Call<ArrayList<Curso>> getCursosPorMateria(Integer materiaId) {
        Curso curso = new Curso();
        curso.id=1;
        curso.materia=new Materia();
        curso.materia.id=materiaId;

        ArrayList<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso);

        return delegate.returningResponse(listaCursos).getCursosPorMateria(materiaId);
    }

    @Override
    public Call<Inscripcion> inscribirAlumno(Integer alumnoId, Integer cursoId) {

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.alumno = new Alumno();
        inscripcion.alumno.id=alumnoId;

        inscripcion.curso=new Curso();
        inscripcion.curso.id=cursoId;

        return delegate.returningResponse(inscripcion).inscribirAlumno(alumnoId,cursoId);
    }

    @Override
    public Call<Inscripcion> desinscribirAlumno(Integer cursoId) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.alumno = new Alumno();

        inscripcion.curso=new Curso();
        inscripcion.curso.id=cursoId;

        return delegate.returningResponse(inscripcion).desinscribirAlumno(cursoId);
    }
}
