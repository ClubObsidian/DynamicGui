package me.virustotal.dynamicgui.util;

public enum Statistic {

	ANIMALS_BRED("animals_bred", "animals_bred"),
	ARMOR_CLEANED("armor_cleaned", "armor_cleaned"),
	AVIATE_ONE_CM("aviate_one_cm", "aviate_one_cm"),
	BANNER_CLEANED("banner_cleaned", "banner_cleaned"),
	BEACON_INTERACTION("beacon_interaction", "beacon_interaction"),
	BOAT_ONE_CM("boat_one_cm", "boat_one_cm"),
	BREAK_ITEM("break_item", null),
	BREWINGSTAND_INTERACTION("brewingstand_interaction", "brewingstand_interaction"),
	CAKE_SLICES_EATEN("cakes_slices_eaten", "cakes_slices_eaten"),
	CAULDRON_FILLED("cauldron_filled", "cauldron_filled"),
	CAULDRON_USED("cauldron_used", "cauldron_used"),
	CHEST_OPENED("chest_opened", "chest_opened"),
	CLIMB_ONE_CM("climb_one_cm", "climb_one_cm"),
	CRAFT_ITEM("craft_item", null),
	CRAFTING_TABLE_INTERACTION("crafting_table_interaction", "crafting_table_interaction"),
	CROUCH_ONE_CM("crouch_one_cm", "crouch_one_cm"),
	DAMAGE_DEALT("damage_dealt", "damage_dealt"),
	DAMAGE_TAKEN("damage_taken", "damage_taken"),
	DEATHS("deaths", "deaths"),
	DISPENSER_INSPECTED("dispenser_inspected", "dispenser_inspected"),
	DROP("drop", "drop"),
	DROP_COUNT("drop_count", null),
	DROPPER_INSPECTED("dropper_inspected", "dropped_inspected"),
	ENDERCHEST_OPENED("enderchest_opened", "enderchest_opened"),
	ENTITY_KILLED_BY("entity_killed_by", null),
	FALL_ONE_CM("fall_one_cm", "fall_one_cm"),
	FISH_CAUGHT,
	FLOWER_POTTED,
	FLY_ONE_CM,
	FURNACE_INTERACTION,
	HOPPER_INSPECTED,
	HORSE_ONE_CM,
	ITEM_ENCHANTED,
	JUMP,
	KILL_ENTITY,
	LEAVE_GAME,
	MINE_BLOCK,
	MINECART_ONE_CM,
	MOB_KILLS,
	NOTEBLOCK_PLAYED,
	NOTEBLOCK_TUNED,
	PICKUP,
	PIG_ONE_CM,
	PLAYER_KILLS,
	RECORD_PLAYED,
	SHULKER_BOX_OPENED,
	SLEEP_IN_BED,
	SNEAK_TIME,
	SPRINT_ONE_CM,
	SWIM_ONE_CM,
	TALKED_TO_VILLAGER,
	TIME_PLAYED,
	TIME_SINCE_DEATH,
	TIME_SINCE_REST,
	TRADED_WITH_VILLAGER,
	TRAPPED_CHEST_TRIGGERED,
	USE_ITEM,
	WALK_ON_WATER_ONE_CM,
	WALK_ONE_CM,
	WALK_UNDER_WATER_ONE_CM;
	
	private String bukkitStatistic;
	private String spongeStatistic;
	private Statistic(String bukkitStatistic, String spongeStatistic)
	{
		this.bukkitStatistic = bukkitStatistic;
		this.spongeStatistic = spongeStatistic;
	}
}