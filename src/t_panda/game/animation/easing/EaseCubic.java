package t_panda.game.animation.easing;

/**  */ 
public enum EaseCubic implements IEasing {
    /**  */ IN(EaseExp.create(3).in()),
    /**  */ OUT(EaseExp.create(3).out()),
    /**  */ INOUT(EaseExp.create(3).inout());

    private final IEasing easing;
    private EaseCubic(IEasing easing) {
        this.easing = easing;
    }
    @Override
    public double calc(double d) {
        return this.easing.calc(d);
    }
}
