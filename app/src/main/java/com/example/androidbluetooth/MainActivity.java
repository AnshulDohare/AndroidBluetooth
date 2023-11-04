package com.example.androidbluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BLUETOOTH = 0;
    private static final int REQUEST_DISCOVERABLE_BLUETOOTH = 1;
    TextView bluetoothText;
    Button turnOn, turnOff, discoverable;

    final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothText = findViewById(R.id.textBluetooth);
        turnOn = findViewById(R.id.btnTurnOnBluetooth);
        turnOff = findViewById(R.id.btnTurnOffBluetooth);
        discoverable = findViewById(R.id.btnBluetoothDiscoverable);
        if (bluetoothAdapter == null) {
            bluetoothText.append("Device not supports Bluetooth");
            turnOn.setClickable(false);
            turnOff.setClickable(false);
            discoverable.setClickable(false);
        }

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothText.setText("Bluetooth Turned On");
        }
        else{
            bluetoothText.setText("Bluetooth Turned Off");
        }

        turnOn.setOnClickListener(v -> {
            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);

            }
        });

        discoverable.setOnClickListener(v -> {
            if (bluetoothAdapter != null && !bluetoothAdapter.isDiscovering()) {
                Toast.makeText(this, "Making Device Discoverable", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(intent, REQUEST_DISCOVERABLE_BLUETOOTH);
            }
        });

        turnOff.setOnClickListener(v -> {
            if (bluetoothAdapter != null) {
                bluetoothAdapter.disable();
            }
            bluetoothText.setText("Bluetooth Turned Off");
            Toast.makeText(this, "Turning Off Bluetooth", Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint({"SetTextI18n", "MissingPermission"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==REQUEST_ENABLE_BLUETOOTH){
            if (bluetoothAdapter.isEnabled()){
                bluetoothText.setText("Bluetooth Turned On");
            }
        }
    }
}