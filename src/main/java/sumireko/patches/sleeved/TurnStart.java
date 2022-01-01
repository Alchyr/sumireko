package sumireko.patches.sleeved;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.interfaces.SleevedCard;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfTurnPostDrawRelics"
)
public class TurnStart {
    @SpirePostfixPatch
    public static void applySleevedCards(AbstractPlayer __instance) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;

                for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                    if (c instanceof SleevedCard)
                        ((SleevedCard) c).sleevedTurnStart();
            }
        });
    }
}