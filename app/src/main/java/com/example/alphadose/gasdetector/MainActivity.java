package com.example.alphadose.gasdetector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private List<Gas> gasList = new ArrayList<>();
    private GasAdapter gasAdapter;

    BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    public String DEVICE_ADDRESS = "30:58:01:D6:19:46";
    public UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public BluetoothDevice device;
    public Boolean found = false;
    public BluetoothSocket socket;
    public OutputStream outputStream;
    public InputStream inputStream;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if(bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if(iterator.getAddress().equals(DEVICE_ADDRESS)) //Replace with iterator.getName() if comparing Device names.
                {
                    device=iterator; //device is an object of type BluetoothDevice
                    found=true;
                    break;
                } } }
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();

            outputStream = socket.getOutputStream();

            inputStream = socket.getInputStream();

            int byteCount = inputStream.available();
            if (byteCount > 0) {
                byte[] rawBytes = new byte[byteCount];
                inputStream.read(rawBytes);
                final String string = new String(rawBytes, "UTF-8");
                handler.post(new Runnable() {
                    public void run() {
                        Log.d("message", string);
                    }
                });
            }
        }
        catch (Exception e) {
            Log.d("message", "error", e);
        }
        gasAdapter = new GasAdapter(gasList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(gasAdapter);

        prepareGasData();
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
}
