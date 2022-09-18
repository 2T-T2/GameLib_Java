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

import javax.imageio.ImageIO;

import t_panda.game.event.ChangeSceneEvent;
import t_panda.game.event.ChangeSceneListener;

/**
 * ISceneを実装したクラス
 * @see IScene
 */
abstract public class Scene<SCENE extends Enum<SCENE>, OBJ_TAG extends Enum<OBJ_TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> implements IScene<SCENE, OBJ_TAG, VKEYPAD> {
    /** このゲーム画面に描画するこのシーンの画面画像 */
    private transient BufferedImage scene_image;
    /** このシーンの親であるIGame継承オブジェクト */
    private transient IGame<SCENE, OBJ_TAG, VKEYPAD> parent;
    private transient boolean desirializeAndNeverCallUpdateFlg = false;

    /** このシーンに属するIGObject継承オブジェクト */
    private ArrayList<IGObject<SCENE, OBJ_TAG, VKEYPAD>> children;
    /** 1フレームで追加されるべきIGObject継承オブジェクト */
    private ArrayList<IGObject<SCENE, OBJ_TAG, VKEYPAD>> addObjs;
    /** シーン識別Enumオブジェクト
     */
    private final SCENE sceneName;
    /**
     * このシーンに切り替わってからのフレーム数
     * @see #getFrameCount()
     */
    private long frameCount;
    /**  */ private int drawCenterX;
    /**  */ private int drawCenterY;

    /**
     * コンストラクタ
     * @param scene 自身に対応したシーン識別Enum
     */
    public Scene(SCENE scene) {
        this.sceneName = scene;
    }
    /**
     * 自身がデシリアライズによってインスタンスが生成されているかつ、{@link #update(IKeyInput, MouseInput)}が呼び出されていない状態で有るかどうかを判定します。
     * @return 自身がデシリアライズによってインスタンスが生成されているかつ、{@link #update(IKeyInput, MouseInput)}が呼び出されていない状態で有るかどうか
     */
    protected boolean isDeserializeAndNeverCallUpdate() {
        return desirializeAndNeverCallUpdateFlg;
    }
    /**
     * シーン画像をゲーム画面に描画します
     * @param g ゲーム画面に描画するためのGraphics
     */
    protected void drawToScreen(Graphics g) {
        // g.drawImage(getSceneImage(), 0, 0, this);
        g.drawImage(getSceneImage(), getDrawLeft(), getDrawTop(), this);
    }
    /**
     * 自身のシーンになってからのフレームのカウント数を更新します。
     * {@link #update(IKeyInput, MouseInput)}で呼び出されます。
     */
    protected void frameCountUp() {
        frameCount++;
    }
    /**
     * 自身のシーンになってからのフレームのカウント数をリセットします。
     */
    protected void resetFrameCount() {
        frameCount = 0;
    }
    /**
     * ゲームの解像度横幅を取得します。
     * @return ゲームの解像度横幅
     */
    protected int getGameWidth() {
        return parent.getGameWidth();
    }
    /**
     * ゲームの解像度縦幅を取得します。
     * @return ゲームの解像度縦幅
     */
    protected int getGameHeight() {
        return parent.getGameHeight();
    }
    /**
     * シーンが自身から切り替わった時に呼び出されます。このクラスで実装しているChangeSceneListenerのonChangeSceneで呼び出すか判定を行っています。シーンが切り替わって{@link #init(t_panda.game.IScene.InitArg)}の実装が呼び出され、初期化をされる項目を無効化し、他のシーンで無駄なリソースを消費しない為の処理がされています。
     * @see ChangeSceneListener#onChangeScene(ChangeSceneEvent)
     * @see #disableChildrenList()
     * @see #disableSceneImage()
     * @see #disableMetaData()
     * @see #initChildrenList()
     * @see #initSceneImage(int, int)
     * @see #initMetaData(t_panda.game.IScene.InitArg)
     */
    protected void leave() {
        disableSceneImage();
        disableChildrenList();
        disableMetaData();
    }
    /**
     * ゲーム画面に表示する画像を取得します。
     * @return ゲーム画面に表示する画像
     */
    protected BufferedImage getSceneImage() {
        return scene_image;
    }

    // ================ abstract Method ================
    /**
     * ゲーム画面に表示する画像に描画処理を行います
     * @param g ゲーム画面に表示する画像に描画するためのGraphics
     */
    abstract protected void drawToSceneImage(Graphics g);

    // ================ IScene Method ================
    /**
     * {@inheritDoc}。シリアライズ化を行う場合には、デフォルトの動作では、属していたIGObjectが空になります。それを意図しない場合は、{@link #initMetaData(t_panda.game.IScene.InitArg)}と、{@link #initSceneImage(int, int)}を呼び出すようにしてください。
     * <pre> デシリアライズ時に考えられるサンプルコード
     * {@code
     * @Override public void init(InitArg<?,?,?> initArg) {
     *   if(isDeserializeAndNeverCallUpdate()) {
     *     initMetaData(initArg);
     *     initSceneImage(getGameWidth(), getGameHeight());
     *     // initChildrenList();     これを呼び出すと、シリアライズ時に属していたIGObjectが無くなる。
     *     |
     *     | デシリアライズされたときの初期化処理。
     *     |
     *     return;
     *   }
     *   super.init(initArg);
     *   |
     *   | 通常の初期化処理
     *   |
     * }
     * }
     * </pre>
     */
    @Override
    public void init(InitArg<SCENE, OBJ_TAG, VKEYPAD> initArg) {
        initMetaData(initArg);
        initSceneImage(getGameWidth(), getGameHeight());
        initChildrenList();
        resetFrameCount();
    }
    @Override
    public void draw(Graphics g) {
        Graphics myGra = getGraphics();
        // clear(myGra);
        drawToSceneImage(myGra);
        drawChildObj(myGra);
        myGra.dispose();
        drawToScreen(g);
    }
    @Override
    public void update(IKeyInput<VKEYPAD> keyInput, MouseInput mouseInput) {
        updateChildObj(children, keyInput, mouseInput);
        frameCountUp();
        this.desirializeAndNeverCallUpdateFlg = false;
    }

    @Override
    public IGObject<SCENE, OBJ_TAG, VKEYPAD> getChildByIndex(int index) {
        return this.children.get(index);
    }
    @Override
    public int getChildrenCount() {
        return this.children.size();
    }
    @Override
    public SCENE getSceneName() {
        return this.sceneName;
    }
    @Override
    public int getDrawWidth() {
        return this.scene_image.getWidth();
    }
    @Override
    public int getDrawHeight() {
        return this.scene_image.getHeight();
    }
    @Override
    public long getFrameCount() {
        return this.frameCount;
    }
    @Override
    public Graphics getGraphics() {
        return scene_image.createGraphics();
    }
    @Override
    public Graphics2D createGraphics() {
        return scene_image.createGraphics();
    }
    @Override
    public int getDrawCenterX() {
        return drawCenterX;
    }
    @Override
    public int getDrawCenterY() {
        return drawCenterY;
    }
    @Override
    public void setDrawCenterX(int x) {
        this.drawCenterX = x;
    }
    @Override
    public void setDrawCenterY(int y) {
        this.drawCenterY = y;
    }
    @Override
    public void addObj(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj) {
        addObjs.add(obj);
        obj.init(IGObject.createInitArg(this, this));
    }
    @Override
    public void addObjToHost(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj) {
        addObj(obj);
    }
    @Override
    public void destroyObject(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj) {
        obj.destroy();
    }
    @Override
    public void destroyObjectFromHost(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj) {
        destroyObject(obj);
    }
    // ================= IGOjectHoser =================
    @Override
    public void addReserveObj() {
        this.children.addAll(this.addObjs);
        addObjs.clear();
    }
    // ================= ISceneChanger =================
    @Override
    public void changeScene(SCENE scene, Object initData) {
        parent.changeScene(scene, initData);
    }
    // ================= ImageObserver =~=============
    @Override public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) { return parent.imageUpdate(arg0, arg1, arg2, arg3, arg4, arg5); }

    // ================= Event 関連 =================
    @Override
    public void onChangeScene(ChangeSceneEvent<SCENE> e) {
        if(e.getBeforeSceneName() == getSceneName())
            leave();
    }

    // ================= Final Method =================
    /**
     * 自身に属すIObjectを管理するListを初期化します。通常{@link #init(t_panda.game.IScene.InitArg)}で呼び出します。
     */
    final protected void initChildrenList() {
        if(children == null) children = new ArrayList<>();
        children.clear();
        if(addObjs == null) addObjs = new ArrayList<>();
        addObjs.clear();
    }
    /**
     * ゲーム画面に描画する画像を初期化します。通常{@link #init(t_panda.game.IScene.InitArg)}で呼び出します。
     * @param w ゲーム画面に描画する画像の横幅
     * @param h ゲーム画面に描画する画像の縦幅
     */
    final protected void initSceneImage(int w, int h) {
        if(scene_image  != null) {scene_image.flush(); scene_image = null;}
        this.scene_image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        this.drawCenterX = getGameWidth()>>1;
        this.drawCenterY = (h-scene_image.getHeight()) + (getGameHeight()>>1);
    }
    /**
     * 内部処理を正常に行うための初期化処理。通常{@link #init(t_panda.game.IScene.InitArg)}で呼び出します。
     * @param initArg 初期化初期を行うための引数オブジェクト
     */
    final protected void initMetaData(InitArg<SCENE, OBJ_TAG, VKEYPAD> initArg) {
        parent = initArg.getHost();
    }
    /**
     * ゲーム画面に描画する画像を無効化します。通常{@link #leave()}で呼び出します。
     */
    final protected void disableSceneImage() {
        if(scene_image == null) return;
        scene_image.flush();
        scene_image = null;
    }
    /**
     * 自身に属すIObjectを管理するListを無効化します。通常{@link #leave()}で呼び出します。
     */
    final protected void disableChildrenList() {
        if(children == null) return;
        children.forEach(e -> { e.destroy(); });
        children.clear();
        children = null;
        if(addObjs == null) return;
        addObjs.clear();
        addObjs = null;
    }
    /**
     * 内部処理を正常に行うためのオブジェクトを無効化します。通常{@link #leave()}で呼び出します。
     */
    final protected void disableMetaData() {
        parent = null;
    }

    /**
     * シリアライズされる時に呼び出される関数。
     * @param out オブジェクトアウトプットストリーム
     * @throws IOException 入出力例外
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        try (var baos = new java.io.ByteArrayOutputStream()) {
            if(scene_image == null) { out.writeInt(0); return; }
            ImageIO.write(this.scene_image, "PNG", baos);
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
        int len = in.readInt();
        if(len == 0) return;
        try(var bais = new ByteArrayInputStream(in.readNBytes(len))) {
            this.scene_image = ImageIO.read(bais);
        }
        this.desirializeAndNeverCallUpdateFlg = true;
    }
}
