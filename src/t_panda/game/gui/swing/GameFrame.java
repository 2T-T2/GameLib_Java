package t_panda.game.gui.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import t_panda.game.IGame;
import t_panda.game.IKeyCodeGetable;

/**
 * GamePanelを追加済みのJFrameです。IGameを継承しています
 * @see GamePanel
 * @see IGame
 * @see JFrame
 */
public class GameFrame<SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> extends JFrame implements ComponentListener, Runnable, KeyListener {
    /** ゲーム画面を表示するパネル */
    private final GamePanel<SCENE, VKEYPAD> gPanel;
    /** */
    private final float gameAspect;

    /**
     * コンストラクタ
     * @param game ゲームオブジェクト
     */
    public GameFrame(IGame<SCENE, VKEYPAD> game) {
        this.gameAspect = (float)game.getGameWidth() / (float)game.getGameHeight();

        getContentPane().setPreferredSize(new java.awt.Dimension(game.getGameWidth(), game.getGameHeight()));
        JPanel dummy = new JPanel();

        dummy.setLayout(null);
        gPanel = new GamePanel<>(game);
        gPanel.addKeyListener(this);
        setPanelBounds();
        dummy.add(gPanel);
        add(dummy);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentListener(this);
    }

    /**
     * 自身に設定されたIGame継承オブジェクトを取得します。
     * @return 自身に設定されたIGame継承オブジェクト
     */
    public IGame<SCENE, VKEYPAD> getGame() {
        return this.gPanel.getGame();
    }

    private void setPanelBounds() {
        java.awt.Dimension clientSize = this.getContentPane().getSize();
        int h = (int)(Math.min(clientSize.getWidth()/gameAspect, clientSize.getHeight()));
        int w = (int)(h * gameAspect);
        int x = (clientSize.width-w)>>1;
        int y = (clientSize.height-h)>>1;
        gPanel.setBounds(x, y, w, h);
        // gPanel.getGame().setDestWidth(w);
        // mouseInput.setScale((float)getGameWidth()/(float)getDestWidth());
        gPanel.getGame().setMouseInputScale((float)getGame().getGameWidth()/(float)w);
    }
    @Override public void componentMoved(ComponentEvent e) {}
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void componentResized(ComponentEvent e) {
        setPanelBounds();
    }

    // =============== IRunnable =============== 
    @Override public void run() {
        setVisible(true);
        Thread gameThread = new Thread(gPanel.getGame());
        gameThread.start();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override public void keyPressed(KeyEvent arg0) {}
    @Override public void keyReleased(KeyEvent arg0) {}
    @Override public void keyTyped(KeyEvent arg0) {}
}
