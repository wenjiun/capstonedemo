package my.edu.mmu.foe.capstoneprojectdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class MainActivityFragment extends Fragment {

    // Please modify it to the correct BLE MAC Address
    private static final String ADDRESS = "00:1A:7D:DA:71:13";

    private static final String CAPSTONE_SERVICE_UUID = "13333333-3333-3333-3333-333333333337";

    private static final String CAPSTONE_CHARACTERISTICS_UUID = "13333333-3333-3333-3333-333333330001";
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic charCapstone;
    private EditText editText;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothManager = (BluetoothManager)getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        editText = (EditText)view.findViewById(R.id.editText);
        view.findViewById(R.id.buttonConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device = mBluetoothAdapter.getRemoteDevice(ADDRESS);
                bluetoothGatt = device.connectGatt(getActivity(), true, new BluetoothGattCallback() {
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        super.onConnectionStateChange(gatt, status, newState);
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            gatt.discoverServices();
                        }
                    }
                    @Override
                    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                        super.onServicesDiscovered(gatt, status);
                        charCapstone = gatt.getService(UUID.fromString(CAPSTONE_SERVICE_UUID))
                                .getCharacteristic(UUID.fromString(CAPSTONE_CHARACTERISTICS_UUID));
                        bluetoothGatt = gatt;
                    }
                    @Override
                    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                        super.onCharacteristicRead(gatt, characteristic, status);
                        final byte[] values = characteristic.getValue();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // only 1 byte of data is expected
                                editText.setText(""+values[0]);
                            }
                        });
                    }
                });
            }
        });
        view.findViewById(R.id.buttonRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothGatt.readCharacteristic(charCapstone);
            }
        });
        view.findViewById(R.id.buttonWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte value = (byte)Integer.parseInt(editText.getText().toString());
                charCapstone.setValue(new byte[]{value});
                bluetoothGatt.writeCharacteristic(charCapstone);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bluetoothGatt.disconnect();
    }
}
