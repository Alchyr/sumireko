package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.XCostAction;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;
import sumireko.util.SealIntent;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class OccultSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "OccultSeal",
            -1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BONUS = 2;
    private static final int UPG_BONUS = 1;

    public OccultSeal() {
        super(cardInfo, false);

        tags.add(CustomCardTags.BUFF_SEAL);

        setMagic(BONUS, UPG_BONUS);
        setSeal(-1);
    }

    @Override
    public boolean hasEnoughEnergy() {
        boolean returnVal = super.hasEnoughEnergy();

        if (OccultFields.isOccult.get(this) && !this.freeToPlay() && !this.isInAutoplay) {
            this.cantUseMessage = TEXT[11];
            returnVal = false;
        }

        return returnVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (OccultFields.isOccult.get(this)) {
            this.energyOnUse += this.magicNumber;
        }

        addToBot(new XCostAction(this,
                (x, params)->{
                    this.baseSealValue = this.sealValue = x;
                    this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
                    this.initializeDescription();
                    return true;
                }));

        super.use(p, m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new OccultSeal();
    }

    @Override
    public ArrayList<AbstractGameAction> triggerSealEffect(AbstractMonster target) {
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        actions.add(new AbstractGameAction() {
            @Override
            public void update() {
                OccultSeal.this.rawDescription = cardStrings.DESCRIPTION;
                OccultSeal.this.initializeDescription();
                this.isDone = true;
            }
        });
        return actions;
    }

    @Override
    public void applyBaseAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.tempSealValue);
    }

    @Override
    public void applyAdjacencyEffect(SealCard c) {
        c.modifySealValue(this.sealValue);
    }

    @Override
    public void getIntent(SealIntent i) {
        i.addIntent(SealIntent.BUFF);
        i.bonusEffect("+" + this.sealValue);
    }
}