package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Inscripcion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EstudianteApiTest {
    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;

    @Before
    public void setUp() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("http://test.com")
                        .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder
                .client(httpClient.build())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Test
    public void testGetCursos() throws Exception{
        BehaviorDelegate<EstudianteApi> delegate = mockRetrofit.create(EstudianteApi.class);
        EstudianteApi mockEstudianteService = new MockEstudianteApi(delegate);

        Call<ArrayList<Curso>> cursosCall=mockEstudianteService.getCursos();
        Response<ArrayList<Curso>> cursosResponse = cursosCall.execute();

        assertTrue(cursosResponse.isSuccessful());
        assertEquals(cursosResponse.body().size(),2);
    }

    @Test
    public void testGetCursosPorMateria() throws Exception{
        BehaviorDelegate<EstudianteApi> delegate = mockRetrofit.create(EstudianteApi.class);
        EstudianteApi mockEstudianteService = new MockEstudianteApi(delegate);

        Integer idMateria=new Integer(2);

        Call<ArrayList<Curso>> cursosCall=mockEstudianteService.getCursosPorMateria(idMateria);
        Response<ArrayList<Curso>> cursosResponse = cursosCall.execute();

        assertTrue(cursosResponse.isSuccessful());
        assertEquals(cursosResponse.body().size(),1);
        assertEquals(cursosResponse.body().get(0).materia.id,idMateria);
    }

    @Test
    public void testInscribirAlumno() throws Exception {
        BehaviorDelegate<EstudianteApi> delegate = mockRetrofit.create(EstudianteApi.class);
        EstudianteApi mockEstudianteService = new MockEstudianteApi(delegate);

        Integer idAlumno=new Integer(6);
        Integer idCurso=new Integer(2);

        Call<Inscripcion> inscripcionCall=mockEstudianteService.inscribirAlumno(idAlumno,idCurso);
        Response<Inscripcion> inscripcionResponse = inscripcionCall.execute();

        assertTrue(inscripcionResponse.isSuccessful());
        assertEquals(inscripcionResponse.body().alumno.id,idAlumno);
        assertEquals(inscripcionResponse.body().curso.id,idCurso);

    }

}
