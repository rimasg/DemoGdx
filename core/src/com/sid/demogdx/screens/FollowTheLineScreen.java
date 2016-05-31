package com.sid.demogdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sid.demogdx.DemoGdx;
import com.sid.demogdx.entities.LineSegment;
import com.sid.demogdx.utils.AppConfig;

/**
 * Created by Okis on 2016.05.08 @ 13:46.
 */
public class FollowTheLineScreen extends AbstractScreen {
    private LineSegment currentLineSegment;
    private Array<LineSegment> lineSegments = new Array<>();
    private Array<Vector2> lines = new Array<>();
    private int worldW, worldH;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public FollowTheLineScreen(DemoGdx game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        worldW = AppConfig.WWP;
        worldH = AppConfig.WHP;
        addLineSegments();

        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                // return super.mouseMoved(screenX, screenY);
                stage.getCamera().unproject(touchPos.set(screenX, screenY, 0));
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // return super.touchDown(screenX, screenY, pointer, button);
                stage.getCamera().unproject(touchPos.set(screenX, screenY, 0));
                return false;
            }
        };

        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void addLineSegments() {
        lines.add(new Vector2(worldW / 2, 0));
        lines.add(new Vector2(worldW / 2 - 100, 250));
        lines.add(new Vector2(worldW / 2 + 100 , 450));
        lines.add(new Vector2(worldW / 2 - 150 , 650));
/*
        for (int i = 0; i < 3; i++) {
            currentLineSegment = new LineSegment(skin.getAtlas().findRegion("line_segment"));
            currentLineSegment.setPos(50f, 50f);
            currentLineSegment.setAngle(45f);
            lineSegments.add(currentLineSegment);
        }
*/
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.setAutoShapeType(true);
//        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin();
        drawLines();
        shapeRenderer.end();

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        font.draw(game.batch, "X: " + touchPos.x + " Y: " + touchPos.y, 5f, 20f);
        // TODO: 2016.05.09 commented temporarily
//        drawLineSegments();
        game.batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.getMainMenuScreen());
        }
    }

    private void drawLineSegments() {
        for (LineSegment lineSegment : lineSegments) {
            lineSegment.draw(game.batch);
        }
    }

    private void drawLines() {
        Gdx.gl.glLineWidth(12f);
        for (int i = 0; i < lines.size - 1; i++) {
            final Vector2 lineStart = lines.get(i);
            final Vector2 lineEnd = lines.get(i + 1);
            if (touchPos.y > lineStart.y && touchPos.y < lineEnd.y) {
                if (checkIfOnPath(lineStart, lineEnd)) {
                    shapeRenderer.setColor(Color.BLUE);
                } else {
                    shapeRenderer.setColor(Color.RED);
                }
            } else {
                shapeRenderer.setColor(Color.BLUE);
            }
            shapeRenderer.line(lineStart, lineEnd);
        }
    }

    private boolean checkIfOnPath(Vector2 start, Vector2 end) {
        final float distancePoint = Intersector.distanceLinePoint(start.x, start.y, end.x, end.y, touchPos.x, touchPos.y);
        return distancePoint <= 30;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        shapeRenderer.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
