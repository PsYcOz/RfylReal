package 								com.example.runforyourlife;


import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import 									org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import 									org.andengine.engine.options.EngineOptions;
import 									org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import 									org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import 									org.andengine.entity.scene.Scene;
import 									org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import com.example.runforyourlife.scene.SceneManager;


/** @author Cataldo Nicolas
 *  @version 1.0
 *  @contact cataldo.nico@gmail.com
 */
public class GameActivity extends BaseGameActivity 
{

	//-------------------------------------------
	// ATTRIBUTES
	//-------------------------------------------
	
	//_ENGINE OPTIONS_

	private Camera 							camera;
	private static final int 				CAMERA_WIDTH = 800;
	private static final int 				CAMERA_HEIGHT = 480;
	
	
	//_GRAPHICS OPTIONS_
	ResourcesManager 						resourcesManager;
	
	//-------------------------------------------
	// METHODS
	//-------------------------------------------
		
	//--ENGINE OPTIONS--
	@Override
	public Engine 							onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}

	public EngineOptions 					onCreateEngineOptions()
	{
	    camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}
	
	//--RESSOURCES LOADER--
	@Override
	public void 							onCreateResources(	OnCreateResourcesCallback pOnCreateResourcesCallback)
											throws Exception 
	{
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    resourcesManager = ResourcesManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void 							onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
											throws Exception 
	{	
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	//-- MAIN BEHAVIOUR HANDLING
	@Override
	public void 							onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
	    mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	            public void 				onTimePassed(final TimerHandler pTimerHandler) 
	            {
	                mEngine.unregisterUpdateHandler(pTimerHandler);
	                SceneManager.getInstance().createMenuScene();
	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}
}