package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sumireko.abstracts.BaseCard;
import sumireko.actions.HandSelectAction;
import sumireko.powers.RetainSpecificCardPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Magnify extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Magnify",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public Magnify() {
        super(cardInfo, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(1, (c) -> true, cards -> {
            for (AbstractCard c : cards)
            {
                c.modifyCostForCombat(Math.max((upgraded ? 2 : 1 ) * c.costForTurn, 0));
                int block = c.cost == -1 ? EnergyPanel.getCurrentEnergy() : (Math.max(c.costForTurn, 0));
                if (block > 0)
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));


                boolean newRetain = true;
                for (AbstractPower pw : AbstractDungeon.player.powers)
                {
                    if (pw instanceof RetainSpecificCardPower && c.uuid.equals(((RetainSpecificCardPower) pw).card.uuid)) {
                        newRetain = false;
                        break;
                    }
                }
                if (newRetain)
                    addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RetainSpecificCardPower(AbstractDungeon.player, c)));
            }
        }, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Magnify();
    }
}