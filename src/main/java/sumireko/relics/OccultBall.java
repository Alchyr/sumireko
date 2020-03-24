package sumireko.relics;

import sumireko.abstracts.BaseRelic;
import sumireko.actions.OccultBallAction;

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
        if (this.counter <= 0)
            addToBot(new OccultBallAction(this));
    }
}