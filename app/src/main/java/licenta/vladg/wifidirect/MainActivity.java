package licenta.vladg.wifidirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WifiP2pManager p2pManager;
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver messageReceiver;
    private IntentFilter p2pFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        initFields();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageReceiver, p2pFilter);
    }



    private void initFields() {
        p2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = p2pManager.initialize(this, getMainLooper(), null);
        messageReceiver = new MessageReceiver(p2pManager, channel, this);

        p2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reasonCode) {

            }
        });


        p2pFilter = new IntentFilter();
        p2pFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        p2pFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        p2pFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        p2pFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void initComponents() {
        final Button sendButton = (Button) findViewById(R.id.sendBtn);
        final EditText messageTextView = (EditText) findViewById(R.id.messageTV);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = messageTextView.getText().toString();

                if (message != null && !message.isEmpty()) {
                    doSendMessage(message);
                }
            }
        });
    }

    private void doSendMessage(String message) {
        //TODO
    }

}
