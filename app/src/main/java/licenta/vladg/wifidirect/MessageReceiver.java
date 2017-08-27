package licenta.vladg.wifidirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Notifies when a WiFi P2P event is caught
 */
public class MessageReceiver extends BroadcastReceiver {

    private WifiP2pManager p2pManager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;

    WifiP2pManager.PeerListListener  myPeerListListener;

    public MessageReceiver(WifiP2pManager p2pManager, WifiP2pManager.Channel channel, MainActivity activity) {
        this.p2pManager = p2pManager;
        this.channel = channel;
        this.activity = activity;

        p2pManager.requestPeers(channel, myPeerListListener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();

        switch (actionName) {
            case (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION) :
                boolean state = checkState(intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1));
                break;

            case (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION) :
                if (p2pManager != null) {
                    p2pManager.requestPeers(channel, myPeerListListener);
                }
                break;

            case (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION) :
                //TODO
                break;

            case (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION) :
                //TODO
                break;

            default:
                break;
        }
    }

    private boolean checkState(int state) {
        return WifiP2pManager.WIFI_P2P_STATE_ENABLED == state;
    }

    private WifiP2pManager.PeerListListener getPeerListListener() {
        return myPeerListListener;
    }
}
