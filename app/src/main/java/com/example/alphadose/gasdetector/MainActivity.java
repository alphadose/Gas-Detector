package com.example.alphadose.gasdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static List<Gas> gasList = new ArrayList<>();
    private static GasAdapter gasAdapter;
    private static boolean prepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        gasAdapter = new GasAdapter(gasList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(gasAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView tr = view.findViewById(R.id.formula);
                        String formula = tr.getText().toString();
                        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                        intent.putExtra("gas", formula);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {}
                })
        );

        if(!prepared) {
            prepareGasData();
            prepared = true;
        }

        Intent intent = new Intent(this, BluetoothService.class);
        startService(intent);
    }

    private void prepareGasData() {
        Gas gas = new Gas("NO2", "Nitrogen Dioxide", "100 ppm");
        gasList.add(gas);

        gas = new Gas("SO2", "Sulphur Dioxide", "200 ppm");
        gasList.add(gas);

        gas = new Gas("O2", "Oxygen", "300 ppm");
        gasList.add(gas);

        gas = new Gas("Cl", "Chlorine", "400 ppm");
        gasList.add(gas);

        gas = new Gas("Ar", "Argon", "20 ppm");
        gasList.add(gas);

        gas = new Gas("Xe", "Xenon", "10 ppm");
        gasList.add(gas);

        gas = new Gas("H2", "Hydrogen", "100 ppm");
        gasList.add(gas);

        gas = new Gas("He", "Helium", "100 ppm");
        gasList.add(gas);

        gas = new Gas("N2", "Nitrogen", "1000 ppm");
        gasList.add(gas);

        gas = new Gas("F", "Fluorine", "40 ppm");
        gasList.add(gas);

        gas = new Gas("Ne", "Neon", "50 ppm");
        gasList.add(gas);

        gasAdapter.notifyDataSetChanged();
    }

    public static void updateGasData(String data) {
        HashMap<String, String> GasMap = new HashMap<>();
        String units[] = data.split(",");
        for(String unit: units) {
            GasMap.put(unit.split("=")[0], unit.split("=")[1]);
        }
        for(Gas gas: gasList) {
            gas.setConcentration(GasMap.get(gas.getFormula()) + " ppm");
        }
        gasAdapter.notifyDataSetChanged();
    }
}
