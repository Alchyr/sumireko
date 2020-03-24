package sumireko.abstracts;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import com.megacrit.cardcrawl.relics.SneckoSkull;
import sumireko.actions.SealAction;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Collections;
import java.util.Map;

public abstract class SealCard extends BaseCard {
    public int baseSealValue;
    public int sealValue;
    public int sealUpgrade;

    public boolean isSealModified;
    public boolean upgradedSeal;

    public SealCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        super(cardInfo, upgradesDescription);
        baseSealValue = sealValue = -1;
        sealUpgrade = 0;
        upgradedSeal = false;
        isSealModified = false;
    }
/*
    @Override
    public void modifyCostForCombat(int amt) {

    }
    @Override
    public void setCostForTurn(int amt) {

    }
    @Override
    public void updateCost(int amt) {

    }
    @Override
    public boolean freeToPlay() {
        return false;
    }

    @Override
    public void update() {
        super.update();
        this.costForTurn = this.cost = this.upgradeCost && this.upgraded ? this.costUpgrade : this.baseCost;
        this.isCostModified = this.isCostModifiedForTurn = false;
    }*/

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

    public abstract void triggerSealEffect(AbstractMonster target);
    public abstract void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters);

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SealAction(this, m));
    }

    public void resetSealValue() {
        sealValue = baseSealValue;
        isSealModified = false;
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
    public void resetAttributes() {
        super.resetAttributes();
        sealValue = baseSealValue;
        isSealModified = false;
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
