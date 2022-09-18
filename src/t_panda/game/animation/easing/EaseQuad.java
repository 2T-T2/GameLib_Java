package t_panda.game.animation.easing;

/**  */ 
public enum EaseQuad implements IEasing {
    /**  */ IN(EaseExp.create(2).in()),
    /**  */ OUT(EaseExp.create(2).out()),
    /**  */ INOUT(EaseExp.create(2).inout());

    private final IEasing easing;
    private EaseQuad(IEasing easing) {
        this.easing = easing;
    }
    @Override
    public double calc(double d) {
        return this.easing.calc(d);
    }
}
