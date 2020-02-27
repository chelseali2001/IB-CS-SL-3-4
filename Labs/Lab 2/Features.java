import java.util.ArrayList;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.*;

/**
 * Features class
 * @author Chelsea Li
 */
public class Features {
    private Stage secondaryStage;
    private ImageView clockImage;
    private Group secondaryRoot;
    private Button menuButton;
    private ImageView hourHand;
    private ImageView minuteHand;
    private ImageView secondHand;
    private Label digitalLabel;
    private Label dateLabel;
    private RotateTransition hourTransition;
    private RotateTransition minuteTransition;
    private RotateTransition secondTransition;
    private Timeline digitalTimeline;
    static ArrayList<Features> featuresList;

    static {
        featuresList = new ArrayList<Features>();
    }

    /**
     * Creates and stores new features object
     * @param sStage
     * @param cImage
     * @param sRoot
     * @param mButton
     * @param hHand
     * @param minHand
     * @param secHand
     * @param digitalL
     * @param dateL
     * @param hTransition
     * @param minTransition
     * @param secTransition
     * @param dTimeline
     */
    public Features(Stage sStage, ImageView cImage, Group sRoot, Button mButton, 
                    ImageView hHand, ImageView minHand, ImageView secHand, 
                    Label digitalL, Label dateL, 
                    RotateTransition hTransition, RotateTransition minTransition, RotateTransition secTransition,
                    Timeline dTimeline) {
        this.secondaryStage = sStage;
        this.clockImage = cImage;
        this.secondaryRoot = sRoot;
        this.menuButton = mButton;
        this.hourHand = hHand;
        this.minuteHand = minHand;
        this.secondHand = secHand;
        this.digitalLabel = digitalL;
        this.dateLabel = dateL;
        this.hourTransition = hTransition;
        this.minuteTransition = minTransition;
        this.secondTransition = secTransition;
        this.digitalTimeline = dTimeline;
        featuresList.add(this);
    }

    /**
     * Gets secondary stage
     * @return secondaryStage
     */
    public Stage getSecondaryStage() {
        return secondaryStage;
    }

    public void setSecondaryStage(Stage sStage) {
        secondaryStage = sStage;
    }

    /**
     * Gets clock image
     * @return clockImage
     */
    public ImageView getClockImage() {
        return clockImage; 
    }

    public void setClockImage(ImageView cImage) {
        clockImage = cImage;
    }

    /**
     * Gets secondary root
     * @return secondaryRoot
     */
    public Group getSecondaryRoot() {
        return secondaryRoot; 
    }

    public void setSecondaryRoot(Group sRoot) {
        secondaryRoot = sRoot;
    }

    /**
     * Gets menu button
     * @return menu button
     */
    public Button getMenuButton() {
        return menuButton; 
    }

    public void setMenuButton(Button mButton) {
        menuButton = mButton;
    }

    /**
     * Gets hour hand
     * @return hour hand
     */
    public ImageView getHourHand() {
        return hourHand; 
    }

    public void setHourHand(ImageView hHand) {
        hourHand = hHand;
    }

    /**
     * Gets minute hand
     * @return minute hand
     */
    public ImageView getMinuteHand() {
        return minuteHand; 
    }

    public void setMinuteHand(ImageView minHand) {
        minuteHand = minHand;
    }

    /**
     * Gets second hand
     * @return second hand
     */
    public ImageView getSecondHand() {
        return secondHand; 
    }

    public void setSecondHand(ImageView secHand) {
        secondHand = secHand;
    }

    /**
     * Gets digital label
     * @return digitalLabel
     */
    public Label getDigitalLabel() {
        return digitalLabel; 
    }

    public void setDigitalLabel(Label digitalL) {
        digitalLabel = digitalL;
    }

    public Label getDateLabel() {
        return dateLabel; 
    }

    public void setDateLabel(Label dateL) {
        dateLabel = dateL;
    }

    public RotateTransition getHourTransition() {
        return hourTransition; 
    }

    public void setHourTransition(RotateTransition hTransition) {
        hourTransition = hTransition;
    }

    public RotateTransition getMinuteTransition() {
        return minuteTransition; 
    }

    public void setMinuteTransition(RotateTransition minTransition) {
        minuteTransition = minTransition;
    }

    public RotateTransition getSecondTransition() {
        return secondTransition; 
    }

    public void setSecondTransition(RotateTransition secTransition) {
        secondTransition = secTransition;
    }

    public Timeline getDigitalTimeline() {
        return digitalTimeline; 
    }

    public void setDigitalTimeline(Timeline dTimeline) {
        digitalTimeline = dTimeline;
    }
}