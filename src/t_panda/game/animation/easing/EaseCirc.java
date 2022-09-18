package t_panda.game.animation.easing;

/**  */ 
public enum EaseCirc implements IEasing {
    /**  */ IN(EaseInCirc.INSTANCE),
    /**  */ OUT(EaseOutCirc.INSTANCE),
    /**  */ INOUT(EaseInOutCirc.INSTANCE);

    private final IEasing easing;
    private EaseCirc(IEasing easing) { this.easing = easing; }
    @Override
    public double calc(double d) {
        return easing.calc(d);
    }
    private enum EaseInCirc implements IEasing {
        INSTANCE;
        @Override
        public double calc(double x) {
            return 1 - Math.sqrt(1 - Math.pow(x, 2));
        }
    }
    private enum EaseOutCirc implements IEasing {
        INSTANCE;
        @Override
        public double calc(double x) {
            return Math.sqrt(1 - Math.pow(x - 1, 2));
        }
    }
    private enum EaseInOutCirc implements IEasing {
        INSTANCE;
        @Override
        public double calc(double x) {
            return x < 0.5
            ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
            : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
        }
    }
}