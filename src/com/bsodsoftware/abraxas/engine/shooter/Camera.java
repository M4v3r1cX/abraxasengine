package com.bsodsoftware.abraxas.engine.shooter;

import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.player.Player;

import java.awt.event.KeyEvent;

public class Camera  {
   private double xPos;
   private double yPos;
   private double xDir;
   private double yDir;
   private double xPlane;
   private double yPlane;
   private boolean left;
   private boolean right;
   private boolean forward;
   private boolean back;
   private boolean strafeRight;
   private boolean strafeLeft;
   private final double MOVE_SPEED = 0.07D;
   private final double ROTATION_SPEED = 0.06D;
   private Player player;
   private boolean vieneDePausa;

   public Camera(double x, double y, double xd, double yd, double xp, double yp, Player player) {
      this.xPos = x;
      this.yPos = y;
      this.xDir = xd;
      this.yDir = yd;
      this.xPlane = xp;
      this.yPlane = yp;
      this.player = player;
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
   }

   public void keyPressed(int key) {
      System.out.println("tecla apretada: " + key);
      if (this.player.getState().equals(Player.STATE.PAUSE)) {
         // Moverse por menú de pausa

         if (key == KeyInputEnum.ESC.getValue()) {
            this.player.setState(Player.STATE.STANDING);
            vieneDePausa = true;
         }
      }

      if (!this.player.getState().equals(Player.STATE.PAUSE) && !this.player.getState().equals(Player.STATE.IN_EVENT)) {
         System.out.println("Ni en pausa ni en evento");
         if (key == KeyInputEnum.LEFT_ARROW.getValue()) {
            this.left = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.RIGHT_ARROW.getValue()) {
            this.right = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.UP_ARROW.getValue() || key == KeyInputEnum.W.getValue()) {
            this.forward = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.DOWN_ARROW.getValue() || key == KeyInputEnum.S.getValue()) {
            this.back = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.A.getValue()) {
            this.strafeLeft = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.D.getValue()) {
            this.strafeRight = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (!vieneDePausa) {
            if (key == KeyInputEnum.ESC.getValue()) {
               this.player.setState(Player.STATE.PAUSE);
               vieneDePausa = false;
            }
         }
      }
   }

   public void keyReleased(int key) {
      if (key == KeyInputEnum.LEFT_ARROW.getValue()) {
         this.left = false;
      }

      if (key == KeyInputEnum.RIGHT_ARROW.getValue()) {
         this.right = false;
      }

      if (key == KeyInputEnum.UP_ARROW.getValue() || key == KeyInputEnum.W.getValue()) {
         this.forward = false;
      }

      if (key == KeyInputEnum.DOWN_ARROW.getValue() || key == KeyInputEnum.S.getValue()) {
         this.back = false;
      }

      if (key == KeyInputEnum.A.getValue()) {
         this.strafeLeft = false;
      }

      if (key == KeyInputEnum.D.getValue()) {
         this.strafeRight = false;
      }

      /*if (!isRight() && !isLeft() && !isForward() && !isBack() && !this.player.getState().equals(Player.STATE.PAUSE)) {
         this.player.setState(Player.STATE.STANDING);
      }*/

   }

   public void stopPlayerMovement(Player.STATE state){
      this.forward = false;
      this.back = false;
      this.left = false;
      this.right = false;
      this.strafeLeft = false;
      this.strafeRight = false;
      this.player.setState(state);
   }

   public void keyTyped(KeyEvent key) {
   }

   public class CameraValues {
      public static final double STARTING_LOCATION_X = 4.5D;
      public static final double STARTING_LOCATION_Y = 4.5D;
      public static final double FOV = -0.66D;
   }

   public double getxPos() {
      return xPos;
   }

   public void setxPos(double xPos) {
      this.xPos = xPos;
   }

   public double getyPos() {
      return yPos;
   }

   public void setyPos(double yPos) {
      this.yPos = yPos;
   }

   public double getxDir() {
      return xDir;
   }

   public void setxDir(double xDir) {
      this.xDir = xDir;
   }

   public double getyDir() {
      return yDir;
   }

   public void setyDir(double yDir) {
      this.yDir = yDir;
   }

   public double getxPlane() {
      return xPlane;
   }

   public void setxPlane(double xPlane) {
      this.xPlane = xPlane;
   }

   public double getyPlane() {
      return yPlane;
   }

   public void setyPlane(double yPlane) {
      this.yPlane = yPlane;
   }

   public boolean isLeft() {
      return left;
   }

   public void setLeft(boolean left) {
      this.left = left;
   }

   public boolean isRight() {
      return right;
   }

   public void setRight(boolean right) {
      this.right = right;
   }

   public boolean isForward() {
      return forward;
   }

   public void setForward(boolean forward) {
      this.forward = forward;
   }

   public boolean isBack() {
      return back;
   }

   public void setBack(boolean back) {
      this.back = back;
   }

   public double getMOVE_SPEED() {
      return MOVE_SPEED;
   }

   public double getROTATION_SPEED() {
      return ROTATION_SPEED;
   }

   public boolean isStrafeRight() {
      return strafeRight;
   }

   public void setStrafeRight(boolean strafeRight) {
      this.strafeRight = strafeRight;
   }

   public boolean isStrafeLeft() {
      return strafeLeft;
   }

   public void setStrafeLeft(boolean strafeLeft) {
      this.strafeLeft = strafeLeft;
   }

   public boolean isVieneDePausa() {
      return vieneDePausa;
   }

   public void setVieneDePausa(boolean vieneDePausa) {
      this.vieneDePausa = vieneDePausa;
   }
}
