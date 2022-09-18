package t_panda.game.animation.easing;

/**  */ 
public interface IEasing {
    /**
     * 指定された進捗率を用いてイージングで計算を行った時の値を返します
     * @param d 指定された進捗率
     * @return イージングで計算を行った時の値を返します
     */ 
    double calc(double d);
}
