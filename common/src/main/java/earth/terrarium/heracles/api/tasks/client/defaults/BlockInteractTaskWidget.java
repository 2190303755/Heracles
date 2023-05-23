package earth.terrarium.heracles.api.tasks.client.defaults;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack;
import earth.terrarium.heracles.api.client.DisplayWidget;
import earth.terrarium.heracles.api.client.WidgetUtils;
import earth.terrarium.heracles.api.tasks.QuestTaskDisplayFormatter;
import earth.terrarium.heracles.api.tasks.client.display.TaskTitleFormatter;
import earth.terrarium.heracles.api.tasks.defaults.BlockInteractTask;
import earth.terrarium.heracles.common.handlers.progress.TaskProgress;
import earth.terrarium.heracles.common.utils.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.ByteTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record BlockInteractTaskWidget(
    BlockInteractTask task, TaskProgress<ByteTag> progress, List<ItemStack> stacks
) implements DisplayWidget {

    private static final Component DESCRIPTION = Component.translatable("task.heracles.block_interaction.desc.singular");

    public BlockInteractTaskWidget(BlockInteractTask task, TaskProgress<ByteTag> progress) {
        this(task, progress, task.block().getValue().map(
            block -> List.of(block.asItem().getDefaultInstance()),
            tag -> ModUtils.getValue(Registries.BLOCK, tag).stream().map(ItemStack::new).toList()
        ));
    }

    @Override
    public void render(PoseStack pose, ScissorBoxStack scissor, int x, int y, int width, int mouseX, int mouseY, boolean hovered, float partialTicks) {
        Font font = Minecraft.getInstance().font;
        WidgetUtils.drawBackground(pose, x, y, width);
        int iconSize = (int) (width * 0.1f);
        Minecraft.getInstance().getItemRenderer().renderGuiItem(pose, getCurrentItem(), x + 5 + (int) (iconSize / 2f) - 8, y + 5 + (int) (iconSize / 2f) - 8);
        font.draw(pose, TaskTitleFormatter.create(this.task), x + iconSize + 10, y + 5, 0xFFFFFFFF);
        font.draw(pose, DESCRIPTION, x + iconSize + 10, y + 7 + font.lineHeight, 0xFF808080);
        String progress = QuestTaskDisplayFormatter.create(this.task, this.progress);
        font.draw(pose, progress, x + width - 5 - font.width(progress), y + 5, 0xFFFFFFFF);

        int progressY = y + 5 + (font.lineHeight + 2) * 2;
        WidgetUtils.drawProgressBar(pose, x + iconSize + 10, progressY + 2, x + width - 5, progressY + font.lineHeight - 2, this.task, this.progress);
    }

    private ItemStack getCurrentItem() {
        if (this.stacks.isEmpty()) {
            return ItemStack.EMPTY;
        }
        int index = Math.max(0, (int) ((System.currentTimeMillis() / 1000) % this.stacks.size()));
        return this.stacks.get(index);
    }

    @Override
    public int getHeight(int width) {
        return (int) (width * 0.1f) + 10;
    }

}
