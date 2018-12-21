package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball {
    Vector2 position;
    private TextureRegion texture;
    float speed;
    float n;
    float time;
    private float angle; // вращение
    private final int WIDTH = 66;
    private final int HEIGTH = 66;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Ball(TextureRegion texture) {
        this.texture = texture;
        position = new Vector2(650, 720);
        speed = 15.0f;
        angle = 0.0f;
        n = 10.0f;
        this.rectangle = new Rectangle(position.x, position.y, WIDTH, HEIGTH);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 33, 33, WIDTH, HEIGTH, 1, 1, angle);
    }

    public void update(float dt) {
        time += dt;
        angle += 5;
        position.y -= speed;
        rectangle.setPosition(position.x, position.y);
    }
    public void recreate() {
        position.y = 720;
        position.x = MathUtils.random(0, 1200);
    }
}
