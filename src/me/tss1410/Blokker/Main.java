package me.tss1410.Blokker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import me.tss1410.Blokker.Chat.ChatHandler;
import me.tss1410.Blokker.Chat.ChatListener;
import me.tss1410.Blokker.Commands.AFK;
import me.tss1410.Blokker.Commands.Back;
import me.tss1410.Blokker.Commands.Ban;
import me.tss1410.Blokker.Commands.Bank;
import me.tss1410.Blokker.Commands.Blokker;
import me.tss1410.Blokker.Commands.CWarn;
import me.tss1410.Blokker.Commands.DelHome;
import me.tss1410.Blokker.Commands.DelWarn;
import me.tss1410.Blokker.Commands.DelWarp;
import me.tss1410.Blokker.Commands.H;
import me.tss1410.Blokker.Commands.Home;
import me.tss1410.Blokker.Commands.Homes;
import me.tss1410.Blokker.Commands.Kick;
import me.tss1410.Blokker.Commands.LW;
import me.tss1410.Blokker.Commands.M;
import me.tss1410.Blokker.Commands.R;
import me.tss1410.Blokker.Commands.Raid;
import me.tss1410.Blokker.Commands.Rank;
import me.tss1410.Blokker.Commands.S;
import me.tss1410.Blokker.Commands.SS;
import me.tss1410.Blokker.Commands.Saldo;
import me.tss1410.Blokker.Commands.SetHome;
import me.tss1410.Blokker.Commands.SetWarp;
import me.tss1410.Blokker.Commands.Sinfo;
import me.tss1410.Blokker.Commands.Sitt;
import me.tss1410.Blokker.Commands.TP;
import me.tss1410.Blokker.Commands.TPH;
import me.tss1410.Blokker.Commands.Tempban;
import me.tss1410.Blokker.Commands.Tips;
import me.tss1410.Blokker.Commands.Unban;
import me.tss1410.Blokker.Commands.Viktig;
import me.tss1410.Blokker.Commands.Warn;
import me.tss1410.Blokker.Commands.Warp;
import me.tss1410.Blokker.Commands.Warps;
import me.tss1410.Blokker.Commands.Work;
import me.tss1410.Blokker.Diverse.AltAnnetListener;
import me.tss1410.Blokker.Diverse.BankHandler;
import me.tss1410.Blokker.Home.HomeData;
import me.tss1410.Blokker.MySQL.MySQL;
import me.tss1410.Blokker.PVPRaid.BlokkHandler;
import me.tss1410.Blokker.PVPRaid.Timer;
import me.tss1410.Blokker.Players.PlayerHandler;
import me.tss1410.Blokker.Players.PlayerListener;
import me.tss1410.Blokker.Players.Spiller;
import me.tss1410.Blokker.Players.WorkPlayerData;
import me.tss1410.Blokker.Warp.WarpData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin{
	public MySQL sql;
	public boolean isRaiding = false;

	public HashMap<UUID, Integer> timeplayed = new HashMap<UUID, Integer>();
	public HashMap<Spiller, String> sethomemessage = new HashMap<Spiller, String>();
	public List<String> afkusers = new ArrayList<String>();
	public HashMap<UUID, Spiller> spillere = new HashMap<UUID, Spiller>();
	public HashMap<UUID, WorkPlayerData> work = new HashMap<UUID,WorkPlayerData>();
	public HashSet<HomeData> homes = new HashSet<HomeData>();
	public HashSet<WarpData> warps = new HashSet<WarpData>();
	public HashSet<HomeData> deletedhomes = new HashSet<HomeData>();
	public HashMap<Player, Location> deaths = new HashMap<Player, Location>();
	public HashMap<String, String> tpa = new HashMap<String, String>();

	public long ticks = getServer().getWorlds().get(0).getFullTime();

	PlayerHandler ph = new PlayerHandler(this);
	ChatHandler ch = new ChatHandler(this);
	BankHandler bh = new BankHandler();
	BlokkHandler bha = new BlokkHandler(this);
	
	public String noPerm = ChatColor.RED + "Du har ikke tilgang til å utføre denne kommandoen";

	public void onEnable(){
		isRaiding = false;
		setupConfig();
		setupMySQL();
		setupCommands();
		setupListeners();
		startTimer();
		addPlayersToSet();
		addHomesToSet();		
	}

	public void onDisable(){
		restoreWorkers();
		addPlayersToSQL();
	}































	private void setupCommands() {
		getCommand("raid").setExecutor(new Raid(this));
		getCommand("rank").setExecutor(new Rank(this));
		getCommand("ss").setExecutor(new SS());
		getCommand("saldo").setExecutor(new Saldo(this));
		getCommand("bank").setExecutor(new Bank(this));
		getCommand("afk").setExecutor(new AFK(this));
		getCommand("warn").setExecutor(new Warn(this));
		getCommand("lw").setExecutor(new LW(this));
		getCommand("cwarn").setExecutor(new CWarn(this));
		getCommand("delwarn").setExecutor(new DelWarn(this));
		getCommand("back").setExecutor(new Back(this));
		getCommand("work").setExecutor(new Work(this));
		getCommand("sethome").setExecutor(new SetHome(this));
		getCommand("delhome").setExecutor(new DelHome(this));
		getCommand("h").setExecutor(new H());
		getCommand("kick").setExecutor(new Kick());
		getCommand("m").setExecutor(new M());
		getCommand("r").setExecutor(new R());
		getCommand("s").setExecutor(new S());
		getCommand("homes").setExecutor(new Homes(this));
		getCommand("home").setExecutor(new Home(this));
		getCommand("tp").setExecutor(new TP());
		getCommand("tph").setExecutor(new TPH());
		getCommand("setwarp").setExecutor(new SetWarp(this));
		getCommand("delwarp").setExecutor(new DelWarp(this));
		getCommand("warps").setExecutor(new Warps(this));
		getCommand("warp").setExecutor(new Warp(this));
		getCommand("ban").setExecutor(new Ban(this));
		getCommand("blokker").setExecutor(new Blokker(this));
		getCommand("unban").setExecutor(new Unban(this));
		getCommand("tempban").setExecutor(new Tempban(this));
		getCommand("sinfo").setExecutor(new Sinfo(this));
		getCommand("sitt").setExecutor(new Sitt());
		getCommand("viktig").setExecutor(new Viktig());
		getCommand("tips").setExecutor(new Tips());


	}

	@SuppressWarnings("unused")
	public void startTimer(){

		BukkitTask task = new Timer(this).runTaskTimer(this, 1, 1);


	}

	public void stopTimer(){

	}

	public void setupConfig(){

		getConfig().options().copyDefaults(true);
		getConfig().addDefault("user", "root");
		getConfig().addDefault("pass", "123");
		getConfig().addDefault("host", "localhost");
		getConfig().addDefault("port", "3306");
		getConfig().addDefault("database", "Blokker");
		saveConfig();
		getConfig().options().copyDefaults(true);

	}

	public void setupMySQL(){
		this.sql = new MySQL(getConfig().getString("host"), getConfig().getString("port"), getConfig().getString("database"),getConfig().getString("user"), getConfig().getString("pass"));
		if (this.sql.open() == null) {
			System.out.println("SQL LOGIN ERROR!");
		}

		try {
			this.sql.query("CREATE TABLE IF NOT EXISTS `homes` (`id` INT auto_increment primary key, `player` INT, `name` VARCHAR(255), `x` INT, `y` INT, `z` INT, `world` VARCHAR(255), UNIQUE KEY idx_id (id)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
			this.sql.query("CREATE TABLE IF NOT EXISTS `blockprotection` (`id` INT auto_increment primary key, `x` INT, `y` INT, `z` INT, `placer` INT, `date` DATETIME DEFAULT NULL, `world` varchar(255), `material` varchar(255), UNIQUE KEY idx_table_x_y_z_world ( x, y, z, world )) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
			this.sql.query("CREATE TABLE IF NOT EXISTS `players` (`id` INT auto_increment primary key, `uuid` varchar(255), `name` varchar(255), `raid` TINYINT, `balance` INT, `rank` INT, `timeplayed` FLOAT, `raids` INT DEFAULT 0, UNIQUE KEY idx_players_name_uuid_id (name, uuid, id)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
			this.sql.query("CREATE TABLE IF NOT EXISTS `warndata` (`id` INT auto_increment primary key, `player` INT, `setdate` DATETIME, `setter` INT, `reason` VARCHAR(255), UNIQUE KEY idx_id (id)) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
			this.sql.query("CREATE TABLE IF NOT EXISTS `warps` (`id` INT auto_increment primary key, `name` VARCHAR(255), `world` VARCHAR(255), `x` INT, `y`INT, `z` INT) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
			this.sql.query("CREATE TABLE IF NOT EXISTS `bandata` (`id` INT auto_increment primary key, `player` INT, `until` DATETIME, `grunn` VARCHAR(255), `type` TINYINT)");



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setupListeners(){
		//this.getServer().getPluginManager().registerEvents(new BlokkListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new AltAnnetListener(), this);
	}

	public PlayerHandler getPlayerHandler(){
		return ph;
	}

	public ChatHandler getChatHandler(){
		return ch;
	}

	public BankHandler getBankHandler(){
		return bh;
	}

	public BlokkHandler getBlockHandler(){
		return bha;
	}
	public void addPlayersToSet(){
		try {
			ResultSet rs = sql.query("SELECT * FROM players");
			while(rs.next()){
				spillere.put(UUID.fromString(rs.getString("uuid")), new Spiller(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getInt("id"), rs.getBoolean("raid"), rs.getInt("balance"), getChatHandler().getRankFromInt(rs.getInt("rank")), rs.getInt("timeplayed")));
				System.out.println("Addet " + rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addPlayersToSQL(){

		for(Spiller s : spillere.values()){
			try {
				int balance = s.getBalance();
				String name = s.getName();
				int raid = getPlayerHandler().getIntFromBoolean(s.getRaid());
				int rank = getChatHandler().getRankInt(s.getRank());
				int i = s.getTimePlayed();
				sql.query("UPDATE players SET balance=" + balance + ", name='" + name + "', raid=" + raid + ", rank=" + rank + ", timeplayed=" + i + " WHERE uuid='" + s.getUUID() + "'");
				//sql.query("REPLACE INTO players (balance, name, raid, rank) VALUES (" + balance + ", '" + name + "', " + raid + ", " + rank + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void restoreWorkers(){
		for(WorkPlayerData w : work.values()){
			Player p = Bukkit.getPlayer(w.getUUID());
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().setArmorContents(w.getArmor());
			p.getInventory().setArmorContents(w.getArmor());
			if(p.isFlying()){
				p.teleport(p.getWorld().getHighestBlockAt(p.getLocation()).getLocation());
			}
		}
	}

	public void addHomesToSet(){
		try {
			ResultSet rs = sql.query("SELECT * FROM homes");

			while(rs.next()){
				homes.add(new HomeData(rs.getInt("x"), rs.getInt("y"), rs.getInt("z"), rs.getString("world"), rs.getString("name"), rs.getInt("player"), ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addWarpsToSet(){
		try {
			ResultSet rs = sql.query("SELECT * FROM warps");

			while(rs.next()){
				warps.add(new WarpData(rs.getString("world"), rs.getString("name"), rs.getInt("x"), rs.getInt("y"), rs.getInt("z")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
