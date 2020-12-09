package sumireko.patches;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import sumireko.SealSystem;

import java.util.ArrayList;

public class DefinitelyNotPureNailPatch
{
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
    public static class ApplyPowersSingle
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp"}
        )
        public static void Insert(AbstractCard __instance, @ByRef float[] tmp)
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
            method="applyPowers"
    )
    public static class ApplyPowersMulti
    {
        @SpireInsertPatch(
                locator=Locator.class,
                localvars={"tmp", "i"}
        )
        public static void Insert(AbstractCard __instance, float[] tmp, int i)
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