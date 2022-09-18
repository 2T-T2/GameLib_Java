package t_panda.game;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * IGObjectを管理するための関数を実装します。
 */
public interface IGObjectHost<SCENE extends Enum<SCENE>, OBJ_TAG extends Enum<OBJ_TAG>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> extends java.awt.image.ImageObserver, Serializable {
    /**
     * 自身に属する子要素を描画します。
     * @param g 自身に描画するグラフィクス
     */
    default void drawChildObj(Graphics g) {
        for (int i = 0; i < getChildrenCount(); i++)
            getChildByIndex(i).draw(g);
    }
    /**
     * 自身に属する子要素を更新します。
     * @param children 自身に属するIGObject
     * @param keyInput キー入力情報を保持したオブジェクト
     * @param mouseInput マウス入力情報を保持したオブジェクト
     */
    default void updateChildObj(ArrayList<IGObject<SCENE,OBJ_TAG,VKEYPAD>> children, IKeyInput<VKEYPAD> keyInput, MouseInput mouseInput) {
        this.addReserveObj();
        for (int i = children.size()-1; i >= 0; i--) {
            var child = children.get(i);
            child.update(keyInput, mouseInput);
            if(child.isDestroyed()) {
                children.remove(i);
                child.removed();
            }
        }
    }

    /**
     * 自身に属しているIGObjectをインデックスを指定して取得します
     * @param index 取得するIGObjectの
     * @return  指定されたインデックスのIGObject
     */
    IGObject<SCENE, OBJ_TAG, VKEYPAD> getChildByIndex(int index);
    /**
     * 自身に属するIGObjectの個数を取得します。
     * @return 自身に属するIGObjectの個数
     */
    int getChildrenCount();

    /**
     * 自身に指定されたIGOjectを所属させる
     * @param obj 自身に所属させるIGOject
     */
    void addObj(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj);
    /**
     * 自身が属しているIGOjectHostに指定されたIGOjectを所属させる。自身がIGObjectHostに属していない場合は{@link #addObj(IGObject)}を呼び出します。
     * @param obj 自身が属しているIGOjectHost所属させるIGOject
     */
    void addObjToHost(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj);
    /**
     * 自身から指定されたIGOjectを削除させる
     * @param obj 自身から削除させるIGOject
     */
    void destroyObject(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj);
    /**
     * 自身が属しているIGOjectHostから指定されたIGOjectを削除させる。自身がIGObjectHostに属していない場合は{@link #addObj(IGObject)}を呼び出します。
     * @param obj 自身が属しているIGOjectHostから削除させるIGOject
     */
    void destroyObjectFromHost(IGObject<SCENE, OBJ_TAG, VKEYPAD> obj);
    /**
     * このフレームで追加されるべきオブジェクトを追加します。
     */
    void addReserveObj();
}
