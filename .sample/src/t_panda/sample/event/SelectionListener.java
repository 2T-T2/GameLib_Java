package t_panda.sample.event;

import t_panda.game.IGObject;
import t_panda.sample.Defined.*;

public interface SelectionListener {
    void pressOkKey(IGObject<Scenes, Tag, Key> obj);
    void pressCancelKey(IGObject<Scenes, Tag, Key> obj);
}
