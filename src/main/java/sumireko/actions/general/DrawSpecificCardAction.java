package sumireko.actions.general;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class DrawSpecificCardAction extends AbstractGameAction {
    private final AbstractCard card;
    private boolean firstCheck;
    
    public DrawSpecificCardAction(AbstractCard c) {
        card = c;
        firstCheck = true;
        this.duration = 0.1f;
    }

    @Override
    public void update() {
        if (firstCheck) {
            if (AbstractDungeon.player.drawPile.isEmpty() || !AbstractDungeon.player.drawPile.contains(card)) {
                this.isDone = true;
                return;
            }
            else {
                AbstractPower p = AbstractDungeon.player.getPower(NoDrawPower.POWER_ID);
                if (p != null) {
                    p.flash();
                    this.isDone = true;
                    return;
                }
            }
        }
        
        if (!SoulGroup.isActive()) {
            if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else {
                this.duration -= Gdx.graphics.getDeltaTime();
                if (this.duration <= 0.0F) {

                    if (AbstractDungeon.player.drawPile.contains(card)) {
                        AbstractDungeon.player.drawPile.group.remove(card);
                        AbstractDungeon.player.drawPile.group.add(card);
                        AbstractDungeon.player.draw();
                        AbstractDungeon.player.hand.refreshHandLayout();
                    }

                    this.isDone = true;
                }
            }
        }
    }
}
