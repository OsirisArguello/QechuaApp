package com.tdp2.quechuaapp.networking;


import static org.junit.Assert.*;
import com.tdp2.quechuaapp.model.Curso;

import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;


public class DocenteApiTest {
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
    public void testGetCurso() throws Exception {
        BehaviorDelegate<DocenteApi> delegate = mockRetrofit.create(DocenteApi.class);
        DocenteApi mockDocenteService = new MockDocenteService(delegate);

        Call<Curso> cursoCall=mockDocenteService.getCurso(new Integer(1));
        Response<Curso> cursoResponse = cursoCall.execute();

        assertTrue(cursoResponse.isSuccessful());
        assertEquals(cursoResponse.body().id,new Integer(1));


    }

    @Test
    public void testGetCursoFailure() throws Exception {
        BehaviorDelegate<DocenteApi> delegate = mockRetrofit.create(DocenteApi.class);
        DocenteApi mockDocenteService = new MockDocenteService(delegate);

        Call<Curso> cursoCall=mockDocenteService.getCurso(new Integer(1));
        Response<Curso> cursoResponse = cursoCall.execute();

        assertTrue(cursoResponse.isSuccessful());
        assertEquals(cursoResponse.body().id,new Integer(1));


    }



    /*@SmallTest
    public void testFailedQuoteRetrieval() throws Exception {
        BehaviorDelegate<QuoteOfTheDayRestService> delegate = mockRetrofit.create(QuoteOfTheDayRestService.class);
        MockFailedQODService mockQodService = new MockFailedQODService(delegate);

        //Actual Test
        Call<QuoteOfTheDayResponse> quote = mockQodService.getQuoteOfTheDay();
        Response<QuoteOfTheDayResponse> quoteOfTheDayResponse = quote.execute();
        Assert.assertFalse(quoteOfTheDayResponse.isSuccessful());

        Converter<ResponseBody, QuoteOfTheDayErrorResponse> errorConverter = retrofit.responseBodyConverter(QuoteOfTheDayErrorResponse.class, new Annotation[0]);
        QuoteOfTheDayErrorResponse error = errorConverter.convert(quoteOfTheDayResponse.errorBody());

        //Asserting response
        Assert.assertEquals(404, quoteOfTheDayResponse.code());
        Assert.assertEquals("Quote Not Found", error.getError().getMessage());

    }*/
}