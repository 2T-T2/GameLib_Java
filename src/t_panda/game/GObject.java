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

/**
 * IGObjectを実装した基幹クラスです。
 */
abstract public class GObject<SCENE extends Enum<SCENE>,TAG extends Enum<TAG>,VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable,MSG,CHILD extends IGObject<SCENE, TAG, VKEYPAD, MSG, ?>>

    implements IGObject<SCENE, TAG, VKEYPAD, MSG, CHILD> {
    /**
     * サイズ変更の際のスケーリング方法
     */
    enum ChangeSizeMode {
        /** 割り当てられた広さを埋めるようにコンテンツのサイズを変更します。 縦横比は維持されません。 */
        FILL,
        /** コンテンツの現在のサイズを維持します。 */
        NONE,
        /** 割り当てられた広さに収まるようにコンテンツのサイズを変更しますが、元の縦横比が維持されます。 */
        UNIFORM,
        /** 割り当てられた広さを埋めるようにコンテンツのサイズを変更しますが、元の縦横比が維持されます。 ソース コンテンツの縦横比が対象の四角形の縦横比と異なる場合は、ソース コンテンツが対象の四角形に収まるように切り取られます。 */
        UNIFORM_TO_FILL
    }
    /**  */ private transient BufferedImage image;

    /**  */ private final IFlag<TAG> tags;
    /**  */ private boolean destroyed;
    /**  */ private int hitHeight;
    /**  */ private int hitWidth;
    /**  */ private int drawCenterX;
    /**  */ private int drawCenterY;
    /**  */ private IGObjectHost<SCENE, TAG, VKEYPAD, MSG, IGObject<SCENE, TAG, VKEYPAD, MSG, ?>> host;
    /**  */ private ArrayList<CHILD> children;
    /**  */ private ArrayList<CHILD> addObjs;
    /**  */ private int drawWidth;
    /**  */ private int drawHeight;
    /**  */ private long frameCount;
    /**  */ private IScene<SCENE, TAG, VKEYPAD, MSG, ?> scene;
    /**  */ private int hitCenterX;
    /**  */ private int hitCenterY;

    /**
     * コンストラクタ
     * @param draw_centerX オブジェクトの描画中央X座標
     * @param draw_centerY オブジェクトの描画中央Y座標
     * @param hit_centerX オブジェクトの当たり判定中央X座標
     * @param hit_centerY オブジェクトの当たり判定中央Y座標
     * @param draw_width オブジェクトの描画横幅
     * @param draw_height オブジェクトの描画縦幅
     * @param hit_width オブジェクトの当たり判定横幅
     * @param hit_height オブジェクトの当たり判定縦幅
     * @param tags オブジェクトの特徴を表すタグ
     */
    public GObject(int draw_centerX, int draw_centerY, int hit_centerX, int hit_centerY, int draw_width, int draw_height, int hit_width, int hit_height, IFlag<TAG> tags) {
        this.tags = tags;
        this.drawCenterX = draw_centerX;
        this.drawCenterY = draw_centerY;
        this.hitCenterX = hit_centerX;
        this.hitCenterY = hit_centerY;
        this.drawWidth = draw_width;
        this.drawHeight = draw_height;
        this.hitWidth = hit_width;
        this.hitHeight = hit_height;
        this.destroyed = false;
    }

    /**
     * 自身のイメージに描画を行う関数です
     * @param g 自身のイメージに描画を行うためのGraphics
     */
    abstract protected void drawToObjImage(Graphics g);

    /**
     * 自身属するシーンの描画矩形範囲横幅を取得します
     * @return 自身属するシーンの描画矩形範囲横幅
     */
    protected int getSceneWidth() {
        return this.scene.getDrawWidth();
    }
    /**
     * 自身属するシーンの描画矩形範囲縦幅を取得します
     * @return 自身属するシーンの描画矩形範囲縦幅
     */
    protected int getSceneHeight() {
        return this.scene.getDrawHeight();
    }
    /**
     * 自身のイメージをシーンのイメージに描画を行う関数です。
     * @param g シーンに描画を行うためのGraphics
     */
    protected void drawToScene(Graphics g) {
        g.drawImage(this.image, getDrawLeft(), getDrawTop(), getDrawWidth(), getDrawHeight(), this);
    }
    @Override
    public void addReserveObj() {
        children.addAll(addObjs);
        addObjs.clear();
    }
    /**
     * 自身の描画サイズを変更します。
     * @param new_width 変更後横幅
     * @param new_height 変更後縦幅
     * @param mode スケーリングの方法
     */
    protected void changeDrawSize(int new_width, int new_height, ChangeSizeMode mode) {
        int old_width = this.drawWidth;
        int old_height = this.drawHeight;
        this.drawWidth = new_width;
        this.drawHeight = new_height;
        var new_image = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
        var g = new_image.createGraphics();
        int x = 0, y = 0, w = 0, h = 0;
        float aspect = (float)new_width / (float)new_height;
        switch (mode) {
            case FILL:
                w = new_width;
                h = new_height;
            break;
            case NONE:
                x = (old_width-new_width)>>1;
                y = (old_height-new_height)>>1;
                w = old_width;
                h = old_height;
            break;
            case UNIFORM:
                h = (int)(Math.min(new_width/aspect, new_height));
                w = (int)(h * aspect); 
                x = (new_width-w)>>1;
                y = (new_height-h)>>1;           
            break;
            case UNIFORM_TO_FILL:
                h = (int)(Math.max(new_width/aspect, new_height));
                w = (int)(h * aspect); 
                x = (new_width-w)>>1;
                y = (new_height-h)>>1;           
            break;
        }
        g.drawImage(this.image, x, y, w, h, this);
        g.dispose();
        this.image.flush();
        this.image = null;
        this.image = new_image;
    }

    @Override
    public void draw(Graphics g) {
        Graphics myGra = getGraphics();
        clear(myGra);
        drawToObjImage(myGra);
        drawChildObj(myGra);
        myGra.dispose();
        drawToScene(g);
    }

    @Override
    public void clear(Graphics g) {
        ((java.awt.Graphics2D)g).setBackground(IGame.TRANSEPARENT);
        IGObject.super.clear(g);
    }

    @Override
    public void update(IKeyInput<VKEYPAD> keyInput, IMouseInput mouseInput) {
        updateChildObj(children, keyInput, mouseInput);
        frameCount++;
    }

    @Override
    public void addObj(CHILD obj) {
        this.addObjs.add(obj);
        obj.init(IGObject.createInitArg(host, scene));
    }
    @Override
    public void destroyObject(CHILD obj) {
        obj.destroy();
    }

    @Override
    public void init(InitArg<SCENE, TAG, VKEYPAD, MSG> initArg) {
        initMetaData(initArg);
        initObjImage();
        initObjArrayList();
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public void removed() {
        disableMetaData();
        disableChildrenList();
        disableObjImage();
    }

    @Override
    public CHILD getChildByIndex(int index) {
        return this.children.get(index);
    }
    @Override
    public int getChildrenCount() {
        return this.children.size();
    }

    @Override
    public IFlag<TAG> getTags() {
        return tags;
    }

    @Override
    public Graphics getGraphics() {
        return this.image.createGraphics();
    }
    @Override
    public Graphics2D createGraphics() {
        return this.image.createGraphics();
    }

    @Override
    public int getDrawWidth() {
        return drawWidth;
    }

    @Override
    public int getDrawHeight() {
        return drawHeight;
    }

    @Override
    public int getHitWidth() {
        return this.hitWidth;
    }

    @Override
    public int getHitHeight() {
        return this.hitHeight;
    }

    @Override
    public int getHitCenterX() {
        return this.hitCenterX;
    }
    @Override
    public int getHitCenterY() {
        return this.hitCenterY;
    }

    @Override
    public int getDrawCenterX() {
        return this.drawCenterX;
    }
    @Override
    public int getDrawCenterY() {
        return this.drawCenterY;
    }

    @Override
    public long getFrameCount() {
        return frameCount;
    }

    @Override
    public void setDrawCenterX(int x) {
        this.drawCenterX = x;
    }
    @Override
    public void setHitCenterX(int x) {
        this.hitCenterX = x;
    }

    @Override
    public void setDrawCenterY(int y) {
        this.drawCenterY = y;
    }
    @Override
    public void setHitCenterY(int y) {
        this.hitCenterY = y;
    }

    @Override
    public void changeScene(SCENE scene, Object initData) {
        this.scene.changeScene(scene, initData);
    }

    /**
     * シーンに対してメッセージを送信します。
     * @param msg シーンに送信するメッセージ
     */
    protected void sendMessageToScene(MSG msg) {
        this.scene.onMessageFromObj(this, msg);
    }
    /**
     * 自身が属しているホストに対してメッセージを送信します。
     * @param msg 自身が属しているホストに送信するメッセージ
     */
    protected void sendMessageToHost(MSG msg) {
        if(this.host == null) sendMessageToScene(msg);
        else  this.host.onMessageFromChild(this, msg);
    }
    
    @Override
    public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        return this.scene.imageUpdate(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    // ================= Final Method =================
    /**
     * 自身に属すIObjectを管理するListを初期化します。通常{@link #init(t_panda.game.IGObject.InitArg)}で呼び出されます。
     */
    final protected void initObjArrayList() {
        this.children = new ArrayList<>();
        this.addObjs = new ArrayList<>();        
    }
    /**
     * シーン画像に描画する画像を初期化します。通常{@link #init(t_panda.game.IGObject.InitArg)}で呼び出されます。
     */
    final protected void initObjImage() {
        this.image = new BufferedImage(this.drawWidth, this.drawHeight, BufferedImage.TYPE_INT_ARGB);
    }
    /**
     * 内部処理を正常に行うための初期化処理。通常{@link #init(t_panda.game.IGObject.InitArg)}で呼び出されます。
     * @param initArg 初期化処理の際に使用する引数オブジェクト
     */
    final protected void initMetaData(InitArg<SCENE, TAG, VKEYPAD, MSG> initArg) {
        this.host = initArg.getHost();
        this.frameCount = 0;
        this.scene = initArg.getScene();
    }
    /**
     * シーン画像に描画する画像を初期化します。通常{@link #destroy()}で呼び出されます。
     */
    final protected void disableObjImage() {
        if(image == null) return;
        image.flush();
        image = null;
    }
    /**
     * 自身に属すIObjectを管理するListを無効化します。通常{@link #destroy()}で呼び出されます。
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
     * 内部処理を正常に行うためのオブジェクトを無効化します。通常{@link #destroy()}で呼び出されます。
     */
    final protected void disableMetaData() {
        this.host = null;
        this.scene = null;
    }

    /**
     * シリアライズされる時に呼び出される関数。
     * @param out オブジェクトアウトプットストリーム
     * @throws IOException 入出力例外
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        try (var baos = new java.io.ByteArrayOutputStream()) {
            if(image == null) { out.writeInt(0); return; }
            ImageIO.write(this.image, "PNG", baos);
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
            this.image = ImageIO.read(bais);
        }
    }
}
