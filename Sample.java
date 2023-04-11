package plugin.sample;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.swing.ListSelectionModel;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sample extends JavaPlugin implements Listener {

  List<String> a = new ArrayList<>();
  private int count = 0;

  @Override
  public void onEnable() {
    // Plugin startup logic
    Bukkit.getPluginManager().registerEvents(this, this);

    List<Integer> numberList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    numberList.stream()
        .mapToInt(number -> number)
        .filter(number -> number <= 5)
        .forEach(System.out::println);

    String mailed = inf@raise-tech.net;
  }


  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * @param e イベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) throws IOException {
    // イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.getPlayer();
    World world = player.getWorld();

    if (count % 2 == 0) {
        // 花火オブジェクトをプレイヤーのロケーション地点に対して出現させる。
        Firework firework = world.spawn(player.getLocation(), Firework.class);

        // 花火オブジェクトが持つメタ情報を取得。
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        // メタ情報に対して設定を追加したり、値の上書きを行う。
        // 今回は青色で星型の花火を打ち上げる。
        fireworkMeta.addEffect(
            FireworkEffect.builder()
                .withColor(Color.RED)
                .with(Type.BALL_LARGE)
                .withFlicker()
                .build());
        fireworkMeta.setPower(1);

        // 追加した情報で再設定する。
        firework.setFireworkMeta(fireworkMeta);

        //firework.txtに書き込みを行う
        Path path = Path.of("firework.txt");
        Files.writeString(path, "花火があがった！");
        player.sendMessage(Files.readString(path));
    }
    count++;
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();
    Arrays.stream(itemStacks)
        .filter(
            item -> !Objects.isNull(item) && item.getMaxStackSize() == 64 && item.getAmount() < 64)
        .forEach(item -> item.setAmount(64));
    player.getInventory().setContents(itemStacks);
  }


  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
