package t_panda.game.animation.easing;

/**  */ 
public enum EaseQuint implements IEasing {
    /**  */ IN(EaseExp.create(5).in()),
    /**  */ OUT(EaseExp.create(5).out()),
    /**  */ INOUT(EaseExp.create(5).inout());

    private final IEasing easing;
    private EaseQuint(IEasing easing) {
        this.easing = easing;
    }
    @Override
    public double calc(double d) {
        return this.easing.calc(d);
    }
}
