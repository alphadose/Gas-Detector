package com.example.alphadose.gasdetector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static List<Gas> gasList = new ArrayList<>();
    private static GasAdapter gasAdapter;

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

        prepareGasData();

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
        HashMap<String, String> GasMap = new HashMap<String, String>();
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
