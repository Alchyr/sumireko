package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import sumireko.SealSystem;

public class ResetSealSystemAction extends AbstractGameAction {
    @Override
    public void update() {
        SealSystem.reset();
        this.isDone = true;
    }
}
