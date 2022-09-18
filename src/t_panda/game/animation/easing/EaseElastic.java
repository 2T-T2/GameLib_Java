package t_panda.game.animation.easing;

/**  */ 
public enum EaseElastic implements IEasing {
    /**  */ IN(EaseInElastic.INSTANCE), 
    /**  */ OUT(EaseOutElastic.INSTANCE),
    /**  */  INOUT(EaseInOutElastic.INSTANCE);

    private final IEasing easing;
    private static final double c4 = (2 * Math.PI) / 3;
    private EaseElastic(IEasing easing) { this.easing = easing; }
    @Override
    public double calc(double d) {
        return easing.calc(d);
    }

    private enum EaseInElastic implements IEasing {
        INSTANCE
        ;

        @Override
        public double calc(double x) {
            return x == 0 || x == 1 ? x
                : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4);
        }
    }
    private enum EaseOutElastic implements IEasing {
        INSTANCE
        ;

        @Override
        public double calc(double x) {
            return x == 0 || x == 1 ? x
                : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1;
        }
    }
    private enum EaseInOutElastic implements IEasing {
        INSTANCE
        ;
        private final double c5 = (2 * Math.PI) / 4.5;
        @Override
        public double calc(double x) {
            return x == 0 ||  x == 1 ? x
                : x < 0.5
                    ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2
                    : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1;
        }
    }
}