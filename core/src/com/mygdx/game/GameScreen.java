package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreen implements Screen {
    private MyGame myGame;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private TextureRegion textureBowl;
    private TextureRegion textureBall;
    private boolean paused;
    private BitmapFont font48;
    private BitmapFont font96;
    private TextureRegion textureFon;
    private Sound playerSound;
    private float score;
    private float score2;
    private boolean gameover;
    private Bowl bowl;
    private Ball ball;
    private float time;
    private Stage stage;
    private Skin skin;

    public GameScreen(MyGame myGame, SpriteBatch batch) {
        this.myGame = myGame;
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("1.pack");
        textureFon = atlas.findRegion("neon");
        textureBowl = atlas.findRegion("bowl");
        textureBall = atlas.findRegion("ball");
        bowl = new Bowl(textureBowl);
        ball = new Ball(textureBall);

        playerSound = Gdx.audio.newSound(Gdx.files.internal("pop.wav"));

        gameover = false;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font48 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
        this.score2 = 30.0f;
        this.score = 0;
        Gdx.input.setInputProcessor(null);
        paused = false;
        createGUI();
    }

    public void createGUI() {
        stage = new Stage(myGame.getViewport(), batch);
        skin = new Skin(atlas);
        Gdx.input.setInputProcessor(stage);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.up = skin.getDrawable("ball");
        textButtonStyle.font = font48;
        skin.add("tbs", textButtonStyle);
        TextButton btnPause = new TextButton("PAUSE", skin, "tbs");
        btnPause.setPosition(1130, 0);
        stage.addActor(btnPause);
        btnPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused = !paused;
            }
        });
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(textureFon, 0, 0, 1280,720);
        bowl.render(batch);
        ball.render(batch);

        font48.draw(batch, "SPEED: " + (int)ball.speed, 0, 702, 1280, 1, false);
        font48.draw(batch, "SCORE: " + (int)score, 22, 702);
        font48.draw(batch, "BALL: " + (int)score2, 1100, 702);
        if (gameover) {
            font96.draw(batch,"GAME OVER", 0, 500, 1280, 1, false);
            font48.setColor(1, 1, 1, 0.5f + 0.5f * (float) Math.sin(time * 5.0f));
            font48.draw(batch,"Tab to RESTART", 0, 380, 1280,1, false);
            font48.setColor(1, 1, 1, 1);
        }

        batch.end();
        stage.draw();
    }
    public void restart() {
        gameover = false;
        ball.n = 10.0f;
        ball.speed = 15.0f;
        score2 = 30.0f;
        score = 0;
    }

    public void update(float dt) {
        stage.act(dt);
        if (!paused) {
            time += dt;
            if (!gameover) {
                bowl.update(dt);
                ball.update(dt);

                    if (ball.getRectangle().overlaps(bowl.getRectangle())) {
                        ball.getPosition().x = bowl.getPosition().x;
                        ball.recreate();
                        score++;
                        playerSound.play();
                        if (score == ball.n) {
                            ball.speed += 2.0f;
                            ball.n +=10.0f;
                        }
                    }
                    if (ball.getPosition().y <= 0) {
                        ball.recreate();
                        score2--;
                        if (score2 == 0) {
                            gameover = true; // игра закончена
                        }
                    }
            } else {
                if (Gdx.input.justTouched()) {
                    restart();
                }
            }
        }
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {
        myGame.getViewport().update(width, height, true);
        myGame.getViewport().apply();
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
        playerSound.dispose();
        font48.dispose();
        font96.dispose();
        atlas.dispose();
        stage.dispose();
        skin.dispose();
    }
}
