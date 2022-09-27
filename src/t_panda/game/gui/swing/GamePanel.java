package t_panda.game.gui.swing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import t_panda.game.IGame;
import t_panda.game.IKeyCodeGetable;
import t_panda.game.event.GameDrawEvent;
import t_panda.game.event.GameDrawListener;

/**
 * ゲームオブジェクトを操作するJPanel継承クラス
 */
public class GamePanel<SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> extends JPanel implements KeyListener, MouseListener, MouseMotionListener, GameDrawListener {
    /** */
    private final IGame<SCENE, VKEYPAD> game;

    /**
     * コンストラクタ
     * @param game ゲームオブジェクト
     */
    public GamePanel(IGame<SCENE, VKEYPAD> game) {
        setFocusable(true);
        setRequestFocusEnabled(true);
        grabFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.game = game;
        this.game.addGameDrawListener(this);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(this.game.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {
        game.setMouseCurrentX(e.getX());
        game.setMouseCurrentY(e.getY());
    }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {
        game.setMouseClickX(e.getButton(), e.getX());
        game.setMouseClickY(e.getButton(), e.getY());
    }
    @Override public void mouseReleased(MouseEvent e) {
        game.setMouseReleaseX(e.getButton(), e.getX());
        game.setMouseReleaseY(e.getButton(), e.getY());
    }
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        game.pressKey(e.getKeyCode());
    }
    @Override public void keyReleased(KeyEvent e) {
        game.releaseKey(e.getKeyCode());
    }

    @Override
    public void gameDraw(GameDrawEvent e) {
        repaint();
    }
    /**
     * 自身に設定されたIGame継承オブジェクトを取得します。
     * @return 自身に設定されたIGame継承オブジェクト
     */
    public IGame<SCENE, VKEYPAD> getGame() {
        return this.game;
    }
}
