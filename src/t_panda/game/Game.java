package t_panda.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;

import t_panda.game.event.ChangeSceneEvent;
import t_panda.game.event.ChangeSceneListener;
import t_panda.game.event.GameDrawEvent;
import t_panda.game.event.GameDrawListener;

/**
 * IGameを実装したクラス。
 * デシリアライズによって復元されたときには、自動で追加される以外の、ChangeEventListener、GameDrawListenerは、未登録状態になります。
 * @see IGame
 */
public class Game<SCENE extends Enum<SCENE>, OBJ_TAG extends Enum<OBJ_TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> implements IGame<SCENE, OBJ_TAG, VKEYPAD> {
    /**ゲームの画面画像 */
    private transient BufferedImage gameImage;
    /**ゲームのシーンが変更されたときに発生するイベントのリスナを保持している可変長配列 */
    private transient ArrayList<ChangeSceneListener<SCENE>> changeSceneListeners;
    /**ゲームが描画されたタイミングで発生するイベントのリスナを保持している可変長配列。デシリアライされたときには復元されません。 */
    private transient ArrayList<GameDrawListener> gameDrawListeners;
    /**次のフレームで変更先となるシーンを識別するEnuおオブジェクト */
    private transient SCENE nextScene;
    /**次のシーンを初期化するデータ */
    private transient Object nextSceneInitData;

    /**ゲーム解像度縦幅 */
    private final int gameWidth;
    /**ゲーム解像度横幅 */
    private final int gameHeight;
    /**シーン配列 */
    private final ArrayList<IScene<SCENE, OBJ_TAG, VKEYPAD>> scenes;

    /**マウス入力保持オブジェクト */
    private final MouseInput mouseInput;
    /**キー入力保持オブジェクト */
    private final KeyInput<VKEYPAD> keyInput;

    /** ゲームのFPS */
    private int fps;
    /** ゲーム開始時からのフレーム数 */
    private long frameCount;
    /** 現在のシーンを表す識別Enumオブジェクト */
    private SCENE currentScene;

    /**ゲームのスレッドを終了するかどうかのフラグ */
    private boolean gameThreadEndFlg;
    /**ゲームのスレッドを一時停止するかどうかのフラグ */
    private boolean gameThreadPouseFlg;
    /** 実際のマウス入力座標をゲームの解像度にあわせたゲーム座標にスケーリングする*/
    private float mouseInputScale;

    private Game(Game.Builder<SCENE, OBJ_TAG, VKEYPAD> builder) {
        this.gameWidth = builder.width;
        this.gameHeight = builder.height;
        this.scenes = builder.scenes;
        this.changeSceneListeners = builder.changeSceneListeners;
        this.mouseInput = new MouseInput();
        this.keyInput = new KeyInput<>();
        this.gameImage = new BufferedImage(builder.width, builder.height, BufferedImage.TYPE_INT_ARGB);
        this.gameThreadEndFlg = false;
        this.gameThreadPouseFlg = false;

        if(builder.isSetFps)
            this.fps = builder.fps;
        else
            this.fps = 30;

        this.frameCount = 0;
        this.currentScene = builder.firstScene;
        this.nextSceneInitData = builder.initArgData;
        this.gameDrawListeners = new ArrayList<>();
    }
    public void requestGameThreadStop() {
        this.gameThreadEndFlg = true;
    }
    public void requestSetGameThreadPouse(boolean b) {
        this.gameThreadPouseFlg = b;
    }
    @Override
    public BufferedImage getImage() {
        return this.gameImage;
    }
    @Override
    public void addGameDrawListener(GameDrawListener listener) {
        this.gameDrawListeners.add(listener);
    }

    @Override
    public void setMouseClickX(int button, int x) {
        mouseInput.setClickX(button, x);
    }
    @Override
    public void setMouseClickY(int button, int y) {
        mouseInput.setClickY(button, y);
    }
    @Override
    public void setMouseReleaseX(int button, int x) {
        mouseInput.setReleaseX(button, x);
    }
    @Override
    public void setMouseReleaseY(int button, int y) {
        mouseInput.setReleaseY(button, y);
    }
    @Override
    public void setMouseCurrentX(int x) {
        mouseInput.setCurrentX(x);
    }
    @Override
    public void setMouseCurrentY(int y) {
        mouseInput.setCurrentY(y);
    }
    @Override
    public void pressKey(int keyCode) {
        keyInput.pressKey(keyCode);
    }
    @Override
    public void releaseKey(int keyCode) {
        keyInput.releaseKey(keyCode);
    }

    private void resetMouseInput() {
        for (MouseButtons button : MouseButtons.values()) {
            mouseInput.setClickX(button.getButtonId(), MouseInput.NOT_CLICK);
            mouseInput.setClickY(button.getButtonId(), MouseInput.NOT_CLICK);
            mouseInput.setReleaseX(button.getButtonId(), MouseInput.NOT_RELEASE);
            mouseInput.setReleaseY(button.getButtonId(), MouseInput.NOT_RELEASE);
        }
    }

    @Override
    public void update(IKeyInput<VKEYPAD> keyInput, MouseInput mouseInput) {
        mouseInput.setScale(mouseInputScale);
        getCurrentScene().update(keyInput, mouseInput);
        resetMouseInput();
        changeSceneIfNeeded();
        frameCount++;
    }
    @Override
    public void setMouseInputScale(float f) {
        this.mouseInputScale = f;
    }

    private void changeSceneIfNeeded() {
        if(nextScene == null) return;
        SCENE before = this.currentScene;
        this.currentScene = nextScene;
        getCurrentScene().init(IScene.createInitArg(this, nextSceneInitData));
        for (var changeSceneListener : changeSceneListeners) {
            changeSceneListener.onChangeScene(new ChangeSceneEvent<SCENE>(before, nextScene));
        }
        nextScene = null;
        nextSceneInitData = null;
    }

    @Override
    public void changeScene(SCENE scene, Object initData) {
        nextScene = scene;
        nextSceneInitData = initData;
    }

    @Override
    public void draw(Graphics g) {
        g.clearRect(0, 0, getGameWidth(), getGameHeight());
        getCurrentScene().draw(g);
        for (var listener : gameDrawListeners) {
            listener.gameDraw(new GameDrawEvent(this));
        }
    }

    @Override
    public void run() {
        getCurrentScene().init(IScene.createInitArg(this, nextSceneInitData));
        java.awt.Graphics2D g = gameImage.createGraphics();
        g.setBackground(IGame.TRANSEPARENT);
        while(true) {
            try {
                Thread.sleep(1000/getFps());
                if(gameThreadPouseFlg) continue;
                if(getCurrentScene() == null || gameThreadEndFlg) break;
                update(keyInput, mouseInput);
                draw(g);
                keyInput.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        g.dispose();
    }

    @Override
    public boolean imageUpdate(Image img , int flg , int x , int y , int w , int h) {
        return (flg & ALLBITS) == 0;
    }

    @Override
    public IScene<SCENE, OBJ_TAG, VKEYPAD> getCurrentScene() {
        return this.scenes.get(this.currentScene.ordinal());
    }

    @Override
    public int getFps() {
        return this.fps;
    }

    @Override
    public int getGameWidth() {
        return this.gameWidth;
    }

    @Override
    public int getGameHeight() {
        return this.gameHeight;
    }

    @Override
    public long getFrameCount() {
        return this.frameCount;
    }

    @Override
    public void setFps(int fps) {
        this.fps = fps;
    }

    @Override
    public void addChangeSceneListener(ChangeSceneListener<SCENE> listener) {
        changeSceneListeners.add(listener);
    }

    @Override
    public void removeChangeSceneListener(ChangeSceneListener<SCENE> listener) {
        changeSceneListeners.remove(listener);
    }    
    @Override
    public int getDrawCenterX() {
        return getGameWidth()>>1;
    }
    @Override
    public int getDrawCenterY() {
        return getGameHeight()>>1;
    }
    @Override
    public void setDrawCenterX(int x) {
        throw new UnsupportedOperationException("ゲームのサイズは不変です");
    }
    @Override
    public void setDrawCenterY(int y) {
        throw new UnsupportedOperationException("ゲームのサイズは不変です");
    }
    @Override
    public int getDrawWidth() {
        return getGameWidth();
    }
    @Override
    public int getDrawHeight() {
        return getGameHeight();
    }
    @Override
    public Graphics getGraphics() {
        return this.gameImage.createGraphics();
    }
    @Override
    public Graphics2D createGraphics() {
        return this.createGraphics();
    }
    /**
     * シリアライズされる時に呼び出される関数。
     * @param out オブジェクトアウトプットストリーム
     * @throws IOException 入出力例外
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        try (var baos = new java.io.ByteArrayOutputStream()) {
            ImageIO.write(this.gameImage, "PNG", baos);
            var bytes = baos.toByteArray();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
    }
    /**
     * デシリアライズされる時に呼び出される関数。
     * @param in オブジェクトインプットストリーム
     * @throws IOException 入出力例外
     * @throws ClassNotFoundException デシリアライズの結果適切なクラスが見つからなかった場合に発生
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try(var bais = new ByteArrayInputStream(in.readNBytes(in.readInt()))) {
            this.gameImage = ImageIO.read(bais);
        }
        changeSceneListeners = new ArrayList<>();
        changeSceneListeners.addAll(this.scenes);
        gameDrawListeners = new ArrayList<>();
    }
    /**
     * Gameオブジェクトを生成するビルダークラス。
     */
    public static class Builder <SCENE extends Enum<SCENE>, OBJ_TAG extends Enum<OBJ_TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> implements IGame.IBuilder<SCENE, OBJ_TAG, VKEYPAD> {
        private final ArrayList<ChangeSceneListener<SCENE>> changeSceneListeners;
        private final ArrayList<IScene<SCENE, OBJ_TAG, VKEYPAD>> scenes;
        private final int height;
        private final int width;

        private final SCENE firstScene;
        private int fps;
        private boolean isSetFps;
        private Object initArgData;

        /**
         * コンストラクタ
         * @param width 横幅
         * @param height 縦幅
         * @param firstScene 最初のシーン
         */
        public Builder(int width, int height, SCENE firstScene) {
            this.width = width;
            this.height = height;
            this.firstScene = firstScene;
            this.scenes = new ArrayList<>();
            this.changeSceneListeners = new ArrayList<>();
            this.initArgData = null;
        }
        public IGame<SCENE, OBJ_TAG, VKEYPAD> build() {
            Collections.sort(this.scenes, new Comparator<IScene<SCENE, OBJ_TAG, VKEYPAD>>() {
                public int compare(IScene<SCENE, OBJ_TAG, VKEYPAD> arg0, IScene<SCENE, OBJ_TAG, VKEYPAD> arg1) {
                    return arg0.getSceneName().ordinal() - arg1.getSceneName().ordinal();
                }                
            });
            return new Game<>(this);
        }
        /**
         * ゲームのFPS(frame per sce)を設定します。
         * @param fps ゲームのFPS
         * @return 設定後の自身
         */
        public Builder<SCENE, OBJ_TAG, VKEYPAD> fps(int fps) {
            this.isSetFps = true;
            this.fps = fps;
            return this;
        }
        /**
         * ゲームのシーンを追加します。
         * @param scene ゲームのシーン
         * @return 設定後の自身
         */
        public Builder<SCENE, OBJ_TAG, VKEYPAD> addScene(IScene<SCENE, OBJ_TAG, VKEYPAD> scene) {
            this.scenes.add(scene);
            this.changeSceneListeners.add(scene);
            return this;
        }
        /**
         * 最初のシーンを初期化する時に使用するデータを指定します
         * @param data 最初のシーンを初期化する時に使用するデータ
         * @return 設定後の自身
         */
        public Builder<SCENE, OBJ_TAG, VKEYPAD> initData(Object data) {
            this.initArgData = data;
            return this;
        }
    }
}
