package sumireko.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import sumireko.actions.seals.SealAction;
import sumireko.enums.CharacterEnums;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.ModifySealPower;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.*;

public abstract class SealCard extends BaseCard {
    public int baseSealValue;
    public int sealValue;
    public int sealUpgrade;

    public int tempSealValue; //used during calculation in "base" methods.

    public boolean isSealModified;
    public boolean upgradedSeal;

    public boolean publicHovered; //hovered is private and this just makes it easier rather than using reflection.

    public SealCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo, upgradesDescription, CharacterEnums.SUMIREKO_CARD_COLOR);
    }

    public SealCard(CardInfo cardInfo, boolean upgradesDescription, CardColor color) {
        super(cardInfo, upgradesDescription, color);
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

    @Override
    public void triggerOnGlowCheck() {
        if (this.hasTag(CustomCardTags.ULTRA_FRAGILE_SEAL)) {
            if (this.hasTag(CustomCardTags.RETURNING_SEAL)) {
                this.tags.remove(CustomCardTags.ULTRA_FRAGILE_SEAL);
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
            else {
                this.glowColor = Color.RED.cpy();
            }
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    //return an arraylist of actions that will be added to bottom.
    //This arraylist will be passed to addAdjacentSealEffect and can be adjusted using that.
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        return new ArrayList<>();
    }
    public void addAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {

    }
    public void modifyAdjacentSealEffect(SealCard base, ArrayList<AbstractGameAction> actions, AbstractMonster target) {
        //occurs after add, to modify all actions of the seal.
    }
    //health bar render return is for something outside damage/hp loss inflicted to the pretend monster.
    //aka, just murder seal's self-damage.
    //*aka, for nothing right now, because murder seal has been Yeeted
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        return null;
    }
    //For effects that add additional effects to adjacent seals
    public void instantAdjacentEffect(SealCard primary, HashMap<AbstractMonster, PretendMonster> previewMonsters) {
    }
    //This is for effects that trigger based on adjacent seals. Currently, just Restraining Seal.
    public void instantAdjacentEffectOnUnblockedDamage(SealCard base, PretendMonster pretendMonster, int damage) {

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

    public abstract void getIntent(SealIntent i);
    public void applyAdjacencyIntent(SealIntent sealIntent) {
    }
    public int getSealBlock() {
        return 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SealAction(this, m));
    }

    public void resetSealValue() {
        tempSealValue = sealValue = baseSealValue;
        isSealModified = false;
    }

    public void negateSeal() {
        if (baseSealValue >= 0)
        {
            sealValue = 0;
            isSealModified = true;
        }
    }

    public void modifySealValue(int amount) {
        if (baseSealValue >= 0)
        {
            sealValue += amount;
            if (sealValue != baseSealValue)
                isSealModified = true;
        }
    }

    public void multiplySealValue(int amount) {
        if (baseSealValue >= 0) {
            sealValue *= amount;
            if (sealValue != baseSealValue)
                isSealModified = true;
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        sealValue = baseSealValue;

        /*sealValue += SealSystem.cardCounts.containsKey(this.cardID) ? SealSystem.cardCounts.get(this.cardID) + 1 : 0;
        isSealModified = sealValue != baseSealValue;*/

        for (AbstractPower p : AbstractDungeon.player.powers)
        {
            if (p instanceof ModifySealPower)
                ((ModifySealPower) p).modifySeal(this);
        }
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        resetSealValue();
    }

    public float modifyDamage(DamageInfo.DamageType type, float damage)
    {
        return damage;
    }

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

        Iterator<AbstractPower> iterator = m.powers.iterator();
        AbstractPower pow;
        while (iterator.hasNext()) {
            pow = iterator.next();

            if (pow.ID.equals(p.ID))
            {
                pow.amount += stackAmount; //fuck you powers adding a remove action when you stack to 0
                if (pow.amount == 0) {
                    iterator.remove();
                }

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
