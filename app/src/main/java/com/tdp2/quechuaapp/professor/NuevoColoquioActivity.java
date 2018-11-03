package com.tdp2.quechuaapp.professor;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import com.applandeo.materialcalendarview.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.networking.model.ColoquioRequest;
import com.tdp2.quechuaapp.utils.DateUtils;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevoColoquioActivity extends AppCompatActivity {

    Curso curso;
    Calendar fechaColoquio;
    Calendar horaInicioColoquio;
    Calendar horaFinalColoquio;
    EditText horaInicioColoquioText;
    EditText horaFinalColoquioText;
    EditText fechaColoquioText;
    private DocenteService docenteService;

    DatePicker datePicker;

    private OnSelectDateListener listener = new OnSelectDateListener() {
        @Override
        public void onSelect(List<Calendar> calendars) {
            fechaColoquio=calendars.get(0);
            String fechaColoquioString=new SimpleDateFormat("dd/MM/yyyy").format(fechaColoquio.getTime());
            fechaColoquioText.setText(fechaColoquioString);
        }


    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_nuevo_final);

        curso = (Curso) getIntent().getSerializableExtra("curso");

        datePicker=buildDatePicker();

        setUpInitials();
        attachEvents();
    }

    private void attachEvents() {

        fechaColoquioText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if(fechaColoquioText.getError()==null){
                        datePicker.show();
                        fechaColoquioText.clearFocus();
                    }

                }
            }
        });

        fechaColoquioText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaColoquioText.setError(null);
                datePicker.show();
                fechaColoquioText.clearFocus();
            }
        });

        fechaColoquioText.setKeyListener(null);

        horaInicioColoquioText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(horaInicioColoquioText.getError()==null){
                        selectHoraInicioButtonPressed(v);
                        horaInicioColoquioText.clearFocus();
                    }

                }
            }
        });
        horaInicioColoquioText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                horaInicioColoquioText.setError(null);
                selectHoraInicioButtonPressed(v);
                horaInicioColoquioText.clearFocus();
            }
        });

        horaInicioColoquioText.setKeyListener(null);

        horaFinalColoquioText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(horaFinalColoquioText.getError()==null){
                        selectHoraFinalButtonPressed(v);
                        horaFinalColoquioText.clearFocus();
                    }
                }
            }
        });

        horaFinalColoquioText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                horaFinalColoquioText.setError(null);
                selectHoraFinalButtonPressed(v);
                horaFinalColoquioText.clearFocus();
            }
        });

        horaFinalColoquioText.setKeyListener(null);


        ImageButton button = findViewById(R.id.fechaColoquioButton);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
                fechaColoquioText.setError(null);
            }
        });

        Button establecerFechaButton = findViewById(R.id.establecerFechaButton);
        establecerFechaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarIngresos()){
                    mostrarMensajeConfirmacion();
                } else {
                    return;
                }
            }
        });
    }

    private void setUpInitials() {

        docenteService=new DocenteService();

        fechaColoquio=Calendar.getInstance();
        horaInicioColoquio=Calendar.getInstance();
        horaFinalColoquio=Calendar.getInstance();

        horaInicioColoquioText = findViewById(R.id.horaInicioColoquioText);
        horaFinalColoquioText = findViewById(R.id.horaFinalColoquioText);
        fechaColoquioText = findViewById(R.id.fechaColoquioText);


    }

    private void mostrarMensajeConfirmacion() {
        String fechaColoquioString=new SimpleDateFormat("dd/MM/yyyy").format(fechaColoquio.getTime());

        String messageToDisplay=String.format(getResources().getString(R.string.asignarFechaPregunta), fechaColoquioString, horaInicioColoquioText.getText(),
                horaFinalColoquioText.getText());
        showConfirmationAlert(NuevoColoquioActivity.this, "Confirmación de Creacion de Coloquio", messageToDisplay, "Asignar Fecha","Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nuevoColoquio();
                    }
                });
    }

    private boolean validarIngresos() {
        if(TextUtils.isEmpty(fechaColoquioText.getText())){
            fechaColoquioText.setError("Seleccione una fecha para el coloquio");
            fechaColoquioText.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(horaInicioColoquioText.getText())){
            horaInicioColoquioText.setError("Seleccione una hora de inicio el coloquio");
            horaInicioColoquioText.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(horaFinalColoquioText.getText())){

            horaFinalColoquioText.setError("Seleccione una hora de finalización para el coloquio");
            horaFinalColoquioText.requestFocus();
            return false;
        }

        if(!horaFinalColoquio.after(horaInicioColoquio)){
            horaFinalColoquioText.setError("La hora de finalización debe ser posterior a la hora de inicio");
            horaFinalColoquioText.requestFocus();
            return false;
        }
        return true;
    }

    private DatePicker buildDatePicker() {
        DatePickerBuilder builder = new DatePickerBuilder(NuevoColoquioActivity.this, listener)
                .pickerType(CalendarView.ONE_DAY_PICKER);

        //TODO: Setear periodo de finales
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.set(2018,9,07);
        max.set(2018,11,23);

        //TODO: Setear dias no permitidos
        List<Calendar> diasNoPermitidos = new ArrayList<>();

        List<Calendar> domingos=DateUtils.obtenerDomingosEntreFechas(min,max);

        diasNoPermitidos.addAll(domingos);


        datePicker = builder
                .date(Calendar.getInstance())
                .minimumDate(min)
                .maximumDate(max)
                .disabledDays(diasNoPermitidos)
                .disabledDaysLabelsColor(R.color.lightGray)
                .build();
        return datePicker;
    }

    private void nuevoColoquio() {
        ProgressBar loadingView = findViewById(R.id.loading_profesor_nuevo_final);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        ColoquioRequest coloquio = crearNuevoColoquio();

        docenteService.crearColoquios(coloquio, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_nuevo_final);
                loadingView.setVisibility(View.INVISIBLE);
                DialogBuilder.showAlert("La fecha de final ha sido asignada con éxito", "Fecha de Final Asignada", NuevoColoquioActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_nuevo_final);
                loadingView.setVisibility(View.INVISIBLE);
                DialogBuilder.showAlert("Hubo un error al asignar la fecha de final. Por favor reintente más tarde.","Asignación Fallida",NuevoColoquioActivity.this);

            }

            @Override
            public Context getContext() {
                return NuevoColoquioActivity.this;
            }
        });

    }

    private ColoquioRequest crearNuevoColoquio() {
        ColoquioRequest coloquio = new ColoquioRequest();
        coloquio.fecha=new SimpleDateFormat("yyyy-MM-dd").format(fechaColoquio.getTime());
        coloquio.horaInicio=horaInicioColoquioText.getText().toString();
        coloquio.horaFin=horaFinalColoquioText.getText().toString();
        coloquio.curso=curso;
        coloquio.periodo=curso.periodo;
        coloquio.estado = "ACTIVO";

        //TODO: VER COMO ESTABLECEMOS EL AULA Y SEDE DEL COLOQUIO
        coloquio.aula="414";
        coloquio.sede="PC";

        return coloquio;
    }


    public void selectHoraInicioButtonPressed(View view) {
        horaInicioColoquioText.setError(null);
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaInicioColoquio.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horaInicioColoquio.set(Calendar.MINUTE, minute);
                EditText horaInicioColoquioText=findViewById(R.id.horaInicioColoquioText);
                String horaInicio= new SimpleDateFormat("HH:mm").format(horaInicioColoquio.getTime());
                horaInicioColoquioText.setText(horaInicio);
            }
        },  Calendar.HOUR_OF_DAY,  Calendar.MINUTE, true);

        recogerHora.show();
    }

    public void selectHoraFinalButtonPressed(View view) {
        horaFinalColoquioText.setError(null);
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaFinalColoquio.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horaFinalColoquio.set(Calendar.MINUTE, minute);
                EditText horaFinColoquioText=findViewById(R.id.horaFinalColoquioText);
                String horaFin= new SimpleDateFormat("HH:mm").format(horaFinalColoquio.getTime());
                horaFinColoquioText.setText(horaFin);
            }
        },  Calendar.HOUR_OF_DAY,  Calendar.MINUTE, true);

        recogerHora.show();
    }

    public void showConfirmationAlert(Context context, String title, String messageToDisplay, String positiveMessage, String negativeMessage, DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveMessage, positiveListener);


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeMessage,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
