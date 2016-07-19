package sensors.myjankyapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by cost on 7/19/16.
 */
public class AcceptThread extends Thread{
    private final BluetoothServerSocket mmServerSocket;
    private final UUID MY_UUID = UUID.randomUUID();
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    String NAME = "buetooth  bob";

    public AcceptThread() {
        //Use temporary object that is later assigned to mmServerSocket,
        //because mmServerScoket is final
        BluetoothServerSocket tmp = null;
        try {
            //MY_UUID is app's UUID string, also used by client node
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        //Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            //If a connection was accepted
            if (socket != null) {
                //Do work to manage the connection (in a separate thread)
//                manageConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) { }
                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}
