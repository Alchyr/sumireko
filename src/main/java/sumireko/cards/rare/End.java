/*package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import sumireko.abstracts.BaseCard;
import sumireko.effects.ScalingLightningEffect;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class End extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "End",
            9,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    public End() {
        super(cardInfo, true);

        setDamage(0);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.modifyCostForCombat(1);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.freeToPlay() ? 0 : (this.costForTurn * (upgraded ? 3 : 2));
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = this.freeToPlay() ? 0 : (this.costForTurn * (upgraded ? 3 : 2));
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (this.damage < 50) {
        float scale = Math.max(0.1f, this.damage / 25.0f);
        damageSingle(m, AbstractGameAction.AttackEffect.NONE);
        addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
        addToBot(new VFXAction(new ScalingLightningEffect(m.drawX, m.drawY, scale), 0.1f));
        /*}
        else {
            damageSingle(m, AbstractGameAction.AttackEffect.NONE);
        }*//*
    }

    @Override
    public AbstractCard makeCopy() {
        return new End();
    }
}*/