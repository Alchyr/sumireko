package sumireko.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import sumireko.SealSystem;
import sumireko.actions.seals.SealAction;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.ModifySealPower;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Collections;
import java.util.Map;

public abstract class SealCard extends BaseCard {
    public int baseSealValue;
    public int sealValue;
    public int sealUpgrade;

    public int tempSealValue; //used during calculation.

    public boolean isSealModified;
    public boolean upgradedSeal;

    public boolean publicHovered; //hovered is private and this just makes it easier rather than using reflection.

    public SealCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        super(cardInfo, upgradesDescription);
        baseSealValue = sealValue = -1;
        sealUpgrade = 0;
        upgradedSeal = false;
        isSealModified = false;
    }

    public void setSeal(int baseValue, int upgradeAmount)
    {
        sealValue = baseSealValue = baseValue;
        sealUpgrade = upgradeAmount;
    }
    public void setSeal(int baseValue)
    {
        setSeal(baseValue, 0);
    }

    @Override
    public void upgrade() {
        if (!upgraded && sealUpgrade != 0)
        {
            sealValue = baseSealValue += sealUpgrade;
            upgradedSeal = true;
        }
        super.upgrade();
    }

    public void triggerSealEffect(AbstractMonster target) {

    }
    //health bar rendering for this is for something outside damage/hp loss inflicted to the pretend monster.
    //aka, just murder seal's self-damage.
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        return null;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (this.hasTag(CustomCardTags.ULTRA_FRAGILE_SEAL)) {
            this.glowColor = Color.RED.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SealAction(this, m));
    }

    public void resetSealValue() {
        sealValue = baseSealValue;
        isSealModified = false;
    }

    public void negateSeal() {
        sealValue = 0;
        isSealModified = true;
    }

    public void modifySealValue(int amount) {
        sealValue += amount;
        if (sealValue != baseSealValue)
            isSealModified = true;
    }

    public void multiplySealValue(int amount) {
        sealValue *= amount;
        if (sealValue != baseSealValue)
            isSealModified = true;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        sealValue = baseSealValue;

        sealValue += SealSystem.cardCounts.containsKey(this.cardID) ? SealSystem.cardCounts.get(this.cardID) + 1 : 0;
        isSealModified = sealValue != baseSealValue;

        for (AbstractPower p : AbstractDungeon.player.powers)
        {
            if (p instanceof ModifySealPower)
                ((ModifySealPower) p).modifySeal(this);
        }
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        sealValue = baseSealValue;
        isSealModified = false;
    }

    //called before base methods are called
    public void lockBaseValue() {
        tempSealValue = sealValue;
    }

    public void applyBaseAdjacencyEffect(SealCard c) //add/subtract
    {

    }
    public void applyAdjacencyEffect(SealCard c) //add/subtract
    {

    }
    public void applyFinalBaseAdjacencyEffect(SealCard c) //add/subtract
    {

    }
    public void applyFinalAdjacencyEffect(SealCard c) //multiply/divide
    {

    }

    public void centerMultiplier() //right now, exclusively explosion seal.
    {

    }

    public float modifyDamage(DamageInfo.DamageType type, float damage)
    {
        return damage;
    }

    public abstract void getIntent(SealIntent i);

    @Override
    public void hover() {
        super.hover();
        this.publicHovered = true;
    }

    @Override
    public void unhover() {
        super.unhover();
        this.publicHovered = false;
    }

    public static void pretendApplyPower(PretendMonster m, AbstractPower p, int stackAmount)
    {
        boolean beltTrigger = false;

        if (AbstractDungeon.player.hasRelic(ChampionsBelt.ID) && p.ID.equals(VulnerablePower.POWER_ID) && !m.hasPower(ArtifactPower.POWER_ID)) {
            beltTrigger = true; //for correct interaction with artifact
        }

        if (m.hasPower(ArtifactPower.POWER_ID) && p.type == AbstractPower.PowerType.DEBUFF) {
            AbstractPower artifact = m.getPower(ArtifactPower.POWER_ID);
            artifact.amount -= 1;
            if (artifact.amount <= 0)
                m.powers.remove(artifact);
            return;
        }

        for (AbstractPower pow : m.powers)
        {
            if (pow.ID.equals(p.ID))
            {
                pow.stackPower(stackAmount);

                if (beltTrigger)
                    pretendApplyPower(m, new WeakPower(m, 1, false), 1);

                return;
            }
        }
        m.powers.add(p);
        Collections.sort(m.powers);

        if (beltTrigger)
            pretendApplyPower(m, new WeakPower(m, 1, false), 1);
    }
}
