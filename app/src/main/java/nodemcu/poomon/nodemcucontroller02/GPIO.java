package nodemcu.poomon.nodemcucontroller02;

public class GPIO {
    private final String[] pins = {"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "RX", "TX"};
    private String returnURL, urlServer;
    private int pinNum;

    public GPIO(int pinNum){
        this.pinNum = pinNum;
        String urlServer = "192.168.1.16";
        returnURL = String.format("http://" + urlServer + "/" + pins[pinNum]);
    }
    public void resetURL(){
        returnURL = String.format("http://" + urlServer + "/" + pins[pinNum]);
    }
    public String getOnURL() {
        this.resetURL();
        return returnURL += "/1";
    }
    public String getOffURL(){
        this.resetURL();
        return returnURL += "/0";
    }
    public String getStateURL(){
        this.resetURL();
        return returnURL += "/state";
    }

    public void setUrlServer(String url){
        urlServer = url;
    }

}
