package sumireko.abstracts;

import basemod.abstracts.CustomCard;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sumireko.SumirekoMod;
import sumireko.enums.CharacterEnums;
import sumireko.util.CardInfo;
import sumireko.util.TextureLoader;

import static sumireko.SumirekoMod.makeID;


public abstract class BaseCard extends CustomCard {
    public static final CardColor COLOR = CharacterEnums.SUMIREKO_CARD_COLOR;

    protected CardStrings cardStrings;
    protected String img;

    protected boolean upgradesDescription;

    protected int baseCost;

    protected boolean upgradeCost;
    protected boolean upgradeDamage;
    protected boolean upgradeBlock;
    protected boolean upgradeMagic;

    protected int costUpgrade;
    protected int damageUpgrade;
    protected int blockUpgrade;
    protected int magicUpgrade;

    protected boolean baseExhaust;
    protected boolean upgExhaust;
    protected boolean baseInnate;
    protected boolean upgInnate;

    public BaseCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public BaseCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription)
    {
        super(makeID(cardName), "", TextureLoader.getAndLoadCardTextureString(cardName, cardType), cost, "", cardType, COLOR, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);

        this.rawDescription = cardStrings.DESCRIPTION;
        this.originalName = cardStrings.NAME;
        this.name = originalName;

        this.baseCost = cost;

        this.upgradesDescription = upgradesDescription;

        this.upgradeCost = false;
        this.upgradeDamage = false;
        this.upgradeBlock = false;
        this.upgradeMagic = false;

        this.costUpgrade = cost;
        this.damageUpgrade = 0;
        this.blockUpgrade = 0;
        this.magicUpgrade = 0;

        initializeCard();
    }

    public void loadFrames(String cardName, int frameCount, float frameRate)
    {
        try
        {
            //AnimatedCardsPatch.load(this, frameCount, frameRate, getAnimatedCardTextures(cardName, type));
        }
        catch (Exception e)
        {
            SumirekoMod.logger.error("Failed to load animated card image for " + cardName + ".");
        }
    }

    //Methods meant for constructor use
    protected void setDamage(int damage)
    {
        this.setDamage(damage, 0);
    }
    protected void setBlock(int block)
    {
        this.setBlock(block, 0);
    }
    protected void setMagic(int magic)
    {
        this.setMagic(magic, 0);
    }
    protected void setCostUpgrade(int costUpgrade)
    {
        this.costUpgrade = costUpgrade;
        this.upgradeCost = true;
    }
    protected void setExhaust(boolean exhaust) { this.setExhaust(exhaust, exhaust); }
    protected void setInnate(boolean innate) {this.setInnate(innate, innate); }
    protected void setDamage(int damage, int damageUpgrade)
    {
        this.baseDamage = this.damage = damage;
        if (damageUpgrade != 0)
        {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgrade;
        }
    }
    protected void setBlock(int block, int blockUpgrade)
    {
        this.baseBlock = this.block = block;
        if (blockUpgrade != 0)
        {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgrade;
        }
    }
    protected void setMagic(int magic, int magicUpgrade)
    {
        this.baseMagicNumber = this.magicNumber = magic;
        if (magicUpgrade != 0)
        {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgrade;
        }
    }
    protected void setExhaust(boolean baseExhaust, boolean upgExhaust)
    {
        this.baseExhaust = baseExhaust;
        this.upgExhaust = upgExhaust;
        this.exhaust = baseExhaust;
    }
    protected void setInnate(boolean baseInnate, boolean upgInnate)
    {
        this.baseInnate = baseInnate;
        this.isInnate = baseInnate;
        this.upgInnate = upgInnate;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();

        if (card instanceof BaseCard)
        {
            card.rawDescription = this.rawDescription;
            ((BaseCard) card).upgradesDescription = this.upgradesDescription;

            ((BaseCard) card).baseCost = this.baseCost;

            ((BaseCard) card).upgradeCost = this.upgradeCost;
            ((BaseCard) card).upgradeDamage = this.upgradeDamage;
            ((BaseCard) card).upgradeBlock = this.upgradeBlock;
            ((BaseCard) card).upgradeMagic = this.upgradeMagic;

            ((BaseCard) card).costUpgrade = this.costUpgrade;
            ((BaseCard) card).damageUpgrade = this.damageUpgrade;
            ((BaseCard) card).blockUpgrade = this.blockUpgrade;
            ((BaseCard) card).magicUpgrade = this.magicUpgrade;

            ((BaseCard) card).baseExhaust = this.baseExhaust;
            ((BaseCard) card).upgExhaust = this.upgExhaust;
            ((BaseCard) card).baseInnate = this.baseInnate;
            ((BaseCard) card).upgInnate = this.upgInnate;
        }

        return card;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            this.upgradeName();

            if (this.upgradesDescription)
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;

            if (upgradeCost)
            {
                int diff = this.baseCost - this.cost; //positive if cost is reduced

                this.upgradeBaseCost(costUpgrade);
                this.cost -= diff;
                this.costForTurn -= diff;
                if (cost < 0)
                    cost = 0;

                if (costForTurn < 0)
                    costForTurn = 0;
            }

            if (upgradeDamage)
                this.upgradeDamage(damageUpgrade);

            if (upgradeBlock)
                this.upgradeBlock(blockUpgrade);

            if (upgradeMagic)
                this.upgradeMagicNumber(magicUpgrade);

            if (baseExhaust ^ upgExhaust) //different
                this.exhaust = upgExhaust;

            if (baseInnate ^ upgInnate) //different
                this.isInnate = upgInnate;


            this.initializeDescription();
        }
    }

    public void initializeCard()
    {
        //FontHelper.cardDescFont_N.getData().setScale(1.0f);
        this.initializeTitle();
        this.initializeDescription();
    }

    //utility methods
    protected void gainEnergy(int amount) {
        addToBot(new GainEnergyAction(amount));
    }
    protected void drawCards(int amount) {
        addToBot(new DrawCardAction(amount));
    }
    protected void block() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }
    protected void giveBlock(AbstractCreature target, int amount) {
        addToBot(new GainBlockAction(target, AbstractDungeon.player, amount));
    }
    protected void damageSingle(AbstractCreature target, AbstractGameAction.AttackEffect effect)
    {
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn), effect));
    }
    protected void damageSingle(AbstractCreature target, int amount, AbstractGameAction.AttackEffect effect)
    {
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, amount, this.damageTypeForTurn), effect));
    }
    protected void damageSingle(AbstractCreature target, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect)
    {
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, amount, type), effect));
    }
    protected void damageAll(AbstractGameAction.AttackEffect effect)
    {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, this.damageTypeForTurn, effect));
    }
    protected void damageAll(int amount, AbstractGameAction.AttackEffect effect)
    {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amount, this.damageTypeForTurn, effect));
    }
    protected void damageAll(int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect)
    {
        if (type != DamageInfo.DamageType.NORMAL)
        {
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(amount, true), type, effect));
        }
        else
        {
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amount, type, effect));
        }
    }


    protected void applySingle(AbstractCreature c, AbstractPower power)
    {
        addToBot(new ApplyPowerAction(c, AbstractDungeon.player, power, power.amount));
    }

    protected void applySelf(AbstractPower power)
    {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, power, power.amount));
    }

    protected<T extends AbstractPower & CloneablePowerInterface> void applyAll(T power, AbstractGameAction.AttackEffect effect)
    {
        applyAll(power, false, effect);
    }
    protected<T extends AbstractPower & CloneablePowerInterface> void applyAll(T power, boolean isFast, AbstractGameAction.AttackEffect effect)
    {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
        {
            AbstractPower toApply = power.makeCopy();

            toApply.owner = m;
            toApply.updateDescription();

            addToBot(new ApplyPowerAction(m, AbstractDungeon.player, toApply, toApply.amount, isFast, effect));
        }
    }

    protected WeakPower getWeak(AbstractCreature c, int amount)
    {
        return new WeakPower(c, amount, false);
    }
    protected VulnerablePower getVuln(AbstractCreature c, int amount)
    {
        return new VulnerablePower(c, amount, false);
    }
}