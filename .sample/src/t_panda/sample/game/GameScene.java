package t_panda.sample.game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import t_panda.game.IGObject;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.game.Scene;
import t_panda.sample.SaveData;
import t_panda.sample.Defined.Fonts;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.event.ScoreChangeListener;

public class GameScene extends Scene<Scenes, Tag, Key> implements ScoreChangeListener {
    private boolean started = false;
    private Player player;
    private Boll boll;
    private Brick[] bricks;
    private int score = 0;
    private int preScore;

    private long desirializeFrameCount;

    public GameScene(Scenes scene) {
        super(scene);
    }

    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        if(isDeserializeAndNeverCallUpdate()) {
            initMetaData(initArg);
            initSceneImage(getGameWidth(), getGameHeight());
            return;
        }
        super.init(initArg);
        player = new Player(getDrawWidth()>>1, getDrawHeight()*6/7, getDrawWidth()/5, 16);
        addObj(player);
        int block_h = 20;
        int block_w = getDrawWidth()/9;
        bricks = new Brick[5*8];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 8; x++) {
                bricks[x+y*8] = new Brick(block_w+((block_w)*x), block_h+(block_h)*y, block_w, block_h);
                bricks[x+y*8].addScoreChangeListener(this);
                addObj(bricks[x+y*8]);
            }
        }
        boll = new Boll(getDrawWidth()>>1, getDrawHeight()*6/7-16, 16);
        addObj(boll);
        started = false;
        score = 0;
    }
    @Override
    protected void leave() {
        super.leave();
        player = null;
        boll = null;
        bricks = null;
    }

    @Override
    protected void drawToSceneImage(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getDrawWidth(), getDrawHeight());
        if(!started) {
            g.setColor(Color.white);
            g.setFont(Fonts.L_FONT);
            g.drawString("Press Start Key!", 3, (getDrawHeight()>>1));
        }else {
            g.setColor(Color.white);
            g.setFont(Fonts.S_FONT);
            g.drawString("SCORE: "+score, 0, Fonts.S_FONT.getSize());
        }
    }
    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        if(keyInput.getKey(Key.START)) started = true;
        if(!started) return;
        super.update(keyInput, mouseInput);
        for (int i = 0; i < this.getChildrenCount(); i++)
            if(boll.checkBound(this.getChildByIndex(i)) && this.getChildByIndex(i).hasTag(Tag.BRICK))
                this.getChildByIndex(i).destroy();

        if( keyInput.getKeyDown(Key.START) ) 
            中断セーブ();

        if(boll.isDestroyed()) changeScene(Scenes.GAMEOVER, score);
        if(score%1500 == 0 || (preScore/1500) != score/1500) {
            int a = score / 1500;
            boll.setSpdX((int)Math.signum(boll.getSpdX())*(Math.abs(boll.getSpdX())+a));
            boll.setSpdY((int)Math.signum(boll.getSpdY())*(Math.abs(boll.getSpdY())+a));
        }
        preScore = score;
        score++;
    }
    @Override
    public void updateChildObj(ArrayList<IGObject<Scenes, Tag, Key>> children, IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.updateChildObj(children, keyInput, mouseInput);
    }
    @Override
    public void drawChildObj(Graphics g) {
        super.drawChildObj(g);
    }
    @Override
    public void add(int p) {
        score+=p;
    }
    @Override
    public void sub(int p) {
        score-=p;
    }
    private void 中断セーブ() {
        if(getFrameCount() <= 5) return;
        if(this.desirializeFrameCount+1 == getFrameCount() ) return;
        try {
            this.desirializeFrameCount = getFrameCount();
            SaveData.INSTANCE.save_中断セーブデータ();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
