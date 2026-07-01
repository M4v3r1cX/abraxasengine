package com.bsodsoftware.abraxas.engine.raycaster;

import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.events.CollisionEngine;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.things.Enemy;
import com.bsodsoftware.abraxas.engine.things.Player;
import com.bsodsoftware.abraxas.engine.things.Door;

import java.awt.event.KeyEvent;
import java.util.List;

public class Camera {
   private float xPos;
   private float yPos;
   private float xDir;
   private float yDir;
   private float xPlane;
   private float yPlane;
   private boolean left;
   private boolean right;
   private boolean forward;
   private boolean back;
   private boolean strafeRight;
   private boolean strafeLeft;
   private final float MOVE_SPEED = 0.07f;
   private final float ROTATION_SPEED = 0.06f;
   private Player player;
   private boolean vieneDePausa;
   private float mouseDeltaX;
   private int lastMouseX;
   private CollisionEngine collisionEngine;

   public Camera(float x, float y, float xd, float yd, float xp, float yp, Player player, CollisionEngine collisionEngine) {
      this.xPos = x;
      this.yPos = y;
      this.xDir = xd;
      this.yDir = yd;
      this.xPlane = xp;
      this.yPlane = yp;
      this.player = player;
      this.mouseDeltaX = 0;
      this.lastMouseX = 0;
      this.collisionEngine = collisionEngine;
   }

   public void update(int[][] map, Door[][] doors, List<SpriteRaycaster> sprites) {
      float moveX = 0, moveY = 0;
      if (this.forward) {
         moveX += this.xDir;
         moveY += this.yDir;
      }
      if (this.back) {
         moveX -= this.xDir;
         moveY -= this.yDir;
      }

      if (this.strafeLeft) {
         moveX += -this.yDir;
         moveY += this.xDir;
      }

      if (this.strafeRight) {
         moveX += this.yDir;
         moveY += -this.xDir;
      }

      float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
      if (length > 0) {
         moveX /= length;
         moveY /= length;
      }

      float newX = this.xPos + moveX * MOVE_SPEED;
      float newY = this.yPos + moveY * MOVE_SPEED;

      int checkX = (int)(newX + Math.signum(moveX) * player.getRadius());
      int checkY = (int)this.yPos;
      boolean canMoveX = isWalkable(checkX, checkY, doors, map);

      checkX = (int)this.xPos;
      checkY = (int)(newY + Math.signum(moveY) * player.getRadius());
      boolean canMoveY = isWalkable(checkX, checkY, doors, map);

      boolean spriteBlockX = collisionEngine.collidesWithSprite(newX, this.yPos, sprites, player.getRadius());
      boolean spriteBlockY = collisionEngine.collidesWithSprite(this.xPos, newY, sprites, player.getRadius());

      if (canMoveX && !spriteBlockX) {
         this.xPos = newX;
      }

      if (canMoveY && !spriteBlockY) {
         this.yPos = newY;
      }

      float oldxDir;
      float oldxPlane;
      if (this.right) {
         oldxDir = this.xDir;
         this.xDir = (float) (this.xDir * Math.cos(-ROTATION_SPEED) - this.yDir * Math.sin(-ROTATION_SPEED));
         this.yDir = (float) (oldxDir * Math.sin(-ROTATION_SPEED) + this.yDir * Math.cos(-ROTATION_SPEED));
         oldxPlane = this.xPlane;
         this.xPlane = (float) (this.xPlane * Math.cos(-ROTATION_SPEED) - this.yPlane * Math.sin(-ROTATION_SPEED));
         this.yPlane = (float) (oldxPlane * Math.sin(-ROTATION_SPEED) + this.yPlane * Math.cos(-ROTATION_SPEED));
      }

      if (this.left) {
         oldxDir = this.xDir;
         this.xDir = (float) (this.xDir * Math.cos(ROTATION_SPEED) - this.yDir * Math.sin(ROTATION_SPEED));
         this.yDir = (float) (oldxDir * Math.sin(ROTATION_SPEED) + this.yDir * Math.cos(ROTATION_SPEED));
         oldxPlane = this.xPlane;
         this.xPlane = (float) (this.xPlane * Math.cos(ROTATION_SPEED) - this.yPlane * Math.sin(ROTATION_SPEED));
         this.yPlane = (float) (oldxPlane * Math.sin(ROTATION_SPEED) + this.yPlane * Math.cos(ROTATION_SPEED));
      }
   }


   private boolean isWalkable(int x, int y, Door[][] doors, int[][] map) {
      boolean ret = false;
      int tile = map[x][y];
      if (tile == 0) {
         ret = true;
      }
      if (tile == 3) {
         if (doors != null && doors.length > 1) {
            Door door = doors[x][y];
            ret = door != null && door.openAmount >= 0.9f;
         }
      }
      return ret;
   }


   public void tryOpenDoor(int[][] map, Door[][] doors) {
      float reach = 0.8f;
      int checkX = (int)(xPos + xDir * reach);
      int checkY = (int)(yPos + yDir * reach);
      if (map[checkX][checkY] == 3) { // DOOR TILE
         Door door = doors[checkX][checkY];
         if (door != null) {
            if (door.isOpening()) {
               door.setClosing(true);
               door.setOpening(false);
            } else {
               door.setOpening(true);
               door.setClosing(false);
            }
         }
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
         if (key == KeyInputEnum.LEFT_ARROW.getValue() || key == KeyInputEnum.Q.getValue()) {
            this.left = true;
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.RIGHT_ARROW.getValue() || key == KeyInputEnum.E.getValue()) {
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
      if (key == KeyInputEnum.LEFT_ARROW.getValue() || key == KeyInputEnum.Q.getValue()) {
         this.left = false;
      }

      if (key == KeyInputEnum.RIGHT_ARROW.getValue() || key == KeyInputEnum.E.getValue()) {
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

      if (!isRight() && !isLeft() && !isForward() && !isBack() && !this.player.getState().equals(Player.STATE.PAUSE)) {
         this.player.setState(Player.STATE.STANDING);
      }

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

   public void setMouseDeltaX(float deltaX) {
      this.mouseDeltaX = deltaX;
   }

   public float getxPos() {
      return xPos;
   }

   public void setxPos(float xPos) {
      this.xPos = xPos;
   }

   public float getyPos() {
      return yPos;
   }

   public void setyPos(float yPos) {
      this.yPos = yPos;
   }

   public float getxDir() {
      return xDir;
   }

   public void setxDir(float xDir) {
      this.xDir = xDir;
   }

   public float getyDir() {
      return yDir;
   }

   public void setyDir(float yDir) {
      this.yDir = yDir;
   }

   public float getxPlane() {
      return xPlane;
   }

   public void setxPlane(float xPlane) {
      this.xPlane = xPlane;
   }

   public float getyPlane() {
      return yPlane;
   }

   public void setyPlane(float yPlane) {
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

   public float getMOVE_SPEED() {
      return MOVE_SPEED;
   }

   public float getROTATION_SPEED() {
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
