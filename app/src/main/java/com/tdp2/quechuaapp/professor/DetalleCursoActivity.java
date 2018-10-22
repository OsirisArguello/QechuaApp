package com.tdp2.quechuaapp.professor;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Horario;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.professor.view.ListadoInscriptosAdapter;
import com.tdp2.quechuaapp.professor.view.ListadoInscriptosAdapterCallback;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetalleCursoActivity extends AppCompatActivity implements ListadoInscriptosAdapterCallback {

    public Curso curso;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private DocenteService docenteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_cursos);

        curso = (Curso) getIntent().getSerializableExtra("curso");

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        setupInitials();
    }

    private void setupInitials() {

        ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        docenteService = new DocenteService();
        getCurso();
    }

    public void getCurso(){
        docenteService.getCurso(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                Curso curso = (Curso)responseBody;

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.getTabAt(0).setText("Regulares("+curso.getInscriptosRegulares().size()+")");
                tabLayout.getTabAt(1).setText("Condicionales("+curso.getInscriptosCondicionales().size()+")");

                TextView title = findViewById(R.id.id_curso_profesor);
                title.setText("Curso " + curso.id.toString());

                TextView capacidadCurso = findViewById(R.id.capacidad_curso_profesor);
                capacidadCurso.setText("Capacidad: " + curso.capacidadCurso);

                StringBuilder diaString=new StringBuilder();
                StringBuilder horasString=new StringBuilder();
                Integer cantHorarios=1;

                for (Horario horario : curso.horarios) {
                    diaString.append(horario.dia);
                    horasString.append(horario.horaInicio+"-"+horario.horaFin);
                    if(cantHorarios<curso.horarios.size()){
                        diaString.append("\n");
                        horasString.append("\n");
                    }
                    cantHorarios++;
                }

                TextView dias = findViewById(R.id.dias_curso_profesor);
                dias.setText(diaString);

                TextView horario = findViewById(R.id.horarios_curso_profesor);
                horario.setText(horasString);

                sectionsPagerAdapter.setRegulares(curso.getInscriptosRegulares());
                sectionsPagerAdapter.setCondicionales(curso.getInscriptosCondicionales());

                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);

                Toast.makeText(DetalleCursoActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(DetalleCursoActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }

            @Override
            public Context getContext() {
                return DetalleCursoActivity.this;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void aceptar(final Integer inscripcionId) {
        showConfirmationAlert(DetalleCursoActivity.this, "Confirmación de Aceptación", "¿Esta seguro que desea aceptar a este alumno en el curso?", "Aceptar Alumno", "Cancelar",
        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
            loadingView.bringToFront();
            loadingView.setVisibility(View.VISIBLE);
            docenteService.aceptarInscripcion(inscripcionId, new Client() {
                @Override
                public void onResponseSuccess(Object responseBody) {

                    getCurso();

                    DialogBuilder.showAlert("El alumno ha sido aceptado en el curso como REGULAR.","Inscripción Satisfactoria",DetalleCursoActivity.this);
                }

                @Override
                public void onResponseError(String errorMessage) {
                    DialogBuilder.showAlert("Hubo un error al intentar inscribir al alumno. Por favor reintente más tarde.","Inscripción Fallida",DetalleCursoActivity.this);
                }

                @Override
                public Context getContext() {
                    return DetalleCursoActivity.this;
                }
            });
            }
        });
    }

    @Override
    public void rechazar(final Integer inscripcionId) {

        showConfirmationAlert(DetalleCursoActivity.this, "Confirmación de Rechazo", "¿Esta seguro que desea rechazar la inscripción de este alumno?", "Rechazar Inscripción","Cancelar",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
                loadingView.bringToFront();
                loadingView.setVisibility(View.VISIBLE);
                docenteService.rechazarInscripcion(inscripcionId, new Client() {
                    @Override
                    public void onResponseSuccess(Object responseBody) {
                        getCurso();
                        DialogBuilder.showAlert("La inscripción del alumno ha sido rechazada","Rechazo Satisfactorio",DetalleCursoActivity.this);

                    }

                    @Override
                    public void onResponseError(String errorMessage) {
                        DialogBuilder.showAlert("Hubo un error al rechazar la inscripción del alumno. Por favor reintente más tarde.","Rechazo Fallido",DetalleCursoActivity.this);

                    }

                    @Override
                    public Context getContext() {
                        return DetalleCursoActivity.this;
                    }
                });
                }
            });



    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StudentsFragment extends Fragment {

        List<Inscripcion> inscriptos = new ArrayList<>();
        private ListadoInscriptosAdapter adapter;
        private Boolean condicional;

        public void setAlumnos(List<Inscripcion> inscriptos) {
            this.inscriptos = inscriptos;

            if (adapter != null) {
                adapter.clear();
                adapter.addAll(inscriptos);
                adapter.notifyDataSetChanged();
            }
        }

        public StudentsFragment() {
            condicional = false;
        }

        @SuppressLint("ValidFragment")
        public StudentsFragment(Boolean conditionalStudents) {
            condicional = conditionalStudents;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course_view, container, false);

            final ListView listView = rootView.findViewById(R.id.lista_estudiantes);
            adapter = new ListadoInscriptosAdapter(
                    getActivity(),
                    inscriptos
            );
            adapter.condicionales = condicional;
            listView.setAdapter(adapter);

            TextView emptyView = rootView.findViewById(R.id.emptyElementInscripciones);

            if(condicional){
                emptyView.setText("El curso no tiene inscriptos condicionales");
            } else {
                emptyView.setText("El curso aún no tiene inscriptos");
            }

            listView.setEmptyView(emptyView);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public final Integer INDEX_REGULARES = 0;
        public final Integer INDEX_CONDICIONALES = 1;

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private final Map<Integer, List<Inscripcion>> mDataSource = new HashMap<>();

        public void setRegulares(List<Inscripcion> regulares) {

            mDataSource.put(INDEX_REGULARES, regulares);

            StudentsFragment fragment = (StudentsFragment)mFragmentList.get(INDEX_REGULARES);
            fragment.setAlumnos(regulares);
        }

        public void setCondicionales(List<Inscripcion> condicionales) {
            mDataSource.put(INDEX_CONDICIONALES, condicionales);

            StudentsFragment fragment = (StudentsFragment)mFragmentList.get(INDEX_CONDICIONALES);
            fragment.setAlumnos(condicionales);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            // Load tabs
            addFragment(new StudentsFragment(), INDEX_REGULARES,"@string/curso.alumnos.regulares");
            addFragment(new StudentsFragment(true), INDEX_CONDICIONALES,"@string/curso.alumnos.condicionales");

            mDataSource.put(INDEX_REGULARES, new ArrayList<Inscripcion>());
            mDataSource.put(INDEX_CONDICIONALES, new ArrayList<Inscripcion>());
        }

        private void addFragment(Fragment fragment, Integer index, String title) {
            mFragmentList.add(index, fragment);
            mFragmentTitleList.add(index, title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount () {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position) + " (" + mDataSource.get(position).size() + ')';
        }
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

    private void callAcceptService() {

    }

}