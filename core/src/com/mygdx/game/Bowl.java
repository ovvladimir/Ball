package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bowl {
    private TextureRegion texture;
    float speed;
    float time;
    private final int WIDTH = 122;
    private final int HEIGTH = 76;
    Vector2 position;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }
    public Vector2 getPosition() {
        return position;
    }

    public Bowl(TextureRegion texture) {
        this.texture = texture;
        position = new Vector2(650, 50);
        speed = 20.0f;
        this.rectangle = new Rectangle(position.x + WIDTH / 4, position.y + HEIGTH / 4, WIDTH / 2, HEIGTH / 3);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 61, 38, WIDTH, HEIGTH, 1, 1, 0);
    }

    public void update(float dt) {
        time += dt;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed;
        }

        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > 1160) {
            position.x = 1160;
        }
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y > 300) {
            position.y = 300;
        }
        rectangle.setPosition(position.x + WIDTH / 4, position.y + HEIGTH / 4);
    }
}
