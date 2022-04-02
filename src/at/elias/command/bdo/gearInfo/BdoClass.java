package at.elias.command.bdo.gearInfo;

public enum BdoClass {

    WARRIOR("Warrior", "war"),
    RANGER("Ranger"),
    SORCERESS("Sorceress", "Sorc"),
    BERSERKER("Berserker", "Serker", "Zerker"),
    TAMER("Tamer", "Useless"),
    MUSA("Musa"),
    MAHEWA("Maehwa", "Baehwa"),
    VALKYRIE("Valkyrie", "Valk"),
    KUNOICHI("Kunoichi", "Kuno"),
    NINJA("Ninja"),
    WIZARD("Wizard", "Wiz"),
    WITCH("Witch"),
    MYSTIC("Mystic", "Mystake"),
    STRIKER("Striker"),
    LAHN("Lahn", "Bird"),
    ARCHER("Archer"),
    DARK_KNIGHT("Dark Knight", "dk"),
    Shai("Shai", "Loli"),
    GUARDIAN("Guardian"),
    HASHASHIN("Hashashin", "Hash"),
    NOVA("Nova"),
    SAGE("Sage", "Sadge"),
    CORSAIR("Corsair", "Cors"),
    UNKNOWN("Unknown", "No");


    private final String[] displayNames;

    BdoClass(String... displayNames) {
        this.displayNames = displayNames;
    }

    public String[] getDisplayNames() {
        return displayNames;
    }

    public static BdoClass parseClass(String input) {
        for (BdoClass c : BdoClass.values()) {
            for (String s : c.getDisplayNames()) {
                if (input.trim().equalsIgnoreCase(s))
                    return c;
            }
        }
        return BdoClass.UNKNOWN;
    }
}
