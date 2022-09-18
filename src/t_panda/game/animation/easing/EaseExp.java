package t_panda.game.animation.easing;

/**
 * 全体の進捗を底とした、累乗を用いたイージングを行う無名クラスオブジェクトを生成するクラス。
 */
public class EaseExp {
    private final double pow;
    private EaseExp(double pow) { this.pow = pow; }

    /**
     * 累乗の指数を指定して、オブジェクトを生成します。
     * @param pow 指数
     * @return 生成されたオブジェクト
     */
    public static EaseExp create(double pow) {
        return new EaseExp(pow);
    }

    /**
     * 前半部分を減速を行うるIEasing継承無名クラスオブジェクトを生成します。
     * @return 前半部分を減速を行うるIEasing継承無名クラスオブジェクト
     */
    public IEasing in() {
        return new IEasing() {
            public double calc(double d) {
                return Math.pow(d, pow);
            }
        };
    }
    /**
     * 後半部分を減速を行うるIEasing継承無名クラスオブジェクトを生成します。
     * @return 後半部分を減速を行うるIEasing継承無名クラスオブジェクト
     */
    public IEasing out() {
        return new IEasing() {
            public double calc(double d) {
                return 1 - Math.pow(1-d, pow);
            }
        };
    }
    /**
     * 前半部分も後半部分も減速を行うるIEasing継承無名クラスオブジェクトを生成します。
     * @return 前半部分も後半部分も減速を行うるIEasing継承無名クラスオブジェクト
     */
    public IEasing inout() {
        return new IEasing() {
            public double calc(double d) {
                return d < 0.5
                    ? Math.pow(2, pow-1) * Math.pow(d, pow)
                    : 1 - Math.pow(-2 * d + 2, pow) / 2;
            }
        };
    }
}
// x < 0.5 ? 2 * x * x         : 1 - pow(-2 * x + 2, 2) / 2
// x < 0.5 ? 4 * x * x * x     : 1 - pow(-2 * x + 2, 3) / 2;
// x < 0.5 ? 8 * x * x * x * x : 1 - pow(-2 * x + 2, 4) / 2;
