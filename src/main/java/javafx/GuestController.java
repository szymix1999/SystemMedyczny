package javafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class GuestController {

    int adsIndex_1 = 0;
    int adsIndex_2 = 0;

    @FXML
    private ImageView imgAds_1;
    @FXML
    private ImageView imgAds_2;

    @FXML
    private void initialize() {
        randomAds();
    }

    @FXML
    private void guestPharmacyAction(){
        App.setRoot("pharmacy_guest");
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void randomAds() {
        adsIndex_1 = getRandomNumber(0,4);
        adsIndex_2 = getRandomNumber(0,4);
        imgAds_1.setImage(new Image(getClass().getResourceAsStream("/images/ads/" + adsIndex_1 + "_ads.gif")));
        imgAds_2.setImage(new Image(getClass().getResourceAsStream("/images/ads/" + adsIndex_2 + "_ads.gif")));
    }

    @FXML
    private void openWebAds_1() throws IOException, URISyntaxException {
        String url[] = {"https://google.com", "https://facebook.com", "https://github.com", "https://youtube.com"};
        Desktop.getDesktop().browse(new URL(url[adsIndex_1]).toURI());
    }

    @FXML
    private void openWebAds_2() throws IOException, URISyntaxException {
        String url[] = {"https://google.com", "https://facebook.com", "https://github.com", "https://youtube.com"};
        Desktop.getDesktop().browse(new URL(url[adsIndex_2]).toURI());
    }

}
