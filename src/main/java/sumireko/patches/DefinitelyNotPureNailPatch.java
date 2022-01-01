package sumireko.patches;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import sumireko.SealSystem;
import sumireko.interfaces.DamageModifierCard;

import java.util.ArrayList;

public class DefinitelyNotPureNailPatch
{
    private static float addDamage(AbstractCard card, float damage)
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof DamageModifierCard) {
                damage = ((DamageModifierCard) c).modifyDamage(card.damageTypeForTurn, damage);
            }
        }

        if (card.baseDamage != damage) {
            card.isDamageModified = true;
        }

        return damage;
    }

    private static float addDamage(DamageInfo info, float damage)
    {
        if (info.owner == AbstractDungeon.player) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof DamageModifierCard) {
                    damage = ((DamageModifierCard) c).modifyDamage(info.type, damage);
                }
            }

            if (info.base != damage) {
                info.isModified = true;
            }
        }

        return damage;
    }

    private static float multiplyDamage(AbstractCard card, float damage)
    {
        if (SealSystem.centerCard != null)
        {
            damage = SealSystem.centerCard.modifyDamage(card.damageTypeForTurn, damage);
        }

        for (int i = 3; i >= 0; --i)
        {
            if (SealSystem.aroundCards[i] == null)
                continue;

            damage = SealSystem.aroundCards[i].modifyDamage(card.damageTypeForTurn, damage);
        }

        if (card.baseDamage != damage) {
            card.isDamageModified = true;
        }
        return damage;
    }

    private static float multiplyDamage(DamageInfo info, float damage)
    {
        if (info.owner == AbstractDungeon.player) {
            if (SealSystem.centerCard != null)
            {
                damage = SealSystem.centerCard.modifyDamage(info.type, damage);
            }

            for (int i = 3; i >= 0; --i)
            {
                if (SealSystem.aroundCards[i] == null)
                    continue;

                damage = SealSystem.aroundCards[i].modifyDamage(info.type, damage);
            }

            if (info.base != damage) {
                info.isModified = true;
            }
        }
        return damage;
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="applyPowers"
    )
    public static class ApplyPowers
    {
        //Add
        @SpireInsertPatch(
                locator=AddSingle.class,
                localvars={"tmp"}
        )
        public static void InsertAddSingle(AbstractCard __instance, @ByRef float[] tmp)
        {
            tmp[0] = addDamage(__instance, tmp[0]);
        }
        private static class AddSingle extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        @SpireInsertPatch(
                locator=AddMulti.class,
                localvars={"tmp", "i"}
        )
        public static void InsertAddMulti(AbstractCard __instance, float[] tmp, int i)
        {
            tmp[i] = addDamage(__instance, tmp[i]);
        }

        private static class AddMulti extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
            }
        }

        //Multiply
        @SpireInsertPatch(
                locator=MultiplySingle.class,
                localvars={"tmp"}
        )
        public static void InsertMultiplySingle(AbstractCard __instance, @ByRef float[] tmp)
        {
            tmp[0] = multiplyDamage(__instance, tmp[0]);
        }

        private static class MultiplySingle extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        @SpireInsertPatch(
                locator=MultiplyMulti.class,
                localvars={"tmp", "i"}
        )
        public static void InsertMultiplyMulti(AbstractCard __instance, float[] tmp, int i)
        {
            tmp[i] = multiplyDamage(__instance, tmp[i]);
        }

        private static class MultiplyMulti extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardSingle
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp)
        {
            tmp[0] = multiplyDamage(__instance, tmp[0]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="calculateCardDamage"
    )
    public static class CalculateCardMulti
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp", "i"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, float[] tmp, int i)
        {
            tmp[i] = multiplyDamage(__instance, tmp[i]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                ArrayList<Matcher> matchers = new ArrayList<>();
                matchers.add(finalMatcher);
                matchers.add(finalMatcher);
                matchers.add(finalMatcher);
                return LineFinder.findInOrder(ctMethodToPatch, matchers, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=DamageInfo.class,
            method="applyPowers"
    )
    public static class DamageInfoApplyPowers
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp"}
        )
        public static void Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp)
        {
            tmp[0] = multiplyDamage(__instance, tmp[0]);
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}