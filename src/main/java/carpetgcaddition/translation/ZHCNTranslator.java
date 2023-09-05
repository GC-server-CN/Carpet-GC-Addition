package carpetgcaddition.translation;

import java.util.HashMap;

class ZHCNTranslator {
    public static Translator create() {
        var t = new HashMap<String, String>();
        var builder = new TranslationBuilder(t);

        addRule(builder);
        addRuleValidErrorMessage(builder);

        return new Translator(t);
    }

    private static void addRule(TranslationBuilder builder) {
        builder.rule("noteBlockChunkLoader")
            .name("音符盒加载器")
            .desc("当音符盒被触发时加载周围区块");
        builder.rule("pistonHeadChunkLoader")
            .name("活塞(头)加载器")
            .desc("当上方有黑曜石的活塞伸出与回收时，加载活塞头所在的区块6个游戏刻")
            .extra("强烈建议与音符盒加载器一起使用");
        builder.rule("noteBlockChunkLoaderTime")
            .name("音符盒加载时长")
            .desc("每次触发音符盒加载区块的时长(游戏刻)")
            .extra("需开启音符盒加载器(noteBlockChunkLoader)规则");
        builder.rule("noteBlockChunkLoaderNote")
            .name("音符盒加载器音高")
            .desc("设置音符盒触发音符盒区块加载需要的音高")
            .extra("当值为 -1 时，总是会触发加载")
            .extra("当值为 0 - 24 时，音符盒调整到对应的音高才会触发加载")
            .extra("需开启音符盒加载器(noteBlockChunkLoader)规则");
        builder.rule("noteBlockChunkLoaderRadius")
            .name("音符盒加载器加载半径")
            .desc("强加载半径")
            .extra("需开启音符盒加载器(noteBlockChunkLoader)规则");

        builder.rule("playerMessageProperOrderDisabled")
            .name("禁止验证玩家消息发送顺序")
            .desc("禁止服务器验证玩家发送消息的顺序")
            .extra("可以粗暴的修复粘贴投影时会被服务器踢出的问题");
        builder.rule("fakePlayerCollisionWithPlayerDisabled")
            .name("禁用假人与玩家之间的碰撞")
            .desc("禁用假人与其他任何玩家(包括假人)之间的碰撞")
            .extra("可以使用 /fakePlayer 的 collision 选项针对单个假人覆盖配置");

        builder.rule("softDeepslate")
            .name("易碎深板岩")
            .desc("开启后可使用下界合金稿秒破深板岩 (需要急迫2 + 效率5)");

        builder.rule("keepTickEntities")
            .name("保持实体更新")
            .desc("开启后总是进行实体更新，可绕过服务器要求有玩家在线才进行更新的行为");
    }

    private static void addRuleValidErrorMessage(TranslationBuilder builder) {
        builder.message(MessageTranslationKeys.RangeValidatorFail, "值必须在 %s 至 %s 之间");
        builder.message(MessageTranslationKeys.FakePlayerNotExisting, "假人不存在");
    }
}
