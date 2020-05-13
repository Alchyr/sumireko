package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Sever extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Sever",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 50;

    public Sever() {
        super(cardInfo, false);

        setMagic(MAGIC);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = 0;
        if (mo != null)
        {
            this.baseDamage = mo.maxHealth - mo.currentHealth;

            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
        super.calculateCardDamage(mo);
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
        damageSingle(m, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sever();
    }
}