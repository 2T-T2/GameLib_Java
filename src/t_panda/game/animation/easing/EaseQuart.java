package t_panda.game.animation.easing;

/**  */ 
public enum EaseQuart implements IEasing {
    /**  */ IN(EaseExp.create(4).in()),
    /**  */ OUT(EaseExp.create(4).out()),
    /**  */ INOUT(EaseExp.create(4).inout());

    private final IEasing easing;
    private EaseQuart(IEasing easing) {
        this.easing = easing;
    }
    @Override
    public double calc(double d) {
        return this.easing.calc(d);
    }
}
