package com.badlogic.drop.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drop extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    @Override
    public void create() {
        System.out.println("create() called"); // Vérifie si cette ligne s'affiche
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5); // Ajuste la taille si nécessaire

        font.setUseIntegerPositions(false);
        font.getData().setScale(1f);  // Assure-toi que la police est bien à une taille raisonnable
        this.setScreen(new MainMenuScreen(this));  // Affiche l'écran de menu principal au lancement
    }
}

