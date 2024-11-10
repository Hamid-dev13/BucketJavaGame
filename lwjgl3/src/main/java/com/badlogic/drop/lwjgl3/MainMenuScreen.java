package com.badlogic.drop.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Drop game;

    public MainMenuScreen(final Drop game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Code pour initialiser l'écran (non nécessaire ici, mais à placer si besoin)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        // Affiche le texte du menu principal
        game.font.draw(game.batch, "Welcome to Drop!!", 2, 3);  // Ajuste la position si nécessaire
        game.font.draw(game.batch, "Tap anywhere to begin!", 2, 2.5f);  // Idem ici

        game.batch.end();

        // Si on touche l'écran, on passe à l'écran du jeu
        if (Gdx.input.isTouched()) {
            System.out.println("Touched! Transition to GameScreen");
            game.setScreen(new GameScreen(game));  // Change d'écran
            dispose();  // Libère les ressources de l'écran actuel
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Code pour mettre en pause (si nécessaire)
    }

    @Override
    public void resume() {
        // Code pour reprendre après une pause (si nécessaire)
    }

    @Override
    public void hide() {
        // Code pour cacher l'écran (si nécessaire)
    }

    @Override
    public void dispose() {
        // Libère les ressources de l'écran (si nécessaire)
    }
}

