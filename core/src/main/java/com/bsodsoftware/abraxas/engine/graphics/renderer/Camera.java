package com.bsodsoftware.abraxas.engine.graphics.renderer;

import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.events.CollisionEngine;
import com.bsodsoftware.abraxas.engine.entities.Player;
import com.bsodsoftware.abraxas.engine.events.CollisionNotification;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycasterType;
import com.bsodsoftware.abraxas.engine.world.Door;

import java.util.List;

public class Camera {

   public enum Direction {
      NORTH,
      EAST,
      SOUTH,
      WEST
   }

   private Direction direction = Direction.NORTH;

   private boolean moving = false;

   private float targetX;
   private float targetY;

   private static final float MOVE_ANIMATION_SPEED = 0.15f;

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
   private boolean rotating = false;
   private float currentAngle = 0f;
   private float targetAngle = 0f;
   private static final float ROTATION_ANIMATION_SPEED = 8f;
   private final float MOVE_SPEED = 0.07f;
   private final float ROTATION_SPEED = 0.06f;
   private Player player;
   private boolean vieneDePausa;
   private CollisionEngine collisionEngine;
   private CollisionNotification collisionNotification;

   public Camera(float x, float y, float xd, float yd, float xp, float yp, Player player, CollisionEngine collisionEngine, CollisionNotification collisionNotification) {
      this.xPos = x;
      this.yPos = y;
      this.xDir = xd;
      this.yDir = yd;
      this.xPlane = xp;
      this.yPlane = yp;
      this.player = player;
      this.currentAngle = 0f;
      this.targetAngle = 0f;
      this.collisionEngine = collisionEngine;
      this.collisionNotification = collisionNotification;
   }

   public void update() {
      updateGridMovement();
      updateRotation();
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


   public void keyPressed(int key, int[][] map, Door[][] doors, List<SpriteRaycaster> sprites) {
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
            turnLeft();
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.RIGHT_ARROW.getValue() || key == KeyInputEnum.E.getValue()) {
            turnRight();
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.UP_ARROW.getValue() || key == KeyInputEnum.W.getValue()) {
            attemptMoveForward(map, doors, sprites);
            this.player.setState(Player.STATE.WALKING);
         }

         if (key == KeyInputEnum.DOWN_ARROW.getValue() || key == KeyInputEnum.S.getValue()) {
            attemptMoveBackward(map, doors, sprites);
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


   }

   /* Grid Movement */

   private void updateGridMovement() {
      if (!moving) {
         return;
      }

      float dx = targetX - xPos;
      float dy = targetY - yPos;
      float dist = (float)Math.sqrt(dx * dx + dy * dy);

      if (dist < 0.05f) {
         xPos = targetX;
         yPos = targetY;
         moving = false;
      } else {
         xPos += dx * MOVE_ANIMATION_SPEED;
         yPos += dy * MOVE_ANIMATION_SPEED;
      }
   }

   private void attemptMoveForward(int[][] map, Door[][] doors, List<SpriteRaycaster> sprites) {
      if (moving) {
         return;
      }

      int currentX = (int)xPos;
      int currentY = (int)yPos;
      int nextX = currentX;
      int nextY = currentY;

      switch (direction) {
         case NORTH:
            nextY--;
            break;
         case SOUTH:
            nextY++;
            break;
         case EAST:
            nextX++;
            break;
         case WEST:
            nextX--;
            break;
      }

      if (!isWalkable(nextX, nextY, doors, map)) {
         return;
      }

      SpriteRaycaster sprite = collisionEngine.getCollidingSprite(nextX + 0.5, nextY + 0.5, sprites, player.getRadius());

      if (sprite != null) {
         if (sprite.getSpriteType() == SpriteRaycasterType.MONSTER) {
            if (player.getState() != Player.STATE.IN_COMBAT) {
               player.setState(Player.STATE.IN_COMBAT);
               collisionNotification.collisionWithMonster(sprite.getId());
            }
         }

         return;
      }

      targetX = nextX + 0.5f;
      targetY = nextY + 0.5f;

      moving = true;
   }

   private void attemptMoveBackward(int[][] map, Door[][] doors, List<SpriteRaycaster> sprites) {
      //if (moving) return;

      int currentX = (int)xPos;
      int currentY = (int)yPos;
      int nextX = currentX;
      int nextY = currentY;

      switch (direction) {
         case NORTH:
            nextY++;
            break;
         case SOUTH:
            nextY--;
            break;
         case EAST:
            nextX--;
            break;
         case WEST:
            nextX++;
            break;
      }

      if (!isWalkable(nextX, nextY, doors, map)) return;

      targetX = nextX + 0.5f;
      targetY = nextY + 0.5f;

      moving = true;
   }

   private void turnLeft() {
      if (rotating || moving)
         return;

      targetAngle -= 90f;
      rotating = true;
   }

   private void turnRight() {
      if (rotating || moving)
         return;

      targetAngle += 90f;
      rotating = true;
   }

   private void updateRotation() {
      if (!rotating)
         return;

      float diff = targetAngle - currentAngle;

      if (Math.abs(diff) <= ROTATION_ANIMATION_SPEED) {
         currentAngle = targetAngle;
         rotating = false;
         updateDirectionEnum();
      } else {
         currentAngle += Math.signum(diff) * ROTATION_ANIMATION_SPEED;
      }

      updateDirectionFromAngle();
   }

   private void updateDirectionFromAngle() {
      double radians = Math.toRadians(currentAngle);

      xDir = (float)Math.sin(radians);
      yDir = (float)-Math.cos(radians);

      xPlane = -yDir * 0.66f;
      yPlane = xDir * 0.66f;
   }

   private void updateDirectionEnum() {
      int angle = ((int)Math.round(currentAngle) % 360 + 360) % 360;
      switch(angle) {
         case 0:
            direction = Direction.NORTH;
            break;
         case 90:
            direction = Direction.EAST;
            break;
         case 180:
            direction = Direction.SOUTH;
            break;
         case 270:
            direction = Direction.WEST;
            break;
      }
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
