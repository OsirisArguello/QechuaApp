package com.tdp2.quechuaapp.networking;


import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.networking.model.ColoquioRequest;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.mock.BehaviorDelegate;

public class MockDocenteService implements DocenteApi {

    private final BehaviorDelegate<DocenteApi> delegate;

    public MockDocenteService(BehaviorDelegate<DocenteApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<Curso> getInscripcionesACurso(Integer cursoId) {
        Curso curso = new Curso();
        curso.id=cursoId;

        return delegate.returningResponse(curso).getInscripcionesACurso(cursoId);
    }

    @Override
    public Call<Curso> getCurso(String apiToken, Integer cursoId) {
        return null;
    }

    @Override
    public Call<ArrayList<Curso>> getCursos(String a) {
        Curso curso = new Curso();
        return delegate.returningResponse(curso).getCursos("");
    }

    @Override
    public Call<Inscripcion> aceptar(String apiToken, Integer inscripcionId) {
        return null;
    }

    @Override
    public Call<Inscripcion> rechazar(String apiToken, Integer inscripcionId) {
        return null;
    }

    @Override
    public Call<ArrayList<Coloquio>> getColoquios(String apiToken, Integer cursoId) {
        return null;
    }

    @Override
    public Call<Coloquio> crearColoquio(String apiToken, ColoquioRequest coloquio) {
        return null;
    }

    @Override
    public Call<Coloquio> eliminarColoquio(String apiToken, ColoquioRequest coloquio) {
        return null;
    }


}
