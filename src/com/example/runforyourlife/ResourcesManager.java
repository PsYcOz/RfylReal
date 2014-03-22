package com.example.runforyourlife;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * @author Cataldo Nicolas
 * @version 1.0
 */
public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager 	INSTANCE = new ResourcesManager();
    public Engine 								engine;
    public GameActivity 						activity;
    public Camera 								camera;
    public VertexBufferObjectManager 			vbom;

      	//SPLASHSCREEN//
    	public ITextureRegion 					splash_region;
    	private BitmapTextureAtlas 				splashTextureAtlas;
    	
    	//MENU//
    	private BuildableBitmapTextureAtlas 	menuTextureAtlas;
    	public ITextureRegion 					menu_background_region;
    	public ITextureRegion 					play_normal_region;
    	public ITextureRegion 					play_hardcore_region;
    	public ITextureRegion 					options_region;
    	
    	public Font 							font;
    	
    	//GAME//
    	
    	//Obstacle Gfx
	   	private BuildableBitmapTextureAtlas 	gameTextureAtlasObstacle;
	   	
	   	public ITiledTextureRegion 				gameTextureRegionGrave;
	   	public ITiledTextureRegion 				gameTextureRegionMovingobs;
	   	public ITiledTextureRegion 				gameTextureRegionFlyinggobs;
	   	
    	//Platform Gfx
	   	private BuildableBitmapTextureAtlas 	gameTextureAtlasSquarePlatform;
	   	private BuildableBitmapTextureAtlas 	gameTextureAtlasOthersPlatform;
	   	private BuildableBitmapTextureAtlas 	gameTextureAtlasBackground;

	    	
	   	public ITextureRegion 					gameTextureRegionPlatformGrassSquare1;
	   	public ITextureRegion 					gameTextureRegionPlatformSquare1;
	   	public ITextureRegion 					gameTextureRegionPlatformGrassSquare2;
	   	public ITextureRegion 					gameTextureRegionPlatformSquare2;
	   	public ITextureRegion 					gameTextureRegionPlatformGrassPillar;
	   	public ITextureRegion 					gameTextureRegionPlatformPillar;
	   	public ITextureRegion 					gameTextureRegionPlatformGrassLittleAir;
	   	public ITextureRegion 					gameTextureRegionPlatformLittleAir;
	   	public ITextureRegion 					gameTextureRegionBackground;
	   	
	   	//MainChar Gfx
	   	private BuildableBitmapTextureAtlas 	gameTextureAtlasCharacter;
	   	public ITiledTextureRegion 				gameTiledTextureRegionCharacter;
	   	
	   	//Sound
	   	public Music							 mGameTheme;
    //---------------------------------------------
    // METHODS
    //---------------------------------------------

    //---------------------------------------------
    	// SPLASHSCREEN
    //---------------------------------------------
    public void 								loadSplashScreen()
    {
       	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
       	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
       	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
       	splashTextureAtlas.load();
    }
        
    public void 								unloadSplashScreen()
    {
      	splashTextureAtlas.unload();
       	splash_region = null;
    }
    
    //---------------------------------------------
		// MENU
    //---------------------------------------------
    public void 								loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    private void 								loadMenuGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "ecrantitre.png");
    	play_normal_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btnModeNormal.png");
    	play_hardcore_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btnModeHardcore.png");
    	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btnOptions.png");

    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    private void 								loadMenuAudio()
    {
        
    }
    
    private void 								loadMenuFonts()
    {
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        font = FontFactory.createFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "HammersmithOne.ttf", 50, true, 2);
        font.load();
    }

    
    public void 								unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }
        
    public void 								loadMenuTextures()
    {
        menuTextureAtlas.load();
    }

    //---------------------------------------------
		// GAME
    //---------------------------------------------
    public void 								loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    private void 								loadGameGraphics()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
    	
        //PLATFORMS
    	gameTextureAtlasSquarePlatform = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	gameTextureAtlasOthersPlatform = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	gameTextureAtlasBackground = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	
    	gameTextureRegionPlatformGrassSquare1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasSquarePlatform, activity, "GrassBig01.png");
    	gameTextureRegionPlatformSquare1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasSquarePlatform, activity, "PlatFormBig01.png");
    	gameTextureRegionPlatformGrassSquare2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasSquarePlatform, activity, "GrassBig02.png");
    	gameTextureRegionPlatformSquare2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasSquarePlatform, activity, "PlatFormBig02.png");
    	
    	gameTextureRegionPlatformGrassPillar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasOthersPlatform, activity, "GrassPillar.png");
    	gameTextureRegionPlatformPillar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasOthersPlatform, activity, "PlatFormPillar.png");
    	gameTextureRegionPlatformGrassLittleAir = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasOthersPlatform, activity, "GrassPillar.png");
    	gameTextureRegionPlatformLittleAir = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasOthersPlatform, activity, "PlatFormLittleAir.png");
    	
    	gameTextureRegionBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlasOthersPlatform, activity, "Landscape.png");
    	
    	//Obstacles
    	gameTextureAtlasObstacle = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	gameTextureRegionGrave = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasObstacle, activity, "Grave.png", 4, 1);
    	gameTextureRegionMovingobs = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasObstacle, activity, "What.png", 3, 1);
    	gameTextureRegionFlyinggobs = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasObstacle, activity, "Flying.png", 5, 1);
    	
    	//MainChar
    	gameTextureAtlasCharacter = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	gameTiledTextureRegionCharacter = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlasCharacter, activity, "gurlz.png", 6, 1);
    	
    	try 
        {
            this.gameTextureAtlasSquarePlatform.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.gameTextureAtlasSquarePlatform.load();
            
            this.gameTextureAtlasOthersPlatform.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.gameTextureAtlasOthersPlatform.load();
            
            this.gameTextureAtlasBackground.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.gameTextureAtlasBackground.load();
            
            this.gameTextureAtlasObstacle.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.gameTextureAtlasObstacle.load();
            
            
            this.gameTextureAtlasCharacter.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.gameTextureAtlasCharacter.load();
        } 
        catch (final TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }
    }
    
    private void 								loadGameFonts()
    {
        
    }
    
    private void 								loadGameAudio()
    {
    	MusicFactory.setAssetBasePath("mfx/");
    	
    	try 
    	{
    		this.mGameTheme = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "gameTheme01.mp3");
    	} catch (final IOException e) {
			Debug.e(e);
		}
    }
    public void 								unloadGameTextures()
    {
        // TODO (Since we did not create any textures for game scene yet)
    }

    //--ENGINE
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void 						prepareManager(Engine engine
    															, GameActivity activity
    															, Camera camera
    															, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager 			getInstance()
    {
        return INSTANCE;
    }
}

