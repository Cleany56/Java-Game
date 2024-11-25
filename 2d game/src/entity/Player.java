package entity;

import main.KeyHandler;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    //public int hasKey = 0;
    int standCounter = 0;
    
    

    public Player(GamePanel gp, KeyHandler keyH)  {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY= gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues()  {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage()  {

        up1 = setup("player_up");
        up2 = setup("player_up2");
        down1 = setup("player_down");
        down2 = setup("player_down2");
        left1 = setup("player_left");
        left2 = setup("player_left2");
        right1 = setup("player_right");
        right2 = setup("player_right2");


    }
    public BufferedImage setup(String imageName)  {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName+ ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update ()  {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true) {
                direction = "up";
                
    
            }
            else if (keyH.downPressed == true) {
                direction = "down";
                
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
                
            }
            else if (keyH.rightPressed == true){
                direction = "right";
                
            }
            if(dash0n == true) {
				speed = 8; // you can type any number
			}
			if(dash0n == false) {
				speed = 4;
			}
            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    
                }
            }

            spriteCounter++;
            if (spriteCounter > 20) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
    
            }


        }
       else {
            standCounter++;
            if(standCounter == 20){
            spriteNum = 1;
            standCounter = 0;
            }
       }

    }

    public void pickUpObject(int i){
        if (i != 999) {
            
         
        }
    }
    public void draw(Graphics2D g2)  {
        BufferedImage image = null;
        switch(direction)  {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                image = down1;
                }
                if (spriteNum == 2) {
                image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                image = left1;
                }
                if (spriteNum == 2) {
                image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                image = right1;
                }
                if (spriteNum == 2) {
                image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
