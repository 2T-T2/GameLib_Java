package t_panda.game;

/**
 * シーンを変更するchangeSceneを実装します。
 */
public interface ISceneChanger<SCENE extends Enum<SCENE>> {
    /**
     * 指定したシーンに切り替え、シーンの初期化に使用するデータを送信します。
     * @param scene 切り替えるシーン
     * @param initData シーンの初期化に使用するデータ
     * @see IScene#init(t_panda.game.IScene.InitArg)
     * @see IScene.InitArg#getReceiveData()
     */
    void changeScene(SCENE scene, Object initData);
}
