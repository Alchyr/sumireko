package sumireko.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import sumireko.abstracts.BaseRelic;
import sumireko.actions.relics.OccultBallAction;

import static sumireko.SumirekoMod.makeID;

public class OccultBall extends BaseRelic {
    public static final String ID = makeID("OccultBall");
    private static final String IMG = "OccultBall";

    public OccultBall()
    {
        super(ID, IMG, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 3;
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        --this.counter;
        if (this.counter <= 0) {
            OccultBall o = this;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new OccultBallAction(o));
                    this.isDone = true;
                }
            });
        }
    }
}