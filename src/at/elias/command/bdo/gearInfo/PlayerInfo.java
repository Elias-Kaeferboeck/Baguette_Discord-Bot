package at.elias.command.bdo.gearInfo;

import at.elias.sql.Manager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class PlayerInfo {
    private User owner;

    private String familyName, characterName, note;
    private int ap, aap, dp, gs, accuracy, evasion, damageReduction, level, hp;

    private boolean awakening;

    private BdoClass playerClass;

    public PlayerInfo(User owner) {
        this.owner = owner;

        this.familyName = "";
        this.characterName = "";
        this.note = "";

        this.ap = 0;
        this.aap = 0;
        this.dp = 0;
        this.gs = 0;
        this.accuracy = 0;
        this.evasion = 0;
        this.damageReduction = 0;
        this.level = 0;
        this.hp = 0;

        this.awakening = false;

        this.playerClass = BdoClass.UNKNOWN;
    }

    public PlayerInfo setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public PlayerInfo setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public PlayerInfo setNote(String note) {
        this.note = note;
        return this;
    }

    public PlayerInfo setAp(int ap) {
        this.ap = ap;
        return this.setGs();
    }

    public PlayerInfo setAap(int aap) {
        this.aap = aap;
        return this.setGs();
    }

    public PlayerInfo setDp(int dp) {
        this.dp = dp;
        return this.setGs();
    }

    public PlayerInfo setAccuracy(int accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public PlayerInfo setGs() {
        int ap = getAp() == 0 ? 1 : getAp();
        int aap = getAap() == 0 ? 1 : getAap();
        int dp = getDp();
        this.gs = ((int) Math.ceil(ap + aap) / 2) + dp;
        return this;
    }

    public PlayerInfo setLevel(int level) {
        this.level = level;
        return this;
    }

    public PlayerInfo setHp(int hp) {
        this.hp = hp;
        return this;
    }

    public PlayerInfo setEvasion(int evasion) {
        this.evasion = evasion;
        return this;
    }

    public PlayerInfo setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
        return this;
    }

    public PlayerInfo setAwakening(boolean awakening) {
        this.awakening = awakening;
        return this;
    }

    public PlayerInfo setPlayerClass(BdoClass playerClass) {
        this.playerClass = playerClass;
        return this;
    }

    public BdoClass getPlayerClass() {
        return playerClass;
    }

    public int getAap() {
        return aap;
    }

    public int getAp() {
        return ap;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public int getDp() {
        return dp;
    }

    public int getEvasion() {
        return evasion;
    }

    public int getGs() {
        return gs;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getNote() {
        return note;
    }

    public User getOwner() {
        return owner;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public boolean isAwakening() {
        return awakening;
    }

    public MessageEmbed toEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setThumbnail(getOwner().getAvatarUrl());
        eb.setTitle((getFamilyName().equalsIgnoreCase("0") ? "?" : getFamilyName()) + " (" + (getCharacterName().equalsIgnoreCase("0") ? "?" : getCharacterName()) + ")");
        eb.addField("Level", (getLevel() == 0 ? "?" : getLevel()) + "", true);
        eb.addField("Class", getPlayerClass().getDisplayNames()[0], true);
        eb.addField("Awakening?", Boolean.toString(isAwakening()), true);
        eb.addField("AP", (getAp() == 0 ? "?" : getAp()) + "", true);
        eb.addField("Awakening AP", (getAap() == 0 ? "?" : getAap()) + "", true);
        eb.addField("DP", (getDp() == 0 ? "?" : getDp()) + "", true);
        eb.addField("Accuracy", (getAccuracy() == 0 ? "?" : getAccuracy()) + "", true);
        eb.addField("Evasion", (getEvasion() == 0 ? "?" : getEvasion()) + "", true);
        eb.addField("Damage Reduction", (getDamageReduction() == 0 ? "?" : getDamageReduction()) + "", true);
        eb.addField("Gearscore", (getGs() <= 1 ? "?" : getGs()) + "", true);
        eb.addBlankField(true);
        eb.addField("HP", (getHp() == 0 ? "?" : getHp()) + "", true);
        if (getNote().equalsIgnoreCase("0"))
            eb.addField("Additional Note", getNote(), false);
        return eb.build();
    }

    public void save() {
        String id = getOwner().getId();
        saveValue(id, "family_name", getFamilyName());
        saveValue(id, "character_name", getCharacterName());
        saveValue(id, "note", getNote());
        saveValue(id, "ap", getAp() + "");
        saveValue(id, "aap", getAap() + "");
        saveValue(id, "dp", getDp() + "");
        saveValue(id, "accuracy", getAccuracy() + "");
        saveValue(id, "evasion", getEvasion() + "");
        saveValue(id, "level", getLevel() + "");
        saveValue(id, "hp", getHp() + "");
        saveValue(id, "damage_reduction", getDamageReduction() + "");
        saveValue(id, "awakening ", Boolean.toString(isAwakening()));
        saveValue(id, "class", getPlayerClass().getDisplayNames()[0]);

    }

    private void saveValue(String id, String type, String value) {
        new Manager().setString(id, "playerinfo", type, value);
    }

    public PlayerInfo load() {
        String id = getOwner().getId();
        PlayerInfo playerInfo = new PlayerInfo(owner);

        playerInfo.setFamilyName(getValue(id, "family_name"));
        playerInfo.setCharacterName(getValue(id, "character_name"));
        playerInfo.setNote(getValue(id, "note"));
        playerInfo.setAp(Integer.parseInt(getValue(id, "ap")));
        playerInfo.setAap(Integer.parseInt(getValue(id, "aap")));
        playerInfo.setDp(Integer.parseInt(getValue(id, "dp")));
        playerInfo.setAccuracy(Integer.parseInt(getValue(id, "accuracy")));
        playerInfo.setEvasion(Integer.parseInt(getValue(id, "evasion")));
        playerInfo.setLevel(Integer.parseInt(getValue(id, "level")));
        playerInfo.setHp(Integer.parseInt(getValue(id, "hp")));
        playerInfo.setDamageReduction(Integer.parseInt(getValue(id, "damage_reduction")));
        playerInfo.setAwakening(Boolean.parseBoolean(getValue(id, "awakening")));
        playerInfo.setPlayerClass(BdoClass.parseClass(getValue(id, "class")));

        return playerInfo;
    }

    private String getValue(String id, String type) {
        return new Manager().getString(id, "playerinfo", type);
    }
}
