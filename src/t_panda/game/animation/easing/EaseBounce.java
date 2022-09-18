package t_panda.game.animation.easing;

/**
 * バウンドをしているよううなイージングを行います。
 */
public enum EaseBounce implements IEasing {
    /**  */ IN(EaseInBounce.INSTANCE), 
    /**  */ OUT(EaseOutBounce.INSTANCE), 
    /**  */ INOUT(EaseInOutBounce.INSTANCE);

    private final IEasing easing;
    private EaseBounce(IEasing easing) { this.easing = easing; }

    @Override
    public double calc(double d) { return easing.calc(d); }

    private enum EaseOutBounce implements IEasing {
        INSTANCE;
        private final double n1 = 7.5625;
        private final double d1 = 2.75;
        @Override
        public double calc(double d) {
            if (d < 1 / d1)
                return n1 * d * d;
            else if (d < 2 / d1)
                return n1 * (d -= 1.5 / d1) * d + 0.75;
            else if (d < 2.5 / d1)
                return n1 * (d -= 2.25 / d1) * d + 0.9375;
            else
                return n1 * (d -= 2.625 / d1) * d + 0.984375;
        }
    }
    private enum EaseInBounce implements IEasing {
        INSTANCE;
        @Override
        public double calc(double d) {
            return 1 - EaseOutBounce.INSTANCE.calc(1 - d);
        }
    }
    private enum EaseInOutBounce implements IEasing {
        INSTANCE;
        @Override
        public double calc(double d) {
            return d < 0.5
            ? (1 - EaseOutBounce.INSTANCE.calc(1 - 2 * d)) / 2
            : (1 + EaseOutBounce.INSTANCE.calc(2 * d - 1)) / 2;
        }
    }
}