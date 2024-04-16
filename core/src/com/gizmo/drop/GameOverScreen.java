package com.gizmo.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;
    Texture backgroundTexture;
    ImageButton restartButton;
    int dropsGathered;
    Sound gameOverSound;

    public GameOverScreen(final Drop game, int dropsGathered) {
        this.game = game;
        this.dropsGathered = dropsGathered;

        // Configurar la cámara y el viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        // Inicializar el SpriteBatch y el BitmapFont
        batch = new SpriteBatch();
        font = new BitmapFont();

        // Inicializar el escenario (Stage)
        stage = new Stage(viewport, batch);

        // Configurar el fondo
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover.wav"));

        // Configurar el botón de reinicio
        Texture buttonTexture = new Texture(Gdx.files.internal("restartbutton.png"));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        restartButton = new ImageButton(buttonDrawable);
        restartButton.setSize(100, 50); // Ajusta los valores según el tamaño deseado
        restartButton.setPosition(350, 150);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambiar a la pantalla principal cuando se haga clic en el botón de reinicio
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        // Agregar actores al escenario
        stage.addActor(restartButton);

        // Configurar el manejador de entrada para el escenario
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        gameOverSound.play();
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar el fondo
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 800, 480);
        font.setColor(new Color(Color.BLACK));
        font.draw(batch, "Drops Collected: " + dropsGathered, 300, 400);
        batch.end();

        // Dibujar el escenario (Stage)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Liberar recursos
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
        stage.dispose();
        gameOverSound.stop();
        gameOverSound.dispose();
    }
}
