package com.tdp2.quechuaapp.professor;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
import android.widget.Toast;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.professor.view.AlumnosAdapter;

import java.util.ArrayList;

public class DetalleCursoActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void setupInitials() {
        DocenteService service = new DocenteService();
        service.getCurso(0, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                // TODO: get list of students from curso
                Curso curso =(Curso) responseBody;

                Alumno alum0 = new Alumno();
                alum0.nombre = "Lucia";
                alum0.apellido = "Capon";

                Alumno alum1 = new Alumno();
                alum1.nombre = "Juan";
                alum1.apellido = "Gonzalez";

                ArrayList<Alumno> regList = new ArrayList<>();
                regList.add(alum0);

                ArrayList<Alumno> condList = new ArrayList<>();
                condList.add(alum1);

                sectionsPagerAdapter.regular = regList;
                sectionsPagerAdapter.condicional = condList;

                /*
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);
                */
                viewPager.refreshDrawableState();
            }

            @Override
            public void onResponseError(String errorMessage) {
                /*
                ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);
                */
                Toast.makeText(DetalleCursoActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StudentsFragment extends Fragment {

        ArrayList<Alumno> alumnos;

        public StudentsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course_view, container, false);

            final ListView listView = rootView.findViewById(R.id.lista_estudiantes);
            AlumnosAdapter adapter = new AlumnosAdapter(
                    getActivity(),
                    alumnos
            );
            listView.setAdapter(adapter);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Alumno> regular;
        ArrayList<Alumno> condicional;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            StudentsFragment fragment = new StudentsFragment();
            fragment.alumnos = position == 0 ? regular : condicional;
            return fragment;
        }

        @Override
        public int getCount () {
            return 2;
        }
    }

}