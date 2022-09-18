package t_panda.game.event;

/**
 * シーンの切り替わりを検知するイベントリスナ
 */
public interface ChangeSceneListener<SCENE extends Enum<SCENE>> {
    /**
     * シーンの切り替わりが発生した場合に呼び出されるます
     * @param e シーンが切り替わった時に発生するイベントオブジェクト
     * @see ChangeSceneEvent
     */
    void onChangeScene(ChangeSceneEvent<SCENE> e);
}
