package sumireko.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import javassist.CtBehavior;
import sumireko.SealSystem;
import sumireko.abstracts.SealCard;

import static sumireko.SumirekoMod.makeID;

public class CardDestination {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SealCardUse"));

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class NoMoveSeals
    {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<?> noMove(UseCardAction __instance, AbstractCard ___targetCard, float ___duration)
        {
            if (___targetCard instanceof SealCard)
            {
                if (SealSystem.centerCard == null || SealSystem.centerCard.equals(___targetCard)) //not full
                {
                    ReflectionHacks.setPrivate(__instance, AbstractGameAction.class, "duration", ___duration - Gdx.graphics.getDeltaTime());

                    AbstractDungeon.player.hand.removeCard(___targetCard);

                    AbstractDungeon.player.cardInUse = null;
                    ___targetCard.exhaustOnUseOnce = false;
                    ___targetCard.dontTriggerOnUseCard = false;
                    AbstractDungeon.actionManager.addToBottom(new HandCheckAction());

                    return SpireReturn.Return(null);
                }
                else
                {
                    //popup message
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, uiStrings.TEXT[0], true));
                }
            }
            return SpireReturn.Continue();
        }


        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "purgeOnUse");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}