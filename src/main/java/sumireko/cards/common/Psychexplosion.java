package sumireko.cards.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import sumireko.abstracts.BaseCard;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Psychexplosion extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Psychexplosion",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 3;


    public Psychexplosion() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (OccultFields.isOccultPlayable.get(this))
        {
            this.damage *= 2;
            this.isDamageModified = true;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);

        if (OccultFields.isOccultPlayable.get(this))
        {
            this.damage *= 2;
            this.isDamageModified = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (OccultFields.isOccultPlayable.get(this))
        {
            addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX + MathUtils.random(-35.0f * Settings.scale, 35.0f * Settings.scale), m.hb.cY + MathUtils.random(0, 20.0f * Settings.scale))));
            damageSingle(m, AbstractGameAction.AttackEffect.FIRE);
        }
        else
        {
            damageSingle(m, AbstractGameAction.AttackEffect.FIRE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Psychexplosion();
    }
}