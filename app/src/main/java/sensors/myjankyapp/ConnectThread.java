package sensors.myjankyapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by cost on 7/19/16.
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final UUID MY_UUID = UUID.randomUUID();

    public ConnectThread(BluetoothDevice device) {
        //Use tmp object that is later assigned to mmScoket,
        //because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        //Get a BluetoothScoket to connect wiht the given BluetoothDevice
        try {
            //MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
    }

    public void run() {
        //Cancel discovery because it will slow down the connection
//        mBluetoothAdapter.cancelDiscovery();

        try {
            //connect the devicee through the socket. This will block
            //Until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            //Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        //Do work to manage the connection 9in a separate thread)
//        manageConnectedSocket(mmSocket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
