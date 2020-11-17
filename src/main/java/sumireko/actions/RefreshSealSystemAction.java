package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import sumireko.SealSystem;

public class RefreshSealSystemAction extends AbstractGameAction {
    @Override
    public void update() {
        SealSystem.refresh();
        this.isDone = true;
    }
}
