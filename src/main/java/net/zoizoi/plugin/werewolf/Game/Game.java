package net.zoizoi.plugin.werewolf.Game;

import net.zoizoi.plugin.werewolf.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public class Game {
  Main plugin;
  private LinkedHashMap<Player, GamePlayer> playerList = new LinkedHashMap<Player, GamePlayer>();
  private String Result;
  private LinkedHashMap<Player, GamePlayer> villagePlayerList = new LinkedHashMap<Player, GamePlayer>();
  private LinkedHashMap<Player, GamePlayer> wolfPlayerList = new LinkedHashMap<Player, GamePlayer>();
  private LinkedHashMap<Player, GamePlayer> betrayerPlayerList = new LinkedHashMap<Player, GamePlayer>();

  public boolean isReady = false;
  public boolean isRunning = false;

  public Game(Main plugin) {
    this.plugin = plugin;
    Result = "";
  }

  public boolean AddPlayer(Player player) {
    if (playerList.containsKey(player)) {
      // 既に含まれている場合
      return false;
    } else {
      GamePlayer gamePlayer = new GamePlayer(plugin, player);
      playerList.put(player, gamePlayer);
      return true;
    }
  }

  public boolean DeletePlayer(Player player) {
    if (playerList.containsKey(player)) {
      playerList.remove(player);
      return true;
    } else {
      return false;
    }
  }


  public HashMap<Player, GamePlayer> getPlayers() {
    return playerList;
  }

  public void Start(Main plugin) {
    isRunning = true;

    List<Player> shuffledPlayerList = new ArrayList<Player>(playerList.keySet());
    Collections.shuffle(shuffledPlayerList);
    for (int i = 0; i < shuffledPlayerList.size(); i++) {
      plugin.getLogger().info("" + shuffledPlayerList.size());
      plugin.getLogger().info("i = " + i);
      plugin.getLogger().info("Citizen = " + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen"));
      plugin.getLogger().info("Citizen = " + plugin.config.getInt("member.2.Citizen"));
      if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")) {
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Citizen"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは市民です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 市民 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")) {
        plugin.getLogger().info("Prophet");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Prophet"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは予言者です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 予言者 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")) {
        plugin.getLogger().info("Necromancer");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Necromancer"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは霊媒師です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 霊媒師 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else if (i < plugin.config.getInt("member." + shuffledPlayerList.size() + ".Citizen")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Prophet")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Necromancer")
        + plugin.config.getInt("member." + shuffledPlayerList.size() + ".Werewolf")) {
        plugin.getLogger().info("Werewolf");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Werewolf"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは人狼です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 人狼 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      } else {
        plugin.getLogger().info("Betrayer");
        playerList.get(shuffledPlayerList.get(i)).setJob(new Job(plugin, "Betrayer"));
        shuffledPlayerList.get(i).sendTitle("ゲームが開始されました", "あなたは狂人です", 10, 50, 10);
        shuffledPlayerList.get(i).sendMessage("ゲームが開始されました");
        shuffledPlayerList.get(i).sendMessage("あなたは 狂人 です");
        villagePlayerList.put(shuffledPlayerList.get(i), playerList.get(shuffledPlayerList.get(i)));
      }

    }
  }

  public boolean PlayerDie(GamePlayer gamePlayer) {
    gamePlayer.setLife(false);
    switch (gamePlayer.getJob().getJobName()) {
      case "Citizen":
      case "Prophet":
      case "Necromancer":
        villagePlayerList.remove(gamePlayer);
        break;
      case "Werewolf":
        wolfPlayerList.remove(gamePlayer);
        break;
      case "Betrayer":
        betrayerPlayerList.remove(gamePlayer);
        break;
      default:
        break;
    }
    if (villagePlayerList.size() == 0) {
      this.Result = "Village";
      return true;
    } else if (wolfPlayerList.size() == 0) {
      this.Result = "Wolf";
      return true;
    } else {
      return false;
    }
  }

  public void Stop() {
    isRunning = false;
  }
  public String getResult(){
    return Result;
  }
}
