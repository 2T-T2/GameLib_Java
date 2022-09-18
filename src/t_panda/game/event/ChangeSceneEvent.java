package t_panda.game.event;

/**
 * シーンが切り替わった時に発生するイベントオブジェクト
 */
public class ChangeSceneEvent<SCENE extends Enum<SCENE>> {
    private final SCENE before_scene;
    private final SCENE after_scene;

    /**
     * コンストラクタ
     * @param before_scene 切り替える前のシーン
     * @param after_scene 切り替えた後のシーン
     */
    public ChangeSceneEvent(SCENE before_scene, SCENE after_scene) {
        this.before_scene = before_scene;
        this.after_scene = after_scene;
    }

    /**
     * シーンが切り替わる前のシーン識別Enum
     * @return シーンが切り替わる前のシーン識別Enum
     */
    public SCENE getBeforeSceneName() { return before_scene; }
    /**
     * シーンが切り替わった後のシーン識別Enum
     * @return シーンが切り替わった後のシーン識別Enum
     */
    public SCENE getAfterSceneName() { return after_scene; }
}
