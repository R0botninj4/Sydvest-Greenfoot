import greenfoot.*;

public class Button extends Actor {
    private String text;
    private int width = 200;
    private int height = 60;

    public Button(String text) {
        this.text = text;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Color.LIGHT_GRAY);
        img.fillRect(0, 0, width, height);
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, width - 1, height - 1);

        GreenfootImage textImg = new GreenfootImage(text, 24, Color.BLACK, null);
        img.drawImage(textImg, (width - textImg.getWidth()) / 2, (height - textImg.getHeight()) / 2);
        setImage(img);
    }

    public String getText() {
        return text;
    }
}
