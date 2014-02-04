package com.example.runforyourlife.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;
import com.example.runforyourlife.scene.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener 
{
	private MenuScene 							menuChildScene;
	private final int 						MENU_PLAY = 0;
	private final int 						MENU_HARDCORE = 1;
	private final int 						MENU_OPTIONS = 2;
	
	private void 								createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playNormalMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_normal_region, vbom), 1.2f, 1);
	    final IMenuItem playHardcoreMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_hardcore_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playNormalMenuItem);
	    menuChildScene.addMenuItem(playHardcoreMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playNormalMenuItem.setPosition(playNormalMenuItem.getX()+250, playNormalMenuItem.getY() + 10);
	    playHardcoreMenuItem.setPosition(playHardcoreMenuItem.getX()+250, playHardcoreMenuItem.getY() + 20);
	    optionsMenuItem.setPosition(optionsMenuItem.getX()+250, optionsMenuItem.getY()+20);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}
	
	public boolean 							onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
	        switch(pMenuItem.getID())
	        {
	        	case MENU_PLAY:
	        		SceneManager.getInstance().loadGameScene(engine);
	        		return true;
	        	case MENU_HARDCORE:
	        		return true;
	        	case MENU_OPTIONS:
	        		return true;
	        	default:
	        		return false;
	    }
	}
	private void 								createBackground()
	{
		attachChild(new Sprite(0, 30, resourcesManager.menu_background_region, vbom)
	    {
	        @Override
	        protected void 					preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}
	
	
	@Override
	public void 								createScene() 
	{
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void 								onBackKeyPressed() 
	{
		System.exit(0);
	}

	@Override
	public SceneType 							getSceneType() 
	{
		return SceneType.SCENE_MENU;
	}

	@Override
	public void 								disposeScene() 
	{
		// TODO Auto-generated method stub
		
	}

}
