package earth.terrarium.heracles.api.client.theme;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;

public record QuestsScreenTheme(
    Color headerTitle,
    Color headerGroupsTitle,
    Color groupName,
    Color completedGroupName
) {

    public static final QuestsScreenTheme DEFAULT = new QuestsScreenTheme(
        new Color(0x404040),
        new Color(0x404040),
        new Color(0xFFFFFF),
        new Color(0x6AAB73)
    );

    public static final Codec<QuestsScreenTheme> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Color.CODEC.fieldOf("headerTitle").orElse(DEFAULT.headerTitle()).forGetter(QuestsScreenTheme::headerTitle),
        Color.CODEC.fieldOf("headerGroupsTitle").orElse(DEFAULT.headerGroupsTitle()).forGetter(QuestsScreenTheme::headerGroupsTitle),
        Color.CODEC.fieldOf("groupName").orElse(DEFAULT.groupName()).forGetter(QuestsScreenTheme::groupName),
        Color.CODEC.fieldOf("completedGroupName").orElse(DEFAULT.completedGroupName()).forGetter(QuestsScreenTheme::completedGroupName)
    ).apply(instance, QuestsScreenTheme::new));

    public static int getHeaderTitle() {
        return Theme.getInstance().questsScreen().headerTitle().getValue();
    }

    public static int getHeaderGroupsTitle() {
        return Theme.getInstance().questsScreen().headerGroupsTitle().getValue();
    }

    public static int getGroupName() {
        return Theme.getInstance().questsScreen().groupName().getValue();
    }

    public static int getCompletedGroupName() {
        return Theme.getInstance().questsScreen().completedGroupName().getValue();
    }
}
