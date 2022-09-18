package t_panda.game.animation.easing;

/**  */
public enum EaseBack implements IEasing {
    /**  */ IN(EaseInBack.INSTANCE),
    /**  */ OUT(EaseOutBack.INSTANCE),
    /**  */ INOUT(EaseInOutBack.INSTANCE);

    private static final double c1 = 1.70158; 
    private static final double c3 = 2.70158; 
    private final IEasing easing;
    private EaseBack(IEasing easing) { this.easing = easing; }
    @Override
    public double calc(double d) {
        return easing.calc(d);
    }
    private enum EaseInBack implements IEasing {
        INSTANCE;

        @Override
        public double calc(double d) {
            return c3 * d * d * d - c1 * d * d;
        }
    }
    private enum EaseOutBack implements IEasing {
        INSTANCE;

        @Override
        public double calc(double d) {
            return 1 + c3 * Math.pow(d - 1, 3) + c1 * Math.pow(d - 1, 2);
        }
    }
    private enum EaseInOutBack implements IEasing {
        INSTANCE;
        private final static double c2 = c1 * 1.525;
        @Override
        public double calc(double d) {
            return d < 0.5
            ? (Math.pow(2 * d, 2) * ((c2 + 1) * 2 * d - c2)) / 2
            : (Math.pow(2 * d - 2, 2) * ((c2 + 1) * (d * 2 - 2) + c2) + 2) / 2;
        }
    }
}
