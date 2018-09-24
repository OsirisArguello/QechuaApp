package com.tdp2.quechuaapp.professor;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.professor.view.AlumnosAdapter;

import java.util.ArrayList;
import java.util.Map;

public class DetalleCursoActivity extends AppCompatActivity {

    public Curso curso = DocenteService.getCursoMock(0);

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

        setupInitials();
    }

    private void setupInitials() {
        TextView title = ((Toolbar)findViewById(R.id.toolbar)).findViewById(R.id.toolbar_title);
        title.setText("Curso " + curso.id.toString() + "\n \n" + "Vacantes: " + curso.vacantes.toString() + "\n");

        ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        DocenteService service = new DocenteService();
        service.getCurso(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                Curso curso = (Curso)responseBody;
                sectionsPagerAdapter.setRegulares(curso.est_regulares);
                sectionsPagerAdapter.setCondicionales(curso.est_condicionales);

                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_detalle_curso);
                loadingView.setVisibility(View.INVISIBLE);

                // TODO: Remove this, just for testing
                Curso curso = DocenteService.getCursoMock(0);
                sectionsPagerAdapter.setRegulares(curso.est_regulares);
                sectionsPagerAdapter.setCondicionales(curso.est_condicionales);
                /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

        List<Alumno> alumnos = new ArrayList<>();
        private AlumnosAdapter adapter;
        private Boolean condicional;

        public void setAlumnos(List<Alumno> alumnos) {
            this.alumnos = alumnos;

            if (adapter != null) {
                adapter.clear();
                adapter.addAll(alumnos);
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
            adapter = new AlumnosAdapter(
                    getActivity(),
                    alumnos
            );
            adapter.condicionales = condicional;
            listView.setAdapter(adapter);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public final Integer INDEX_REGULARES = 0;
        public final Integer INDEX_CONDICIONALES = 1;

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private final Map<Integer, List<Alumno>> mDataSource = new HashMap<>();

        public void setRegulares(List<Alumno> regulares) {
            mDataSource.put(INDEX_REGULARES, regulares);

            StudentsFragment fragment = (StudentsFragment)mFragmentList.get(INDEX_REGULARES);
            fragment.setAlumnos(regulares);
        }

        public void setCondicionales(List<Alumno> condicionales) {
            mDataSource.put(INDEX_CONDICIONALES, condicionales);

            StudentsFragment fragment = (StudentsFragment)mFragmentList.get(INDEX_CONDICIONALES);
            fragment.setAlumnos(condicionales);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            // Load tabs
            addFragment(new StudentsFragment(), INDEX_REGULARES,"@string/curso.alumnos.regulares");
            addFragment(new StudentsFragment(true), INDEX_CONDICIONALES,"@string/curso.alumnos.condicionales");

            mDataSource.put(INDEX_REGULARES, new ArrayList<Alumno>());
            mDataSource.put(INDEX_CONDICIONALES, new ArrayList<Alumno>());
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

}