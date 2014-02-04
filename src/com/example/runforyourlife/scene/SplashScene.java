package com.example.runforyourlife.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import com.example.runforyourlife.scene.SceneManager.SceneType;

/**
 * @author Cataldo Nicolas
 * @version 1.0
 */
public class SplashScene extends BaseScene
{
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
	private Sprite _splash;
	
    @Override
    public void createScene()
    {
    	_splash = new Sprite(0, 0, resourcesManager.splash_region, vbom)
    	{
    	    @Override
    	    protected void preDraw(GLState pGLState, Camera pCamera) 
    	    {
    	       super.preDraw(pGLState, pCamera);
    	       pGLState.enableDither();
    	    }
    	};
    	_splash.setScale(2.3f);
    	_splash.setPosition(230, 120);
    	attachChild(_splash);
    }

    @Override
    public void onBackKeyPressed()
    {

    }

    @Override
    public SceneType getSceneType()
    {
    	return SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene()
    {
    	_splash.detachSelf();
        _splash.dispose();
        this.detachSelf();
        this.dispose();
    }
}