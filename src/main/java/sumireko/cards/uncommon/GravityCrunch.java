package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class GravityCrunch extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GravityCrunch",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;


    public GravityCrunch() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c.freeToPlay() || c.cost < -1)
            {
                continue;
            }
            if (c.costForTurn >= 0)
            {
                this.baseDamage += c.costForTurn;
            }
            else if (c.cost == -1) {
                this.baseDamage += EnergyPanel.getCurrentEnergy();
            }
        }
        this.baseDamage *= this.magicNumber;

        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c.freeToPlay() || c.cost < -1)
            {
                continue;
            }
            if (c.costForTurn >= 0)
            {
                this.baseDamage += c.costForTurn;
            }
            else if (c.cost == -1) {
                this.baseDamage += EnergyPanel.getCurrentEnergy();
            }
        }
        this.baseDamage *= this.magicNumber;
        super.calculateCardDamage(mo);

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), 0.5F));
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public AbstractCard makeCopy() {
        return new GravityCrunch();
    }
}