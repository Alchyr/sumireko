package sumireko.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CustomCardTags {
    @SpireEnum
    public static AbstractCard.CardTags BUFF_SEAL; //a seal that buffs adjacent seals
    @SpireEnum
    public static AbstractCard.CardTags FRAGILE_SEAL; //a seal that exhausts after use instead of being discarded
    @SpireEnum
    public static AbstractCard.CardTags ULTRA_FRAGILE_SEAL; //a seal that vanishes after use completely. (for things that double card plays)
    @SpireEnum
    public static AbstractCard.CardTags FINAL; //cannot be upgraded or made occult. This is mainly for the Occult part of that.
}
