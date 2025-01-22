package np.nicolai.btaura;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Set;

public class ActivityOne extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    ListView bluetooth_devices_listview;
    ArrayList<String> al = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private static final int BT_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        checkBluetoothPermissions();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth isn't supported", Toast.LENGTH_SHORT).show();
            return;
        }
        pairedDevices = bluetoothAdapter.getBondedDevices();

        bluetooth_devices_listview = findViewById(R.id.listViewBluetoothDevices);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        bluetooth_devices_listview.setAdapter(arrayAdapter);
        pairedDevices = bluetoothAdapter.getBondedDevices();
        bluetooth_devices_listview.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            String[] parts = selectedItem.split("\n");
            String deviceName = parts[0];
            String macAddress = parts[1];
            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (deviceName.equals("HC-05") || deviceName.equals("HC-06") && bluetoothDevice.getAddress().equals(macAddress)) {
                    Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void enableBluetooth(View v) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            return;
        if(!bluetoothAdapter.isEnabled()){
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetooth);
            bluetoothAdapter.enable();
        }
        else
            Toast.makeText(this, "Bluetooth already enabled", Toast.LENGTH_SHORT).show();
    }
    public void bluetoothStatus(View v){
        if(bluetoothAdapter.isEnabled())
            Toast.makeText(this, "Bluetooth in ON", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Bluetooth in OFF", Toast.LENGTH_SHORT).show();
    }
    public void showPairedDevices(View v){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if(!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth in Disabled", Toast.LENGTH_SHORT).show();
            return;
        }
        pairedDevices = bluetoothAdapter.getBondedDevices();
        al.clear();
        for(BluetoothDevice bd:pairedDevices) {
            al.add(bd.getName() + "\n" + bd.getAddress());
        }
        arrayAdapter.notifyDataSetChanged();
    }
    private void checkBluetoothPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.BLUETOOTH_CONNECT, android.Manifest.permission.BLUETOOTH_SCAN},
                    BT_CODE);
        }
    }

}