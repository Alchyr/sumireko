package sumireko.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sumireko.abstracts.SealCard;
import sumireko.cards.rare.MysterySeal;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MysteryUpgrade {
    protected static final int SEAL_UPGRADE = 2;

    protected static UIStrings lines = CardCrawlGame.languagePack.getUIString(makeID("MysteryUpgrades"));
    protected static String[] TEXT = lines.TEXT;

    //When this upgrade is applied to the card.
    public int priority;
    public boolean isSealEffect;
    public boolean canStack;

    public MysteryUpgrade(int priority, boolean isSealEffect) {
        this(priority, isSealEffect, true);
    }
    public MysteryUpgrade(int priority, boolean isSealEffect, boolean canStack)
    {
        this.priority = priority;
        this.isSealEffect = isSealEffect;
        this.canStack = canStack;
    }

    public void use(MysterySeal c, AbstractPlayer p, AbstractMonster m)
    {

    }

    public void triggerSealEffect(MysterySeal c, AbstractMonster target) {

    }

    public void instantSealEffect(MysterySeal c, PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

    }

    public void applyBaseAdjacencyEffect(MysterySeal source, SealCard c) {
    }

    public void applyAdjacencyEffect(MysterySeal source, SealCard c) {
    }

    public void apply(MysterySeal c)
    {

    }

    public void addNormalEffect(StringBuilder description)
    {
    }
    public void addSealEffect(StringBuilder description)
    {
    }

    protected void addToBot(AbstractGameAction a)
    {
        AbstractDungeon.actionManager.addToBottom(a);
    }

    protected void applySingle(AbstractCreature c, AbstractPower power)
    {
        addToBot(new ApplyPowerAction(c, AbstractDungeon.player, power, power.amount));
    }

    protected WeakPower getWeak(AbstractCreature c, int amount)
    {
        return new WeakPower(c, amount, false);
    }
    protected VulnerablePower getVuln(AbstractCreature c, int amount)
    {
        return new VulnerablePower(c, amount, false);
    }
}
