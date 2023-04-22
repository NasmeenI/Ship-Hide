package utilz;

public class Constants {
	
	public static class GameProcess {
		public static final int ORIGINAL_TILE_SIZE = 16;
	    public static final int SCALE = 3;
	    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
	    public static final int MAX_SCREEN_COL = 110;
	    public static final int MAX_SCREEN_ROW = 60;
	    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
	    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
	}
	
	public static class GameState {
		public static final int PLAY_STATE = 1;
	    public static final int PAUSE_STATE = 2;
	    public static final int GAME_OVER_STATE = 3;
	    public static final int GAME_COMPLETE_STATE = 4;
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
		}
	}
	
	public static class Screen {
		public static String TITLE = "Ship Hide";
		public static final int S_WIDTH_DEFAULT = 960;
		public static final int S_HEIGHT_DEFAULT = 640;
		public static final int FPS = 600;
	}
	
	public static class Player {
		public static final int P_WIDTH = 50;
		public static final int P_HEIGHT = 90;
	}
	
	public static class Tile {
		public static final int TILESIZE = 48;
	}
	
	public static class DoorJail {
		public static final int X_POS_DOORJAIL = 71;
		public static final int Y_POS_DOORJAIL = 15;
	}

	public static class Debug {
		public static final boolean SOLID_SHOW = false;;
	}
}