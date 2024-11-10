package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Sprite bucketSprite; // Déclarez votre Sprite ici
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        bucketSprite = new Sprite(bucketTexture); // Correction ici
        bucketSprite.setSize(1, 1);  // Ajoutez un point-virgule à la fin
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        touchPos = new Vector2();
        dropSprites = new Array<>();

    }

    @Override
    public void resize(int width, int height) {
        // Redéfinition de la taille de la fenêtre
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;  // Augmentation de la vitesse pour un déplacement plus fluide
        float delta = Gdx.graphics.getDeltaTime();

        // Vérifier si la touche de droite est enfoncée
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            bucketSprite.translateX(speed * delta);


        }

        // Vérifier si la touche de gauche est enfoncée
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }


    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime();

        // Loop through the sprites backwards to prevent out of bounds errors
        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i); // Get the sprite from the list
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            //si la goutte touche le bas du cadre, elle est supprimé
            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            creatDroplet();
        }
    }



    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(bucketTexture, 0, 0, 1, 1);
        bucketSprite.draw(spriteBatch);
        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }
        spriteBatch.end();
    }
private void creatDroplet (){
        //Création des variables localement pour simplifier la demarche
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
//Création du drop des gouttes d'eau
    Sprite dropSprite = new Sprite(dropTexture);
    dropSprite.setSize(dropWidth,dropHeight);
    dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
    dropSprite.setY(worldHeight);
    dropSprites.add(dropSprite);

    }
    @Override
    public void pause() {
        // Cette méthode est appelée quand l'application est mise en pause.
    }

    @Override
    public void resume() {
        // Cette méthode est appelée quand l'application reprend après la pause.
    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();
        bucketTexture.dispose();
        dropTexture.dispose();
        dropSound.dispose();
        spriteBatch.dispose();
    }
}

