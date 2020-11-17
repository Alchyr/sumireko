/*package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Bend extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Bend",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 11;

    public Bend() {
        super(cardInfo, false);

        setDamage(DAMAGE);

        tags.add(CustomCardTags.FINAL);
    }

    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("POWER_CONSTRICTED", 0.05F));
        addToBot(new VFXAction(new FlashPowerEffect(new ConstrictedPower(m, p,1)))); //no this isn't efficient but it's the lazy way
        damageSingle(m, AbstractGameAction.AttackEffect.NONE);

        addToBot(new DrawCardAction(1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bend();
    }
}*/