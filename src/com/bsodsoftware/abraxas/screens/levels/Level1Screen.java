package com.bsodsoftware.abraxas.screens.levels;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1Screen extends GameState {
    private ArrayList<String> script = new ArrayList<>();
    private int currentPos = 0;
    private int textX = 30;
    private int textY = 490;
    private GameStateManager gameStateManager;
    private Font fontMessage;
    private Font fontCharName;
    private Sprite sprite;
    private Sprite textframe;
    private Sprite character;
    private boolean isQuestion = false;

    public Level1Screen(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            sprite = new Sprite("/Background/backschool.png", 1);
            //sprite.setVector(-0.1, 0);
            textframe = new Sprite("/textframe.png", 1);
            //textframe.setVector(-0.1, 0);
            textframe.setPosition(100,300);
            character = new Sprite("/Sprites/monokuma.png", 1);
            character.setPosition(250, 114);

            fontCharName = new Font("Arial", Font.BOLD, 28);
            fontMessage = new Font("Arial", Font.PLAIN, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cargarScript();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        sprite.update();
    }

    @Override
    public void draw(Graphics2D graphics) {
        sprite.draw(graphics);
        textframe.draw(graphics);
        character.draw(graphics);

        if (currentPos <= script.size()) {
            String line = script.get(currentPos);
            String[] content = line.split("\\|");
            if (content.length <= 2) {
                graphics.setFont(fontCharName);
                setCharacterName(graphics, content[0]);
                graphics.setColor(Color.WHITE);
                graphics.setFont(fontMessage);
                drawString(graphics, content[1], textX, textY);
            } else {
                drawQuestion(graphics, content, textX, textY);
            }
        }
    }

    private void setCharacterName(Graphics2D graphics2D, String name) {
        drawString(graphics2D, name, 40, 450);
    }

    private void drawString(Graphics2D graphics, String text, int x, int y) {
        String[] line = text.split("\n");
        for (int i = 0; i < line.length; i++) {
            if (i > 0) {
                y += graphics.getFontMetrics().getHeight();
            }
            graphics.drawString(line[i], x, y);
        }
    }

    private void drawQuestion(Graphics2D graphics, String[] content, int textX, int textY) {
        isQuestion = true;
        String question = content[1];
        String option1 = content[2];
        String option2 = content[4];
        int answer1 = Integer.parseInt(content[3]);
        int answer2 = Integer.parseInt(content[5]);
        drawString(graphics, question, textX, textY);
        
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == KeyEvent.VK_ENTER) {
            if (!isQuestion) {
                currentPos++;
            }
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    private void cargarScript() {
        /**
         * Explicación del sistema de script corta:
         * El engine entiende los textos según el index de frases separadas por pipe (|)
         *
         * Posiciones actuales:
         * 0: Nombre de personaje
         * 1: Mensaje
         * 2: Opcion 1 pregunta
         * 3: Valor opcion 1 pregunta
         * 4: Opcion 2 pregunta
         * 5: Valor opcion 2 pregunta
         *
         * La idea es que si escoges la opcion 1 te mande al index del script. Si escoges el valor 20, te lleva al mensaje 20, etc.
         */
        // TODO cargar script y preguntas desde archivo por separado
        script.add("Monokuma|UPUPUPU... y wea");
        script.add("Monokuma|Este es el dating sim más raro en el que he estado.");
        script.add("Monokuma|Me pregunto que pasará si es que sobrepaso el límite de caracteres \n disponibles en pantalla, onda, esta frase va a pasar de largo o va a wrapear pa abajo?");
        script.add("Monokuma|UPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPU\nPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUP\nUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPUUPUPUPUPU");
        script.add("Monokuma?|Upupupu?|Upupu?|5|UPUPU!?|6");
        script.add("Monokuma|Escogiste opcion 1");
        script.add("Monokuma|Escogiste opcion 2");
    }
}
