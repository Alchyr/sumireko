package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.TeleportationAction;
import sumireko.actions.general.HandSelectAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Teleportation extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Teleportation",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 15;
    private static final int UPG_BLOCK = 5;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = -1;


    public Teleportation() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HandSelectAction(69420, (c)->true,
                (cards) -> {
                    int amount = cards.size();
                    if (amount >= this.magicNumber) {
                        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(p, 1), 1));
                    }
                    else {
                        addToTop(new GainBlockAction(p, p, this.block));
                    }
                    for (AbstractCard c : cards) {
                        p.hand.moveToExhaustPile(c);
                    }
                    CardCrawlGame.dungeon.checkForPactAchievement();
                }, null, cardStrings.EXTENDED_DESCRIPTION[3],
                false, true, true)
        );
        //addToBot(new TeleportationAction(this.magicNumber, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Teleportation();
    }
}