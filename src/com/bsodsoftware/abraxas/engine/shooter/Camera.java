package com.bsodsoftware.abraxas.engine.shooter;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera  {
   public double xPos;
   public double yPos;
   public double xDir;
   public double yDir;
   public double xPlane;
   public double yPlane;
   public boolean left;
   public boolean right;
   public boolean forward;
   public boolean back;
   public final double MOVE_SPEED = 0.09D;
   public final double ROTATION_SPEED = 0.06D;

   public Camera(double x, double y, double xd, double yd, double xp, double yp) {
      this.xPos = x;
      this.yPos = y;
      this.xDir = xd;
      this.yDir = yd;
      this.xPlane = xp;
      this.yPlane = yp;
   }

   public void update(int[][] map) {
      if (this.forward) {
         if (map[(int)(this.xPos + this.xDir * MOVE_SPEED)][(int)this.yPos] == 0) {
            this.xPos += this.xDir * MOVE_SPEED;
         }

         if (map[(int)this.xPos][(int)(this.yPos + this.yDir * MOVE_SPEED)] == 0) {
            this.yPos += this.yDir * MOVE_SPEED;
         }
      }

      if (this.back) {
         if (map[(int)(this.xPos - this.xDir * MOVE_SPEED)][(int)this.yPos] == 0) {
            this.xPos -= this.xDir * MOVE_SPEED;
         }

         if (map[(int)this.xPos][(int)(this.yPos - this.yDir * MOVE_SPEED)] == 0) {
            this.yPos -= this.yDir * MOVE_SPEED;
         }
      }

      double oldxDir;
      double oldxPlane;
      if (this.right) {
         oldxDir = this.xDir;
         this.xDir = this.xDir * Math.cos(-ROTATION_SPEED) - this.yDir * Math.sin(-ROTATION_SPEED);
         this.yDir = oldxDir * Math.sin(-ROTATION_SPEED) + this.yDir * Math.cos(-ROTATION_SPEED);
         oldxPlane = this.xPlane;
         this.xPlane = this.xPlane * Math.cos(-ROTATION_SPEED) - this.yPlane * Math.sin(-ROTATION_SPEED);
         this.yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + this.yPlane * Math.cos(-ROTATION_SPEED);
      }

      if (this.left) {
         oldxDir = this.xDir;
         this.xDir = this.xDir * Math.cos(ROTATION_SPEED) - this.yDir * Math.sin(0.06D);
         this.yDir = oldxDir * Math.sin(ROTATION_SPEED) + this.yDir * Math.cos(0.06D);
         oldxPlane = this.xPlane;
         this.xPlane = this.xPlane * Math.cos(ROTATION_SPEED) - this.yPlane * Math.sin(ROTATION_SPEED);
         this.yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + this.yPlane * Math.cos(ROTATION_SPEED);
      }
      System.out.println("xDir: " + this.xDir + " - yDir: " + this.yDir + " - xPos: " + this.xPos + " - yPos: " + this.yPos);
   }

   public void keyPressed(int key) {
      if (key == KeyInputEnum.LEFT_ARROW.getValue()) {
         this.left = true;
      }

      if (key == KeyInputEnum.RIGHT_ARROW.getValue()) {
         this.right = true;
      }

      if (key == KeyInputEnum.UP_ARROW.getValue()) {
         this.forward = true;
      }

      if (key == KeyInputEnum.DOWN_ARROW.getValue()) {
         this.back = true;
      }
      
      if (key == KeyInputEnum.ESC.getValue()) {
    	  System.out.println("Buh bye");
    	  System.exit(0);
      }

   }

   public void keyReleased(int key) {
      if (key == 37) {
         this.left = false;
      }

      if (key == 39) {
         this.right = false;
      }

      if (key == 38) {
         this.forward = false;
      }

      if (key == 40) {
         this.back = false;
      }

   }

   public void keyTyped(KeyEvent key) {
   }

   public class CameraValues {
      public static final double STARTING_LOCATION_X = 4.5D;
      public static final double STARTING_LOCATION_Y = 4.5D;
      public static final double FOV = -0.66D;
   }
}
