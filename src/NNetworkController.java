
public class NNetworkController {
    private NNetwork network;
    private NNetworkView networkView;

    public NNetworkController(NNetwork network) {
        this.network = network;
        this.networkView = new NNetworkView(network);
    }

    public void show() {
        networkView.show(network.getPrimaryStage());
        networkView.setUpButtons(network.getPrimaryStage());
    }
}
