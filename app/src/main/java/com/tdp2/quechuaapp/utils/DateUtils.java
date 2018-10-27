package com.tdp2.quechuaapp.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtils {

    public static List<Calendar> obtenerDomingosEntreFechas(Calendar fechaInicial, Calendar fechaFinal){
        List<Calendar> listaDomingos = new ArrayList<>();
        Calendar fechaIterador=Calendar.getInstance();
        fechaIterador.setTime(fechaInicial.getTime());

        //Encontramos el primer domingo
        while (!fechaIterador.after(fechaFinal)){
            int dia=fechaIterador.get(Calendar.DAY_OF_WEEK);
            if(dia==Calendar.SUNDAY){
                Calendar domingo=Calendar.getInstance();
                domingo.setTime(fechaIterador.getTime());
                listaDomingos.add(domingo);
                break;
            }
            fechaIterador.add(Calendar.DAY_OF_YEAR,1);
        }

        fechaIterador.add(Calendar.DAY_OF_YEAR,7);

        //Agregamos el resto de domingos
        while (!fechaIterador.after(fechaFinal)){
            Calendar domingo=Calendar.getInstance();
            domingo.setTime(fechaIterador.getTime());
            listaDomingos.add(domingo);
            fechaIterador.add(Calendar.DAY_OF_YEAR,7);
        }

        return listaDomingos;
    }

}
